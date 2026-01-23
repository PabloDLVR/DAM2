#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
import csv
import sqlite3
from datetime import datetime

DB_PATH = "gps.sqlite"
CSV_PATH = "mapa.csv"
ALT_PERCENT_DEFAULT = 0.15

# ---------------------------------------------------------------------
# CSV propio (40 nodos, 80 aristas dirigidas, >=40 unidireccionales)
# ---------------------------------------------------------------------

def _lcg(seed):
    # Generador congruencial lineal determinista (evita random)
    return (1103515245 * seed + 12345) % (2**31)

def generate_default_edges_40_80():
    """
    Devuelve lista de aristas (origen, destino, coste).
    - 40 nodos: N01..N40
    - 80 aristas dirigidas
    - 50 unidireccionales (sin reverso)
    - 30 aristas formando 15 pares bidireccionales
    """
    nodes = [f"N{str(i).zfill(2)}" for i in range(1, 41)]
    edges = []
    used = set()  # (u,v)

    # 15 pares bidireccionales (30 aristas)
    # Los pares están elegidos para ser estables y evitar duplicados.
    for i in range(1, 16):
        a = nodes[i - 1]          # N01..N15
        b = nodes[i + 14]         # N16..N30
        cost = 10 + (i % 9) * 3   # coste positivo
        if (a, b) not in used and (a != b):
            edges.append((a, b, cost)); used.add((a, b))
        if (b, a) not in used and (a != b):
            edges.append((b, a, cost + 2)); used.add((b, a))

    # 50 unidireccionales extra (sin reverso)
    # Generación determinista controlando que no exista (v,u).
    seed = 20260123
    while len(edges) < 80:
        seed = _lcg(seed)
        i = (seed % 40)
        seed = _lcg(seed)
        j = (seed % 40)
        if i == j:
            continue
        u = nodes[i]
        v = nodes[j]
        if (u, v) in used:
            continue
        # asegurar unidireccionalidad (no permitir reverso)
        if (v, u) in used:
            continue
        # coste determinista positivo
        seed = _lcg(seed)
        cost = 5 + (seed % 90)  # 5..94
        edges.append((u, v, float(cost)))
        used.add((u, v))

    return edges

def ensure_default_csv(csv_path):
    if os.path.exists(csv_path):
        return
    edges = generate_default_edges_40_80()
    with open(csv_path, "w", encoding="utf-8", newline="") as f:
        w = csv.writer(f, delimiter=",")
        w.writerow(["origen", "destino", "coste"])
        for o, d, c in edges:
            w.writerow([o, d, c])
    print(f"\nSe ha creado automáticamente el CSV: {csv_path}")
    print("Contiene 40 nodos y 80 aristas dirigidas (>=40 unidireccionales).\n")


# ---------------------------------------------------------------------
# DAO (SQL SOLO aquí)
# ---------------------------------------------------------------------

def dao_connect(db_path):
    conn = sqlite3.connect(db_path)
    conn.execute("PRAGMA foreign_keys = ON")
    return conn

def dao_init_schema(conn):
    conn.executescript("""
    CREATE TABLE IF NOT EXISTS nodos (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nombre TEXT NOT NULL UNIQUE
    );

    CREATE TABLE IF NOT EXISTS aristas (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        origen_id INTEGER NOT NULL,
        destino_id INTEGER NOT NULL,
        FOREIGN KEY (origen_id) REFERENCES nodos(id),
        FOREIGN KEY (destino_id) REFERENCES nodos(id),
        UNIQUE (origen_id, destino_id)
    );

    CREATE TABLE IF NOT EXISTS pesos (
        arista_id INTEGER PRIMARY KEY,
        coste REAL NOT NULL CHECK (coste >= 0),
        FOREIGN KEY (arista_id) REFERENCES aristas(id)
    );

    CREATE TABLE IF NOT EXISTS historico_rutas (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        origen TEXT NOT NULL,
        destino TEXT NOT NULL,
        intermedios TEXT NOT NULL,
        fecha_hora TEXT NOT NULL,
        coste_total REAL NOT NULL,
        ruta TEXT NOT NULL,
        uso_alternativa INTEGER NOT NULL CHECK (uso_alternativa IN (0,1))
    );

    CREATE INDEX IF NOT EXISTS idx_hist_odv
        ON historico_rutas(origen, destino, intermedios);
    """)
    conn.commit()

def dao_is_graph_loaded(conn):
    cur = conn.cursor()
    cur.execute("SELECT COUNT(*) FROM aristas")
    return cur.fetchone()[0] > 0

def dao_get_or_create_node_id(conn, name):
    cur = conn.cursor()
    cur.execute("SELECT id FROM nodos WHERE nombre=?", (name,))
    row = cur.fetchone()
    if row:
        return row[0]
    cur.execute("INSERT INTO nodos(nombre) VALUES (?)", (name,))
    conn.commit()
    return cur.lastrowid

def dao_insert_edge_with_weight(conn, origen, destino, coste):
    oid = dao_get_or_create_node_id(conn, origen)
    did = dao_get_or_create_node_id(conn, destino)

    cur = conn.cursor()
    cur.execute("SELECT id FROM aristas WHERE origen_id=? AND destino_id=?", (oid, did))
    row = cur.fetchone()
    if row:
        arista_id = row[0]
        cur.execute("INSERT OR REPLACE INTO pesos(arista_id, coste) VALUES (?,?)", (arista_id, float(coste)))
    else:
        cur.execute("INSERT INTO aristas(origen_id, destino_id) VALUES (?,?)", (oid, did))
        arista_id = cur.lastrowid
        cur.execute("INSERT INTO pesos(arista_id, coste) VALUES (?,?)", (arista_id, float(coste)))
    conn.commit()

def dao_load_csv_to_db(conn, csv_path):
    with open(csv_path, "r", encoding="utf-8-sig", newline="") as f:
        sample = f.read(2048); f.seek(0)
        delim = ";" if sample.count(";") > sample.count(",") else ","
        r = csv.reader(f, delimiter=delim)
        first = next(r, None)
        if first is None:
            raise ValueError("CSV vacío.")

        def is_header(row):
            s = ",".join([c.strip().lower() for c in row])
            return ("origen" in s) or ("destino" in s) or ("coste" in s) or ("peso" in s)

        if not is_header(first):
            _insert_csv_row(conn, first)

        for row in r:
            if not row or all(not c.strip() for c in row):
                continue
            _insert_csv_row(conn, row)

def _insert_csv_row(conn, row):
    if len(row) < 3:
        raise ValueError(f"Fila CSV inválida (3 columnas): {row}")
    o = row[0].strip()
    d = row[1].strip()
    c = float(row[2].strip().replace(",", "."))
    if not o or not d:
        raise ValueError(f"Origen/destino vacío: {row}")
    if c < 0:
        raise ValueError(f"Coste negativo: {row}")
    dao_insert_edge_with_weight(conn, o, d, c)

def dao_build_graph_from_db(conn):
    cur = conn.cursor()
    cur.execute("""
        SELECT n1.nombre, n2.nombre, p.coste
        FROM aristas a
        JOIN nodos n1 ON a.origen_id = n1.id
        JOIN nodos n2 ON a.destino_id = n2.id
        JOIN pesos p ON p.arista_id = a.id
    """)
    rows = cur.fetchall()

    nodes = set()
    adj = {}
    for o, d, c in rows:
        nodes.add(o); nodes.add(d)
        adj.setdefault(o, []).append((d, float(c)))
    for n in nodes:
        adj.setdefault(n, [])
    return nodes, adj

def dao_get_latest_history(conn, origen, destino, intermedios_str):
    cur = conn.cursor()
    cur.execute("""
        SELECT fecha_hora, coste_total, ruta, uso_alternativa
        FROM historico_rutas
        WHERE origen=? AND destino=? AND intermedios=?
        ORDER BY id DESC
        LIMIT 1
    """, (origen, destino, intermedios_str))
    row = cur.fetchone()
    if not row:
        return None
    return {
        "fecha_hora": row[0],
        "coste_total": float(row[1]),
        "ruta": row[2],
        "uso_alternativa": int(row[3]),
    }

def dao_insert_history(conn, origen, destino, intermedios_str, coste_total, ruta_list, uso_alternativa):
    ruta_str = " > ".join(ruta_list)
    conn.execute("""
        INSERT INTO historico_rutas(origen, destino, intermedios, fecha_hora, coste_total, ruta, uso_alternativa)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """, (
        origen, destino, intermedios_str,
        datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
        float(coste_total),
        ruta_str,
        1 if uso_alternativa else 0
    ))
    conn.commit()

def dao_get_history_last_n(conn, n=10):
    cur = conn.cursor()
    cur.execute("""
        SELECT fecha_hora, origen, destino, intermedios, coste_total, ruta, uso_alternativa
        FROM historico_rutas
        ORDER BY id DESC
        LIMIT ?
    """, (int(n),))
    out = []
    for row in cur.fetchall():
        out.append({
            "fecha_hora": row[0],
            "origen": row[1],
            "destino": row[2],
            "intermedios": row[3],
            "coste_total": float(row[4]),
            "ruta": row[5],
            "uso_alternativa": int(row[6]),
        })
    return out


# ---------------------------------------------------------------------
# Dijkstra (SIN SQL, SIN prints, SIN lectura de ficheros)
# ---------------------------------------------------------------------

def dijkstra(adj, origen, destino, banned_edges=None):
    if banned_edges is None:
        banned_edges = set()

    if origen == destino:
        return 0.0, [origen]

    nodes = list(adj.keys())
    dist = {n: float("inf") for n in nodes}
    prev = {n: None for n in nodes}
    visited = set()

    dist[origen] = 0.0

    while True:
        u = None
        best = float("inf")
        for n in nodes:
            if n in visited:
                continue
            if dist[n] < best:
                best = dist[n]
                u = n

        if u is None or best == float("inf"):
            break
        if u == destino:
            break

        visited.add(u)

        for v, w in adj.get(u, []):
            if (u, v) in banned_edges:
                continue
            alt = dist[u] + w
            if alt < dist[v]:
                dist[v] = alt
                prev[v] = u

    if dist[destino] == float("inf"):
        return None, None

    path = []
    cur = destino
    while cur is not None:
        path.append(cur)
        cur = prev[cur]
    path.reverse()

    if not path or path[0] != origen:
        return None, None

    return float(dist[destino]), path

def compute_route_with_intermediates(adj, origen, destino, intermedios):
    full_path = []
    total_cost = 0.0
    points = [origen] + intermedios + [destino]

    for i in range(len(points) - 1):
        a = points[i]
        b = points[i + 1]
        dist, path = dijkstra(adj, a, b)
        if dist is None:
            return None, None
        total_cost += dist
        if i == 0:
            full_path.extend(path)
        else:
            full_path.extend(path[1:])
    return total_cost, full_path

def compute_alternative_route(adj, origen, destino, best_path, best_cost, alt_percent=ALT_PERCENT_DEFAULT):
    if not best_path or len(best_path) < 2:
        return None, None

    candidates = []
    for i in range(len(best_path) - 1):
        u = best_path[i]
        v = best_path[i + 1]
        dist, path = dijkstra(adj, origen, destino, banned_edges={(u, v)})
        if dist is None:
            continue
        if path == best_path:
            continue
        candidates.append((dist, path))

    if not candidates:
        return None, None

    candidates.sort(key=lambda x: x[0])
    alt_cost, alt_path = candidates[0]

    if alt_cost <= best_cost * (1.0 + float(alt_percent)):
        return alt_cost, alt_path
    return None, None


# ---------------------------------------------------------------------
# Interacción (CLI)
# ---------------------------------------------------------------------

def prompt(msg):
    return input(msg).strip()

def normalize_intermediates(raw):
    raw = raw.strip()
    if not raw:
        return []
    return [p.strip() for p in raw.split(",") if p.strip()]

def format_intermediates_str(intermedios):
    return ",".join(intermedios) if intermedios else ""

def print_nodes(nodes):
    print("\nNODOS DISPONIBLES:")
    for n in sorted(nodes, key=lambda s: s.lower()):
        print(f" - {n}")
    print("")

def menu():
    print("\n--- GPS DAM (CLI) ---")
    print("1) Calcular ruta")
    print("2) Ver histórico (últimas 10)")
    print("3) Salir")
    return prompt("Opción: ")

def show_history(conn):
    items = dao_get_history_last_n(conn, 10)
    if not items:
        print("\nHistórico vacío.\n")
        return
    print("\nÚLTIMAS RUTAS:")
    for it in items:
        alt_txt = "Sí" if it["uso_alternativa"] == 1 else "No"
        via = it["intermedios"] if it["intermedios"] else "-"
        print(f"- [{it['fecha_hora']}] {it['origen']} -> {it['destino']} | Intermedios: {via}")
        print(f"  Coste: {it['coste_total']:.3f} | Alternativa elegida: {alt_txt}")
        print(f"  Ruta: {it['ruta']}")
    print("")

def calculate_route(conn, nodes, adj):
    print_nodes(nodes)

    origen = prompt("Origen: ")
    if origen not in nodes:
        print("Error: origen no existe.\n")
        return

    destino = prompt("Destino: ")
    if destino not in nodes:
        print("Error: destino no existe.\n")
        return

    intermedios = normalize_intermediates(prompt("Intermedios (coma) [ENTER ninguno]: "))
    for x in intermedios:
        if x not in nodes:
            print(f"Error: intermedio '{x}' no existe.\n")
            return

    inter_str = format_intermediates_str(intermedios)

    hist = dao_get_latest_history(conn, origen, destino, inter_str)
    if hist:
        print("\nRuta en histórico:")
        print(f"Fecha/hora: {hist['fecha_hora']}")
        print(f"Coste: {hist['coste_total']:.3f}")
        print(f"Ruta: {hist['ruta']}")
        if prompt("¿Usar sin recalcular? (s/n): ").lower() == "s":
            print("\nUsada del histórico.\n")
            return

    if intermedios:
        cost, path = compute_route_with_intermediates(adj, origen, destino, intermedios)
        if cost is None:
            print("\nNo existe ruta pasando por esos intermedios.\n")
            return
        print("\nRUTA ÓPTIMA (con intermedios):")
        print(f"Coste total: {cost:.3f}")
        print("Camino:", " > ".join(path))
        dao_insert_history(conn, origen, destino, inter_str, cost, path, uso_alternativa=False)
        print("\nGuardada en histórico.\n")
        return

    best_cost, best_path = dijkstra(adj, origen, destino)
    if best_cost is None:
        print("\nNo existe ruta.\n")
        return

    print("\nRUTA ÓPTIMA:")
    print(f"Coste total: {best_cost:.3f}")
    print("Camino:", " > ".join(best_path))

    alt_cost, alt_path = compute_alternative_route(adj, origen, destino, best_path, best_cost, ALT_PERCENT_DEFAULT)

    used_alt = False
    final_cost = best_cost
    final_path = best_path

    if alt_cost is not None:
        print("\nRUTA ALTERNATIVA (<= 15% más costosa):")
        print(f"Coste total: {alt_cost:.3f}")
        print("Camino:", " > ".join(alt_path))
        if prompt("¿Elegir alternativa? (s/n): ").lower() == "s":
            used_alt = True
            final_cost = alt_cost
            final_path = alt_path
            print("\nAlternativa seleccionada.\n")

    dao_insert_history(conn, origen, destino, inter_str, final_cost, final_path, uso_alternativa=used_alt)
    print("Guardada en histórico.\n")

def ensure_db_has_graph(conn):
    if dao_is_graph_loaded(conn):
        return
    ensure_default_csv(CSV_PATH)
    dao_load_csv_to_db(conn, CSV_PATH)
    print("Grafo cargado en SQLite desde el CSV generado por la app.\n")

def main():
    ensure_default_csv(CSV_PATH)

    conn = dao_connect(DB_PATH)
    try:
        dao_init_schema(conn)
        ensure_db_has_graph(conn)

        nodes, adj = dao_build_graph_from_db(conn)

        while True:
            op = menu()
            if op == "1":
                calculate_route(conn, nodes, adj)
                nodes, adj = dao_build_graph_from_db(conn)
            elif op == "2":
                show_history(conn)
            elif op == "3":
                print("\nSaliendo.\n")
                break
            else:
                print("Opción inválida.\n")
    finally:
        conn.close()

if __name__ == "__main__":
    main()

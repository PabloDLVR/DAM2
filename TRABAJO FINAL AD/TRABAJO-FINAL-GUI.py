
import os
import csv
import sqlite3
from datetime import datetime
import tkinter as tk
from tkinter import ttk, messagebox

DB_PATH = "gps.sqlite"
CSV_PATH = "mapa.csv"
ALT_PERCENT_DEFAULT = 0.15

# ---------------------------------------------------------------------
# CSV propio (40 nodos, 80 aristas, >=40 unidireccionales)
# ---------------------------------------------------------------------

def _lcg(seed):
    return (1103515245 * seed + 12345) % (2**31)

def generate_default_edges_40_80():
    """
    40 nodos:
    - 17 CCAA + Ceuta + Melilla (19)
    - 21 países famosos (21)
    Total = 40
    80 aristas dirigidas:
    - 15 pares bidireccionales (30 aristas)
    - 50 unidireccionales (sin reverso)
    """

    nodes = [
        # España: 17 CCAA + Ceuta + Melilla (19)
        "Andalucía", "Aragón", "Asturias", "Illes Balears", "Canarias", "Cantabria",
        "Castilla-La Mancha", "Castilla y León", "Cataluña", "Comunidad Valenciana",
        "Extremadura", "Galicia", "Comunidad de Madrid", "Región de Murcia",
        "Comunidad Foral de Navarra", "País Vasco", "La Rioja", "Ceuta", "Melilla",

        # Países famosos (21) -> total 40
        "Estados Unidos", "Canadá", "México", "Brasil", "Argentina", "Chile", "Colombia",
        "Reino Unido", "Irlanda", "Francia", "Alemania", "Italia", "Portugal",
        "Países Bajos", "Bélgica", "Suiza", "Suecia", "Noruega", "Japón", "China", "Australia",
    ]

    edges = []
    used = set()  # (u,v)

    # 15 pares bidireccionales (30 aristas)
    # Elegimos nodos 0..14 con 15..29 para no solapar.
    for i in range(15):
        a = nodes[i]         # 0..14
        b = nodes[i + 15]    # 15..29
        cost = 20 + (i % 7) * 6  # coste positivo determinista

        if (a, b) not in used and a != b:
            edges.append((a, b, float(cost)))
            used.add((a, b))

        if (b, a) not in used and a != b:
            edges.append((b, a, float(cost + 3)))
            used.add((b, a))

    # 50 unidireccionales (sin reverso)
    seed = 20260123
    n = len(nodes)  # 40
    while len(edges) < 80:
        seed = _lcg(seed); i = seed % n
        seed = _lcg(seed); j = seed % n
        if i == j:
            continue

        u = nodes[i]
        v = nodes[j]

        if (u, v) in used:
            continue
        # evitar reverso para mantener unidireccionalidad
        if (v, u) in used:
            continue

        seed = _lcg(seed)
        cost = 10 + (seed % 120)  # 10..129
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
    print("Nodos: 17 CCAA + Ceuta + Melilla + 21 países (40 total).")
    print("Aristas: 80 dirigidas.\n")

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
    return sorted(nodes, key=lambda s: s.lower()), adj

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


# ---------------------------------------------------------------------
# Dijkstra (SIN SQL, SIN GUI)
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

def normalize_intermediates(raw):
    raw = raw.strip()
    if not raw:
        return []
    return [p.strip() for p in raw.split(",") if p.strip()]

def format_intermediates_str(intermedios):
    return ",".join(intermedios) if intermedios else ""

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
# GUI (Tkinter) - sin clases propias
# ---------------------------------------------------------------------

def ensure_db_has_graph(conn):
    if dao_is_graph_loaded(conn):
        return
    ensure_default_csv(CSV_PATH)
    dao_load_csv_to_db(conn, CSV_PATH)

def gui_main():
    ensure_default_csv(CSV_PATH)

    conn = dao_connect(DB_PATH)
    dao_init_schema(conn)
    ensure_db_has_graph(conn)

    nodes, adj = dao_build_graph_from_db(conn)

    root = tk.Tk()
    root.title("GPS DAM (GUI)")

    frm = ttk.Frame(root, padding=12)
    frm.grid(row=0, column=0, sticky="nsew")

    root.columnconfigure(0, weight=1)
    root.rowconfigure(0, weight=1)
    frm.columnconfigure(1, weight=1)

    ttk.Label(frm, text="Origen:").grid(row=0, column=0, sticky="w", padx=(0, 8), pady=4)
    origen_var = tk.StringVar(value=nodes[0] if nodes else "")
    origen_cb = ttk.Combobox(frm, textvariable=origen_var, values=nodes, state="readonly")
    origen_cb.grid(row=0, column=1, sticky="ew", pady=4)

    ttk.Label(frm, text="Destino:").grid(row=1, column=0, sticky="w", padx=(0, 8), pady=4)
    destino_var = tk.StringVar(value=nodes[1] if len(nodes) > 1 else (nodes[0] if nodes else ""))
    destino_cb = ttk.Combobox(frm, textvariable=destino_var, values=nodes, state="readonly")
    destino_cb.grid(row=1, column=1, sticky="ew", pady=4)

    ttk.Label(frm, text="Intermedios (coma):").grid(row=2, column=0, sticky="w", padx=(0, 8), pady=4)
    inter_var = tk.StringVar(value="")
    inter_entry = ttk.Entry(frm, textvariable=inter_var)
    inter_entry.grid(row=2, column=1, sticky="ew", pady=4)

    out = tk.Text(frm, height=14, width=70)
    out.grid(row=4, column=0, columnspan=2, sticky="nsew", pady=(10, 0))
    frm.rowconfigure(4, weight=1)

    def write_out(text):
        out.delete("1.0", tk.END)
        out.insert(tk.END, text)

    def on_calculate():
        origen = origen_var.get().strip()
        destino = destino_var.get().strip()
        intermedios = normalize_intermediates(inter_var.get())

        if origen not in nodes:
            messagebox.showerror("Error", "El nodo de origen no existe.")
            return
        if destino not in nodes:
            messagebox.showerror("Error", "El nodo de destino no existe.")
            return
        for x in intermedios:
            if x not in nodes:
                messagebox.showerror("Error", f"El nodo intermedio '{x}' no existe.")
                return

        inter_str = format_intermediates_str(intermedios)

        # Histórico
        hist = dao_get_latest_history(conn, origen, destino, inter_str)
        if hist:
            use = messagebox.askyesno(
                "Histórico",
                f"Existe una ruta guardada.\n\nFecha: {hist['fecha_hora']}\nCoste: {hist['coste_total']:.3f}\n\n¿Usarla sin recalcular?"
            )
            if use:
                write_out(
                    "RUTA (HISTÓRICO)\n"
                    f"Origen: {origen}\nDestino: {destino}\nIntermedios: {inter_str if inter_str else '-'}\n"
                    f"Coste total: {hist['coste_total']:.3f}\n"
                    f"Ruta: {hist['ruta']}\n"
                )
                return

        if intermedios:
            cost, path = compute_route_with_intermediates(adj, origen, destino, intermedios)
            if cost is None:
                write_out("No existe una ruta posible con esos puntos.\n")
                return
            dao_insert_history(conn, origen, destino, inter_str, cost, path, uso_alternativa=False)
            write_out(
                "RUTA ÓPTIMA (CON INTERMEDIOS)\n"
                f"Origen: {origen}\nDestino: {destino}\nIntermedios: {inter_str}\n"
                f"Coste total: {cost:.3f}\n"
                f"Ruta: {' > '.join(path)}\n"
            )
            return

        best_cost, best_path = dijkstra(adj, origen, destino)
        if best_cost is None:
            write_out("No existe una ruta posible entre origen y destino.\n")
            return

        alt_cost, alt_path = compute_alternative_route(adj, origen, destino, best_path, best_cost, ALT_PERCENT_DEFAULT)

        chosen_alt = False
        final_cost = best_cost
        final_path = best_path

        text = (
            "RUTA ÓPTIMA\n"
            f"Coste total: {best_cost:.3f}\n"
            f"Ruta: {' > '.join(best_path)}\n"
        )

        if alt_cost is not None:
            choose = messagebox.askyesno(
                "Alternativa disponible",
                f"Hay una alternativa <= 15% más costosa.\n\n"
                f"Óptima: {best_cost:.3f}\nAlternativa: {alt_cost:.3f}\n\n"
                "¿Quieres elegir la alternativa?"
            )
            text += "\nRUTA ALTERNATIVA (<= 15%)\n"
            text += f"Coste total: {alt_cost:.3f}\n"
            text += f"Ruta: {' > '.join(alt_path)}\n"

            if choose:
                chosen_alt = True
                final_cost = alt_cost
                final_path = alt_path
                text += "\nSelección: ALTERNATIVA\n"
            else:
                text += "\nSelección: ÓPTIMA\n"

        # Nota: si no hay intermedios, intermedios_str debe ser "" para el histórico (como ya haces)
        dao_insert_history(conn, origen, destino, "", final_cost, final_path, uso_alternativa=chosen_alt)
        write_out(text)

    btn = ttk.Button(frm, text="Calcular ruta", command=on_calculate)
    btn.grid(row=3, column=0, columnspan=2, sticky="ew", pady=(8, 0))

    def on_close():
        try:
            conn.close()
        finally:
            root.destroy()

    root.protocol("WM_DELETE_WINDOW", on_close)
    root.mainloop()

def main():
    gui_main()

if __name__ == "__main__":
    main()

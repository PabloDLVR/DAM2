#!/usr/bin/env python3
# -*- coding: utf-8 -*-

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
# Coordenadas (lat, lon) aproximadas para dibujar un "mapa" esquemático
# (capital autonómica o ciudad representativa / capital del país)
# ---------------------------------------------------------------------

def get_node_coords_latlon():
    # (latitud, longitud). Aproximado.
    return {
        # CCAA / Ciudades representativas
        "Andalucía": (37.389, -5.984),            # Sevilla
        "Aragón": (41.649, -0.889),               # Zaragoza
        "Asturias": (43.361, -5.850),             # Oviedo
        "Illes Balears": (39.569, 2.650),         # Palma
        "Canarias": (28.463, -16.251),            # Santa Cruz de Tenerife
        "Cantabria": (43.462, -3.810),            # Santander
        "Castilla-La Mancha": (39.857, -4.024),   # Toledo
        "Castilla y León": (41.652, -4.724),      # Valladolid
        "Cataluña": (41.387, 2.170),              # Barcelona
        "Comunidad Valenciana": (39.470, -0.376), # Valencia
        "Extremadura": (38.879, -6.970),          # Mérida
        "Galicia": (42.878, -8.545),              # Santiago
        "Comunidad de Madrid": (40.416, -3.703),  # Madrid
        "Región de Murcia": (37.992, -1.130),     # Murcia
        "Comunidad Foral de Navarra": (42.817, -1.644), # Pamplona
        "País Vasco": (43.263, -2.935),           # Bilbao
        "La Rioja": (42.466, -2.445),             # Logroño
        "Ceuta": (35.889, -5.319),
        "Melilla": (35.292, -2.938),

        # Países
        "Estados Unidos": (38.907, -77.037),      # Washington DC
        "Canadá": (45.421, -75.697),              # Ottawa
        "México": (19.432, -99.133),              # CDMX
        "Brasil": (-15.793, -47.882),             # Brasilia
        "Argentina": (-34.604, -58.382),          # Buenos Aires
        "Chile": (-33.448, -70.669),              # Santiago
        "Colombia": (4.711, -74.072),             # Bogotá
        "Reino Unido": (51.507, -0.128),          # London
        "Irlanda": (53.349, -6.260),              # Dublin
        "Francia": (48.857, 2.352),               # Paris
        "Alemania": (52.520, 13.405),             # Berlin
        "Italia": (41.903, 12.496),               # Rome
        "Portugal": (38.722, -9.139),             # Lisbon
        "Países Bajos": (52.367, 4.904),          # Amsterdam
        "Bélgica": (50.847, 4.357),               # Brussels
        "Suiza": (46.948, 7.447),                 # Bern
        "Suecia": (59.329, 18.068),               # Stockholm
        "Noruega": (59.913, 10.752),              # Oslo
        "Japón": (35.676, 139.650),               # Tokyo
        "China": (39.904, 116.407),               # Beijing
        "Australia": (-35.280, 149.130),          # Canberra
    }

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
# Dijkstra (SIN SQL)
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
# GUI (Tkinter) - “mapa” en Canvas con zoom/pan y ruta resaltada
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
    coords = get_node_coords_latlon()

    # Validación: si por lo que sea faltan coords, se les asigna una posición "en rejilla"
    missing = [n for n in nodes if n not in coords]
    if missing:
        # rejilla simple a la derecha del mapa
        base_lat, base_lon = 0.0, 0.0
        step = 5.0
        for idx, n in enumerate(missing):
            coords[n] = (base_lat - (idx // 5) * step, base_lon + (idx % 5) * step)

    root = tk.Tk()
    root.title("GPS DAM (GUI + Mapa)")

    root.rowconfigure(0, weight=1)
    root.columnconfigure(0, weight=1)
    root.columnconfigure(1, weight=0)

    # Panel mapa
    map_frame = ttk.Frame(root, padding=8)
    map_frame.grid(row=0, column=0, sticky="nsew")
    map_frame.rowconfigure(0, weight=1)
    map_frame.columnconfigure(0, weight=1)

    canvas = tk.Canvas(map_frame, background="white", highlightthickness=1)
    canvas.grid(row=0, column=0, sticky="nsew")

    # Panel controles
    side = ttk.Frame(root, padding=12)
    side.grid(row=0, column=1, sticky="nsew")

    ttk.Label(side, text="Origen:").grid(row=0, column=0, sticky="w", pady=(0, 4))
    origen_var = tk.StringVar(value=nodes[0] if nodes else "")
    origen_cb = ttk.Combobox(side, textvariable=origen_var, values=nodes, state="readonly", width=28)
    origen_cb.grid(row=1, column=0, sticky="ew", pady=(0, 10))

    ttk.Label(side, text="Destino:").grid(row=2, column=0, sticky="w", pady=(0, 4))
    destino_var = tk.StringVar(value=nodes[1] if len(nodes) > 1 else (nodes[0] if nodes else ""))
    destino_cb = ttk.Combobox(side, textvariable=destino_var, values=nodes, state="readonly", width=28)
    destino_cb.grid(row=3, column=0, sticky="ew", pady=(0, 10))

    ttk.Label(side, text="Intermedios (coma):").grid(row=4, column=0, sticky="w", pady=(0, 4))
    inter_var = tk.StringVar(value="")
    inter_entry = ttk.Entry(side, textvariable=inter_var)
    inter_entry.grid(row=5, column=0, sticky="ew", pady=(0, 10))

    # Mostrar u ocultar aristas "de fondo"
    show_edges_var = tk.BooleanVar(value=False)
    show_edges_chk = ttk.Checkbutton(side, text="Mostrar aristas (fondo)", variable=show_edges_var)
    show_edges_chk.grid(row=6, column=0, sticky="w", pady=(0, 10))

    out = tk.Text(side, height=16, width=40)
    out.grid(row=8, column=0, sticky="nsew", pady=(10, 0))
    side.rowconfigure(8, weight=1)

    # Estado de vista (zoom/pan)
    view = {
        "scale": 1.0,
        "offset_x": 0.0,
        "offset_y": 0.0,
        "dragging": False,
        "drag_last": (0, 0),
        "bbox": None,  # (min_lon, min_lat, max_lon, max_lat)
    }

    def write_out(text):
        out.delete("1.0", tk.END)
        out.insert(tk.END, text)

    def compute_bbox():
        lats = [coords[n][0] for n in nodes]
        lons = [coords[n][1] for n in nodes]
        min_lat, max_lat = min(lats), max(lats)
        min_lon, max_lon = min(lons), max(lons)
        # margen
        pad_lat = (max_lat - min_lat) * 0.08 + 1e-6
        pad_lon = (max_lon - min_lon) * 0.08 + 1e-6
        return (min_lon - pad_lon, min_lat - pad_lat, max_lon + pad_lon, max_lat + pad_lat)

    view["bbox"] = compute_bbox()

    def latlon_to_xy(lat, lon):
        # Proyección equirectangular simple al canvas, con zoom/pan
        min_lon, min_lat, max_lon, max_lat = view["bbox"]
        w = max(canvas.winfo_width(), 1)
        h = max(canvas.winfo_height(), 1)

        x = (lon - min_lon) / (max_lon - min_lon) * w
        y = (max_lat - lat) / (max_lat - min_lat) * h  # invertimos Y

        # aplicar zoom desde centro
        cx, cy = w / 2, h / 2
        x = cx + (x - cx) * view["scale"] + view["offset_x"]
        y = cy + (y - cy) * view["scale"] + view["offset_y"]
        return x, y

    def draw_base(show_edges=False):
        canvas.delete("all")

        # Fondo: rejilla suave (da sensación de mapa)
        w = max(canvas.winfo_width(), 1)
        h = max(canvas.winfo_height(), 1)
        step = 80
        for x in range(0, w, step):
            canvas.create_line(x, 0, x, h, fill="#f0f0f0")
        for y in range(0, h, step):
            canvas.create_line(0, y, w, y, fill="#f0f0f0")

        # Aristas de fondo (opcional, tenues)
        if show_edges:
            for u in adj:
                lat_u, lon_u = coords[u]
                x1, y1 = latlon_to_xy(lat_u, lon_u)
                for v, _c in adj[u]:
                    lat_v, lon_v = coords[v]
                    x2, y2 = latlon_to_xy(lat_v, lon_v)
                    canvas.create_line(x1, y1, x2, y2, fill="#e6e6e6", width=1)

        # Nodos
        for n in nodes:
            lat, lon = coords[n]
            x, y = latlon_to_xy(lat, lon)
            r = 4
            canvas.create_oval(x-r, y-r, x+r, y+r, fill="#222222", outline="")
            # Etiqueta corta (para no saturar)
            label = n
            if len(label) > 14:
                label = label[:12] + "…"
            canvas.create_text(x+8, y-8, text=label, anchor="w", fill="#555555", font=("Segoe UI", 9))

        # Leyenda
        canvas.create_rectangle(10, 10, 220, 70, fill="white", outline="#dddddd")
        canvas.create_text(20, 22, anchor="w", text="Leyenda", fill="#333333", font=("Segoe UI", 9, "bold"))
        canvas.create_line(20, 40, 90, 40, fill="#000000", width=3)
        canvas.create_text(100, 40, anchor="w", text="Ruta óptima", fill="#333333", font=("Segoe UI", 9))
        canvas.create_line(20, 56, 90, 56, fill="#444444", width=2, dash=(6, 4))
        canvas.create_text(100, 56, anchor="w", text="Alternativa (<=15%)", fill="#333333", font=("Segoe UI", 9))

    def draw_route(path, dashed=False):
        if not path or len(path) < 2:
            return
        for i in range(len(path) - 1):
            u = path[i]
            v = path[i + 1]
            lat_u, lon_u = coords[u]
            lat_v, lon_v = coords[v]
            x1, y1 = latlon_to_xy(lat_u, lon_u)
            x2, y2 = latlon_to_xy(lat_v, lon_v)
            if dashed:
                canvas.create_line(x1, y1, x2, y2, fill="#444444", width=2, dash=(6, 4))
            else:
                canvas.create_line(x1, y1, x2, y2, fill="#000000", width=3)
            # Marcar nodos de la ruta un poco mayores
            r = 6
            canvas.create_oval(x1-r, y1-r, x1+r, y1+r, fill="#000000", outline="")
            canvas.create_oval(x2-r, y2-r, x2+r, y2+r, fill="#000000", outline="")

    def redraw(show_edges=None):
        if show_edges is None:
            show_edges = show_edges_var.get()
        draw_base(show_edges=show_edges)

    # Zoom con rueda
    def on_mousewheel(event):
        # Windows: event.delta, Linux: Button-4/5 (no siempre)
        factor = 1.0
        if hasattr(event, "delta") and event.delta:
            factor = 1.1 if event.delta > 0 else 0.9
        else:
            return
        view["scale"] *= factor
        # limitar zoom
        if view["scale"] < 0.6:
            view["scale"] = 0.6
        if view["scale"] > 4.0:
            view["scale"] = 4.0
        redraw()

    # Pan arrastrando
    def on_button_press(event):
        view["dragging"] = True
        view["drag_last"] = (event.x, event.y)

    def on_button_release(_event):
        view["dragging"] = False

    def on_motion(event):
        if not view["dragging"]:
            return
        x0, y0 = view["drag_last"]
        dx = event.x - x0
        dy = event.y - y0
        view["offset_x"] += dx
        view["offset_y"] += dy
        view["drag_last"] = (event.x, event.y)
        redraw()

    # Ajuste a ventana
    def fit_to_view():
        view["scale"] = 1.0
        view["offset_x"] = 0.0
        view["offset_y"] = 0.0
        view["bbox"] = compute_bbox()
        redraw()

    # Botones
    def on_toggle_edges():
        redraw(show_edges=show_edges_var.get())

    def on_reset_view():
        fit_to_view()

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
                redraw()
                # reconstruir lista a partir del string "A > B > C"
                ruta_list = [p.strip() for p in hist["ruta"].split(">")]
                ruta_list = [p for p in ruta_list if p]
                draw_route(ruta_list, dashed=False)
                write_out(
                    "RUTA (HISTÓRICO)\n"
                    f"Origen: {origen}\nDestino: {destino}\nIntermedios: {inter_str if inter_str else '-'}\n"
                    f"Coste total: {hist['coste_total']:.3f}\n"
                    f"Ruta: {hist['ruta']}\n"
                )
                return

        # Calcular
        if intermedios:
            cost, path = compute_route_with_intermediates(adj, origen, destino, intermedios)
            if cost is None:
                redraw()
                write_out("No existe una ruta posible con esos puntos.\n")
                return
            dao_insert_history(conn, origen, destino, inter_str, cost, path, uso_alternativa=False)
            redraw()
            draw_route(path, dashed=False)
            write_out(
                "RUTA ÓPTIMA (CON INTERMEDIOS)\n"
                f"Origen: {origen}\nDestino: {destino}\nIntermedios: {inter_str}\n"
                f"Coste total: {cost:.3f}\n"
                f"Ruta: {' > '.join(path)}\n"
            )
            return

        best_cost, best_path = dijkstra(adj, origen, destino)
        if best_cost is None:
            redraw()
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

        redraw()

        # Pintar óptima
        draw_route(best_path, dashed=False)

        # Alternativa, si existe, pintarla discontinua y preguntar
        if alt_cost is not None:
            draw_route(alt_path, dashed=True)
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
                # Resaltar visualmente la alternativa: repintamos base y sólo alternativa como “principal”
                redraw()
                draw_route(alt_path, dashed=False)
            else:
                text += "\nSelección: ÓPTIMA\n"
                # Ya está pintada la óptima como principal

        dao_insert_history(conn, origen, destino, "", final_cost, final_path, uso_alternativa=chosen_alt)
        write_out(text)

    ttk.Separator(side).grid(row=7, column=0, sticky="ew", pady=(0, 10))

    btn_calc = ttk.Button(side, text="Calcular ruta", command=on_calculate)
    btn_calc.grid(row=9, column=0, sticky="ew", pady=(10, 6))

    btn_fit = ttk.Button(side, text="Recentrar / Reset vista", command=on_reset_view)
    btn_fit.grid(row=10, column=0, sticky="ew", pady=(0, 6))

    btn_edges = ttk.Button(side, text="Aplicar 'Mostrar aristas'", command=on_toggle_edges)
    btn_edges.grid(row=11, column=0, sticky="ew")

    # Eventos del canvas
    canvas.bind("<ButtonPress-1>", on_button_press)
    canvas.bind("<ButtonRelease-1>", on_button_release)
    canvas.bind("<B1-Motion>", on_motion)
    canvas.bind("<MouseWheel>", on_mousewheel)   # Windows/macOS Tk
    # En algunos Linux: rueda como botones
    canvas.bind("<Button-4>", lambda e: (setattr(view, "scale", view["scale"]), None))
    canvas.bind("<Button-5>", lambda e: (setattr(view, "scale", view["scale"]), None))

    # Redibujar al cambiar tamaño
    def on_resize(_event):
        redraw()
    canvas.bind("<Configure>", on_resize)

    def on_close():
        try:
            conn.close()
        finally:
            root.destroy()

    root.protocol("WM_DELETE_WINDOW", on_close)

    # Primera pintura
    fit_to_view()
    root.mainloop()

def main():
    gui_main()

if __name__ == "__main__":
    main()

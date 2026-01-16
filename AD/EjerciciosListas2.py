# ==============================
# LISTAS CON SIMULACIÓN DE PUNTEROS
# ==============================

# ---------- CREAR NODO ----------
def crear_nodo(dato):
    if dato < 1 or dato > 98:
        return None
    return {
        "dato": dato,
        "sig": None,
        "ant": None
    }


# ---------- INSERTAR AL INICIO ----------
def insertar_inicio(lista, nodo, tipo):
    if lista is None:
        if "circular" in tipo:
            nodo["sig"] = nodo
            if "d" in tipo:
                nodo["ant"] = nodo
        return nodo

    if "circular" in tipo:
        ultimo = lista["ant"] if "d" in tipo else buscar_ultimo(lista)
        nodo["sig"] = lista
        lista["ant"] = nodo if "d" in tipo else None
        if "d" in tipo:
            nodo["ant"] = ultimo
            ultimo["sig"] = nodo
            lista["ant"] = nodo
        return nodo
    else:
        nodo["sig"] = lista
        if "d" in tipo:
            lista["ant"] = nodo
        return nodo


# ---------- INSERTAR AL FINAL ----------
def insertar_final(lista, nodo, tipo):
    if lista is None:
        return None

    if "circular" in tipo:
        ultimo = lista["ant"] if "d" in tipo else buscar_ultimo(lista)
        ultimo["sig"] = nodo
        nodo["sig"] = lista
        if "d" in tipo:
            nodo["ant"] = ultimo
            lista["ant"] = nodo
    else:
        actual = lista
        while actual["sig"]:
            actual = actual["sig"]
        actual["sig"] = nodo
        if "d" in tipo:
            nodo["ant"] = actual
    return lista


# ---------- BUSCAR ÚLTIMO ----------
def buscar_ultimo(lista):
    actual = lista
    while actual["sig"] and actual["sig"] != lista:
        actual = actual["sig"]
    return actual


# ---------- CONTAR NODOS ----------
def contar_nodos(lista, tipo):
    if lista is None:
        return 0

    contador = 1
    actual = lista["sig"]

    while actual and actual != lista:
        contador += 1
        actual = actual["sig"]

    return contador


# ---------- BUSCAR NODO ----------
def buscar_nodo(lista, valor, tipo):
    if lista is None:
        return None

    actual = lista
    while True:
        if actual["dato"] == valor:
            return actual
        actual = actual["sig"]
        if actual is None or actual == lista:
            break
    return None


# ---------- ELIMINAR NODO ----------
def eliminar_nodo(lista, valor, tipo):
    if lista is None:
        return None

    actual = lista
    anterior = None

    while True:
        if actual["dato"] == valor:
            if anterior:
                anterior["sig"] = actual["sig"]
                if "d" in tipo and actual["sig"]:
                    actual["sig"]["ant"] = anterior
            else:
                lista = actual["sig"]

            if "circular" in tipo and lista:
                ultimo = lista["ant"] if "d" in tipo else buscar_ultimo(lista)
                ultimo["sig"] = lista
                if "d" in tipo:
                    lista["ant"] = ultimo
            return lista

        anterior = actual
        actual = actual["sig"]
        if actual is None or actual == lista:
            break
    return lista


# ---------- IMPRIMIR VALORES ----------
def imprimir_valor_lista(lista, tipo):
    if lista is None:
        print("Lista vacía")
        return

    actual = lista
    while True:
        print(actual["dato"], end=" -> ")
        actual = actual["sig"]
        if actual is None or actual == lista:
            break
    print("NULL")


# ---------- IMPRIMIR LISTA COMPLETA ----------
def imprimir_lista_completa(lista, tipo):
    if lista is None:
        print("Lista vacía")
        return

    actual = lista
    while True:
        print(actual)
        actual = actual["sig"]
        if actual is None or actual == lista:
            break


# ---------- IMPRIMIR AL REVÉS ----------
def imprimir_reves(lista, tipo):
    if lista is None or "d" not in tipo:
        print("No se puede imprimir al revés")
        return

    actual = lista["ant"]
    while True:
        print(actual["dato"], end=" -> ")
        actual = actual["ant"]
        if actual == lista["ant"]:
            break
    print("NULL")


# ---------- COPIAR LISTA A FICHERO ----------
def copiar_lista(lista, tipo):
    nombres = {
        "enlazada": "lista_enlazada.txt",
        "enlazada_d": "lista_enlazada_d.txt",
        "circular": "lista_circular.txt",
        "circular_d": "lista_circular_d.txt"
    }

    fichero = nombres[tipo]

    with open(fichero, "w") as f:
        if lista is None:
            f.write("Lista vacía")
            return

        actual = lista
        while True:
            f.write(str(actual) + "\n")
            actual = actual["sig"]
            if actual is None or actual == lista:
                break


# ---------- EXTRA: ORDENAR LISTA ----------
def ordenar_lista(lista, tipo):
    if lista is None:
        return None

    valores = []
    actual = lista
    while True:
        valores.append(actual["dato"])
        actual = actual["sig"]
        if actual is None or actual == lista:
            break

    valores.sort()

    nueva = None
    for v in valores:
        nodo = crear_nodo(v)
        if nueva is None:
            nueva = insertar_inicio(None, nodo, tipo)
        else:
            insertar_final(nueva, nodo, tipo)

    return nueva

from typing import Dict, Optional, List, Tuple

NULL_INICIO = 0
NULL_FINAL = 99
MIN_VAL = 1
MAX_VAL = 98


def _es_valido_1_98(x: int) -> bool:
    return isinstance(x, int) and MIN_VAL <= x <= MAX_VAL


class ListaSimulada:
    """
    Lista con 'memoria' de nodos indexada por direcciones simuladas 1..98.
    Cada nodo es un diccionario con:
      - 'dato': int (1..98)
      - 'sig' : int (0/99/1..98) según tipo
      - 'ant' : int (solo para dobles)
    """

    def __init__(self, tipo: str):
        if tipo not in ("enlazada", "enlazada_d", "circular", "circular_d"):
            raise ValueError("Tipo de lista no válido.")
        self.tipo = tipo

        # Memoria simulada: address -> nodo(dict)
        self.memoria: Dict[int, Dict[str, int]] = {}

        # Direcciones libres (pool). Simula una memoria limitada 1..98
        self.libres: List[int] = list(range(MIN_VAL, MAX_VAL + 1))

        # Cabeza (inicio) de la lista. 0 si vacía.
        self.inicio: int = NULL_INICIO

    # -----------------------------
    # Función: crear_nodo
    # -----------------------------
    def crear_nodo(self, dato: int) -> Tuple[int, Dict[str, int]]:
        """
        Crea un nodo (diccionario) y lo almacena en una dirección libre.
        Devuelve (direccion, nodo).
        """
        if not _es_valido_1_98(dato):
            raise ValueError("El dato debe ser entero entre 1 y 98.")

        if not self.libres:
            raise RuntimeError("Memoria llena: no hay direcciones libres (1..98).")

        direccion = self.libres.pop(0)

        nodo = {"dato": dato, "sig": NULL_FINAL}
        if self.es_doble():
            nodo["ant"] = NULL_INICIO

        self.memoria[direccion] = nodo
        return direccion, nodo

    # -----------------------------
    # Utilidades de tipo
    # -----------------------------
    def es_doble(self) -> bool:
        return self.tipo in ("enlazada_d", "circular_d")

    def es_circular(self) -> bool:
        return self.tipo in ("circular", "circular_d")

    # -----------------------------
    # Función: contar_nodos
    # -----------------------------
    def contar_nodos(self) -> int:
        """Devuelve el número de nodos de la lista."""
        if self.inicio == NULL_INICIO:
            return 0

        count = 0
        actual = self.inicio
        while True:
            count += 1
            sig = self.memoria[actual]["sig"]

            if self.es_circular():
                if sig == self.inicio:
                    break
            else:
                if sig == NULL_FINAL:
                    break

            actual = sig

        return count

    # -----------------------------
    # Función: buscar_nodo
    # -----------------------------
    def buscar_nodo(self, dato: int) -> int:
        """
        Busca un nodo por su 'dato'.
        Devuelve la dirección si lo encuentra; 0 si no está.
        """
        if self.inicio == NULL_INICIO:
            return NULL_INICIO

        actual = self.inicio
        while True:
            if self.memoria[actual]["dato"] == dato:
                return actual

            sig = self.memoria[actual]["sig"]

            if self.es_circular():
                if sig == self.inicio:
                    break
            else:
                if sig == NULL_FINAL:
                    break

            actual = sig

        return NULL_INICIO

    # -----------------------------
    # Función: insertar_inicio
    # -----------------------------
    def insertar_inicio(self, dato: int) -> None:
        """
        Añade un nuevo nodo al inicio.
        Si la lista está vacía, la crea con el valor correspondiente.
        """
        dir_nuevo, _ = self.crear_nodo(dato)

        if self.inicio == NULL_INICIO:
            # Lista vacía
            self.inicio = dir_nuevo
            if self.es_circular():
                # El único nodo apunta a sí mismo
                self.memoria[dir_nuevo]["sig"] = dir_nuevo
                if self.es_doble():
                    self.memoria[dir_nuevo]["ant"] = dir_nuevo
            else:
                self.memoria[dir_nuevo]["sig"] = NULL_FINAL
                if self.es_doble():
                    self.memoria[dir_nuevo]["ant"] = NULL_INICIO
            return

        # Lista no vacía
        antiguo_inicio = self.inicio
        self.inicio = dir_nuevo

        # Enlace hacia delante
        self.memoria[dir_nuevo]["sig"] = antiguo_inicio

        if self.es_doble():
            # Enlace hacia atrás del antiguo inicio
            self.memoria[antiguo_inicio]["ant"] = dir_nuevo

        if self.es_circular():
            # Hay que actualizar el último nodo para que apunte al nuevo inicio
            ultimo = self._obtener_ultimo()
            self.memoria[ultimo]["sig"] = self.inicio
            if self.es_doble():
                # En circular doble: ajustar ant del nuevo inicio y ant del antiguo
                self.memoria[self.inicio]["ant"] = ultimo
                self.memoria[antiguo_inicio]["ant"] = self.inicio
        else:
            if self.es_doble():
                self.memoria[dir_nuevo]["ant"] = NULL_INICIO

    # -----------------------------
    # Función: insertar_final
    # -----------------------------
    def insertar_final(self, dato: int) -> None:
        """
        Añade un nodo al final.
        Si la lista está vacía, NO podrá insertar ningún nodo.
        """
        if self.inicio == NULL_INICIO:
            print("No se puede insertar al final: la lista está vacía.")
            return

        dir_nuevo, _ = self.crear_nodo(dato)
        ultimo = self._obtener_ultimo()

        # Enlazar el último al nuevo
        self.memoria[ultimo]["sig"] = dir_nuevo

        if self.es_circular():
            # Nuevo apunta al inicio
            self.memoria[dir_nuevo]["sig"] = self.inicio
        else:
            # Nuevo es final
            self.memoria[dir_nuevo]["sig"] = NULL_FINAL

        if self.es_doble():
            self.memoria[dir_nuevo]["ant"] = ultimo
            if self.es_circular():
                # En circular doble: el inicio debe tener ant = nuevo (nuevo es último)
                self.memoria[self.inicio]["ant"] = dir_nuevo

    # -----------------------------
    # Función: insertar_nodo
    # -----------------------------
    def insertar_nodo(self, dato_nuevo: int, dato_anterior: int) -> None:
        """
        Inserta un nodo nuevo ENTRE dos nodos.
        Se inserta DESPUÉS del nodo cuyo dato es dato_anterior.
        Si fuera nodo inicial o final, se inserta en su correspondiente posición.
        """
        if self.inicio == NULL_INICIO:
            # Si está vacía, lo más coherente es crear como inicio
            # (el enunciado solo especifica esto explícitamente para insertar_inicio,
            # pero no prohíbe esta decisión aquí).
            self.insertar_inicio(dato_nuevo)
            return

        dir_ant = self.buscar_nodo(dato_anterior)
        if dir_ant == NULL_INICIO:
            print("No se puede insertar: no existe el nodo 'anterior' indicado.")
            return

        # Si el 'anterior' es el último, equivale a insertar_final
        if self._es_ultimo(dir_ant):
            self.insertar_final(dato_nuevo)
            return

        # Inserción entre dir_ant y su siguiente
        dir_siguiente = self.memoria[dir_ant]["sig"]
        dir_nuevo, _ = self.crear_nodo(dato_nuevo)

        self.memoria[dir_ant]["sig"] = dir_nuevo
        self.memoria[dir_nuevo]["sig"] = dir_siguiente

        if self.es_doble():
            self.memoria[dir_nuevo]["ant"] = dir_ant
            self.memoria[dir_siguiente]["ant"] = dir_nuevo

    # -----------------------------
    # Función: eliminar_nodo
    # -----------------------------
    def eliminar_nodo(self, dato: int) -> None:
        """
        Elimina el nodo cuyo 'dato' coincide con el solicitado.
        """
        if self.inicio == NULL_INICIO:
            print("Lista vacía. No hay nada que eliminar.")
            return

        dir_obj = self.buscar_nodo(dato)
        if dir_obj == NULL_INICIO:
            print("El nodo no existe. No se elimina nada.")
            return

        # Caso 1: eliminar el único nodo
        if self.contar_nodos() == 1:
            self._liberar(dir_obj)
            self.inicio = NULL_INICIO
            return

        # Caso 2: eliminar el inicio
        if dir_obj == self.inicio:
            nuevo_inicio = self.memoria[dir_obj]["sig"]

            if self.es_circular():
                ultimo = self._obtener_ultimo()
                self.memoria[ultimo]["sig"] = nuevo_inicio
                if self.es_doble():
                    self.memoria[nuevo_inicio]["ant"] = ultimo
            else:
                if self.es_doble():
                    self.memoria[nuevo_inicio]["ant"] = NULL_INICIO

            self._liberar(dir_obj)
            self.inicio = nuevo_inicio
            return

        # Caso 3: eliminar un nodo intermedio o final
        if self.es_doble():
            ant = self.memoria[dir_obj]["ant"]
            sig = self.memoria[dir_obj]["sig"]

            self.memoria[ant]["sig"] = sig

            if self.es_circular():
                # Si estamos eliminando el último, 'sig' será inicio
                if sig == self.inicio:
                    # actualizar ant del inicio
                    self.memoria[self.inicio]["ant"] = ant
                else:
                    self.memoria[sig]["ant"] = ant
            else:
                if sig == NULL_FINAL:
                    # era el último: nada que actualizar hacia delante
                    pass
                else:
                    self.memoria[sig]["ant"] = ant

            self._liberar(dir_obj)
        else:
            # Simple: hay que encontrar el anterior recorriendo
            ant = self._buscar_anterior(dir_obj)
            sig = self.memoria[dir_obj]["sig"]
            self.memoria[ant]["sig"] = sig
            self._liberar(dir_obj)

    # -----------------------------
    # Función: imprimir_valor_lista
    # -----------------------------
    def imprimir_valor_lista(self) -> None:
        """Imprime el valor (dato) de cada nodo."""
        if self.inicio == NULL_INICIO:
            print("Lista vacía.")
            return

        valores = []
        actual = self.inicio
        while True:
            valores.append(str(self.memoria[actual]["dato"]))
            sig = self.memoria[actual]["sig"]

            if self.es_circular():
                if sig == self.inicio:
                    break
            else:
                if sig == NULL_FINAL:
                    break
            actual = sig

        print("Valores:", " -> ".join(valores))

    # -----------------------------
    # Función: imprimir_lista_completa
    # -----------------------------
    def imprimir_lista_completa(self) -> None:
        """Imprime todos los campos de cada nodo, mostrando la relación entre nodos."""
        if self.inicio == NULL_INICIO:
            print("Lista vacía.")
            return

        print(f"Tipo: {self.tipo}")
        print(f"Inicio (cabeza): {self.inicio}")
        print("-" * 60)

        actual = self.inicio
        while True:
            nodo = self.memoria[actual]
            if self.es_doble():
                print(f"[dir={actual}] dato={nodo['dato']} ant={nodo['ant']} sig={nodo['sig']}")
            else:
                print(f"[dir={actual}] dato={nodo['dato']} sig={nodo['sig']}")

            sig = nodo["sig"]
            if self.es_circular():
                if sig == self.inicio:
                    break
            else:
                if sig == NULL_FINAL:
                    break
            actual = sig

        print("-" * 60)

    # -----------------------------
    # Función: imprimir_reves
    # -----------------------------
    def imprimir_reves(self) -> None:
        """
        Imprime la lista desde el final al principio.
        En listas dobles, se usa 'ant'. En simples, se recorre buscando anteriores.
        """
        if self.inicio == NULL_INICIO:
            print("Lista vacía.")
            return

        if self.es_doble():
            ultimo = self._obtener_ultimo()
            valores = []
            actual = ultimo
            while True:
                valores.append(str(self.memoria[actual]["dato"]))
                ant = self.memoria[actual]["ant"]

                if self.es_circular():
                    if actual == self.inicio:
                        break
                    actual = ant
                else:
                    if ant == NULL_INICIO:
                        break
                    actual = ant

            print("Reverso:", " -> ".join(valores))
        else:
            # Simple: construir la lista de direcciones y luego imprimir al revés
            direcciones = self._listar_direcciones()
            valores = [str(self.memoria[d]["dato"]) for d in reversed(direcciones)]
            print("Reverso:", " -> ".join(valores))

    # -----------------------------
    # Función: copiar_lista
    # -----------------------------
    def copiar_lista(self) -> None:
        """
        Copia la lista (todos sus campos) a un fichero:
          lista_enlazada.txt
          lista_enlazada_d.txt
          lista_circular.txt
          lista_circular_d.txt
        """
        nombre = f"lista_{self.tipo}.txt"

        with open(nombre, "w", encoding="utf-8") as f:
            f.write(f"Tipo: {self.tipo}\n")
            f.write(f"Inicio: {self.inicio}\n")
            f.write("Formato: [dir] dato ant sig (si aplica)\n")
            f.write("-" * 60 + "\n")

            if self.inicio == NULL_INICIO:
                f.write("Lista vacía.\n")
            else:
                actual = self.inicio
                while True:
                    nodo = self.memoria[actual]
                    if self.es_doble():
                        f.write(f"[{actual}] dato={nodo['dato']} ant={nodo['ant']} sig={nodo['sig']}\n")
                    else:
                        f.write(f"[{actual}] dato={nodo['dato']} sig={nodo['sig']}\n")

                    sig = nodo["sig"]
                    if self.es_circular():
                        if sig == self.inicio:
                            break
                    else:
                        if sig == NULL_FINAL:
                            break
                    actual = sig

            f.write("-" * 60 + "\n")

        print(f"Lista copiada a: {nombre}")

    # =========================================================
    # Funciones internas auxiliares (no exigidas pero necesarias)
    # =========================================================

    def _liberar(self, direccion: int) -> None:
        """Elimina el nodo de memoria y devuelve su dirección al pool de libres."""
        if direccion in self.memoria:
            del self.memoria[direccion]
        if direccion not in self.libres:
            self.libres.append(direccion)
            self.libres.sort()

    def _obtener_ultimo(self) -> int:
        """Obtiene la dirección del último nodo."""
        if self.inicio == NULL_INICIO:
            return NULL_INICIO

        actual = self.inicio
        while True:
            sig = self.memoria[actual]["sig"]
            if self.es_circular():
                if sig == self.inicio:
                    return actual
            else:
                if sig == NULL_FINAL:
                    return actual
            actual = sig

    def _es_ultimo(self, direccion: int) -> bool:
        """Devuelve True si 'direccion' corresponde al último nodo."""
        if self.inicio == NULL_INICIO:
            return False
        return self._obtener_ultimo() == direccion

    def _buscar_anterior(self, direccion_obj: int) -> int:
        """En lista simple: devuelve la dirección del nodo cuyo 'sig' es direccion_obj."""
        actual = self.inicio
        while True:
            sig = self.memoria[actual]["sig"]
            if sig == direccion_obj:
                return actual

            if self.es_circular():
                if sig == self.inicio:
                    break
            else:
                if sig == NULL_FINAL:
                    break

            actual = sig

        return NULL_INICIO

    def _listar_direcciones(self) -> List[int]:
        """Devuelve una lista con las direcciones en orden de recorrido."""
        if self.inicio == NULL_INICIO:
            return []

        direcciones = []
        actual = self.inicio
        while True:
            direcciones.append(actual)
            sig = self.memoria[actual]["sig"]

            if self.es_circular():
                if sig == self.inicio:
                    break
            else:
                if sig == NULL_FINAL:
                    break

            actual = sig

        return direcciones


def menu():
    print("==============================================")
    print("Listas mediante simulación de punteros (0 y 99)")
    print("1- Listas enlazadas")
    print("2- Listas doblemente enlazadas")
    print("3- Listas circulares enlazadas")
    print("4- Listas circulares doblemente enlazadas")
    print("0- Salir")
    print("==============================================")


def menu_operaciones():
    print("\n--- Operaciones ---")
    print("1) insertar_inicio")
    print("2) insertar_nodo (después de un dato)")
    print("3) insertar_final")
    print("4) contar_nodos")
    print("5) eliminar_nodo")
    print("6) imprimir_valor_lista")
    print("7) imprimir_lista_completa")
    print("8) imprimir_reves")
    print("9) buscar_nodo")
    print("10) copiar_lista")
    print("0) volver")
    print("-------------------")


def pedir_entero(msg: str) -> int:
    while True:
        try:
            x = int(input(msg))
            return x
        except ValueError:
            print("Entrada no válida. Debe ser un número entero.")


def main():
    lista: Optional[ListaSimulada] = None

    while True:
        menu()
        op = pedir_entero("Elige tipo de lista: ")

        if op == 0:
            print("Saliendo...")
            break

        if op == 1:
            lista = ListaSimulada("enlazada")
        elif op == 2:
            lista = ListaSimulada("enlazada_d")
        elif op == 3:
            lista = ListaSimulada("circular")
        elif op == 4:
            lista = ListaSimulada("circular_d")
        else:
            print("Opción no válida.")
            continue

        # Operaciones sobre la lista creada
        while True:
            menu_operaciones()
            o = pedir_entero("Selecciona operación: ")

            if o == 0:
                print("Volviendo al menú de tipo de lista...\n")
                break

            try:
                if o == 1:
                    d = pedir_entero("Dato (1..98): ")
                    lista.insertar_inicio(d)

                elif o == 2:
                    dn = pedir_entero("Dato nuevo (1..98): ")
                    da = pedir_entero("Dato anterior (1..98): ")
                    lista.insertar_nodo(dn, da)

                elif o == 3:
                    d = pedir_entero("Dato (1..98): ")
                    lista.insertar_final(d)

                elif o == 4:
                    print("Número de nodos:", lista.contar_nodos())

                elif o == 5:
                    d = pedir_entero("Dato a eliminar (1..98): ")
                    lista.eliminar_nodo(d)

                elif o == 6:
                    lista.imprimir_valor_lista()

                elif o == 7:
                    lista.imprimir_lista_completa()

                elif o == 8:
                    lista.imprimir_reves()

                elif o == 9:
                    d = pedir_entero("Dato a buscar (1..98): ")
                    dir_encontrada = lista.buscar_nodo(d)
                    if dir_encontrada == NULL_INICIO:
                        print("No encontrado.")
                    else:
                        print(f"Encontrado en dirección: {dir_encontrada}")

                elif o == 10:
                    lista.copiar_lista()

                else:
                    print("Operación no válida.")

            except Exception as e:
                print("Error:", e)


if __name__ == "__main__":
    main()


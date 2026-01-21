from __future__ import annotations
from typing import Optional, Dict, Any, List
from datetime import datetime


class ColaEnlazada:

    def __init__(self) -> None:
        self.frente: Optional[Dict[str, Any]] = None
        self.fin: Optional[Dict[str, Any]] = None

    # ----------------------------
    # Operaciones básicas (cola)
    # ----------------------------
    def colavacia(self) -> bool:
        """Devuelve True si la cola está vacía."""
        return self.frente is None

    def encolar(self, animal: str) -> None:
        """Agrega un elemento al final de la cola."""
        animal = animal.strip()
        if not animal:
            raise ValueError("El animal no puede estar vacío.")

        nodo = {"dato": animal, "sig": None}

        if self.colavacia():
            self.frente = nodo
            self.fin = nodo
        else:
            # enlazar el antiguo fin con el nuevo nodo
            assert self.fin is not None
            self.fin["sig"] = nodo
            self.fin = nodo

    def desencolar(self) -> Optional[str]:
        """
        Elimina el elemento del principio y lo devuelve.
        Si está vacía, devuelve None.
        """
        if self.colavacia():
            return None

        nodo = self.frente
        self.frente = nodo["sig"]

        # Si al desencolar queda vacía, también fin pasa a None
        if self.frente is None:
            self.fin = None

        return nodo["dato"]

    def frente_cola(self) -> Optional[str]:
        """Consulta el elemento del principio sin eliminarlo."""
        if self.colavacia():
            return None
        return self.frente["dato"]

    # ----------------------------
    # Funciones extra solicitadas
    # ----------------------------
    def imprimircola(self) -> None:
        """Muestra por pantalla todos los elementos de la cola (de frente a fin)."""
        if self.colavacia():
            print("Cola vacía.")
            return

        print("Cola (de FRENTE a FIN):")
        actual = self.frente
        i = 1
        while actual is not None:
            print(f"  {i}. {actual['dato']}")
            actual = actual["sig"]
            i += 1

    def contar(self) -> int:
        """Muestra/devuelve el número de elementos de la cola."""
        c = 0
        actual = self.frente
        while actual is not None:
            c += 1
            actual = actual["sig"]
        return c

    def vaciarcola(self) -> None:
        """Vacía completamente la cola."""
        self.frente = None
        self.fin = None

    def invertircola(self) -> ColaEnlazada:
        """
        Copia en una cola nueva los elementos de la cola pero de forma inversa.
        Si la cola original (frente->fin) es: A, B, C
        La invertida (frente->fin) será: C, B, A
        """
        invertida = ColaEnlazada()

        # Recolectamos elementos en lista (frente->fin)
        elementos: List[str] = []
        actual = self.frente
        while actual is not None:
            elementos.append(actual["dato"])
            actual = actual["sig"]

        # Encolar en orden inverso para invertir
        for e in reversed(elementos):
            invertida.encolar(e)

        return invertida

    def copiarcola(self) -> ColaEnlazada:
        """
        Copia en una cola destino el contenido de una cola origen en el mismo orden,
        creando por lo tanto dos colas exactas.
        """
        copia = ColaEnlazada()

        actual = self.frente
        while actual is not None:
            copia.encolar(actual["dato"])
            actual = actual["sig"]

        return copia

    def guardarcola(self, nombre_fichero: str = "cola.txt") -> None:
        """
        Guarda en un fichero el contenido de la cola y la info relevante.
        El formato lo decide el desarrollador.
        """
        with open(nombre_fichero, "w", encoding="utf-8") as f:
            f.write("COLA (Queue) - Implementada con lista enlazada\n")
            f.write(f"Fecha/hora: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write(f"Elementos: {self.contar()}\n")
            f.write("Contenido (de FRENTE a FIN):\n")
            f.write("-" * 40 + "\n")

            if self.colavacia():
                f.write("Cola vacía.\n")
            else:
                actual = self.frente
                i = 1
                while actual is not None:
                    f.write(f"{i}. {actual['dato']}\n")
                    actual = actual["sig"]
                    i += 1

            f.write("-" * 40 + "\n")

        print(f"Cola guardada en: {nombre_fichero}")


# ----------------------------
# Menú de la aplicación
# ----------------------------
def pedir_texto(msg: str) -> str:
    return input(msg).strip()


def menu() -> None:
    print("\n===============================")
    print("   COLAS (Queue) - Menú")
    print("===============================")
    print("1) Encolar")
    print("2) Desencolar")
    print("3) Frente (consultar)")
    print("4) ¿Cola vacía?")
    print("5) Imprimir cola")
    print("6) Contar elementos")
    print("7) Invertir cola (nueva cola)")
    print("8) Copiar cola (nueva cola exacta)")
    print("9) Vaciar cola")
    print("10) Guardar cola en fichero")
    print("0) Salir")
    print("===============================")


def main() -> None:
    cola = ColaEnlazada()

    while True:
        menu()
        op = pedir_texto("Elige una opción: ")

        try:
            if op == "0":
                print("Saliendo...")
                break

            elif op == "1":
                animal = pedir_texto("Introduce un animal vertebrado a encolar: ")
                cola.encolar(animal)
                print("Elemento encolado correctamente.")

            elif op == "2":
                x = cola.desencolar()
                if x is None:
                    print("No se puede desencolar: cola vacía.")
                else:
                    print(f"Elemento desencolado: {x}")

            elif op == "3":
                x = cola.frente_cola()
                if x is None:
                    print("Cola vacía. No hay frente.")
                else:
                    print(f"Frente: {x}")

            elif op == "4":
                print("Cola vacía." if cola.colavacia() else "Cola con elementos.")

            elif op == "5":
                cola.imprimircola()

            elif op == "6":
                print(f"Número de elementos: {cola.contar()}")

            elif op == "7":
                inv = cola.invertircola()
                print("Cola invertida creada (sin modificar la original).")
                print("Original:")
                cola.imprimircola()
                print("Invertida:")
                inv.imprimircola()

            elif op == "8":
                copia = cola.copiarcola()
                print("Copia exacta creada (sin modificar la original).")
                print("Original:")
                cola.imprimircola()
                print("Copia:")
                copia.imprimircola()

            elif op == "9":
                cola.vaciarcola()
                print("Cola vaciada completamente.")

            elif op == "10":
                nombre = pedir_texto("Nombre del fichero (ENTER para 'cola.txt'): ")
                if not nombre:
                    nombre = "cola.txt"
                cola.guardarcola(nombre)

            else:
                print("Opción no válida.")

        except Exception as e:
            print("Error:", e)


if __name__ == "__main__":
    main()

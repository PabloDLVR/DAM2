

from __future__ import annotations
from typing import Optional, Dict, Any
from datetime import datetime


class PilaEnlazada:
    """
    Pila LIFO usando lista enlazada simple.
    Cada nodo es un diccionario: {'dato': <marca>, 'sig': <nodo_siguiente>}
    El tope es self.tope (referencia al nodo superior).
    """

    def __init__(self) -> None:
        self.tope: Optional[Dict[str, Any]] = None

    # ----------------------------
    # Operaciones básicas (pila)
    # ----------------------------
    def pilavacia(self) -> bool:
        """Devuelve True si la pila está vacía."""
        return self.tope is None

    def apilar(self, marca: str) -> None:
        """Push: agrega una marca al tope."""
        marca = marca.strip()
        if not marca:
            raise ValueError("La marca no puede estar vacía.")

        nodo = {"dato": marca, "sig": self.tope}
        self.tope = nodo

    def desapilar(self) -> Optional[str]:
        """Pop: elimina el elemento del tope y lo devuelve. Si está vacía, devuelve None."""
        if self.pilavacia():
            return None

        nodo = self.tope
        self.tope = nodo["sig"]
        return nodo["dato"]

    def tope_pila(self) -> Optional[str]:
        """Top/Peek: consulta el elemento del tope sin eliminarlo."""
        if self.pilavacia():
            return None
        return self.tope["dato"]

    # ----------------------------
    # Funciones extra solicitadas
    # ----------------------------
    def imprimirpila(self) -> None:
        """Muestra por pantalla todos los elementos de la pila (desde el tope hacia abajo)."""
        if self.pilavacia():
            print("Pila vacía.")
            return

        print("Pila (de TOPE a BASE):")
        actual = self.tope
        i = 1
        while actual is not None:
            print(f"  {i}. {actual['dato']}")
            actual = actual["sig"]
            i += 1

    def contar(self) -> int:
        """Devuelve el número de elementos de la pila."""
        c = 0
        actual = self.tope
        while actual is not None:
            c += 1
            actual = actual["sig"]
        return c

    def vaciarpila(self) -> None:
        """Vacía completamente la pila."""
        self.tope = None

    def invertirpila(self) -> PilaEnlazada:

        invertida = PilaEnlazada()
        # Recorremos y guardamos en una lista para poder invertir el orden sin romper la pila original
        elementos = []
        actual = self.tope
        while actual is not None:
            elementos.append(actual["dato"])
            actual = actual["sig"]
            
        for e in elementos:
            invertida.apilar(e)

        return invertida

    def copiarpila(self) -> PilaEnlazada:
        """
        Copia en una pila destino el contenido de una pila origen en el mismo orden,
        creando dos pilas exactas (mismo tope->base).

        Estrategia:
        - Recolectar elementos de origen (tope->base) en lista.
        - Apilar en destino en orden inverso para conservar el orden original.
        """
        copia = PilaEnlazada()
        elementos = []
        actual = self.tope
        while actual is not None:
            elementos.append(actual["dato"])
            actual = actual["sig"]

        # Para mantener el mismo orden (mismo tope), debemos apilar desde la base hacia el tope:
        # Si elementos = [A, B, C] con A=tope, apilar en orden inverso: C, B, A -> copia con A=tope
        for e in reversed(elementos):
            copia.apilar(e)

        return copia

    def guardarpila(self, nombre_fichero: str = "pila.txt") -> None:
        """
        Guarda en un fichero el contenido de la pila y la info relevante.
        El formato lo decide el desarrollador (según enunciado).
        """
        with open(nombre_fichero, "w", encoding="utf-8") as f:
            f.write("PILA (Stack) - Implementada con lista enlazada\n")
            f.write(f"Fecha/hora: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write(f"Elementos: {self.contar()}\n")
            f.write("Contenido (de TOPE a BASE):\n")
            f.write("-" * 40 + "\n")

            if self.pilavacia():
                f.write("Pila vacía.\n")
            else:
                actual = self.tope
                i = 1
                while actual is not None:
                    f.write(f"{i}. {actual['dato']}\n")
                    actual = actual["sig"]
                    i += 1

            f.write("-" * 40 + "\n")

        print(f"Pila guardada en: {nombre_fichero}")


# ----------------------------
# Menú de la aplicación
# ----------------------------
def pedir_texto(msg: str) -> str:
    return input(msg).strip()


def menu() -> None:
    print("\n===============================")
    print("   PILAS (Stack) - Menú")
    print("===============================")
    print("1) Apilar (push)")
    print("2) Desapilar (pop)")
    print("3) Tope (peek/top)")
    print("4) ¿Pila vacía? (isEmpty)")
    print("5) Imprimir pila")
    print("6) Contar elementos")
    print("7) Invertir pila (nueva pila)")
    print("8) Copiar pila (nueva pila exacta)")
    print("9) Vaciar pila")
    print("10) Guardar pila en fichero")
    print("0) Salir")
    print("===============================")


def main() -> None:
    pila = PilaEnlazada()

    while True:
        menu()
        op = pedir_texto("Elige una opción: ")

        try:
            if op == "0":
                print("Saliendo...")
                break

            elif op == "1":
                marca = pedir_texto("Introduce la marca de coche a apilar: ")
                pila.apilar(marca)
                print("Elemento apilado correctamente.")

            elif op == "2":
                x = pila.desapilar()
                if x is None:
                    print("No se puede desapilar: pila vacía.")
                else:
                    print(f"Elemento desapilado: {x}")

            elif op == "3":
                x = pila.tope_pila()
                if x is None:
                    print("Pila vacía. No hay tope.")
                else:
                    print(f"Tope: {x}")

            elif op == "4":
                print("Pila vacía." if pila.pilavacia() else "Pila con elementos.")

            elif op == "5":
                pila.imprimirpila()

            elif op == "6":
                print(f"Número de elementos: {pila.contar()}")

            elif op == "7":
                inv = pila.invertirpila()
                print("Pila invertida creada (sin modificar la original).")
                print("Original:")
                pila.imprimirpila()
                print("Invertida:")
                inv.imprimirpila()

            elif op == "8":
                copia = pila.copiarpila()
                print("Copia exacta creada (sin modificar la original).")
                print("Original:")
                pila.imprimirpila()
                print("Copia:")
                copia.imprimirpila()

            elif op == "9":
                pila.vaciarpila()
                print("Pila vaciada completamente.")

            elif op == "10":
                nombre = pedir_texto("Nombre del fichero (ENTER para 'pila.txt'): ")
                if not nombre:
                    nombre = "pila.txt"
                pila.guardarpila(nombre)

            else:
                print("Opción no válida.")

        except Exception as e:
            print("Error:", e)


if __name__ == "__main__":
    main()

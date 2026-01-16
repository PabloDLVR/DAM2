import os
import pickle

class Nodo:
    """Nodo para lista enlazada que almacena un dato y referencia al siguiente nodo."""
    def __init__(self, dato):
        self.dato = dato
        self.siguiente = None

class Pila:
    """Pila implementada con lista enlazada."""
    def __init__(self):
        self.tope = None
        self.tamanio = 0

    def apilar(self, dato):
        """Apila un nuevo elemento en la pila."""
        nuevo_nodo = Nodo(dato)
        nuevo_nodo.siguiente = self.tope
        self.tope = nuevo_nodo
        self.tamanio += 1
        print(f"'{dato}' ha sido apilado.")

    def desapilar(self):
        """Desapila el elemento en el tope y lo retorna."""
        if self.pilavacia():
            print("La pila está vacía, no se puede desapilar.")
            return None
        dato = self.tope.dato
        self.tope = self.tope.siguiente
        self.tamanio -= 1
        print(f"'{dato}' ha sido desapilado.")
        return dato

    def pilavacia(self):
        """Retorna True si la pila está vacía, False en caso contrario."""
        return self.tope is None

    def obtener_tope(self):
        """Retorna el dato en el tope sin desapilarlo."""
        if self.pilavacia():
            print("La pila está vacía, no hay tope.")
            return None
        return self.tope.dato

    def imprimirpila(self):
        """Muestra todos los elementos de la pila."""
        if self.pilavacia():
            print("La pila está vacía.")
            return
        actual = self.tope
        elementos = []
        while actual:
            elementos.append(actual.dato)
            actual = actual.siguiente
        print("Pila (tope -> base):", " -> ".join(elementos))

    def contar(self):
        """Muestra el número de elementos de la pila."""
        print(f"La pila tiene {self.tamanio} elementos.")

    def invertirpila(self):
        """Retorna una nueva pila con los elementos invertidos."""
        pila_invertida = Pila()
        actual = self.tope
        while actual:
            pila_invertida.apilar(actual.dato)
            actual = actual.siguiente
        return pila_invertida

    def copiarpila(self):
        """Retorna una copia exacta de la pila."""
        pila_copia = Pila()
        # Usamos una lista auxiliar para preservar el orden
        elementos = []
        actual = self.tope
        while actual:
            elementos.append(actual.dato)
            actual = actual.siguiente
        # Apilamos en orden inverso para mantener la estructura original
        for dato in reversed(elementos):
            pila_copia.apilar(dato)
        return pila_copia

    def vaciarpila(self):
        """Vacía completamente la pila."""
        while not self.pilavacia():
            self.desapilar()
        print("La pila ha sido vaciada por completo.")

    def guardarpila(self, nombre_archivo="pila_guardada.pkl"):
        """Guarda la pila en un archivo usando pickle."""
        elementos = []
        actual = self.tope
        while actual:
            elementos.append(actual.dato)
            actual = actual.siguiente
        datos = {
            'elementos': elementos,
            'tamanio': self.tamanio,
        }
        with open(nombre_archivo, 'wb') as archivo:
            pickle.dump(datos, archivo)
        print(f"Pila guardada en '{nombre_archivo}'.")

def cargarpila(nombre_archivo="pila_guardada.pkl"):
    """Carga una pila desde un archivo."""
    if not os.path.exists(nombre_archivo):
        print("El archivo no existe.")
        return None
    with open(nombre_archivo, 'rb') as archivo:
        datos = pickle.load(archivo)
    pila = Pila()
    for dato in reversed(datos['elementos']):
        pila.apilar(dato)
    pila.tamanio = datos['tamanio']
    print(f"Pila cargada desde '{nombre_archivo}'.")
    return pila

def mostrar_menu():
    """Muestra el menú de opciones."""
    print("\n" + "="*40)
    print("MENÚ DE PILAS DE MARCAS DE COCHES")
    print("="*40)
    print("1. Apilar una marca")
    print("2. Desapilar")
    print("3. Ver tope")
    print("4. Imprimir pila")
    print("5. Contar elementos")
    print("6. Invertir pila")
    print("7. Copiar pila")
    print("8. Vaciar pila")
    print("9. Guardar pila en archivo")
    print("10. Cargar pila desde archivo")
    print("11. Salir")
    print("="*40)

def main():
    pila = Pila()
    while True:
        mostrar_menu()
        opcion = input("Seleccione una opción: ").strip()
        if opcion == "1":
            marca = input("Ingrese la marca de coche a apilar: ").strip()
            if marca:
                pila.apilar(marca)
            else:
                print("Error: La marca no puede estar vacía.")
        elif opcion == "2":
            pila.desapilar()
        elif opcion == "3":
            tope = pila.obtener_tope()
            if tope:
                print(f"El tope de la pila es: {tope}")
        elif opcion == "4":
            pila.imprimirpila()
        elif opcion == "5":
            pila.contar()
        elif opcion == "6":
            pila_invertida = pila.invertirpila()
            print("Pila invertida creada. Mostrando pila invertida:")
            pila_invertida.imprimirpila()
        elif opcion == "7":
            pila_copia = pila.copiarpila()
            print("Copia de la pila creada. Mostrando copia:")
            pila_copia.imprimirpila()
        elif opcion == "8":
            pila.vaciarpila()
        elif opcion == "9":
            nombre = input("Nombre del archivo (Enter para 'pila_guardada.pkl'): ").strip()
            if not nombre:
                nombre = "pila_guardada.pkl"
            pila.guardarpila(nombre)
        elif opcion == "10":
            nombre = input("Nombre del archivo (Enter para 'pila_guardada.pkl'): ").strip()
            if not nombre:
                nombre = "pila_guardada.pkl"
            pila_cargada = cargarpila(nombre)
            if pila_cargada:
                pila = pila_cargada
        elif opcion == "11":
            print("Saliendo del programa. ¡Hasta luego!")
            break
        else:
            print("Opción no válida. Intente nuevamente.")

if __name__ == "__main__":
    main()
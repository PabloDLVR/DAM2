# Ejercicio 1: Crear y mostrar una lista
frutas = ["manzana", "pera", "naranja", "plátano", "fresa"]
print("Lista completa:", frutas)
print("Primer elemento:", frutas[0])
print("Último elemento:", frutas[-1])

# Ejercicio 2: Añadir y eliminar elementos
numeros = []
numeros.append(10)
numeros.append(20)
numeros.append(30)
del numeros[1]  # Elimina el segundo número (índice 1)
print("Lista final después de añadir y eliminar:", numeros)

# Ejercicio 3: Recorrer la lista
lista_numeros = [1, 2, 3, 4, 5]
for numero in lista_numeros:
    print(f"{numero} x 2 = {numero * 2}")

# Ejercicio 4: Buscar en la lista
palabras = ["manzana", "pera", "naranja"]
buscar = input("Introduce una palabra para buscar en la lista: ")
if buscar in palabras:
    print(f"La palabra '{buscar}' está en la lista.")
else:
    print(f"La palabra '{buscar}' NO está en la lista.")

# Ejercicio 5: Listas y slicing
lista_10 = list(range(1, 11))
print("Primeros 3 números:", lista_10[:3])
print("Últimos 3 números:", lista_10[-3:])
print("Números de la posición 3 a 7:", lista_10[3:8])  # índices 3 a 7 inclusive

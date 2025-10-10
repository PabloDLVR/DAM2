#1. Tabla de multiplicar: Escribe un programa en python que imprima todas las tablas de multiplicar del 1 al 10. Será importante que se presenten de forma clara y ordenada.
from tkinter import NO


for i in range(1, 11):
    print(f"Tabla del {i}:")
    for j in range(1, 11):
        print(f"{i} x {j} = {i * j}")
    print()
 #2.Semi-triángulo de asteriscos: Crea un programa que dibuje un semi-triángulo de asteriscos. El usuario debe ingresar la altura por teclado.
altura = int(input("Introduce la altura del semi-triángulo: "))
for i in range(1, altura + 1):
    print("*" * i)
    #3. Patrón de números: Diseña un programa que imprima un patrón de números en forma de cuadrado. Por ejemplo, si el usuario introduce por teclado el número 4, el patrón 5debería ser:
n = int(input("Introduce un número: "))
for i in range(1, n + 1):
    for j in range(1, n + 1):
        print(i, end=" ")
    print()
    # 4a. Matriz de ceros: Escribe un programa que cree una matriz cuadrada de ceros de tamaño nxn, donde n es el número de elementos de cada fila y columna y será especificado por el usuario (introducido por teclado).
    n = int(input("Introduce el tamaño de la matriz: "))
    matriz = [[0 for _ in range(n)] for _ in range(n)]
    for fila in matriz:
        print(fila)
        break
    #4b. Modifica el ejemplo anterior (4a) para que en lugar de ceros, introduzca automáticamente (NO por teclado) los números impares que correspondan.
    n = int(input("Introduce el tamaño de la matriz: "))
    matriz = [[0 for _ in range(n)] for _ in range(n)]
    contador = 1
    for i in range(n):
        for j in range(n):
            matriz[i][j] = contador
            contador += 2
    for fila in matriz:
        print(fila)

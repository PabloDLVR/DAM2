#1. Tabla de multiplicar: Escribe un programa en python que imprima todas las tablas de multiplicar del 1 al 10. Será importante que se presenten de forma clara y ordenada.
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
        break
    
#4c. Rellenando matrices. Amplía el ejercicio 4a de forma que el usuario no sólo decida la dimensión de la matriz cuadrada sino que introduzca los datos (números enteros) por teclado.
n = int(input("Introduce el tamaño de la matriz: "))
matriz = []
for i in range(n):
    fila = []
    for j in range(n):
        valor = int(input(f"Introduce el valor para la posición ({i}, {j}): "))
        fila.append(valor)
    matriz.append(fila)
for fila in matriz:
    print(fila)

#5. Suma de matrices: amplía el ejercicio 4c con un programa que sume dos matrices cuadradas con el mismo orden. Todos los datos de la suma son números enteros.
n = int(input("Introduce el tamaño de la primera matriz: "))
matriz1 = []
for i in range(n):
    fila = []
    for j in range(n):
        valor = int(input(f"Introduce el valor para la posición ({i}, {j}) de la primera matriz: "))
        fila.append(valor)
    matriz1.append(fila)

n = int(input("Introduce el tamaño de la segunda matriz: "))
matriz2 = []
for i in range(n):
    fila = []
    for j in range(n):
        valor = int(input(f"Introduce el valor para la posición ({i}, {j}) de la segunda matriz: "))
        fila.append(valor)
    matriz2.append(fila)

# Sumar las dos matrices
matriz_suma = []
for i in range(n):
    fila = []
    for j in range(n):
        fila.append(matriz1[i][j] + matriz2[i][j])
    matriz_suma.append(fila)

print("La matriz resultante de la suma es:")
for fila in matriz_suma:
    print(fila)
    
#6. Cálculo de la suma de fila y columna en una matriz: Crea una matriz en la que cada elemento sea la suma del número de fila y columna correspondiente. El usuario indicará por teclado la dimensión de la matriz.
n = int(input("Introduce el tamaño de la matriz: "))
matriz = []
for i in range(n):
    fila = []
    for j in range(n):
        fila.append(i + j)
    matriz.append(fila)
for fila in matriz:
    print(fila)
    
    
#7. Dibujo de un rombo: Diseña un programa que dibuje un rombo equilibrado de asteriscos. El usuario debe ingresar la altura del rombo, siendo esta siempre impar. Si introduce una altura par, deberá indicar por pantalla que vuelva a introducir la altura (rombos con altura par...no son rombos equilibrados).
altura = int(input("Introduce la altura del rombo (debe ser impar): "))
if altura % 2 == 0: 
    print("La altura debe ser un número impar. Por favor, vuelve a intentarlo.")
else:
    mitad = altura // 2
    for i in range(mitad + 1):
        print(" " * (mitad - i) + "*" * (2 * i + 1))
    for i in range(mitad - 1, -1, -1):
        print(" " * (mitad - i) + "*" * (2 * i + 1))
        
        # Ejercicio 8

# Matriz dada
matriz = [
    [1, 2, 3, 4, 5],
    [6, 7, 8, 9, 10],
    [11, 12, 13, 14, 15],
    [16, 17, 18, 19, 20],
    [21, 22, 23, 24, 25]
]

n = len(matriz)

# Suma de diagonal principal
diag_principal = sum(matriz[i][i] for i in range(n))

# Suma de diagonal secundaria
diag_secundaria = sum(matriz[i][n - 1 - i] for i in range(n))

# Preguntar si repetir el elemento central
repetir = input("¿Deseas repetir el elemento central en la suma? (s/n): ").lower()

# Si no se desea repetir, se resta el valor central una vez
if repetir == 'n':
    suma_total = diag_principal + diag_secundaria - matriz[n // 2][n // 2]
else:
    suma_total = diag_principal + diag_secundaria

print(f"Suma de las diagonales: {suma_total}")

# Ejercicio 9

n = int(input("Introduce el tamaño de la matriz (por ejemplo 5): "))
matriz = []

print("Introduce los valores de la matriz fila por fila:")

for i in range(n):
    fila = list(map(int, input(f"Fila {i+1}: ").split()))
    while len(fila) != n:
        print(f"Debe ingresar {n} valores por fila.")
        fila = list(map(int, input(f"Fila {i+1}: ").split()))
    matriz.append(fila)

# Suma de diagonal principal y secundaria
diag_principal = sum(matriz[i][i] for i in range(n))
diag_secundaria = sum(matriz[i][n - 1 - i] for i in range(n))

# Preguntar si repetir el elemento central
repetir = input("¿Deseas repetir el elemento central en la suma? (s/n): ").lower()

if repetir == 'n' and n % 2 != 0:
    suma_total = diag_principal + diag_secundaria - matriz[n // 2][n // 2]
else:
    suma_total = diag_principal + diag_secundaria

print(f"Suma de las diagonales: {suma_total}")

        

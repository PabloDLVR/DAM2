#############################################################################################
#1.1.4 Ejercicios python
#Ejercicio 1: Programa que calcula la suma de los números del 1 al 10
suma = 0  
for numero in range(1, 11):
    suma += numero  
print("La suma de los números del 1 al 10 es:", suma)


#Ejercicio 2: Programa que calcula el factorial de un número

n = int(input("Introduce un número para calcular su factorial: "))
factorial = 1  
for i in range(1, n + 1):
    factorial *= i 

print("El factorial de", n, "es:", factorial)


# Ejercicio 3: Diseña un programa que muestre los números primos entre 1 y 50.

for num in range(2, 51):
    es_primo = True
    for i in range(2, num):
        if num % i == 0:
            es_primo = False
            break
    if es_primo:
        print(num)


# Ejercicio 4: Realiza un programa que calcule la suma de los dígitos de un número entero ingresado por el usuario.

numero = int(input("Introduce un número entero: "))
suma = 0

while numero > 0:
    digito = numero % 10
    suma += digito
    numero = numero // 10
print("La suma de los dígitos es:", suma)

# Ejercicio 5: Escribe un programa que sume los números pares del 1 al 100.
suma = 0
for num in range(1, 101):
    if num % 2 == 0:
        suma += num
print("La suma de los pares del 1 al 100 es:", suma)


# Ejercicio 6: Escribe un programa que calcule el área de un triángulo dados su base y altura.

base = float(input("Introduce la base del triángulo: "))
altura = float(input("Introduce la altura del triángulo: "))
area = (base * altura) / 2
print("El área del triángulo es:", area)

# Ejercicio 7: Realiza un programa que muestre los números del 1 al 100,
# pero que reemplace los múltiplos de 3 por "Fizz" y los múltiplos de 5 por "Buzz".

for num in range(1, 101):
    if num % 3 == 0:
        print("Fizz")
    elif num % 5 == 0:
        print("Buzz")
    else:
        print(num)


# Ejercicio 8: Crea un programa que simule un juego de adivinanza,
# donde el usuario debe adivinar un número aleatorio generado por la computadora.

import random

numero_secreto = random.randint(1, 10)
adivinado = False

while not adivinado:
    intento = int(input("Adivina un número entre 1 y 10: "))
    if intento == numero_secreto:
        print("¡Correcto! Has adivinado el número.")
        adivinado = True
    else:
        print("Intenta de nuevo.")

# Ejercicio 9: Diseña un programa que calcule el promedio de una lista de números ingresados por el usuario.

cantidad = int(input("¿Cuántos números quieres ingresar?: "))
suma = 0

for i in range(cantidad):
    numero = float(input("Introduce un número: "))
    suma += numero

promedio = suma / cantidad
print("El promedio es:", promedio)


# Ejercicio 10: Realiza un programa que cuente cuántas vocales hay en una cadena de texto ingresada por el usuario.

texto = input("Introduce un texto: ")
contador = 0

for letra in texto:
    if letra.lower() in "aeiou":
        contador += 1

print("El número de vocales es:", contador)

# Ejercicio 11: Crea un programa que determine si una palabra es un palíndromo o no.

palabra = input("Introduce una palabra: ")
invertida = ""

for letra in palabra:
    invertida = letra + invertida

if palabra == invertida:
    print("La palabra es un palíndromo.")
else:
    print("La palabra no es un palíndromo.")


# Ejercicio 12: Escribe un programa que calcule el área de un círculo dado su radio.

radio = float(input("Introduce el radio del círculo: "))
area = 3.1416 * (radio ** 2)
print("El área del círculo es:", area)

# Ejercicio 13: Diseña un programa que genere una tabla de multiplicar para un número ingresado por el usuario.

numero = int(input("Introduce un número para ver su tabla de multiplicar: "))

for i in range(1, 11):
    print(numero, "x", i, "=", numero * i)


# Ejercicio 14: Realiza un programa que sume los números impares del 1 al 100.

suma = 0
for num in range(1, 101):
    if num % 2 != 0:
        suma += num

print("La suma de los impares del 1 al 100 es:", suma)

# Ejercicio 15: Crea un programa que cuente cuántas veces aparece una letra específica en una cadena de texto ingresada por el usuario.

texto = input("Introduce un texto: ")
letra = input("Introduce la letra que quieres contar: ")
contador = 0

for caracter in texto:
    if caracter == letra:
        contador += 1

print("La letra", letra, "aparece", contador, "veces.")


# Ejercicio 16: Escribe un programa que encuentre el máximo común divisor (MCD) de dos números ingresados por el usuario.

a = int(input("Introduce el primer número: "))
b = int(input("Introduce el segundo número: "))

while b != 0:
    resto = a % b
    a = b
    b = resto

print("El MCD es:", a)

# Ejercicio 17: Diseña un programa que calcule el número de combinaciones posibles de n elementos
# tomados de k en k, donde n y k son ingresados por el usuario.

# Fórmula: C(n, k) = n! / (k! * (n-k)!)

def factorial(num):
    resultado = 1
    for i in range(1, num + 1):
        resultado *= i
    return resultado

n = int(input("Introduce el número total de elementos (n): "))
k = int(input("Introduce el número de elementos tomados (k): "))

combinaciones = factorial(n) // (factorial(k) * factorial(n - k))
print("El número de combinaciones posibles es:", combinaciones)


# Ejercicio 18: Realiza un programa que simule el juego de "Piedra, Papel o Tijeras" contra la computadora.

import random

opciones = ["piedra", "papel", "tijeras"]
eleccion_usuario = input("Elige piedra, papel o tijeras: ").lower()
eleccion_pc = random.choice(opciones)

print("La computadora eligió:", eleccion_pc)

if eleccion_usuario == eleccion_pc:
    print("¡Empate!")
elif (eleccion_usuario == "piedra" and eleccion_pc == "tijeras") or \
     (eleccion_usuario == "papel" and eleccion_pc == "piedra") or \
     (eleccion_usuario == "tijeras" and eleccion_pc == "papel"):
    print("¡Ganaste!")
else:
    print("Perdiste, intenta de nuevo.")
  
#############################################################################################
    
# 5.1 - Suma de números

num1 = int(input("Introduce el primer número: "))
num2 = int(input("Introduce el segundo número: "))
suma = num1 + num2
print ("La suma es:", suma)

# 5.2 - Calculadora de area de un cuadrado

lado = int(input("Introduce el lado del cuadrado: "))
lado * lado
print("El área del cuadrado es:", lado * lado)

# 5.3 - Conversion de temperatura

celsius = int(input("Introduce la temperatura en grados Celsius: "))
fahrenheit = (celsius * 9/5) + 32
print("La temperatura en grados Fahrenheit es:", fahrenheit)

# 5.4 - Calculadora de IMC
peso = int(input("Introduce tu peso en kg: "))
altura = int(input("Introduce tu altura en metros: "))
imc = peso / (altura ** 2)
print(f"Tu índice de masa corporal es:, {imc}")

# 5.5 - Concatenación de cadenas: 

cadena1 = input("Introduce la primera cadena: ")
cadena2 = input("Introduce la segunda cadena: ")
cadena_concatenada = cadena1 + " " + cadena2
print("La cadena concatenada es:", cadena_concatenada)

# 5. 6 Determinar el tipo de dato:



# 5.7 Calcular el promedio de tres números:

num1 = int(input("Introduce el primer número: "))
num2 = int(input("Introduce el segundo número: "))
num3 = int(input("Introduce el tercer número: "))
promedio = (num1 + num2 + num3) / 3
print("El promedio es:", promedio)

# 5.8 Área de un triángulo:

base = int(input("Introduce la base del triángulo: "))
altura = int(input("Introduce la altura del triángulo: "))
area = (base * altura) / 2
print("El área del triángulo es:", area)

# 5.9 Edad en el futuro:

edad_actual = int(input("Introduce tu edad actual: "))
años_futuro = int(input("Introduce el número de años en el futuro: "))
edad_futura = edad_actual + años_futuro
print("Tu edad en", años_futuro, "años será:", edad_futura)

# 5.10 Área de un rectángulo:

largo = int(input("Introduce el largo del rectángulo: "))
ancho = int(input("Introduce el ancho del rectángulo: "))
area = largo * ancho
print("El área del rectángulo es:", area)

# 5.11 Días a segundos:

dias = int(input("Introduce el número de días: "))
segundos = dias * 24 * 60 * 60
print(dias, "días son", segundos, "segundos")

# 5.12 Raíz cuadrada:

num = int(input("Introduce un número: "))
raiz_cuadrada = num ** 0.5
print("La raíz cuadrada de", num, "es:", raiz_cuadrada)

# 5.13 Conversión de moneda:

euros = float(input("Introduce la cantidad en euros: "))
tasa_cambio = 1.1  # Ejemplo de tasa de cambio
dolares = euros * tasa_cambio
print(euros, "euros son", dolares, "dólares")
# --------------------------------------------------------
# 🌟 Calculadora Científica 🌟
# --------------------------------------------------------

# Librerías necesarias
import math  
from colorama import Fore, Style, init  

# Inicializamos colorama (para que funcione en Windows también)
init(autoreset=True)

# -----------------------------
# 🔐 Sistema de contraseña
# -----------------------------
password = "1234"  # Contraseña inicial

def verificar_password():
    """Función que pide contraseña y valida antes de acceder 🛡️"""
    global password
    intento = input(Fore.YELLOW + "👉 Ingresa la contraseña: ")
    if intento == password:
        print(Fore.GREEN + "✅ ¡Acceso concedido! Bienvenido a tu calculadora científica 🌟")
        return True
    else:
        print(Fore.RED + "❌ Contraseña incorrecta. Intenta de nuevo.")
        return False

def cambiar_password():
    """Función para cambiar la contraseña actual y pedir validación 🔑"""
    global password
    nueva = input(Fore.CYAN + "🔑 Escribe tu nueva contraseña: ")
    password = nueva
    print(Fore.GREEN + "✅ Contraseña cambiada con éxito.")
    # Ahora pedimos la nueva contraseña para confirmar
    while True:
        intento = input(Fore.YELLOW + "👉 Vuelve a introducir la nueva contraseña para continuar: ")
        if intento == password:
            print(Fore.GREEN + "✅ ¡Perfecto! Contraseña confirmada. 🚀")
            break
        else:
            print(Fore.RED + "❌ Contraseña incorrecta, inténtalo de nuevo.")

# -----------------------------
# 📚 Operaciones Matemáticas
# -----------------------------
def suma(a, b): return a + b
def resta(a, b): return a - b
def multiplicacion(a, b): return a * b
def division(a, b): return "⚠️ No se puede dividir entre 0" if b == 0 else a / b
def valor_absoluto(a): return abs(a)
def seno(a): return math.sin(math.radians(a))
def coseno(a): return math.cos(math.radians(a))
def tangente(a): return math.tan(math.radians(a))
def log_base10(a): return math.log10(a) if a > 0 else "⚠️ Número no válido"
def log_neperiano(a): return math.log(a) if a > 0 else "⚠️ Número no válido"
def exponencial(a): return math.exp(a)
def raiz_cuadrada(a): return math.sqrt(a) if a >= 0 else "⚠️ Número negativo"

# 🌟 Operaciones extra
def potencia(a, b): return a ** b
def factorial(a): return math.factorial(a) if a >= 0 else "⚠️ Número no válido"
def log_base_n(a, n): return math.log(a, n) if a > 0 and n > 1 else "⚠️ Valores no válidos"

# -----------------------------
# 📜 Menú Principal
# -----------------------------
def menu():
    print(Fore.MAGENTA + Style.BRIGHT + "\n🧮 MENÚ DE LA CALCULADORA CIENTÍFICA 🧮")
    print(Fore.CYAN + """
    1️⃣  Suma
    2️⃣  Resta
    3️⃣  Multiplicación
    4️⃣  División
    5️⃣  Valor Absoluto
    6️⃣  Seno
    7️⃣  Coseno
    8️⃣  Tangente
    9️⃣  Logaritmo base 10
    🔟  Logaritmo Neperiano
    1️⃣ 1️⃣ Exponencial (e^x)
    1️⃣ 2️⃣ Raíz cuadrada
    1️⃣ 3️⃣ Potencia
    1️⃣ 4️⃣ Factorial
    1️⃣ 5️⃣ Logaritmo en base n
    1️⃣ 6️⃣ Cambiar contraseña
    0️⃣  Apagar calculadora
    """)

# -----------------------------
# 🚀 Programa Principal
# -----------------------------
def calculadora():
    if not verificar_password():
        return  # Si la contraseña no es correcta, salir

    while True:
        menu()
        opcion = input(Fore.YELLOW + "👉 Elige una opción: ")

        if opcion == "0":
            print(Fore.RED + "👋 Apagando la calculadora... ¡Hasta luego!")
            break

        elif opcion == "1":
            a, b = float(input("➕ Primer número: ")), float(input("➕ Segundo número: "))
            print(Fore.GREEN + f"Resultado: {suma(a, b)}")

        elif opcion == "2":
            a, b = float(input("➖ Primer número: ")), float(input("➖ Segundo número: "))
            print(Fore.GREEN + f"Resultado: {resta(a, b)}")

        elif opcion == "3":
            a, b = float(input("✖️ Primer número: ")), float(input("✖️ Segundo número: "))
            print(Fore.GREEN + f"Resultado: {multiplicacion(a, b)}")

        elif opcion == "4":
            a, b = float(input("➗ Primer número: ")), float(input("➗ Segundo número: "))
            print(Fore.GREEN + f"Resultado: {division(a, b)}")

        elif opcion == "5":
            a = float(input("🔢 Número: "))
            print(Fore.GREEN + f"Resultado: {valor_absoluto(a)}")

        elif opcion == "6":
            a = float(input("📐 Ángulo en grados: "))
            print(Fore.GREEN + f"Seno({a}) = {seno(a)}")

        elif opcion == "7":
            a = float(input("📐 Ángulo en grados: "))
            print(Fore.GREEN + f"Coseno({a}) = {coseno(a)}")

        elif opcion == "8":
            a = float(input("📐 Ángulo en grados: "))
            print(Fore.GREEN + f"Tangente({a}) = {tangente(a)}")

        elif opcion == "9":
            a = float(input("🔢 Número: "))
            print(Fore.GREEN + f"log10({a}) = {log_base10(a)}")

        elif opcion == "10":
            a = float(input("🔢 Número: "))
            print(Fore.GREEN + f"ln({a}) = {log_neperiano(a)}")

        elif opcion == "11":
            a = float(input("🔢 Número: "))
            print(Fore.GREEN + f"e^{a} = {exponencial(a)}")

        elif opcion == "12":
            a = float(input("🔢 Número: "))
            print(Fore.GREEN + f"√{a} = {raiz_cuadrada(a)}")

        elif opcion == "13":
            a, b = float(input("🔢 Base: ")), float(input("🔢 Exponente: "))
            print(Fore.GREEN + f"{a}^{b} = {potencia(a, b)}")

        elif opcion == "14":
            a = int(input("🔢 Número entero: "))
            print(Fore.GREEN + f"{a}! = {factorial(a)}")

        elif opcion == "15":
            a, n = float(input("🔢 Número: ")), float(input("🔢 Base del logaritmo: "))
            print(Fore.GREEN + f"log_{n}({a}) = {log_base_n(a, n)}")

        elif opcion == "16":
            cambiar_password()

        else:
            print(Fore.RED + "⚠️ Opción no válida, intenta de nuevo.")

# -----------------------------
# ▶️ Ejecutamos la calculadora
# -----------------------------
calculadora()

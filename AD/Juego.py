import random
import time

tiempo_limite = random.randint(8, 15)
numero_secreto = random.randint(1, 100)

print(f"Tienes {tiempo_limite} segundos para adivinar el número entre 1 y 100.")

inicio = time.time()

while True:
    if time.time() - inicio > tiempo_limite:
        print(f"⏰ Se acabó el tiempo. El número era {numero_secreto}.")
        break

    intento_str = input("Adivina el número: ")

    if not intento_str.isdigit():
        print("⚠️ Solo se permiten números enteros.")
        continue

    intento = int(intento_str)

    if intento == numero_secreto:
        print("🎉 ¡Felicidades! Has adivinado el número.")
        break
    elif intento < numero_secreto:
        print("El número es mayor.")
    else:
        print("El número es menor.")

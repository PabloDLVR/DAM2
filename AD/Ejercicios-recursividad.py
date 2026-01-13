def factorial(n):
    if n == 0 or n == 1:
        return 1
    else:
        return n * factorial(n - 1)
    

def multiplicacion(a,b):
    if b == 0:
        return 0
    else:
        return a + multiplicacion(a, b - 1)


def mcd(a,b):
    if a == 0 and b == 0:
        return "Indefinido"
    elif b == 0:
        return a
    else:
        return mcd(b, a % b)
    

def exponente(base, exp):
    if exp == 0:
        return 1
    else:
        return base * exponente(base, exp - 1)
    

def resta(a,b):
    if b == 0:
        return a
    else:
        return resta(a - 1, b - 1)
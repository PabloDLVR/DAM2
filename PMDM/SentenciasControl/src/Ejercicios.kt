fun main() {

    //Ejercicio1()
    //Ejercicio2()
}
//1. Escribe un programa que lea un número entero y que indique si es par o impar.
fun Ejercicio1(){
    println("Intrduce un número: ")
    val numero = readLine()!!.toInt()

    if(numero %2 == 0){
        println("El numero: $numero es par")
    }else{
        println("El numero: $numero es impar")
    }
}

//2. Escribe un programa que lea un número entero y que indique si es positivo, negativo o cero.
fun Ejercicio2(){
    println("Intrduce un número: ")
    val numero = readLine()!!.toInt()
    if(numero == 0){
        println("El numero es: $numero")
    }
    else if(numero > 0){
        println("El numero: $numero es positivo")
    }else{
        println("El numero : $numero es negativo")
    }
}

//3. Escribe un programa que lea un número entero y que indique si es primo o no.
fun Ejercicio3(){

}

//4. Escribe un programa que lea un número entero y que muestre por pantalla todos los números primos menores o iguales al número leído.


//5. Escribe un programa que lea un número entero y que calcule el factorial de ese número.


//6. Escribe un programa que lea un número entero y que calcule la suma de los primeros N números naturales.


//7. Escribe un programa que lea un número entero y que calcule la suma de los dígitos de ese número.


//8. Escribe un programa que lea un número entero y que muestre por pantalla su representación en binario.


//9. Escribe un programa que lea un número entero y que muestre por pantalla su representación en hexadecimal.


//10. Escribe un programa que lea un número entero y que muestre por pantalla su representación en octal.
fun main() {
    // comentario de linea
    /* println("Este es mi primer programa kotlin")

     // var -> variables mutables  puede cambiar su valor
     // val -> variables no mutables no pueden cambiar de valor

     var nombre: String = "Oscar" // String
     var edad: Int = 24 // Int
     val DNI: String = "123154a"
     lateinit var direcion: String
     var correo: String? = null

     println("Por fvaor introdue tu nombre")
     nombre = readln()
     println("Por favor introdue tu edad")
     edad = readln().toInt() //toInt para cambiar el tipo de variable

     println("Hola mi nombre es $nombre tengo $edad y mi DNI es $DNI")
     println("Mi nombre tiene ${nombre.length} letras")
     correo = "correo1@cesjp.com"
     println("Mi correo es ${correo?.length ?: "Sin asisgnar"}")
     direcion = "Alcorcon"
     println("Mi direccion es ${direcion}")

 */
    /*var nombreParam: String = "Pablo" // String
    saludar(nombreParam)*/

    /*println("El resultado de la operacion es ${sumar(op1 = 4, op2 = 7)}")*/

    //sumaFlecha(5, 7)

    //decdirWhen(9)

    repetirFor()

}

fun saludar(nombreParam: String = "Sin nombre", vecesParam: Int = 1) {

    println("Hola $nombreParam, acabas de realizar una funcion")

}

fun sumar(op1: Int, op2: Int): Int {
    return op1 + op2
}

val sumaFlecha = { p1: Int, p2: Int -> println("El resultado de la suma es ${p1 + p2}") }

fun decdirWhen(valor: Int) {

    println("El valor analizado es: ")
    when (valor) {
        1 -> {
            println("El caso tiene como valor 1 ")
        }

        2 -> {
            println("El caso tiene como valor 2 ")
        }

        3 -> {
            println("El caso tiene como valor 3 ")
        }

        4 -> {
            println("EEl caso tiene como valor 4")
        }
    }

}

fun repetirFor() {
    /*for (i in 0.. 9) {
        println(i)
    }*/


    // Si la funcion de flecha solo tiene 1 argumento, este se puede llmar con la palabra reservada it
   // (20..30).forEach { }
    (20..30).forEachIndexed { index, item -> println("Elemento en posicion $index con valor $item") }
}



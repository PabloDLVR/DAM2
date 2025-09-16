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

    sumaFlecha(5, 7)

}

fun saludar(nombreParam: String = "Sin nombre", vecesParam: Int = 1) {

    println("Hola $nombreParam, acabas de realizar una funcion")

}

fun sumar(op1: Int, op2: Int): Int {
    return op1 + op2
}

val sumaFlecha = { p1: Int, p2: Int -> println("El resultado de la suma es ${p1 + p2}") }




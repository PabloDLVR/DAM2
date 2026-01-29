fun cifrar(palabra: String, desplazamiento: Int): String {
    return palabra.map { (it + desplazamiento).toChar() }.joinToString("")
}

fun descifrar(palabra: String, desplazamiento: Int): String {
    return palabra.map { (it - desplazamiento).toChar() }.joinToString("")

}

fun main() {

    val palabra = "hola"
    val desplazamiento = 3

    val palabraCifrada = cifrar(palabra, desplazamiento)
    println("Palabra cifrada: $palabraCifrada")

    val palabraDescifrada = descifrar(palabraCifrada, desplazamiento)
    println("Palabra descifrada: $palabraDescifrada")
}
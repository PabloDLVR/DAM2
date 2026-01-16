import java.security.MessageDigest

fun hashSHA256(texto: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    val hash = md.digest(texto.toByteArray())
    return hash.joinToString("") { "%02x".format(it) }
}

fun main() {

    val palabraOriginal = "perro"
    val hashObjetivo = hashSHA256(palabraOriginal)

    println("Hash objetivo: $hashObjetivo")

    val posiblesPalabras = listOf(
        "adios",
        "casa",
        "perro",
        "hola",
        "java",
        "kotlin"
    )

    for (palabra in posiblesPalabras) {
        val hashActual = hashSHA256(palabra)

        if (hashActual == hashObjetivo) {
            println("¡Palabra encontrada!: $palabra")
            break
        }
    }
}

import java.nio.file.Files
import java.nio.file.Paths
import java.security.*
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher

// ========== FUNCIONES PARA CARGAR CLAVES ==========
fun cargarPublicaDesdeBase64(archivoBase64: String): PublicKey {
    // Leer clave pública en Base64
    val base64 = Files.readString(Paths.get(archivoBase64)).trim()
    val bytesClave = Base64.getDecoder().decode(base64)

    // Convertir a PublicKey
    return KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(bytesClave))
}

fun cargarPublicaDesdeDer(archivoDer: String): PublicKey {
    val contenido = Files.readAllBytes(Paths.get(archivoDer))
    return KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(contenido))
}

// ========== DESCIFRAR CON CLAVE PÚBLICA (TU AMIGO) ==========
fun descifrarConPublica(textoCifradoBase64: String, clavePublica: PublicKey): String {
    val cipher = Cipher.getInstance("RSA")
    cipher.init(Cipher.DECRYPT_MODE, clavePublica)

    // Decodificar Base64 y descifrar
    val bytesCifrados = Base64.getDecoder().decode(textoCifradoBase64)
    val bytesDescifrados = cipher.doFinal(bytesCifrados)

    return String(bytesDescifrados)
}

fun main() {
    println("=== CÓDIGO DE TU AMIGO - PARA DESCIFRAR ===")

    // ========== PASO 1: PREPARAR ARCHIVOS ==========
    println("\n1. Coloca en la misma carpeta estos archivos que recibiste:")
    println("   - publica_base64.txt (clave pública de tu amigo)")
    println("   - mensaje_cifrado.txt (mensaje cifrado)")

    // Simular que tenemos los archivos
    // (En realidad, los copiarás manualmente a esta carpeta)

    try {
        // ========== PASO 2: CARGAR CLAVE PÚBLICA ==========
        println("\n2. Cargando clave pública de tu amigo...")
        val clavePublicaAmigo = cargarPublicaDesdeBase64("publica_base64.txt")
        println("✓ Clave pública cargada correctamente")

        // Mostrar fingerprint de la clave
        val md = MessageDigest.getInstance("SHA-256")
        val fingerprint = Base64.getEncoder().encodeToString(
            md.digest(clavePublicaAmigo.encoded)
        ).substring(0, 20)
        println("   Fingerprint (SHA-256): ${fingerprint}...")

        // ========== PASO 3: CARGAR MENSAJE CIFRADO ==========
        println("\n3. Cargando mensaje cifrado...")
        val mensajeCifrado = Files.readString(Paths.get("mensaje_cifrado.txt")).trim()
        println("✓ Mensaje cifrado cargado (${mensajeCifrado.length} caracteres Base64)")

        // ========== PASO 4: DESCIFRAR ==========
        println("\n4. Descifrando mensaje con clave pública...")
        val mensajeDescifrado = descifrarConPublica(mensajeCifrado, clavePublicaAmigo)

        // ========== RESULTADO ==========
        println("\n" + "=".repeat(50))
        println("¡MENSAJE DESCIFRADO CON ÉXITO!")
        println("=".repeat(50))
        println("\n📨 Mensaje recibido de tu amigo:")
        println("   \"$mensajeDescifrado\"")
        println("\n" + "=".repeat(50))

    } catch (e: Exception) {
        println("\n❌ ERROR: ${e.message}")
        println("\nAsegúrate de que:")
        println("1. Los archivos 'publica_base64.txt' y 'mensaje_cifrado.txt' están en la misma carpeta")
        println("2. Los archivos no están vacíos")
        println("3. No modificaste los archivos")

        // Mostrar contenido actual de los archivos para debugging
        println("\n--- DEBUG INFO ---")
        try {
            val archivos = listOf("publica_base64.txt", "mensaje_cifrado.txt")
            for (archivo in archivos) {
                if (Files.exists(Paths.get(archivo))) {
                    println("$archivo existe, tamaño: ${Files.size(Paths.get(archivo))} bytes")
                } else {
                    println("$archivo NO existe")
                }
            }
        } catch (e2: Exception) {
            println("Error en debug: ${e2.message}")
        }
    }
}

// Función alternativa si recibes la clave en DER binario
fun cargarSiEsNecesario() {
    // Si tu amigo te envió 'publica.der' en lugar de Base64:
    if (Files.exists(Paths.get("publica.der"))) {
        val clave = cargarPublicaDesdeDer("publica.der")
        println("Clave cargada desde DER binario")
    }
}
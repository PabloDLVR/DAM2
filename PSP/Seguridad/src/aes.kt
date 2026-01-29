import java.security.Key
import javax.crypto.Cipher
import  javax.crypto.KeyGenerator
import java.util.Base64
import javax.crypto.spec.IvParameterSpec

fun generarAESclave(): Key = KeyGenerator.getInstance("AES").apply { init(128) }.generateKey()

fun cifrar(texto: String, clave: Key): ByteArray {
    val cifrado = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cifrado.init(Cipher.ENCRYPT_MODE, clave)
    val iv = cifrado.iv
    val textoCifrado = cifrado.doFinal(texto.toByteArray())
    return iv + textoCifrado
}

fun descifrar(dato: ByteArray, key: Key): String {
    val iv = dato.copyOfRange(0, 16)
    val textoCifrado = dato.copyOfRange(16, dato.size)
    val descifrado = Cipher.getInstance("AES/CBC/PKCS5Padding")
    descifrado.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
    return String(descifrado.doFinal(textoCifrado))
}

fun main() {
    val clave = generarAESclave()
    val textocifrado = cifrar("Hola Mundo", clave)
    val cifrado = Base64.getEncoder().encode(textocifrado).toString()
    val textodescifrado = descifrar(textocifrado, clave)
    println("Texto cifrado: $cifrado")
    println("Texto descifrado: $textodescifrado")
}
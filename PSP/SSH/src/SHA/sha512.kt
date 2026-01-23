package SHA

import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun sha512(texto: String): String {
    val sha = MessageDigest.getInstance("SHA-512")
    val claveBytes = sha.digest(texto.toByteArray(Charsets.UTF_8))
    return claveBytes.joinToString("") { "%02x".format(it) }
    /*val hexString = StringBuilder()
    for (b in claveBytes) {
        val hex = Integer.toHexString(0xff and b.toInt())
        if (hex.length == 1) hexString.append('0')
        hexString.append(hex)*/
}

fun hmac(key: String, text: String): String {
    val hmac = Mac.getInstance("HmacMD5")
    hmac.init(SecretKeySpec(key.toByteArray(), "HmacMD5"))
    return hmac.doFinal(text.toByteArray()).joinToString("") { "%02x".format(it) }

    /* val keyBytes = key.toByteArray(Charsets.UTF_8)
     val keySpec = javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA512")
     hmac.init(keySpec)
     val textBytes = text.toByteArray(Charsets.UTF_8)
     val hmacBytes = hmac.doFinal(textBytes)
     val hmacHex = hmacBytes.joinToString("") { "%02x".format(it) }
     println(hmacHex)*/
}

fun main() {
    println(sha512("Javier Moro de mierda"))
    println(hmac("1234", "Javier Moro de mierda"))
}

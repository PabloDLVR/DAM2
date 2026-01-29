import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.security.PrivateKey
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64

fun generarRSA(): KeyPair =
    KeyPairGenerator.getInstance("RSA").apply { initialize(2048) }.genKeyPair()

fun guardar(path: Path, bytes: ByteArray) {
    Files.createDirectories(path.parent)   // <-- crea carpetas si faltan
    Files.write(path, bytes)
}

fun cargarPublica(path: Path): PublicKey {
    val contenido = Files.readAllBytes(path)
    val keyFactory = KeyFactory.getInstance("RSA")
    val keySpec = X509EncodedKeySpec(contenido)
    return keyFactory.generatePublic(keySpec)
}

fun cargarPrivada(path: Path): PrivateKey {
    val contenido = Files.readAllBytes(path)
    val keyFactory = KeyFactory.getInstance("RSA")
    val keySpec = PKCS8EncodedKeySpec(contenido)
    return keyFactory.generatePrivate(keySpec)
}

fun main() {
    val parClaves = generarRSA()

    val dir = Paths.get(System.getProperty("user.dir"), "keys")
    val pubPath = dir.resolve("publicKey.der")
    val privPath = dir.resolve("privateKey.der")

    guardar(pubPath, parClaves.public.encoded)
    guardar(privPath, parClaves.private.encoded)

    val publica = cargarPublica(pubPath)
    val privada = cargarPrivada(privPath)

    println("Public key (Base64): ${Base64.getEncoder().encodeToString(publica.encoded)}")
    println("Private key (Base64): ${Base64.getEncoder().encodeToString(privada.encoded)}")
    println("Guardadas en: $dir")
}

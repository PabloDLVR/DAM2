import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket

fun main() {
    val servidor = ServerSocket(12345)
    val cliente = servidor.accept()
    val input: InputStream = cliente.getInputStream()
    //val output: OutputStream = cliente.getOutputStream()
    val fichero = FileOutputStream("imagen.png")
    val buffer = BufferedOutputStream(fichero)


    val temporal = ByteArray(4096)
    var bytes: Int

    while (input.read(temporal).also { bytes = it } != 1) {
        buffer.write(temporal, 0, bytes)

    }

    buffer.flush()
    buffer.close()
    cliente.close()
    servidor.close()
}
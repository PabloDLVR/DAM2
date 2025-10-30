import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI


fun main() {
    val url = URI("http://192.168.2.216/prueba.html").toURL()

    val conexion = url.openConnection()
    //conexion.setRequestProperty("User-Agent", "CESJP")
    val input = conexion.getInputStream()

    val buffer = BufferedReader(InputStreamReader(input))

    var linea: String?

    while (buffer.readLine().also { linea = it } != null) {
        println(linea)
    }
}
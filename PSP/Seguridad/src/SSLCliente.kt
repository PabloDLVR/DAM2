import java.io.DataInputStream
import java.io.DataOutputStream
import javax.net.ssl.SSLSocketFactory

fun main() {
    System.setProperty("javax.net.ssl.trustStore", "Almacen")
    System.setProperty("javax.net.ssl.trustStorePassword", "12345678")


    val socketfactory = SSLSocketFactory.getDefault() as SSLSocketFactory
    val cliente = socketfactory.createSocket("127.0.0.1", 2000)

    val entrada = DataInputStream(cliente.getInputStream())
    val salida = DataOutputStream(cliente.getOutputStream())

    salida.writeUTF("Hola mundo")
    println(entrada.readUTF())

    salida.close()
    entrada.close()
    cliente.close()
    salida.flush()

}
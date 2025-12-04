import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

fun main() {
    try {
        val socketMulticast = MulticastSocket(5000)
        val direccion = InetAddress.getByName("224.0.0.3")

        socketMulticast.joinGroup(direccion)

        val mensaje = "".toByteArray()
        val paquete = DatagramPacket(mensaje, mensaje.size, direccion, 5000)
        socketMulticast.send(paquete)

        while (true) {
            val bytes = ByteArray(1024)
            val paqueterecibido = DatagramPacket(bytes, bytes.size)
            socketMulticast.receive(paqueterecibido)
            println(String(paqueterecibido.data, paqueterecibido.offset, paqueterecibido.length))

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
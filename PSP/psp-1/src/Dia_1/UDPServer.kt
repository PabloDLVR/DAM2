package Dia_1

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.channels.DatagramChannel

fun main() {
    val datagrama = DatagramSocket()
    val direccion = InetAddress.getByName("localhhost")
    val paquete = DatagramPacket("hola".toByteArray(), "hola".toByteArray().size, direccion, 1234)

    datagrama.send(paquete)
}
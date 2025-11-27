package Dia_1

import java.net.DatagramPacket
import java.net.DatagramSocket

fun main() {
    val datagrama = DatagramSocket(1234)
    while (true) {
        val buffer = ByteArray(1024)
        val paqueterespuesta = DatagramPacket(buffer, buffer.size)
        datagrama.receive(paqueterespuesta)
        println(paqueterespuesta.address.hostAddress)
        val cadenafinal = String(paqueterespuesta.data, 0, paqueterespuesta.length)
        println(cadenafinal)
    }
}
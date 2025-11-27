package Dia_1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

class TCPServidor {
    fun startServer() = runBlocking {
        try {
            val server = ServerSocket(5000)
            while (true) {
                val cliente = server.accept()
                launch(context = Dispatchers.IO) {
                    val leer = BufferedReader(InputStreamReader(cliente.getInputStream()))
                    val escribir = PrintWriter(cliente.getOutputStream(), true)
                    var mensaje: String?
                    do {
                        mensaje = leer.readLine()
                        print(mensaje)
                        escribir.println("Mensaje recibido ")
                    } while (mensaje != null || mensaje != "fin")
                    leer.close()
                    escribir.close()
                    cliente.close()
                    server.close()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main() {
    val server = TCPServidor().startServer()
}
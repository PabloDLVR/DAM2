package Dia_1;

import java.io.ObjectOutputStream
import java.net.Socket

class ClienteO {
    fun enviarObjeto(persona: Persona) {
        val cliente = Socket("localhost", 1238)
        val out = ObjectOutputStream(cliente.getOutputStream())
        out.writeObject(persona)
        out.flush()
        out.close()
        cliente.close()
    }
}
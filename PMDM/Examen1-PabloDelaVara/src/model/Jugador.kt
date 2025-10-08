package model

class Jugador(id: Int, nombre: String, var posicion: String,var valor: Double) :
    Participante(nombre, ArrayList(), 0, 10_000_000.00, id) {




    override fun toString(): String {
        return "Jugador(id=$id, nombre='$nombre', posicion='$posicion', valor=$valor,)"
    }
}
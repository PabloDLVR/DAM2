package controller
import model.Administrador
import model.Jugador

class Fantasy(var jugadores: ArrayList<Jugador>, var fichajes: ArrayList<Jugador>) {



    fun empezarLiga(administrador: Administrador, clave: Int) {
        administrador.iniciarLiga(clave)

    }

    fun listarJugadoresValor() {
        jugadores.sortBy { it.valor }
        for (jugador in jugadores) {
            if (jugador.valor>=3_000_000){
                println(jugador)
            }else
            println("No hay jugadores con valor mayor o igual a 3.000.000")
        }
    }

    override fun toString(): String {
        return "Fantasy(conjuntoJugadores=$jugadores, fichajes=$fichajes)"
    }
}
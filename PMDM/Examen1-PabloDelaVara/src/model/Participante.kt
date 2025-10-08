package model

 open class Participante(
    var nombre: String,
    var plantilla: ArrayList<Jugador>,
    var puntos: Int,
    var presupuesto: Double,
    var id: Int
) {

    fun añadirParticipantes(jugador: Jugador) {
        if (jugador.valor <= presupuesto) {
            plantilla.add(jugador)
            presupuesto -= jugador.valor
        } else {
            println("No tienes suficiente presupuesto para fichar a este jugador.")
        }
    }

      fun listarPlantilla() {
        if (plantilla.isEmpty()) {
            println("No tienes jugadores en tu plantilla.")
        } else {
            println("Tu plantilla es:")
            for (jugador in plantilla) {
                println(jugador)
            }
        }
    }

    override fun toString(): String {
        return "Participante(nombre='$nombre', plantilla=$plantilla, puntos=$puntos, presupuesto=$presupuesto, id=$id)"
    }
}
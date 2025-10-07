package model

class Mutante(
    id: Int,
    nombre: String,
    nivel: Int,
    val armaBiologica: ArmaBiologica
) : Superheroe(id, nombre, nivel) {

    override fun tipo(): String = "Mutante"

    override fun toString(): String {
        return super.toString() + " | Tipo: Mutante | Arma: ${armaBiologica.nombre}"
    }
}

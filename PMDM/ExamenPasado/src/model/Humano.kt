package model

class Humano(
    id: Int,
    nombre: String,
    nivel: Int,
    val resistencia: Int,
    val armaTradicional: ArmaTradicional
) : Superheroe(id, nombre, nivel) {

    override fun tipo(): String = "Humano"

    override fun toString(): String {
        return super.toString() + " | Tipo: Humano | Resistencia: $resistencia | Arma: ${armaTradicional.nombre}"
    }
}

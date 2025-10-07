package model

class ArmaTradicional(
    id: Int,
    nombre: String,
    nivelPotencia: Int,
    nivelDanyo: Int,
    val peso: Double
) : Arma(id, nombre, nivelPotencia, nivelDanyo) {

    override fun tipo(): String = "Tradicional"

    override fun toString(): String {
        return super.toString() + " | Tipo: Tradicional | Peso: $peso"
    }
}

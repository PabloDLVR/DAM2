package model

class ArmaBiologica(
    id: Int,
    nombre: String,
    nivelPotencia: Int,
    nivelDanyo: Int,
    val descripcion: String
) : Arma(id, nombre, nivelPotencia, nivelDanyo) {

    override fun tipo(): String = "Biológica"

    override fun toString(): String {
        return super.toString() + " | Tipo: Biológica | Descripción: $descripcion"
    }
}

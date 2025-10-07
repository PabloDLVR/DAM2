package model

open class Arma(
    val id: Int,
    val nombre: String,
    val nivelPotencia: Int,
    val nivelDanyo: Int
) {
    open fun tipo(): String = "Arma"

    override fun toString(): String {
        return "ID: $id | Nombre: $nombre | Potencia: $nivelPotencia | Daño: $nivelDanyo"
    }
}

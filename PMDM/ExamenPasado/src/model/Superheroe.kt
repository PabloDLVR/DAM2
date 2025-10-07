package model

open class Superheroe(
    val id: Int,
    val nombre: String,
    val nivel: Int
) {
    open fun tipo(): String = "Superhéroe"

    override fun toString(): String {
        return "ID: $id | Nombre: $nombre | Nivel: $nivel"
    }
}

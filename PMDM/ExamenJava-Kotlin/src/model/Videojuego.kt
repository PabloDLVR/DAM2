package model

open class Videojuego(
    var titulo: String,
    var desarrollador: String,
    var anioLanzamiento: Int,
    var precio: Double,
    var clasificacionEdad: String
) {
    open fun calcularPrecioFinal(): Double = precio

    override fun toString(): String {
        return "Título: $titulo | Desarrollador: $desarrollador | Año: $anioLanzamiento | Precio: $precio € | Clasificación: $clasificacionEdad"
    }
}

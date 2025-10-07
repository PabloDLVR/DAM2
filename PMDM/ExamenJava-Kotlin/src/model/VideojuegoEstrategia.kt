package model

class VideojuegoEstrategia(
    titulo: String,
    desarrollador: String,
    anioLanzamiento: Int,
    precio: Double,
    clasificacionEdad: String,
    val complejidad: Int,
    val duracionPartida: Int,
    private val tamanoGB: Double
) : Videojuego(titulo, desarrollador, anioLanzamiento, precio, clasificacionEdad), Descargable {

    override fun calcularPrecioFinal(): Double {
        var final = precio
        final *= (1 + 0.03 * complejidad)
        return final
    }

    override fun calcularTiempoDescarga(velocidadInternet: Double): Double {
        return tamanoGB / velocidadInternet
    }

    override fun obtenerTamañoGB(): Double = tamanoGB

    override fun toString(): String {
        return super.toString() + " | Tipo: Estrategia | Complejidad: $complejidad | Duración: $duracionPartida min | Tamaño: ${tamanoGB}GB | Precio final: ${"%.2f".format(calcularPrecioFinal())}€"
    }
}

package model

class VideojuegoAccion(
    titulo: String,
    desarrollador: String,
    anioLanzamiento: Int,
    precio: Double,
    clasificacionEdad: String,
    val nivelViolencia: Int,
    val modoMultijugador: Boolean,
    private val tamanoGB: Double
) : Videojuego(titulo, desarrollador, anioLanzamiento, precio, clasificacionEdad), Descargable {

    override fun calcularPrecioFinal(): Double {
        var final = precio
        if (nivelViolencia > 3) final *= 1.05
        if (modoMultijugador) final *= 1.10
        return final
    }

    override fun calcularTiempoDescarga(velocidadInternet: Double): Double {
        // tiempo = tamaño (GB) / velocidad (GB/min)
        return tamanoGB / velocidadInternet
    }

    override fun obtenerTamañoGB(): Double = tamanoGB

    override fun toString(): String {
        return super.toString() + " | Tipo: Acción | Violencia: $nivelViolencia | Multijugador: $modoMultijugador | Tamaño: ${tamanoGB}GB | Precio final: ${"%.2f".format(calcularPrecioFinal())}€"
    }
}

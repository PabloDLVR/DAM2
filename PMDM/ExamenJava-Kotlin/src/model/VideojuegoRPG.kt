package model

class VideojuegoRPG(
    titulo: String,
    desarrollador: String,
    anioLanzamiento: Int,
    precio: Double,
    clasificacionEdad: String,
    val mundoAbierto: Boolean,
    val horasHistoriaPrincipal: Int,
    private val tamanoGB: Double
) : Videojuego(titulo, desarrollador, anioLanzamiento, precio, clasificacionEdad), Descargable {

    override fun calcularPrecioFinal(): Double {
        var final = precio
        if (mundoAbierto) final *= 1.15
        final *= (1 + 0.02 * (horasHistoriaPrincipal / 10))
        return final
    }

    override fun calcularTiempoDescarga(velocidadInternet: Double): Double {
        return tamanoGB / velocidadInternet
    }

    override fun obtenerTamañoGB(): Double = tamanoGB

    override fun toString(): String {
        return super.toString() + " | Tipo: RPG | Mundo Abierto: $mundoAbierto | Horas historia: $horasHistoriaPrincipal | Tamaño: ${tamanoGB}GB | Precio final: ${"%.2f".format(calcularPrecioFinal())}€"
    }
}

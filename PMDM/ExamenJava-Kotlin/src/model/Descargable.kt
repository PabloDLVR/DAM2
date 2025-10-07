package model

interface Descargable {
    fun calcularTiempoDescarga(velocidadInternet: Double): Double
    fun obtenerTamañoGB(): Double
}

package controller

import model.*

class PlataformaJuegos {
    private val juegos = mutableListOf<Videojuego>()

    // Añadir y eliminar
    fun agregarJuego(juego: Videojuego) {
        juegos.add(juego)
        println("✅ Juego añadido: ${juego.titulo}")
    }

    fun eliminarJuego(titulo: String) {
        val eliminado = juegos.removeIf { it.titulo.equals(titulo, ignoreCase = true) }
        if (eliminado) println("🗑 Juego eliminado.")
        else println("❌ No se encontró ese juego.")
    }

    // Mostrar juegos
    fun mostrarTodosOrdenados() {
        if (juegos.isEmpty()) {
            println("No hay juegos registrados.")
            return
        }
        juegos.sortedBy { it.calcularPrecioFinal() }.forEachIndexed { i, j ->
            println("${i + 1}. $j")
        }
    }

    fun filtrarPorClasificacion(clasif: String) {
        val filtrados = juegos.filter { it.clasificacionEdad.equals(clasif, ignoreCase = true) }
        if (filtrados.isEmpty()) println("No hay juegos con clasificación $clasif.")
        else filtrados.forEach { println(it) }
    }

    fun filtrarPorTipo(tipo: String) {
        val filtrados = when (tipo.lowercase()) {
            "accion" -> juegos.filterIsInstance<VideojuegoAccion>()
            "estrategia" -> juegos.filterIsInstance<VideojuegoEstrategia>()
            "rpg" -> juegos.filterIsInstance<VideojuegoRPG>()
            else -> emptyList()
        }

        if (filtrados.isEmpty()) println("No hay juegos del tipo $tipo.")
        else filtrados.forEach { println(it) }
    }

    fun calcularPrecioTotal(): Double {
        return juegos.sumOf { it.calcularPrecioFinal() }
    }

    fun verDetalles(indice: Int) {
        if (indice in 1..juegos.size)
            println(juegos[indice - 1])
        else println("Índice no válido.")
    }

    fun calcularTiempoDescarga(indice: Int, velocidad: Double) {
        if (indice !in 1..juegos.size) {
            println("Índice no válido.")
            return
        }
        val juego = juegos[indice - 1]
        if (juego is Descargable) {
            val tiempo = juego.calcularTiempoDescarga(velocidad)
            println("⏳ Tiempo estimado de descarga: ${"%.2f".format(tiempo)} minutos.")
        } else println("El juego no es descargable.")
    }

    fun getCantidad(): Int = juegos.size
}

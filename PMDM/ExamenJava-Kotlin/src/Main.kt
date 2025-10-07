import controller.PlataformaJuegos
import model.*

fun main() {
    val plataforma = PlataformaJuegos()

    // Juegos de ejemplo
    plataforma.agregarJuego(VideojuegoAccion("Call of Heroes", "EpicDev", 2021, 59.99, "18+", 4, true, 25.0))
    plataforma.agregarJuego(VideojuegoAccion("Battle City", "IronGames", 2019, 39.99, "16+", 2, false, 15.0))
    plataforma.agregarJuego(VideojuegoEstrategia("Civilizations X", "MindSoft", 2020, 49.99, "12+", 4, 60, 20.0))
    plataforma.agregarJuego(VideojuegoEstrategia("War Tactics", "BrainPlay", 2022, 44.99, "12+", 3, 45, 10.0))
    plataforma.agregarJuego(VideojuegoRPG("Dragon Kingdom", "FireWorks", 2023, 69.99, "16+", true, 50, 40.0))
    plataforma.agregarJuego(VideojuegoRPG("Dungeon Quest", "RetroStudio", 2018, 29.99, "12+", false, 25, 18.0))

    while (true) {
        println("\n🎮 === MENÚ PLATAFORMA DE JUEGOS ===")
        println("1. Ver todos los juegos")
        println("2. Filtrar por tipo (Acción, Estrategia, RPG)")
        println("3. Filtrar por clasificación de edad")
        println("4. Ver detalles de un juego (por índice)")
        println("5. Calcular tiempo de descarga")
        println("6. Calcular precio total de todos los juegos")
        println("7. Salir")
        print("Opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> plataforma.mostrarTodosOrdenados()
            2 -> {
                print("Tipo de juego: ")
                val tipo = readLine() ?: ""
                plataforma.filtrarPorTipo(tipo)
            }
            3 -> {
                print("Clasificación de edad: ")
                val clasif = readLine() ?: ""
                plataforma.filtrarPorClasificacion(clasif)
            }
            4 -> {
                if (plataforma.getCantidad() == 0) println("No hay juegos.")
                else {
                    print("Número de juego: ")
                    val num = readLine()?.toIntOrNull() ?: 0
                    plataforma.verDetalles(num)
                }
            }
            5 -> {
                if (plataforma.getCantidad() == 0) println("No hay juegos.")
                else {
                    print("Número de juego: ")
                    val num = readLine()?.toIntOrNull() ?: 0
                    print("Velocidad de internet (GB/min): ")
                    val vel = readLine()?.toDoubleOrNull() ?: 0.0
                    plataforma.calcularTiempoDescarga(num, vel)
                }
            }
            6 -> println("💰 Precio total: ${"%.2f".format(plataforma.calcularPrecioTotal())} €")
            7 -> {
                println("👋 Saliendo del sistema...")
                return
            }
            else -> println("❌ Opción no válida.")
        }
    }
}

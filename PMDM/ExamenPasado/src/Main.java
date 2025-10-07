import controller.ShieldController;
import static java.io.IO.print;
import static java.sql.DriverManager.println;
import static kotlin.io.ConsoleKt.readLine;

fun main() {
    val controller = ShieldController()

    while (true) {
        println("\n=== MENÚ S.H.I.E.L.D. ===")
        println("1. Crear superhéroe humano")
        println("2. Crear superhéroe mutante")
        println("3. Crear arma tradicional")
        println("4. Crear arma biológica")
        println("5. Listar superhéroes")
        println("6. Listar armas")
        println("7. Enfrentar superhéroes")
        println("8. Salir")
        print("Opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> controller.crearHumano()
            2 -> controller.crearMutante()
            3 -> controller.crearArmaTradicional()
            4 -> controller.crearArmaBiologica()
            5 -> controller.listarHeroes()
            6 -> controller.listarArmas()
            7 -> controller.enfrentar()
            8 -> {
                println("👋 Saliendo del sistema.")
            }
            else -> println("❌ Opción no válida.")
        }
    }
}

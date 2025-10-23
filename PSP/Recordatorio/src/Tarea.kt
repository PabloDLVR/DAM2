import java.util.concurrent.Executors

fun main() {
    val ejecutar = Executors.newSingleThreadExecutor()
    ejecutar.execute {
        println("Soy una tarea ejecutándose en un hilo separado.")
    }
    ejecutar.shutdown()
}
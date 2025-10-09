import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

fun descargar() {
    println("Iniciando descarga...")
    TimeUnit.SECONDS.sleep(2)
    println("Lo he descargado")
}

fun main() {
    val tiempo = measureTimeMillis {
        for (i in 1..10) {
            descargar()
        }
    }
    println("Tiempo de descarga: $tiempo ms")
}
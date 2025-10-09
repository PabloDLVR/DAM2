import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

fun descargar2() {
    println("Iniciando descarga...")
    TimeUnit.SECONDS.sleep(2)
    println("Lo he descargado")
}

fun main() {
    val tiempo = measureTimeMillis {
       val hilos= listOf(Thread{descargar2()},Thread{descargar2()},Thread{descargar2()},Thread{descargar2()},Thread{descargar2()},)
           Thread{descargar2()}
        hilos.forEach { it.start() }
        hilos.forEach { it.join() }
    }
    println("Tiempo de descarga: $tiempo ms")
}

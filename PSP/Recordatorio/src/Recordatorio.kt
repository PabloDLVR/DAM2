import java.util.Timer
import java.util.TimerTask

fun main() {
    val tiempo = Timer()
    val intervalo = 1L
    val segundos = intervalo * 60 * 1000

    val recordatorio = object : TimerTask() {
        override fun run() {
            println("¡Es hora de tomar un descanso y estirarte!")
        }
    }
    tiempo.scheduleAtFixedRate(recordatorio, 0, segundos)
    readLine()
}
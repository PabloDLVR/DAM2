//Tabla de multiplicar del 1 al 10 con hilos en orden
fun main() {
    val hilos = mutableListOf<Thread>()
    for (i in 1..10) {
        val hilo = Thread {
            for (j in 1..10) {
                println("$i x $j = ${i * j}")
                Thread.sleep(100) // Simula un pequeño retraso
            }
        }
        hilos.add(hilo)
    }
    for (hilo in hilos) {
        hilo.start()
        hilo.join() // Espera a que el hilo termine antes de iniciar el siguiente
    }
}


fun main() {
    val hilos = mutableListOf<Thread>()
    for (i in 1..10) {
        val hilo = Thread {
            for (j in 1..10) {
                println("$i x $j = ${i * j}")
                Thread.sleep(100)
            }
        }
        hilos.add(hilo)
    }
    for (hilo in hilos) {
        hilo.start()
        hilo.join()
    }
}

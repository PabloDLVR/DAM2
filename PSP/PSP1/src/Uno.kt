fun main() {

    val liebre = Thread {
        try {
            while (!Thread.currentThread().isInterrupted) {
                println("Hola desde la liebre")
                for (i in 1..5) {
                    println("Liebre avanzando: $i")
                    Thread.sleep(1000)
                }
            }
        } catch (InterruptedException: Exception) {
            println("Liebre fue abatida")
        }
        println("Liebre llegó a la meta")
    }


    val tortuga = Thread {
        println("Hola desde la tortuga")
        for (i in 1..5) {
            println("Tortuga avanzando: $i")
            Thread.sleep(2000)
        }
        println("Tortuga llegó a la meta")
    }
    liebre.start()
    tortuga.start()
    Thread.sleep(5000)
    liebre.interrupt()
    liebre.join()
    tortuga.join()
    println("La carrera ha terminado")
}
fun main() {
    val liebre = Thread {
        println("Hola desde la liebre")
        for (i in 1..5) {
            println("Liebre avanzando: $i")
            Thread.sleep(1000)
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
    liebre.join()
    tortuga.join()
    println("La carrera ha terminado")
}
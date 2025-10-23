import java.util.concurrent.CountDownLatch

fun main() {
    val interruptor = CountDownLatch(3)
    fun tarea() {
        println("Tarea iniciada en el hilo ${Thread.currentThread().name}")
        Thread.sleep(1000)
        println("Tarea completada en el hilo ${Thread.currentThread().name}")
        interruptor.countDown()
    }

    val hilo1 = Thread {
        tarea()
    }
    val hilo2 = Thread {
        tarea()
    }
    val hilo3 = Thread {
        tarea()
    }
    val hilo4 = Thread {
        tarea()
    }

    hilo1.start()
    hilo2.start()
    hilo3.start()
    hilo4.start()

    interruptor.await()
    println("Todas las tareas han completado su trabajo.")
}
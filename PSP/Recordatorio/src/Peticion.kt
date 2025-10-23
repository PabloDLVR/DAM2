// Main.kt
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

fun main() {
    // Configuración: modifica aquí los números, la URL y el retraso entre envíos.
    val numbers = listOf(10, 25, 38, 99)               // <-- cambiar números si quieres
    val jsonPayload = numbers.joinToString(prefix = "[", postfix = "]")
    val url = "http://192.168.2.216:80/"              // <-- ajusta puerto/path si hace falta
    val delayMs: Long = 1000                          // tiempo entre solicitudes (ms). Pon 0 para sin pausa

    val mediaType = "application/json; charset=utf-8".toMediaType()

    val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .callTimeout(30, TimeUnit.SECONDS)
        .build()

    // Hook para cerrar el cliente de forma ordenada al recibir Ctrl+C
    Runtime.getRuntime().addShutdownHook(Thread {
        println("\n[shutdown] Solicitud de parada recibida. Cerrando cliente...")
        try {
            client.dispatcher.executorService.shutdown()
            client.connectionPool.evictAll()
        } catch (e: Exception) {
            // ignorar
        }
        println("[shutdown] Cliente cerrado. Fin.")
    })

    println("Iniciando envío continuo a $url")
    println("Payload: $jsonPayload")
    println("Pulsa Ctrl+C para detener...")

    while (true) {
        try {
            val body = jsonPayload.toRequestBody(mediaType)
            val request = Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .build()

            client.newCall(request).execute().use { resp ->
                val responseBody = resp.body?.string()
                println("[${System.currentTimeMillis()}] Enviado -> código=${resp.code} ; body=${responseBody}")
            }

            if (delayMs > 0) Thread.sleep(delayMs)
        } catch (e: InterruptedException) {
            // hilo interrumpido -> salir limpiamente
            println("[info] Interrumpido. Saliendo...")
            break
        } catch (e: Exception) {
            // Mostrar error y continuar (timeout, conexión, etc.)
            println("[error] ${e::class.simpleName}: ${e.message}")
            // opcional: pequeña pausa tras error para evitar bucle muy rápido
            try { Thread.sleep(500) } catch (_: InterruptedException) { break }
        }
    }

    // cierre final (por si no se llegó al shutdown hook)
    try {
        client.dispatcher.executorService.shutdown()
        client.connectionPool.evictAll()
    } catch (_: Exception) { }
    println("Programa terminado.")
}



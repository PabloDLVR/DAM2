import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

fun main() {
    val client = OkHttpClient()

    print("Introduce la URL: ")
    val url = readln()

    print("¿Cuántas peticiones quieres enviar?: ")
    val cantidad = readln().toInt()

    println("\nEnviando $cantidad peticiones a: $url\n")

    for (i in 1..cantidad) {
        val request = Request.Builder()
            .url(url)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    println("[$i] Error: Código ${response.code}")
                } else {
                    println("[$i] Petición exitosa - Código: ${response.code}")
                }
            }
        } catch (e: IOException) {
            println("[$i] Error en la petición: ${e.message}")
        }
    }



    println("\n✅ Todas las peticiones han sido enviadas.")
}



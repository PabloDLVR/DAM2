import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.io.ByteArrayOutputStream
import java.net.URLEncoder

fun main() {
    // Inicializamos un cliente HTTP para hacer la petición de audio externa
   // val httpClient = HttpClient(CIO)

    val server = embeddedServer(Netty, port = 8080) {
        routing {

            // 1. Endpoint Original (JSON)
            get("/saludo") {
                val jsonString = "{\"mensaje\": \"¡Hola! Tu API está funcionando.\"}"
                call.respondText(jsonString, ContentType.Application.Json)
            }

            // 2. Endpoint: Generar código QR
            // Uso: http://localhost:8080/qr?texto=HolaMundo
            get("/qr") {
                // Obtenemos el texto de la URL, si no hay, usamos uno por defecto
                val texto = call.request.queryParameters["texto"] ?: "Sin texto"

                try {
                    // Generamos el QR en memoria
                    val qrWriter = QRCodeWriter()
                    val bitMatrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, 300, 300)

                    // Convertimos la matriz a una imagen PNG (bytes)
                    val stream = ByteArrayOutputStream()
                    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", stream)
                    val byteArray = stream.toByteArray()

                    // Respondemos con la imagen
                    call.respondBytes(byteArray, ContentType.Image.PNG)
                } catch (e: Exception) {
                    call.respondText("Error generando QR: ${e.message}", status = HttpStatusCode.InternalServerError)
                }
            }

            // 3. Endpoint: Texto a Audio (TTS)
            // Uso: http://localhost:8080/audio?texto=Hola
            get("/audio") {
                val texto = call.request.queryParameters["texto"] ?: "Hola mundo"

                try {
                    // Usamos una API de TTS gratuita y simple
                    val ttsUrl = "https://api.voicerss.org/?key=demo&hl=es-es&src=${URLEncoder.encode(texto, "UTF-8")}"

                    val audioBytes = java.net.URL(ttsUrl).readBytes()
                    call.respondBytes(audioBytes, ContentType.Audio.MPEG)
                } catch (e: Exception) {
                    call.respondText("Error: ${e.message}")
                }
            }
        }
    }
    server.start(wait = true)
}
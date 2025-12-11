import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.Serializable

@Serializable
data class Saludo(val mensaje: String)

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }

        install(CORS) {
            anyHost() // Para desarrollo - en producción especifica los hosts
        }

        routing {
            get("/saludo") {
                val saludo = Saludo("¡Hola! Este es un mensaje desde tu API en Kotlin.")
                call.respond(saludo)
            }
        }
    }
    server.start(wait = true)
}
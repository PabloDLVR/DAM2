import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

fun main() {
    val input= BufferedReader(InputStreamReader(System.`in`))
   /* val array = (input.readLine().split(",").toTypedArray())
    print(array.joinToString(",")+"\n")*/

    var saldo = 0

    while (true) {
        val mensaje = input.readLine() ?: break

        when {
            mensaje.contains("Bienvenido") -> {
                println("Hola, gracias.")
            }
            mensaje.contains("ingresar") -> {
                val cantidad = 200
                saldo += cantidad
                println("He ingresado $cantidad€. Saldo actual: $saldo")
            }
            mensaje.contains("retirar") -> {
                val cantidad = 50
                saldo -= cantidad
                println("He retirado $cantidad€. Saldo actual: $saldo")
            }
            mensaje.contains("consultar") -> {
                println("Su saldo actual es: $saldo €")
            }
            mensaje.contains("Gracias") -> {
                println("Adiós, hasta luego.")
                break
            }
            else -> {
                println("No entendí el mensaje.")
            }
        }
    }
}
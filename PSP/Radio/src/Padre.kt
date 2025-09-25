import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter

fun main() {
    val proceso = ProcessBuilder("C:\\Users\\pablo\\.jdks\\openjdk-24.0.2+12-54\\bin\\java.exe\" \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2025.2.1\\lib\\idea_rt.jar=63802\" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\\Users\\pablo\\Documents\\GitHub\\DAM2\\PSP\\Radio\\out\\production\\Radio;C:\\Users\\pablo\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\2.2.0\\kotlin-stdlib-2.2.0.jar;C:\\Users\\pablo\\.m2\\repository\\org\\jetbrains\\annotations\\13.0\\annotations-13.0.jar HijoKt")
    val procesoArranque = proceso.start()

    val array = arrayOf("Buenos días, cuánto dinero desea ingresar?")

    val out = OutputStreamWriter(procesoArranque.outputStream)
    /*out.write(array.joinToString(",")+"\n")
    out.flush()


    /*print(array.joinToString(","))
    out.write(array.joinToString(","))*/
     */

    val input = procesoArranque.inputStream
    val leer = BufferedReader(InputStreamReader(input))
    //print(leer.readLine())


    val mensajes = listOf(
        "Bienvenido al cajero automático.",
        "¿Cuánto dinero desea ingresar?",
        "¿Cuánto dinero desea retirar?",
        "Gracias por usar el cajero."
    )

    var saldoFinal = ""

    for (msg in mensajes) {
        println("Padre -> $msg")
        out.write(msg + "\n")
        out.flush()

        val respuesta = leer.readLine()
        println("Hijo -> $respuesta")
    }
}
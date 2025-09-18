import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    print("Mensaje recibido del procso hijo: Hola dede el proceso padre!\n" +
            "")
    var linea = BufferedReader(InputStreamReader(ProcessBuilder("C:\\Users\\pablo\\.jdks\\openjdk-24.0.2+12-54\\bin\\java.exe\" \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2025.2.1\\lib\\idea_rt.jar=54155\" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\\Users\\pablo\\Documents\\GitHub\\DAM2\\PSP\\untitled1\\out\\production\\Tema1;C:\\Users\\pablo\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\2.2.0\\kotlin-stdlib-2.2.0.jar;C:\\Users\\pablo\\.m2\\repository\\org\\jetbrains\\annotations\\13.0\\annotations-13.0.jar LLamarKt").start().inputStream)).readLine()
    println(linea.uppercase())


   /* val nombre = "arp -a"
    val proceso = ProcessBuilder("cmd.exe", "/c", "arp -a")
    val procesoTemporal = proceso.start()
    val lector =
        BufferedReader(InputStreamReader(procesoTemporal.inputStream)) //TODO Para tener una conexion en tiempo real con el proceso y leer el proceso
    var linea: String?
    proceso.start() */

    //proceso.redirectInput(ProcessBuilder.Redirect.INHERIT) TODO Escribir el proceso principal
    //proceso.redirectError(ProcessBuilder.Redirect.INHERIT) TODO Corregir el error del proceso Leer el proceso
    //print(procesoTemporal.inputStream.bufferedReader().readText()) TODO Para leer el proceso
    //print(procesoTemporal.isAlive) TODO Para saber si esta vivo el proceso
    //procesoTemporal.waitFor()

    /*while (lector.readText().also { linea = it } != null) {
        print(linea)
    }*/


}

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val proceso = ProcessBuilder("C:\\Users\\pablo\\.jdks\\openjdk-24.0.2+12-54\\bin\\java.exe\"\"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2025.2.1\\lib\\idea_rt.jar=55947\" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\\Users\\pablo\\Documents\\GitHub\\DAM2\\PSP\\untitled1\\out\\production\\Tema1;C:\\Users\\pablo\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\2.2.0\\kotlin-stdlib-2.2.0.jar;C:\\Users\\pablo\\.m2\\repository\\org\\jetbrains\\annotations\\13.0\\annotations-13.0.jar HijoKt")
    val procesoEjecutado = proceso.start()

    val output= OutputStreamWriter(procesoEjecutado.outputStream)
    output.write("Hola hijo\n")
    output.flush()

    val input = BufferedReader(InputStreamReader(procesoEjecutado.inputStream)).readLine()
    print(input)
}
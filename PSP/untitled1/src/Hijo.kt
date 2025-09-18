import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter

fun main() {
    val input = BufferedReader(InputStreamReader(System.`in`))
    val output = OutputStreamWriter(System.out)
    output.write(input.readLine().uppercase())
    output.flush()
}
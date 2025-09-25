import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

fun main() {
    val input= BufferedReader(InputStreamReader(System.`in`))
    val array = (input.readLine().split(",").toTypedArray())

    val numero: Int


    print(array.joinToString(",")+"\n")
}
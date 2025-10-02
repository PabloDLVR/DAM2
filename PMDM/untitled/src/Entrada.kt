import model.Asalariado
import model.Persona
import model.Trabajador

fun main() {
    /*var persona= Persona("Pablo","De la Vara", "12345678A",123,"paf@gmail.com")
    persona.mostrarDatos()*/
    val asalariado = Asalariado("Pablo", "De la Vara", "12345678A", 28000.00, 12, 21.0)
    asalariado.mostrarDatos()
}
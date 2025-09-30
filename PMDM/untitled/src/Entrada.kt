import model.Persona
import model.Trabajador

fun main() {
    /*var persona= Persona("Pablo","De la Vara", "12345678A",123,"paf@gmail.com")
    persona.mostrarDatos()*/

    var trabajador = Trabajador("Pablo", "De la Vara", "12345678A", 12300, 12,
        true, 911111, "ddd@gmail.com")
    trabajador.mostrarDatos()
}
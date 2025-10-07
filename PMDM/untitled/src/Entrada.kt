import model.Asalariado
import model.Autonomo
import model.Directivo
import model.Jefes
import model.Persona
import model.Sindicato
import model.Trabajador

fun main() {
    /*var persona= Persona("Pablo","De la Vara", "12345678A",123,"paf@gmail.com")
    persona.mostrarDatos()*/
    val asalariado = Asalariado("Pablo", "De la Vara", "12345678A", 29000.00, 12, 21.0)
    val autonomo = Autonomo("Pablo", "De la Vara", "12345678A", 28000.00, true, 12)
    val jefes = Jefes("Pablo", "De la Vara", "12345678A", 22000.00, 5)
    val directivo = Directivo("PabloDr", "De la Vara", "12345678A", 123, "kmkm@gmail.com", 6)
    val trabajadores = arrayListOf<Persona>(asalariado, autonomo, jefes, directivo)
   /* trabajadores.forEach {
        //Si eres detipo trabajador -> Calcules el salario
        if (it is Trabajador){
            println("Calculando salario del trabajador: ${it.calcularSalarioNeto()}")
    }
        it.mostrarDatos()
    }*/

    //Calcular beneficios

    trabajadores.forEach {
        if (it is Sindicato){
            it.calcularBeneficios()
        }
    }

}
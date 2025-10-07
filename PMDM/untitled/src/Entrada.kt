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


    //Para poder bajar sueldos, un jefe solo bajara sueldos
    //a los trabajadores que no son jefes
    //la cantidad de salario que puede bajar es del 10%
    //En caso de ser un directiv, le podre bajar el sueldo a todos los trabajadores
    //incluido a los jefes. Un 20% a los Asalariados/Autonomos
    //un 10% a los jefes


    trabajadores.forEach {
        if (it is Sindicato) {
            it.bajarsueldos(trabajadores.filter { it !is Directivo } as ArrayList<Trabajador>)
        }
    }

}
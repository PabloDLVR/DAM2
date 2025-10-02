package model

class Jefes(nombre: String, apellido: String, dni: String, salario: Double, var nivelResponsablidad: Int) :
    Trabajador(nombre, apellido, dni, salario) {
    override fun calcularSalarioNeto(): Double {
        nivelResponsablidad = (1..10).random()
        if (nivelResponsablidad >= 3) {
            salario + (salario * 1.3)
        } else if (nivelResponsablidad < 3){
             salario - (salario * 1.3)
        }
        return salario
    }

    override fun mostrarDatos() {
        super.mostrarDatos()
    }
}
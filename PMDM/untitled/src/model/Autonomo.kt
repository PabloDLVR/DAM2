package model

class Autonomo(nombre: String, apellido: String, dni: String, salario: Double, seguro: Boolean, var cuotas: Int) :
    Trabajador(nombre, apellido, dni, salario) {
    override fun calcularSalarioNeto(): Double {
        return salario - (cuotas * 12)
    }

    override fun mostrarDatos() {
        super.mostrarDatos()
    }
}
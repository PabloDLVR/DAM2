package model

abstract class Trabajador(nombre: String, apellido: String, dni: String, var salario: Double) :
    Persona(nombre, apellido, dni) {
    var seguro: Boolean = false

    constructor(
        nombre: String,
        apellido: String,
        dni: String,
        salario: Double,
        nPagas: Int,
        seguro: Boolean,
        telefono: Int,
        correo: String
    ) : this(nombre, apellido, dni, salario) {
        this.seguro = seguro
        this.telefono = telefono
        this.correo = correo
    }

    override fun mostrarDatos() {
        super.mostrarDatos()
        println("Salario: $salario")
        println("Tiene seguro: $seguro")
    }

    abstract fun calcularSalarioNeto(): Double
}
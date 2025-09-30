package model

class Trabajador(nombre: String, apellido: String, dni: String, var salario: Int, var nPagas: Int) :
    Persona(nombre, apellido, dni) {
    var seguro: Boolean = false

    constructor(
        nombre: String,
        apellido: String,
        dni: String,
        salario: Int,
        nPagas: Int,
        seguro: Boolean,
        telefono: Int,
        correo: String
    ) : this(nombre, apellido, dni, salario, nPagas) {
        this.seguro = seguro
        this.telefono = telefono
        this.correo = correo
    }

    override fun mostrarDatos() {
        super.mostrarDatos()
        println("Salario: $salario")
        println("Número de pagas: $nPagas")
        println("Tiene seguro: $seguro")
    }
}
package model

abstract class Persona(var nombre: String, var apellido: String, var dni: String) {
    //nombre, apellido, dni(obligatorio)
    //opcional telefono y correo
    var telefono: Int? = null
    var correo: String? = null


    constructor(nombre: String, apellido: String, dni: String, telefono: Int, correo: String) : this(
        nombre,
        apellido,
        dni
    ) {
        this.telefono = telefono
        this.correo = correo
    }

    open fun mostrarDatos() {
        println("El nombre es: $nombre")
        println("El apellido es: $apellido")
        println("El DNI es: $dni")
        println("El telefono es: ${telefono ?: "No se ha especificado"}")
        println("El correo es: ${correo ?: "No se ha especificado"}")
    }
}
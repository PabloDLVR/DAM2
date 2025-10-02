package model

class Jefes(nombre: String, apellido: String, dni: String, salario: Double, var nivelResponsablidad: Int) :
    Trabajador(nombre, apellido, dni, salario) {
}
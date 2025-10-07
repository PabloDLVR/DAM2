package model

import kotlin.io.println

class Jefes(nombre: String, apellido: String, dni: String, salario: Double, var nivelResponsablidad: Int) :
    Trabajador(nombre, apellido, dni, salario), Sindicato {
    override fun calcularSalarioNeto(): Double {
        nivelResponsablidad = (1..10).random()
        if (nivelResponsablidad >= 3) {
            salario + (salario * 1.3)
        } else if (nivelResponsablidad < 3) {
            salario - (salario * 1.3)
        }
        return salario
    }

    override fun mostrarDatos() {
        super.mostrarDatos()
        println("nResponsabilidad: ${nivelResponsablidad}")
    }

    override fun bajarsueldos(lista: ArrayList<Trabajador>): Boolean {
        println("Procedes a bajar los sueldos, eres jefe y puedes hacerlo")
        return true
    }

    override fun calcularBeneficios(): Double {
        println("Como jefe vas a calcular los beneficios de la empresa")
        return 0.0
    }


}
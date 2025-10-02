package model

import kotlin.random.Random

class Asalariado(nombre: String, apellido: String, dni: String, salario: Double, var nPagas: Int, var irpf: Double) :
    Trabajador(nombre, apellido, dni, salario) {
    override fun calcularSalarioNeto(): Double {
        // (10000 + 0-20) - 10000
        //salario total - l que te quitan de irpf
        return salario - (salario * irpf)
    }

    //Un asalariado puede pedir aumento de sueldo, se genera un aleatorio entre 1-10, en caso de generar un num <5 no se le sube el salario
    //En caso de genera un numero >5 se le sube un 10% el salario

    fun aumentoSueldo() {
        val aleatorio: Int = (1..10).random()
        if (aleatorio >= 5) {
            salario + (salario * 0.10)
        } else {
            println("TUVISTE SUERTE HIJO DE PUTA")
        }

    }

    override fun mostrarDatos() {
        super.mostrarDatos()
        println("nPagas = ${nPagas}")
        println("IRPF = ${irpf}")
    }


}
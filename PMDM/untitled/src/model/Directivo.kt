package model

class Directivo(nombre: String, apellido: String, dni: String, telefono: Int, correo: String, var nAcciones: Int) :
    Persona(nombre, apellido, dni, telefono, correo), Sindicato {
    override fun mostrarDatos() {
        super.mostrarDatos()
        println("nAcciones = ${nAcciones}")
    }

    fun venderAcciones(acciones: Int) {
        if (acciones > nAcciones) {
            println("No puedo vender tantas acciones")
        } else {
            nAcciones -= acciones
            println("Nº acciones actualizadas")
        }
    }

    override fun bajarsueldos(lista: ArrayList<Trabajador>): Boolean {
        println("Procedes a bajar los sueldos, eres directivo y puedes hacerlo")
        lista.forEach {
            if (it is Jefes){
                it.salario = it.salario*0.90
            }else{
                it.salario = it.salario*0.80
            }
        }
        return true
    }

    override fun calcularBeneficios(): Double {
        println("Como directivo vas a calcular el beneficio de la empresa")
        return 0.0
    }


}
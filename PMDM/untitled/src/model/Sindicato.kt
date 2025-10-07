package model

interface Sindicato {
    fun bajarsueldos(lista: ArrayList<Trabajador>): Boolean
    fun calcularBeneficios(): Double
}
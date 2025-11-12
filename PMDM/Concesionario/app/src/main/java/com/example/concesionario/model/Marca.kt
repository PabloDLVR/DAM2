package com.example.concesionario.model

class Marca {
    var nombre: String
    var numeroModelos: Int

    constructor(nombre: String, numeroModelos: Int) {
        this.nombre = nombre
        this.numeroModelos = numeroModelos
    }

    override fun toString(): String {
        return nombre
    }
}
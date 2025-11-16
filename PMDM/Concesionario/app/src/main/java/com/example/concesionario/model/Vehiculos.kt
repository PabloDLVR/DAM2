package com.example.concesionario.model

class Vehiculos {
    var marca: Marca
    var modelo: String
    var precio: Double
    var imagen: Int
    var cv: Int
    var color: String

    constructor(
        marca: Marca,
        modelo: String,
        precio: Double,
        imagen: Int,
        cv: Int,
        color: String
    ) {
        this.marca = marca
        this.modelo = modelo
        this.precio = precio
        this.imagen = imagen
        this.cv = cv
        this.color = color
    }

}
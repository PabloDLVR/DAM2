package model

import java.io.InputStreamReader

// Contructor PRIMARIO
class Producto(
    var id: Int,
    var nombre: String = "SIN NOMBRE",
    var precio: Double = 10.00,
    var descropcion: String = "SIN DESCRIPCION"
) {
    /*
    var nombre: String? = null
    var descripcion: String? = null

    // Contructor SECUNDARIO se basan en los primarios


    constructor(id: Int, precio: Double, nombre: String, descripcion: String?) : this(id, precio) {
        this.nombre = nombre
    }
    constructor(id: Int, precio: Double, nombre: String?, descripcion: String) : this(id, precio) {
        this.descripcion = descripcion
    }
    constructor(id: Int, precio: Double, nombre: String, descripcion: String) : this(id, precio) {
        this.nombre = nombre
        this.descripcion = descripcion
    }
    */

    // Metodo para poder mostrar todos los datos del producto concreto
    //si ni hay nombre aoarece sin nombre
    //si ni hay descripcion aoarece sin descripcion

    fun verDatos(){
        println("El ID: ${id}\nNombre: ${nombre}\nCon precio: ${precio}\nDescripcion: ${descropcion}\n")
    }

    fun crearTienda(){

    }
}
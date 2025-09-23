package model

class crearTienda(var nombreTienda: String) {
    var caja: Double = 0.0
    lateinit var almacen: Array<Producto?>

    // init -> ejecua si o si desues del constructor (primario o secundario)
    init {
        almacen = arrayOfNulls(6)
    }

    //un metodo para poder mostrar todos los productos del almacen. En caso de
    //tner todos los huecos disponibles (vacios) sacar un aviso

    fun mostrarProductos() {
        
        println("Nombre: {$nombreTienda}")
    }

}
package model

class crearTienda(var nombreTienda: String) {
    var caja: Double = 0.0
    var almacen: Array<Producto?>

    // init -> ejecua si o si desues del constructor (primario o secundario)
    init {
        almacen = arrayOfNulls(6)
    }

    //un metodo para poder mostrar todos los productos del almacen. En caso de
    //tner todos los huecos disponibles (vacios) sacar un aviso

    fun mostrarAlmacen() {
        var nulos = 0; almacen.forEach {
            it
            it?.verDatos() ?: nulos++
        }
        if (nulos == almacen.size)
            println("No hay productos en el almacen")
    }

    //agregar un elemento al almacen. En caso de no tener hueco
    //disponible se mostrara un aviso

    fun agregarElemento(producto: Producto) {
        for (item in 0..almacen.size - 1) {
            if (item == null) {
                almacen[item] = producto
                return
            }
        }
        println("El almacen está completo")
    }

    //Vamos a vender un producto, para ello se solicita el id del producto a vender
    //en caso de estar en el almacen: Se elimina de este, su precio se suma a la caja
    //si se vende se da aviso de prodcuto vendido y se da el valor de la caja
    //si no se encuentra se da el aviso de que no esta en el almacen.

    fun venderProducto(producto: Producto, id: Int) {
        readLine()
        for (item in 0..almacen.size - 1) {
            if (almacen[item] != null && almacen[item]?.id == id) {
                caja += almacen[item]!!.precio
                almacen[item]=null
                return
            }
        }
        println("El ID no esta en la lista")
    }


}
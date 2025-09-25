package model

class Cliente(var id: Int, var nombre: String) {
    var carrito: ArrayList<Producto>
    var factura: Double = 0.0

    init {
        carrito = ArrayList()
    }

    fun agregarProductoCarrito(producto: Producto) {
        carrito.add(producto)
        println("Producto agregado al carrito correctamente")
    }

    //Mostrar todos los elementos del carrito
    fun mostrarCarrito(): Unit {
        if (carrito.isEmpty()) {
            println("El carrito está vacío")
        } else {
            println("El carrito contienes esto: ")
            carrito.forEach { it.verDatos() }
        }
    }

    fun verPoicion(posicion: Int): Unit {
        if (posicion > carrito.size - 1) {
            println("ID fuera de rango")
        } else {
        }
        carrito[posicion].verDatos()
    }

    //TODO Eliminar un producto del carrito
    //TODO En caso de no estar el id en el carrito -> aviso de qe no se encuentra
    //TODO En caso de si estar en el carrito y solo existir uno, lo elimina
    //TODO En caso de si estar en el carrito y existir varios -> Confirmacion de eliminar 1

    fun eliminarProducto(id: Int): Unit {
        val filtroId = carrito.filter {
            it
            return@filter it.id == id
        }
        println("El numero de resultado es: ${filtroId.size}")
        if (filtroId.isEmpty()) {
            println("No se encuentra el ID")
        } else if(filtroId.size==1){
            carrito.remove(filtroId.first())
            println("Borrado correctamente")
        }else{
            println("Se han encontrado varias coincidencias, Cual quieres borrar? (1 para el primero / n para todos")
            val opcion: String = readln()
            if (opcion.equals("1",true)){
                carrito.remove(filtroId.first())
            }else if (opcion.equals("n",true)){
                carrito.removeAll(filtroId.toSet())
            }
        }
    }

    //TODO hacer un metodo para calcular la factura del cliente, cuando un cliente pide la factura se muestra por consola y vacia el carrito

    fun facturaCliente(){
       if (carrito.isEmpty()){
           println("El carrito esta vacío")
       }else{}
        carrito.forEach { it
        factura+=it.precio
        }
        println("Debes un total de $factura")
        carrito.clear()
    }
}
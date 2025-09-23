import model.Producto
fun main() {
    var camiseta: Producto = Producto(id = 1, precio = 1.50)
    var zapatilla = Producto(2)
    var pantalon = Producto(3, "Pantalones", 30.00)
    var gorra = Producto(4, precio = 20.00, descropcion = "Gorra molona")
    var cartera = Producto(5, "cartera cuero", 10.00, "Cartera para guardar la pasta")

    val listaProductos: Array<Producto?> = arrayOf(camiseta, zapatilla, pantalon, gorra, cartera)

    val listaProductosVacio: Array<Producto?> =arrayOfNulls(5)

    //listaProductos[listaProductos.size-1].verDatos() // size porque aqui no esta lengch -1
    //listaProductos.last().verDatos() // Otra forma de mostrar la ultima posicion

    listaProductos.last()?.precio = 20.00
    listaProductos[2] = null
    listaProductos.last()?.verDatos()
    listaProductos.forEach { it
        it?.verDatos()
    }

    for (i in listaProductos){
        i?.verDatos()
    }

    listaProductos.forEachIndexed { index, producto ->
        println("Mostrando producto en posicion: $index")
        producto?.verDatos()
    }

    //TODO vamos a crear una tienda. Para ello crar la clase necesaria donde pueda asignar:
    //TODO 1- Nombre de la tienda (obligatorio)
    //TODO 2- Almacen: sitio donde se guardan los productos
    //TODO Tiene un tamaño fijo de 6
    //TODO 3- Caja: se guardar la caja cuando se venda un prodcuto



}
import model.Categoria
import model.Producto
import model.crearTienda

fun main() {
    var camiseta: Producto = Producto(id = 1, precio = 1.50,categoria = Categoria.Ropa)
    var zapatilla = Producto(2, categoria = Categoria.Ropa)
    var pantalon = Producto(3, "Pantalones", Categoria.Ropa,30.00)
    var gorra = Producto(4, precio = 20.00, descropcion = "Gorra molona", categoria = Categoria.Ropa)
    var cartera = Producto(5, "cartera cuero", categoria = Categoria.Ropa, 25.00)
    var telefono = Producto(6,"Iphone 17", categoria = Categoria.Tecnologia,899.00)

    val listaProductos: Array<Producto?> = arrayOf(camiseta, zapatilla, pantalon, gorra, cartera,telefono)

    val listaProductosVacio: Array<Producto?> =arrayOfNulls(6)

    //listaProductos[listaProductos.size-1].verDatos() // size porque aqui no esta lengch -1
    //listaProductos.last().verDatos() // Otra forma de mostrar la ultima posicion

   /* listaProductos.last()?.precio = 20.00
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
    }*/

    // vamos a crear una tienda. Para ello crar la clase necesaria donde pueda asignar:
    // 1- Nombre de la tienda (obligatorio)
    // 2- Almacen: sitio donde se guardan los productos
    // Tiene un tamaño fijo de 6
    // 3- Caja: se guardar la caja cuando se venda un prodcuto


    var tienda:crearTienda= crearTienda("Tienda de todo un poco")
    tienda.almacen=listaProductos
    //tienda.mostrarAlmacen()
    tienda.buscarProductosCategoria(Categoria.Ropa)



}
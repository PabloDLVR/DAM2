import controller.Controlador

fun main() {
    val controlador = Controlador()
    var opcion: Int

    do {
        println("===== MENÚ TIENDA =====")
        println("1. Añadir producto")
        println("2. Mostrar productos")
        println("3. Registrar cliente")
        println("4. Mostrar clientes")
        println("5. Añadir producto al carrito")
        println("6. Ver carrito de un cliente")
        println("7. Generar factura de un cliente")
        println("0. Salir")
        print("Seleccione una opción: ")

        opcion = readLine()?.toIntOrNull() ?: -1

        when (opcion) {
            1 -> controlador.agregarProducto()
            2 -> controlador.mostrarProductos()
            3 -> controlador.registrarCliente()
            4 -> controlador.mostrarClientes()
            5 -> controlador.comprarProducto()
            6 -> controlador.verCarritoCliente()
            7 -> controlador.generarFactura()
            0 -> println("Saliendo del programa...")
            else -> println("Opción no válida, intente de nuevo.\n")
        }

    } while (opcion != 0)
}

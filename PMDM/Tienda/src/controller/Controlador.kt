package controller

import model.Categoria
import model.Cliente
import model.Producto

class Controlador {

    private val productos = ArrayList<Producto>()
    private val clientes = ArrayList<Cliente>()
    private var productoIdCounter = 1
    private var clienteIdCounter = 1

    fun agregarProducto() {
        println("=== Añadir producto ===")
        print("Nombre: ")
        val nombre = readLine() ?: "SIN NOMBRE"

        print("Precio: ")
        val precio = readLine()?.toDoubleOrNull() ?: 0.0

        println("Categorías disponibles:")
        Categoria.values().forEachIndexed { i, cat -> println("${i + 1}. $cat") }
        print("Seleccione una categoría (número): ")
        val categoriaIndex = readLine()?.toIntOrNull()?.minus(1)
        val categoriaSeleccionada = if (categoriaIndex != null && categoriaIndex in Categoria.values().indices) {
            Categoria.values()[categoriaIndex]
        } else {
            Categoria.Generica
        }

        print("Descripción: ")
        val descripcion = readLine() ?: "SIN DESCRIPCION"

        val producto = Producto(productoIdCounter++, nombre, categoriaSeleccionada, precio, descripcion)
        productos.add(producto)
        println("✅ Producto añadido correctamente.\n")
    }

    fun mostrarProductos() {
        println("=== Lista de productos ===")
        if (productos.isEmpty()) {
            println("No hay productos registrados.\n")
            return
        }
        productos.forEach { it.verDatos() }
    }

    fun registrarCliente() {
        println("=== Registrar cliente ===")
        print("Nombre del cliente: ")
        val nombre = readLine() ?: "Sin nombre"
        val cliente = Cliente(clienteIdCounter++, nombre)
        clientes.add(cliente)
        println("✅ Cliente registrado correctamente.\n")
    }

    fun mostrarClientes() {
        println("=== Lista de clientes ===")
        if (clientes.isEmpty()) {
            println("No hay clientes registrados.\n")
            return
        }
        clientes.forEachIndexed { i, c ->
            println("${i + 1}. [ID ${c.id}] ${c.nombre}")
        }
        println()
    }

    fun comprarProducto() {
        if (productos.isEmpty() || clientes.isEmpty()) {
            println("⚠️ Debe haber al menos un cliente y un producto para realizar una compra.\n")
            return
        }

        println("Seleccione el cliente:")
        clientes.forEachIndexed { i, c -> println("${i + 1}. ${c.nombre}") }
        val clienteIndex = readLine()?.toIntOrNull()?.minus(1)
        if (clienteIndex == null || clienteIndex !in clientes.indices) {
            println("Selección inválida.\n")
            return
        }

        println("Seleccione el producto:")
        productos.forEachIndexed { i, p -> println("${i + 1}. ${p.nombre}") }
        val productoIndex = readLine()?.toIntOrNull()?.minus(1)
        if (productoIndex == null || productoIndex !in productos.indices) {
            println("Selección inválida.\n")
            return
        }

        val cliente = clientes[clienteIndex]
        val producto = productos[productoIndex]

        cliente.agregarProductoCarrito(producto)
        println("✅ ${cliente.nombre} ha añadido ${producto.nombre} al carrito.\n")
    }

    fun verCarritoCliente() {
        if (clientes.isEmpty()) {
            println("No hay clientes registrados.\n")
            return
        }

        println("Seleccione el cliente para ver su carrito:")
        clientes.forEachIndexed { i, c -> println("${i + 1}. ${c.nombre}") }
        val clienteIndex = readLine()?.toIntOrNull()?.minus(1)
        if (clienteIndex == null || clienteIndex !in clientes.indices) {
            println("Selección inválida.\n")
            return
        }

        clientes[clienteIndex].mostrarCarrito()
    }

    fun generarFactura() {
        if (clientes.isEmpty()) {
            println("No hay clientes registrados.\n")
            return
        }

        println("Seleccione el cliente para generar su factura:")
        clientes.forEachIndexed { i, c -> println("${i + 1}. ${c.nombre}") }
        val clienteIndex = readLine()?.toIntOrNull()?.minus(1)
        if (clienteIndex == null || clienteIndex !in clientes.indices) {
            println("Selección inválida.\n")
            return
        }

        clientes[clienteIndex].facturaCliente()
        println()
    }
}

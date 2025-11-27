package com.example.listas.model

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.listas.databinding.SecondActivityBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: SecondActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val producto = intent.getSerializableExtra("Producto") as? Producto

        if (producto != null){
            mostrarDetalles(producto)
        }else{
            finish()
        }

        binding.botonAtrasDetalle.setOnClickListener {
            finish()
        }

    }

    private fun mostrarDetalles(producto: Producto){
        binding.nombreDetalle.text = producto.nombre
        binding.precioDetalle.text ="Precio: ${producto.precio}"
        binding.stockDetalle.text = "Stock: ${producto.stock}"
        binding.categoriaDetalle.text = "Categoría: ${producto.categoria}"
        binding.descripcionDetalle.text = "Descripcion: ${producto.descripcion}"


    }
}
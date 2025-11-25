package com.example.listas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listas.adapter.AdapteProducto
import com.example.listas.databinding.ActivityMainBinding
import com.example.listas.model.Producto

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapteProducto: AdapteProducto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val lista: ArrayList<Producto> = arrayListOf(
            Producto(
                1, "Nombre",
                12, 10.0, "Descripcion", "Categoria",
                "Imagen"
            )
        )
        adapteProducto = AdapteProducto(lista, this)

        if (resources.configuration.orientation == 1) {
            binding.recyclerProductos.layoutManager =
                LinearLayoutManager(
                    this, LinearLayoutManager.VERTICAL,
                    false
                )
        } else {
            binding.recyclerProductos.layoutManager =
                GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
            binding.recyclerProductos.adapter = adapteProducto
        }
    }
}
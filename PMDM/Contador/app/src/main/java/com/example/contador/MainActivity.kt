package com.example.contador

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contador.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding
    var contador: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contador= savedInstanceState?.getInt("contador") ?: 0
        binding.contadorTexto.text = contador.toString()
        acciones()
    }

    private fun acciones() {
        binding.botonPositivo.setOnClickListener(this)
        binding.botonNegativo.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("contador", contador)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.botonPositivo.id->{
                contador++
            }
            binding.botonNegativo.id->{
                contador--
            }
        }
        binding.contadorTexto.text = contador.toString()
    }
}

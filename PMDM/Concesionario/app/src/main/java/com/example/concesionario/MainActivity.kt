package com.example.concesionario

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.concesionario.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.example.concesionario.model.Marca

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var listaMarcas: ArrayList<Marca>
    lateinit var adapterMarcas: ArrayAdapter<Marca>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        instancias()
        initGUI()
        acciones()
        enableEdgeToEdge()
        setContentView(binding.root)
    }

    private fun initGUI() {
        binding.spinnerMarcas.adapter = adapterMarcas
        adapterMarcas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    }


    private fun instancias() {
        listaMarcas = arrayListOf(Marca("Seat", 0), Marca("Renault", 0), Marca("Citroen", 0))

        adapterMarcas = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaMarcas)
    }


    private fun acciones() {
        binding.botonFiltrar.setOnClickListener {
            Snackbar.make(
                it, "La seleccion del spinner de vehiculos es: " +
                        "${binding.spinnerMarcas.adapter.getItem(binding.spinnerMarcas.selectedItemPosition)}"
                , Snackbar.LENGTH_LONG
            ).show()
        }
        binding.spinnerVehiculos.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {

        when (parent?.id) {
            binding.spinnerVehiculos.id -> {
                Snackbar.make(
                    view!!, "La seleccion del spinner de vehiculos es: " +
                            "${binding.spinnerVehiculos.selectedItem}", Snackbar.LENGTH_LONG
                ).show()
            }
        }

    }
}
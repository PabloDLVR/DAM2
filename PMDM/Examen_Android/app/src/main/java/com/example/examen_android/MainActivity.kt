package com.example.examen_android

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.examen_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var ArrayAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        ArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        setContentView(binding.root)
        acciones()
        initGUI()
        //instancias()
    }

    /*private fun instancias() {
         TODO("Not yet implemented")
     }*/

    private fun initGUI() {
        val opciones = listOf("Gasolina", "Diésel", "Híbrido", "Eléctrico")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGasoilina.adapter = adapter

    }

    private fun acciones() {
        binding.spinnerGasoilina.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position).toString()
                if (selectedOption == "Híbrido" || selectedOption == "Eléctrico") {
                    binding.editAOMatricula.setText("2018")
                    binding.editAOMatricula.isEnabled = false
                } else {
                    binding.editAOMatricula.isEnabled = true
                }
                if (selectedOption == "Híbrido") {
                    binding.editAutonomia.isEnabled = true
                } else {
                    binding.editAutonomia.isEnabled = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.btnEnviar.setOnClickListener {
            binding.editAOMatricula.setText("")
            binding.editNombre.setText("")
            binding.editApellido.setText("")
            binding.editMatricula.setText("")
            binding.editAutonomia.setText("")
            binding.spinnerGasoilina.setSelection(0)

        }

    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
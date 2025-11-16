package com.example.calculadoraimc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var etPeso: EditText
    private lateinit var etAltura: EditText
    private lateinit var rgSexo: RadioGroup
    private lateinit var btnCalcular: Button
    private lateinit var rootLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupButton()
    }

    private fun initViews() {
        etPeso = findViewById(R.id.etPeso)
        etAltura = findViewById(R.id.etAltura)
        rgSexo = findViewById(R.id.rgSexo)
        btnCalcular = findViewById(R.id.btnCalcular)
        rootLayout = findViewById(android.R.id.content)
    }

    private fun setupButton() {
        btnCalcular.setOnClickListener {
            if (validarCampos()) {
                calcularIMC()
            }
        }
    }

    private fun validarCampos(): Boolean {
        val peso = etPeso.text.toString().trim()
        val altura = etAltura.text.toString().trim()
        val sexoSeleccionado = rgSexo.checkedRadioButtonId

        if (peso.isEmpty()) {
            mostrarError("Por favor, introduce tu peso")
            return false
        }

        if (altura.isEmpty()) {
            mostrarError("Por favor, introduce tu altura")
            return false
        }

        if (sexoSeleccionado == -1) {
            mostrarError("Por favor, selecciona tu sexo")
            return false
        }

        val pesoNum = peso.toDouble()
        val alturaNum = altura.toDouble()

        if (pesoNum <= 0) {
            mostrarError("El peso debe ser mayor que 0")
            return false
        }

        if (alturaNum <= 0) {
            mostrarError("La altura debe ser mayor que 0")
            return false
        }

        return true
    }

    private fun calcularIMC() {
        val peso = etPeso.text.toString().toDouble()
        val altura = etAltura.text.toString().toDouble()
        val sexo = when (rgSexo.checkedRadioButtonId) {
            R.id.rbHombre -> "H"
            R.id.rbMujer -> "M"
            else -> "H"
        }

        val imc = peso / (altura * altura)

        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("IMC", imc)
            putExtra("SEXO", sexo)
        }
        startActivity(intent)
    }

    private fun mostrarError(mensaje: String) {
        Snackbar.make(rootLayout, mensaje, Snackbar.LENGTH_LONG).show()
    }
}
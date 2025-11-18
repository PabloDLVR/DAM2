package com.example.calculadoraimc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.text.DecimalFormat

class SecondActivity : AppCompatActivity() {

    private lateinit var tvIMC: TextView
    private lateinit var tvEstado: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initViews()
        mostrarResultado()
        setupButton()
    }

    private fun initViews() {
        tvIMC = findViewById(R.id.tvIMC)
        tvEstado = findViewById(R.id.tvEstado)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        btnVolver = findViewById(R.id.btnVolver)
    }

    private fun mostrarResultado() {
        val imc = intent.getDoubleExtra("IMC", 0.0)
        val sexo = intent.getStringExtra("SEXO") ?: "H"

        val df = DecimalFormat("#.##")
        tvIMC.text = df.format(imc)

        val resultado = obtenerEstadoFisico(imc, sexo)
        tvEstado.text = resultado.estado
        tvDescripcion.text = resultado.descripcion
        tvEstado.setBackgroundColor(ContextCompat.getColor(this, resultado.color))
    }

    private fun obtenerEstadoFisico(imc: Double, sexo: String): ResultadoIMC {
        return when (sexo) {
            "H" -> obtenerEstadoHombre(imc)
            "M" -> obtenerEstadoMujer(imc)
            else -> obtenerEstadoHombre(imc)
        }
    }

    private fun obtenerEstadoHombre(imc: Double): ResultadoIMC {
        return when {
            imc < 18.5 -> ResultadoIMC(
                "Bajo peso",
                "Debes ganar peso de forma saludable",
                R.color.orange
            )
            imc < 25 -> ResultadoIMC(
                "Peso normal",
                "Mantén tus hábitos saludables",
                R.color.green
            )
            imc < 30 -> ResultadoIMC(
                "Sobrepeso",
                "Considera hacer más ejercicio",
                R.color.yellow
            )
            imc < 35 -> ResultadoIMC(
                "Obesidad Clase I",
                "Consulta con un especialista",
                R.color.orange
            )
            imc < 40 -> ResultadoIMC(
                "Obesidad Clase II",
                "Es importante buscar ayuda médica",
                R.color.red
            )
            else -> ResultadoIMC(
                "Obesidad Clase III",
                "Busca atención médica inmediata",
                R.color.dark_red
            )
        }
    }

    private fun obtenerEstadoMujer(imc: Double): ResultadoIMC {
        return when {
            imc < 16.5 -> ResultadoIMC(
                "Bajo peso",
                "Debes ganar peso de forma saludable",
                R.color.orange
            )
            imc < 23 -> ResultadoIMC(
                "Peso normal",
                "Mantén tus hábitos saludables",
                R.color.green
            )
            imc < 26 -> ResultadoIMC(
                "Sobrepeso",
                "Considera hacer más ejercicio",
                R.color.yellow
            )
            imc < 31 -> ResultadoIMC(
                "Obesidad Clase I",
                "Consulta con un especialista",
                R.color.orange
            )
            imc < 34 -> ResultadoIMC(
                "Obesidad Clase II",
                "Es importante buscar ayuda médica",
                R.color.red
            )
            else -> ResultadoIMC(
                "Obesidad Clase III",
                "Busca atención médica inmediata",
                R.color.dark_red
            )
        }
    }

    private fun setupButton() {
        btnVolver.setOnClickListener {
            finish()
        }
    }

    data class ResultadoIMC(
        val estado: String,
        val descripcion: String,
        val color: Int
    )
}
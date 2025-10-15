package com.example.holamundo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v("ciclo_vida", "Ejecutando metodo onCreate")

    }

    override fun onRestart() {
        super.onRestart()
        Log.v("ciclo_vida", "Ejecutando metodo onRestart")

    }

    override fun onStart() {
        super.onStart()
        Log.v("ciclo_vida", "Ejecutando metodo onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.v("ciclo_vida", "Ejecutando metodo onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.v("ciclo_vida", "Ejecutando metodo onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v("ciclo_vida", "Ejecutando metodo onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("ciclo_vida", "Ejecutando metodo onDestroy")
    }


}
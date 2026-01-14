package com.example.tema4

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tema4.databinding.ActivityMainBinding
import com.example.tema4.model.User
import com.google.gson.Gson
import org.json.JSONArray
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        realizarPeticionJSON()
    }

    private fun realizarPeticionJSON() {
        val url = "https://dummyjson.com/users"
        //1. Realizar la peticion de forma correcta
        val peticionJSON: JsonObjectRequest = JsonObjectRequest(
            url,
            {
                val gson = Gson()
                val usersArray: JSONArray = it.getJSONArray("users")
                for (i in 0 .. usersArray.length()-1){
                    val userJSON = usersArray.getJSONObject(i)
                    val user = gson.fromJson(userJSON.toString(), User::class.java)
                }

                Log.v("Conexion", it.toString())
                //Log.v("Conexion", "Los datos se obtienen de forma correcta")
            },
            {
                Log.v("Conexion", "Error en la conexion")
            },
        )
        //2. Añado la peticion a la pila de Volley
        Volley.newRequestQueue(this)

    }
}

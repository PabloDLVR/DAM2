package com.example.gps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var etNombre: EditText
    private lateinit var tvPosicion: TextView
    private lateinit var btnObtenerPosicion: Button
    private lateinit var btnEnviar: Button

    private var ultimaLat: Double? = null
    private var ultimaLng: Double? = null

    // ⚠️ CAMBIA ESTO SEGÚN DONDE CORRA EL SERVIDOR
    // Emulador Android Studio -> 10.0.2.2
    private val SERVER_URL = "http://10.0.2.2:8000/posicion"

    private val httpClient = OkHttpClient()

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permisos ->
            val fineGranted = permisos[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseGranted = permisos[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineGranted || coarseGranted) {
                obtenerUltimaPosicion()
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        etNombre = findViewById(R.id.etNombre)
        tvPosicion = findViewById(R.id.tvPosicion)
        btnObtenerPosicion = findViewById(R.id.btnObtenerPosicion)
        btnEnviar = findViewById(R.id.btnEnviar)

        btnObtenerPosicion.setOnClickListener {
            comprobarPermisosYObtener()
        }

        btnEnviar.setOnClickListener {
            enviarAlServidor()
        }
    }

    private fun comprobarPermisosYObtener() {
        val fine = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarse = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED) {
            obtenerUltimaPosicion()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun obtenerUltimaPosicion() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    ultimaLat = location.latitude
                    ultimaLng = location.longitude
                    tvPosicion.text = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                } else {
                    tvPosicion.text = "No se pudo obtener la ubicación"
                }
            }
            .addOnFailureListener {
                tvPosicion.text = "Error al obtener la ubicación: ${it.message}"
            }
    }

    private fun enviarAlServidor() {
        val nombre = etNombre.text.toString().trim()
        val lat = ultimaLat
        val lng = ultimaLng

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Escribe un nombre de usuario", Toast.LENGTH_SHORT).show()
            return
        }

        if (lat == null || lng == null) {
            Toast.makeText(this, "Primero obtén la posición", Toast.LENGTH_SHORT).show()
            return
        }

        val timestamp = System.currentTimeMillis()

        // Construimos el JSON
        val json = JSONObject().apply {
            put("nombre", nombre)
            put("lat", lat)
            put("lng", lng)
            put("timestamp", timestamp)
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = json.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(SERVER_URL)
            .post(body)
            .build()

        // Petición en un hilo aparte
        Thread {
            try {
                val response = httpClient.newCall(request).execute()
                val exito = response.isSuccessful
                val mensaje = response.body?.string() ?: ""

                runOnUiThread {
                    if (exito) {
                        Toast.makeText(this, "Enviado correctamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Error del servidor: $mensaje",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Error al conectar: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }.start()
    }
}

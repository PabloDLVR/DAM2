package com.example.tuapp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import java.util.Locale
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import android.graphics.Color
import com.example.apis.R
import io.ktor.client.features.json.serializer.KotlinxSerializer

@Serializable
data class Saludo(val mensaje: String)

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var textView: TextView
    private lateinit var btnObtenerSaludo: Button
    private lateinit var btnReproducirAudio: Button
    private lateinit var btnGenerarQR: Button
    private lateinit var imageViewQR: ImageView

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var httpClient: HttpClient

    private var mensajeActual: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        textView = findViewById(R.id.textView)
        btnObtenerSaludo = findViewById(R.id.btnObtenerSaludo)
        btnReproducirAudio = findViewById(R.id.btnReproducirAudio)
        btnGenerarQR = findViewById(R.id.btnGenerarQR)
        imageViewQR = findViewById(R.id.imageViewQR)

        // Inicializar TextToSpeech
        textToSpeech = TextToSpeech(this, this)

        // Inicializar cliente HTTP
        httpClient = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }

        // Configurar listeners de botones
        btnObtenerSaludo.setOnClickListener {
            obtenerSaludoDesdeServidor()
        }

        btnReproducirAudio.setOnClickListener {
            reproducirAudio()
        }

        btnGenerarQR.setOnClickListener {
            generarQR()
        }
    }

    private fun obtenerSaludoDesdeServidor() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Cambia la IP por la de tu servidor local o dirección del servidor
                val saludo: Saludo = httpClient.get("http://10.0.2.2:8080/saludo")

                withContext(Dispatchers.Main) {
                    mensajeActual = saludo.mensaje
                    textView.text = mensajeActual
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    textView.text = "Error: ${e.message}"
                }
            }
        }
    }

    private fun reproducirAudio() {
        if (mensajeActual.isNotEmpty()) {
            textToSpeech.speak(mensajeActual, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun generarQR() {
        if (mensajeActual.isNotEmpty()) {
            val bitmap = generateQRCode(mensajeActual, 400, 400)
            imageViewQR.setImageBitmap(bitmap)
        }
    }

    private fun generateQRCode(text: String, width: Int, height: Int): Bitmap {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            text,
            BarcodeFormat.QR_CODE,
            width,
            height
        )

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        return bitmap
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("es", "ES"))

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Idioma no soportado
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        httpClient.close()
        textToSpeech.shutdown()
    }
}
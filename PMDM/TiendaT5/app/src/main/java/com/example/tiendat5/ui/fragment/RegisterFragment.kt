package com.example.tiendat5.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiendat5.databinding.FragmentRegistroBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * Fragmento que gestiona la interfaz de registro de nuevos usuarios.
 * Permite al usuario ingresar sus datos personales (nombre, apellido, correo, contraseña, edad)
 * y los envía para ser validados y almacenados en el sistema.
 */
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegistroBinding
    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener argumentos del Bundle si existen
        arguments?.let {
            val correo = it.getString("correo", "")
            val pass = it.getString("pass", "")

            if (correo.isNotEmpty()) {
                binding.editCorreo.setText(correo)
            }
            if (pass.isNotEmpty()) {
                binding.editPassword.setText(pass)
            }
        }
    }

}
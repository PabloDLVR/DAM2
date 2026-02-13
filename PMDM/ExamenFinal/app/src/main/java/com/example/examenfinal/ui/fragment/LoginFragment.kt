package com.example.examenfinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.examenfinal.R
import com.example.examenfinal.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            iniciarSesion(email, password)
        }
    }

    private fun iniciarSesion(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
               Snackbar.make(binding.btnLogin, "Bienvenido de nuevo", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_launchesFragment)

            }
            .addOnFailureListener {

                crearCuenta(email, password)
            }
    }

    private fun crearCuenta(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Snackbar.make(binding.root, "Cuenta creada correctamente", Snackbar.LENGTH_LONG)
                    .setAction("Iniciar Sesión") {
                        iniciarSesionDirecto(email, password)
                    }
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun iniciarSesionDirecto(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_launchesFragment)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al iniciar sesión", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.tiendat5.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tiendat5.R
import com.example.tiendat5.data.DataSet
import com.example.tiendat5.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * Fragmento que gestiona la interfaz de inicio de sesión de usuarios.
 * Permite a usuarios existentes ingresar sus credenciales (correo y contraseña) para acceder
 * a la aplicación. Si las credenciales son incorrectas, ofrece la opción de registrarse como
 * nuevo usuario. También proporciona un botón directo al formulario de registro.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FramentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FramentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.btnLogin.setOnClickListener {

            auth.signInWithEmailAndPassword(
                binding.editCorreoLogin.text.toString(),
                binding.editPassLogin.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    Snackbar
                        .make(binding.root, "Usuario no encontrado", Snackbar.LENGTH_SHORT)
                        .setAction(
                            "Quieres registrarlo",
                            {
                                val bundle = Bundle()
                                bundle.putString("correo", binding.editCorreoLogin.text.toString())
                                bundle.putString("pass", binding.editPassLogin.text.toString())
                                findNavController().navigate(
                                    R.id.action_loginFragment_to_registerFragment,
                                    bundle
                                )
                            })
                        .show()
                }
            }
            // auth -> no existe
            // auth -> si existe


            /*
            val loginUSer = DataSet.loginUser(
                binding.editCorreoLogin.text.toString(),
                binding.editPassLogin.text.toString()
            )
            if (loginUSer != null) {
                // nombre
                val bundle = Bundle()
                bundle.putSerializable("user", loginUSer)
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment,bundle)
            } else {
                Snackbar
                    .make(binding.root, "Usuario no encontrado", Snackbar.LENGTH_SHORT)
                    .setAction(
                        "Quieres registrarlo",
                        {
                            val bundle = Bundle()
                            bundle.putString("correo", binding.editCorreoLogin.text.toString())
                            bundle.putString("pass", binding.editPassLogin.text.toString())
                            findNavController().navigate(
                                R.id.action_loginFragment_to_registerFragment,
                                bundle
                            )
                        })
                    .show()
            }

             */
        }
        binding.btnRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}
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

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            if (DataSet.loginUser(
                    binding.editCorreoLogin.text.toString(),
                    binding.editPassLogin.text.toString()
                )
            ) {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            } else {
                val args = Bundle().apply {
                    putString("correo", binding.editCorreoLogin.text.toString())
                    putString("pass", binding.editPassLogin.text.toString())
                }
                Snackbar.make(binding.root, "Usuario no encontrado", Snackbar.LENGTH_SHORT)
                    .setAction("Quieres registrarlo") {
                        findNavController().navigate(R.id.action_loginFragment_to_registerFragment, args)
                    }
                    .show()
            }
        }
        binding.btnRegistro.setOnClickListener {
            val args = Bundle().apply {
                putString("correo", binding.editCorreoLogin.text.toString())
                putString("pass", binding.editPassLogin.text.toString())
            }
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment, args)
        }
    }

}
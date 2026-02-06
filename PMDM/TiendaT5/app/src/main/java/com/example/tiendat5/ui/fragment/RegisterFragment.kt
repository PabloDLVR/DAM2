package com.example.tiendat5.ui.fragment

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tiendat5.databinding.FragmentRegistroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * Fragmento que gestiona la interfaz de registro de nuevos usuarios.
 * Permite al usuario ingresar sus datos personales (nombre, apellido, correo, contraseña, edad)
 * y los envía para ser validados y almacenados en el sistema.
 */
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegistroBinding
    private lateinit var adaterEdad: ArrayAdapter<Int>
    private lateinit var listaEdades: ArrayList<Int>
    private lateinit var auth: FirebaseAuth
    private var nombre: String? = null
    private var pass: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
        listaEdades = ArrayList()
        for (i in 16..90) {
            listaEdades.add(i)
        }
        adaterEdad = ArrayAdapter(context, R.layout.simple_spinner_item, listaEdades)
        adaterEdad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nombre = this.arguments?.getString("correo")
        pass = this.arguments?.getString("pass")

        // ponerlo en su sitio

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        /*binding.editCorreoRegistro.setText(nombre ?: "")
        binding.editPassRegistro.setText(pass ?: "")*/
        if (nombre != null || pass != null) {
            binding.editCorreoRegistro.setText(nombre)
            binding.editPassRegistro.setText(pass)
        }
        binding.spinnerEdadRegistro.adapter = adaterEdad
        binding.btnRegistro.setOnClickListener {
            // registra un usuario
            auth.createUserWithEmailAndPassword(
                binding.editCorreoRegistro.text.toString(),
                binding.editPassRegistro.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Snackbar.make(binding.root, "Usuario creado con existo", Snackbar.LENGTH_SHORT)
                        .show()
                    val usuarioLogeado = auth.currentUser!!.uid
                    val bundle = Bundle()
                    // bundle.putString("uid",usuarioLogeado)
                    findNavController().navigate(R.id.action_registerFragment_to_diagloRegistroOK)
                } else {
                    findNavController().navigate(R.id.action_registerFragment_to_diagloRegistroFAIL)
                }
            }
            /*
            if (DataSet.registerUser(
                    User(
                        nombre = binding.editNombreRegistro.text.toString(),
                        apellido = binding.editApellidoRegistro.text.toString(),
                        correo = binding.editCorreoRegistro.text.toString(),
                        pass = binding.editPassRegistro.text.toString(),
                        edad = binding.spinnerEdadRegistro.selectedItem.toString().toInt()
                    )
                )
            ) {

                findNavController().navigate(R.id.action_registerFragment_to_diagloRegistroOK)
            } else {
                findNavController().navigate(R.id.action_registerFragment_to_diagloRegistroFAIL)
            }

             */
        }
        super.onResume()
    }

}
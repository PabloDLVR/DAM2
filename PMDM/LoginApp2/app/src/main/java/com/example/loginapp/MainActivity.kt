package com.example.loginapp

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        acciones()
    }

    private fun acciones() {
        binding.botonLogin.setOnClickListener(this)
        binding.botonGoogle.setOnClickListener(this)
        binding.botonFacebook.setOnClickListener(this)
        binding.botonGithub.setOnClickListener(this)
        binding.checkRecordar.setOnCheckedChangeListener(this)
        binding.spinnerPerfil.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.botonFacebook.visibility=View.INVISIBLE
                binding.botonGithub.visibility=View.INVISIBLE
                binding.botonGoogle.visibility=View.INVISIBLE
                when(position){
                    0 -> {
                        binding.botonGoogle.visibility=View.VISIBLE
                    }
                    1 -> {
                        binding.botonGithub.visibility=View.VISIBLE
                    }
                    2 -> {
                        binding.botonFacebook.visibility=View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.botonLogin.id -> {

            }

            binding.botonGoogle.id -> {
                // Handle Google login button click
            }

            binding.botonFacebook.id -> {
                // Handle Facebook login button click
            }

            binding.botonGithub.id -> {
                // Handle GitHub login button click
            }
        }
    }


    override fun onCheckedChanged(
        buttonView: CompoundButton,
        isChecked: Boolean
    ) {

    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}




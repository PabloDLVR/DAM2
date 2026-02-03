package com.example.tiendat5.data

import com.example.tiendat5.model.User

/**
 * Clase que gestiona el almacenamiento en memoria de usuarios registrados.
 * Proporciona métodos para registrar nuevos usuarios y validar credenciales de login.
 * Actúa como repositorio de datos temporal (no persiste en base de datos).
 */
class DataSet {
    companion object {
        val listaUsuarios: ArrayList<User> = ArrayList()
        fun registerUser(user: User): Boolean {
            if (listaUsuarios.find { it.correo == user.correo } != null) {
                return false
            } else {
                this.listaUsuarios.add(user)
                return true
            }
        }

        fun loginUser(correo: String, pass: String): Boolean {
            return listaUsuarios
                .find { it.correo == correo && it.pass == pass } != null;
        }
    }
}
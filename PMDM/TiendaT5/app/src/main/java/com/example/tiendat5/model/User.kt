package com.example.tiendat5.model

import java.io.Serializable

/**
 * Modelo de datos que representa un usuario en la aplicación.
 * Contiene la información personal y de autenticación del usuario.
 * Implementa Serializable para permitir pasar objetos User entre actividades y fragments.
 */
class User(
    var nombre: String,
    var apellido: String,
    var correo: String,
    var pass: String,
    var edad: Int
): Serializable
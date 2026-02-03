package com.example.tiendat5.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Diálogo que se muestra cuando el registro de un usuario es exitoso.
 * Presenta un mensaje de confirmación y ofrece al usuario la opción de iniciar sesión
 * inmediatamente con sus nuevas credenciales.
 */
class DiagloRegistroOK : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Registro correcto")
        builder.setMessage("Quieres iniciar sesion con el usuario")
        builder.setPositiveButton("SI", { _, _ -> })
        builder.setNegativeButton("NO", { _, _ -> })
        return builder.create()
    }
}
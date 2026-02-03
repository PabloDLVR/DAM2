package com.example.tiendat5.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Diálogo que se muestra cuando el registro de un usuario falla.
 * Notifica al usuario que no se pudo crear la cuenta con los datos proporcionados
 * (probablemente porque el correo ya existe o hay un error en el servidor).
 */
class DiagloRegistroFAIL : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Registro incorrecto")
        builder.setMessage("No se ha podido crear el usuario")
        return builder.create()
    }
}
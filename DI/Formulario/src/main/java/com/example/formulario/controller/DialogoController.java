package com.example.formulario.controller;

import com.example.formulario.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogoController implements Initializable {


    @FXML
    private Label textoNombre,textoMail,textoLocalizacion,textoEdad,textoGenero;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUsuario(Usuario usuario){
        textoNombre.setText(usuario.getNombre());
        textoMail.setText(usuario.getCorreo());
        textoLocalizacion.setText(usuario.getLocalizacion());
        textoEdad.setText(String.valueOf(usuario.getEdad()));
        textoGenero.setText(usuario.getGenero());
    }

}

package com.example.appinicial;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button botonPulsar;
    @FXML
    private Label labelSaludo;
    @FXML
    private TextField textfieldNombre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Ejecutado directamente en la asociacion de la stage

        acciones();

    }

    private void acciones() {
        botonPulsar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Has pulsado el boton");
            }
        });
    }
}

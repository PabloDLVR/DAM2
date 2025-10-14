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
    private Button botonPulsar,botonVaciar;
    @FXML
    private Label labelSaludo;
    @FXML
    private TextField textfieldNombre;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Ejecutado directamente en la asociacion de la stage

        //acciones();
        //vaciar();

    }

    private void vaciar(){
        botonVaciar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textfieldNombre.setText("");
                labelSaludo.setText("");
            }
        });
    }

    private void acciones() {
        botonPulsar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //El metodo ejecutar cuando se pulsa el boton
                String nombre = textfieldNombre.getText();
                if (nombre.isBlank()) {
                    System.out.println("El nombre no puede estar vacio");
                } else {
                    labelSaludo.setText("Enhorabuena " + nombre + ", has completado el primer ejercicio de JavaFX");
                }

                //al pulsar el boton se capture el texto que esta en el textfield y se lo asignamos al label

                //el mensaje de "Enhorabuena Pablo, has completado el primer ejercicio de JavaFX"
            }
        });
    }

    class manejoAcciones implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            System.out.println("Has pulsado el boton");
            //que boton se ha pulado
            String nombre = textfieldNombre.getText();
            if (nombre.isBlank()) {
                System.out.println("El nombre no puede estar vacio");
            } else {
                labelSaludo.setText("Enhorabuena " + nombre + ", has completado el primer ejercicio de JavaFX");
            }
        }
    }
}

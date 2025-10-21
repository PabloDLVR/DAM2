package com.example.appinicial;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button botonPulsar, botonVaciar;
    @FXML
    private Label labelSaludo;
    @FXML
    private TextField textfieldNombre;

    private DropShadow sombra;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Ejecutado directamente en la asociacion de la stage
        instancias();
        initGUI();
        acciones();
    }

    private void vaciar() {
        botonVaciar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textfieldNombre.setText("");
                labelSaludo.setText("");
            }
        });
    }

    private void initGUI() {
        //personalizara las partes de la UI
        botonPulsar.setEffect(sombra);
    }

    private void instancias() {
        sombra = new DropShadow();
    }

    private void acciones() {

        botonPulsar.setOnAction(new ManejoAccion());
        botonVaciar.setOnAction(new ManejoAccion());
        botonPulsar.setOnMouseEntered(new ManejoRaton());
        botonPulsar.setOnMousePressed(new ManejoRaton());
        botonPulsar.setOnMouseReleased(new ManejoRaton());
        botonPulsar.setOnMouseExited(new ManejoRaton());

    }

    class ManejoAccion implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            if (actionEvent.getSource() == botonPulsar) {
                String nombre = textfieldNombre.getText();
                if (nombre.isBlank()) {
                    System.out.println("Por favor introduce un nombre");
                } else {
                    labelSaludo.setText("Enhorabuena " + nombre + " has completado el primer ejercicio");
                    textfieldNombre.clear();
                }
                System.out.println("Has pulsado el boton pulsar");
            } else if (actionEvent.getSource() == botonVaciar) {
                textfieldNombre.clear();
                labelSaludo.setText("Coño que no lo he enchufado");
                System.out.println("Has pulsado el boton vaciar");
            }
        }


    }

    class ManejoRaton implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Button boton = (Button) mouseEvent.getSource();
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {
                botonPulsar.setCursor(Cursor.OPEN_HAND);
                boton.setEffect(sombra);
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                botonPulsar.setCursor(Cursor.CLOSED_HAND);
                boton.setEffect(sombra);
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                botonPulsar.setCursor(Cursor.OPEN_HAND);
                boton.setEffect(sombra);
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
                boton.setEffect(null);
            }
        }
    }

}

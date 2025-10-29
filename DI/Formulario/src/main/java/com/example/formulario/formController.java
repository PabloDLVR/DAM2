package com.example.formulario;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class formController implements Initializable {

    @FXML
    private Button buttonAgregar, buttonEliminar;

    @FXML
    private TextField textfieldNombre, textfieldCorreo, textfieldLocalizacion;

    @FXML
    private ToggleButton toggleLista;

    @FXML
    private CheckBox checkDisponibilidad;

    @FXML
    private RadioButton radioMasculino, radioFemenino;

    @FXML
    private BorderPane panelGeneral;

    @FXML
    private ComboBox<Integer> comboEdad;

    private ToggleGroup grupoGenero;

    private ObservableList<Integer> listaEdades;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instancias();
        initGUI();
        acciones();
    }

    private void acciones() {
        buttonAgregar.setOnAction(new ManejoActions());
        buttonEliminar.setOnAction(new ManejoActions());
        checkDisponibilidad.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    System.out.println("El usuario está disponible");
                } else {
                    System.out.println("El usuario no está disponible");
                }
            }
        });
    }

    //LOGICA

    private void instancias() {
        grupoGenero = new ToggleGroup();
        grupoGenero.getToggles().addAll(radioMasculino, radioFemenino);
        listaEdades = FXCollections.observableArrayList();
        for (int i = 18; i <= 91; i++) {
            listaEdades.add(i);
        }
    }


    //GRÁFICO

    private void initGUI() {
        comboEdad.setItems(listaEdades);
    }

    class ManejoActions implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (actionEvent.getSource() == buttonAgregar) {
                String nombre = textfieldNombre.getText();
                String correo = textfieldCorreo.getText();
                String localizacion = textfieldLocalizacion.getText();
                String genero = ((RadioButton) grupoGenero.getSelectedToggle()).getText();
                int edad = comboEdad.getSelectionModel().getSelectedItem();
                boolean disponibilidad = checkDisponibilidad.isSelected();

                System.out.println("Nombre: " + nombre);
                System.out.println("Correo: " + correo);
                System.out.println("Localización: " + localizacion);
                System.out.println("Género: " + genero);
                System.out.println("Edad: " + edad);
                System.out.println("Disponibilidad: " + disponibilidad);


            } else if (actionEvent.getSource() == buttonEliminar) {
                System.out.println("Eliminar");
            }
        }
    }
}

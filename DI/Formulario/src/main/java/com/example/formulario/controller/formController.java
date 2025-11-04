package com.example.formulario.controller;

import com.example.formulario.HelloApplication;
import com.example.formulario.model.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class formController implements Initializable {

    @FXML
    private Button botonAgregar;

    @FXML
    private Button botonEliminar;

    @FXML
    private Button botonDetalle;

    @FXML
    private CheckBox checkDisponibilidad;

    @FXML
    private ComboBox<Integer> comboEdad;

    @FXML
    private BorderPane panelGeneral;
    @FXML
    private ListView<Usuario> listViewUsuarios;

    @FXML
    private GridPane parteDerecha;

    @FXML
    private RadioButton radioFemenino;

    @FXML
    private RadioButton radioMasculino;

    @FXML
    private TextField texfieldNombre;

    @FXML
    private TextField textfieldCorreo;

    @FXML
    private TextField textfieldLocalizacion;

    @FXML
    private ToggleButton toggleLista;

    @FXML
    private ToggleGroup grupoGenero;

    @FXML
    private ObservableList<Integer> listaEdades;

    @FXML
    private ObservableList<Usuario> listaUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        instancias();
        initGUI();
        acciones();
    }

    private void acciones() {
        botonDetalle.setOnAction(new ManejoActions());
        botonAgregar.setOnAction(new ManejoActions());
        botonEliminar.setOnAction(new ManejoActions());
        checkDisponibilidad.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue,
                                Boolean oldValue, Boolean newValue) {
                botonAgregar.setDisable(!newValue);

            }
        });
        toggleLista.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue,
                                Boolean aBoolean, Boolean t1) {
                if (t1) {
                    panelGeneral.setRight(parteDerecha);
                } else {
                    panelGeneral.setRight(null);
                }
            }
        });
    }

    private void instancias() {
        grupoGenero = new ToggleGroup();
        grupoGenero.getToggles().addAll(radioMasculino, radioFemenino);
        listaEdades = FXCollections.observableArrayList();
        listaUsuarios = FXCollections.observableArrayList();
        for (int i = 18; i < 91; i++) {
            listaEdades.add(i);
        }
    }

    private void initGUI() {
        listViewUsuarios.setItems(listaUsuarios);
        comboEdad.setItems(listaEdades);
        botonAgregar.setDisable(!checkDisponibilidad.isSelected());
        if (toggleLista.isSelected()) {
            panelGeneral.setRight(parteDerecha);
        } else {
            panelGeneral.setRight(null);
        }
    }

    class ManejoActions implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            if (actionEvent.getSource() == botonAgregar) {


                if (!texfieldNombre.getText().isEmpty()
                        && !textfieldCorreo.getText().isEmpty()
                        && !textfieldLocalizacion.getText().isEmpty()
                        && grupoGenero.getSelectedToggle() != null
                        && comboEdad.getSelectionModel().getSelectedItem() >= 0
                ) {
                    String nombre = texfieldNombre.getText();
                    String correo = textfieldCorreo.getText();
                    String localizacion = textfieldLocalizacion.getText();
                    String genero = ((RadioButton) grupoGenero.getSelectedToggle()).getText();
                    boolean disponibilidad = checkDisponibilidad.isSelected();
                    int edad = comboEdad.getSelectionModel().getSelectedItem();
                    Usuario usuario = new Usuario(
                            nombre, correo, localizacion, edad, genero, disponibilidad
                    );
                    listaUsuarios.add(usuario);

                    // limpiar todos los datos
                } else {
                    System.out.println("Error, faltan datoss");
                }


            } else if (actionEvent.getSource() == botonEliminar) {
                Usuario usuarioSeleccionado = listViewUsuarios.getSelectionModel().getSelectedItem();
                if (usuarioSeleccionado != null) {
                    listaUsuarios.remove(usuarioSeleccionado);
                    System.out.println("Usuario eliminado");
                } else {
                    System.out.println("No hay ningun usuario seleccionado");
                    Stage ventanaDialogo = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example" +
                                "/formulario/view/dialogo-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        ventanaDialogo.setScene(scene);
                        ventanaDialogo.setTitle("Confirmacion");
                        ventanaDialogo.setResizable(false);
                        ventanaDialogo.initModality();
                        ventanaDialogo.showAndWait();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (actionEvent.getSource() == botonDetalle) {
                Usuario usuarioSeleccionado = listViewUsuarios.getSelectionModel().getSelectedItem();
                System.out.println("Detalle del usuario seleccionado: ");
                if (usuarioSeleccionado != null) {
                    System.out.println(usuarioSeleccionado);
                } else {
                    System.out.println("No hay ningun usuario seleccionado");
                }
            }
        }
    }
}
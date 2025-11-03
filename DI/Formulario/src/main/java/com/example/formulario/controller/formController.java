package com.example.formulario.controller;

import com.example.formulario.model.Usuario;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

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

    @FXML
    private FlowPane parteDerecha;

    private ToggleGroup grupoGenero;
    private ObservableList<Integer> listaEdades;
    private ObservableList<Usuario> listaUsuarios;
    private ListView<Usuario> listViewUsuarios;
    private VBox vboxLista;

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
                buttonAgregar.setDisable(!t1);
            }
        });

        toggleLista.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    actualizarListaUsuarios();
                    panelGeneral.setRight(parteDerecha);
                } else {
                    panelGeneral.setRight(null);
                }
            }
        });
    }

    private void instancias() {
        grupoGenero = new ToggleGroup();
        radioMasculino.setToggleGroup(grupoGenero);
        radioFemenino.setToggleGroup(grupoGenero);

        listaEdades = FXCollections.observableArrayList();
        for (int i = 18; i <= 91; i++) {
            listaEdades.add(i);
        }

        listaUsuarios = FXCollections.observableArrayList();
        listViewUsuarios = new ListView<>(listaUsuarios);

        // Configurar cómo se muestra cada usuario en la lista
        listViewUsuarios.setCellFactory(param -> new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario usuario, boolean empty) {
                super.updateItem(usuario, empty);
                if (empty || usuario == null) {
                    setText(null);
                } else {
                    setText(usuario.getNombre() + " - " + usuario.getEdad() + " años - " + usuario.getGenero());
                }
            }
        });

        // Crear el VBox para la lista
        vboxLista = new VBox();
        vboxLista.setSpacing(10);
        vboxLista.setPadding(new javafx.geometry.Insets(10));
    }

    private void initGUI() {
        comboEdad.setItems(listaEdades);
        buttonAgregar.setDisable(!checkDisponibilidad.isSelected());

        // Configurar la parte derecha con la lista de usuarios
        Label tituloLista = new Label("Lista de Usuarios:");
        vboxLista.getChildren().addAll(tituloLista, listViewUsuarios);

        // Limpiar y agregar el VBox al FlowPane
        parteDerecha.getChildren().clear();
        parteDerecha.getChildren().add(vboxLista);

        // Configurar tamaño de la ListView
        listViewUsuarios.setPrefHeight(300);
        listViewUsuarios.setPrefWidth(250);
    }

    private void actualizarListaUsuarios() {
        listViewUsuarios.refresh();
    }

    private void limpiarFormulario() {
        textfieldNombre.clear();
        textfieldCorreo.clear();
        textfieldLocalizacion.clear();
        grupoGenero.selectToggle(null);
        comboEdad.getSelectionModel().clearSelection();
        checkDisponibilidad.setSelected(false);
    }

    class ManejoActions implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (actionEvent.getSource() == buttonAgregar) {
                agregarUsuario();
            } else if (actionEvent.getSource() == buttonEliminar) {
                eliminarUsuario();
            }
        }
    }

    private void agregarUsuario() {
        if (validarFormulario()) {
            String nombre = textfieldNombre.getText();
            String correo = textfieldCorreo.getText();
            String localizacion = textfieldLocalizacion.getText();
            String genero = ((RadioButton) grupoGenero.getSelectedToggle()).getText();
            int edad = comboEdad.getSelectionModel().getSelectedItem();
            boolean disponibilidad = checkDisponibilidad.isSelected();

            Usuario nuevoUsuario = new Usuario(nombre, correo, localizacion, edad, genero, disponibilidad);
            listaUsuarios.add(nuevoUsuario);

            System.out.println("Usuario agregado: " + nuevoUsuario);
            limpiarFormulario();

            // Si la lista está visible, actualizarla
            if (toggleLista.isSelected()) {
                actualizarListaUsuarios();
            }
        } else {
            mostrarAlerta("Error", "Por favor, complete todos los campos del formulario.");
        }
    }

    private void eliminarUsuario() {
        Usuario usuarioSeleccionado = listViewUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            listaUsuarios.remove(usuarioSeleccionado);
            System.out.println("Usuario eliminado: " + usuarioSeleccionado.getNombre());
            actualizarListaUsuarios();
        } else {
            mostrarAlerta("Error", "Por favor, seleccione un usuario de la lista para eliminar.");
        }
    }

    private boolean validarFormulario() {
        return textfieldNombre.getText() != null && !textfieldNombre.getText().trim().isEmpty()
                && textfieldCorreo.getText() != null && !textfieldCorreo.getText().trim().isEmpty()
                && textfieldLocalizacion.getText() != null && !textfieldLocalizacion.getText().trim().isEmpty()
                && grupoGenero.getSelectedToggle() != null
                && comboEdad.getSelectionModel().getSelectedItem() != null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
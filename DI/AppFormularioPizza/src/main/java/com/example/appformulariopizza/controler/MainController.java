package com.example.appformulariopizza.controler;


import com.example.appformulariopizza.dao.UsuarioDAOImp;
import com.example.appformulariopizza.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button botonAgregar;

    @FXML
    private ComboBox<String> comboPizza;

    @FXML
    private ListView<String> listView;

    @FXML
    private MenuItem menuDetalle;

    @FXML
    private GridPane panelDerecha;

    @FXML
    private BorderPane panelGeneral;

    @FXML
    private RadioButton radioFamiliar, radioMediana, radioPequena;

    @FXML
    private TextField textNombre, textTelefono;

    private ToggleGroup grupoTamanio;
    private ObservableList<String> listaPizzas;
    private ObservableList<String> listaPedidos;
    private int contadorPedidos = 1;
    private UsuarioDAOImp usuarioDAOImp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instancias();
        acciones();
        unitGUI();
    }

    private void unitGUI() {
        listView.setItems(listaPedidos);
        comboPizza.setItems(listaPizzas);

        textNombre.setPromptText("Introduce nombre");
        textTelefono.setPromptText("Introduce teléfono");
        comboPizza.setPromptText("Selecciona una pizza");
    }

    private void acciones() {
        menuDetalle.setOnAction(new ManejoAcciones());
        botonAgregar.setOnAction(new ManejoAcciones());
    }

    private void instancias() {
        grupoTamanio = new ToggleGroup();
        grupoTamanio.getToggles().addAll(radioPequena, radioMediana, radioFamiliar);

        listaPizzas = FXCollections.observableArrayList();
        listaPizzas.addAll("Margarita", "Pepperoni", "Cuatro Quesos", "Hawaiana", "Barbacoa", "Vegetal");

        listaPedidos = FXCollections.observableArrayList();
        usuarioDAOImp = new UsuarioDAOImp();
    }


    private void limpiarFormulario() {
        textNombre.clear();
        textTelefono.clear();
        grupoTamanio.selectToggle(null);
        comboPizza.getSelectionModel().clearSelection();
    }

    private double calcularPrecio(String pizza, String tamanio) {
        double precioBase = 0;

        switch (pizza) {
            case "Margarita":
                precioBase = 8.00;
                break;
            case "Pepperoni":
                precioBase = 10.00;
                break;
            case "Cuatro Quesos":
                precioBase = 12.00;
                break;
            case "Hawaiana":
                precioBase = 11.00;
                break;
            case "Barbacoa":
                precioBase = 13.00;
                break;
            case "Vegetal":
                precioBase = 9.00;
                break;
        }

        switch (tamanio) {
            case "Pequeña":
                return precioBase * 0.7;
            case "Mediana":
                return precioBase;
            case "Familiar":
                return precioBase * 1.5;
            default:
                return precioBase;
        }
    }

    private String obtenerTamanioSeleccionado() {
        if (radioPequena.isSelected()) return "Pequeña";
        if (radioMediana.isSelected()) return "Mediana";
        if (radioFamiliar.isSelected()) return "Familiar";
        return null;
    }

    class ManejoAcciones implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            if (actionEvent.getSource() == botonAgregar || actionEvent.getSource() == menuDetalle) {
                System.out.println("Presionaste el boton agregar pedido");

                if (!textNombre.getText().isEmpty()
                        && !textTelefono.getText().isEmpty()
                        && comboPizza.getSelectionModel().getSelectedItem() != null
                        && grupoTamanio.getSelectedToggle() != null) {

                    String nombre = textNombre.getText();
                    String telefonoTexto = textTelefono.getText();
                    String pizza = comboPizza.getSelectionModel().getSelectedItem();
                    String tamanio = obtenerTamanioSeleccionado();

                    try {
                        int telefono = Integer.parseInt(telefonoTexto);

                        if (telefono < 100000000 || telefono > 999999999) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error en teléfono");
                            alert.setContentText("El teléfono debe tener exactamente 9 dígitos");
                            alert.show();
                            return;
                        }

                        double precio = calcularPrecio(pizza, tamanio);

                        Usuario usuario = new Usuario(nombre, telefono, pizza, tamanio, precio);

                        boolean fallo = false;
                        try {
                            usuarioDAOImp.insertarUsuario(usuario);
                        } catch (SQLException e) {
                            fallo = true;
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error al agregar");
                            alert.setContentText("Error al guardar en la base de datos: " + e.getMessage());
                            alert.show();
                        }

                        if (!fallo) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Pedido agregado correctamente");
                            alert.setContentText("Pedido realizado:\n" +
                                    "Nombre: " + nombre + "\n" +
                                    "Teléfono: " + telefono + "\n" +
                                    "Pizza: " + pizza + "\n" +
                                    "Tamaño: " + tamanio + "\n" +
                                    "Precio: " + String.format("%.2f€", precio));
                            alert.show();

                            limpiarFormulario();
                        }

                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error en teléfono");
                        alert.setContentText("El teléfono debe contener solo números");
                        alert.show();

                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Faltan datos");
                    alert.setContentText("Por favor, complete todos los campos obligatorios");
                    alert.show();
                }
            }
        }
    }
}
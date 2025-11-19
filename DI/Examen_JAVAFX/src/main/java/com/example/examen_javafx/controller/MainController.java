package com.example.examen_javafx.controller;

import com.example.examen_javafx.dao.ProductosDAOImp;
import com.example.examen_javafx.dao.UsuariosDAOImp;
import com.example.examen_javafx.model.Productos;
import com.example.examen_javafx.model.Usuarios;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;


import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class MainController implements Serializable {

    @FXML
    private Button botonLogin;

    @FXML
    private Label labelCorreo;

    @FXML
    private Label labelPass;

    @FXML
    private Button botonDetalle;

    @FXML
    private Button botonFiltrar;

    @FXML
    private ComboBox<?> comboCAT;

    private UsuariosDAOImp usuarioDAOImp;
    private ProductosDAOImp productosDAOImp;
    private ArrayList<Usuarios> listaUsuarios;
    private ArrayList<Productos> listaProductos;

    public void initialize(URL url, java.util.ResourceBundle resourceBundle) {
        instancias();
        unitGUI();
        acciones();
    }

    private void unitGUI() {
        //llenar comboBox categorias

    }

    private void acciones() {
        botonLogin.setOnAction(new ManejoAcciones());
    }

    private void instancias() {
        //inicializar lista
        usuarioDAOImp = new UsuariosDAOImp();
        productosDAOImp = new ProductosDAOImp();
        listaUsuarios = new ArrayList<Usuarios>();
        listaProductos = new ArrayList<Productos>();
    }

    class ManejoAcciones implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            //limpiar campos cuando presiones login
            botonLogin.setOnAction(e -> {
                labelCorreo.setText("");
                labelPass.setText("");
            });
            //si existe usuario pasar a la siguiente ventana
            if (listaUsuarios.isEmpty()){
                //salte una alerta

            }
        }
    }

}

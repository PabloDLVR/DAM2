package com.example.appcalculadora.controler;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainControler implements Initializable {

    @FXML
    private Button boton0, boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9,
            botonMas, botonMenos, botonIgual, botonPor, botonDivi, botonPunto, botonBorrar, botonAC, botonPorciento;

    @FXML
    private GridPane gridPanel;

    @FXML
    private Label pantalla;

    @FXML
    private ListView<String> historialListView;

    @FXML
    private MenuItem menuCientifica;

    @FXML
    private MenuItem menuEstandar;

    private String operacionActual = "";
    private double num1 = 0;
    private boolean nuevaOperacion = true;
    private String expresionHistorial = "";

    // Historial
    private ObservableList<String> historialData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instancias();
        initGUI();
        acciones();
    }

    private void instancias() {
        historialData = FXCollections.observableArrayList();
        historialListView.setItems(historialData);
    }

    private void initGUI() {
        pantalla.setText("0");
    }

    private void acciones() {

        EventHandler<ActionEvent> numericHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleNumericButton(event);
            }
        };

        EventHandler<ActionEvent> operatorHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleOperatorButton(event);
            }
        };

        // Asignación de Handlers a botones numéricos
        boton0.setOnAction(numericHandler);
        boton1.setOnAction(numericHandler);
        boton2.setOnAction(numericHandler);
        boton3.setOnAction(numericHandler);
        boton4.setOnAction(numericHandler);
        boton5.setOnAction(numericHandler);
        boton6.setOnAction(numericHandler);
        boton7.setOnAction(numericHandler);
        boton8.setOnAction(numericHandler);
        boton9.setOnAction(numericHandler);
        botonPunto.setOnAction(numericHandler);

        botonMas.setOnAction(operatorHandler);
        botonMenos.setOnAction(operatorHandler);
        botonPor.setOnAction(operatorHandler);
        botonDivi.setOnAction(operatorHandler);
        botonPorciento.setOnAction(operatorHandler);
        botonIgual.setOnAction(operatorHandler);
        botonAC.setOnAction(operatorHandler);
        botonBorrar.setOnAction(operatorHandler);

        menuCientifica.setOnAction(event -> abrirCientifica());
    }

    private void handleNumericButton(ActionEvent event) {
        Button boton = (Button) event.getSource();
        String textoBoton = boton.getText();

        if (boton == botonPunto) {
            if (!pantalla.getText().contains(".")) {
                pantalla.setText(pantalla.getText() + ".");
            }
            return;
        }

        if (pantalla.getText().equals("0") || nuevaOperacion) {
            pantalla.setText(textoBoton);
            nuevaOperacion = false;
        } else {
            pantalla.setText(pantalla.getText() + textoBoton);
        }
    }

    private void handleOperatorButton(ActionEvent event) {
        Button boton = (Button) event.getSource();

        if (boton == botonAC) {
            pantalla.setText("0");
            operacionActual = "";
            num1 = 0;
            expresionHistorial = "";
            nuevaOperacion = true;
            historialData.clear();
            return;
        }

        if (boton == botonBorrar) {
            if (pantalla.getText().length() > 1) {
                pantalla.setText(pantalla.getText().substring(0, pantalla.getText().length() - 1));
            } else {
                pantalla.setText("0");
            }
            return;
        }

        if (boton == botonIgual) {
            calcular();
            return;
        }

        num1 = Double.parseDouble(pantalla.getText());
        operacionActual = boton.getText();

        expresionHistorial = formatNumber(num1) + " " + operacionActual;

        nuevaOperacion = true;
    }

    private void calcular() {
        if (operacionActual.isEmpty()) {
            nuevaOperacion = true;
            return;
        }

        double num2 = Double.parseDouble(pantalla.getText());
        double resultado = 0;

        String expresionCompleta = expresionHistorial + " " + formatNumber(num2);

        switch (operacionActual) {
            case "+":
                resultado = num1 + num2;
                break;
            case "-":
                resultado = num1 - num2;
                break;
            case "×":
            case "*":
                resultado = num1 * num2;
                break;
            case "÷":
            case "/":
                if (num2 == 0) {
                    pantalla.setText("Error");
                    nuevaOperacion = true;
                    operacionActual = "";
                    expresionHistorial = "";
                    return;
                }
                resultado = num1 / num2;
                break;
            case "%":

                resultado = num1 * (num2 / 100);

                expresionCompleta = expresionHistorial + formatNumber(num2) + "%";
                break;
        }

        String resultadoFormateado = formatNumber(resultado);

        String historialEntry = expresionCompleta + " = " + resultadoFormateado;
        historialData.add(0, historialEntry);

        pantalla.setText(resultadoFormateado);

        operacionActual = "";
        expresionHistorial = "";
        num1 = resultado;
        nuevaOperacion = true;
    }

    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.valueOf((long) number);
        } else {
            return String.valueOf(number);
        }
    }

    private void abrirCientifica() {
        try {
            System.out.println("=== DEBUG DETALLADO ===");
            System.out.println("Directorio de trabajo: " + System.getProperty("user.dir"));
            System.out.println("Classpath: " + System.getProperty("java.class.path"));

            // Probar múltiples rutas
            String[] rutas = {
                    "calculadoraCientifica-view.fxml",
                    "/calculadoraCientifica-view.fxml",
                    "com/example/appcalculadora/calculadoraCientifica-view.fxml",
                    "/com/example/appcalculadora/calculadoraCientifica-view.fxml",
                    "../calculadoraCientifica-view.fxml"
            };

            URL urlEncontrado = null;
            for (String ruta : rutas) {
                URL testUrl = getClass().getResource(ruta);
                System.out.println("Probando ruta: '" + ruta + "' -> " + testUrl);
                if (testUrl != null) {
                    urlEncontrado = testUrl;
                    break;
                }
            }

            if (urlEncontrado == null) {
                System.err.println("❌ NO SE ENCONTRÓ EL ARCHIVO FXML EN NINGUNA RUTA");

                // Listar recursos disponibles
                System.out.println("Recursos en raíz:");
                try {
                    java.util.Enumeration<URL> resources = getClass().getClassLoader().getResources("");
                    while (resources.hasMoreElements()) {
                        System.out.println(" - " + resources.nextElement());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            System.out.println("✅ ARCHIVO ENCONTRADO EN: " + urlEncontrado);

            // Intentar cargar
            try {
                FXMLLoader loader = new FXMLLoader(urlEncontrado);
                Parent root = loader.load();
                System.out.println("✅ FXML CARGADO EXITOSAMENTE");

                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.setTitle("Calculadora Científica");

                Stage currentStage = (Stage) pantalla.getScene().getWindow();
                System.out.println("Cerrando ventana actual...");
                currentStage.close();

                System.out.println("Mostrando nueva ventana...");
                newStage.show();

            } catch (Exception e) {
                System.err.println("❌ ERROR AL CARGAR EL FXML:");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("❌ ERROR GENERAL:");
            e.printStackTrace();
        }
    }
}
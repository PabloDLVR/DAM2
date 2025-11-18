
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
import java.util.ResourceBundle;

public class CalculadoraCientifica implements Initializable {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button boton0, boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9,
            botonMas, botonMenos, botonIgual, botonPor, botonDivi, botonPunto, botonBorrar, botonAC, botonPorciento;

    // Botones científicos
    @FXML
    private Button botonSin, botonCos, botonTan, botonLog, botonRaiz, botonPotencia,
            botonPi, botonE, botonFac, botonParAbre;

    @FXML
    private GridPane gridPanel;

    @FXML
    private Label pantalla;

    @FXML
    private ListView<String> historialListView;

    @FXML
    private MenuItem menuEstandar;

    private String operacionActual = "";
    private double num1 = 0;
    private boolean nuevaOperacion = true;
    private String expresionHistorial = "";
    private boolean enGrados = true; // true = grados, false = radianes

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

        // Operadores básicos
        botonMas.setOnAction(operatorHandler);
        botonMenos.setOnAction(operatorHandler);
        botonPor.setOnAction(operatorHandler);
        botonDivi.setOnAction(operatorHandler);
        botonPorciento.setOnAction(operatorHandler);
        botonIgual.setOnAction(operatorHandler);
        botonAC.setOnAction(operatorHandler);
        botonBorrar.setOnAction(operatorHandler);

        // Funciones científicas
        botonSin.setOnAction(e -> calcularSeno());
        botonCos.setOnAction(e -> calcularCoseno());
        botonTan.setOnAction(e -> calcularTangente());
        botonLog.setOnAction(e -> calcularLogaritmo());
        botonRaiz.setOnAction(e -> calcularRaiz());
        botonPotencia.setOnAction(e -> prepararPotencia());
        botonPi.setOnAction(e -> insertarPi());
        botonE.setOnAction(e -> insertarE());
        botonFac.setOnAction(e -> calcularFactorial());
        botonParAbre.setOnAction(e -> insertarParentesis("("));

        // Menú
        menuEstandar.setOnAction(event -> abrirEstandar());
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
            case "^":
                resultado = Math.pow(num1, num2);
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

    // ========== FUNCIONES CIENTÍFICAS ==========

    private void calcularSeno() {
        try {
            double valor = Double.parseDouble(pantalla.getText());
            double angulo = enGrados ? Math.toRadians(valor) : valor;
            double resultado = Math.sin(angulo);

            pantalla.setText(formatNumber(resultado));
            historialData.add(0, "sin(" + formatNumber(valor) + "°) = " + formatNumber(resultado));
            nuevaOperacion = true;

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }

    private void calcularCoseno() {
        try {
            double valor = Double.parseDouble(pantalla.getText());
            double angulo = enGrados ? Math.toRadians(valor) : valor;
            double resultado = Math.cos(angulo);

            pantalla.setText(formatNumber(resultado));
            historialData.add(0, "cos(" + formatNumber(valor) + "°) = " + formatNumber(resultado));
            nuevaOperacion = true;

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }

    private void calcularTangente() {
        try {
            double valor = Double.parseDouble(pantalla.getText());
            double angulo = enGrados ? Math.toRadians(valor) : valor;

            // Evitar errores con ángulos donde cos(angulo) = 0
            if (Math.cos(angulo) == 0) {
                pantalla.setText("Error");
                return;
            }

            double resultado = Math.tan(angulo);
            pantalla.setText(formatNumber(resultado));
            historialData.add(0, "tan(" + formatNumber(valor) + "°) = " + formatNumber(resultado));
            nuevaOperacion = true;

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }

    private void calcularLogaritmo() {
        try {
            double valor = Double.parseDouble(pantalla.getText());
            if (valor <= 0) {
                pantalla.setText("Error");
                return;
            }
            double resultado = Math.log10(valor);

            pantalla.setText(formatNumber(resultado));
            historialData.add(0, "log(" + formatNumber(valor) + ") = " + formatNumber(resultado));
            nuevaOperacion = true;

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }

    private void calcularRaiz() {
        try {
            double valor = Double.parseDouble(pantalla.getText());
            if (valor < 0) {
                pantalla.setText("Error");
                return;
            }
            double resultado = Math.sqrt(valor);

            pantalla.setText(formatNumber(resultado));
            historialData.add(0, "√(" + formatNumber(valor) + ") = " + formatNumber(resultado));
            nuevaOperacion = true;

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }

    private void prepararPotencia() {
        try {
            num1 = Double.parseDouble(pantalla.getText());
            operacionActual = "^";
            expresionHistorial = formatNumber(num1) + " ^ ";
            nuevaOperacion = true;
        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }

    private void insertarPi() {
        pantalla.setText(String.valueOf(Math.PI));
        nuevaOperacion = true;
    }

    private void insertarE() {
        pantalla.setText(String.valueOf(Math.E));
        nuevaOperacion = true;
    }

    private void calcularFactorial() {
        try {
            int valor = Integer.parseInt(pantalla.getText());
            if (valor < 0) {
                pantalla.setText("Error");
                return;
            }

            long factorial = 1;
            for (int i = 2; i <= valor; i++) {
                factorial *= i;
            }

            pantalla.setText(String.valueOf(factorial));
            historialData.add(0, valor + "! = " + factorial);
            nuevaOperacion = true;

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }

    private void insertarParentesis(String parentesis) {
        if (pantalla.getText().equals("0") || nuevaOperacion) {
            pantalla.setText(parentesis);
        } else {
            pantalla.setText(pantalla.getText() + parentesis);
        }
        nuevaOperacion = false;
    }

    // ========== MÉTODOS AUXILIARES ==========

    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.valueOf((long) number);
        } else {
            // Redondear a 10 decimales para evitar números muy largos
            return String.format("%.10f", number).replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }

    private void abrirEstandar() {
        try {
            System.out.println("=== ABRIENDO CALCULADORA ESTÁNDAR ===");

            // Prueba diferentes rutas
            String[] rutas = {
                    "calculadora-view.fxml",
                    "/calculadora-view.fxml",
                    "com/example/appcalculadora/calculadora-view.fxml",
                    "/com/example/appcalculadora/calculadora-view.fxml",
                    "../calculadora-view.fxml"
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
                System.err.println("❌ No se encontró calculadoraEstandar-view.fxml");
                return;
            }

            System.out.println("✅ Archivo encontrado: " + urlEncontrado);

            FXMLLoader loader = new FXMLLoader(urlEncontrado);
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Calculadora Estándar");

            Stage currentStage = (Stage) pantalla.getScene().getWindow();
            currentStage.close();

            newStage.show();
            System.out.println("✅ Calculadora estándar abierta exitosamente");

        } catch (IOException e) {
            System.err.println("Error al cargar la vista estándar:");
            e.printStackTrace();
        }
    }

    // Méto do para cambiar entre grados y radianes (podrías agregar un botón para esto)
    public void cambiarModoAngulo() {
        enGrados = !enGrados;
        // Aquí podrías actualizar la interfaz para mostrar el modo actual
    }

}
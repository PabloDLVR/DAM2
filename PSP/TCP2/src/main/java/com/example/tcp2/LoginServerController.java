package com.example.tcp2;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.application.Platform;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginServerController {

    @FXML
    private TextArea txtLog;

    public void initialize() {
        iniciarServidorTCP();
    }

    private void iniciarServidorTCP() {
        new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(5000);
                agregarTexto("Servidor TCP escuchando en el puerto 5000...\n");

                while (true) {
                    Socket client = server.accept();
                    agregarTexto("Cliente conectado: " + client.getInetAddress() + "\n");

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(client.getInputStream())
                    );

                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                    // Leer usuario y contraseña desde Android
                    String data = in.readLine(); // formato: user:pass

                    agregarTexto("Credenciales recibidas → " + data + "\n");

                    // Si quieres validar:
                    if (data.equals("admin:1234")) {
                        out.println("OK");
                        agregarTexto("Login correcto\n");
                    } else {
                        out.println("ERROR");
                        agregarTexto("Login incorrecto\n");
                    }

                    client.close();
                }

            } catch (Exception e) {
                agregarTexto("ERROR: " + e.getMessage() + "\n");
            }
        }).start();
    }

    private void agregarTexto(String texto) {
        Platform.runLater(() -> txtLog.appendText(texto));
    }
}


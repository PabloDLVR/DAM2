package com.example.appformulariopizza.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // conexion a la base de datos
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection() {
        String user = "root";
        String pass = "";
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s",
                    SchemaDB.URL, SchemaDB.PORT, SchemaDB.DB_NAME), user, pass);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }


}

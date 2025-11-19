package com.example.examen_javafx.dao;

import com.example.examen_javafx.database.DBConnection;
import com.example.examen_javafx.model.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAOImp implements UsuarioDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void UsuariosDAOImp() {
        connection= DBConnection.getConnection();
    }

    @Override
    public void insertarUsuario(Usuarios usuarios) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, correo, contrasena) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, usuarios.getNombre());
        preparedStatement.setString(2, usuarios.getCorreo());
        preparedStatement.setString(3, usuarios.getContrasena());
        preparedStatement.executeUpdate();
    }
}

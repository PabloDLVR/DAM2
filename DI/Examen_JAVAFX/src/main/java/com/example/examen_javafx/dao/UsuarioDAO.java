package com.example.examen_javafx.dao;

import com.example.examen_javafx.model.Usuarios;

import java.sql.SQLException;

public interface UsuarioDAO {
    void insertarUsuario(Usuarios usuarios) throws SQLException;
}

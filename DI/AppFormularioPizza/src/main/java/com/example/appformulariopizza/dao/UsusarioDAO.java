package com.example.appformulariopizza.dao;

import com.example.appformulariopizza.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsusarioDAO {
    void insertarUsuario(Usuario usuario) throws SQLException;
    List<Usuario> obtenerUsuario();
    int borrarUsuario(String nombre);
}

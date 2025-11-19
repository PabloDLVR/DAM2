package com.example.examen_javafx.dao;

import com.example.examen_javafx.model.Productos;

import java.sql.SQLException;

public interface ProductosDAO {
    void insertarProductos(Productos productos) throws SQLException;

}

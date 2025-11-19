package com.example.examen_javafx.dao;

import com.example.examen_javafx.database.DBConnection;
import com.example.examen_javafx.model.Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductosDAOImp implements ProductosDAO{


    @Override
    public void insertarProductos(Productos productos) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "INSERT INTO productos (nombre, categoria, precio) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, productos.getNombre());
        preparedStatement.setString(2, productos.getCategoria());
        preparedStatement.setDouble(3, productos.getPrecio());
        preparedStatement.executeUpdate();
    }
}

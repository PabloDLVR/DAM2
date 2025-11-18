package com.example.appformulariopizza.dao;

import com.example.appformulariopizza.database.DBConnection;
import com.example.appformulariopizza.database.SchemaDB;
import com.example.appformulariopizza.model.Usuario;
import com.mysql.cj.xdevapi.Schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImp implements UsusarioDAO{

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UsuarioDAOImp() {
        connection = DBConnection.getConnection();
    }


    @Override
    public void insertarUsuario(Usuario usuario) throws SQLException {
        String sql = String.format("INSERT INTO %s (%s ,%s, %s, %s, %s) VALUES (?,?, ?, ?, ?)",
                SchemaDB.TABLE_USUARIOS, SchemaDB.COL_NAME, SchemaDB.COL_TELEFONO, SchemaDB.COL_NOMBREPIZZA,
                SchemaDB.COL_TAMANO, SchemaDB.COL_PRECIO);

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, usuario.getNombre());
        preparedStatement.setInt(2, usuario.getTelefono());
        preparedStatement.setString(3, usuario.getNombrePizza());
        preparedStatement.setString(4, usuario.getTamano());
        preparedStatement.setDouble(5, usuario.getPrecio());
        preparedStatement.execute();
    }

    @Override
    public List<Usuario> obtenerUsuario() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setTelefono(resultSet.getInt("telefono"));
                usuario.setTamano(resultSet.getString("tamanio"));
                usuario.setPrecio(resultSet.getDouble("precio"));

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    @Override
    public int borrarUsuario(String nombre) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(nombre));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

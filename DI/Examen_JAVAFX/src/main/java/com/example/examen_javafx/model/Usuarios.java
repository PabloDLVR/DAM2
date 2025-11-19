package com.example.examen_javafx.model;

import lombok.Data;

@Data
public class Usuarios {
    private int id;
    private String nombre;
    private String correo;
    private String contrasena;

    public Usuarios() {
    }

    public Usuarios(int id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }
}







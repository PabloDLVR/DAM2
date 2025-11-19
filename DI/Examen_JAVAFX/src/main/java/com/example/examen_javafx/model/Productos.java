package com.example.examen_javafx.model;

import lombok.Data;

@Data
public class Productos {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;

    public Productos() {
    }

    public Productos(int id, String nombre, String categoria, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }
}

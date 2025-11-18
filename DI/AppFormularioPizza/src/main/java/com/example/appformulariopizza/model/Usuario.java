package com.example.appformulariopizza.model;

import lombok.Data;

@Data
public class Usuario {
    private int id;
    private String nombre;
    private int telefono;
    private String nombrePizza;
    private String tamano;
    private double precio;

    public Usuario(){}

    public Usuario(String nombre, int telefono, String nombrePizza, String tamano, double precio) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.nombrePizza = nombrePizza;
        this.tamano = tamano;
        this.precio = precio;
    }
}

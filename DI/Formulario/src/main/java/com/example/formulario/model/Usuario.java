package com.example.formulario.model;

import lombok.Data;

@Data

public class Usuario {
    private String nombre;
    private String correo;
    private String localizacion;
    private Integer edad;
    private String genero;
    private Boolean disponibilidad;

    public Usuario(String nombre, String correo, String localizacion, Integer edad, String genero, Boolean disponibilidad) {
        this.nombre = nombre;
        this.correo = correo;
        this.localizacion = localizacion;
        this.edad = edad;
        this.genero = genero;
        this.disponibilidad = disponibilidad;
    }

    public Usuario() {
    }

    @Override
    public String toString() {
        return nombre;
    }
}

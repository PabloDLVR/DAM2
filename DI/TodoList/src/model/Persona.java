package model;

import java.lang.reflect.Array;

public class Persona {
    private String nombre, apellido, dni;
    private int edad;
    private int[] numeros = new int[4];


    public Persona(String nombre, String apellido, String dni, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.edad = edad;
    }

    public Persona() {

    }

    //Cada tarea puede asigar una persona
    //Solo se podrá asignar una persona si hay hueco dispnible
    //cadavez que se asigne una persona, se deberá colocar en el primer hueco disponible;
    //si no hay hueco saltar aviso;
    //no podrás agregar dos personas que tengan el mismo DNI;

    //En una tarea se pueden quitar responsables. Solo podre quitar una tarea si el DNI
    //que me indicas esta dentro de la lista de responsables.Mostrar aviso tanto para
    //proceso OK como proceso no OK

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", edad=" + edad +
                '}';
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}

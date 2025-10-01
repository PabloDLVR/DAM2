package model;

import java.util.ArrayList;

//TODO crear el método que permite agregar un encargo, los encargos deben tener ID unico -> PONER AVISOS
//TODO Crear el método que permite eliminar un encargo, para ello se pide el ID del encargo y se quita de la lista -> PONER AVISO

public class Tarea {

    private String titulo, descripcion;
    private boolean prioritario, completada;
    private Persona[] personas;
    private ArrayList<Encargo> listaTareas;

    public Tarea() {
    }

    public Tarea(String titulo, String descripcion, boolean prioritario, int numeroPersonas) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioritario = prioritario;
        listaTareas = new ArrayList<>();
        personas = new Persona[numeroPersonas];
        //Completada = False;
    }

    public Tarea(String titulo, String descripcion, int numeroPersonas) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        personas = new Persona[numeroPersonas];
        listaTareas = new ArrayList<>();
        //Completada = False;
        //Prioritario = False;
    }



    public void añadirPersona(Persona persona) {
        for (int i = 0; i < personas.length; i++) {
            if (personas[i] == null && !estaDNI(persona.getDni())) {
                personas[i] = persona;
                System.out.println("Persona agregada correctamente");
                return;
            }
        }
        System.out.println("No hay hueco disponible, tarea completa");
    }

    private Encargo eataEncargo(int id) {
        for (Encargo encargo : listaTareas) {
            if (encargo.getId() == id)
                return encargo;
        }
        return null;
    }

    public void añadirEncargo(Encargo encargo) {
        if (eataEncargo(encargo.getId()) != null) {
            System.out.println("Error en el proceso, no se pudo agregar");
        } else {
            listaTareas.add(encargo);
            System.out.println("Encargo añadido correctamente");
        }
    }

    public void eliminarEncargo(int id) {
        if (eataEncargo(id) != null) {
            listaTareas.remove(eataEncargo(id));
            System.out.println("Borrado correctamente");
        } else {
            System.out.println("El ID no se encuentra en la lista");
        }
    }

    private boolean estaDNI(String dni) {
        for (Persona persona : personas) {
            if (persona != null && persona.getDni().equalsIgnoreCase(dni)) {
                return true;
            }
        }
        return false;
    }

    public void eliminarResponsable(String dni) {
        for (Persona persona : personas) {
            if (persona != null && persona.getDni().equalsIgnoreCase(dni)) {
                persona = null;
                System.out.println("Persona elminada correctamente");
                return;
            }
        }
        System.out.printf("La persona con DNI %s no esta en esta tarea", dni);
    }

    //mostrar los datos de todos los usuarios que son responsables de dicha tarea
    //en caso de no ttener ninguna avisar
    //en caso de tener huecos disponibles, avisar de cuantos

    public void disponibilidad() {
        int numeroHuecos = 0;
        for (Persona persona : personas) {
            if (persona == null) {
                numeroHuecos++;
            } else {
                System.out.println(persona);
            }
        }
        if (numeroHuecos == personas.length) {
            System.out.println("No hay responsables asignados");
        } else if (numeroHuecos > 0) {
            System.out.printf("Hay %d huecos disponibles%n", numeroHuecos);
        } else {
            System.out.println("Todos los responsables están ubicados");
        }
    }

    public void mostarEncagados() {
        int num = 0;
        for (Persona persona : personas) {
            if (persona == null) {
                num++;
            } else {
                System.out.println(persona);
            }
        }
        if (num == personas.length) {
            System.out.println("No hay responsables asignados");
        } else if (num > 0) {
            System.out.printf("Hay %d huecos disponibles%n", num);
        } else {
            System.out.println("Todos los responsbles estan hubicados");
        }
    }

    public void completarTerea(){
        for (Encargo encargo: listaTareas) {
            if (!encargo.isCompletada()) {
                System.out.println("No se puede completar la tarea");
                return;
            }
        }
        completada = true;
        System.out.println("tarea completada con exito");
    }

    public void listarEncargoCompletado(){
        for (Encargo encargos : listaTareas) {
            encargos.mostrarDatos();
        }
    }

    public boolean completarEncargo(int id) {
        if (eataEncargo(id) != null && !eataEncargo(id).isCompletada()) {
            eataEncargo(id).setCompletada(true);
            System.out.println("El encargo se completo con exito");
            return true;
        }else {
            System.out.println("No se pudo completar o no esta en la lista");
            return false;
        }
    }

    public void listarEncargos() {
        for (Encargo encargos : listaTareas) {
            encargos.mostrarDatos();
        }
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", prioritario=" + prioritario +
                ", completada=" + completada +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isPrioritario() {
        return prioritario;
    }

    public void setPrioritario(boolean prioritario) {
        this.prioritario = prioritario;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public ArrayList<Encargo> getListaTareas() {
        return listaTareas;
    }

    public void setListaTareas(ArrayList<Encargo> listaTareas) {
        this.listaTareas = listaTareas;
    }

    public Persona[] getPersonas() {
        return personas;
    }

    public void setPersonas(Persona[] personas) {
        this.personas = personas;
    }
}

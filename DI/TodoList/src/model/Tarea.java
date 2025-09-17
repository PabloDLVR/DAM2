package model;

public class Tarea {

    private String titulo, descripcion;
    private boolean prioritario, completada;
    private Persona[] personas;

    public Tarea() {
        for (int i = 0; i < personas.length; i++) {
            if (personas == null) {
                System.out.println("Introduzca su nombre");
            }
        }
    }

    public Tarea(String titulo, String descripcion, boolean prioritario, int numeroPersonas) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioritario = prioritario;
        personas = new Persona[numeroPersonas];
        //Completada = False;
    }

    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
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
}

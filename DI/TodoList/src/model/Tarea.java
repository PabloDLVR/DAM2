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
            if (personas[i] == null) {
                personas[i] = persona;
                System.out.println("Persona agregada correctamente");
                return;
            }if (personas[i].equals(persona.getDni())){
                System.out.println("No se ha podido agregar correctamente, DNI ya registrado");
            }
        }
        System.out.println("No hay hueco disponible, tarea completa");
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

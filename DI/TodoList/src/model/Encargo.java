package model;

public class Encargo {
    private int id;
    private String desceipcion;
    private boolean completada;
    private Persona[] personas;

    public Encargo(int id, String desceipcion) {
        this.id = id;
        this.desceipcion = desceipcion;
    }

    public Encargo() {
    }


    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public String getDesceipcion() {
        return desceipcion;
    }

    public void setDesceipcion(String desceipcion) {
        this.desceipcion = desceipcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Encargo{" +
                "id=" + id +
                ", desceipcion='" + desceipcion + '\'' +
                ", completada=" + completada +
                '}';
    }
}

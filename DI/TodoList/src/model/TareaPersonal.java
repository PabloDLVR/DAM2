package model;

public class TareaPersonal extends Tarea implements FuncioalidadCompartida {
    private String ubicacion;

    public TareaPersonal() {

    }

    public TareaPersonal(String titulo, String descripcion, boolean prioritario, int numeroPersonas, String ubicacion) {
        super(titulo, descripcion, prioritario, numeroPersonas);
        this.ubicacion = ubicacion;
    }

    public TareaPersonal(String titulo, String descripcion, int numeroPersonas, String ubicacion) {
        super(titulo, descripcion, numeroPersonas);
        this.ubicacion = ubicacion;
    }

    @Override
    public void metodoComun() {

    }

    //@Override
    public void enviarAviso() {
        System.out.println("Recuerda terminar las tareasbuff");
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return super.toString() + "TareaPersonal{" +
                "ubicacion='" + ubicacion + '\'' +
                '}';
    }
}

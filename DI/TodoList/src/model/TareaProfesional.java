package model;

import java.util.Date;

public class TareaProfesional extends Tarea implements FuncioalidadCompartida{
    private int presupuesto;
    private Date fechaLimite;

    public TareaProfesional() {

    }

    public TareaProfesional(String titulo, String descripcion, int numeroPersonas, int presupuesto, Date fechaLimite) {
        super(titulo, descripcion, numeroPersonas);
        this.presupuesto = presupuesto;
        this.fechaLimite = fechaLimite;
    }

    @Override
    public void enviarAviso() {
        //TODO Se imprime por consola, a cad uno de los responsables el mensaje,
        //TODO Borja, recuerda terminar las tareas asociadas a esta tarea
        //TODO Borja, recuerda terminar las tareas asociadas a esta tarea
        for (Persona persona : getPersonas()) {
            if (persona != null) {
                System.out.printf("%s recuerda que completar %d tareas pendientes\n"
                        , persona.getNombre(), getListaTareas().size());
            }
        }
    }

    @Override
    public void metodoComun() {
        
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    @Override
    public String toString() {
        return super.toString() + "TareaProfesional{" +
                "presupuesto=" + presupuesto +
                ", fechaLimite=" + fechaLimite +
                '}';
    }


}

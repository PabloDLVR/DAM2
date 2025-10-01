import model.*;

import java.util.ArrayList;
import java.util.Date;

//Creamos un encargo, los encargos tienen una propiedad llamada descripcion,id y otra completa

public class Main {
    public static void main(String[] args) {
        Tarea tarea = new Tarea("Practica DI",
                "Esta practica de DI es hacer un programa todolist",
                true,
                2);
        /*tarea.añadirPersona(new Persona("Pablo","De la Vara","12345678A",24));
        tarea.añadirPersona(new Persona("Oscar","De la Torre","12345678B",24));
        Persona personas = new Persona();*/

       /* tarea.añadirEncargo(new Encargo(1,"Planificar la entrega"));
        tarea.añadirEncargo(new Encargo(2,"Codificar la practica"));
        tarea.añadirEncargo(new Encargo(3,"Grabar video"));
        tarea.añadirEncargo(new Encargo(4,"Entregar practica"));
        tarea.eliminarEncargo(4);
        System.out.printf("Esta tarea ciena con %d encargos", tarea.getListaTareas().size());*/

        TareaProfesional tareaProfesional = new TareaProfesional("TareaP1", "descricion", 5, 500000, new Date());
        TareaPersonal tareaPersonal = new TareaPersonal("Tarea1", "Descripcion", 5, "Alcorcón");

        ArrayList<Tarea> tareas = new ArrayList<>();
        tareas.add(tareaPersonal);
        tareas.add(tareaProfesional);
        for (Tarea tarea1 : tareas) {
           // tarea1.enviarAviso();
            System.out.println(tarea1);
        }
    }
}

//TODO Listar todos ls encargos de una tarea
//TODO Buscar un encargo por id y mostrar sus datos
//TODO Completar un encargo -> pasar su variable completada a true
//TODO Buscar un encargo que estan completados ->
//TODO Completar una tarea -> Una tarea quedará completa si todos sus encargos están completos

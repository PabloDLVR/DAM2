import model.Encargo;
import model.Persona;
import model.Tarea;

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

        tarea.añadirEncargo(new Encargo(1,"Planificar la entrega"));
        tarea.añadirEncargo(new Encargo(2,"Codificar la practica"));
        tarea.añadirEncargo(new Encargo(3,"Grabar video"));
        tarea.añadirEncargo(new Encargo(4,"Entregar practica"));
        tarea.eliminarEncargo(4);
        System.out.printf("Esta tarea ciena con %d encargos", tarea.getListaTareas().size());

    }
}

import model.Persona;
import model.Tarea;

public class Main {
    public static void main(String[] args) {
        Tarea tarea = new Tarea("Practica DI",
                "Esta practica de DI es hacer un programa todolist",
                true,
                2);
        tarea.añadirPersona(new Persona("Pablo","De la Vara","12345678A",24));
        tarea.añadirPersona(new Persona("Oscar","De la Torre","12345678B",24));
        Persona personas = new Persona();

    }
}

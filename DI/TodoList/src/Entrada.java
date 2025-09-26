import java.util.Scanner;

public class Entrada {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            System.out.println("1.- Registrar Tarea");
            System.out.println("2.- Modificar Tarea");
            System.out.println("3.- Listar Tareas");
            System.out.println("4.- Completar Tareas");
            System.out.println("5.- Listar completadas");
            System.out.println("6.- Listar incompletadas");
            System.out.println("7.- Salir");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Que tip de tareas quieres registrar?"
                            +"1.-Tarea Profesional o 2.- Tarea Personal");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                    case 4:
                        break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Saliendo....");
            }
        }
        while (opcion != 7);
    }
}

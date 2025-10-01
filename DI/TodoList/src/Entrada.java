import model.Encargo;
import model.Tarea;
import model.TareaPersonal;
import model.TareaProfesional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Entrada {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TareaPersonal tareaPersonal = new TareaPersonal("TareaP2", "descripcion", 2,
                "Alcorcon");
        TareaProfesional tareaProfesional = new TareaProfesional("TareaP1",
                "descripcion", true,
                5, 100000, new Date());
        Encargo encargos = new Encargo();
        ArrayList<Tarea> tareas = new ArrayList<>();

        int opcion = 0;
        do {
            System.out.println("1.- Registrar Tarea");
            System.out.println("2.- Modificar Tarea");
            System.out.println("3.- Listar Tareas");
            System.out.println("4.- Completar Tareas");
            System.out.println("5.- Listar completadas");
            System.out.println("6.- Listar incompletadas");
            System.out.println("7.- Salir");
            System.out.println("Que desea hacer?");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Que tipo de tarea quieres agregar 1 para tarea personal 2 para tarea profesional");
                    int num = scanner.nextInt();
                    scanner.nextLine();
                    if (num == 1) {
                        tareas.add(tareaPersonal);
                        //teareaPersonal.agregarEncargo(new Encargos("ejemplo", 1));
                        System.out.println("Dime que tarea tiene que hacer");
                        String des = scanner.nextLine();
                        System.out.println("Que id tiene esa tarea");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        tareaPersonal.añadirEncargo(new Encargo(id, des));

                    } else if (num == 2) {
                        tareas.add(tareaProfesional);
                        //tareaProfesional.agregarEncargo(new Encargos("ejemplo", 1));
                        System.out.println("Dime que tarea tiene que hacer");
                        String des = scanner.nextLine();
                        System.out.println("Que id tiene esa tarea");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        tareaProfesional.añadirEncargo(new Encargo(id, des));

                    } else
                        System.out.println("No se puede hacer esa opcion");
                    break;
                case 2:
                    break;
                case 3:

                    for (Tarea tarea : tareas) {
                        if (tarea instanceof TareaProfesional) {
                            System.out.println("Encargos de una tarea profesional:");
                            tarea.listarEncargos();
                        } else if (tarea instanceof TareaPersonal) {
                            System.out.println("Encargos de una tarea personal:");
                            tarea.listarEncargos();
                        } else {
                            System.out.println("No hay nada en esa opción");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Que ID de encargo quieres completar");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    boolean encontrado = false;
                    for (Tarea tarea : tareas) {
                        if (tarea.completarEncargo(id)) {
                            System.out.println("Encargo " + id + " completado.");
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("No se encontró ningún encargo con ese ID.");
                    }
                    break;
                case 5:
                    for (Tarea tarea : tareas) {
                        if (tarea.isCompletada()) {
                            if (tarea instanceof TareaProfesional) {
                                System.out.println("[Profesional] " + tarea);
                            } else if (tarea instanceof TareaPersonal) {
                                System.out.println("[Personal] " + tarea);
                            }
                        }
                    }
                    break;
                case 6:
                    for (Tarea tarea : tareas) {
                        if (!tarea.isCompletada()) {
                            if (tarea instanceof TareaProfesional) {
                                System.out.println("[Profesional] " + tarea);
                            } else if (tarea instanceof TareaPersonal) {
                                System.out.println("[Personal] " + tarea);
                            }
                        }
                    }
                    break;
                default:
                    System.out.println("Saliendo...");
            }
        } while (opcion != 7);
    }
}


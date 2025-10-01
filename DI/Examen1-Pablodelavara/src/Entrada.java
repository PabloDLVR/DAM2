import Controller.Plataforma;
import model.Videojuego;

import java.util.ArrayList;
import java.util.Scanner;

public class Entrada {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Plataforma plataforma = new Plataforma(new ArrayList<>());
        Videojuego videojuego1 = new Videojuego("TLU", "SX", "PEGI 18", 2014, 79.99);
        Videojuego videojuego2 = new Videojuego("UCH", "SX", "PEGI 18", 2014, 79.99);
        Videojuego videojuego3 = new Videojuego("BO2", "TY", "PEGI 18", 2014, 79.99);
        Videojuego videojuego4 = new Videojuego("BO2", "TY", "PEGI 18", 2014, 79.99);
        Videojuego videojuego5 = new Videojuego("PK", "GF", "PEGI 18", 2014, 79.99);
        Videojuego videojuego6 = new Videojuego("VAL", "RG", "PEGI 18", 2014, 79.99);
        int opcion = 0;
        do {
            System.out.println("1.- Añadir videojuego");
            System.out.println("2.- Eliminar videojuego");
            System.out.println("3.- Mostrar videojuegos");
            System.out.println("4.- Mostrar videojuego por tipo (Accion, Estrategia, RPG)");
            System.out.println("5.- Mostrar videojuego por Edad");
            System.out.println("6.- Mostar detalles de juego");
            System.out.println("7.- Mostrar precio total");
            System.out.println("8.- Salir");
            System.out.println("Que desea hacer?");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    plataforma.añadirJuego(videojuego1);
                    plataforma.añadirJuego(videojuego2);
                    plataforma.añadirJuego(videojuego3);
                    plataforma.añadirJuego(videojuego4);
                    plataforma.añadirJuego(videojuego5);
                    plataforma.añadirJuego(videojuego6);
                    System.out.println("Juegos añadidos correctamente");
                    break;
                case 2:
                    plataforma.eliminarJuego(videojuego1);
                    plataforma.eliminarJuego(videojuego2);
                    plataforma.eliminarJuego(videojuego3);
                    plataforma.eliminarJuego(videojuego4);
                    plataforma.eliminarJuego(videojuego5);
                    plataforma.eliminarJuego(videojuego6);
                    System.out.println("Juegos eliminados correctamente");
                    break;
                case 3:
                    plataforma.mostrarJuegos();
                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                default:
                    System.out.println("Saliendo...");
            }
        } while (opcion != 8);
    }
}


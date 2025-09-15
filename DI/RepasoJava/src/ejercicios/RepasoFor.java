package ejercicios;

import java.util.Scanner;

public class RepasoFor {
    static Scanner scanner = new Scanner(System.in);

    public void Ejercicio1() {
        int n = scanner.nextInt();
        if (n <= 0) n = 10;
        double suma = 0;
        for (int i = 0; i < n; i++) suma += scanner.nextDouble();
        System.out.printf("Media: %.2f\n", suma / n);
    }

    public void Ejercicio2() {
        int n = scanner.nextInt();
        for (int i = 0; i <= 10; i++)
            System.out.println(n + " x " + i + " = " + (n * i));
    }

    public void Ejercicio3() {
        for (int n = 0; n <= 10; n++) {
            System.out.println("Tabla de " + n);
            for (int i = 0; i <= 10; i++)
                System.out.println(n + " x " + i + " = " + (n * i));
        }
    }

    public void Ejercicio4() {

    }
}

package ejercicios;

import java.util.Scanner;

public class RepasoSwitch {
    static Scanner scanner = new Scanner(System.in);

    public void Ejercicio1() {
        char c = scanner.next().toLowerCase().charAt(0);
        switch (c) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                System.out.println("Vocal");
                break;
            default:
                System.out.println(Character.isLetter(c) ? "Consonante" : "No letra");
        }
    }

    public void Ejercicio2() {
        int n = scanner.nextInt();
        switch (Math.abs(n) % 2) {
            case 0:
                System.out.println("Par");
                break;
            default:
                System.out.println("Impar");
        }
    }

    public void Ejercicio3() {
        char op = scanner.next().charAt(0);
        double a = scanner.nextDouble(), b = scanner.nextDouble();
        switch (op) {
            case '+':
                System.out.println(a + b);
                break;
            case '-':
                System.out.println(a - b);
                break;
            case '*':
                System.out.println(a * b);
                break;
            case '/':
                System.out.println(b != 0 ? a / b : "Error: división por cero");
                break;
            default:
                System.out.println("Operador no válido");
        }
    }

    public void Ejercicio4() {
        int m = scanner.nextInt();
        switch (m) {
            case 12:
            case 1:
            case 2:
                System.out.println("Invierno");
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("Primavera");
                break;
            case 6:
            case 7:
            case 8:
                System.out.println("Verano");
                break;
            case 9:
            case 10:
            case 11:
                System.out.println("Otoño");
                break;
            default:
                System.out.println("Mes inválido");
        }
    }

    public void Ejercicio5() {
        Scanner sc = new Scanner(System.in);
        String code = scanner.next();
        switch (code) {
            case "P001":
                System.out.println("Producto: Lápiz, Precio: 0,50€");
                break;
            case "P002":
                System.out.println("Producto: Cuaderno, Precio: 2,00€");
                break;
            case "P003":
                System.out.println("Producto: Bolígrafo, Precio: 1,20€");
                break;
            default:
                System.out.println("Código no reconocido");
        }
    }
}

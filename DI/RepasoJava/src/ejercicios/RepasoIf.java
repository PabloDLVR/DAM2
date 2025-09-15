package ejercicios;

import java.util.Scanner;

public class RepasoIf {
    static Scanner scanner = new Scanner(System.in);

    public void Ejercicio1() {
        Scanner scanner = new Scanner(System.in);
        int entero1 = scanner.nextInt();
        int entero2 = scanner.nextInt();
        if (entero1 > entero2) {
            System.out.println("El primer numero es mayor que el segundo");
        }
        if (entero1 < entero2) {
            System.out.println("El primer numero es menor que el segundo");
        } else System.out.println("Ambos números son iguales");
    }

    public void Ejercicio2() {
        int n = scanner.nextInt();
        boolean multiplo = n % 20 == 0;
        boolean Rango = n >= -100 && n <= 100;
        if (multiplo && Rango) System.out.println("Es múltiplo de 20 y está entre -100 y 100");
        else if (multiplo) System.out.println("Es múltiplo de 20 y no está entre -100 y 100");
        else if (Rango) System.out.println("No es múltiplo de 20 y está entre -100 y 100");
        else System.out.println("No es múltiplo de 20 y no está entre -100 y 100");
    }

    public void Ejercicio3() {
        int n = scanner.nextInt();
        if (n < 1000 || n > 9999) {
            System.out.println("El número no tiene 4 dígitos");
            return;
        }
        int u = n % 10, d = (n / 10) % 10, c = (n / 100) % 10, m = n / 1000;
        if (u == m && d == c) System.out.println("Es capicúa");
        else System.out.println("No es capicúa");

    }

    public void Ejercicio4(){
        int n = scanner.nextInt();
        if (n % 2 == 0) n++; else n--;
        System.out.println(n);
    }

    public void Ejercicio5(){
        int n = scanner.nextInt();
        int res = n;
        if (n % 2 == 0) res += 2;
        if (n % 3 == 0) res += 3;
        if (n % 5 == 0) res += 5;
        if (res == n) res += 1;
        System.out.println(res);
    }

    public void Ejercicio6(){

    }

    public void Ejercicio7(){

    }
}

import java.util.Scanner;

public class Entrada {

    public static void main(String[] args) {

        //Lectura de teclado
        Scanner lector = new Scanner(System.in);
        //Impresion
        System.out.println("Primer programa de repaso");
        //Variables --> guardar valor que sera utilizado
        final String DNI = "123456A"; //Final --> Para que no se cambie el valor nunca
        System.out.println("Introdce tu nombre");
        String nombre = lector.next();
        String correo; // null
        System.out.println("Introdce tu edad");
        int edad = lector.nextInt();
        //Array --> lista no dinamica --> final
        //ArrayList / HashTable --> lista dinamica --> final
        System.out.printf("Mi nombre es %s y tengo %d años%n"
                , nombre, edad);



    }
}

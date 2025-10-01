package Controller;

import model.Accion;
import model.Estrategia;
import model.RPG;
import model.Videojuego;

import java.util.ArrayList;

public class Plataforma {
    Videojuego videojuego = new Videojuego();
    ArrayList<Videojuego> videojuegos;

    public Plataforma(ArrayList videojuegos) {
        this.videojuegos = videojuegos;
    }


    public void añadirJuego(Videojuego videojuego) {
        if (videojuego instanceof Accion) {
            videojuegos.add(videojuego);
        } else if (videojuego instanceof Estrategia) {
            videojuegos.add(videojuego);
        } else if (videojuego instanceof RPG) {
            videojuegos.add(videojuego);
        }
    }

    public void eliminarJuego(Videojuego videojuego) {
        for (int i = 0; i < videojuegos.size() - 1; i++) {
            if (videojuegos.contains(videojuego)) {
                videojuegos.remove(videojuego);
            }
        }
    }

    public void mostrarJuegos() {
        for (Videojuego videojuego : videojuegos) {
           videojuegos.toString();
            System.out.println(videojuego);
        }
    }

    public void mostrarJuegosPrecio() {

    }

    public void mostrarJuegosEdad() {

    }

    public void mostrarJuegosTipo() {

    }

    public void calcularPrecioTotal() {
        for (int i = 0; i < videojuegos.size() - 1; i++) {

        }
    }

}

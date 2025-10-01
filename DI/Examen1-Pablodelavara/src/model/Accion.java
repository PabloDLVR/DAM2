package model;

public class Accion extends Videojuego implements Descargable {
    int nivelViolencia;
    boolean modoMultijugador;
    double tamañoGB;

    public Accion() {

    }

    public Accion(int nivelViolencia, boolean modoMultijugador) {
        this.nivelViolencia = nivelViolencia;
        this.modoMultijugador = modoMultijugador;
    }

    public Accion(String titulo, String desarrollador, String clasificacionEdad, int anoLanzamiento, double precio, int nivelViolencia, boolean modoMultijugador) {
        super(titulo, desarrollador, clasificacionEdad, anoLanzamiento, precio);
        this.nivelViolencia = nivelViolencia;
        this.modoMultijugador = modoMultijugador;
    }

    @Override
    public void calcularPrecioFinal(double precio) {
        super.calcularPrecioFinal(precio);
        if (nivelViolencia > 3) {
            setPrecio(getPrecio()*0.5);
        } else if (modoMultijugador = true) {
            setPrecio(getPrecio()*0.10);
        }else {
            System.out.println("No tiene aumento de precio");
        }
    }

    public int getNivelViolencia() {
        return nivelViolencia;
    }

    public void setNivelViolencia(int nivelViolencia) {
        this.nivelViolencia = nivelViolencia;
    }

    public boolean isModoMultijugador() {
        return modoMultijugador;
    }

    public void setModoMultijugador(boolean modoMultijugador) {
        this.modoMultijugador = modoMultijugador;
    }
}

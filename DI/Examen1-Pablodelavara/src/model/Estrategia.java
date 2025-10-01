package model;

public class Estrategia extends Videojuego implements Descargable{
    int complejidad, duracionPartida;
    double tamañoGB;

    public Estrategia(){

    }

    public Estrategia(int complejidad, int duracionPartida) {
        this.complejidad = complejidad;
        this.duracionPartida = duracionPartida;
    }

    public Estrategia(String titulo, String desarrollador, String clasificacionEdad, int anoLanzamiento, double precio, int complejidad, int duracionPartida) {
        super(titulo, desarrollador, clasificacionEdad, anoLanzamiento, precio);
        this.complejidad = complejidad;
        this.duracionPartida = duracionPartida;
    }

    @Override
    public void calcularPrecioFinal(double precio) {
        super.calcularPrecioFinal(precio);
        if (complejidad==1){
            setPrecio(getPrecio()*0.3);
        }if (complejidad>1){
            setPrecio(getPrecio()*0.6);
        }if (complejidad>2){
            setPrecio(getPrecio()*0.9);
        }if (complejidad>3){
            setPrecio(getPrecio()*0.12);
        }if (complejidad>4){
            setPrecio(getPrecio()*0.15);
        }
    }

    public int getComplejidad() {
        return complejidad;
    }

    public void setComplejidad(int complejidad) {
        this.complejidad = complejidad;
    }

    public int getDuracionPartida() {
        return duracionPartida;
    }

    public void setDuracionPartida(int duracionPartida) {
        this.duracionPartida = duracionPartida;
    }
}

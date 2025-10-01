package model;

public class RPG extends Videojuego implements Descargable {
    boolean mundoAbierto;
    int historiaPrincipal;
    double tamañoGB;

    public RPG() {

    }

    public RPG(boolean mundoAbierto, int historiaPrincipal) {
        this.mundoAbierto = mundoAbierto;
        this.historiaPrincipal = historiaPrincipal;
    }

    public RPG(String titulo, String desarrollador, String clasificacionEdad, int anoLanzamiento, double precio, boolean mundoAbierto, int historiaPrincipal) {
        super(titulo, desarrollador, clasificacionEdad, anoLanzamiento, precio);
        this.mundoAbierto = mundoAbierto;
        this.historiaPrincipal = historiaPrincipal;
    }

    @Override
    public void calcularPrecioFinal(double precio) {
        super.calcularPrecioFinal(precio);
        if (mundoAbierto == true){
            setPrecio(getPrecio()*0.15);
        } else if (historiaPrincipal > 10) {
            setPrecio(getPrecio()*0.2);
        }
    }

    public boolean isMundoAbierto() {
        return mundoAbierto;
    }

    public void setMundoAbierto(boolean mundoAbierto) {
        this.mundoAbierto = mundoAbierto;
    }

    public int getHistoriaPrincipal() {
        return historiaPrincipal;
    }

    public void setHistoriaPrincipal(int historiaPrincipal) {
        this.historiaPrincipal = historiaPrincipal;
    }
}

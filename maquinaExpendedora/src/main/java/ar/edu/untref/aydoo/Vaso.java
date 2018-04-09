package ar.edu.untref.aydoo;

public class Vaso {
    private int cantidadDeAzucar;
    private boolean contieneCafe;
    private boolean contieneLeche;
    private boolean contieneTe;

    public int getCantidadDeAzucar() {
        return this.cantidadDeAzucar;
    }

    public boolean tieneCafe() {
        return contieneCafe;
    }

    public boolean tieneAzucar() {
        return this.cantidadDeAzucar > 0;
    }

    public boolean tieneTe() {
        return this.contieneTe;
    }

    public boolean tieneLeche() {
        return this.contieneLeche;
    }

    public void ponerSustancia(final Sustancia sustanciaAPoner) {
        if (sustanciaAPoner == Sustancia.CAFE) {
            this.contieneCafe = true;
        } else if (sustanciaAPoner == Sustancia.LECHE) {
            this.contieneLeche = true;
        } else if (sustanciaAPoner == Sustancia.TE) {
            this.contieneTe = true;
        }
    }

    public void ponerAzucar(final int cantidadAzucar) {
        this.cantidadDeAzucar = cantidadAzucar;
    }
}

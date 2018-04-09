package ar.edu.untref.aydoo;

public class MaquinaExpendedoraDeBebidas {
    private HacedorDeCafe miHacedorDeCafe;
    private Azucarero miAzucarero;
    private HacedorDeTeConLeche miHacedorDeTeConLeche;

    public MaquinaExpendedoraDeBebidas() {
        miAzucarero = new Azucarero();
        miHacedorDeCafe = new HacedorDeCafe();
        miHacedorDeTeConLeche = new HacedorDeTeConLeche();
    }

    public void hacerCafeConLecheConNDeAzucar(
            final Vaso unVaso, final int cantidadAzucar) {
        Vaso miVaso = unVaso;
        this.miHacedorDeCafe.hacerCafe(miVaso);
        this.miAzucarero.ponerAzucar(miVaso, cantidadAzucar);
    }

    public void hacerTeConLecheConNDeAzucar(
            final Vaso unVaso, final int cantidadAzucar) {
        this.miHacedorDeTeConLeche.hacerTeConLeche(unVaso);
        this.miAzucarero.ponerAzucar(unVaso, cantidadAzucar);
    }
}

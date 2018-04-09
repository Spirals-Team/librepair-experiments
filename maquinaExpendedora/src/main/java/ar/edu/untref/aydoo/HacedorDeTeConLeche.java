package ar.edu.untref.aydoo;

public class HacedorDeTeConLeche {
    private Lechero miLechero;
    private HacedorDeTe miTetero;

    public HacedorDeTeConLeche() {
        miTetero = new HacedorDeTe();
        miLechero = new Lechero();
    }

    public void hacerTeConLeche(final Vaso unVaso) {
        this.miTetero.ponerTe(unVaso);
        this.miLechero.ponerLeche(unVaso);
    }
}

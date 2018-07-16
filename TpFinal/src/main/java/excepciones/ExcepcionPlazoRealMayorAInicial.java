package excepciones;

public class ExcepcionPlazoRealMayorAInicial  extends RuntimeException {
    private final static String mensajePlazoRealMayorAInicial = "El plazo real debe ser menor al plazo incial";
    
    public ExcepcionPlazoRealMayorAInicial() {
        super(mensajePlazoRealMayorAInicial);
    }
}

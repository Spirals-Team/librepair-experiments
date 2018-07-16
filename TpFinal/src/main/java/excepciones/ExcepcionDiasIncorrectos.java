package excepciones;

public class ExcepcionDiasIncorrectos extends RuntimeException {
    private final static String mensajeDiasIncorrectos = "El plazo de dias para no debe ser menor a 30 dias";
    
    public ExcepcionDiasIncorrectos() {
        super(mensajeDiasIncorrectos);
    }
}

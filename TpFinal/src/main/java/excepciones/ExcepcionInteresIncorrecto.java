package excepciones;

public class ExcepcionInteresIncorrecto extends RuntimeException {
    private final static String mensajeInteresIncorrecto = "El interes ingresado debe ser mayor a 0";
    
    public ExcepcionInteresIncorrecto() {
        super(mensajeInteresIncorrecto);
    }
}

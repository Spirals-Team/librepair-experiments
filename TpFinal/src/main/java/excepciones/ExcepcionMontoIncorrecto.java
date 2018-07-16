package excepciones;

public class ExcepcionMontoIncorrecto extends RuntimeException {
    private final static String mensajeMontoIncorrecto = "El monto ingresado debe ser mayor a 0";
    
    public ExcepcionMontoIncorrecto() {
        super(mensajeMontoIncorrecto);
    }
}

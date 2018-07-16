package excepciones;

public class ExcepcionMontoMenorACotizacionDolar extends RuntimeException {
    private final static String mensajeMontoIncorrecto = "El monto ingresado no puede ser menor a la cotizacion del dolar";
    
    public ExcepcionMontoMenorACotizacionDolar() {
        super(mensajeMontoIncorrecto);
    }
}

package excepciones;

public class ExcepcionCotizacionDolarIncorrecta extends RuntimeException {
    private final static String mensajeCotizacionDolarIncorrecta = "La cotizacion debe ser mayor a 0";
    
    public ExcepcionCotizacionDolarIncorrecta() {
        super(mensajeCotizacionDolarIncorrecta);
    }
}
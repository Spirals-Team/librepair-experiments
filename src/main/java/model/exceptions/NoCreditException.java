package model.exceptions;


public class NoCreditException extends RuntimeException{

    public NoCreditException() {
        super("No tenes suficiente crédito para alquilar este vehiculo... sorry, macri gato");
    }
}

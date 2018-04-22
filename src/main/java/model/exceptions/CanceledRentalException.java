package model.exceptions;


public class CanceledRentalException extends RuntimeException {

    public CanceledRentalException(){
        super("Se da por cancelado el alquiler ya que no existe confirmación del dueño del vehiculo");
    }
}

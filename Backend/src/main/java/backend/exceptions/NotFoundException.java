package backend.exceptions;

/**
 *
 * @author lam@cphbusiness.dk
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super("Requested item could not be found");
    }  
}
package model.exceptions;

public class UserBlockedException extends RuntimeException {

    public UserBlockedException(){
        super("Usuario bloqueado para realizar publicaciones");
    }
}

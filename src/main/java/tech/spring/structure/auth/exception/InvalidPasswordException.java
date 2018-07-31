package tech.spring.structure.auth.exception;

public class InvalidPasswordException extends RuntimeException {

    private static final long serialVersionUID = 4573657931026884956L;

    public InvalidPasswordException(String message) {
        super(message);
    }

}

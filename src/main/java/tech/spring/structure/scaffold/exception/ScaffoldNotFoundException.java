package tech.spring.structure.scaffold.exception;

public class ScaffoldNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1254844305411434081L;

    public ScaffoldNotFoundException(String message) {
        super(message);
    }

}

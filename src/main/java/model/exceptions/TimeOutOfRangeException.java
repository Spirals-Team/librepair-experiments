package model.exceptions;

public class TimeOutOfRangeException extends RuntimeException {

    public TimeOutOfRangeException() {
        super("El tiempo mínimo de alquiler es 1 día y el máximo 5 días.");
    }
}

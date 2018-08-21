package academy.learnprogramming.exceptions;

public class SaveException extends RuntimeException {

    public SaveException() {
    }

    public SaveException(final String message) {
        super(message);
    }

    public SaveException(final String message, final Throwable ex) {
        super(message, ex);
    }

    public SaveException(final Exception ex) {
        super(ex);
    }
}
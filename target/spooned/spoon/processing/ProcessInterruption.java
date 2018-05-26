package spoon.processing;


public class ProcessInterruption extends java.lang.RuntimeException {
    public ProcessInterruption() {
    }

    public ProcessInterruption(java.lang.String message) {
        super(message);
    }

    public ProcessInterruption(java.lang.String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public ProcessInterruption(java.lang.Throwable cause) {
        super(cause);
    }
}


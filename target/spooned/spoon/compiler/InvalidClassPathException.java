package spoon.compiler;


public class InvalidClassPathException extends spoon.SpoonException {
    private static final long serialVersionUID = 1L;

    public InvalidClassPathException() {
        super();
    }

    public InvalidClassPathException(java.lang.String msg) {
        super(msg);
    }

    public InvalidClassPathException(java.lang.Throwable e) {
        super(e);
    }

    public InvalidClassPathException(java.lang.String msg, java.lang.Exception e) {
        super(msg, e);
    }
}


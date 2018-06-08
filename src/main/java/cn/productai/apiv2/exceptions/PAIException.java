package cn.productai.apiv2.exceptions;

public class PAIException extends Exception {
    public PAIException() {
    }

    public PAIException(String message) {
        super(message);
    }

    public PAIException(Throwable cause) {
        super(cause);
    }
}

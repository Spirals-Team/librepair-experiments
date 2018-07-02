package br.com.igorventurelli.mercadolivre.exception;

public class CodeNotValidException extends MercadoLivreException {

    public CodeNotValidException() {
        super();
    }

    public CodeNotValidException(final String message) {
        super(message);
    }

    public CodeNotValidException(final Throwable t) {
        super(t);
    }

    public CodeNotValidException(final String message, final Throwable t) {
        super(message, t);
    }
}

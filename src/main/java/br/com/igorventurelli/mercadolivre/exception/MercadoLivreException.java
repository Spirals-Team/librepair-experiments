package br.com.igorventurelli.mercadolivre.exception;

public class MercadoLivreException extends RuntimeException {

    public MercadoLivreException() {
        super();
    }

    public MercadoLivreException(final String message) {
        super(message);
    }

    public MercadoLivreException(final Throwable t) {
        super(t);
    }

    public MercadoLivreException(final String message, final Throwable t) {
        super(message, t);
    }
}

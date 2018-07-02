package br.com.igorventurelli.mercadolivre.exception;

public class AuthorizationFailureException extends MercadoLivreException {

    public AuthorizationFailureException() {
        super();
    }

    public AuthorizationFailureException(final String message) {
        super(message);
    }

    public AuthorizationFailureException(final Throwable t) {
        super(t);
    }

    public AuthorizationFailureException(final String message, final Throwable t) {
        super(message, t);
    }
}

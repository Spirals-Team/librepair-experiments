package br.com.igorventurelli.mercadolivre.domain.enumeration;

public enum TokenType {

    BEARER("bearer");

    private final String type;

    TokenType(final String type) {
        this.type = type;
    }

    public static TokenType getFromString(final String value) {
        for(final TokenType tokenType : TokenType.values()) {
            if(tokenType.type.equals(value)) return tokenType;
        }
        return null;
    }
}

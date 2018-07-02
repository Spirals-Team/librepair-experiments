package br.com.igorventurelli.mercadolivre.domain.enumeration;

public enum AppScope {

    READ("read"), OFFLINE_ACCESS("offline_access"), WRITE("write");

    private final String scope;

    AppScope(final String scope) {
        this.scope = scope;
    }

    public static AppScope getFromString(final String value) {
        for(final AppScope scope : AppScope.values()) {
            if(scope.scope.equals(value)) return scope;
        }
        return null;
    }
}

package ar.com.kfgodel.temas.application.auth;

import ar.com.kfgodel.dependencies.api.DependencyInjector;
import ar.com.kfgodel.webbyconvention.api.auth.WebCredential;
import convention.rest.api.tos.BackofficeUserTo;

import java.util.Optional;

/**
 * Created by sandro on 03/07/17.
 */
public class BackofficeCallbackAuthenticatorForAll extends BackofficeCallbackAuthenticator {
    public static BackofficeCallbackAuthenticator create(DependencyInjector appInjector) {
        BackofficeCallbackAuthenticator authenticator = new BackofficeCallbackAuthenticatorForAll();
        authenticator.appInjector = appInjector;
        return authenticator;
    }

    @Override
    public Optional<Object> apply(WebCredential webCredential) {
        Optional<BackofficeUserTo> backofficeUser = createToFrom(webCredential);
        Optional<Long> idAutenticado = backofficeUser
                .filter(BackofficeUserTo::hasGrantedAccess)
                .map(this::buscarIdDelUsuarioEnLaBase);
        return idAutenticado.map(Object.class::cast); // El casteo solo para ayudar al compilador
    }
}

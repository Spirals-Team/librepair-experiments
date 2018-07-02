package br.com.igorventurelli.mercadolivre;

import br.com.igorventurelli.mercadolivre.api.MercadoLivreAPI;
import br.com.igorventurelli.mercadolivre.api.impl.MercadoLivreAPIImpl;
import br.com.igorventurelli.mercadolivre.domain.AppInformation;
import br.com.igorventurelli.mercadolivre.exception.MercadoLivreException;

import java.io.IOException;

public final class MercadoLivre {

    private final AppInformation appInformation;
    private final MercadoLivreAPI api;


    //private UserInformation userInformation;

    public MercadoLivre(final Long clientId, final String clientSecret, final String code, final String callbackUrl) {
        appInformation = new AppInformation(clientId, clientSecret);
        api = new MercadoLivreAPIImpl(appInformation);
        api.authorize(code, callbackUrl);
    }
}

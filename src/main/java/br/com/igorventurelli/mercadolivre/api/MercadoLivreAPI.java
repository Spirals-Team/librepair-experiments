package br.com.igorventurelli.mercadolivre.api;

import java.io.IOException;

public interface MercadoLivreAPI {

    void authorize(final String code, final String callbackUrl);

    void uploadImage(final String imageUrl);

    void refreshToken();

}

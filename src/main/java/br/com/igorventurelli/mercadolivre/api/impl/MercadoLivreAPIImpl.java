package br.com.igorventurelli.mercadolivre.api.impl;

import br.com.igorventurelli.mercadolivre.api.MercadoLivreAPI;
import br.com.igorventurelli.mercadolivre.api.parser.AuthorizationResponseParser;
import br.com.igorventurelli.mercadolivre.domain.AppInformation;
import br.com.igorventurelli.mercadolivre.domain.UserInformation;
import br.com.igorventurelli.mercadolivre.domain.enumeration.AppScope;
import br.com.igorventurelli.mercadolivre.domain.enumeration.TokenType;
import br.com.igorventurelli.mercadolivre.exception.CodeNotValidException;
import br.com.igorventurelli.mercadolivre.exception.MercadoLivreException;
import br.com.igorventurelli.mercadolivre.http.HttpRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.HttpResponseStatus;
import com.ning.http.client.Response;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MercadoLivreAPIImpl implements MercadoLivreAPI {

    private final HttpRequest http = new HttpRequest();
    private final AppInformation appInformation;
    private UserInformation userInformation;

    public MercadoLivreAPIImpl(final AppInformation appInformation) {
        this.appInformation = appInformation;
    }

    @Override
    public void authorize(final String code, final String callbackUrl) {
        final FluentStringsMap params = new FluentStringsMap();
        params.add("grant_type", "authorization_code");
        params.add("client_id", String.valueOf(appInformation.getClientId()));
        params.add("client_secret", appInformation.getClientSecret());
        params.add("code", code);
        params.add("redirect_uri", callbackUrl);

        final Response response = http.post("/oauth/token", params);

        UserInformation u = new AuthorizationResponseParser().parseSingle(response);
        System.out.println(u);
    }

    @Override
    public void uploadImage(final String imageUrl) {

    }

    @Override
    public void refreshToken() {

    }
}

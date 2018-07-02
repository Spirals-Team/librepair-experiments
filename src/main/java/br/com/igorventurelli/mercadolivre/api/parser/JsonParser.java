package br.com.igorventurelli.mercadolivre.api.parser;

import br.com.igorventurelli.mercadolivre.exception.MercadoLivreException;
import com.google.gson.JsonObject;
import com.ning.http.client.Response;

import java.io.IOException;

public abstract class JsonParser<T> {

    public T parseSingle(final Response response) {
        final JsonObject jsonObject = createJsonObject(response);
        return parseSingleImpl(jsonObject, response.getStatusCode());
    }

    protected abstract T parseSingleImpl(final JsonObject jsonObject, final int httpStatus);

    private JsonObject createJsonObject(final Response response) {
        try {
            return new com.google.gson.JsonParser().parse(response.getResponseBody()).getAsJsonObject();
        } catch (IOException e) {
            throw new MercadoLivreException(e);
        }
    }
}

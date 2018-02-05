package io.descoped.client.http.internal;

import io.descoped.client.http.Client;
import io.descoped.client.http.Request;
import io.descoped.client.http.Response;
import io.descoped.client.http.ResponseBodyHandler;
import io.descoped.client.http.internal.httpRequest.HttpRequestExchange;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public class ClientImpl implements Client {

    public ClientImpl() {
    }

    @Override
    public Response send​(Request req, ResponseBodyHandler responseBodyHandler) {
        HttpRequestExchange exchange = new HttpRequestExchange(req, responseBodyHandler);
        return exchange.response();
    }

}

package io.descoped.client.http;

import io.descoped.client.http.internal.ClientImpl;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public interface Client<T> {

    static Client create() {
        return new ClientImpl();
    }

    <T> Response<T> send​(Request req, ResponseBodyHandler<T> responseBodyHandler);

}

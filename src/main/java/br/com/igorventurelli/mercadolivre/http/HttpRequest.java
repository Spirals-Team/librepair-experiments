package br.com.igorventurelli.mercadolivre.http;

import br.com.igorventurelli.mercadolivre.exception.MercadoLivreException;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public final class HttpRequest {

    private static final String API_URL = "https://api.mercadolibre.com%s";
    private static final Logger LOGGER = Logger.getLogger(HttpRequest.class);

    private final AsyncHttpClient http;

    public HttpRequest() {
        AsyncHttpClientConfig cf = new AsyncHttpClientConfig.Builder().setUserAgent("MERCADOLIVRE-JAVA-SDK-0.0.1").build();
        http = new AsyncHttpClient(cf);
    }

    public Response post(final String relativeUrl, final FluentStringsMap params) {
        BoundRequestBuilder brb = getRequestBuilder(relativeUrl);
        brb.setQueryParameters(params);

        return executeHttpRequest(brb);
    }

    private BoundRequestBuilder getRequestBuilder(final String relativeUrl) {
        return http.preparePost(buildFullApiUrl(relativeUrl))
                .addHeader("Accept", "application/json");
    }

    private String buildFullApiUrl(final String relativeUrl) {
        return String.format(API_URL, relativeUrl);
    }

    private Response executeHttpRequest(AsyncHttpClient.BoundRequestBuilder r) {

        try {
            return r.execute().get();


        } catch (InterruptedException | ExecutionException | IOException e) {
            LOGGER.fatal("There's a problem during the request", e);
            throw new MercadoLivreException(e);
        }
    }
}

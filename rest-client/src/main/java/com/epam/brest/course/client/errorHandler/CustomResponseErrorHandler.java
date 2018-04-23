package com.epam.brest.course.client.errorHandler;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 *client rest error handler.
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {
    /**
     *
     */
    private ResponseErrorHandler errorHandler =
            new DefaultResponseErrorHandler();

    /**
     * @param response .
     * @return response err.
     * @throws IOException .
     */
    @Override
    public final boolean hasError(final ClientHttpResponse response)
            throws IOException {
        return errorHandler.hasError(response);
    }

    /**
     * @param response with status.
     * @throws IOException exe.
     */
    @Override
    public final void handleError(final ClientHttpResponse response)
            throws IOException {
        throw new ServletDataAccessException(" ERROR: "
                + response.getStatusCode()
                + " :" + response.getStatusText()
                + " :" + response.getBody());
    }

}


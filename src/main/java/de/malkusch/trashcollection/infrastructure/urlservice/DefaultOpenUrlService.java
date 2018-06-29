package de.malkusch.trashcollection.infrastructure.urlservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;

class DefaultOpenUrlService implements OpenUrlService {

    private final Duration connectTimeout;
    private final Duration readTimeout;

    DefaultOpenUrlService(Duration connectTimeout, Duration readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public InputStream open(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout((int) connectTimeout.toMillis());
        connection.setReadTimeout((int) readTimeout.toMillis());
        return connection.getInputStream();
    }

}

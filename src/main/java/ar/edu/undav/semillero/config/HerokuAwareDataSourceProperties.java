package ar.edu.undav.semillero.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Alejandro Gomez
 */
public class HerokuAwareDataSourceProperties extends DataSourceProperties {

    private final URI herokuDsUri;

    public HerokuAwareDataSourceProperties(@Value("${JAWSDB_MARIA_URL:}") String herokuDsUrl) {
        herokuDsUri = parseHerokuUrl(herokuDsUrl);
    }

    @Override
    public String determineUrl() {
        return determineProperty(uri -> "jdbc:mysql://" + uri.getHost() + ":" + String.valueOf(uri.getPort()) + uri.getPath(), super::determineUrl);
    }

    @Override
    public String determineUsername() {
        return determineProperty(uri -> uri.getUserInfo().split(":")[0], super::determineUsername);
    }

    @Override
    public String determinePassword() {
        return determineProperty(uri -> uri.getUserInfo().split(":")[1], super::determinePassword);
    }

    private String determineProperty(Function<URI, String> mapper, Supplier<String> supplier) {
        return Optional.ofNullable(herokuDsUri)
                .map(mapper)
                .orElseGet(supplier);
    }

    private static URI parseHerokuUrl(String herokuDsUrl) {
        if (StringUtils.hasText(herokuDsUrl)) {
            try {
                return new URI(herokuDsUrl);
            } catch (URISyntaxException ex) {
                throw new RuntimeException("Invalid jaws db url", ex);
            }
        }
        return null;
    }
}

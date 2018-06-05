package pl.hycom.ip2018.searchengine.localsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import pl.hycom.ip2018.searchengine.localsearch.service.DefaultLocalSearch;
import pl.hycom.ip2018.searchengine.localsearch.service.FileChecker;
import pl.hycom.ip2018.searchengine.localsearch.service.LocalSearch;
import pl.hycom.ip2018.searchengine.localsearch.util.ZonedDateTimeStringConverter;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * IoC Container processed it to generate beans
 */
@Configuration
public class LocalSearchConfiguration {

    private final Environment environment;

    @Autowired
    public LocalSearchConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public LocalSearch localSearch() {
        return new DefaultLocalSearch();
    }

    /**
     * File Checker Bean to check if file is plain text or binary
     *
     * @return actual {@link FileChecker} bean
     */
    @Bean
    public FileChecker fileChecker() {
        return new FileChecker();
    }

    /**
     * Converter Bean for converting long values to ZonedDateTime objects
     *
     * @return actual {@link ZonedDateTimeStringConverter} bean
     */
    @Bean
    public ZonedDateTimeStringConverter zonedDateTimeStringConverter() {
        return new ZonedDateTimeStringConverter();
    }

    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("messages",
                Locale.forLanguageTag(environment.getProperty("rest.api.infoLanguage")));
    }
}

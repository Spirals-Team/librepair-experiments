package de.malkusch.trashcollection.infrastructure.urlservice;

import java.nio.file.Path;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class OpenUrlServiceConfiguration {

    @Bean
    @Primary
    OpenUrlService openUrlService(@Value("${downloadFailsafeDirectory}") Path bufferDirectory) {
        return new FailsafeUrlService(bufferDirectory, retryableOpenUrlService());
    }

    @Value("${connectTimeout}")
    private String connectTimeout;

    @Value("${readTimeout}")
    private String readTimeout;

    @Bean
    public OpenUrlService retryableOpenUrlService() {
        return new DefaultOpenUrlService(Duration.parse(connectTimeout), Duration.parse(readTimeout));
    }

}

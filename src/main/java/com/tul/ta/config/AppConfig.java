package com.tul.ta.config;

import com.tul.ta.client.ApiAuthentication;
import com.tul.ta.client.DefaultApiAuthentication;
import com.tul.ta.client.DefaultFlightApiCommunicator;
import com.tul.ta.client.FlightApiCommunicator;
import com.tul.ta.util.HttpQueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
//@EnableScheduling
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public ApiAuthentication apiAuthentication() {
        logger.info("DefaultApiAuthentication init");
        return new DefaultApiAuthentication();
    }

    @Bean
    public RestTemplate restTemplate() {
        logger.info("RestTemplate init");
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    public HttpQueryUtils httpQueryUtils() {
        logger.info("HttpQueryUtils init");
        return new HttpQueryUtils();
    }

    @Bean
    public FlightApiCommunicator flightApiCommunicator() {
        logger.info("DefaultFlightApiCommunicator init");
        return new DefaultFlightApiCommunicator();
    }
}

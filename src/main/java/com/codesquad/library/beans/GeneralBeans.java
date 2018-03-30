package com.codesquad.library.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GeneralBeans {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

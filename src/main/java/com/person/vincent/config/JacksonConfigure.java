package com.person.vincent.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Author: vincent
 * Date: 2018-04-10 11:10:00
 * Comment:
 */

@Component
public class JacksonConfigure {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = converter.getObjectMapper();
        mapper.setDateFormat(dateFormat);
        // 针对 hibernate 的循环引用栈溢出解决方案
        // mapper.registerModule(new Hibernate5Module());
        return converter;
    }
}

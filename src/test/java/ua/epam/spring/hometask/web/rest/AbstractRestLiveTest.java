package ua.epam.spring.hometask.web.rest;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import ua.epam.spring.hometask.web.rest.converters.PdfHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRestLiveTest {
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new PdfHttpMessageConverter());
        return converters;
    }

}

package com.ljd.hackajob.phonebook.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.ljd.hackajob.phonebook.api.exceptions.ConstraintViolationExceptionMapper;
import com.ljd.hackajob.phonebook.api.exceptions.LastDitchExceptionMapper;
import com.ljd.hackajob.phonebook.api.exceptions.PhonebookExceptionMapper;

@ApplicationPath("/api/v1")
@RolesAllowed("user")
public class ApplicationConfig extends Application {

    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ContactsAPI.class);
        classes.add(PhoneNumbersAPI.class);
        
        classes.add(PhonebookExceptionMapper.class);
        classes.add(ConstraintViolationExceptionMapper.class);
        classes.add(LastDitchExceptionMapper.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper();
        AnnotationIntrospector introspector = new JacksonAnnotationIntrospector();
        mapper.setAnnotationIntrospector(introspector);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(getDateFormat());

        // Set up the Jackson provider
        JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
        jsonProvider.setMapper(mapper);
        jsonProvider.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);

        // Add the provider to the set of singletons
        singletons.add(jsonProvider);

        return singletons;
    }

    private static DateFormat getDateFormat() {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        df.setTimeZone(TIME_ZONE);
        return df;
    }
}

package com.ljd.hackajob.phonebook.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public abstract class ModelTest {
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    
    protected ObjectMapper mapper;
    
    public ModelTest() {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        df.setTimeZone(TIME_ZONE);
        mapper = new ObjectMapper();        
        AnnotationIntrospector introspector = new JacksonAnnotationIntrospector();
        mapper.setAnnotationIntrospector(introspector);
        // Hide nulls
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Set the date format
        mapper.setDateFormat(df);
    }    
}

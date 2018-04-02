package com.gdc.aerodev.service;

import com.gdc.aerodev.service.exception.ServiceException;

import java.io.IOException;
import java.util.Properties;

public abstract class GenericService {

    protected String getTableName(String propertyName){
        Properties properties = new Properties();
        try {
            properties.load(GenericService.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new ServiceException("Error reading properties from '/db.properties' file: ", e);
        }
        return properties.getProperty(propertyName);
    }

}

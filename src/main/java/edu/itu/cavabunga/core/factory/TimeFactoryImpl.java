package edu.itu.cavabunga.core.factory;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Needed to mock calls to time()
 */
@Component
public class TimeFactoryImpl implements TimeFactory {

    @Override
    public Date getTime() {
        return new Date();
    }
}

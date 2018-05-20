package edu.itu.cavabunga.core.factory;

import java.util.Date;

/**
 * Needed to mock calls to time()
 */
public interface TimeFactory {

    Date getTime();
}

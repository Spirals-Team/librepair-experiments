package org.jboss.resteasy.core;

import java.util.Map;

/**
 * RESTEasy Configuration that contains mutable properties.
 * This is used for changing properties in configurable classes
 * such as ResteasyProviderFactory or ClientConfiguration.
 *
 */
public interface MutableConfiguration {
    Map<String, Object> getMutableProperties();
}

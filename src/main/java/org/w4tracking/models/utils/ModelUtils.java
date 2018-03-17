package org.w4tracking.models.utils;

import java.util.UUID;

public class ModelUtils {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

}

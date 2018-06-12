package com.meteoradesigner.util.validator;

//TODO
public class CommonValidatorUtil {
    /**
     * Throws @code{RuntimeException} if given entity is null.
     *
     * @param value   object for assert.
     * @param message error massage.
     */
    public static void notNull(Object value, String message) {
        if (value == null) {
            throw new RuntimeException(message);
        }
    }
}

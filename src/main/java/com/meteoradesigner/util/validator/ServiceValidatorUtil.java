package com.meteoradesigner.util.validator;


import com.meteoradesigner.util.exception.NotFoundException;

//TODO doc
public class ServiceValidatorUtil {
    public static void validateNotFound(boolean conditionToCheck, String toPrint) {
        if (!conditionToCheck) {
            throw new NotFoundException(toPrint);
        }
    }

    public static <ID> void validateNotFoundWithId(boolean conditionToCheck, ID id) {
        validateNotFound(conditionToCheck, "id=" + id);
    }

    public static <T> T validateNotFound(T objectToCheck, String toPrint) {
        validateNotFound(objectToCheck != null, toPrint);
        return objectToCheck;
    }

    public static <T, ID> T validateNotFoundWithId(T objectToCheck, ID id) {
        return validateNotFound(objectToCheck, "id=" + id);
    }

    public static boolean validateNotFoundBoolean(boolean conditionToCheck, String toPrint) {
        if (!conditionToCheck) {
            throw new NotFoundException(toPrint);
        }
        return true;
    }

    public static <ID> boolean validateNotFoundWithIdBoolean(boolean conditionToCheck, ID id) {
        return validateNotFoundBoolean(conditionToCheck, "id=" + id);
    }

    public static Throwable getPrimeCause(Throwable throwable) {
        Throwable cause = null;
        Throwable result = throwable;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}

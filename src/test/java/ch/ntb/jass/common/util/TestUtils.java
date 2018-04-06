package ch.ntb.jass.common.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.lang.annotation.Annotation;

import static org.junit.Assert.assertNull;

public class TestUtils {
    public static void assertNotAnnotatedWith(Class<?> type, Class<? extends Annotation> annotation) {
        assertNull(getTypeErrorMessage(type, "Must not be annotated with @" + JsonTypeInfo.class.getSimpleName()),
                type.getAnnotation(annotation));
    }

    public static String getTypeErrorMessage(Class<?> type, String message) {
        return message + ": " + System.lineSeparator() + "\t====> " + type.getName();
    }

    public static String quote(String name) {
        if (name == null)
            return null;
        return '"' + name.replace("\"", "\\\"") + '"';
    }

    public static boolean hasPublicDefaultConstructor(Class<?> type) {
        try {
            return type.getConstructor() != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}

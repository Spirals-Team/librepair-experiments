package ch.ntb.jass.common;

import ch.ntb.jass.common.proto.Message;
import ch.ntb.jass.common.util.TestUtils;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class MessageClassAnnotationValidationTestCase {
    private static final Class<Message> ROOT_CLASS = Message.class;

    @Test
    public void validateSubTypeAnnotation() {
        JsonSubTypes subTypesAnnotation = ROOT_CLASS.getAnnotation(JsonSubTypes.class);

        Set<Class<?>> types = new HashSet<>();
        Set<String> typeKeys = new HashSet<>();

        if (subTypesAnnotation != null) {
            for (JsonSubTypes.Type type : subTypesAnnotation.value()) {
                Class<?> annotatedSubclass = type.value();
                String subTypeKey = type.name();

                assertTrue(TestUtils.getTypeErrorMessage(annotatedSubclass, "Not a subclass of " + ROOT_CLASS.getName()),
                        ROOT_CLASS.isAssignableFrom(annotatedSubclass));
                assertTrue(TestUtils.getTypeErrorMessage(ROOT_CLASS, "class listed multiple times as a subtype: " + annotatedSubclass.getName()),
                        types.add(annotatedSubclass));
                assertTrue(TestUtils.getTypeErrorMessage(ROOT_CLASS, "duplicate subtype key: " + TestUtils.quote(subTypeKey)),
                        typeKeys.add(subTypeKey));
            }
        }
    }
}

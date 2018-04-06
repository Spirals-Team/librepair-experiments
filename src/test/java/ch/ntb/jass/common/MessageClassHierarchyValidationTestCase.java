package ch.ntb.jass.common;

import ch.ntb.jass.common.proto.Message;
import ch.ntb.jass.common.util.TestUtils;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.SubclassMatchProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class MessageClassHierarchyValidationTestCase {
    private static final Class<Message> ROOT_CLASS = Message.class;

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> params() {
        // find all subclasses
        Set<Class<?>> allSubclasses = new HashSet<>();

        new FastClasspathScanner()
                .matchSubclassesOf(ROOT_CLASS, new SubclassMatchProcessor<Message>() {
                    @Override
                    public void processMatch(Class<? extends Message> subclass) {
                        allSubclasses.add(subclass);
                    }
                }).scan();

        // return test parameters
        return allSubclasses.stream()
                .map(aClass -> new Object[]{aClass, Collections.unmodifiableSet(allSubclasses)})
                .collect(Collectors.toList());
    }

    private final Class<? extends Message> messageType;
    private final Set<Class<? extends Message>> allSubclasses;

    public MessageClassHierarchyValidationTestCase(Class<? extends Message> messageType, Set<Class<? extends Message>> allSubclasses) {
        this.messageType = messageType;
        this.allSubclasses = allSubclasses;
    }

    @Test
    public void checkEntity() {
        JsonSubTypes subTypesAnnotation = ROOT_CLASS.getAnnotation(JsonSubTypes.class);

        Set<Class<?>> allAnnotatedSubclasses = new HashSet<>();
        Set<Class<?>> subclasses = new HashSet<>();
        JsonSubTypes.Type typeAnnotationEntry = null;

        // collect annotated type information

        if (subTypesAnnotation != null) {
            for (JsonSubTypes.Type type : subTypesAnnotation.value()) {
                Class<?> annotatedSubclass = type.value();
                allAnnotatedSubclasses.add(annotatedSubclass);
                if (annotatedSubclass.equals(messageType)) {
                    if (typeAnnotationEntry == null)
                        typeAnnotationEntry = type;
                }
            }
        }

        for (Class<?> subclass : allAnnotatedSubclasses)
            if (messageType.isAssignableFrom(subclass))
                subclasses.add(subclass);

        assertNotNull(TestUtils.getTypeErrorMessage(messageType, "is not annotated as a subtype of " + ROOT_CLASS.getName()),
                subTypesAnnotation);

        assertTrue(TestUtils.getTypeErrorMessage(messageType, "must either be abstract or annotated as a subtype of " + ROOT_CLASS.getName()),
                Modifier.isAbstract(messageType.getModifiers()) || allAnnotatedSubclasses.contains(messageType));

        assertTrue(TestUtils.getTypeErrorMessage(messageType, "must either be abstract or have a public default constructor"),
                Modifier.isAbstract(messageType.getModifiers()) || TestUtils.hasPublicDefaultConstructor(messageType));

        assertTrue(TestUtils.getTypeErrorMessage(messageType, "abstract classes must not be leaf classes"),
                !Modifier.isAbstract(messageType.getModifiers()) || !subclasses.isEmpty());

        TestUtils.assertNotAnnotatedWith(messageType, JsonTypeInfo.class);
        TestUtils.assertNotAnnotatedWith(messageType, JsonSubTypes.class);
    }

}

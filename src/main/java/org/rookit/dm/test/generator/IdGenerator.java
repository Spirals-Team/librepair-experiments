
package org.rookit.dm.test.generator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@SuppressWarnings("javadoc")
@BindingAnnotation
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
public @interface IdGenerator {
    // Empty on purpose
}

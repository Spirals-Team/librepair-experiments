package com.pholser.junit.quickcheck;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({ PARAMETER, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Also {
    String[] value();
}

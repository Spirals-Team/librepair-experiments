package com.github.funthomas424242;


import com.github.funthomas424242.rades.annotations.RadesBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@RadesBuilder
@Retention(RetentionPolicy.SOURCE)
public @interface TestMetaAnnotation {
}

package com.github.funthomas424242;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@MetaAnnotation
@Retention(RetentionPolicy.SOURCE)
public @interface MetaMetaAnnotation {
}

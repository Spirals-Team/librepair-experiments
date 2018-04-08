package com.github.funthomas424242.rades.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Generiert eine Builderklasse.
 * Die generierte Builderklasse wird im gleichen Package erstellt.
 * Als Name der Builderklasse wird der Name des annotierten Types + "Builder" verwendet.
 *
 * @version $Version$, $Date$
 * @since 1.1.0
 */
@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface AddBuilder {

    String simpleBuilderClassName() default "";

}
package tech.spring.structure.scaffold;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Inherited
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface ScaffoldProperty {

    String type() default "text";

    String gloss() default "";

    String help() default "";

    String autocomplete() default "";

    boolean autofocus() default false;

    boolean hidden() default false;

    boolean disabled() default false;

}

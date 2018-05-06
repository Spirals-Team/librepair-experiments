package pl.hycom.ip2018.searchengine.aggregate.notation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a fields as to be usage by AggregateSearch
 * in future response searching.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Autowired
public @interface Usage {

    /**
     * Declares whether the field is processed
     * <p>Defaults to {@code true}.
     */
    String value() default "true";
}

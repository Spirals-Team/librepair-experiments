package cn.productai.api.core.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Thinkpad on 2017/6/30.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceDescriptionAttribute {
    String Name() default "";
    String ServiceType() default "search";
    String ServiceId() default "";
}

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
public @interface ParaSignAttribute {
    String Name() default "";
    boolean IsNeedUrlEncode() default false;
}

package cn.productai.api.core.attribute;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IgnoreExtraParasAttribute {

}

/**
 * Created on 2018/1/22.
 */
package com.alicp.jetcache.anno;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheInvalidate {

    /**
     * If you want to use multi backend cache system, you can setup multi "cache area" in configuration,
     * this attribute specifies the name of the "cache area" you want to use.
     * @return the name of cache area
     */
    String area() default CacheConsts.DEFAULT_AREA;

    /**
     * The name of this Cache instance which need a remove operation.
     * @return the name of the cache which need a remove operation
     */
    String name();

    /**
     * Specify the key by expression script, optional. If not specified,
     * use all parameters of the target method and keyConvertor to generate one.
     * @return an expression script which specifies key
     */
    String key() default CacheConsts.UNDEFINED_STRING;

    /**
     * Expression attribute used for conditioning the invalidation.
     */
    String condition() default CacheConsts.UNDEFINED_STRING;
}

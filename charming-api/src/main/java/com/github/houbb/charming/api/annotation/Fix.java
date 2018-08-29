/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> 修复 </p>
 *
 * <pre> Created: 2018/8/17 上午10:19  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
@Charming
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fix {
}

/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.config;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/21 下午5:29  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public interface CharmingLevel {

    /**
     * 级别
     * @return int 级别
     */
    int level();


    /**
     * 当前级别是否比对比的更加严重
     * @param other 其他对象
     * @return 是否
     */
    boolean serious(CharmingLevel other);

}

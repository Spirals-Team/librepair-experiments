/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.constant;

import com.github.houbb.charming.api.config.CharmingLevel;
import com.github.houbb.paradise.common.util.ObjectUtil;

/**
 * <p> </p>
 *
 * TODO: 如何保证足够的细粒度？足够灵活？
 * <pre> Created: 2018/8/23 下午2:06  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public enum CharmingLevelEnum implements CharmingLevel {

    /**
     * 错误
     */
    ERROR(90),
    /**
     * 警告
     */
    WARN(80),
    /**
     * 正常
     */
    NORMAL(70),
    /**
     * none
     */
    NONE(0),
    ;

    /**
     * 错误级别
     */
    private final int level;

    CharmingLevelEnum(int level) {
        this.level = level;
    }

    @Override
    public int level() {
        return this.level;
    }

    @Override
    public boolean serious(CharmingLevel other) {
        if(ObjectUtil.isNull(other)) {
            return false;
        }
        return this.level >= other.level();
    }

}

/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.rule;

import com.github.houbb.charming.api.config.CharmingLevel;

/**
 * <p> 规则 </p>
 *
 * 为了简单起见，只支持 单个的发现 + 校验规则。
 *
 * <pre> Created: 2018/8/21 下午5:38  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public interface Rule {

    /**
     * 错误级别
     * 1. 错误修正
     * 2. 警告修正
     * @return
     */
    CharmingLevel getFixLevel();

    /**
     * 目标规则的发现者
     * @return
     */
    Discover getDiscover();

    /**
     * 目标规则的校验者
     * @return
     */
    Evaluate getEvaluate();

    /**
     * 目标规则的修正者
     * @return
     */
    Fix getFix();

}

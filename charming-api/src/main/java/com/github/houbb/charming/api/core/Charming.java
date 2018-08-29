/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.core;

import com.github.houbb.charming.api.config.CharmingConfig;
import com.github.houbb.charming.api.domain.CharmingResult;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/21 下午5:38  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public interface Charming {

    /**
     * 执行验证
     * @param original 原始字符串
     * @return 规范结果
     */
    CharmingResult charimg(final String original);

    /**
     * 执行验证
     * @param original 原始字符串
     * @param charmingConfig 配置
     * @return 规范结果
     */
    CharmingResult charimg(final String original, final CharmingConfig charmingConfig);

}

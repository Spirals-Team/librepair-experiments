/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.support.context;

import com.github.houbb.charming.api.config.CharmingConfig;

import java.util.Collection;

/**
 * <p> 上下文 </p>
 *
 * <pre> Created: 2018/8/21 下午5:38  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public interface CharmingContext {

    /**
     * 原始内容
     * @return
     */
    String original();

    /**
     * 修正配置
     * @return
     */
    CharmingConfig config();

    /**
     * 当前 context 明细
     * @return
     */
    CharmingRuleEntry currentEntry();

    /**
     * 已经执行过的 context 明细列表
     * @return
     */
    Collection<CharmingRuleEntry> passedEntries();

}

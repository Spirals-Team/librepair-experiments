/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.support.context;

import com.github.houbb.charming.api.domain.DiscoverResult;
import com.github.houbb.charming.api.domain.EvaluateResult;
import com.github.houbb.charming.api.domain.FixResult;
import com.github.houbb.charming.api.rule.Rule;

import java.util.Collection;

/**
 * <p> 每次规则执行的明细 </p>
 *
 * <pre> Created: 2018/8/21 下午5:38  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public interface CharmingRuleEntry {

    /**
     * 处理的原始字符串
     * @return
     */
    String original();

    /**
     * 当前执行规则
     * @return
     */
    Rule rule();

    /**
     * 发现的结果集
     * @return
     */
    Collection<DiscoverResult> discoverResults();

    /**
     * 校验的结果集
     * @return
     */
    Collection<EvaluateResult> evaluateResults();

    /**
     * 修正的结果
     * @return
     */
    FixResult fixResult();

}

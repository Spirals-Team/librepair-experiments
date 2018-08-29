/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.config;

import com.github.houbb.charming.api.rule.Rule;
import com.github.houbb.charming.api.support.listener.DiscoverListener;
import com.github.houbb.charming.api.support.listener.EvaluateListener;
import com.github.houbb.charming.api.support.listener.FixListener;

import java.util.Collection;

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
public interface CharmingConfig {

    /**
     * 规则集合
     * @return 集合
     */
    Collection<Rule> getRules();

    /**
     * 发现目标监听器集合
     * @return 集合
     */
    Collection<DiscoverListener> getDiscoverListeners();

    /**
     * 校验监听器集合
     * @return 集合
     */
    Collection<EvaluateListener> getEvaluateListeners();

    /**
     * 修复监听器集合
     * @return 集合
     */
    Collection<FixListener> getFixListeners();

}

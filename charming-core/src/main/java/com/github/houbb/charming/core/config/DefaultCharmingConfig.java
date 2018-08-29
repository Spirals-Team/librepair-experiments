/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.config;

import com.github.houbb.charming.api.config.CharmingConfig;
import com.github.houbb.charming.api.constant.CharmingLevelEnum;
import com.github.houbb.charming.api.rule.Rule;
import com.github.houbb.charming.api.support.listener.DiscoverListener;
import com.github.houbb.charming.api.support.listener.EvaluateListener;
import com.github.houbb.charming.api.support.listener.FixListener;
import com.github.houbb.charming.core.plugin.bif.listener.WordCountListener;
import com.github.houbb.charming.core.plugin.bif.rule.discover.EnWordNumDiscover;
import com.github.houbb.charming.core.plugin.bif.rule.discover.NumberDiscover;
import com.github.houbb.charming.core.plugin.bif.rule.evaluate.WhitespaceEvaluate;
import com.github.houbb.charming.core.plugin.bif.rule.fix.WhitespaceFix;
import com.github.houbb.charming.core.rule.DefaultRuleBuilder;

import java.util.Collection;
import java.util.Collections;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/17 上午10:40  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class DefaultCharmingConfig implements CharmingConfig {

    private Collection<Rule> rules;
    private Collection<DiscoverListener> discoverListeners;
    private Collection<EvaluateListener> evaluateListeners;
    private Collection<FixListener> fixListeners;

    @Override
    public Collection<Rule> getRules() {
        return rules;
    }

    public void setRules(Collection<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public Collection<DiscoverListener> getDiscoverListeners() {
        return discoverListeners;
    }

    public void setDiscoverListeners(Collection<DiscoverListener> discoverListeners) {
        this.discoverListeners = discoverListeners;
    }

    @Override
    public Collection<EvaluateListener> getEvaluateListeners() {
        return evaluateListeners;
    }

    public void setEvaluateListeners(Collection<EvaluateListener> evaluateListeners) {
        this.evaluateListeners = evaluateListeners;
    }

    @Override
    public Collection<FixListener> getFixListeners() {
        return fixListeners;
    }

    public void setFixListeners(Collection<FixListener> fixListeners) {
        this.fixListeners = fixListeners;
    }



    /**
     * 静态类单例
     */
    private static class DefaultCharmingConfigHolder {
        private static Rule rule = DefaultRuleBuilder.aDefaultRule()
                .fixLevel(CharmingLevelEnum.ERROR)
                .discover(new EnWordNumDiscover())
                .evaluate(new WhitespaceEvaluate())
                .fix(new WhitespaceFix())
                .build();
        private static Collection<Rule> rules = Collections.singletonList(rule);
        private static Collection<FixListener> fixListeners = Collections.singletonList(new WordCountListener());
        private static DefaultCharmingConfig DEFAULT_CONFIG = DefaultCharmingConfigBuilder
                .aDefaultCharmingConfig()
                .rules(rules)
                .fixListeners(fixListeners)
                .build();
    }

    /**
     * 默认配置
     *
     * @return 默认配置
     */
    public static DefaultCharmingConfig defaultConfig() {
        return DefaultCharmingConfigHolder.DEFAULT_CONFIG;
    }

}

/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.config;

import com.github.houbb.charming.api.rule.Rule;
import com.github.houbb.charming.api.support.listener.DiscoverListener;
import com.github.houbb.charming.api.support.listener.EvaluateListener;
import com.github.houbb.charming.api.support.listener.FixListener;

import java.util.Collection;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/24 下午2:59  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public final class DefaultCharmingConfigBuilder {
    private Collection<Rule>             rules;
    private Collection<DiscoverListener> discoverListeners;
    private Collection<EvaluateListener> evaluateListeners;
    private Collection<FixListener>      fixListeners;

    private DefaultCharmingConfigBuilder() {
    }

    public static DefaultCharmingConfigBuilder aDefaultCharmingConfig() {
        return new DefaultCharmingConfigBuilder();
    }

    public DefaultCharmingConfigBuilder rules(Collection<Rule> rules) {
        this.rules = rules;
        return this;
    }

    public DefaultCharmingConfigBuilder discoverListeners(Collection<DiscoverListener> discoverListeners) {
        this.discoverListeners = discoverListeners;
        return this;
    }

    public DefaultCharmingConfigBuilder evaluateListeners(Collection<EvaluateListener> evaluateListeners) {
        this.evaluateListeners = evaluateListeners;
        return this;
    }

    public DefaultCharmingConfigBuilder fixListeners(Collection<FixListener> fixListeners) {
        this.fixListeners = fixListeners;
        return this;
    }

    public DefaultCharmingConfig build() {
        DefaultCharmingConfig defaultCharmingConfig = new DefaultCharmingConfig();
        defaultCharmingConfig.setRules(rules);
        defaultCharmingConfig.setDiscoverListeners(discoverListeners);
        defaultCharmingConfig.setEvaluateListeners(evaluateListeners);
        defaultCharmingConfig.setFixListeners(fixListeners);
        return defaultCharmingConfig;
    }
}

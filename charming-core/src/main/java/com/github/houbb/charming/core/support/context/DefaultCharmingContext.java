/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.support.context;

import com.github.houbb.charming.api.config.CharmingConfig;
import com.github.houbb.charming.api.domain.DiscoverResult;
import com.github.houbb.charming.api.domain.EvaluateResult;
import com.github.houbb.charming.api.domain.FixResult;
import com.github.houbb.charming.api.rule.Rule;
import com.github.houbb.charming.api.support.context.CharmingContext;
import com.github.houbb.charming.api.support.context.CharmingRuleEntry;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/23 下午2:05  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class DefaultCharmingContext implements CharmingContext {

    private final String original;

    private final CharmingConfig config;

    private CharmingRuleEntry ruleEntry;

    private Collection<CharmingRuleEntry> ruleEntries = new ArrayList<>();

    public DefaultCharmingContext(String original, CharmingConfig config) {
        this.original = original;
        this.config = config;
    }

    @Override
    public String original() {
        return original;
    }

    @Override
    public CharmingConfig config() {
        return config;
    }

    @Override
    public CharmingRuleEntry currentEntry() {
        return ruleEntry;
    }

    @Override
    public Collection<CharmingRuleEntry> passedEntries() {
        return ruleEntries;
    }

    public void setRuleEntry(CharmingRuleEntry ruleEntry) {
        this.ruleEntry = ruleEntry;
    }

    public void addPassedEntry(CharmingRuleEntry passedEntry) {
        ruleEntries.add(passedEntry);
    }
}

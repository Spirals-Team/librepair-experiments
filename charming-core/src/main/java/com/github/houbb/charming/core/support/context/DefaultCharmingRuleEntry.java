/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.support.context;

import com.github.houbb.charming.api.domain.DiscoverResult;
import com.github.houbb.charming.api.domain.EvaluateResult;
import com.github.houbb.charming.api.domain.FixResult;
import com.github.houbb.charming.api.rule.Rule;
import com.github.houbb.charming.api.support.context.CharmingRuleEntry;

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
public class DefaultCharmingRuleEntry implements CharmingRuleEntry {

    private String original;

    private Rule rule;

    private Collection<DiscoverResult> discoverResults;

    private Collection<EvaluateResult> evaluateResults;

    private FixResult fixResult;

    @Override
    public String original() {
        return original;
    }

    @Override
    public Rule rule() {
        return rule;
    }

    @Override
    public Collection<DiscoverResult> discoverResults() {
        return discoverResults;
    }

    @Override
    public Collection<EvaluateResult> evaluateResults() {
        return evaluateResults;
    }

    @Override
    public FixResult fixResult() {
        return fixResult;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setDiscoverResults(Collection<DiscoverResult> discoverResults) {
        this.discoverResults = discoverResults;
    }

    public void setEvaluateResults(Collection<EvaluateResult> evaluateResults) {
        this.evaluateResults = evaluateResults;
    }

    public void setFixResult(FixResult fixResult) {
        this.fixResult = fixResult;
    }

}

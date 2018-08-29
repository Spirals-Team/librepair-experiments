/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.domain;

import com.github.houbb.charming.api.rule.Rule;

import java.util.Collection;

/**
 * <p> 规则结果 </p>
 *
 * <pre> Created: 2018/8/21 下午5:56  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class RuleResult extends AbstractResult {

    private static final long serialVersionUID = 1186965968721608159L;

    /**
     * 规则
     */
    private Rule rule;

    /**
     * 发现结果集合
     */
    private Collection<DiscoverResult> discoverResults;

    /**
     * 校验结果集合
     */
    private Collection<EvaluateResult> evaluateResults;

    /**
     * 修正结果
     */
    private FixResult fixResult;

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public Collection<DiscoverResult> getDiscoverResults() {
        return discoverResults;
    }

    public void setDiscoverResults(Collection<DiscoverResult> discoverResults) {
        this.discoverResults = discoverResults;
    }

    public Collection<EvaluateResult> getEvaluateResults() {
        return evaluateResults;
    }

    public void setEvaluateResults(Collection<EvaluateResult> evaluateResults) {
        this.evaluateResults = evaluateResults;
    }

    public FixResult getFixResult() {
        return fixResult;
    }

    public void setFixResult(FixResult fixResult) {
        this.fixResult = fixResult;
    }

    @Override
    public String toString() {
        return "RuleResult{" +
                "rule=" + rule +
                ", discoverResults=" + discoverResults +
                ", evaluateResults=" + evaluateResults +
                ", fixResult=" + fixResult +
                '}';
    }
}

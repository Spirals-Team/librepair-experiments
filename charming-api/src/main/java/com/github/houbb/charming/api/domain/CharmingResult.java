/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.domain;

import java.util.Collection;

/**
 * <p> 优化结果 </p>
 *
 * <pre> Created: 2018/8/21 下午6:06  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class CharmingResult extends AbstractResult  {

    private static final long serialVersionUID = 6195454698846134852L;

    /**
     * 修正的结果
     */
    private final String result;

    /**
     * 规则执行结果
     */
    private final Collection<RuleResult> ruleResults;

    public CharmingResult(String result, Collection<RuleResult> ruleResults) {
        this.result = result;
        this.ruleResults = ruleResults;
    }

    public String getResult() {
        return result;
    }

    public Collection<RuleResult> getRuleResults() {
        return ruleResults;
    }

    @Override
    public String toString() {
        return "CharmingResult{" +
                "result='" + result + '\'' +
                ", ruleResults=" + ruleResults +
                '}';
    }
}

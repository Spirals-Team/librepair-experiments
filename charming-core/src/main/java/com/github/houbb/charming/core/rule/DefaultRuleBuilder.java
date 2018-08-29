/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.rule;

import com.github.houbb.charming.api.config.CharmingLevel;
import com.github.houbb.charming.api.rule.Discover;
import com.github.houbb.charming.api.rule.Evaluate;
import com.github.houbb.charming.api.rule.Fix;

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
public final class DefaultRuleBuilder {
    private CharmingLevel fixLevel;
    private Discover      discover;
    private Evaluate      evaluate;
    private Fix           fix;

    private DefaultRuleBuilder() {
    }

    public static DefaultRuleBuilder aDefaultRule() {
        return new DefaultRuleBuilder();
    }

    public DefaultRuleBuilder fixLevel(CharmingLevel fixLevel) {
        this.fixLevel = fixLevel;
        return this;
    }

    public DefaultRuleBuilder discover(Discover discover) {
        this.discover = discover;
        return this;
    }

    public DefaultRuleBuilder evaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
        return this;
    }

    public DefaultRuleBuilder fix(Fix fix) {
        this.fix = fix;
        return this;
    }

    public DefaultRule build() {
        DefaultRule defaultRule = new DefaultRule();
        defaultRule.setFixLevel(fixLevel);
        defaultRule.setDiscover(discover);
        defaultRule.setEvaluate(evaluate);
        defaultRule.setFix(fix);
        return defaultRule;
    }
}

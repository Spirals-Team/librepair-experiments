/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.rule;

import com.github.houbb.charming.api.config.CharmingLevel;
import com.github.houbb.charming.api.rule.Discover;
import com.github.houbb.charming.api.rule.Evaluate;
import com.github.houbb.charming.api.rule.Fix;
import com.github.houbb.charming.api.rule.Rule;

import lombok.Builder;
import lombok.Data;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/23 下午4:42  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class DefaultRule implements Rule {

    private CharmingLevel fixLevel;

    private Discover discover;

    private Evaluate evaluate;

    private Fix fix;

    @Override
    public CharmingLevel getFixLevel() {
        return fixLevel;
    }

    public void setFixLevel(CharmingLevel fixLevel) {
        this.fixLevel = fixLevel;
    }

    @Override
    public Discover getDiscover() {
        return discover;
    }

    public void setDiscover(Discover discover) {
        this.discover = discover;
    }

    @Override
    public Evaluate getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public Fix getFix() {
        return fix;
    }

    public void setFix(Fix fix) {
        this.fix = fix;
    }



}

/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.rule;

import com.github.houbb.charming.api.domain.EvaluateResult;
import com.github.houbb.charming.api.domain.FixResult;
import com.github.houbb.charming.api.support.context.CharmingContext;

import java.util.Collection;

/**
 * <p> 修正 </p>
 *
 * <pre> Created: 2018/8/21 下午5:38  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public interface Fix {

    /**
     * 修正
     * @param evaluateResults
     * @return
     */
    FixResult fix(final Collection<EvaluateResult> evaluateResults,
                  final CharmingContext charmingContext);

}

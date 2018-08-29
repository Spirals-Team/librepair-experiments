/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.rule;

import com.github.houbb.charming.api.domain.DiscoverResult;
import com.github.houbb.charming.api.domain.EvaluateResult;
import com.github.houbb.charming.api.support.context.CharmingContext;

import java.util.Collection;

/**
 * <p> 校验 </p>
 *
 * <pre> Created: 2018/8/21 下午5:38  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public interface Evaluate {

    /**
     * 校验
     * @param discoverResults
     * @return
     */
    Collection<EvaluateResult> evaluate(final Collection<DiscoverResult> discoverResults,
                                        final CharmingContext charmingContext);

}

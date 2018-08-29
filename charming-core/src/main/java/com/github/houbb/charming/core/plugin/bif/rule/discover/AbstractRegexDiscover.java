/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.plugin.bif.rule.discover;

import com.github.houbb.charming.api.domain.DiscoverResult;
import com.github.houbb.charming.api.rule.Discover;
import com.github.houbb.charming.api.support.context.CharmingContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> 正则表达式发现类 </p>
 *
 * <pre> Created: 2018/8/29 下午3:45  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public abstract class AbstractRegexDiscover implements Discover {

    /**
     * 正则表达式
     * @return 正则表达式
     */
    protected abstract String regex();

    @Override
    public Collection<DiscoverResult> discover(String original, CharmingContext charmingContext) {
        List<DiscoverResult> discoverResults = new ArrayList<>();
        String regEx = regex();
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(original);
        while (m.find()) {
            DiscoverResult discoverResult = new DiscoverResult();
            discoverResult.setTarget(m.group());
            discoverResult.setStartIndex(m.start());
            discoverResult.setEndIndex(m.end());
            discoverResults.add(discoverResult);
        }
        return discoverResults;
    }

}

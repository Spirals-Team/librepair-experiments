/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.plugin.bif.rule.discover;


/**
 * <p> 数字发现 </p>
 * ps: 此处英文，标点符号应该是相近的。可以考虑统一处理。
 * <pre> Created: 2018/8/17 上午11:03  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class NumberDiscover extends AbstractRegexDiscover {

    @Override
    protected String regex() {
        return "[0-9]+";
    }

}

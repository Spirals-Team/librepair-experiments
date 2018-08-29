/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.plugin.bif.rule.discover;


import com.github.houbb.charming.core.util.RegexUtil;

/**
 * <p> 英语单词+数字 </p>
 * i'll -
 * <pre> Created: 2018/8/17 上午11:03  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class EnWordNumDiscover extends AbstractRegexDiscover {

    @Override
    protected String regex() {
        return RegexUtil.Regex.EN_WORD_NUM;
    }

}

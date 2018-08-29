/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.util;

import com.github.houbb.paradise.common.util.ArrayUtil;
import com.github.houbb.paradise.common.util.StringUtil;

/**
 * <p> 正则工具类 </p>
 *
 * 换行符号？
 * <pre> Created: 2018/8/29 下午3:53  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public final class RegexUtil {

    public static class Regex {

        /**
         * 英语单词和数字
         */
        public static final String EN_WORD_NUM = "[A-Za-z0-9-/:\\[\\]\\(\\)%+\\.!\\{\\}]{1,}";

        /**
         * 换行符
         */
        public static final String RETURN_LINES = "[.\\n]*";
        /**
         * 英文标点符号
         * http://blog.51cto.com/itlearner/155212
         */
        public static final String EN_PUNCTUATION_MARK = "[-,.?:;'\"!`]|(-{2})|(\\.{3})|(\\(\\))|(\\[\\])|(\\{\\})";
        /**
         * 中文标点符号
         * https://blog.csdn.net/cysear/article/details/80435756
         */
        public static final String ZH_PUNCTUATION_MARK = "^[\\u3002|\\uff1f|\\uff01|\\uff0c|\\u3001|\\uff1b|\\uff1a|\\u201c|\\u201d|\\u2018|\\u2019|\\uff08|\\uff09|\\u300a|\\u300b|\\u3008|\\u3009|\\u3010|\\u3011|\\u300e|\\u300f|\\u300c|\\u300d|\\ufe43|\\ufe44|\\u3014|\\u3015|\\u2026|\\u2014|\\uff5e|\\ufe4f|\\uffe5]$";
    }

    /**
     * 是否为空格或者满足正则表达式
     * @param c 字符
     * @param regexes 正则数组
     * @return 是否成功
     */
    public static boolean isWhitespaceOrRegex(char c, final String... regexes) {
        String cStr = String.valueOf(c);
        if(StringUtil.BLANK.equals(cStr)) {
            return true;
        }
        if(ArrayUtil.isEmpty(regexes)) {
            return false;
        }

        for(String regex : regexes) {
            if(cStr.matches(regex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为空格或者标点符号或者换行符
     * @param c 字符
     * @return 是否
     */
    public static boolean isWhitespaceOrMark(char c) {
        return isWhitespaceOrRegex(c, Regex.EN_PUNCTUATION_MARK,
                Regex.ZH_PUNCTUATION_MARK,
                Regex.RETURN_LINES);
    }

    private RegexUtil(){}

}

/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.plugin.bif.rule.evaluate;


import com.github.houbb.charming.api.constant.CharmingLevelEnum;
import com.github.houbb.charming.api.domain.DiscoverResult;
import com.github.houbb.charming.api.domain.EvaluateResult;
import com.github.houbb.charming.api.domain.entry.EvaluateAroundEntry;
import com.github.houbb.charming.api.rule.Evaluate;
import com.github.houbb.charming.api.support.context.CharmingContext;
import com.github.houbb.charming.core.util.RegexUtil;
import com.github.houbb.paradise.common.util.ArrayUtil;
import com.github.houbb.paradise.common.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p> 空格校验 </p>
 *
 * <pre> Created: 2018/8/17 上午11:20  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class WhitespaceEvaluate implements Evaluate {

    /**
     * TODO: 这里的 advice 是否可以隐藏起来
     *
     * @param discoverResults 发现的结果
     * @param charmingContext 上下文
     * @return 校验的结果集合
     */
    @Override
    public Collection<EvaluateResult> evaluate(Collection<DiscoverResult> discoverResults,
                                               CharmingContext charmingContext) {
        Collection<EvaluateResult> resultCollection = new ArrayList<>();

        final String original = charmingContext.original();

        for (DiscoverResult discoverResult : discoverResults) {
            EvaluateAroundEntry aroundEntry = fitRule(discoverResult, original);
            boolean bothFit = aroundEntry.isPreFit() && aroundEntry.isNextFit();
            if (!bothFit) {
                EvaluateResult evaluateResult = new EvaluateResult();
                evaluateResult.setStartIndex(discoverResult.getStartIndex());
                evaluateResult.setEndIndex(discoverResult.getEndIndex());
                evaluateResult.setTarget(discoverResult.getTarget());
                evaluateResult.setAdvice(advice(evaluateResult));
                evaluateResult.setLevel(CharmingLevelEnum.ERROR);
                evaluateResult.setAroundEntry(aroundEntry);

                resultCollection.add(evaluateResult);
            }
        }

        return resultCollection;
    }


    /**
     * 对于  advice 的处理一定要交给框架本身。但是允许用户自行定义。
     *
     * @param eval 校验
     * @return
     */
    private String advice(EvaluateResult eval) {
        final String format = "{ %s } 位于 [%d, %d] 不满足规则：{ %s }";
        return String.format(format, eval.getTarget(), eval.getStartIndex(),
                eval.getEndIndex(), "XXX");
    }

    /**
     * 是否满足规则
     *
     * @param discoverResult 结果
     * @param original       最原始的字符串
     * @return 是否
     */
    private EvaluateAroundEntry fitRule(DiscoverResult discoverResult, final String original) {
        EvaluateAroundEntry evaluateAroundEntry = new EvaluateAroundEntry();
        int start = discoverResult.getStartIndex();
        int end = discoverResult.getEndIndex();


        if (0 == start) {
            evaluateAroundEntry.setPreFit(true);

            // 下一个元素的处理
            char nextChar = original.charAt(end + 1);
            fillNext(evaluateAroundEntry, nextChar);
            return evaluateAroundEntry;
        }
        if (end >= original.length() + 1) {
            evaluateAroundEntry.setNextFit(true);
            return evaluateAroundEntry;
        }

        char preChar = original.charAt(start - 1);
        fillPre(evaluateAroundEntry, preChar);

        char nextChar = original.charAt(end);
        fillNext(evaluateAroundEntry, nextChar);

        return evaluateAroundEntry;
    }

    /**
     * 上一个元素
     * @param evaluateAroundEntry 明细
     * @param character 符号
     */
    private void fillPre(EvaluateAroundEntry evaluateAroundEntry, Character character) {
        boolean fit = RegexUtil.isWhitespaceOrRegex(character, RegexUtil.Regex.RETURN_LINES,
                RegexUtil.Regex.ZH_PUNCTUATION_MARK);
        evaluateAroundEntry.setPreFit(fit);
        evaluateAroundEntry.setPreContent(character);
    }

    /**
     * 下一个元素
     * @param evaluateAroundEntry 明细
     * @param character 符号
     */
    private void fillNext(EvaluateAroundEntry evaluateAroundEntry, Character character) {
        boolean fit = RegexUtil.isWhitespaceOrMark(character);
        evaluateAroundEntry.setNextFit(fit);
        evaluateAroundEntry.setNextContent(character);
    }

}

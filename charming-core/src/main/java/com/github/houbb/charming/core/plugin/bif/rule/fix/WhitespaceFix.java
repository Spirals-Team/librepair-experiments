/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.plugin.bif.rule.fix;

import com.github.houbb.charming.api.domain.EvaluateResult;
import com.github.houbb.charming.api.domain.FixResult;
import com.github.houbb.charming.api.domain.entry.EvaluateAroundEntry;
import com.github.houbb.charming.api.rule.Fix;
import com.github.houbb.charming.api.rule.Rule;
import com.github.houbb.charming.api.support.context.CharmingContext;
import com.github.houbb.charming.core.plugin.bif.domain.WhitespaceFixPrepare;
import com.github.houbb.paradise.common.util.CollectionUtil;
import com.github.houbb.paradise.common.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p> 空格修复类 </p>
 *
 * <pre> Created: 2018/8/17 下午2:02  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class WhitespaceFix implements Fix {

    private CharmingContext charmingContext;

    @Override
    public FixResult fix(Collection<EvaluateResult> evaluateResults, CharmingContext charmingContext) {
        this.charmingContext = charmingContext;

        StringBuilder stringBuilder = new StringBuilder();

        List<WhitespaceFixPrepare> fixPrepares = fixPrepareList(evaluateResults, charmingContext.original());
        fixPrepares.forEach(prepare->{
            if(prepare.isIgnoreFix()) {
                stringBuilder.append(prepare.getTarget());
            } else {
                stringBuilder.append(prepare.getPreContent());
                final EvaluateAroundEntry aroundEntry = prepare.getAroundEntry();
                if(!aroundEntry.isPreFit()) {
                    stringBuilder.append(StringUtil.BLANK);
                }
                stringBuilder.append(prepare.getTarget());
                if(!aroundEntry.isNextFit()) {
                    stringBuilder.append(StringUtil.BLANK);
                }
            }
        });

        FixResult fixResult = new FixResult();
        fixResult.setTarget(charmingContext.currentEntry().original());
        fixResult.setResult(stringBuilder.toString());
        return fixResult;
    }

    /**
     * 为了保证可以多线程执行
     * -----target-----target----
     * 截断成为：
     * ----- + target
     * ----- + target
     * ---- remainStr
     * @param evaluateResults 校验结果集合
     * @param original 最原始的字符串
     * @return 结果
     */
    private List<WhitespaceFixPrepare> fixPrepareList(Collection<EvaluateResult> evaluateResults,
                                                      final String original) {
        if (CollectionUtil.isEmpty(evaluateResults)) {
            return Collections.emptyList();
        }

        List<WhitespaceFixPrepare> fixPrepares = new ArrayList<>();
        // 每次截取之后剩下的部分
        String remainStr = original;
        // 最后的下标
        int lastEndIndex = 0;
        for(EvaluateResult evaluateResult : evaluateResults) {
            if (doFix(evaluateResult)) {
                WhitespaceFixPrepare fixPrepare = new WhitespaceFixPrepare();
                int startIndex = evaluateResult.getStartIndex();
                final String content = evaluateResult.getTarget();

                fixPrepare.setTarget(content);
                fixPrepare.setPreContent(original.substring(lastEndIndex, startIndex));
                fixPrepare.setAroundEntry(evaluateResult.getAroundEntry());

                lastEndIndex = evaluateResult.getEndIndex();
                remainStr = original.substring(lastEndIndex);

                fixPrepares.add(fixPrepare);
            }
        }

        Optional<WhitespaceFixPrepare> fixPrepareOptional = WhitespaceFixPrepare.remainFixPrepare(remainStr);
        fixPrepareOptional.ifPresent(fixPrepares::add);
        return fixPrepares;
    }

    /**
     * 执行修复
     *
     * @param evaluateResult 校验结果
     * @return 是否需要修复
     */
    private boolean doFix(EvaluateResult evaluateResult) {
        Rule rule = charmingContext.currentEntry().rule();
        return evaluateResult != null
                && evaluateResult.getLevel().serious(rule.getFixLevel());
    }

}

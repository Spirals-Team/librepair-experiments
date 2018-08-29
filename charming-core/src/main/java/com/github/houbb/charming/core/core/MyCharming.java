/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.core;

import com.github.houbb.charming.api.config.CharmingConfig;
import com.github.houbb.charming.api.core.Charming;
import com.github.houbb.charming.api.domain.CharmingResult;
import com.github.houbb.charming.api.domain.DiscoverResult;
import com.github.houbb.charming.api.domain.EvaluateResult;
import com.github.houbb.charming.api.domain.FixResult;
import com.github.houbb.charming.api.domain.RuleResult;
import com.github.houbb.charming.api.rule.Discover;
import com.github.houbb.charming.api.rule.Evaluate;
import com.github.houbb.charming.api.rule.Fix;
import com.github.houbb.charming.api.rule.Rule;
import com.github.houbb.charming.api.support.listener.DiscoverListener;
import com.github.houbb.charming.api.support.listener.EvaluateListener;
import com.github.houbb.charming.api.support.listener.FixListener;
import com.github.houbb.charming.core.config.DefaultCharmingConfig;
import com.github.houbb.charming.core.support.context.DefaultCharmingContext;
import com.github.houbb.charming.core.support.context.DefaultCharmingRuleEntry;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.paradise.common.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * <p> 默认实现 </p>
 * 所有的报告都是用线程处理好。
 * <pre> Created: 2018/8/17 上午10:35  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class MyCharming implements Charming {

    private static final Log LOG = LogFactory.getLog(MyCharming.class);

    @Override
    public CharmingResult charimg(String original) {
        return this.charimg(original, DefaultCharmingConfig.defaultConfig());
    }

    /**
     * TODO: 每个监听的 collection 都可以使用多线程使用。
     * 这里的代码感觉不够简洁 需要优化
     *
     * @param original       原始字符串
     * @param charmingConfig 配置
     * @return
     */
    @Override
    public CharmingResult charimg(String original, CharmingConfig charmingConfig) {
        DefaultCharmingContext charmingContext = new DefaultCharmingContext(original, charmingConfig);

        Collection<Rule> rules = charmingConfig.getRules();
        if (CollectionUtil.isEmpty(rules)) {
            LOG.warn("对应规则为空，无需处理，直接返回。");
            return new CharmingResult(original, Collections.emptyList());
        }

        Collection<RuleResult> ruleResults = new ArrayList<>();
        String originalTemp = original;
        for (Rule rule : rules) {


            DefaultCharmingRuleEntry defaultCharmingRuleEntry = new DefaultCharmingRuleEntry();
            defaultCharmingRuleEntry.setRule(rule);
            defaultCharmingRuleEntry.setOriginal(originalTemp);
            charmingContext.setRuleEntry(defaultCharmingRuleEntry);

            Discover discover = rule.getDiscover();
            Evaluate evaluate = rule.getEvaluate();
            Fix fix = rule.getFix();

            defaultCharmingRuleEntry.setRule(rule);

            //1. 发现
            Collection<DiscoverListener> discoverListeners = charmingConfig.getDiscoverListeners();
            if (CollectionUtil.isNotEmpty(discoverListeners)) {
                discoverListeners.forEach(d -> d.before(charmingContext));
            }

            Collection<DiscoverResult> discoverResults = discover.discover(originalTemp, charmingContext);
            defaultCharmingRuleEntry.setDiscoverResults(discoverResults);
            charmingContext.setRuleEntry(defaultCharmingRuleEntry);
            if (CollectionUtil.isNotEmpty(discoverListeners)) {
                discoverListeners.forEach(d -> d.after(charmingContext));
            }

            //2. 校验
            Collection<EvaluateListener> evaluateListeners = charmingConfig.getEvaluateListeners();
            if (CollectionUtil.isNotEmpty(evaluateListeners)) {
                evaluateListeners.forEach(e -> e.before(charmingContext));
            }
            Collection<EvaluateResult> evaluateResults = evaluate.evaluate(discoverResults, charmingContext);
            defaultCharmingRuleEntry.setEvaluateResults(evaluateResults);
            charmingContext.setRuleEntry(defaultCharmingRuleEntry);
            if (CollectionUtil.isNotEmpty(evaluateListeners)) {
                evaluateListeners.forEach(e -> e.after(charmingContext));
            }

            //3. 修正
            Collection<FixListener> fixListeners = charmingConfig.getFixListeners();
            if (CollectionUtil.isNotEmpty(fixListeners)) {
                fixListeners.forEach(f -> f.before(charmingContext));
            }
            FixResult fixResult = fix.fix(evaluateResults, charmingContext);
            defaultCharmingRuleEntry.setFixResult(fixResult);
            charmingContext.setRuleEntry(defaultCharmingRuleEntry);
            originalTemp = fixResult.getResult();
            if (CollectionUtil.isNotEmpty(fixListeners)) {
                fixListeners.forEach(f -> f.after(charmingContext));
            }

            // 更新上下文
            charmingContext.addPassedEntry(defaultCharmingRuleEntry);
            charmingContext.setRuleEntry(null);

            // 构建当前规则的执行结果
            RuleResult ruleResult = new RuleResult();
            ruleResult.setRule(rule);
            ruleResult.setDiscoverResults(discoverResults);
            ruleResult.setEvaluateResults(evaluateResults);
            ruleResult.setFixResult(fixResult);
            ruleResults.add(ruleResult);
        }

        return new CharmingResult(originalTemp, ruleResults);
    }

}

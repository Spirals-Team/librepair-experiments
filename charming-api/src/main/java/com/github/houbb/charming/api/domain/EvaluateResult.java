/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.domain;

import com.github.houbb.charming.api.config.CharmingLevel;
import com.github.houbb.charming.api.domain.entry.EvaluateAroundEntry;

/**
 * <p> 检验结果 </p>
 *
 * <pre> Created: 2018/8/21 下午5:56  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class EvaluateResult extends AbstractResult {

    private static final long serialVersionUID = -8565899116657628750L;

    /**
     * 开始位置
     */
    private int startIndex;

    /**
     * 结束位置
     */
    private int endIndex;

    /**
     * 目标内容
     */
    private String target;

    /**
     * 建议
     */
    private String advice;

    /**
     * 级别
     */
    private CharmingLevel level;

    /**
     * 上下的明细
     */
    private EvaluateAroundEntry aroundEntry;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public CharmingLevel getLevel() {
        return level;
    }

    public void setLevel(CharmingLevel level) {
        this.level = level;
    }

    public EvaluateAroundEntry getAroundEntry() {
        return aroundEntry;
    }

    public void setAroundEntry(EvaluateAroundEntry aroundEntry) {
        this.aroundEntry = aroundEntry;
    }
}

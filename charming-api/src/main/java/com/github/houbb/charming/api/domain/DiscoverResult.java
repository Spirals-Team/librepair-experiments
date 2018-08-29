/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.domain;

/**
 * <p> 发现结果 </p>
 *
 * <pre> Created: 2018/8/21 下午5:56  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class DiscoverResult extends AbstractResult {

    private static final long serialVersionUID = 1889528351778326720L;

    /**
     * 开始下标
     */
    private int startIndex;

    /**
     * 结束下标
     */
    private int endIndex;

    /**
     * 目标元素
     */
    private String target;

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
}

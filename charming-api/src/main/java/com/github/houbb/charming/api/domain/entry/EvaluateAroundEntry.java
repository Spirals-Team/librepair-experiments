/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.domain.entry;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/8/29 下午4:18  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class EvaluateAroundEntry {

    /**
     * 全部满足
     */
    private boolean bothFit;

    /**
     * 前一个是否合适
     */
    private boolean preFit;

    /**
     * 后一个是否合适
     */
    private boolean nextFit;

    /**
     * 后一个内容
     */
    private Character preContent;

    /**
     * 前一个内容
     */
    private Character nextContent;

    public boolean isPreFit() {
        return preFit;
    }

    public void setPreFit(boolean preFit) {
        this.preFit = preFit;
    }

    public boolean isNextFit() {
        return nextFit;
    }

    public void setNextFit(boolean nextFit) {
        this.nextFit = nextFit;
    }

    public boolean isBothFit() {
        return bothFit;
    }

    public Character getPreContent() {
        return preContent;
    }

    public void setPreContent(Character preContent) {
        this.preContent = preContent;
    }

    public Character getNextContent() {
        return nextContent;
    }

    public void setNextContent(Character nextContent) {
        this.nextContent = nextContent;
    }

    public void setBothFit(boolean bothFit) {
        this.bothFit = bothFit;
    }
}

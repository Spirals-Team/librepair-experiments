/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.plugin.bif.domain;

import com.github.houbb.charming.api.domain.entry.EvaluateAroundEntry;
import com.github.houbb.paradise.common.util.StringUtil;

import java.io.Serializable;
import java.util.Optional;

/**
 * <p> 空白修复准备 </p>
 *
 * <pre> Created: 2018/8/23 下午2:21  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class WhitespaceFixPrepare implements Serializable {

    private static final long serialVersionUID = 3810816629062986386L;

    private String target;

    private int startIndex;

    private int endIndex;

    /**
     * 前一个字符串的内容
     */
    private String preContent;

    /**
     * 是否忽略修正
     */
    private boolean ignoreFix;

    /**
     * 前后明细
     */
    private EvaluateAroundEntry aroundEntry;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

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

    public String getPreContent() {
        return preContent;
    }

    public void setPreContent(String preContent) {
        this.preContent = preContent;
    }

    public boolean isIgnoreFix() {
        return ignoreFix;
    }

    public void setIgnoreFix(boolean ignoreFix) {
        this.ignoreFix = ignoreFix;
    }

    public EvaluateAroundEntry getAroundEntry() {
        return aroundEntry;
    }

    public void setAroundEntry(EvaluateAroundEntry aroundEntry) {
        this.aroundEntry = aroundEntry;
    }

    public static Optional<WhitespaceFixPrepare> remainFixPrepare(final String remainStr) {
        if(StringUtil.isEmpty(remainStr)) {
            return Optional.empty();
        }
        WhitespaceFixPrepare fixPrepare = new WhitespaceFixPrepare();
        fixPrepare.setIgnoreFix(true);
        fixPrepare.setTarget(remainStr);
        return Optional.of(fixPrepare);
    }
}

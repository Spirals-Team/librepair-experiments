/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.domain;

/**
 * <p> 修正结果 </p>
 *
 * <pre> Created: 2018/8/21 下午5:56  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class FixResult extends AbstractResult {

    private static final long serialVersionUID = 143685862812770998L;

    /**
     * 原始字符串
     */
    private String target;

    /**
     * 执行结果
     */
    private String result;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

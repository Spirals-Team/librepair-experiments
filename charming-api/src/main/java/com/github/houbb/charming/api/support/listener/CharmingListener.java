/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.api.support.listener;

import com.github.houbb.charming.api.support.context.CharmingContext;

/**
 * <p> 发现目标对象监听器 </p>
 *
 * 备注：这个接口不应该直接被继承
 *
 * <pre> Created: 2018/8/21 下午5:43  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public interface CharmingListener {

    /**
     * 方法执行之前
     * @param charmingContext
     */
    void before(final CharmingContext charmingContext);

    /**
     * 方法执行之后
     * @param charmingContext
     */
    void after(final CharmingContext charmingContext);

}

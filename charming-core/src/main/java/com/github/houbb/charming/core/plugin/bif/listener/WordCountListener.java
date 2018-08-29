/*
 * Copyright (c)  2018. houbinbin Inc.
 * charming All rights reserved.
 */

package com.github.houbb.charming.core.plugin.bif.listener;

import com.github.houbb.charming.api.support.context.CharmingContext;
import com.github.houbb.charming.api.support.listener.FixListener;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

/**
 * <p> 字数统计监听器 </p>
 *
 * <pre> Created: 2018/8/23 下午4:33  </pre>
 * <pre> Project: charming  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class WordCountListener implements FixListener {

    private static final Log LOG = LogFactory.getLog(WordCountListener.class);

    @Override
    public void before(CharmingContext charmingContext) {
        //do nothing...
    }

    @Override
    public void after(CharmingContext charmingContext) {
        final String original = charmingContext.original();
        LOG.info("总字数：{}", original.length());
    }

}

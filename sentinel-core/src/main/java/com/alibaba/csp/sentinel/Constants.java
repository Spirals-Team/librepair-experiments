/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel;

import com.alibaba.csp.sentinel.node.ClusterNode;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.node.EntranceNode;
import com.alibaba.csp.sentinel.slotchain.StringResourceWrapper;
import com.alibaba.csp.sentinel.slots.system.SystemRule;

/**
 * @author qinan.qn
 * @author youji.zj
 * @author jialiang.linjl
 */
public class Constants {

    public final static int MAX_CONTEXT_NAME_SIZE = 2000;
    public final static int MAX_SLOT_CHAIN_SIZE = 6000;
    public final static String ROOT_ID = "machine-root";
    public final static String CONTEXT_DEFAULT_NAME = "default_context_name";

    public final static DefaultNode ROOT = new EntranceNode(new StringResourceWrapper(ROOT_ID, EntryType.IN),
        Env.nodeBuilder.buildClusterNode());

    /**
     * statistics for {@link SystemRule} checking.
     */
    public final static ClusterNode ENTRY_NODE = new ClusterNode();

    /**
     * 超过这个时间的请求不作为平均时间计算
     */
    public final static int TIME_DROP_VALVE = 4900;

    /*** 框架功能打开或者关闭的开关 ***/
    public static volatile boolean ON = true;

}
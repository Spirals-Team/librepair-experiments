/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.job.lite.api.strategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 作业分片策略选项.
 * 
 * @author zhangliang
 */
@Getter
@RequiredArgsConstructor
public final class JobShardingStrategyOption {
    
    /**
     * 作业名称.
     */
    private final String jobName;
    
    /**
     * 作业分片总数.
     */
    private final int shardingTotalCount;
}

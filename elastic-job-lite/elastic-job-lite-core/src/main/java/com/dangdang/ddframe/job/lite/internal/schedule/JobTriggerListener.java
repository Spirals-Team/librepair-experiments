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

package com.dangdang.ddframe.job.lite.internal.schedule;

import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

import com.dangdang.ddframe.job.lite.internal.execution.ExecutionService;
import com.dangdang.ddframe.job.lite.internal.sharding.ShardingService;

import lombok.RequiredArgsConstructor;

/**
 * 作业触发监听器.
 * 
 * @author zhangliang
 */
@RequiredArgsConstructor
public final class JobTriggerListener extends TriggerListenerSupport {
    
    private final ExecutionService executionService;
    
    private final ShardingService shardingService;
    
    @Override
    public String getName() {
        return "JobTriggerListener";
    }
    
    @Override
    public void triggerMisfired(final Trigger trigger) {
        executionService.setMisfire(shardingService.getLocalHostShardingItems());
    }
}

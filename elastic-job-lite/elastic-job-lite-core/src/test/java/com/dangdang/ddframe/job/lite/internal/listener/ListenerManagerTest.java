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

package com.dangdang.ddframe.job.lite.internal.listener;

import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.internal.config.ConfigurationListenerManager;
import com.dangdang.ddframe.job.lite.internal.election.LeaderListenerManager;
import com.dangdang.ddframe.job.lite.internal.failover.FailoverListenerManager;
import com.dangdang.ddframe.job.lite.internal.guarantee.GuaranteeListenerManager;
import com.dangdang.ddframe.job.lite.internal.instance.InstanceShutdownListenerManager;
import com.dangdang.ddframe.job.lite.internal.instance.InstanceTriggerListenerManager;
import com.dangdang.ddframe.job.lite.internal.sharding.ShardingListenerManager;
import com.dangdang.ddframe.job.lite.internal.storage.JobNodeStorage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.unitils.util.ReflectionUtils;

import java.util.Collections;

import static org.mockito.Mockito.verify;

public class ListenerManagerTest {
    
    @Mock
    private JobNodeStorage jobNodeStorage;
    
    @Mock
    private LeaderListenerManager leaderListenerManager;
    
    @Mock
    private ShardingListenerManager shardingListenerManager;
    
    @Mock
    private FailoverListenerManager failoverListenerManager;
    
    @Mock
    private InstanceShutdownListenerManager instanceShutdownListenerManager;
    
    @Mock
    private InstanceTriggerListenerManager instanceTriggerListenerManager;
    
    @Mock
    private ConfigurationListenerManager configurationListenerManager;
    
    @Mock
    private GuaranteeListenerManager guaranteeListenerManager;
    
    @Mock
    private RegistryCenterConnectionStateListener regCenterConnectionStateListener;
    
    private final ListenerManager listenerManager = new ListenerManager(null, "test_job", Collections.<ElasticJobListener>emptyList());
    
    @Before
    public void setUp() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);
        ReflectionUtils.setFieldValue(listenerManager, "jobNodeStorage", jobNodeStorage);
        ReflectionUtils.setFieldValue(listenerManager, "leaderListenerManager", leaderListenerManager);
        ReflectionUtils.setFieldValue(listenerManager, "shardingListenerManager", shardingListenerManager);
        ReflectionUtils.setFieldValue(listenerManager, "failoverListenerManager", failoverListenerManager);
        ReflectionUtils.setFieldValue(listenerManager, "instanceShutdownListenerManager", instanceShutdownListenerManager);
        ReflectionUtils.setFieldValue(listenerManager, "instanceTriggerListenerManager", instanceTriggerListenerManager);
        ReflectionUtils.setFieldValue(listenerManager, "configurationListenerManager", configurationListenerManager);
        ReflectionUtils.setFieldValue(listenerManager, "guaranteeListenerManager", guaranteeListenerManager);
        ReflectionUtils.setFieldValue(listenerManager, "regCenterConnectionStateListener", regCenterConnectionStateListener);
    }
    
    @Test
    public void assertStartAllListeners() {
        listenerManager.startAllListeners();
        verify(leaderListenerManager).start();
        verify(shardingListenerManager).start();
        verify(failoverListenerManager).start();
        verify(instanceShutdownListenerManager).start();
        verify(configurationListenerManager).start();
        verify(guaranteeListenerManager).start();
        verify(jobNodeStorage).addConnectionStateListener(regCenterConnectionStateListener);
    }
}

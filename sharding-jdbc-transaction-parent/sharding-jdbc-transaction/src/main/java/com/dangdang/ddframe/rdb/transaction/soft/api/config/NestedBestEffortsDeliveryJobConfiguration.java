/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package com.dangdang.ddframe.rdb.transaction.soft.api.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Embed best efforts delivery B.A.S.E transaction asynchronized job configuration.
 * 
 * @author zhangliang
 */
@Getter
@Setter
public final class NestedBestEffortsDeliveryJobConfiguration extends AbstractBestEffortsDeliveryJobConfiguration {
    
    /**
     * Embed zookeeper's port.
     */
    private int zookeeperPort = 4181;
    
    /**
     * Embed zookeeper's data directory.
     */
    private String zookeeperDataDir = String.format("target/test_zk_data/%s/", System.nanoTime());
    
    /**
     * Max asynchronized delivery try times.
     */
    private int asyncMaxDeliveryTryTimes = 3;
    
    /**
     * Delay millis for asynchronized delivery.
     */
    private long asyncMaxDeliveryTryDelayMillis = 60  * 1000L;
    
}

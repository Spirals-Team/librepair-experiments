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
package com.alibaba.csp.sentinel.node.metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.node.ClusterNode;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.clusterbuilder.ClusterBuilderSlot;

public class MetricTimerListener implements Runnable {

    private static final MetricWriter metricWriter = new MetricWriter(SentinelConfig.singleMetricFileSize(),
        SentinelConfig.totalMetricFileCount());

    @Override
    public void run() {
        Map<Long, List<MetricNode>> maps = new TreeMap<Long, List<MetricNode>>();

        // 每5秒打印一次,把丢弃的seconds都给丢掉。
        for (Entry<ResourceWrapper, ClusterNode> e : ClusterBuilderSlot.getClusterNodeMap().entrySet()) {
            String name = e.getKey().getName();
            ClusterNode node = e.getValue();
            Map<Long, MetricNode> metrics = node.metrics();

            for (Entry<Long, MetricNode> entry : metrics.entrySet()) {
                long time = entry.getKey();
                MetricNode metricNode = entry.getValue();
                metricNode.setResource(name);
                if (maps.get(time) == null) {
                    maps.put(time, new ArrayList<MetricNode>());
                }
                List<MetricNode> nodes = maps.get(time);
                nodes.add(entry.getValue());
            }
        }
        if (!maps.isEmpty()) {
            for (Entry<Long, List<MetricNode>> entry : maps.entrySet()) {
                try {
                    metricWriter.write(entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    RecordLog.info("write metric error: ", e);
                }
            }
        }

    }

}

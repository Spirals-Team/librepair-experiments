/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.cluster.coordination.node;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.nifi.cluster.protocol.NodeIdentifier;

public final class NodeStateManager {
    private final ConcurrentMap<NodeIdentifier, NodeConnectionStatus> nodeStatuses = new ConcurrentHashMap<>();

    public NodeConnectionStatus getConnectionStatus(final NodeIdentifier nodeId) {
        return nodeStatuses.get(nodeId);
    }

    public void setConnectionStatus(final NodeIdentifier nodeId, final NodeConnectionStatus status) {
        nodeStatuses.put(nodeId, status);
        // TODO: Notify other nodes
    }

    public boolean transitionConnectionStatus(final NodeIdentifier nodeId, final NodeConnectionStatus currentStatus, final NodeConnectionStatus newStatus) {
        return nodeStatuses.replace(nodeId, currentStatus, newStatus);
        // TODO: Notify other nodes
    }
}

/*
 * Copyright 2018 The Hekate Project
 *
 * The Hekate Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.hekate.cluster.internal.gossip;

import io.hekate.cluster.ClusterNodeId;
import java.io.Serializable;

public abstract class GossipNodeInfoBase implements Serializable {
    private static final long serialVersionUID = 1;

    public abstract ClusterNodeId id();

    public abstract GossipNodeStatus status();

    public abstract long version();

    public ComparisonResult compare(GossipNodeInfoBase other) {
        assert other != null : "Other node is null.";

        int cmp = status().compareTo(other.status());

        long thisVer = version();
        long otherVer = other.version();

        if (cmp == 0) {
            return thisVer == otherVer ? ComparisonResult.SAME : thisVer > otherVer ? ComparisonResult.AFTER : ComparisonResult.BEFORE;
        } else if (cmp < 0) {
            return thisVer <= otherVer ? ComparisonResult.BEFORE : ComparisonResult.CONCURRENT;
        } else {
            return thisVer >= otherVer ? ComparisonResult.AFTER : ComparisonResult.CONCURRENT;
        }
    }
}

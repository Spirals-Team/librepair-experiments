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

package org.apache.nifi.wali;

import java.util.Map;
import java.util.Set;

public class StandardSnapshotRecovery<T> implements SnapshotRecovery<T> {
    private final Map<Object, T> recordMap;
    private final Set<String> recoveredSwapLocations;
    private final long maxTransactionId;

    public StandardSnapshotRecovery(final Map<Object, T> recordMap, final Set<String> recoveredSwapLocations, final long maxTransactionId) {
        this.recordMap = recordMap;
        this.recoveredSwapLocations = recoveredSwapLocations;
        this.maxTransactionId = maxTransactionId;
    }

    @Override
    public long getMaxTransactionId() {
        return maxTransactionId;
    }

    @Override
    public Map<Object, T> getRecords() {
        return recordMap;
    }

    @Override
    public Set<String> getRecoveredSwapLocations() {
        return recoveredSwapLocations;
    }

}

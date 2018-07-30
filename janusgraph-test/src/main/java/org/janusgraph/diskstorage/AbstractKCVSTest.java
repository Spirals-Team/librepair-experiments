// Copyright 2017 JanusGraph Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.janusgraph.diskstorage;

import org.janusgraph.diskstorage.keycolumnvalue.StoreManager;
import org.janusgraph.diskstorage.util.time.TimestampProvider;
import org.janusgraph.diskstorage.util.time.TimestampProviders;
import org.janusgraph.diskstorage.util.StandardBaseTransactionConfig;

/**
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public class AbstractKCVSTest {

    protected static final TimestampProvider times = TimestampProviders.MICRO;

    protected StandardBaseTransactionConfig getTxConfig() {
        return StandardBaseTransactionConfig.of(times);
    }

    protected StandardBaseTransactionConfig getConsistentTxConfig(StoreManager manager) {
        return StandardBaseTransactionConfig.of(times,manager.getFeatures().getKeyConsistentTxConfig());
    }

}

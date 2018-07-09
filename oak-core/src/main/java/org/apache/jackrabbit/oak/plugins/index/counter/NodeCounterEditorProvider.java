/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.jackrabbit.oak.plugins.index.counter;

import java.util.UUID;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.oak.api.CommitFailedException;
import org.apache.jackrabbit.oak.api.PropertyState;
import org.apache.jackrabbit.oak.api.Type;
import org.apache.jackrabbit.oak.plugins.index.IndexEditorProvider;
import org.apache.jackrabbit.oak.plugins.index.IndexUpdateCallback;
import org.apache.jackrabbit.oak.plugins.index.counter.NodeCounterEditor.NodeCounterRoot;
import org.apache.jackrabbit.oak.plugins.index.counter.jmx.NodeCounter;
import org.apache.jackrabbit.oak.spi.commit.Editor;
import org.apache.jackrabbit.oak.spi.state.NodeBuilder;
import org.apache.jackrabbit.oak.spi.state.NodeState;

@Component
@Service(IndexEditorProvider.class)
public class NodeCounterEditorProvider implements IndexEditorProvider {

    public static final String TYPE = "counter";

    public static final String RESOLUTION = "resolution";

    public static final String SEED = "seed";

    @Override
    @CheckForNull
    public Editor getIndexEditor(@Nonnull String type,
            @Nonnull NodeBuilder definition, @Nonnull NodeState root,
            @Nonnull IndexUpdateCallback callback) throws CommitFailedException {
        if (!TYPE.equals(type)) {
            return null;
        }
        int resolution; 
        PropertyState s = definition.getProperty(RESOLUTION);
        if (s == null) {
            resolution = NodeCounterEditor.DEFAULT_RESOLUTION; 
        } else {
            resolution = s.getValue(Type.LONG).intValue();
        }
        long seed;
        s = definition.getProperty(SEED);
        if (s != null) {
            seed = s.getValue(Type.LONG).intValue();
        } else {
            seed = 0;
            if (NodeCounter.COUNT_HASH) {
                // create a random number (that way we can also check if this feature is enabled)
                seed = UUID.randomUUID().getMostSignificantBits();
                definition.setProperty(SEED, seed);
            }
        }
        NodeCounterRoot rootData = new NodeCounterRoot(
                resolution, seed, definition, root, callback);
        return new NodeCounterEditor(rootData, null, "/", null);
    }

}

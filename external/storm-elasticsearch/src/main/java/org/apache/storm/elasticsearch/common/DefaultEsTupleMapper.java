/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.storm.elasticsearch.common;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.storm.tuple.ITuple;

public class DefaultEsTupleMapper implements EsTupleMapper {
    @Override
    public String getSource(ITuple tuple) {
        return tuple.getStringByField("source");
    }

    @Override
    public String getIndex(ITuple tuple) {
        return tuple.getStringByField("index");
    }

    @Override
    public String getType(ITuple tuple) {
        return tuple.getStringByField("type");
    }

    @Override
    public String getId(ITuple tuple) {
        return tuple.getStringByField("id");
    }

    @Override
    public Map<String, String> getParams(ITuple tuple, Map<String, String> defaultValue) {
        if (!tuple.contains("params")) {
            return defaultValue;
        }
        Object o = tuple.getValueByField("params");
        if (o instanceof Map) {
            Map<String, String> params = new HashMap<>();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) o).entrySet()) {
                params.put(entry.getKey().toString(), entry.getValue() == null ? null : entry.getValue().toString());
            }
            return params;
        }
        return defaultValue;
    }
}

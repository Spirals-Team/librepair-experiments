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

package org.janusgraph.util.datastructures;

import java.util.concurrent.ConcurrentMap;

/**
 * Utility class for Maps
 *
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public class Maps {

    public static <K, V> V putIfAbsent(ConcurrentMap<K, V> map, K key, Factory<V> factory) {
        V res = map.get(key);
        if (res != null) return res;
        else {
            V newObject = factory.create();
            res = map.putIfAbsent(key, newObject);
            if (res == null) return newObject;
            else return res;
        }
    }


}

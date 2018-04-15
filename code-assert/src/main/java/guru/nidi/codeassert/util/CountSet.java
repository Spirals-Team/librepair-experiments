/*
 * Copyright © 2015 Stefan Niederhauser (nidin@gmx.ch)
 *
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
 */
package guru.nidi.codeassert.util;

import java.util.*;

public class CountSet<T> {
    private final Map<T, Integer> map = new HashMap<>();

    public void add(T elem) {
        final Integer c = map.get(elem);
        map.put(elem, (c == null ? 0 : c) + 1);
    }

    public boolean contains(T elem) {
        return map.containsKey(elem);
    }

    public Map<T, Integer> asMap() {
        return map;
    }

    public Set<T> asSet() {
        return map.keySet();
    }
}

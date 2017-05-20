/*
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
package com.facebook.presto.util;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public class MoreSets
{
    private MoreSets() {}

    public static <T> Set<T> newIdentityHashSet()
    {
        return Sets.newIdentityHashSet();
    }

    public static <T> Set<T> newIdentityHashSet(Iterable<T> elements)
    {
        Set<T> set = newIdentityHashSet();
        Iterables.addAll(set, requireNonNull(elements, "elements cannot be null"));
        return set;
    }
}

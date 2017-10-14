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
package com.facebook.presto.resourceGroups;

import com.facebook.presto.spi.resourceGroups.ResourceGroupId;
import com.facebook.presto.spi.resourceGroups.SelectionContext;
import com.google.common.collect.ImmutableSet;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;

public class TestStaticSelector
{
    @Test
    public void testStaticSelectorUserRegex()
    {
        ResourceGroupId resourceGroupId = new ResourceGroupId(new ResourceGroupId("global"), "foo");
        StaticSelector selector = new StaticSelector(Optional.of(Pattern.compile("user.*")), Optional.empty(), ImmutableSet.of(), Optional.empty(), new ResourceGroupIdTemplate("global.foo"));
        assertEquals(selector.match(new SelectionContext(true, "userA", Optional.empty(), ImmutableSet.of("tag1"), 1, Optional.empty())), Optional.of(resourceGroupId));
        assertEquals(selector.match(new SelectionContext(true, "userB", Optional.of("source"), ImmutableSet.of(), 10, Optional.empty())), Optional.of(resourceGroupId));
        assertEquals(selector.match(new SelectionContext(true, "A.user", Optional.empty(), ImmutableSet.of("tag1"), 1, Optional.empty())), Optional.empty());
    }

    @Test
    public void testStaticSelectorSourceRegex()
    {
        ResourceGroupId resourceGroupId = new ResourceGroupId(new ResourceGroupId("global"), "foo");
        StaticSelector selector = new StaticSelector(Optional.empty(), Optional.of(Pattern.compile(".*source.*")), ImmutableSet.of(), Optional.empty(), new ResourceGroupIdTemplate("global.foo"));
        assertEquals(selector.match(new SelectionContext(true, "userA", Optional.empty(), ImmutableSet.of("tag1"), 1, Optional.empty())), Optional.empty());
        assertEquals(selector.match(new SelectionContext(true, "userB", Optional.of("source"), ImmutableSet.of(), 10, Optional.empty())), Optional.of(resourceGroupId));
        assertEquals(selector.match(new SelectionContext(true, "A.user", Optional.of("a source b"), ImmutableSet.of("tag1"), 1, Optional.empty())), Optional.of(resourceGroupId));
    }

    @Test
    public void testStaticSelectorClientTags()
    {
        ResourceGroupId resourceGroupId = new ResourceGroupId(new ResourceGroupId("global"), "foo");
        StaticSelector selector = new StaticSelector(Optional.empty(), Optional.empty(), ImmutableSet.of("tag1", "tag2"), Optional.empty(), new ResourceGroupIdTemplate("global.foo"));
        assertEquals(selector.match(new SelectionContext(true, "userA", Optional.empty(), ImmutableSet.of("tag1", "tag2"), 1, Optional.empty())), Optional.of(resourceGroupId));
        assertEquals(selector.match(new SelectionContext(true, "userB", Optional.of("source"), ImmutableSet.of(), 10, Optional.empty())), Optional.empty());
        assertEquals(selector.match(new SelectionContext(true, "A.user", Optional.of("a source b"), ImmutableSet.of("tag1"), 1, Optional.empty())), Optional.empty());
    }
}

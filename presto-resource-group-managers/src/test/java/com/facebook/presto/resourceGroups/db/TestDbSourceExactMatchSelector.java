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
package com.facebook.presto.resourceGroups.db;

import com.facebook.presto.spi.resourceGroups.ResourceGroupId;
import com.facebook.presto.spi.resourceGroups.SelectionContext;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.airlift.json.JsonCodec;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class TestDbSourceExactMatchSelector
{
    static H2ResourceGroupsDao setup(String prefix)
    {
        DbResourceGroupConfig config = new DbResourceGroupConfig().setConfigDbUrl("jdbc:h2:mem:test_" + prefix + System.nanoTime());
        return new H2DaoProvider(config).get();
    }
    @Test
    public void testMatch()
    {
        H2ResourceGroupsDao dao = setup("db-exact-match-selector");
        dao.createExactMatchSelectorsTable();

        ResourceGroupId resourceGroupId = new ResourceGroupId(ImmutableList.of("global", "test", "user"));
        JsonCodec<ResourceGroupId> codec = JsonCodec.jsonCodec(ResourceGroupId.class);
        dao.insertExactMatchSelector("test", "@test@test_pipeline", codec.toJson(resourceGroupId));

        DbSourceExactMatchSelector selector = new DbSourceExactMatchSelector("test", dao);
        assertEquals(
                selector.match(new SelectionContext(true, "testuser", Optional.of("@test@test_pipeline"), ImmutableSet.of("tag"), 1, Optional.empty())),
                Optional.of(resourceGroupId));
        assertEquals(
                selector.match(new SelectionContext(true, "testuser", Optional.of("@test@test_new"), ImmutableSet.of(), 1, Optional.empty())),
                Optional.empty());
    }
}

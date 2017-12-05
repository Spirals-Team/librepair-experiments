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
package com.facebook.presto.hive;

import com.facebook.presto.testing.MaterializedResult;
import com.facebook.presto.tests.AbstractTestDistributedQueries;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import static com.facebook.presto.SystemSessionProperties.DISTRIBUTED_JOIN;
import static com.facebook.presto.hive.HiveQueryRunner.createQueryRunner;
import static com.facebook.presto.hive.HiveQueryRunner.createSession;
import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.Collections.emptyList;
import static org.testng.Assert.assertTrue;

public class TestHiveDistributedQueries
        extends AbstractTestDistributedQueries
{
    public TestHiveDistributedQueries()
            throws Exception
    {
        //super(() -> createQueryRunner(getTables()));
        super(() -> createQueryRunner(emptyList()));
    }

    @Override
    public void testDelete()
    {
        // Hive connector currently does not support row-by-row delete
    }

    @Test
    public void testCreateSpatialTable()
    {
        assertUpdate("CREATE TABLE test_points (latitude double, longitude double, name varchar)");
        assertTrue(getQueryRunner().tableExists(getSession(), "test_points"));
        assertTableColumnNames("test_points", "latitude", "longitude", "name");

        assertUpdate("CREATE TABLE test_polygons (wkt varchar, name varchar)");
        assertTrue(getQueryRunner().tableExists(getSession(), "test_polygons"));
        assertTableColumnNames("test_polygons", "wkt", "name");

        assertUpdate("INSERT INTO test_points VALUES (0.1, 0.1, 'a'), (0.9, 0.9, 'b'), (1.2, 1.2, 'c'), (2.2, 2.2, 'd')", 4);
        assertUpdate("INSERT INTO test_polygons VALUES ('POLYGON ((0 0, 1 0, 1 1, 0 1, 0 0))', 'p1'), ('POLYGON ((0.7 0.7, 2.7 0.7, 2.7 2.7, 0.7 2.7, 0.7 0.7))', 'p2')", 2);

        assertQuery(createSession(ImmutableMap.of(DISTRIBUTED_JOIN, "false")),
                "SELECT test_polygons.name, count(1) from test_polygons, test_points " +
                "WHERE ST_Contains(ST_GeometryFromText(wkt), ST_Point(longitude, latitude)) " +
                "GROUP BY 1 ORDER BY 1", "SELECT 'p1', 2 UNION ALL SELECT 'p2', 3");

        MaterializedResult result = computeActual(createSession(ImmutableMap.of(DISTRIBUTED_JOIN, "false")),
                "EXPLAIN SELECT test_polygons.name, count(1) from test_polygons, test_points " +
                        "WHERE ST_Contains(ST_GeometryFromText(wkt), ST_Point(longitude, latitude)) " +
                        "GROUP BY 1 ORDER BY 1");

        System.out.println(getOnlyElement(result.getOnlyColumnAsSet()));
    }

    // Hive specific tests should normally go in TestHiveIntegrationSmokeTest
}

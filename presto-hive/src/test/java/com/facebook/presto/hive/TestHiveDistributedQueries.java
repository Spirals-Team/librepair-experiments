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

import com.facebook.presto.Session;
import com.facebook.presto.tests.AbstractTestDistributedQueries;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import static com.facebook.presto.SystemSessionProperties.DISTRIBUTED_JOIN;
import static com.facebook.presto.hive.HiveQueryRunner.createQueryRunner;
import static com.facebook.presto.hive.HiveQueryRunner.createSession;
import static io.airlift.tpch.TpchTable.getTables;

public class TestHiveDistributedQueries
        extends AbstractTestDistributedQueries
{
    // A set of polygons such that:
    // - a and c intersect;
    // - c covers b;
    private static final String POLYGONS_SQL = "VALUES " +
            "('POLYGON ((-0.5 -0.6, 1.5 0, 1 1, 0 1, -0.5 -0.6))', 'a'), " +
            "('POLYGON ((2 2, 3 2, 2.5 3, 2 2))', 'b'), " +
            "('POLYGON ((0.8 0.7, 0.8 4, 5 4, 4.5 0.8, 0.8 0.7))', 'c'), " +
            "('POLYGON ((7 7, 11 7, 11 11, 7 7))', 'd')";

    // A set of points such that:
    // - a contains x
    // - b and c contain y
    // - d contains z
    private static final String POINTS_SQL = "VALUES " +
            "(-0.1, -0.1, 'x'), " +
            "(2.1, 2.1, 'y'), " +
            "(7.1, 7.2, 'z')";

    public TestHiveDistributedQueries()
            throws Exception
    {
        super(() -> createQueryRunner(getTables()));
    }

    @Override
    public void testDelete()
    {
        // Hive connector currently does not support row-by-row delete
    }

    @Test
    public void testBroadcastSpatialJoinContains()
    {
        // Test ST_Contains(build, probe)
        Session session = createSession(ImmutableMap.of(DISTRIBUTED_JOIN, "false"));
        assertQuery(session,
                "SELECT b.name, a.name " +
                "FROM (" + POINTS_SQL + ") AS a (latitude, longitude, name), (" + POLYGONS_SQL + ") AS b (wkt, name) " +
                "WHERE ST_Contains(ST_GeometryFromText(wkt), ST_Point(longitude, latitude))",
                "SELECT * FROM (VALUES ('a', 'x'), ('b', 'y'), ('c', 'y'), ('d', 'z'))");

        assertQuery(session,
                "SELECT b.name, a.name " +
                "FROM (" + POLYGONS_SQL + ") AS a (wkt, name), (" + POLYGONS_SQL + ") AS b (wkt, name) " +
                "WHERE ST_Contains(ST_GeometryFromText(b.wkt), ST_GeometryFromText(a.wkt))",
                "SELECT * FROM (VALUES ('a', 'a'), ('b', 'b'), ('c', 'c'), ('d', 'd'), ('c', 'b'))");

        // Test ST_Contains(probe, build)
        assertQuery(session,
                "SELECT b.name, a.name " +
                "FROM (" + POLYGONS_SQL + ") AS b (wkt, name), (" + POINTS_SQL + ") AS a (latitude, longitude, name) " +
                "WHERE ST_Contains(ST_GeometryFromText(wkt), ST_Point(longitude, latitude))",
                "SELECT * FROM (VALUES ('a', 'x'), ('b', 'y'), ('c', 'y'), ('d', 'z'))");

        assertQuery(session,
                "SELECT b.name, a.name " +
                "FROM (" + POLYGONS_SQL + ") AS a (wkt, name), (" + POLYGONS_SQL + ") AS b (wkt, name) " +
                "WHERE ST_Contains(ST_GeometryFromText(a.wkt), ST_GeometryFromText(b.wkt))",
                "SELECT * FROM (VALUES ('a', 'a'), ('b', 'b'), ('c', 'c'), ('d', 'd'), ('b', 'c'))");
    }

    @Test
    public void testBroadcastSpatialJoinIntersects()
    {
        // Test ST_Intersects(build, probe)
        Session session = createSession(ImmutableMap.of(DISTRIBUTED_JOIN, "false"));
        assertQuery(session,
                "SELECT a.name, b.name " +
                "FROM (" + POLYGONS_SQL + ") AS a (wkt, name), (" + POLYGONS_SQL + ") AS b (wkt, name) " +
                "WHERE ST_Intersects(ST_GeometryFromText(b.wkt), ST_GeometryFromText(a.wkt))",
                "SELECT * FROM VALUES ('a', 'a'), ('b', 'b'), ('c', 'c'), ('d', 'd'), " +
                "('a', 'c'), ('c', 'a'), ('c', 'b'), ('b', 'c')");

        // Test ST_Intersects(probe, build)
        assertQuery(session,
                "SELECT a.name, b.name " +
                "FROM (" + POLYGONS_SQL + ") AS a (wkt, name), (" + POLYGONS_SQL + ") AS b (wkt, name) " +
                "WHERE ST_Intersects(ST_GeometryFromText(a.wkt), ST_GeometryFromText(b.wkt))",
                "SELECT * FROM VALUES ('a', 'a'), ('b', 'b'), ('c', 'c'), ('d', 'd'), " +
                "('a', 'c'), ('c', 'a'), ('c', 'b'), ('b', 'c')");
    }

    @Test
    public void testBroadcastSpatialJoinIntersectsWithExtraConditions()
            throws Exception
    {
        Session session = createSession(ImmutableMap.of(DISTRIBUTED_JOIN, "false"));
        assertQuery(session,
                "SELECT a.name, b.name " +
                        "FROM (" + POLYGONS_SQL + ") AS a (wkt, name), (" + POLYGONS_SQL + ") AS b (wkt, name) " +
                        "WHERE ST_Intersects(ST_GeometryFromText(b.wkt), ST_GeometryFromText(a.wkt)) " +
                        "   AND a.name != b.name",
                "SELECT * FROM VALUES ('a', 'c'), ('c', 'a'), ('c', 'b'), ('b', 'c')");

        assertQuery(session,
                "SELECT a.name, b.name " +
                        "FROM (" + POLYGONS_SQL + ") AS a (wkt, name), (" + POLYGONS_SQL + ") AS b (wkt, name) " +
                        "WHERE ST_Intersects(ST_GeometryFromText(b.wkt), ST_GeometryFromText(a.wkt)) " +
                        "   AND a.name < b.name",
                "SELECT * FROM VALUES ('a', 'c'), ('b', 'c')");
    }

    // Hive specific tests should normally go in TestHiveIntegrationSmokeTest
}

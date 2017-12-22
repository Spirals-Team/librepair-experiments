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
package com.facebook.presto.sql.planner.iterative.rule;

import com.facebook.presto.Session;
import com.facebook.presto.plugin.geospatial.GeoPlugin;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.parser.SqlParser;
import com.facebook.presto.sql.planner.iterative.rule.test.BaseRuleTest;
import com.facebook.presto.testing.LocalQueryRunner;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.facebook.presto.SystemSessionProperties.DISTRIBUTED_JOIN;
import static com.facebook.presto.metadata.FunctionExtractor.extractFunctions;
import static com.facebook.presto.plugin.geospatial.GeometryType.GEOMETRY;
import static com.facebook.presto.spi.type.VarcharType.VARCHAR;
import static com.facebook.presto.sql.planner.assertions.PlanMatchPattern.expression;
import static com.facebook.presto.sql.planner.assertions.PlanMatchPattern.join;
import static com.facebook.presto.sql.planner.assertions.PlanMatchPattern.project;
import static com.facebook.presto.sql.planner.assertions.PlanMatchPattern.values;
import static com.facebook.presto.sql.planner.plan.JoinNode.Type.INNER;
import static java.util.Collections.emptyList;

public class TestPushDownSpatialProjections
        extends BaseRuleTest
{
    @BeforeClass
    public void localSetUp()
    {
        super.setUp();

        LocalQueryRunner localQueryRunner = (LocalQueryRunner) tester().getQueryRunner();

        GeoPlugin plugin = new GeoPlugin();
        for (Type type : plugin.getTypes()) {
            localQueryRunner.getTypeManager().addType(type);
        }
        localQueryRunner.getMetadata().addFunctions(extractFunctions(plugin.getFunctions()));
    }

    private Session createSession()
    {
        return tester().createSession(ImmutableMap.of(DISTRIBUTED_JOIN, "false"));
    }

    @Test
    public void testDoesNotFire()
            throws Exception
    {
        // symbols
        tester().assertThat(createSession(), new PushDownSpatialProjections(tester().getMetadata(), new SqlParser()))
                .on(p ->
                        p.join(INNER,
                                p.values(p.symbol("a")),
                                p.values(p.symbol("b")),
                                p.expression("ST_Contains(a, b)")))
                .doesNotFire();

        // scalar expression
        tester().assertThat(createSession(), new PushDownSpatialProjections(tester().getMetadata(), new SqlParser()))
                .on(p ->
                        p.join(INNER,
                                p.values(),
                                p.values(p.symbol("b")),
                                p.expression("ST_Contains(ST_GeometryFromText('POLYGON ...'), b)")))
                .doesNotFire();

        // OR operand
        tester().assertThat(createSession(), new PushDownSpatialProjections(tester().getMetadata(), new SqlParser()))
                .on(p ->
                        p.join(INNER,
                                p.values(p.symbol("wkt", VARCHAR), p.symbol("name_1")),
                                p.values(p.symbol("point", GEOMETRY), p.symbol("name_2")),
                                p.expression("ST_Contains(ST_GeometryFromText(wkt), point) OR name_1 != name_2")))
                .doesNotFire();
    }

    @Test
    public void testPushDownFirstArgument()
            throws Exception
    {
        tester().assertThat(createSession(), new PushDownSpatialProjections(tester().getMetadata(), new SqlParser()))
                .on(p ->
                        p.join(INNER,
                                p.values(p.symbol("wkt", VARCHAR)),
                                p.values(p.symbol("point", GEOMETRY)),
                                p.expression("ST_Contains(ST_GeometryFromText(wkt), point)")))
                .matches(
                        join(INNER,
                                emptyList(),
                                Optional.of("ST_Contains(st_geometryfromtext, point)"),
                                project(ImmutableMap.of("st_geometryfromtext", expression("ST_GeometryFromText(wkt)")), values(ImmutableMap.of("wkt", 0))),
                                values(ImmutableMap.of("point", 0))));
    }

    @Test
    public void testPushDownSecondArgument()
            throws Exception
    {
        tester().assertThat(createSession(), new PushDownSpatialProjections(tester().getMetadata(), new SqlParser()))
                .on(p ->
                        p.join(INNER,
                                p.values(p.symbol("polygon", GEOMETRY)),
                                p.values(p.symbol("lat"), p.symbol("lng")),
                                p.expression("ST_Contains(polygon, ST_Point(lng, lat))")))
                .matches(
                        join(INNER,
                                emptyList(),
                                Optional.of("ST_Contains(polygon, st_point)"),
                                values(ImmutableMap.of("polygon", 0)),
                                project(ImmutableMap.of("st_point", expression("ST_Point(lng, lat)")), values(ImmutableMap.of("lat", 0, "lng", 1)))));
    }

    @Test
    public void testPushDownBothArguments()
            throws Exception
    {
        tester().assertThat(createSession(), new PushDownSpatialProjections(tester().getMetadata(), new SqlParser()))
                .on(p ->
                        p.join(INNER,
                                p.values(p.symbol("wkt", VARCHAR)),
                                p.values(p.symbol("lat"), p.symbol("lng")),
                                p.expression("ST_Contains(ST_GeometryFromText(wkt), ST_Point(lng, lat))")))
                .matches(
                        join(INNER,
                                emptyList(),
                                Optional.of("ST_Contains(st_geometryfromtext, st_point)"),
                                project(ImmutableMap.of("st_geometryfromtext", expression("ST_GeometryFromText(wkt)")), values(ImmutableMap.of("wkt", 0))),
                                project(ImmutableMap.of("st_point", expression("ST_Point(lng, lat)")), values(ImmutableMap.of("lat", 0, "lng", 1)))));
    }

    @Test
    public void testPushDownOppositeOrder()
            throws Exception
    {
        tester().assertThat(createSession(), new PushDownSpatialProjections(tester().getMetadata(), new SqlParser()))
                .on(p ->
                        p.join(INNER,
                                p.values(p.symbol("lat"), p.symbol("lng")),
                                p.values(p.symbol("wkt", VARCHAR)),
                                p.expression("ST_Contains(ST_GeometryFromText(wkt), ST_Point(lng, lat))")))
                .matches(
                        join(INNER,
                                emptyList(),
                                Optional.of("ST_Contains(st_geometryfromtext, st_point)"),
                                project(ImmutableMap.of("st_point", expression("ST_Point(lng, lat)")), values(ImmutableMap.of("lat", 0, "lng", 1))),
                                project(ImmutableMap.of("st_geometryfromtext", expression("ST_GeometryFromText(wkt)")), values(ImmutableMap.of("wkt", 0)))));
    }

    @Test
    public void testPushDownAnd()
            throws Exception
    {
        tester().assertThat(createSession(), new PushDownSpatialProjections(tester().getMetadata(), new SqlParser()))
                .on(p ->
                        p.join(INNER,
                                p.values(p.symbol("wkt", VARCHAR), p.symbol("name_1")),
                                p.values(p.symbol("lat"), p.symbol("lng"), p.symbol("name_2")),
                                p.expression("name_1 != name_2 AND ST_Contains(ST_GeometryFromText(wkt), ST_Point(lng, lat))")))
                .matches(
                        join(INNER,
                                emptyList(),
                                Optional.of("name_1 != name_2 AND ST_Contains(st_geometryfromtext, st_point)"),
                                project(ImmutableMap.of("st_geometryfromtext", expression("ST_GeometryFromText(wkt)")), values(ImmutableMap.of("wkt", 0, "name_1", 1))),
                                project(ImmutableMap.of("st_point", expression("ST_Point(lng, lat)")), values(ImmutableMap.of("lat", 0, "lng", 1, "name_2", 2)))));
    }
}

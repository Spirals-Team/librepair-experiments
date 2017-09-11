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
package com.facebook.presto.geospatial;

import com.facebook.presto.Session;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.testing.LocalQueryRunner;
import com.facebook.presto.tests.AbstractTestQueryFramework;
import com.facebook.presto.tpch.TpchConnectorFactory;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import static com.facebook.presto.metadata.FunctionExtractor.extractFunctions;
import static com.facebook.presto.testing.TestingSession.testSessionBuilder;
import static com.facebook.presto.tpch.TpchMetadata.TINY_SCHEMA_NAME;

public class TestGeoQueries
        extends AbstractTestQueryFramework
{
    public TestGeoQueries()
    {
        super(TestGeoQueries::createLocalQueryRunner);
    }

    @Test
    public void testSTPoint()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_Point(1, 4))", "select 'POINT (1 4)'");
        assertQuery("select ST_AsText(ST_Point(122.3, 10.55))", "select 'POINT (122.3 10.55)'");
    }

    @Test
    public void testSTLineString()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_LineFromText('LineString(1 1, 2 2, 1 3)'))", "select 'LINESTRING (1 1, 2 2, 1 3)'");
        assertQueryFails("select ST_AsText(ST_LineFromText('MULTILineString EMPTY'))", "ST_LineFromText only applies to LineString. Input type is: MultiLineString");
        assertQueryFails("select ST_AsText(ST_LineFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'))", "ST_LineFromText only applies to LineString. Input type is: Polygon");
    }

    @Test
    public void testSTPolygon()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_Polygon('Polygon ((1 1, 1 4, 4 4, 4 1))'))", "select 'POLYGON ((1 1, 4 1, 4 4, 1 4, 1 1))'");
        assertQueryFails("select ST_AsText(ST_Polygon('LineString(1 1, 2 2, 1 3)'))", "ST_Polygon only applies to Polygon. Input type is: LineString");
    }

    @Test
    public void testSTArea()
            throws Exception
    {
        assertQuery("select ST_Area(ST_GeometryFromText('Polygon ((2 2, 2 6, 6 6, 6 2))'))", "select 16.0");
        assertQueryFails("select ST_Area(ST_GeometryFromText('Point (1 4)'))", "ST_Area only applies to Polygon. Input type is: Point");
    }

    @Test
    public void testSTCentroid()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_Centroid(ST_GeometryFromText('LineString EMPTY')))", "select 'POINT EMPTY'");
        assertQuery("select ST_AsText(ST_Centroid(ST_GeometryFromText('Point (3 5)')))", "select 'POINT (3 5)'");
        assertQuery("select ST_AsText(ST_Centroid(ST_GeometryFromText('MultiPoint (1 2, 2 4, 3 6, 4 8)')))", "select 'POINT (2.5 5)'");
        assertQuery("select ST_AsText(ST_Centroid(ST_GeometryFromText('LineString(1 1, 2 2, 3 3)')))", "select 'POINT (2 2)'");
        assertQuery("select ST_AsText(ST_Centroid(ST_GeometryFromText('MultiLineString((1 1, 5 1), (2 4, 4 4))')))", "select 'POINT (3 2)'");
        assertQuery("select ST_AsText(ST_Centroid(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))')))", "select 'POINT (2.5 2.5)'");
        assertQuery("select ST_AsText(ST_Centroid(ST_GeometryFromText('Polygon ((1 1, 5 1, 3 4))')))", "select 'POINT (3 2)'");
        assertQuery("select ST_AsText(ST_Centroid(ST_GeometryFromText('MultiPolygon (((1 1, 1 3, 3 3, 3 1)), ((2 4, 2 6, 6 6, 6 4)))')))", "select 'POINT (3.3333333333333335 4)'");
        assertQuery("select ST_AsText(ST_Centroid(ST_SymDifference(ST_GeometryFromText('POLYGON ((0 0, 0 5, 5 5, 5 0, 0 0))'), ST_GeometryFromText('POLYGON ((1 1, 1 2, 2 2, 2 1, 1 1))'))))", "select 'POINT (2.5416666666666665 2.5416666666666665)'");
    }

    @Test
    public void testSTCoordinateDimension()
            throws Exception
    {
        assertQuery("select ST_CoordDim(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'))", "select 2");
        assertQuery("select ST_CoordDim(ST_GeometryFromText('Point (1 4)'))", "select 2");
    }

    @Test
    public void testSTDimension()
            throws Exception
    {
        assertQuery("select ST_Dimension(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'))", "select 2");
        assertQuery("select ST_Dimension(ST_GeometryFromText('Point (1 4)'))", "select 0");
    }

    @Test
    public void testSTIsClosed()
            throws Exception
    {
        assertQuery("select ST_IsClosed(ST_GeometryFromText('LineString(1 1, 2 2, 1 3, 1 1)'))", "select true");
        assertQuery("select ST_IsClosed(ST_GeometryFromText('LineString(1 1, 2 2, 1 3)'))", "select false");
        assertQueryFails("select ST_IsClosed(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'))", "ST_IsClosed only applies to LineString or MultiLineString. Input type is: Polygon");
    }

    @Test
    public void testSTIsEmpty()
            throws Exception
    {
        assertQuery("select ST_IsEmpty(ST_GeometryFromText('Point (1.5 2.5)'))", "select false");
        assertQuery("select ST_IsEmpty(ST_GeometryFromText('Polygon EMPTY'))", "select true");
    }

    @Test
    public void testSTLength()
            throws Exception
    {
        assertQuery("select ST_Length(ST_GeometryFromText('LineString(0 0, 2 2)'))", "select 2.8284271247461903");
        assertQueryFails("select ST_Length(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'))", "ST_Length only applies to LineString or MultiLineString. Input type is: Polygon");
    }

    @Test
    public void testSTMax()
            throws Exception
    {
        assertQuery("select ST_XMax(ST_GeometryFromText('LineString(8 4, 5 7)'))", "select 8.0");
        assertQuery("select ST_YMax(ST_GeometryFromText('LineString(8 4, 5 7)'))", "select 7.0");
        assertQuery("select ST_XMax(ST_GeometryFromText('Polygon ((2 0, 2 1, 3 1))'))", "select 3.0");
        assertQuery("select ST_YMax(ST_GeometryFromText('Polygon ((2 0, 2 1, 3 1))'))", "select 1.0");
    }

    @Test
    public void testSTMin()
            throws Exception
    {
        assertQuery("select ST_XMin(ST_GeometryFromText('LineString(8 4, 5 7)'))", "select 5.0");
        assertQuery("select ST_YMin(ST_GeometryFromText('LineString(8 4, 5 7)'))", "select 4.0");
        assertQuery("select ST_XMin(ST_GeometryFromText('Polygon ((2 0, 2 1, 3 1))'))", "select 2.0");
        assertQuery("select ST_YMin(ST_GeometryFromText('Polygon ((2 0, 2 1, 3 1))'))", "select 0.0");
    }

    @Test
    public void testSTInteriorRingNumber()
            throws Exception
    {
        assertQuery("select ST_NumInteriorRing(ST_GeometryFromText('Polygon ((0 0, 8 0, 0 8, 0 0), (1 1, 1 5, 5 1, 1 1))'))", "select 1");
        assertQueryFails("select ST_NumInteriorRing(ST_GeometryFromText('LineString(8 4, 5 7)'))", "ST_NumInteriorRing only applies to Polygon. Input type is: LineString");
    }

    @Test
    public void testSTPointCount()
            throws Exception
    {
        assertQuery("select ST_NumPoints(ST_GeometryFromText('Polygon ((0 0, 8 0, 0 8, 0 0), (1 1, 1 5, 5 1, 1 1))'))", "select 6");
        assertQuery("select ST_NumPoints(ST_GeometryFromText('LineString(8 4, 5 7)'))", "select 2");
        assertQuery("select ST_NumPoints(ST_GeometryFromText('Point (1 2)'))", "select 1");
        assertQuery("select ST_NumPoints(ST_GeometryFromText('LineString EMPTY'))", "select 0");
    }

    @Test
    public void testSTIsRing()
            throws Exception
    {
        assertQuery("select ST_IsRing(ST_GeometryFromText('LineString(8 4, 4 8)'))", "select false");
        assertQueryFails("select ST_IsRing(ST_GeometryFromText('Polygon ((2 0, 2 1, 3 1))'))", "ST_IsRing only applies to LineString. Input type is: Polygon");
    }

    @Test
    public void testSTStartEndPoint()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_StartPoint(ST_GeometryFromText('LineString(8 4, 4 8, 5 6)')))", "select 'POINT (8 4)'");
        assertQuery("select ST_AsText(ST_EndPoint(ST_GeometryFromText('LineString(8 4, 4 8, 5 6)')))", "select 'POINT (5 6)'");
        assertQueryFails("select ST_AsText(ST_EndPoint(ST_GeometryFromText('Polygon ((2 0, 2 1, 3 1))')))", "ST_EndPoint only applies to LineString. Input type is: Polygon");
    }

    @Test
    public void testSTXY()
            throws Exception
    {
        assertQuery("select ST_X(ST_GeometryFromText('Point (1 2)'))", "select 1.0");
        assertQuery("select ST_Y(ST_GeometryFromText('Point (1 2)'))", "select 2.0");
        assertQueryFails("select ST_Y(ST_GeometryFromText('Polygon ((2 0, 2 1, 3 1))'))", "ST_Y only applies to Point. Input type is: Polygon");
    }

    @Test
    public void testSTBoundary()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_Boundary(ST_GeometryFromText('Polygon ((1 1, 4 1, 1 4))')))", "select 'LINESTRING (1 1, 4 1, 1 4, 1 1)'");
    }

    @Test
    public void testSTEnvelope()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_Envelope(ST_GeometryFromText('LineString(1 1, 2 2, 1 3)')))", "select 'POLYGON ((1 1, 2 1, 2 3, 1 3, 1 1))'");
    }

    @Test
    public void testSTDifference()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_Difference(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'), ST_GeometryFromText('Polygon ((2 2, 2 5, 5 5, 5 2))')))", "select 'POLYGON ((1 1, 4 1, 4 2, 2 2, 2 4, 1 4, 1 1))'");
    }

    @Test
    public void testSTDistance()
            throws Exception
    {
        assertQuery("select ST_Distance(ST_GeometryFromText('Polygon ((1 1, 1 3, 3 3, 3 1))'), ST_GeometryFromText('Polygon ((4 4, 4 5, 5 5, 5 4))'))", "select 1.4142135623730951");
    }

    @Test
    public void testSTExteriorRing()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_ExteriorRing(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 1))')))", "select 'LINESTRING (1 1, 4 1, 1 4, 1 1)'");
        assertQueryFails("select ST_AsText(ST_ExteriorRing(ST_GeometryFromText('LineString(1 1, 2 2, 1 3)')))", "ST_ExteriorRing only applies to Polygon. Input type is: LineString");
    }

    @Test
    public void testSTIntersection()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_Intersection(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'), ST_GeometryFromText('Polygon ((2 2, 2 5, 5 5, 5 2))')))", "select 'POLYGON ((2 2, 4 2, 4 4, 2 4, 2 2))'");
        assertQuery("select ST_AsText(ST_Intersection(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'), ST_GeometryFromText('LineString(2 0, 2 3)')))", "select 'LINESTRING (2 1, 2 3)'");
        assertQuery("select ST_AsText(ST_Intersection(ST_GeometryFromText('POLYGON ((0 0, 0 1, 1 1, 1 0, 0 0))'), ST_GeometryFromText('LINESTRING (-1 1, 1 -1, 1 2)')))", "select 'GEOMETRYCOLLECTION (POINT (-5.9700999704851220e-18 -5.9698882122483090e-18), LINESTRING (1 0, 1 1))'");
    }

    @Test
    public void testSTSymmetricDifference()
            throws Exception
    {
        assertQuery("select ST_AsText(ST_SymDifference(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'), ST_GeometryFromText('Polygon ((2 2, 2 5, 5 5, 5 2))')))", "select 'MULTIPOLYGON (((1 1, 4 1, 4 2, 2 2, 2 4, 1 4, 1 1)), ((4 2, 5 2, 5 5, 2 5, 2 4, 4 4, 4 2)))'");
    }

    @Test
    public void testStContains()
            throws Exception
    {
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('Polygon((0 2,1 1,0 -1,0 2))'), ST_GeometryFromText('Polygon((-1 3,2 1,0 -3,-1 3))'))", "SELECT false");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('LineString(20 20,30 30)'), ST_GeometryFromText('Point(25 25)'))", "SELECT true");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('Polygon((-1 2, 0 3, 0 1, -1 2))'), ST_GeometryFromText('Polygon((0 3, -1 2, 0 1, 0 3))'))", "SELECT true");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText(null), ST_GeometryFromText('Point(25 25)'))", "SELECT null");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('Polygon((0 2,1 1,0 -1,0 2))'), ST_GeometryFromText('Polygon((-1 3,2 1,0 -3,-1 3))'))", "SELECT false");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('LineString(20 20,30 30)'), ST_GeometryFromText('Point(25 25)'))", "SELECT true");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('Polygon((-1 2, 0 3, 0 1, -1 2))'), ST_GeometryFromText('Polygon((0 3, -1 2, 0 1, 0 3))'))", "SELECT true");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText(null), ST_GeometryFromText('Point(25 25)'))", "SELECT null");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('Polygon((0 2,1 1,0 -1,0 2))'), ST_GeometryFromText('Polygon((-1 3,2 1,0 -3,-1 3))'))", "SELECT false");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('Polygon((0 2,1 1,0 -1,0 2))'), ST_GeometryFromText('Polygon((-1 3,2 1,0 -3,-1 3))'))", "SELECT false");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('LineString(20 20,30 30)'), ST_GeometryFromText('Point(25 25)'))", "SELECT true");
        assertQuery("SELECT ST_Contains(ST_GeometryFromText('LineString(20 20,30 30)'), ST_GeometryFromText('Point(25 25)'))", "SELECT true");
    }

    @Test
    public void testSTCrosses()
            throws Exception
    {
        assertQuery("select ST_Crosses(ST_GeometryFromText('LineString(0 0, 1 1)'), ST_GeometryFromText('LineString(1 0, 0 1)'))", "select true");
        assertQuery("select ST_Crosses(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'), ST_GeometryFromText('Polygon ((2 2, 2 5, 5 5, 5 2))'))", "select false");
        assertQuery("select ST_Crosses(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'), ST_GeometryFromText('LineString(2 0, 2 3)'))", "select true");
        assertQuery("select ST_Crosses(ST_GeometryFromText('LineString(0 0, 1 1)'), ST_GeometryFromText('LineString(1 0, 0 1)'))", "select true");
    }

    @Test
    public void testSTDisjoint()
            throws Exception
    {
        assertQuery("select ST_Disjoint(ST_GeometryFromText('LineString(0 0, 0 1)'), ST_GeometryFromText('LineString(1 1, 1 0)'))", "select true");
        assertQuery("select ST_Disjoint(ST_GeometryFromText('Polygon ((1 1, 1 3, 3 3, 3 1))'), ST_GeometryFromText('Polygon ((4 4, 4 5, 5 5, 5 4))'))", "select true");
        assertQuery("select ST_Disjoint(ST_GeometryFromText('Polygon ((1 1, 1 3, 3 3, 3 1))'), ST_GeometryFromText('Polygon ((4 4, 4 5, 5 5, 5 4))'))", "select true");
    }

    @Test
    public void testSTEquals()
            throws Exception
    {
        assertQuery("select ST_Equals(ST_GeometryFromText('LineString(0 0, 2 2)'), ST_GeometryFromText('LineString(0 0, 2 2)'))", "select true");
        assertQuery("select ST_Equals(ST_GeometryFromText('LineString(0 0, 2 2)'), ST_GeometryFromText('LineString(0 0, 2 2)'))", "select true");
    }

    @Test
    public void testSTIntersects()
            throws Exception
    {
        assertQuery("select ST_Intersects(ST_GeometryFromText('LineString(8 4, 4 8)'), ST_GeometryFromText('Polygon ((2 2, 2 6, 6 6, 6 2))'))", "select true");
        assertQuery("select ST_Intersects(ST_GeometryFromText('LineString(8 4, 4 8)'), ST_GeometryFromText('Polygon ((2 2, 2 6, 6 6, 6 2))'))", "select true");
        assertQuery("select ST_Intersects(ST_GeometryFromText('LineString(8 4, 4 8)'), ST_GeometryFromText('Polygon ((2 2, 2 6, 6 6, 6 2))'))", "select true");
        assertQuery("select ST_Intersects(ST_GeometryFromText('LineString(8 4, 4 8)'), ST_GeometryFromText('Polygon ((2 2, 2 6, 6 6, 6 2))'))", "select true");
    }

    @Test
    public void testSTOverlaps()
            throws Exception
    {
        assertQuery("select ST_Overlaps(ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'), ST_GeometryFromText('Polygon ((3 3, 3 5, 5 5, 5 3))'))", "select true");
    }

    @Test
    public void testSTRelate()
            throws Exception
    {
        assertQuery("select ST_Relate(ST_GeometryFromText('Polygon ((2 0, 2 1, 3 1))'), ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'), '****T****')", "select true");
    }

    @Test
    public void testSTTouches()
            throws Exception
    {
        assertQuery("select ST_Touches(ST_GeometryFromText('Point(1 2)'), ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'))", "select true");
    }

    @Test
    public void testSTWithin()
            throws Exception
    {
        assertQuery("select ST_Within(ST_GeometryFromText('Point(3 2)'), ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'))", "select true");
        assertQuery("select ST_Within(ST_GeometryFromText('Point(3 2)'), ST_GeometryFromText('Polygon ((1 1, 1 4, 4 4, 4 1))'))", "select true");
    }

    private static LocalQueryRunner createLocalQueryRunner()
    {
        Session defaultSession = testSessionBuilder()
                .setCatalog("local")
                .setSchema(TINY_SCHEMA_NAME)
                .build();

        LocalQueryRunner localQueryRunner = new LocalQueryRunner(defaultSession);

        // add the tpch catalog
        // local queries run directly against the generator
        localQueryRunner.createCatalog(defaultSession.getCatalog().get(), new TpchConnectorFactory(1), ImmutableMap.<String, String>of());

        GeoPlugin plugin = new GeoPlugin();
        for (Type type : plugin.getTypes()) {
            localQueryRunner.getTypeManager().addType(type);
        }
        localQueryRunner.getMetadata().addFunctions(extractFunctions(new GeoPlugin().getFunctions()));

        return localQueryRunner;
    }
}

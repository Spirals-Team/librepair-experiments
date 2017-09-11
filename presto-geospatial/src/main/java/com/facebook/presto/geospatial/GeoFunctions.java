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

import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.MultiPath;
import com.esri.core.geometry.MultiVertexGeometry;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.ogc.OGCGeometry;
import com.esri.core.geometry.ogc.OGCLineString;
import com.esri.core.geometry.ogc.OGCMultiPolygon;
import com.esri.core.geometry.ogc.OGCPoint;
import com.esri.core.geometry.ogc.OGCPolygon;
import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.SqlNullable;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.StandardTypes;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.apache.commons.lang.StringUtils;

import static com.facebook.presto.geospatial.GeometryType.NAME;
import static com.facebook.presto.geospatial.GeometryUtils.LINE_STRING;
import static com.facebook.presto.geospatial.GeometryUtils.MULTI_LINE_STRING;
import static com.facebook.presto.geospatial.GeometryUtils.MULTI_POINT;
import static com.facebook.presto.geospatial.GeometryUtils.MULTI_POLYGON;
import static com.facebook.presto.geospatial.GeometryUtils.POINT;
import static com.facebook.presto.geospatial.GeometryUtils.POLYGON;
import static com.facebook.presto.geospatial.GeometryUtils.deserialize;
import static com.facebook.presto.geospatial.GeometryUtils.serialize;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

public final class GeoFunctions
{
    private GeoFunctions() {}

    @Description("Returns a Geometry type LineString object from Well-Known Text representation (WKT)")
    @ScalarFunction("ST_LineFromText")
    @SqlType(NAME)
    public static Slice stLineString(@SqlType(StandardTypes.VARCHAR) Slice input)
    {
        OGCGeometry geometry = getGeometry(OGCGeometry.fromText(input.toStringUtf8()), "ST_LineFromText", LINE_STRING);
        return serialize(geometry);
    }

    @Description("Returns a Geometry type Point object with the given coordinate values")
    @ScalarFunction("ST_Point")
    @SqlType(NAME)
    public static Slice stPoint(@SqlType(StandardTypes.DOUBLE) double x, @SqlType(StandardTypes.DOUBLE) double y)
    {
        OGCGeometry geometry = OGCGeometry.createFromEsriGeometry(new Point(x, y), null);
        return serialize(geometry);
    }

    @Description("Returns a Geometry type Polygon object from Well-Known Text representation (WKT)")
    @ScalarFunction("ST_Polygon")
    @SqlType(NAME)
    public static Slice stPolygon(@SqlType(StandardTypes.VARCHAR) Slice input)
    {
        OGCGeometry geometry = getGeometry(OGCGeometry.fromText(input.toStringUtf8()), "ST_Polygon", POLYGON);
        return serialize(geometry);
    }

    @Description("Returns the area of a polygon using Euclidean measurement on a 2D plane (based on spatial ref) in projected units")
    @ScalarFunction("ST_Area")
    @SqlType(StandardTypes.DOUBLE)
    public static double stArea(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_Area", POLYGON);
        return geometry.getEsriGeometry().calculateArea2D();
    }

    @Description("Returns a Geometry type object from Well-Known Text representation (WKT)")
    @ScalarFunction("ST_GeometryFromText")
    @SqlType(NAME)
    public static Slice stGeometryFromText(@SqlType(StandardTypes.VARCHAR) Slice input)
    {
        return serialize(OGCGeometry.fromText(input.toStringUtf8()));
    }

    @SqlNullable
    @Description("Returns the Well-Known Text (WKT) representation of the geometry")
    @ScalarFunction("ST_AsText")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice stAsText(@SqlType(NAME) Slice input)
    {
        return Slices.utf8Slice(deserialize(input).asText());
    }

    @Description("Returns the Point value that is the mathematical centroid of a Geometry")
    @ScalarFunction("ST_Centroid")
    @SqlType(NAME)
    public static Slice stCentroid(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_Centroid", POINT, MULTI_POINT, LINE_STRING, MULTI_LINE_STRING, POLYGON, MULTI_POLYGON);
        String type = geometry.geometryType();
        if (type.equals(POINT)) {
            return input;
        }

        int pointCount = ((MultiVertexGeometry) geometry.getEsriGeometry()).getPointCount();
        if (pointCount == 0) {
            return serialize(OGCGeometry.createFromEsriGeometry(new Point(), geometry.getEsriSpatialReference()));
        }

        Point centroid = null;
        if (type.equals(MULTI_POINT)) {
            centroid = getPointsCentroid(geometry);
        }
        else if (type.equals(LINE_STRING) || type.equals(MULTI_LINE_STRING)) {
            centroid = getLineCentroid(geometry);
        }
        else if (type.equals(POLYGON)) {
            centroid = getPolygonCentroid(geometry);
        }
        else {
            centroid = getMultiPolygonCentroid(geometry);
        }
        return serialize(OGCGeometry.createFromEsriGeometry(centroid, geometry.getEsriSpatialReference()));
    }

    @Description("Return the coordinate dimension of the Geometry")
    @ScalarFunction("ST_CoordDim")
    @SqlType(StandardTypes.TINYINT)
    public static long stCoordinateDimension(@SqlType(NAME) Slice input)
    {
        return deserialize(input).coordinateDimension();
    }

    @Description("Returns the inherent dimension of this Geometry object, which must be less than or equal to the coordinate dimension")
    @ScalarFunction("ST_Dimension")
    @SqlType(StandardTypes.TINYINT)
    public static long stDimension(@SqlType(NAME) Slice input)
    {
        return deserialize(input).dimension();
    }

    @SqlNullable
    @Description("Returns TRUE if the LineString or Multi-LineString's start and end points are coincident")
    @ScalarFunction("ST_IsClosed")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stIsClosed(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_IsClosed", LINE_STRING, MULTI_LINE_STRING);
        MultiPath lines = (MultiPath) geometry.getEsriGeometry();
        int pathCount = lines.getPathCount();
        for (int i = 0; i < pathCount; i++) {
            Point start = lines.getPoint(lines.getPathStart(i));
            Point end = lines.getPoint(lines.getPathEnd(i) - 1);
            if (!end.equals(start)) {
                return false;
            }
        }
        return true;
    }

    @SqlNullable
    @Description("Returns TRUE if this Geometry is an empty geometrycollection, polygon, point etc")
    @ScalarFunction("ST_IsEmpty")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stIsEmpty(@SqlType(NAME) Slice input)
    {
        return deserialize(input).isEmpty();
    }

    @Description("Returns the length of a LineString or Multi-LineString using Euclidean measurement on a 2D plane (based on spatial ref) in projected units")
    @ScalarFunction("ST_Length")
    @SqlType(StandardTypes.DOUBLE)
    public static double stLength(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_Length", LINE_STRING, MULTI_LINE_STRING);
        return geometry.getEsriGeometry().calculateLength2D();
    }

    @Description("Returns X maxima of a bounding box of a Geometry")
    @ScalarFunction("ST_XMax")
    @SqlType(StandardTypes.DOUBLE)
    public static double stXMax(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = deserialize(input);
        Envelope envelope = getEnvelope(geometry);
        return envelope.getXMax();
    }

    @Description("Returns Y maxima of a bounding box of a Geometry")
    @ScalarFunction("ST_YMax")
    @SqlType(StandardTypes.DOUBLE)
    public static double stYMax(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = deserialize(input);
        Envelope envelope = getEnvelope(geometry);
        return envelope.getYMax();
    }

    @Description("Returns X minima of a bounding box of a Geometry")
    @ScalarFunction("ST_XMin")
    @SqlType(StandardTypes.DOUBLE)
    public static double stXMin(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = deserialize(input);
        Envelope envelope = getEnvelope(geometry);
        return envelope.getXMin();
    }

    @Description("Returns Y minima of a bounding box of a Geometry")
    @ScalarFunction("ST_YMin")
    @SqlType(StandardTypes.DOUBLE)
    public static double stYMin(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = deserialize(input);
        Envelope envelope = getEnvelope(geometry);
        return envelope.getYMin();
    }

    @Description("Returns the cardinality of the collection of interior rings of a polygon")
    @ScalarFunction("ST_NumInteriorRing")
    @SqlType(StandardTypes.BIGINT)
    public static long stNumInteriorRings(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_NumInteriorRing", POLYGON);
        return ((OGCPolygon) geometry).numInteriorRing();
    }

    @Description("Returns the number of points in a Geometry")
    @ScalarFunction("ST_NumPoints")
    @SqlType(StandardTypes.BIGINT)
    public static long stNumPoints(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = deserialize(input);
        if (geometry.getEsriGeometry().isEmpty()) {
            return 0;
        }
        else if (geometry.geometryType().equals(POINT)) {
            return 1;
        }
        return ((MultiVertexGeometry) geometry.getEsriGeometry()).getPointCount();
    }

    @SqlNullable
    @Description("Returns TRUE if and only if the line is closed and simple")
    @ScalarFunction("ST_IsRing")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stIsRing(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_IsRing", LINE_STRING);
        OGCLineString line = (OGCLineString) geometry;
        return line.isClosed() && line.isSimple();
    }

    @Description("Returns the first point of a LINESTRING geometry as a Point")
    @ScalarFunction("ST_StartPoint")
    @SqlType(NAME)
    public static Slice stStartPoint(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_StartPoint", LINE_STRING);
        MultiPath lines = (MultiPath) geometry.getEsriGeometry();
        SpatialReference reference = geometry.getEsriSpatialReference();
        return serialize(OGCGeometry.createFromEsriGeometry(lines.getPoint(0), reference));
    }

    @Description("Returns the last point of a LINESTRING geometry as a Point")
    @ScalarFunction("ST_EndPoint")
    @SqlType(NAME)
    public static Slice stEndPoint(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_EndPoint", LINE_STRING);
        MultiPath lines = (MultiPath) geometry.getEsriGeometry();
        SpatialReference reference = geometry.getEsriSpatialReference();
        return serialize(OGCGeometry.createFromEsriGeometry(lines.getPoint(lines.getPointCount() - 1), reference));
    }

    @Description("Return the X coordinate of the point")
    @ScalarFunction("ST_X")
    @SqlType(StandardTypes.DOUBLE)
    public static double stX(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_X", POINT);
        return ((OGCPoint) geometry).X();
    }

    @Description("Return the Y coordinate of the point")
    @ScalarFunction("ST_Y")
    @SqlType(StandardTypes.DOUBLE)
    public static double stY(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_Y", POINT);
        return ((OGCPoint) geometry).Y();
    }

    @Description("Returns the closure of the combinatorial boundary of this Geometry")
    @ScalarFunction("ST_Boundary")
    @SqlType(NAME)
    public static Slice stBoundary(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = deserialize(input);
        return serialize(geometry.boundary());
    }

    @Description("Returns the bounding rectangular polygon of a Geometry")
    @ScalarFunction("ST_Envelope")
    @SqlType(NAME)
    public static Slice stEnvelope(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = deserialize(input);
        SpatialReference reference = geometry.getEsriSpatialReference();
        Envelope envelope = getEnvelope(geometry);
        return serialize(OGCGeometry.createFromEsriGeometry(envelope, reference));
    }

    @Description("Returns the Geometry value that represents the point set difference of two geometries")
    @ScalarFunction("ST_Difference")
    @SqlType(NAME)
    public static Slice stDifference(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return serialize(leftGeometry.difference(rightGeometry));
    }

    @Description("Returns the 2-dimensional cartesian minimum distance (based on spatial ref) between two geometries in projected units")
    @ScalarFunction("ST_Distance")
    @SqlType(StandardTypes.DOUBLE)
    public static double stDistance(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.distance(rightGeometry);
    }

    @Description("Returns a line string representing the exterior ring of the POLYGON")
    @ScalarFunction("ST_ExteriorRing")
    @SqlType(NAME)
    public static Slice stExteriorRing(@SqlType(NAME) Slice input)
    {
        OGCGeometry geometry = getGeometry(deserialize(input), "ST_ExteriorRing", POLYGON);
        return serialize(((OGCPolygon) geometry).exteriorRing());
    }

    @Description("Returns the Geometry value that represents the point set intersection of two Geometries")
    @ScalarFunction("ST_Intersection")
    @SqlType(NAME)
    public static Slice stIntersection(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return serialize(leftGeometry.intersection(rightGeometry));
    }

    @Description("Returns the Geometry value that represents the point set symmetric difference of two Geometries")
    @ScalarFunction("ST_SymDifference")
    @SqlType(NAME)
    public static Slice stSymmetricDifference(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return serialize(leftGeometry.symDifference(rightGeometry));
    }

    @SqlNullable
    @Description("Returns TRUE if and only if no points of right lie in the exterior of left, and at least one point of the interior of left lies in the interior of right")
    @ScalarFunction("ST_Contains")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stContains(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.contains(rightGeometry);
    }

    @SqlNullable
    @Description("Returns TRUE if the supplied geometries have some, but not all, interior points in common")
    @ScalarFunction("ST_Crosses")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stCrosses(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.crosses(rightGeometry);
    }

    @SqlNullable
    @Description("Returns TRUE if the Geometries do not spatially intersect - if they do not share any space together")
    @ScalarFunction("ST_Disjoint")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stDisjoint(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.disjoint(rightGeometry);
    }

    @SqlNullable
    @Description("Returns TRUE if the given geometries represent the same geometry")
    @ScalarFunction("ST_Equals")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stEquals(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.equals(rightGeometry);
    }

    @SqlNullable
    @Description("Returns TRUE if the Geometries spatially intersect in 2D - (share any portion of space) and FALSE if they don't (they are Disjoint)")
    @ScalarFunction("ST_Intersects")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stIntersects(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.intersects(rightGeometry);
    }

    @SqlNullable
    @Description("Returns TRUE if the Geometries share space, are of the same dimension, but are not completely contained by each other")
    @ScalarFunction("ST_Overlaps")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stOverlaps(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.overlaps(rightGeometry);
    }

    @SqlNullable
    @Description("Returns TRUE if this Geometry is spatially related to another Geometry")
    @ScalarFunction("ST_Relate")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stRelate(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right, @SqlType(StandardTypes.VARCHAR) Slice relation)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.relate(rightGeometry, relation.toStringUtf8());
    }

    @SqlNullable
    @Description("Returns TRUE if the geometries have at least one point in common, but their interiors do not intersect")
    @ScalarFunction("ST_Touches")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stTouches(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.touches(rightGeometry);
    }

    @SqlNullable
    @Description("Returns TRUE if the geometry A is completely inside geometry B")
    @ScalarFunction("ST_Within")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean stWithin(@SqlType(NAME) Slice left, @SqlType(NAME) Slice right)
    {
        OGCGeometry leftGeometry = deserialize(left);
        OGCGeometry rightGeometry = deserialize(right);
        verifySameSpatialReference(leftGeometry, rightGeometry);
        return leftGeometry.within(rightGeometry);
    }

    private static OGCGeometry getGeometry(OGCGeometry geometry, String function, String...validTypes)
    {
        requireNonNull(geometry, "geometry is null");
        String type = geometry.geometryType();
        for (String validType : validTypes) {
            if (type.equals(validType)) {
                return geometry;
            }
        }
        throw new IllegalArgumentException(function + " only applies to " + StringUtils.join(asList(validTypes), " or ") + ". Input type is: " + type);
    }

    private static Envelope getEnvelope(OGCGeometry geometry)
    {
        Envelope envelope = new Envelope();
        geometry.getEsriGeometry().queryEnvelope(envelope);
        return envelope;
    }

    private static void verifySameSpatialReference(OGCGeometry leftGeometry, OGCGeometry rightGeometry)
    {
        checkArgument(leftGeometry.getEsriSpatialReference().equals(rightGeometry.getEsriSpatialReference()), "Input Geometries Spatial Reference do not match");
    }

    // Points centroid is arithmetic mean of the input points
    private static Point getPointsCentroid(OGCGeometry geometry)
    {
        MultiVertexGeometry multiVertex = (MultiVertexGeometry) geometry.getEsriGeometry();
        double xSum = 0;
        double ySum = 0;
        for (int i = 0; i < multiVertex.getPointCount(); i++) {
            Point point = multiVertex.getPoint(i);
            xSum += point.getX();
            ySum += point.getY();
        }
        return new Point(xSum / multiVertex.getPointCount(), ySum / multiVertex.getPointCount());
    }

    // Lines centroid is weighted mean of each line segment, weight in terms of line length
    private static Point getLineCentroid(OGCGeometry geometry)
    {
        Polyline polyline = (Polyline) geometry.getEsriGeometry();
        double xSum = 0;
        double ySum = 0;
        double weightSum = 0;
        for (int i = 0; i < polyline.getPathCount(); i++) {
            Point startPoint = polyline.getPoint(polyline.getPathStart(i));
            Point endPoint = polyline.getPoint(polyline.getPathEnd(i) - 1);
            Line line = new Line();
            line.setStart(startPoint);
            line.setEnd(endPoint);
            double weight = line.calculateLength2D();
            weightSum += weight;
            xSum += (startPoint.getX() + endPoint.getX()) * weight / 2;
            ySum += (startPoint.getY() + endPoint.getY()) * weight / 2;
        }
        return new Point(xSum / weightSum, ySum / weightSum);
    }

    // Polygon centroid: area weighted average of centroids in case of holes
    private static Point getPolygonCentroid(OGCGeometry geometry)
    {
        Polygon polygon = (Polygon) geometry.getEsriGeometry();
        final int pathCount = polygon.getPathCount();

        if (pathCount == 1) {
            return getPolygonSansHolesCentroid(polygon);
        }

        double xSum = 0;
        double ySum = 0;
        double areaSum = 0;

        for (int i = 0; i < pathCount; i++) {
            final int startIndex = polygon.getPathStart(i);
            final int endIndex = polygon.getPathEnd(i);

            final Polygon sansHoles = getPolygon(polygon, startIndex, endIndex);

            final Point centroid = getPolygonSansHolesCentroid(sansHoles);
            final double area = sansHoles.calculateArea2D();

            xSum += centroid.getX() * area;
            ySum += centroid.getY() * area;
            areaSum += area;
        }

        return new Point(xSum / areaSum, ySum / areaSum);
    }

    private static Polygon getPolygon(Polygon polygon, int startIndex, int endIndex)
    {
        final Polyline boundary = new Polyline();
        boundary.startPath(polygon.getPoint(startIndex));
        for (int i = startIndex + 1; i < endIndex; i++) {
            Point current = polygon.getPoint(i);
            boundary.lineTo(current);
        }

        final Polygon newPolygon = new Polygon();
        newPolygon.add(boundary, false);
        return newPolygon;
    }

    // Polygon sans holes centroid:
    // c[x] = (Sigma(x[i] + x[i + 1]) * (x[i] * y[i + 1] - x[i + 1] * y[i]), for i = 0 to N - 1) / (6 * signedArea)
    // c[y] = (Sigma(y[i] + y[i + 1]) * (x[i] * y[i + 1] - x[i + 1] * y[i]), for i = 0 to N - 1) / (6 * signedArea)
    private static Point getPolygonSansHolesCentroid(Polygon polygon)
    {
        final int pointCount = polygon.getPointCount();
        double xSum = 0;
        double ySum = 0;
        double signedArea = 0;
        for (int i = 0; i < pointCount; i++) {
            Point current = polygon.getPoint(i);
            Point next = (i + 1 == polygon.getPointCount()) ? polygon.getPoint(0) : polygon.getPoint(i + 1);
            double ladder = current.getX() * next.getY() - next.getX() * current.getY();
            xSum += (current.getX() + next.getX()) * ladder;
            ySum += (current.getY() + next.getY()) * ladder;
            signedArea += ladder;
        }
        return new Point(xSum / (signedArea * 3), ySum / (signedArea * 3));
    }

    // MultiPolygon centroid is weighted mean of each polygon, weight in terms of polygon area
    private static Point getMultiPolygonCentroid(OGCGeometry geometry)
    {
        OGCMultiPolygon multiPolygon = (OGCMultiPolygon) geometry;
        double xSum = 0;
        double ySum = 0;
        double weightSum = 0;
        for (int i = 0; i < multiPolygon.numGeometries(); i++) {
            Point centroid = getPolygonCentroid(multiPolygon.geometryN(i));
            Polygon polygon = (Polygon) multiPolygon.geometryN(i).getEsriGeometry();
            double weight = polygon.calculateArea2D();
            weightSum += weight;
            xSum += centroid.getX() * weight;
            ySum += centroid.getY() * weight;
        }
        return new Point(xSum / weightSum, ySum / weightSum);
    }
}

=====================
GeoSpatial Functions
=====================

Presto supports SQL/MM specification

Constructors
------------

.. function:: ST_Point(double, double) -> point

    Returns a Geometry type Point object with the given coordinate values.

.. function:: ST_LineFromText(varchar) -> line

    Returns a Geometry type LineString object from Well-Known Text representation (WKT).

.. function:: ST_Polygon(varchar) -> polygon

    Returns a Geometry type Polygon object from Well-Known Text representation (WKT).

.. function:: ST_GeometryFromText(varchar) -> Geometry

    Returns a Geometry type object from Well-Known Text representation (WKT).

.. function:: stAsText(Geometry) -> varchar

    Returns the Well-Known Text (WKT) representation of the Geometry.

Relationship Tests
------------------

.. function:: ST_Contains(Geometry, Geometry) -> boolean

    Returns TRUE if and only if no points of right lie in the exterior of left, and at least one point of the interior of left lies in the interior of right.

.. function:: ST_Crosses(Geometry, Geometry) -> boolean

    Returns TRUE if the supplied geometries have some, but not all, interior points in common.

.. function:: ST_Disjoint(Geometry, Geometry) -> boolean

    Returns TRUE if the Geometries do not "spatially intersect" - if they do not share any space together.

.. function:: ST_Equals(Geometry, Geometry) -> boolean

    Returns TRUE if the given geometries represent the same Geometry.

.. function:: ST_Intersects(Geometry, Geometry) -> boolean

    Returns TRUE if the Geometries spatially intersect in 2D - (share any portion of space) and FALSE if they don't (they are Disjoint)

.. function:: ST_Overlaps(Geometry, Geometry) -> boolean

    Returns TRUE if the Geometries share space, are of the same dimension, but are not completely contained by each other.

.. function:: ST_Relate(Geometry, Geometry) -> boolean

    Returns TRUE if this Geometry is spatially related to another Geometry.

.. function:: ST_Touches(Geometry, Geometry) -> boolean

    Returns TRUE if the geometries have at least one point in common, but their interiors do not intersect.

.. function:: ST_Within(Geometry, Geometry) -> boolean

    Returns TRUE if the Geometry A is completely inside Geometry B.

Operations
----------

.. function:: ST_Boundary(Geometry) -> Geometry

    Returns the closure of the combinatorial boundary of this Geometry.

.. function:: ST_Difference(Geometry, Geometry) -> Geometry

    Returns the Geometry value that represents the point set difference of two geometries.

.. function:: ST_Envelope(Geometry) -> Geometry

    Returns the bounding rectangular polygon of a geometry.

.. function:: ST_ExteriorRing(Geometry) -> Geometry

    Returns a line string representing the exterior ring of the POLYGON.

.. function:: ST_Intersection(Geometry, Geometry) -> Geometry

    Returns the Geometry value that represents the point set intersection of two Geometries.

.. function:: ST_SymDifference(Geometry, Geometry) -> Geometry

    Returns the Geometry value that represents the point set symmetric difference of two Geometries.

Accessors
---------

.. function:: ST_Area(Geometry) -> double

    Returns the area of a polygon using Euclidean measurement on a 2D plane (based on spatial ref) in projected units.

.. function:: ST_Centroid(Geometry) -> Geometry

    Returns the Point value that is the mathematical centroid of a Geometry.

.. function:: ST_CoordDim(Geometry) -> bigint

    Return the coordinate dimension of the Geometry.

.. function:: ST_Dimension(Geometry) -> bigint

    Returns the inherent dimension of this Geometry object, which must be less than or equal to the coordinate dimension.

.. function:: ST_Distance(Geometry, Geometry) -> double

    Returns the 2-dimensional cartesian minimum distance (based on spatial ref) between two Geometries in projected units.

.. function:: ST_IsClosed(Geometry) -> boolean

    Returns TRUE if the LineString's start and end points are coincident.

.. function:: ST_IsEmpty(Geometry) -> boolean

    Returns TRUE if this Geometry is an empty Geometrycollection, polygon, point etc.

.. function:: ST_IsRing(Geometry) -> boolean

    Returns TRUE if and only if the line is closed and simple.

.. function:: ST_Length(Geometry) -> double

    Returns the length of a LineString or Multi-LineString using Euclidean measurement on a 2D plane (based on spatial ref) in projected units.

.. function:: ST_XMax(Geometry) -> double

    Returns X maxima of a bounding box of a Geometry.

.. function:: ST_YMax(Geometry) -> double

    Returns Y maxima of a bounding box of a Geometry.

.. function:: ST_XMin(Geometry) -> double

    Returns X minima of a bounding box of a Geometry.

.. function:: ST_YMin(Geometry) -> double

    Returns Y minima of a bounding box of a Geometry.

.. function:: ST_StartPoint(Geometry) -> point

    Returns the first point of a LineString Geometry as a Point.

.. function:: ST_EndPoint(Geometry) -> point

    Returns the last point of a LineString Geometry as a Point.

.. function:: ST_X(point) -> double

    Return the X coordinate of the point.

.. function:: ST_Y(point) -> double

    Return the Y coordinate of the point.

.. function:: ST_NumPoints(Geometry) -> bigint

    Returns the number of points in a Geometry. This is an extension to SQL/MM ST_NumPoints which only applies to Point and LineString.

.. function:: ST_NumInteriorRing(Geometry) -> bigint

    Returns the cardinality of the collection of interior rings of a polygon.

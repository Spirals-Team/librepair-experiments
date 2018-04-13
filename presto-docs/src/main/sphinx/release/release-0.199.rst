=============
Release 0.199
=============

General Changes
---------------

* Add support for spatial LEFT join.
* Add :func:`hmac_md5`, :func:`hmac_sha1`, :func:`hmac_sha256`, and :func:`hmac_sha512` functions.
* Add :func:`array_sort` function that takes a lambda as a comparator.
* Add :func:`line_locate_point` geospatial function.
* Add support for authentication with JWT access token.
* Add support for ``ORDER BY`` clause in aggregations for queries that use grouping sets.
* Expand grouped execution support to ``GROUP BY`` and ``UNION ALL``, making it possible
  to execute aggregations with less peak memory usage.
* Change the signature of ``round(x, d)`` and ``truncate(x, d)`` functions so that
  ``d`` is of type ``INTEGER``. Previously, ``d`` could be of type ``BIGINT``.
  This behavior can be reverted with the ``deprecated.legacy-round-n-bigint`` config option
  or the ``legacy_round_n_bigint`` session property.
* Add support for installing Presto server RPM to machines with OpenJDK.
* Optimize the :func:`ST_Intersection` function for rectangles aligned with coordinate axes
  (e.g., polygons produced by the :func:`ST_Envelope` and :func:`bing_tile_polygon` functions).
* Finish a query early if the table on the right side of an INNER or RIGHT join doesn't produce any rows. Similarly,
  if the left side of an INNER or LEFT join doesn't produce any rows the query will finish early.
* Improve planning performance when evaluating partition candidates.
* Improve the performance of queries that have ``LIKE`` predicates and that read from ``information_schema`` tables.
* Improve the performance of map-to-map cast.
* Improve the performance of :func:`ST_Touches`, :func:`ST_Within`, :func:`ST_Overlaps`, :func:`ST_Disjoint`,
  and :func:`ST_Crosses` functions.
* Improve the serialization performance of geometry values.

Hive Changes
------------

* Fix failure when writing ``NULL`` values into columns of type ``ROW``, ``MAP``, and ``ARRAY`` with ORC writers.
* Fix ORC writers incorrectly writing non-null values as ``NULL`` for all types.
* Support reading Hive partitions that have a different bucket count from the table,
  as long as the ratio is a power of 2 (1:2^n or 2^n:1).
* For each partitioned table Hive connector exposes a system table that contains the partition values. If
  source table is named ``some_table`` then partitions table is named ``some_table$partitions``. The
  partitions table provides the same data that the ``SHOW PARTITIONS FROM`` returns.
* Listing table partitions (both with the ``...$partitions`` table and using ``SHOW PARTITIONS FROM``) is no
  longer subject to the limit defined by the ``hive.max-partitions-per-scan`` config option.


SPI Changes
-----------

* Add ``resourceGroupId`` and ``queryType`` fields to ``SessionConfigurationContext``.
* Simplify the constructor of ``RowBlock``.

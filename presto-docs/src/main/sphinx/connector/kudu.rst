==============
Kudu Connector
==============

The kudu connector allows querying, inserting and deleting data in `Apache Kudu`_

.. _Apache Kudu: https://kudu.apache.org/


.. contents::
    :local:
    :backlinks: none
    :depth: 1


Compatibility
-------------

Connector is compatible with all Apache Kudu versions starting from 1.0.

If the connector uses features that are not available on the target server, an error will be returned.
Apache Kudu 1.7.0 is currently used for testing.


Configuration
-------------

To configure the Kudu connector, create a catalog properties file
``etc/catalog/kudu.properties`` with the following contents,
replacing the properties as appropriate:

  .. code-block:: none

       connector.name=kudu

       ## List of Kudu master addresses, at least one is needed (comma separated)
       ## Supported formats: example.com, example.com:7051, 192.0.2.1, 192.0.2.1:7051,
       ##                    [2001:db8::1], [2001:db8::1]:7051, 2001:db8::1
       kudu.client.master-addresses=localhost

       ## Optional restriction of tablets for specific tenant.
       ## If a tenant is set, only Kudu tablets starting with `<tenant>.` will
       ## be visible in Presto
       #kudu.session.tenant=mytenant

       #######################
       ### Advanced Kudu Java client configuration
       #######################

       ## Default timeout used for administrative operations (e.g. createTable, deleteTable, etc.)
       #kudu.client.defaultAdminOperationTimeout = 30s

       ## Default timeout used for user operations
       #kudu.client.defaultOperationTimeout = 30s

       ## Default timeout to use when waiting on data from a socket
       #kudu.client.defaultSocketReadTimeout = 10s

       ## Disable Kudu client's collection of statistics.
       #kudu.client.disableStatistics = false


Querying Data
-------------

A Kudu table named ``mytable`` is available in Presto as table
``kudu.default.mytable``. A Kudu table containing a dot is considered as
a schema/table combination, e.g. ``dev.mytable`` is mapped to the Presto
table \`kudu.dev.mytable. Only Kudu table names in lower case are
currently supported.

Before using any tablets, it is needed to create the default schema,
e.g.

  .. code:: sql

    CREATE SCHEMA default;

Example
~~~~~~~

-  Create default schema if needed:

  .. code:: sql

    CREATE SCHEMA IF NOT EXISTS default;

-  Now you can use any Kudu table, if it is lower case and contains no
   dots.
-  Alternatively you can create a users table with

  .. code:: sql

    CREATE TABLE users (
      user_id int,
      first_name varchar,
      last_name varchar
    ) WITH (
     column_design = '{"user_id": {"key": true}}',
     partition_design = '{"hash":[{"columns":["user_id"], "buckets": 2}]}',
     num_replicas = 1
    );

On creating a Kudu table you must/can specify addition information about
the primary key, encoding, and compression of columns and hash or range
partitioning, and the number of replicas. Details see below in section
“Create Kudu Table”.

-  The table can be described using

  .. code:: sql

    DESCRIBE kudu.default.users;

You should get something like

::

       Column   |  Type   |                               Extra                               | Comment
    ------------+---------+-------------------------------------------------------------------+---------
     user_id    | integer | key, encoding=AUTO_ENCODING, compression=DEFAULT_COMPRESSION      |
     first_name | varchar | nullable, encoding=AUTO_ENCODING, compression=DEFAULT_COMPRESSION |
     last_name  | varchar | nullable, encoding=AUTO_ENCODING, compression=DEFAULT_COMPRESSION |
    (3 rows)

-  Insert some data with

  .. code:: sql

    INSERT INTO users VALUES (1, 'Donald', 'Duck'), (2, 'Mickey', 'Mouse');

-  Select the inserted data

  .. code:: sql

    SELECT * FROM users;


Data Type Mapping
-----------------

The data types of Presto and Kudu are mapped as far as possible:

+-----------------------+-----------------------+-----------------------+
| Presto Data Type      | Kudu Data Type        | Comment               |
+=======================+=======================+=======================+
| ``BOOLEAN``           | ``BOOL``              |                       |
+-----------------------+-----------------------+-----------------------+
| ``TINYINT``           | ``INT8``              |                       |
+-----------------------+-----------------------+-----------------------+
| ``SMALLINT``          | ``INT16``             |                       |
+-----------------------+-----------------------+-----------------------+
| ``INTEGER``           | ``INT32``             |                       |
+-----------------------+-----------------------+-----------------------+
| ``BIGINT``            | ``INT64``             |                       |
+-----------------------+-----------------------+-----------------------+
| ``REAL``              | ``FLOAT``             |                       |
+-----------------------+-----------------------+-----------------------+
| ``DOUBLE``            | ``DOUBLE``            |                       |
+-----------------------+-----------------------+-----------------------+
| ``VARCHAR``           | ``STRING``            | see [1]_              |
+-----------------------+-----------------------+-----------------------+
| ``VARBINARY``         | ``BINARY``            | see [1]_              |
+-----------------------+-----------------------+-----------------------+
| ``TIMESTAMP``         | ``UNIXTIME_MICROS``   | µs resolution in Kudu |
|                       |                       | column is reduced to  |
|                       |                       | ms resolution         |
+-----------------------+-----------------------+-----------------------+
| ``DECIMAL``           | ``DECIMAL``           | only supported for    |
|                       |                       | Kudu server >= 1.7.0  |
+-----------------------+-----------------------+-----------------------+
| ``CHAR``              | -                     | not supported         |
+-----------------------+-----------------------+-----------------------+
| ``DATE``              | -                     | not supported [2]_    |
+-----------------------+-----------------------+-----------------------+
| ``TIME``              | -                     | not supported         |
+-----------------------+-----------------------+-----------------------+
| ``JSON``              | -                     | not supported         |
+-----------------------+-----------------------+-----------------------+
| ``TIME WITH           | -                     | not supported         |
| TIMEZONE``            |                       |                       |
+-----------------------+-----------------------+-----------------------+
| ``TIMESTAMP WITH TIME | -                     | not supported         |
| ZONE``                |                       |                       |
+-----------------------+-----------------------+-----------------------+
| ``INTERVAL YEAR TO MO | -                     | not supported         |
| NTH``                 |                       |                       |
+-----------------------+-----------------------+-----------------------+
| ``INTERVAL DAY TO SEC | -                     | not supported         |
| OND``                 |                       |                       |
+-----------------------+-----------------------+-----------------------+
| ``ARRAY``             | -                     | not supported         |
+-----------------------+-----------------------+-----------------------+
| ``MAP``               | -                     | not supported         |
+-----------------------+-----------------------+-----------------------+
| ``IPADDRESS``         | -                     | not supported         |
+-----------------------+-----------------------+-----------------------+


.. [1] On performing ``CREATE TABLE ... AS ...`` from a Presto table to Kudu,
   the optional maximum length is lost

.. [2] On performing ``CREATE TABLE ... AS ...`` from a Presto table to Kudu,
   a ``DATE`` column is converted to ``STRING``


Supported Presto SQL statements
-------------------------------

+---------------------------------------+-------------------------------+
| Presto SQL statement                  | Comment                       |
+=======================================+===============================+
| ``SELECT``                            |                               |
+---------------------------------------+-------------------------------+
| ``INSERT INTO ... VALUES``            | behaves like ``upsert``       |
+---------------------------------------+-------------------------------+
| ``INSERT INTO ... SELECT ...``        | behaves like ``upsert``       |
+---------------------------------------+-------------------------------+
| ``DELETE``                            |                               |
+---------------------------------------+-------------------------------+
| ``CREATE SCHEMA``                     |                               |
+---------------------------------------+-------------------------------+
| ``DROP SCHEMA``                       |                               |
+---------------------------------------+-------------------------------+
| ``CREATE TABLE``                      |                               |
+---------------------------------------+-------------------------------+
| ``CREATE TABLE ... AS``               |                               |
+---------------------------------------+-------------------------------+
| ``DROP TABLE``                        |                               |
+---------------------------------------+-------------------------------+
| ``ALTER TABLE ... RENAME TO ...``     |                               |
+---------------------------------------+-------------------------------+
| ``ALTER TABLE ... RENAME COLUMN ...`` | if not part of primary key    |
+---------------------------------------+-------------------------------+
| ``ALTER TABLE ... ADD COLUMN ...``    |                               |
+---------------------------------------+-------------------------------+
| ``ALTER TABLE ... DROP COLUMN ...``   | if not part of primary key    |
+---------------------------------------+-------------------------------+
| ``SHOW SCHEMAS``                      |                               |
+---------------------------------------+-------------------------------+
| ``SHOW TABLES``                       |                               |
+---------------------------------------+-------------------------------+
| ``SHOW CREATE TABLE``                 |                               |
+---------------------------------------+-------------------------------+
| ``SHOW COLUMNS FROM``                 |                               |
+---------------------------------------+-------------------------------+
| ``DESCRIBE``                          | same as ``SHOW COLUMNS FROM`` |
+---------------------------------------+-------------------------------+
| ``CALL kudu.system.add_range_partitio | add range partition to an     |
| n``                                   | existing table                |
+---------------------------------------+-------------------------------+
| ``CALL kudu.system.drop_range_partiti | drop an existing range        |
| on``                                  | partition from a table        |
+---------------------------------------+-------------------------------+

Not supported are ``SHOW PARTITIONS FROM ...``, ``ALTER SCHEMA ... RENAME``


Create Kudu Table with ``CREATE TABLE``
---------------------------------------

On creating a Kudu Table you need to provide following table properties:
  - ``column_design`` 
  - ``partition_design``
  - ``num_replicas`` (optional, defaults to 3)

Example:

  .. code:: sql

    CREATE TABLE users (
      user_id int,
      first_name varchar,
      last_name varchar
    ) WITH (
     column_design = '{"user_id": {"key": true}}',
     partition_design = '{"hash":[{"columns":["user_id"], "buckets": 2}]}',
     num_replicas = 1
    );


Table property ``column_design``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

With the column design table property you define the columns for the
primary key. Additionally you can overwrite the encoding and compression
of every single column.

The value of this property must be a string of a valid JSON object. The
keys are the columns and the values is a JSON object with the columns
properties to set, i.e.

  ::

    '{"<column name>": {"<column property name>": <value>, ...}, ...}'`


+----------------------+-----------+--------------------------------+
| Column property name | Value     | Comment                        |
+======================+===========+================================+
| ``key``              | ``true``  | If column belongs to primary   |
|                      | or        | key, default : ``false``       |
|                      | ``false`` |                                |
+----------------------+-----------+--------------------------------+
| ``nullable``         | ``true``  | If column is nullable,         |
|                      | or        | default: ``true``. For         |
|                      | ``false`` | non-key columns, key columns   |
|                      |           | must not be nullable.          |
+----------------------+-----------+--------------------------------+
| ``encoding``         | “string   | See Apache Kudu documentation: |
|                      | value”    | `Column encoding`_             |
+----------------------+-----------+--------------------------------+
| ``compression``      | “string   | See Apache Kudu documentation: |
|                      | value”    | `Column compression`_          |
+----------------------+-----------+--------------------------------+

.. _`Column encoding`: https://kudu.apache.org/docs/schema_design.html#encoding
.. _`Column compression`: https://kudu.apache.org/docs/schema_design.html#compression

Example:

  ::

    '{"column1": {"key": true, "encoding": "dictionary", "compression": "LZ4"}, "column2": {...}}'



Table property ``partition_design``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

With the partition design table property you define the partition
layout. In Apache Kudu you can define multiple hash partitions and at
most one range partition. Details see Apache Kudu documentation:
`Partitioning`_

The value of this property must be a string of a valid JSON object. The
keys are either ``hash`` or ``range`` or both, i.e.

  ::

    '{"hash": [{...},...], "range": {...}}'`

.. _Partitioning: https://kudu.apache.org/docs/schema_design.html#partitioning


Hash partitioning
^^^^^^^^^^^^^^^^^

You can provide multiple hash partition groups in Apache Kudu. Each
group consists of a list of column names and the number of buckets.

Example:

  ::

    '{"hash": [{"columns": ["region", "name"], "buckets": 5}]}'

This defines a hash partition with the columns “region” and “name”,
distributed over 5 buckets. All partition columns must be part of the
primary key.


Range partitioning
^^^^^^^^^^^^^^^^^^

You can provide at most one range partition in Apache Kudu. It consists
of a list of columns. The ranges themselves are given either in the
table property ``range_partitions``. Alternatively, the procedures
``kudu.system.add_range_partition`` and
``kudu.system.drop_range_partition`` can be used to manage range
partitions for existing tables. For both ways see below for more
details.

Example:

  ::

    '{"range": {"columns": ["event_time"]}}'

Defines range partitioning on the column “event”.

To add concrete range partitions use either the table property
``range_partitions`` or call the procedure
``kudu.system.add_range_partition``.


Table property ``range_partitions``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

With the ``range_partitions`` table property you specify the concrete
range partitions to be created. The range partition definition itself
must be given in the table property ``partition_design`` separately.

Example:

  .. code:: sql

    CREATE TABLE events (
      serialno varchar,
      event_time timestamp,
      message varchar
    ) WITH (
     column_design = '{"serialno": {"key": true}, "event_time": {"key": true}}',
     partition_design = '{"hash":[{"columns":["serialno"], "buckets": 4}],
                          "range": {"columns":["event_time"]}}',
     range_partitions = '[{"lower": null, "upper": "2017-01-01T00:00:00"},
                          {"lower": "2017-01-01T00:00:00", "upper": "2017-07-01T00:00:00"},
                          {"lower": "2017-07-01T00:00:00", "upper": "2018-01-01T00:00:00"}]',
     num_replicas = 1
    );

This creates a table with a hash partition on column ``serialno`` with 4
buckets and range partitioning on column ``event_time``. Additionally
three range partitions are created:

    1. for all event_times before the year 2017 (lower bound = ``null`` means it is unbound)
    2. for the first half of the year 2017
    3. for the second half the year 2017

This means any try to add rows with ``event_time`` of year 2018 or greater will fail, as no partition is defined.

Managing range partitions
^^^^^^^^^^^^^^^^^^^^^^^^^

For existing tables, there are procedures to add and drop a range
partition.

- adding a range partition

  .. code:: sql

    CALL kudu.system.add_range_partition(<schema>, <table>, <range_partition_as_json_string>),

- dropping a range partition

  .. code:: sql

    CALL kudu.system.drop_range_partition(<schema>, <table>, <range_partition_as_json_string>)

  - ``<schema>``: schema of the table

  - ``<table>``: table names

  - ``<range_partition_as_json_string>``: lower and upper bound of the
    range partition as json string in the form
    ``'{"lower": <value>, "upper": <value>}'``, or if the range partition
    has multiple columns:
    ``'{"lower": [<value_col1>,...], "upper": [<value_col1>,...]}'``. The
    concrete literal for lower and upper bound values are depending on
    the column types.

    Examples:

    +-------------------------------+--------------------------------------+
    | Presto Data Type              | JSON string example                  |
    +===============================+======================================+
    | BIGINT                        | ‘{“lower”: 0, “upper”: 1000000}’     |
    +-------------------------------+--------------------------------------+
    | SMALLINT                      | ‘{“lower”: 10, “upper”: null}’       |
    +-------------------------------+--------------------------------------+
    | VARCHAR                       | ‘{“lower”: “A”, “upper”: “M”}’       |
    +-------------------------------+--------------------------------------+
    | TIMESTAMP                     | ‘{“lower”:                           |
    |                               | “2018-02-01T00:00:00.000”, “upper”:  |
    |                               | “2018-02-01T12:00:00.000”}’          |
    +-------------------------------+--------------------------------------+
    | BOOLEAN                       | ‘{“lower”: false, “upper”: true}’    |
    +-------------------------------+--------------------------------------+
    | VARBINARY                     | values encoded as base64 strings     |
    +-------------------------------+--------------------------------------+

    To specified an unbounded bound, use the value ``null``.

Example:

  .. code:: sql

    CALL kudu.system.add_range_partition('myschema', 'events', '{"lower": "2018-01-01", "upper": "2018-06-01"}')

This would add a range partition for a table ``events`` in the schema
``myschema`` with the lower bound ``2018-01-01`` (more exactly
``2018-01-01T00:00:00.000``) and the upper bound ``2018-07-01``.

Use the sql statement ``SHOW CREATE TABLE`` to query the existing
range partitions (they are shown in the table property
``range_partitions``).

Known limitations
-----------------

-  Only lower case table and column names in Kudu are supported
-  As schemas are not directly supported by Kudu, a special table named
   ``$schemas`` is created when using this connector
-  Using a secured Kudu cluster has not been tested.

package com.booking.replication.applier.hbase;

import com.booking.replication.augmenter.model.schema.CurrentPositionSchemaVersion;
import com.booking.replication.augmenter.model.schema.TableSchema;
import com.booking.replication.augmenter.model.QueryAugmentedEventData;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import org.apache.hadoop.hbase.util.RegionSplitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by bdevetak on 27/11/15.
 */
public class HBaseSchemaManager {

    private final Configuration hbaseConf;

    private Connection connection;

    private final Map<String, Integer> knownHBaseTables = new HashMap<>();

    private final Map<String, Object> configuration;

    // Delta tables
    private static final int DELTA_TABLE_MAX_VERSIONS = 1;

    // Mirrored tables
    private static final int MIRRORED_TABLE_DEFAULT_REGIONS = 16;
    private static final int MIRRORED_TABLE_NUMBER_OF_VERSIONS = 1000;

    // schema history table
    private static final int SCHEMA_HISTORY_TABLE_NR_VERSIONS = 1;

    private static boolean DRY_RUN;
    private static boolean USE_SNAPPY;

    private static final byte[] CF = Bytes.toBytes("d");

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseSchemaManager.class);

    public HBaseSchemaManager(Map<String, Object> configuration) {

        DRY_RUN = false;  // (boolean) configuration.get(HBaseApplier.Configuration.DRYRUN);
        USE_SNAPPY = false; // (boolean) configuration.get(HBaseApplier.Configuration.HBASE_USE_SNAPPY);

        this.configuration = configuration;
        this.hbaseConf = initHBaseConfig(configuration);

        if (! DRY_RUN) {
            try {
                connection = ConnectionFactory.createConnection(hbaseConf);
                LOGGER.info("HBaseSchemaManager successfully established connection to HBase.");
            } catch (IOException e) {
                LOGGER.error("HBaseSchemaManager could not connect to HBase");
                e.printStackTrace();
            }
        }
    }

    private Configuration initHBaseConfig(Map<String, Object> configuration) {

        Configuration hbConf = HBaseConfiguration.create();

        // TODO: adapt to BigTable (no zookeeper, impl class properties)

        String ZOOKEEPER_QUORUM =
                (String) configuration.get(HBaseApplier.Configuration.HBASE_ZOOKEEPER_QUORUM);

        hbConf.set("hbase.zookeeper.quorum", ZOOKEEPER_QUORUM);

        return hbConf;

    }

    public void createMirroredTableIfNotExists(String hbaseTableName) throws IOException {

        try {

            if (!DRY_RUN) {
                if (connection == null) {
                    connection = ConnectionFactory.createConnection(hbaseConf);
                }

                Admin admin = connection.getAdmin();
                TableName tableName = TableName.valueOf(hbaseTableName);

                if (!admin.tableExists(tableName)) {

                    LOGGER.info("table " + hbaseTableName + " does not exist in HBase. Creating...");

                    HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
                    HColumnDescriptor cd = new HColumnDescriptor("d");

                    if (USE_SNAPPY) {
                        cd.setCompressionType(Compression.Algorithm.SNAPPY);
                    }

                    cd.setMaxVersions(MIRRORED_TABLE_NUMBER_OF_VERSIONS);
                    tableDescriptor.addFamily(cd);
                    tableDescriptor.setCompactionEnabled(true);

                    // pre-split into default number of regions
                    RegionSplitter.HexStringSplit splitter = new RegionSplitter.HexStringSplit();
                    byte[][] splitKeys = splitter.split(MIRRORED_TABLE_DEFAULT_REGIONS);

                    admin.createTable(tableDescriptor, splitKeys);
                }

                knownHBaseTables.put(hbaseTableName, 1);
            }
        } catch (IOException e) {
            throw new IOException("Failed to create table in HBase", e);
        }
    }

    public void createDeltaTableIfNotExists(String hbaseTableName, boolean isInitialSnapshotMode) throws IOException {

        try {
            if (! DRY_RUN) {

                if (connection == null) {
                    connection = ConnectionFactory.createConnection(hbaseConf);
                }

                Admin admin = connection.getAdmin();
                TableName tableName = TableName.valueOf(hbaseTableName);

                if (!admin.tableExists(tableName)) {

                    LOGGER.info("table " + hbaseTableName + " does not exist in HBase. Creating...");

                    HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
                    HColumnDescriptor cd = new HColumnDescriptor("d");

                    if (USE_SNAPPY) {
                        cd.setCompressionType(Compression.Algorithm.SNAPPY);
                    }
                    cd.setMaxVersions(DELTA_TABLE_MAX_VERSIONS);
                    tableDescriptor.addFamily(cd);
                    tableDescriptor.setCompactionEnabled(true);

                    // TODO 1: make this more configurable
                    // TODO 2: no splits for BigTable
                    // if daily table pre-split to 16 regions;
                    // if initial snapshot pre-split to 256 regions
                    /*if (isInitialSnapshotMode) {
                        RegionSplitter.HexStringSplit splitter = new RegionSplitter.HexStringSplit();
                        byte[][] splitKeys = splitter.split(INITIAL_SNAPSHOT_DEFAULT_REGIONS);
                        admin.createTable(tableDescriptor, splitKeys);
                    } else {
                        RegionSplitter.HexStringSplit splitter = new RegionSplitter.HexStringSplit();
                        byte[][] splitKeys = splitter.split(DAILY_DELTA_TABLE_DEFAULT_REGIONS);
                        admin.createTable(tableDescriptor, splitKeys);
                    }*/

                    admin.createTable(tableDescriptor);
                } else {
                    LOGGER.info("Table " + hbaseTableName + " already exists in HBase. Probably a case of replaying the binlog.");
                }
            }
            knownHBaseTables.put(hbaseTableName,1);
        } catch (IOException e) {
            throw new IOException("Failed to create table in HBase.", e);
        }
    }

    public boolean isTableKnownToHBase(String tableName) {
        return knownHBaseTables.get(tableName) != null;
    }

    public void writeSchemaSnapshotToHBase(
            CurrentPositionSchemaVersion currentPositionSchemaVersion,
            QueryAugmentedEventData queryAugmentedEventData,
            Map<String, Object> configuration
        ) throws IOException, SchemaTransitionException {

        String mySqlDbName = (String) configuration.get(HBaseApplier.Configuration.REPLICATED_SCHEMA_NAME);

        // get sql_statement
        String ddl = queryAugmentedEventData.getSQL();
        if (ddl == null) {
            throw new SchemaTransitionException("DDL can not be null");
        }

        // get pre/post schemas
        TableSchema preChangeSchema  = queryAugmentedEventData.getBefore();
        TableSchema postChangeSchema = queryAugmentedEventData.getAfter();

        String preChangeSchemaJSON = MAPPER.writeValueAsString(preChangeSchema);
        String postChangeSchemaJSON = MAPPER.writeValueAsString(postChangeSchema);

        String preChangeSchemaCreate  = preChangeSchema.getCreate();
        String postChangeSchemaCreate = postChangeSchema.getCreate();

        // TODO: this just logs table-level changes; add schema dump for the whole database
        Map<String, Object> schemaSnapshot = new HashMap<>();
        schemaSnapshot.put("pre_create", preChangeSchema.getCreate());
        schemaSnapshot.put("ddl", ddl);
        schemaSnapshot.put("post_create", postChangeSchema.getCreate());
        schemaSnapshot.put("schema_position_dump_after", currentPositionSchemaVersion);

        String schemaTransitionSequenceJson = MAPPER.writeValueAsString(schemaSnapshot);

        // TODO: verify that snapshot format is compatible with HBaseSnapshotter
        // event
        //       .getPreTransitionSchemaSnapshot()
        //       .getSchemaVersionCreateStatementsJsonSnapshot();
        // event
        //       .getPostTransitionSchemaSnapshot()
        //       .getSchemaVersionCreateStatementsJsonSnapshot();

        // get event timestamp
        Long eventTimestamp = queryAugmentedEventData.getExecutionTime(); // TODO: timestamp logic
                //event.getSchemaChangeEventTimestamp();

        String hbaseTableName = TableNameMapper.getSchemaHistoryHBaseTableName(configuration);

        String hbaseRowKey = eventTimestamp.toString();
        if ((boolean)configuration.get(HBaseApplier.Configuration.INITIAL_SNAPSHOT_MODE)) {
            // in initial-snapshot mode timestamp is overridden by 0 so all create statements
            // fall under the same timestamp. This is ok since there should be only one schema
            // snapshot for the initial-snapshot. However, having key=0 is not good, so replace
            // it with:
            hbaseRowKey = "initial-snapshot";
        }

        try {

            if (connection == null) {
                connection = ConnectionFactory.createConnection(hbaseConf);
            }

            Admin admin = connection.getAdmin();

            TableName tableName = TableName.valueOf(hbaseTableName);

            if (!admin.tableExists(tableName)) {

                LOGGER.info("table " + hbaseTableName + " does not exist in HBase. Creating...");

                HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
                HColumnDescriptor cd = new HColumnDescriptor("d");
                cd.setMaxVersions(SCHEMA_HISTORY_TABLE_NR_VERSIONS);
                tableDescriptor.addFamily(cd);
                tableDescriptor.setCompactionEnabled(true);

                // pre-split into 16 regions
                // RegionSplitter.HexStringSplit splitter = new RegionSplitter.HexStringSplit();
                // byte[][] splitKeys = splitter.split(SCHEMA_HISTORY_TABLE_DEFAULT_REGIONS);

                admin.createTable(tableDescriptor); // , splitKeys);
            } else {
                LOGGER.info("Table " + hbaseTableName + " already exists in HBase. Probably a case of replaying the binlog.");
            }

            // write schema info
            Put put = new Put(Bytes.toBytes(hbaseRowKey));
            String ddlColumnName  = "ddl";
            put.addColumn(
                    CF,
                    Bytes.toBytes(ddlColumnName),
                    eventTimestamp,
                    Bytes.toBytes(schemaTransitionSequenceJson)
            );

            String schemaSnapshotPreColumnName  = "schemaPreChange";
            put.addColumn(
                    CF,
                    Bytes.toBytes(schemaSnapshotPreColumnName),
                    eventTimestamp,
                    Bytes.toBytes(preChangeSchemaJSON)
            );

            String schemaSnapshotPostColumnName = "schemaPostChange";
            put.addColumn(
                    CF,
                    Bytes.toBytes(schemaSnapshotPostColumnName),
                    eventTimestamp,
                    Bytes.toBytes(postChangeSchemaJSON)
            );

            String preChangeCreateStatementsColumn = "createsPreChange";
            put.addColumn(
                    CF,
                    Bytes.toBytes(preChangeCreateStatementsColumn),
                    eventTimestamp,
                    Bytes.toBytes(preChangeSchemaCreate)
            );

            String postChangeCreateStatementsColumn = "createsPostChange";
            put.addColumn(
                    CF,
                    Bytes.toBytes(postChangeCreateStatementsColumn),
                    eventTimestamp,
                    Bytes.toBytes(postChangeSchemaCreate)
            );

            Table hbaseTable = connection.getTable(tableName);
            hbaseTable.put(put);

        } catch (IOException ioe) {
            throw new SchemaTransitionException("Failed to store schemaChangePointSnapshot in HBase.", ioe);
        }
    }
}
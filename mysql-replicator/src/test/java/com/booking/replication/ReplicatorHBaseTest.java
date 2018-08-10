package com.booking.replication;

import com.booking.replication.applier.Applier;
import com.booking.replication.applier.Partitioner;
import com.booking.replication.applier.Seeker;
import com.booking.replication.applier.kafka.KafkaApplier;
import com.booking.replication.applier.kafka.KafkaSeeker;
import com.booking.replication.augmenter.ActiveSchema;
import com.booking.replication.augmenter.Augmenter;
import com.booking.replication.augmenter.AugmenterContext;
import com.booking.replication.augmenter.model.AugmentedEvent;
import com.booking.replication.checkpoint.CheckpointApplier;
import com.booking.replication.commons.services.ServicesControl;
import com.booking.replication.commons.services.ServicesProvider;
import com.booking.replication.coordinator.Coordinator;
import com.booking.replication.coordinator.ZookeeperCoordinator;
import com.booking.replication.supplier.Supplier;
import com.booking.replication.supplier.mysql.binlog.BinaryLogSupplier;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReplicatorHBaseTest {
    private static final Logger LOG = Logger.getLogger(ReplicatorHBaseTest.class.getName());

    private static final String ZOOKEEPER_LEADERSHIP_PATH = "/replicator/leadership";
    private static final String ZOOKEEPER_CHECKPOINT_PATH = "/replicator/checkpoint";

    private static final String CHECKPOINT_DEFAULT = "{\"timestamp\": 0, \"serverId\": 1, \"gtid\": null, \"binlog\": {\"filename\": \"binlog.000001\", \"position\": 4}}";

    private static final String MYSQL_SCHEMA = "replicator";
    private static final String MYSQL_ROOT_USERNAME = "root";
    private static final String MYSQL_USERNAME = "replicator";
    private static final String MYSQL_PASSWORD = "replicator";
    private static final String MYSQL_ACTIVE_SCHEMA = "active_schema";
    private static final String MYSQL_INIT_SCRIPT = "mysql.init.sql";
    private static final int TRANSACTION_LIMIT = 5;

    private static ServicesControl zookeeper;
    private static ServicesControl mysqlBinaryLog;
    private static ServicesControl mysqlActiveSchema;
    private static ServicesControl hbase;

    @BeforeClass
    public static void before() {
        ServicesProvider servicesProvider = ServicesProvider.build(ServicesProvider.Type.CONTAINERS);

        //ReplicatorHBaseTest.zookeeper = servicesProvider.startZookeeper();
        //ReplicatorHBaseTest.mysqlBinaryLog = servicesProvider.startMySQL(ReplicatorHBaseTest.MYSQL_SCHEMA, ReplicatorHBaseTest.MYSQL_USERNAME, ReplicatorHBaseTest.MYSQL_PASSWORD, ReplicatorHBaseTest.MYSQL_INIT_SCRIPT);
        ReplicatorHBaseTest.mysqlActiveSchema = servicesProvider.startMySQL(ReplicatorHBaseTest.MYSQL_ACTIVE_SCHEMA, ReplicatorHBaseTest.MYSQL_USERNAME, ReplicatorHBaseTest.MYSQL_PASSWORD);
        ReplicatorHBaseTest.hbase = servicesProvider.startHbase();
    }

    @Test
    public void testReplicator() throws Exception {

        //Replicator replicator = new Replicator(this.getConfiguration());

        //replicator.start();

        //replicator.wait(1L, TimeUnit.MINUTES);

        readRowsFromHBase();

        //replicator.stop();
    }

    private void readRowsFromHBase() {

        try {
            // instantiate Configuration class
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "localhost");
            config.set("hbase.zookeeper.property.clientPort", "2181");
            Connection connection = ConnectionFactory.createConnection(config);
            Admin admin = connection.getAdmin();
            String status = admin.getClusterStatus().toString();

           LOG.info("cluster status => " + status);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Map<String, Object> getConfiguration() {
        Map<String, Object> configuration = new HashMap<>();

        // Coordinator Configuration
        configuration.put(Replicator.Configuration.CHECKPOINT_PATH, ReplicatorHBaseTest.ZOOKEEPER_CHECKPOINT_PATH);
        configuration.put(Replicator.Configuration.CHECKPOINT_DEFAULT, ReplicatorHBaseTest.CHECKPOINT_DEFAULT);
        configuration.put(CheckpointApplier.Configuration.TYPE, CheckpointApplier.Type.COORDINATOR.name());
        configuration.put(Coordinator.Configuration.TYPE, Coordinator.Type.ZOOKEEPER.name());
        configuration.put(ZookeeperCoordinator.Configuration.CONNECTION_STRING, ReplicatorHBaseTest.zookeeper.getURL());
        configuration.put(ZookeeperCoordinator.Configuration.LEADERSHIP_PATH, ReplicatorHBaseTest.ZOOKEEPER_LEADERSHIP_PATH);

        // Supplier Configuration
        configuration.put(Supplier.Configuration.TYPE, Supplier.Type.BINLOG.name());
        configuration.put(BinaryLogSupplier.Configuration.MYSQL_HOSTNAME, Collections.singletonList(ReplicatorHBaseTest.mysqlBinaryLog.getHost()));
        configuration.put(BinaryLogSupplier.Configuration.MYSQL_PORT, String.valueOf(ReplicatorHBaseTest.mysqlBinaryLog.getPort()));
        configuration.put(BinaryLogSupplier.Configuration.MYSQL_SCHEMA, ReplicatorHBaseTest.MYSQL_SCHEMA);
        configuration.put(BinaryLogSupplier.Configuration.MYSQL_USERNAME, ReplicatorHBaseTest.MYSQL_ROOT_USERNAME);
        configuration.put(BinaryLogSupplier.Configuration.MYSQL_PASSWORD, ReplicatorHBaseTest.MYSQL_PASSWORD);

        // Schema Manager Configuration
        configuration.put(Augmenter.Configuration.SCHEMA_TYPE, Augmenter.SchemaType.ACTIVE.name());
        configuration.put(ActiveSchema.Configuration.MYSQL_HOSTNAME, ReplicatorHBaseTest.mysqlActiveSchema.getHost());
        configuration.put(ActiveSchema.Configuration.MYSQL_PORT, String.valueOf(ReplicatorHBaseTest.mysqlActiveSchema.getPort()));
        configuration.put(ActiveSchema.Configuration.MYSQL_SCHEMA, ReplicatorHBaseTest.MYSQL_ACTIVE_SCHEMA);
        configuration.put(ActiveSchema.Configuration.MYSQL_USERNAME, ReplicatorHBaseTest.MYSQL_ROOT_USERNAME);
        configuration.put(ActiveSchema.Configuration.MYSQL_PASSWORD, ReplicatorHBaseTest.MYSQL_PASSWORD);

        configuration.put(AugmenterContext.Configuration.TRANSACTION_BUFFER_LIMIT, String.valueOf(ReplicatorHBaseTest.TRANSACTION_LIMIT));

        // Applier Configuration
        configuration.put(Seeker.Configuration.TYPE, Seeker.Type.NONE.name());
        configuration.put(Partitioner.Configuration.TYPE, Partitioner.Type.XXID.name());
        configuration.put(Applier.Configuration.TYPE, Applier.Type.HBASE.name());

        return configuration;
    }

    @AfterClass
    public static void after() {
        ReplicatorHBaseTest.hbase.close();
//        ReplicatorHBaseTest.mysqlBinaryLog.close();
        ReplicatorHBaseTest.mysqlActiveSchema.close();
//        ReplicatorHBaseTest.zookeeper.close();
    }
}

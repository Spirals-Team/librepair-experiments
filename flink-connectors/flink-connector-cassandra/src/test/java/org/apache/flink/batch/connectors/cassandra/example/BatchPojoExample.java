package org.apache.flink.batch.connectors.cassandra.example;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.batch.connectors.cassandra.CassandraPojoInputFormat;
import org.apache.flink.batch.connectors.cassandra.CassandraTupleOutputFormat;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;
import org.apache.flink.streaming.connectors.cassandra.CustomCassandraAnnotatedPojo;

import com.datastax.driver.core.Cluster;

import java.util.ArrayList;

/**
 * This is an example showing the to use the CassandraPojoInput-/CassandraOutputFormats in the Batch API.
 *
 * <p>The example assumes that a table exists in a local cassandra database, according to the following queries:
 * CREATE KEYSPACE IF NOT EXISTS test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': ‘1’};
 * CREATE TABLE IF NOT EXISTS test.batches (number int, strings text, PRIMARY KEY(number, strings));
 */
public class BatchPojoExample {
	private static final String INSERT_QUERY = "INSERT INTO test.batches (number, strings) VALUES (?,?);";
	private static final String SELECT_QUERY = "SELECT number, strings FROM test.batches;";

	/*
	 *	table script: "CREATE TABLE test.batches (number int, strings text, PRIMARY KEY(number, strings));"
	 */
	public static void main(String[] args) throws Exception {

		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		env.setParallelism(1);

		ArrayList<Tuple2<Integer, String>> collection = new ArrayList<>(20);
		for (int i = 0; i < 20; i++) {
			collection.add(new Tuple2<>(i, "string " + i));
		}

		DataSet<Tuple2<Integer, String>> dataSet = env.fromCollection(collection);

		dataSet.output(new CassandraTupleOutputFormat<Tuple2<Integer, String>>(INSERT_QUERY, new ClusterBuilder() {
			@Override
			protected Cluster buildCluster(Cluster.Builder builder) {
				return builder.addContactPoints("127.0.0.1").build();
			}
		}));

		env.execute("Write");

		/*DataSet<Tuple2<Integer, String>> inputDS = env
			.createInput(new CassandraInputFormat<Tuple2<Integer, String>>(SELECT_QUERY, new ClusterBuilder() {
				@Override
				protected Cluster buildCluster(Cluster.Builder builder) {
					return builder.addContactPoints("127.0.0.1").build();
				}
			}), TupleTypeInfo.of(new TypeHint<Tuple2<Integer, String>>() {
			}));

		inputDS.print();*/

		DataSet<CustomCassandraAnnotatedPojo> inputDS = env
			.createInput(new CassandraPojoInputFormat<>(SELECT_QUERY, new ClusterBuilder() {
				@Override
				protected Cluster buildCluster(Cluster.Builder builder) {
					return builder.addContactPoints("127.0.0.1").build();
				}
			}, CustomCassandraAnnotatedPojo.class));

		/*DataSet<String> testStep = inputDS.map(new MapFunction<CustomCassandraAnnotatedPojo, String>() {
			@Override
			public String map(CustomCassandraAnnotatedPojo value) throws Exception {
				return "numberVal: " + value.getNumber() + ", stringsVal: " + value.getStrings();
			}
		});*/

		//testStep.print();
	}
}

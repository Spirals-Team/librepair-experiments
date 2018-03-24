/*******************************************************************************
 *
 *    Copyright (C) 2015-2018 the BBoxDB project
 *  
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License. 
 *    
 *******************************************************************************/
package org.bboxdb.network;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.bboxdb.BBoxDBMain;
import org.bboxdb.commons.math.BoundingBox;
import org.bboxdb.distribution.zookeeper.TupleStoreAdapter;
import org.bboxdb.distribution.zookeeper.ZookeeperClient;
import org.bboxdb.distribution.zookeeper.ZookeeperClientFactory;
import org.bboxdb.distribution.zookeeper.ZookeeperException;
import org.bboxdb.misc.BBoxDBConfigurationManager;
import org.bboxdb.misc.BBoxDBException;
import org.bboxdb.network.client.BBoxDB;
import org.bboxdb.network.client.BBoxDBClient;
import org.bboxdb.network.client.BBoxDBConnection;
import org.bboxdb.network.client.future.EmptyResultFuture;
import org.bboxdb.network.client.future.TupleListFuture;
import org.bboxdb.network.server.ErrorMessages;
import org.bboxdb.storage.entity.DistributionGroupConfiguration;
import org.bboxdb.storage.entity.DistributionGroupConfigurationBuilder;
import org.bboxdb.storage.entity.Tuple;
import org.bboxdb.storage.entity.TupleStoreConfiguration;
import org.bboxdb.storage.entity.TupleStoreConfigurationBuilder;
import org.bboxdb.storage.entity.TupleStoreName;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

public class TestNetworkCommunication {

	/**
	 * The instance of the software
	 */
	private static BBoxDBMain bboxDBMain;
	
	/**
	 * The replication factor for the unit tests
	 */
	public final static short REPLICATION_FACTOR = 1;

	private static final String DISTRIBUTION_GROUP = "testgroup";
	
	@BeforeClass
	public static void init() throws Exception {
		bboxDBMain = new BBoxDBMain();
		bboxDBMain.init();
		bboxDBMain.start();
		
		Thread.currentThread();
		// Wait some time to let the server process start
		Thread.sleep(5000);
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		if(bboxDBMain != null) {
			bboxDBMain.stop();
			bboxDBMain = null;
		}
		
		// Wait some time for socket re-use
		Thread.sleep(5000);
	}
	
	/**
	 * Re-create distribution group for each test
	 * @throws InterruptedException
	 * @throws BBoxDBException 
	 */
	@Before
	public void before() throws InterruptedException, BBoxDBException {
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		TestHelper.recreateDistributionGroup(bboxDBClient, DISTRIBUTION_GROUP);
		disconnect(bboxDBClient);
	}
	
	/**
	 * Test create a distribution group two times
	 * @throws BBoxDBException 
	 * @throws InterruptedException 
	 */
	@Test(timeout=60000)
	public void testCreateDistributionGroupTwoTimes() throws BBoxDBException, InterruptedException {
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
				
		// Create distribution group
		final DistributionGroupConfiguration configuration = DistributionGroupConfigurationBuilder.create(2)
				.withReplicationFactor((short) 1)
				.build();
		
		final EmptyResultFuture resultCreate = bboxDBClient.createDistributionGroup(DISTRIBUTION_GROUP, 
				configuration);
		
		// Prevent retries
		bboxdbConnection.getNetworkOperationRetryer().close();
		
		resultCreate.waitForAll();
		Assert.assertTrue(resultCreate.isFailed());
		Assert.assertEquals(ErrorMessages.ERROR_DGROUP_EXISTS, resultCreate.getMessage(0));
		Assert.assertTrue(bboxdbConnection.getConnectionState().isInRunningState());
		
		disconnect(bboxDBClient);
	}
	
	/**
	 * Test create a table two times
	 * @throws BBoxDBException 
	 * @throws InterruptedException 
	 */
	@Test(timeout=60000)
	public void testCreateTableTwoTimes() throws BBoxDBException, InterruptedException {
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
				
		final String table = DISTRIBUTION_GROUP + "_mytable";
		
		final EmptyResultFuture resultCreateTable1 = bboxDBClient.createTable(table, new TupleStoreConfiguration());
		resultCreateTable1.waitForAll();
		Assert.assertFalse(resultCreateTable1.isFailed());
		
		final EmptyResultFuture resultCreateTable2 = bboxDBClient.createTable(table, new TupleStoreConfiguration());
		
		// Prevent retries
		bboxdbConnection.getNetworkOperationRetryer().close();
		
		resultCreateTable2.waitForAll();
		Assert.assertTrue(resultCreateTable2.isFailed());
		Assert.assertEquals(ErrorMessages.ERROR_TABLE_EXISTS, resultCreateTable2.getMessage(0));
		Assert.assertTrue(bboxdbConnection.getConnectionState().isInRunningState());

		disconnect(bboxDBClient);
	}
	
	/**
	 * Integration test for the disconnect package
	 * 
	 */
	@Test(timeout=60000)
	public void testSendDisconnectPackage() {
		System.out.println("=== Running testSendDisconnectPackage");

		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		Assert.assertTrue(bboxDBClient.isConnected());
		disconnect(bboxDBClient);
		Assert.assertFalse(bboxDBClient.isConnected());
		disconnect(bboxDBClient);
		
		System.out.println("=== End testSendDisconnectPackage");
	}
	
	/**
	 * Test the double connect call
	 */
	@Test(timeout=60000)
	public void testDoubleConnect() {
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		Assert.assertTrue(bboxDBClient.isConnected());
		Assert.assertTrue(bboxDBClient.connect());
		Assert.assertTrue(bboxDBClient.isConnected());
		disconnect(bboxDBClient);
	}
	
	/**
	 * Test the double disconnect call
	 */
	@Test(timeout=60000)
	public void testDoubleDisconnect() {
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		disconnect(bboxDBClient);
		disconnect(bboxDBClient);
	}
	
	/**
	 * Send a delete package to the server
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	 * @throws ZookeeperException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testSendDeletePackage() throws InterruptedException, 
	ExecutionException, ZookeeperException, BBoxDBException {
		
		System.out.println("=== Running sendDeletePackage");

		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
				
		final String tableName = DISTRIBUTION_GROUP + "_relation3";
		final TupleStoreName tupleStoreName = new TupleStoreName(tableName);

		final ZookeeperClient zookeeperClient = ZookeeperClientFactory.getZookeeperClient();
		final TupleStoreAdapter tupleStoreAdapter = zookeeperClient.getTupleStoreAdapter();
		Assert.assertFalse(tupleStoreAdapter.isTableKnown(tupleStoreName));
		
		// Create table
		final TupleStoreConfiguration configuration = TupleStoreConfigurationBuilder.create().build();
		final EmptyResultFuture createFuture = bboxDBClient.createTable(tableName, configuration);
		createFuture.waitForAll();
		Assert.assertTrue(createFuture.isDone());
		Assert.assertFalse(createFuture.isFailed());
		Assert.assertTrue(tupleStoreAdapter.isTableKnown(tupleStoreName));
		
		// Delete table
		final EmptyResultFuture deleteResult1 = bboxDBClient.deleteTable(tableName);
		deleteResult1.waitForAll();
		Assert.assertTrue(deleteResult1.isDone());
		Assert.assertFalse(deleteResult1.isFailed());
		Assert.assertTrue(bboxdbConnection.getConnectionState().isInRunningState());
		Assert.assertFalse(tupleStoreAdapter.isTableKnown(tupleStoreName));
		
		// Second call
		final EmptyResultFuture deleteResult2 = bboxDBClient.deleteTable(tableName);
		deleteResult2.waitForAll();
		Assert.assertTrue(deleteResult2.isDone());
		Assert.assertFalse(deleteResult2.isFailed());
		Assert.assertTrue(bboxdbConnection.getConnectionState().isInRunningState());
		Assert.assertFalse(tupleStoreAdapter.isTableKnown(tupleStoreName));
		
		// Disconnect
		disconnect(bboxDBClient);
		Assert.assertFalse(bboxDBClient.isConnected());

		System.out.println("=== End sendDeletePackage");
	}
	
	/**
	 * Test the state machine of the connection
	 */
	@Test(timeout=60000)
	public void testConnectionState() {
		System.out.println("=== Running testConnectionState");

		final int port = BBoxDBConfigurationManager.getConfiguration().getNetworkListenPort();
		final BBoxDBConnection bboxDBClient = new BBoxDBConnection(new InetSocketAddress("127.0.0.1", port));
		Assert.assertTrue(bboxDBClient.getConnectionState().isInNewState());
		bboxDBClient.connect();
		Assert.assertTrue(bboxDBClient.getConnectionState().isInRunningState());
		bboxDBClient.disconnect();
		Assert.assertTrue(bboxDBClient.getConnectionState().isInTerminatedState());
		
		bboxDBClient.closeSocket();

		System.out.println("=== End testConnectionState");
	}

	/**
	 * The the insert and the deletion of a tuple
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testInsertAndDelete() throws InterruptedException, ExecutionException, BBoxDBException {
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		NetworkQueryHelper.testInsertAndDeleteTuple(bboxDBClient, DISTRIBUTION_GROUP);
		disconnect(bboxDBClient);
	}
	
	/**
	 * Test insert into non existing table
	 * @throws BBoxDBException 
	 * @throws InterruptedException 
	 */
	@Test(timeout=60000)
	public void testInsertIntoNonExstingTable() throws BBoxDBException, InterruptedException {
		System.out.println("=== Running testInsertIntoNonExstingTable");
		
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();

		final String table = DISTRIBUTION_GROUP + "_relationnonexsting";
		final String key = "key12";
		
		System.out.println("Insert tuple");
		final Tuple tuple = new Tuple(key, BoundingBox.FULL_SPACE, "abc".getBytes());
		final EmptyResultFuture insertResult = bboxDBClient.insertTuple(table, tuple);
		
		// Prevent retries
		bboxdbConnection.getNetworkOperationRetryer().close();
		
		insertResult.waitForAll();
		Assert.assertTrue(insertResult.isFailed());
		Assert.assertTrue(insertResult.isDone());
		
		System.out.println(insertResult.getMessage(0));

		bboxDBClient.disconnect();
		
		System.out.println("=== End testInsertIntoNonExstingTable");
	}
	
	/**
	 * Insert some tuples and start a bounding box query afterwards
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testInsertAndBoundingBoxQuery() throws InterruptedException, 
		ExecutionException, BBoxDBException {
		
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		NetworkQueryHelper.testBoundingBoxQuery(bboxDBClient, DISTRIBUTION_GROUP, true);
		disconnect(bboxDBClient);
	}
	
	/**
	 * Start a bounding box query without inserted tuples
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testEmptyBoundingBoxQuery() throws InterruptedException, 
		ExecutionException, BBoxDBException {
		
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		NetworkQueryHelper.testBoundingBoxQuery(bboxDBClient, DISTRIBUTION_GROUP, false);
		disconnect(bboxDBClient);
	}
	
	/**
	 * Start a bounding box query without inserted tuples
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testQueriesWithoutTables() throws InterruptedException, 
		ExecutionException, BBoxDBException {
		
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		bboxdbConnection.getNetworkOperationRetryer().close();

		final String table = DISTRIBUTION_GROUP + "_nonexisting";

		System.out.println("== Waiting for queryBoundingBox");
		final BoundingBox boundingBox = new BoundingBox(-1d, 2d, -1d, 2d);
		final TupleListFuture result1 = bboxDBClient.queryBoundingBox(table, boundingBox);
		result1.waitForAll();
		Assert.assertTrue(result1.isFailed());
		Assert.assertEquals(ErrorMessages.ERROR_TABLE_NOT_EXIST, result1.getMessage(0));
		
		System.out.println("== Waiting for queryKey");
		final TupleListFuture result2 = bboxDBClient.queryKey(table, "abc");
		result2.waitForAll();
		Assert.assertTrue(result2.isFailed());
		Assert.assertEquals(ErrorMessages.ERROR_TABLE_NOT_EXIST, result2.getMessage(0));
		
		System.out.println("== Waiting for queryInsertedTime");
		final TupleListFuture result3 = bboxDBClient.queryInsertedTime(table, 1234);
		result3.waitForAll();
		Assert.assertTrue(result3.isFailed());
		Assert.assertEquals(ErrorMessages.ERROR_TABLE_NOT_EXIST, result3.getMessage(0));
		
		System.out.println("== Waiting for queryVersionTime");
		final TupleListFuture result4 = bboxDBClient.queryVersionTime(table, 1234);
		result4.waitForAll();
		Assert.assertTrue(result4.isFailed());
		Assert.assertEquals(ErrorMessages.ERROR_TABLE_NOT_EXIST, result4.getMessage(0));
		
		System.out.println("== Waiting for queryBoundingBoxAndTime");
		final TupleListFuture result5 = bboxDBClient.queryBoundingBoxAndTime(table, boundingBox, 1234);
		result5.waitForAll();
		Assert.assertTrue(result5.isFailed());
		Assert.assertEquals(ErrorMessages.ERROR_TABLE_NOT_EXIST, result5.getMessage(0));
		
		disconnect(bboxDBClient);
	}
	
	/**
	 * Insert some tuples and start a bounding box query afterwards
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testInsertAndBoundingBoxContinousQuery() throws InterruptedException, ExecutionException, BBoxDBException {
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		NetworkQueryHelper.testBoundingBoxQueryContinous(bboxDBClient, DISTRIBUTION_GROUP);
		disconnect(bboxDBClient);
	}
	
	/**
	 * Execute the version time query
	 * @throws BBoxDBException 
	 * @throws InterruptedException 
	 */
	@Test(timeout=60000)
	public void testVersionTimeQuery() throws InterruptedException, BBoxDBException {
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		NetworkQueryHelper.testVersionTimeQuery(bboxDBClient, DISTRIBUTION_GROUP);
		disconnect(bboxDBClient);
	}
	
	/**
	 * Execute the version inserted query
	 * @throws BBoxDBException 
	 * @throws InterruptedException 
	 */
	@Test(timeout=60000)
	public void testInsertedTimeQuery() throws InterruptedException, BBoxDBException {
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		NetworkQueryHelper.testInsertedTimeQuery(bboxDBClient, DISTRIBUTION_GROUP);
		disconnect(bboxDBClient);
	}
	
	/**
	 * Test the tuple join
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testJoin() throws InterruptedException, ExecutionException, BBoxDBException {
		System.out.println("=== Running network testJoin");

		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		NetworkQueryHelper.executeJoinQuery(bboxDBClient, DISTRIBUTION_GROUP);
		
		System.out.println("=== End network testJoin");
		disconnect(bboxDBClient);
	}
	
	/**
	 * Insert some tuples and request it via paging
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testPaging() throws InterruptedException, ExecutionException, BBoxDBException {
		System.out.println("=== Running testPaging");
		final String table = DISTRIBUTION_GROUP + "_relation9999";
		
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
				
		// Create table
		final EmptyResultFuture resultCreateTable = bboxDBClient.createTable(table, new TupleStoreConfiguration());
		resultCreateTable.waitForAll();
		Assert.assertFalse(resultCreateTable.isFailed());
		
		// Inside our bbox query
		final Tuple tuple1 = new Tuple("abc", new BoundingBox(0d, 1d, 0d, 1d), "abc".getBytes());
		final EmptyResultFuture result1 = bboxDBClient.insertTuple(table, tuple1);
		
		final Tuple tuple2 = new Tuple("def", new BoundingBox(0d, 0.5d, 0d, 0.5d), "def".getBytes());
		final EmptyResultFuture result2 = bboxDBClient.insertTuple(table, tuple2);
		
		final Tuple tuple3 = new Tuple("geh", new BoundingBox(0.5d, 1.5d, 0.5d, 1.5d), "geh".getBytes());
		final EmptyResultFuture result3 = bboxDBClient.insertTuple(table, tuple3);		
		
		final Tuple tuple4 = new Tuple("ijk", new BoundingBox(-10d, -9d, -10d, -9d), "ijk".getBytes());
		final EmptyResultFuture result4 = bboxDBClient.insertTuple(table, tuple4);
		
		final Tuple tuple5 = new Tuple("lmn", new BoundingBox(1d, 2d, 1d, 2d), "lmn".getBytes());
		final EmptyResultFuture result5 = bboxDBClient.insertTuple(table, tuple5);
		
		result1.waitForAll();
		result2.waitForAll();
		result3.waitForAll();
		result4.waitForAll();
		result5.waitForAll();

		// Without paging
		System.out.println("Pages = unlimited");
		bboxDBClient.setPagingEnabled(false);
		bboxDBClient.setTuplesPerPage((short) 0);
		final TupleListFuture future = bboxDBClient.queryBoundingBox(table, new BoundingBox(-10d, 10d, -10d, 10d));
		future.waitForAll();
		final List<Tuple> resultList = Lists.newArrayList(future.iterator());		
		Assert.assertEquals(5, resultList.size());
		
		// With paging (tuples per page 10)
		System.out.println("Pages = 10");
		bboxDBClient.setPagingEnabled(true);
		bboxDBClient.setTuplesPerPage((short) 10);
		final TupleListFuture future2 = bboxDBClient.queryBoundingBox(table, new BoundingBox(-10d, 10d, -10d, 10d));
		future2.waitForAll();
		final List<Tuple> resultList2 = Lists.newArrayList(future2.iterator());		
		Assert.assertEquals(5, resultList2.size());
		
		// With paging (tuples per page 5)
		System.out.println("Pages = 5");
		bboxDBClient.setPagingEnabled(true);
		bboxDBClient.setTuplesPerPage((short) 5);
		final TupleListFuture future3 = bboxDBClient.queryBoundingBox(table, new BoundingBox(-10d, 10d, -10d, 10d));
		future3.waitForAll();
		final List<Tuple> resultList3 = Lists.newArrayList(future3.iterator());		
		Assert.assertEquals(5, resultList3.size());
		
		// With paging (tuples per page 2)
		System.out.println("Pages = 2");
		bboxDBClient.setPagingEnabled(true);
		bboxDBClient.setTuplesPerPage((short) 2);
		final TupleListFuture future4 = bboxDBClient.queryBoundingBox(table, new BoundingBox(-10d, 10d, -10d, 10d));
		System.out.println("Client is waiting on: " + future4);
		future4.waitForAll();
		final List<Tuple> resultList4 = Lists.newArrayList(future4.iterator());		
		Assert.assertEquals(5, resultList4.size());
		
		// With paging (tuples per page 1)
		System.out.println("Pages = 1");
		bboxDBClient.setPagingEnabled(true);
		bboxDBClient.setTuplesPerPage((short) 1);
		final TupleListFuture future5 = bboxDBClient.queryBoundingBox(table, new BoundingBox(-10d, 10d, -10d, 10d));
		future5.waitForAll();
		final List<Tuple> resultList5 = Lists.newArrayList(future5.iterator());		
		Assert.assertEquals(5, resultList5.size());
		
		System.out.println("=== End testPaging");
		disconnect(bboxDBClient);
	}
	
	/**
	 * Insert a tuple and request it via key
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testGetByKey() throws InterruptedException, ExecutionException, BBoxDBException {
		System.out.println("=== Running testGetByKey");
		final String table = DISTRIBUTION_GROUP + "_relation12333";
		
		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
				
		// Create table
		final EmptyResultFuture resultCreateTable = bboxDBClient.createTable(table, new TupleStoreConfiguration());
		resultCreateTable.waitForAll();
		Assert.assertFalse(resultCreateTable.isFailed());
		
		// Inside our bbox query
		final Tuple tuple1 = new Tuple("abc", new BoundingBox(0d, 1d, 0d, 1d), "abc".getBytes());
		final EmptyResultFuture result1 = bboxDBClient.insertTuple(table, tuple1);
		
		result1.waitForAll();

		final TupleListFuture future = bboxDBClient.queryKey(table, "abc");
		future.waitForAll();
		
		final List<Tuple> resultList = Lists.newArrayList(future.iterator());		
		Assert.assertEquals(1, resultList.size());
	
		System.out.println("=== End testGetByKey");
		disconnect(bboxDBClient);
	}
	
	/**
	 * Insert some tuples and start a bounding box query afterwards
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws BBoxDBException 
	 */
	@Test(timeout=60000)
	public void testInsertAndBoundingBoxTimeQuery() throws InterruptedException, ExecutionException, BBoxDBException {
		System.out.println("=== Running testInsertAndBoundingBoxTimeQuery");

		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		NetworkQueryHelper.executeBoudingboxAndTimeQuery(bboxDBClient, DISTRIBUTION_GROUP);

		System.out.println("=== End testInsertAndBoundingBoxTimeQuery");
		disconnect(bboxDBClient);
	}
	
	/**
	 * Send a keep alive package to the server
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	 */
	@Test(timeout=60000)
	public void testSendKeepAlivePackage() throws InterruptedException, ExecutionException {
		System.out.println("=== Running sendKeepAlivePackage");

		final BBoxDBConnection bboxdbConnection = connectToServer();
		final BBoxDBClient bboxDBClient = bboxdbConnection.getBboxDBClient();
		
		final EmptyResultFuture result = bboxDBClient.sendKeepAlivePackage();
		result.waitForAll();
		
		Assert.assertTrue(result.isDone());
		Assert.assertFalse(result.isFailed());
		Assert.assertTrue(bboxdbConnection.getConnectionState().isInRunningState());
		
		disconnect(bboxDBClient);
		Assert.assertFalse(bboxDBClient.isConnected());
		
		System.out.println("=== End sendKeepAlivePackage");
		disconnect(bboxDBClient);
	}
	
	/**
	 * Build a new connection to the bboxdb server
	 * 
	 * @return
	 */
	protected BBoxDBConnection connectToServer() {
		final int port = BBoxDBConfigurationManager.getConfiguration().getNetworkListenPort();
		final BBoxDBConnection bboxDBClient = new BBoxDBConnection(new InetSocketAddress("127.0.0.1", port));
		
		if(compressPackages()) {
			bboxDBClient.getClientCapabilities().setGZipCompression();
			Assert.assertTrue(bboxDBClient.getClientCapabilities().hasGZipCompression());
		} else {
			bboxDBClient.getClientCapabilities().clearGZipCompression();
			Assert.assertFalse(bboxDBClient.getClientCapabilities().hasGZipCompression());
		}
		
		Assert.assertFalse(bboxDBClient.isConnected());
		boolean result = bboxDBClient.connect();
		Assert.assertTrue(result);
		Assert.assertTrue(bboxDBClient.isConnected());
		
		if(compressPackages()) { 
			Assert.assertTrue(bboxDBClient.getConnectionCapabilities().hasGZipCompression());
		} else {
			Assert.assertFalse(bboxDBClient.getConnectionCapabilities().hasGZipCompression());
		}
		
		return bboxDBClient;
	}
	
	/**
	 * Test misc methods
	 */
	@Test(timeout=60000)
	public void testMiscMethods() {
		final BBoxDBClient bboxDBClient = connectToServer().getBboxDBClient();
		Assert.assertTrue(bboxDBClient.toString().length() > 10);
		Assert.assertTrue(bboxDBClient.getTuplesPerPage() >= -1);
		bboxDBClient.isPagingEnabled();
		disconnect(bboxDBClient);
	}
	
	/**
	 * Should the packages be compressed or not
	 * @return
	 */
	protected boolean compressPackages() {
		return false;
	}
	
	/**
	 * Disconnect from server
	 * @param bboxDBConnection
	 */
	protected void disconnect(final BBoxDB bboxDBClient) {
		bboxDBClient.disconnect();
		Assert.assertFalse(bboxDBClient.isConnected());
	}
}

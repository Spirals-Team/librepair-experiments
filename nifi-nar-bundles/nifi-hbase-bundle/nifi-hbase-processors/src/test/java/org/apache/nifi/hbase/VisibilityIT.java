/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.security.SecurityCapability;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.security.access.SecureTestUtil;
import org.apache.hadoop.hbase.security.visibility.CellVisibility;
import org.apache.hadoop.hbase.security.visibility.VisibilityClient;
import org.apache.nifi.serialization.SimpleRecordSchema;
import org.apache.nifi.serialization.record.MapRecord;
import org.apache.nifi.serialization.record.MockRecordParser;
import org.apache.nifi.serialization.record.RecordField;
import org.apache.nifi.serialization.record.RecordFieldType;
import org.apache.nifi.serialization.record.RecordSchema;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.apache.hadoop.hbase.security.visibility.VisibilityConstants.LABELS_TABLE_NAME;

public class VisibilityIT {
    private static HBaseTestingUtility UTILITY;

    private static final String[] FULL_AUTHS = new String[] { "PII", "PHI", "UNRESTRICTED" };
    private static final String[] PII_AUTHS  = new String[] { "PII", "UNRESTRICTED" };
    private static final String[] PHI_AUTHS  = new String[] { "PHI", "UNRESTRICTED" };

    private static final String USER_ID = "john.smith";
    private static final String TABLE_NAME = "label_test_table";
    private static final String FAM = "prop";
    private static final String ROW  = "test-row-id";
    private static final String DEFAULT_VISIBILITY = "PII|UNRESTRICTED";
    private static final String CLIENT_VAL = "hbaseClient";

    private static final Map<String, String> ATTRS;

    private static User LOCAL_USER;

    static {
        ATTRS = new HashMap<>();
        ATTRS.put("row_id", ROW);
    }

    private TestRunner GET_RUNNER;
    private static HBaseClientService clientService;

    private static Configuration conf;

    @BeforeClass
    public static void setup() throws Throwable {


        UTILITY = new HBaseTestingUtility();
        conf = UTILITY.getConfiguration();
        SecureTestUtil.enableSecurity(conf);
        conf.set("hbase.coprocessor.region.classes", "org.apache.hadoop.hbase.security.visibility.VisibilityController");
        conf.set("hbase.coprocessor.master.classes", "org.apache.hadoop.hbase.security.visibility.VisibilityController");
        UTILITY.startMiniCluster();

        UTILITY.waitTableEnabled(LABELS_TABLE_NAME.getName(), 50000);

        List<SecurityCapability> capabilities = UTILITY.getConnection().getAdmin()
                .getSecurityCapabilities();
        Assert.assertTrue("CELL_VISIBILITY capability is missing",
                capabilities.contains(SecurityCapability.CELL_VISIBILITY));

        User SUPERUSER = User.createUserForTesting(conf, "admin", new String[] { "supergroup" });
        LOCAL_USER = User.createUserForTesting(conf, USER_ID, new String[]{});

        SUPERUSER.runAs((PrivilegedExceptionAction<Void>) () -> {
            try {
                VisibilityClient.addLabels(UTILITY.getConnection(), FULL_AUTHS);
                VisibilityClient.setAuths(UTILITY.getConnection(), FULL_AUTHS, USER_ID);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException(throwable);
            }

            return null;
        });


        LOCAL_USER.runAs((PrivilegedExceptionAction<Void>) () -> {
            clientService = new IntegrationTestClientService(ConnectionFactory.createConnection(conf));
            return null;
        });


        UTILITY.createTable(TABLE_NAME.getBytes(), FAM.getBytes());
    }

    @Before
    public void beforeEachTest() throws Exception {
        GET_RUNNER = TestRunners.newTestRunner(FetchHBaseRow.class);
        GET_RUNNER.addControllerService(CLIENT_VAL, clientService);
        GET_RUNNER.setProperty(FetchHBaseRow.HBASE_CLIENT_SERVICE, CLIENT_VAL);
        GET_RUNNER.setProperty(FetchHBaseRow.TABLE_NAME, TABLE_NAME);
        GET_RUNNER.setProperty(FetchHBaseRow.COLUMNS, "prop");
        GET_RUNNER.setProperty(FetchHBaseRow.ROW_ID, "${row_id}");
        GET_RUNNER.setProperty(FetchHBaseRow.AUTHORIZATIONS, String.join(", ", FULL_AUTHS));
        GET_RUNNER.enableControllerService(clientService);
        GET_RUNNER.assertValid();
    }

    private void testFetch(int success, int failure, int notFound, String[] limitedPerms) throws Exception {
        testFetch(success, failure, notFound, limitedPerms, ATTRS);
    }

    private void testFetch(int success, int failure, int notFound, String[] limitedPerms, Map<String, String> attrs) throws Exception {
        GET_RUNNER.setProperty(FetchHBaseRow.AUTHORIZATIONS, String.join(", ", limitedPerms));
        GET_RUNNER.enqueue("msg", attrs);
        GET_RUNNER.run();

        GET_RUNNER.assertTransferCount(FetchHBaseRow.REL_FAILURE, failure);
        GET_RUNNER.assertTransferCount(FetchHBaseRow.REL_NOT_FOUND, notFound);
        GET_RUNNER.assertTransferCount(FetchHBaseRow.REL_SUCCESS, success);

        GET_RUNNER.clearTransferState();
    }

    @Test
    public void testPutCell() throws Exception {
        final String QUAL = "name";
        final String MSG  = "Hello, world";

        TestRunner runner = TestRunners.newTestRunner(PutHBaseCell.class);
        runner.addControllerService(CLIENT_VAL, clientService);
        runner.setProperty(PutHBaseCell.DEFAULT_VISIBILITY_STRING, DEFAULT_VISIBILITY);
        runner.setProperty(PutHBaseCell.TABLE_NAME, TABLE_NAME);
        runner.setProperty(PutHBaseCell.COLUMN_FAMILY, FAM);
        runner.setProperty(PutHBaseCell.COLUMN_QUALIFIER, QUAL);
        runner.setProperty(PutHBaseCell.HBASE_CLIENT_SERVICE, CLIENT_VAL);
        runner.setProperty(PutHBaseCell.ROW_ID, "${row_id}");
        runner.enableControllerService(clientService);
        runner.setValidateExpressionUsage(true);
        runner.assertValid();

        runner.enqueue(MSG, ATTRS);
        runner.run();

        runner.assertTransferCount(PutHBaseCell.REL_SUCCESS, 1);

        testFetch(1, 0, 0, PHI_AUTHS);

        runner.clearTransferState();

        Map<String, String> attrs = new HashMap<>();
        attrs.put("row_id", "put-cell-second");
        attrs.put(String.format("visibility.%s.%s", FAM, QUAL), "PHI");
        runner.enqueue(MSG, attrs);
        runner.run();

        runner.assertTransferCount(PutHBaseCell.REL_SUCCESS, 1);

        testFetch(1, 0, 0, new String[]{ "PHI" }, attrs);
        testFetch(0, 0, 1, new String[] { "PII", }, attrs);
    }

    @Test
    public void testPutJson() throws Exception {
        final String JSON = "{ \"name\": \"John Smith\", \"email\": \"john.smith@gmail.com\" }";
        TestRunner runner = TestRunners.newTestRunner(PutHBaseJSON.class);
        runner.addControllerService(CLIENT_VAL,clientService);
        runner.setProperty(PutHBaseJSON.HBASE_CLIENT_SERVICE,CLIENT_VAL);
        runner.setProperty(PutHBaseJSON.TABLE_NAME,TABLE_NAME);
        runner.setProperty(PutHBaseJSON.COLUMN_FAMILY,FAM);
        runner.setProperty(PutHBaseJSON.ROW_ID,"${row_id}");
        runner.setProperty(PutHBaseJSON.DEFAULT_VISIBILITY_STRING,DEFAULT_VISIBILITY);
        runner.enableControllerService(clientService);
        runner.setValidateExpressionUsage(true);
        runner.assertValid();

        runner.enqueue(JSON,ATTRS);
        runner.run();
        runner.assertTransferCount(PutHBaseJSON.REL_SUCCESS,1);

        testFetch(1,0,0, PHI_AUTHS);

        testFetch(0,0,1, new String[] { "PHI" });

        runner.clearTransferState();

        Map<String, String> attrs = new HashMap<>();
        attrs.put("row_id","test-row-2");
        attrs.put(String.format("visibility.%s.name",FAM),"UNRESTRICTED");
        attrs.put(String.format("visibility.%s.email",FAM),"PII");
        runner.enqueue(JSON,attrs);
        runner.run();
        runner.assertTransferCount(PutHBaseJSON.REL_FAILURE,0);
        runner.assertTransferCount(PutHBaseJSON.REL_SUCCESS,1);

        testFetch(1,0,0,PII_AUTHS, attrs);
    }

    private Put buildData(String row, String qual, String val, String visibility) {
        return new Put(row.getBytes())
                .addColumn(FAM.getBytes(), qual.getBytes(), val.getBytes())
                .setCellVisibility(new CellVisibility(visibility));
    }

    private void writeData(List<Put> data) throws Exception {
        Table table = UTILITY.getConnection().getTable(TableName.valueOf(TABLE_NAME));
        table.put(data);
        table.close();
    }

    @Test
    public void testGetHBase() throws Throwable {
        final String newRow = "just-a-hunch";
        List<Put> puts = new ArrayList<>();
        puts.add(buildData(newRow, "name", "John Smith", DEFAULT_VISIBILITY));
        writeData(puts);


        Map<String[], Integer> runs = new HashMap<>();
        runs.put(FULL_AUTHS, 1);
        runs.put(PII_AUTHS, 1);
        runs.put(PHI_AUTHS, 1);
        runs.put(new String[]{ "PHI" }, 0);

        for (Map.Entry<String[], Integer> entry : runs.entrySet()) {
            TestRunner runner = TestRunners.newTestRunner(GetHBase.class);
            runner.addControllerService(CLIENT_VAL, clientService);
            runner.setProperty(GetHBase.HBASE_CLIENT_SERVICE, CLIENT_VAL);
            runner.setProperty(GetHBase.TABLE_NAME, TABLE_NAME);
            runner.setProperty(GetHBase.AUTHORIZATIONS, String.join(", ", entry.getKey()));
            runner.enableControllerService(clientService);
            runner.setValidateExpressionUsage(true);
            runner.assertValid();

            runner.run(1, true, true);
            runner.assertTransferCount(GetHBase.REL_SUCCESS, entry.getValue());
        }
    }

    @Test
    public void testDeleteHBaseRow() throws Exception {
        List<Put> puts = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (int x = 0; x < 5; x++) {
            String id = String.format("delete-row-%d", x);
            puts.add(buildData(id, "name", "John Smith", DEFAULT_VISIBILITY));
            ids.add(id);
        }
        writeData(puts);

        TestRunner runner = TestRunners.newTestRunner(DeleteHBaseRow.class);
        runner.addControllerService(CLIENT_VAL, clientService);
        runner.enableControllerService(clientService);
        runner.setProperty(DeleteHBaseRow.HBASE_CLIENT_SERVICE, CLIENT_VAL);
        runner.setProperty(DeleteHBaseRow.TABLE_NAME, TABLE_NAME);
        runner.setProperty(DeleteHBaseRow.VISIBLITY_LABEL, "${visibility_label}");
        runner.setValidateExpressionUsage(true);
        runner.assertValid();

        Map<String, String> attrs = new HashMap<>();
        attrs.put("visibility_label", DEFAULT_VISIBILITY);
        runner.enqueue(String.join(",", ids), attrs);
        runner.run();
        runner.assertAllFlowFilesTransferred(DeleteHBaseRow.REL_SUCCESS);

        for (int x = 0; x < ids.size(); x++) {
            final int index = x;
            testFetch(0, 0, 1, FULL_AUTHS, new HashMap<String, String>(){{
                put("row_id", ids.get(index));
            }});
        }

        runner.clearTransferState();

        writeData(puts);
        attrs.put("visibility_label", "PHI");
        runner.enqueue(String.join(",", ids), attrs);
        runner.run();
        runner.assertAllFlowFilesTransferred(DeleteHBaseRow.REL_SUCCESS);
        for (int x = 0; x < ids.size(); x++) {
            final int index = x;
            testFetch(1, 0, 0, FULL_AUTHS, new HashMap<String, String>(){{
                put("row_id", ids.get(index));
            }});
        }
    }

    @Test
    public void testDeleteHBaseCells() throws Exception {
        int limit = 10;
        String sep = "::::";
        List<String> deletes = new ArrayList<>();
        String[] props = new String[] { "msg1", "msg2", "msg3" };
        for (int index = 0; index < props.length; index++) {
            String prop = props[index];
            List<Put> puts = new ArrayList<>();
            for (int index2 = 0; index2 < limit; index2++) {
                String rowId = String.format("delete-cell-%d", index);
                Put put = new Put(rowId.getBytes());
                put.addColumn(FAM.getBytes(), prop.getBytes(), UUID.randomUUID().toString().getBytes());
                put.setCellVisibility(new CellVisibility(DEFAULT_VISIBILITY));
                String delete = String.format("%s%s%s%s%s%s%s", rowId, sep, FAM, sep, prop, sep, DEFAULT_VISIBILITY);
                deletes.add(delete);
                puts.add(put);
            }
            writeData(puts);
        }

        TestRunner runner = TestRunners.newTestRunner(DeleteHBaseCells.class);
        runner.addControllerService(CLIENT_VAL, clientService);
        runner.enableControllerService(clientService);
        runner.setProperty(DeleteHBaseCells.HBASE_CLIENT_SERVICE, CLIENT_VAL);
        runner.setProperty(DeleteHBaseCells.TABLE_NAME, TABLE_NAME);
        runner.setProperty(DeleteHBaseCells.SEPARATOR, sep);
        runner.assertValid();

        runner.enqueue(String.join("\n", deletes).trim());
        runner.run();
        runner.assertAllFlowFilesTransferred(DeleteHBaseCells.REL_SUCCESS);
    }

    @Test
    public void testPutHBaseRecordWithVisibilityMap() throws Exception {
        MockRecordParser reader = new MockRecordParser();
        TestRunner runner = TestRunners.newTestRunner(PutHBaseRecord.class);
        runner.addControllerService("reader", reader);
        runner.addControllerService(CLIENT_VAL, clientService);
        runner.enableControllerService(reader);
        runner.enableControllerService(clientService);
        runner.setProperty(PutHBaseRecord.TABLE_NAME, TABLE_NAME);
        runner.setProperty(PutHBaseRecord.HBASE_CLIENT_SERVICE, CLIENT_VAL);
        runner.setProperty(PutHBaseRecord.RECORD_READER_FACTORY, "reader");
        runner.setProperty(PutHBaseRecord.COLUMN_FAMILY, FAM);
        runner.setProperty(PutHBaseRecord.ROW_FIELD_NAME, "id");
        runner.setProperty(PutHBaseRecord.VISIBILITY_RECORD_PATH, "/visibility");
        runner.setProperty(PutHBaseRecord.DEFAULT_VISIBILITY_STRING, DEFAULT_VISIBILITY);

        reader.addSchemaField("id", RecordFieldType.INT);
        final List<RecordField> personFields = new ArrayList<>();
        final RecordField nameField = new RecordField("name", RecordFieldType.STRING.getDataType());
        final RecordField ageField = new RecordField("age", RecordFieldType.INT.getDataType());
        final RecordField sportField = new RecordField("sport", RecordFieldType.STRING.getDataType());
        personFields.add(nameField);
        personFields.add(ageField);
        personFields.add(sportField);
        final Map<String, String> visibilities = new HashMap<>();
        visibilities.put("id", "UNRESTRICTED");
        visibilities.put("person", DEFAULT_VISIBILITY);
        final RecordSchema personSchema = new SimpleRecordSchema(personFields);
        reader.addSchemaField("person", RecordFieldType.RECORD);
        reader.addSchemaField("visibility", RecordFieldType.MAP);

            reader.addRecord(1, new MapRecord(personSchema, new HashMap<String,Object>() {{
                put("name", "John Doe");
                put("age", 48);
                put("sport", "Soccer");
            }}), visibilities);
            reader.addRecord(2, new MapRecord(personSchema, new HashMap<String,Object>() {{
                put("name", "Jane Doe");
                put("age", 47);
                put("sport", "Tennis");

            }}), visibilities);
            reader.addRecord(3, new MapRecord(personSchema, new HashMap<String,Object>() {{
                put("name", "Sally Doe");
                put("age", 47);
                put("sport", "Curling");
            }}), visibilities);
            reader.addRecord(4, new MapRecord(personSchema, new HashMap<String,Object>() {{
                put("name", "Jimmy Doe");
                put("age", 14);
                put("sport", null);
            }}), visibilities);

            reader.addRecord(5, new MapRecord(personSchema, new HashMap<String,Object>() {{
                put("name", "John Smith");
                put("age", 65);
                put("sport", "Baseball");
            }}), null);
            reader.addRecord(6, new MapRecord(personSchema, new HashMap<String,Object>() {{
                put("name", "John Brown");
                put("age", 21);
                put("sport", "Basket Ball");
            }}), null);

            runner.assertValid();

            runner.enqueue("");
            runner.run();

            runner.assertAllFlowFilesTransferred(PutHBaseRecord.REL_SUCCESS);

        for (int rowId = 1; rowId <= 6; rowId++) {
            final int index = rowId;
            testFetch(1, 0, 0, FULL_AUTHS, new HashMap<String, String>() {{
                put("row_id", String.valueOf(index));
            }});

            testFetch(0, 0, 1, new String[] { "PHI" }, new HashMap<String, String>() {{
                put("row_id", String.valueOf(index));
            }});
        }
    }

    @AfterClass
    public static void teardown() throws Exception {
        UTILITY.shutdownMiniCluster();
    }
}

/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.rdb.integrate.tbl.pstatement;

import com.dangdang.ddframe.rdb.integrate.tbl.AbstractShardingTablesOnlyDBUnitTest;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.ShardingDataSource;
import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static com.dangdang.ddframe.rdb.integrate.util.SqlPlaceholderUtil.replacePreparedStatement;
import static com.dangdang.ddframe.rdb.sharding.constant.DatabaseType.Oracle;
import static com.dangdang.ddframe.rdb.sharding.constant.DatabaseType.PostgreSQL;
import static com.dangdang.ddframe.rdb.sharding.constant.DatabaseType.SQLServer;

public final class ShardingTablesOnlyForPreparedStatementWithSelectTest extends AbstractShardingTablesOnlyDBUnitTest {
    
    private static final String TABLE_ONLY_PREFIX = "integrate/dataset/tbl";
    
    private ShardingDataSource shardingDataSource;
    
    
    @Before
    public void init() throws SQLException {
        shardingDataSource = getShardingDataSource();
    }
    
    @Test
    public void assertSelectEqualsWithSingleTable() throws SQLException, DatabaseUnitException {
        assertDataSet(TABLE_ONLY_PREFIX + "/expect/select/SelectEqualsWithSingleTable_0.xml", shardingDataSource.getConnection(), 
                "t_order", replacePreparedStatement(getDatabaseTestSQL().getSelectEqualsWithSingleTableSql()), 10, 1000);
        assertDataSet(TABLE_ONLY_PREFIX + "/expect/select/SelectEqualsWithSingleTable_1.xml", shardingDataSource.getConnection(), 
                "t_order", replacePreparedStatement(getDatabaseTestSQL().getSelectEqualsWithSingleTableSql()), 11, 1109);
        assertDataSet("integrate/dataset/Empty.xml", shardingDataSource.getConnection(), 
                "t_order", replacePreparedStatement(getDatabaseTestSQL().getSelectEqualsWithSingleTableSql()), 12, 1000);
    }
    
    @Test
    public void assertSelectBetweenWithSingleTable() throws SQLException, DatabaseUnitException {
        assertDataSet(TABLE_ONLY_PREFIX + "/expect/select/SelectBetweenWithSingleTable.xml", shardingDataSource.getConnection(), 
                "t_order", replacePreparedStatement(getDatabaseTestSQL().getSelectBetweenWithSingleTableSql()), 10, 12, 1009, 1108);
        assertDataSet("integrate/dataset/Empty.xml", shardingDataSource.getConnection(), 
                "t_order", replacePreparedStatement(getDatabaseTestSQL().getSelectBetweenWithSingleTableSql()), 10, 12, 1309, 1408);
    }
    
    @Test
    public void assertSelectInWithSingleTable() throws SQLException, DatabaseUnitException {
        assertDataSet(TABLE_ONLY_PREFIX + "/expect/select/SelectInWithSingleTable_0.xml", shardingDataSource.getConnection(), 
                "t_order", replacePreparedStatement(getDatabaseTestSQL().getSelectInWithSingleTableSql()), 10, 11, 15, 1009, 1108);
        assertDataSet(TABLE_ONLY_PREFIX + "/expect/select/SelectInWithSingleTable_1.xml", shardingDataSource.getConnection(), 
                "t_order", replacePreparedStatement(getDatabaseTestSQL().getSelectInWithSingleTableSql()), 10, 12, 15, 1009, 1108);
        assertDataSet("integrate/dataset/Empty.xml", shardingDataSource.getConnection(), 
                "t_order", replacePreparedStatement(getDatabaseTestSQL().getSelectInWithSingleTableSql()), 10, 12, 15, 1309, 1408);
    }
    
    @Test
    public void assertSelectLimitWithBindingTable() throws SQLException, DatabaseUnitException {
        if (SQLServer.name().equalsIgnoreCase(currentDbType())) {
            String expectedDataSetFile = TABLE_ONLY_PREFIX + "/expect/select/sqlserver/SelectLimitWithBindingTable.xml";
            assertDataSet(expectedDataSetFile, getShardingDataSource().getConnection(),
                    "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableSql()), 2, 10, 19, 1000, 1909, 2);
            assertDataSet("integrate/dataset/Empty.xml", getShardingDataSource().getConnection(),
                     "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableSql()), 2, 10, 19, 1000, 1909, 20);
            return;
        }
        if (PostgreSQL.name().equalsIgnoreCase(currentDbType())) {
            String expectedDataSetFile = TABLE_ONLY_PREFIX + "/expect/select/SelectLimitWithBindingTable.xml";
            assertDataSet(expectedDataSetFile, getShardingDataSource().getConnection(),
                    "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableSql()), 10, 19, 1000, 1909, 1.5, 2.4);
        } else if (Oracle.name().equalsIgnoreCase(currentDbType())) {
            String expectedDataSetFile = TABLE_ONLY_PREFIX + "/expect/select/oracle/SelectLimitWithBindingTable.xml";
            assertDataSet(expectedDataSetFile, getShardingDataSource().getConnection(),
                    "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableSql()), 10, 19, 1000, 1909, 2, 2);
        } else {
            String expectedDataSetFile = TABLE_ONLY_PREFIX + "/expect/select/SelectLimitWithBindingTable.xml";
            assertDataSet(expectedDataSetFile, getShardingDataSource().getConnection(),
                    "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableSql()), 10, 19, 1000, 1909, 2, 2);
        }
        assertDataSet("integrate/dataset/Empty.xml", getShardingDataSource().getConnection(),
                "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableSql()), 10, 19, 1000, 1909, 100, 200);
    }
    
    @Test
    public void assertSelectLimitWithBindingTableWithRowCount() throws SQLException, DatabaseUnitException {
        if (!Oracle.name().equalsIgnoreCase(currentDbType()) && !SQLServer.name().equalsIgnoreCase(currentDbType())) {
            String expectedDataSetFile = TABLE_ONLY_PREFIX + "/expect/select/SelectLimitWithBindingTableWithoutOffset.xml";
            assertDataSet(expectedDataSetFile, getShardingDataSource().getConnection(), "t_order_item",
                    replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableWithoutOffsetSql()), 10, 19, 1000, 1909, 2);
            assertDataSet("integrate/dataset/Empty.xml", getShardingDataSource().getConnection(), "t_order_item",
                    replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableWithoutOffsetSql()), 10, 19, 1000, 1909, 0);
        }
    }
    
    @Test
    public void assertSelectLimitWithBindingTableWithOffset() throws SQLException, DatabaseUnitException {
        if (PostgreSQL.name().equalsIgnoreCase(currentDbType())) {
            String expectedDataSetFile = TABLE_ONLY_PREFIX + "/expect/select/postgresql/SelectLimitWithBindingTableWithOffset.xml";
            assertDataSet(expectedDataSetFile, getShardingDataSource().getConnection(), "t_order_item",
                    replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableWithOffsetSql()), 10, 19, 1000, 1909, 18);
            assertDataSet("integrate/dataset/Empty.xml", getShardingDataSource().getConnection(), "t_order_item",
                    replacePreparedStatement(getDatabaseTestSQL().getSelectLimitWithBindingTableWithOffsetSql()), 10, 19, 1000, 1909, 1000);
        }
    }
    
    @Test
    public void assertSelectGroupByWithBindingTable() throws SQLException, DatabaseUnitException {
        assertDataSet(TABLE_ONLY_PREFIX + "/expect/select/SelectGroupByWithBindingTable.xml", getShardingDataSource().getConnection(),
                "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectGroupWithBindingTableSql()), 10, 11, 1000, 1109);
        assertDataSet("integrate/dataset/Empty.xml", getShardingDataSource().getConnection(), 
                "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectGroupWithBindingTableSql()), 1, 9, 1000, 1909);
    }
    
    @Test
    public void assertSelectGroupByWithoutGroupedColumn() throws SQLException, DatabaseUnitException {
        String expectedDataSetFile = TABLE_ONLY_PREFIX + "/expect/select/SelectGroupByWithoutGroupedColumn.xml";
        assertDataSet(expectedDataSetFile, getShardingDataSource().getConnection(),
                "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectGroupWithoutGroupedColumnSql()), 10, 11, 1000, 1109);
        assertDataSet("integrate/dataset/Empty.xml", getShardingDataSource().getConnection(), 
                "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectGroupWithoutGroupedColumnSql()), 1, 9, 1000, 1909);
    }
    
    @Test
    public void assertSelectNoShardingTable() throws SQLException, DatabaseUnitException {
        String expectedDataSetFile = TABLE_ONLY_PREFIX + "/expect/select/SelectNoShardingTable.xml";
        assertDataSet(expectedDataSetFile, getShardingDataSource().getConnection(),
                "t_order_item", getDatabaseTestSQL().getSelectWithNoShardingTableSql());
    }
    
    @Test
    public void assertSelectWithBindingTableAndConfigTable() throws SQLException, DatabaseUnitException {
        String expectedDataSetFile = TABLE_ONLY_PREFIX + "/expect/select/SelectWithBindingTableAndConfigTable.xml";
        assertDataSet(expectedDataSetFile, shardingDataSource.getConnection(), 
                "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectGroupWithBindingTableAndConfigSql()), 10, 11, 1009, 1108, "init");
        assertDataSet("integrate/dataset/Empty.xml", shardingDataSource.getConnection(), 
                "t_order_item", replacePreparedStatement(getDatabaseTestSQL().getSelectGroupWithBindingTableAndConfigSql()), 10, 11, 1009, 1108, "none");
    }
}

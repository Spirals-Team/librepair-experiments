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

package com.dangdang.ddframe.rdb.integrate.tbl.statement;

import com.dangdang.ddframe.rdb.integrate.tbl.AbstractShardingTablesOnlyDBUnitTest;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.ShardingDataSource;
import com.dangdang.ddframe.rdb.sharding.constant.SQLType;
import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class ShardingTablesOnlyForStatementWithDMLTest extends AbstractShardingTablesOnlyDBUnitTest {
    
    private ShardingDataSource shardingDataSource;
    
    @Before
    public void init() throws SQLException {
        shardingDataSource = getShardingDataSource();
    }
    
    @Test
    public void assertInsert() throws SQLException, DatabaseUnitException {
        for (int i = 1; i <= 10; i++) {
            try (Connection connection = shardingDataSource.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(String.format(getDatabaseTestSQL().getInsertWithoutPlaceholderSql(), i, i, "'insert'"));
            }
        }
        assertDataSet("insert", "insert");
    }
    
    @Test
    public void assertUpdate() throws SQLException, DatabaseUnitException {
        for (int i = 10; i < 12; i++) {
            for (int j = 0; j < 10; j++) {
                try (Connection connection = shardingDataSource.getConnection();
                     Statement stmt = connection.createStatement()) {
                    assertThat(stmt.executeUpdate(String.format(getDatabaseTestSQL().getUpdateWithoutAliasSql(), "'updated'", i * 100 + j, i)), is(1));
                }
            }
        }
        assertDataSet("update", "updated");
    }
    
    @Test
    public void assertUpdateWithoutShardingValue() throws SQLException, DatabaseUnitException {
        try (Connection connection = shardingDataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(String.format(getDatabaseTestSQL().getUpdateWithoutShardingValueSql(), "'updated'", "'init'")), is(20));
        }
        assertDataSet("update", "updated");
    }
    
    @Test
    public void assertDelete() throws SQLException, DatabaseUnitException {
        for (int i = 10; i < 12; i++) {
            for (int j = 0; j < 10; j++) {
                try (Connection connection = shardingDataSource.getConnection();
                     Statement stmt = connection.createStatement()) {
                    assertThat(stmt.executeUpdate(String.format(getDatabaseTestSQL().getDeleteWithoutAliasSql(), i * 100 + j, i, "'init'")), is(1));
                }
            }   
        }
        assertDataSet("delete", "init");
    }
    
    @Test
    public void assertDeleteWithoutShardingValue() throws SQLException, DatabaseUnitException {
        try (Connection connection = shardingDataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(String.format(getDatabaseTestSQL().getDeleteWithoutShardingValueSql(), "'init'")), is(20));
        }
        assertDataSet("delete", "init");
    }
    
    private void assertDataSet(final String expectedDataSetPattern, final String status) throws SQLException, DatabaseUnitException {
        for (int i = 0; i < 10; i++) {
            assertDataSet(String.format("integrate/dataset/tbl/expect/%s/tbl.xml", expectedDataSetPattern),
                    shardingDataSource.getConnection().getConnection("dataSource_tbl", SQLType.SELECT), 
                    String.format("t_order_%s", i), String.format(getDatabaseTestSQL().getAssertSelectShardingTablesWithStatusSql(), i), status);
        }
    }
}

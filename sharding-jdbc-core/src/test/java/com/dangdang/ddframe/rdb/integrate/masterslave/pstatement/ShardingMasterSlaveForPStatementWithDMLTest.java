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

package com.dangdang.ddframe.rdb.integrate.masterslave.pstatement;

import com.dangdang.ddframe.rdb.integrate.masterslave.AbstractShardingMasterSlaveDBUnitTest;
import com.dangdang.ddframe.rdb.sharding.api.HintManager;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.ShardingDataSource;
import com.dangdang.ddframe.rdb.sharding.constant.SQLType;
import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.dangdang.ddframe.rdb.integrate.util.SqlPlaceholderUtil.replacePreparedStatement;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class ShardingMasterSlaveForPStatementWithDMLTest extends AbstractShardingMasterSlaveDBUnitTest {
    
    private ShardingDataSource shardingDataSource;
    
    @Before
    public void init() throws SQLException {
        shardingDataSource = getShardingDataSource();
    }
    
    @Test
    public void assertInsertWithAllPlaceholders() throws SQLException, DatabaseUnitException {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                try (Connection connection = shardingDataSource.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(getDatabaseTestSQL().getInsertWithAllPlaceholdersSql());
                    preparedStatement.setInt(1, i);
                    preparedStatement.setInt(2, j);
                    preparedStatement.setString(3, "insert");
                    preparedStatement.executeUpdate();
                }
            }
        }
        assertDataSet("insert", "insert");
    }
    
    @Test
    public void assertInsertWithHint() throws SQLException, DatabaseUnitException {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                try (
                        Connection connection = shardingDataSource.getConnection();
                        HintManager hintManager = HintManager.getInstance()
                ) {
                    hintManager.addDatabaseShardingValue("t_order", "user_id", j);
                    hintManager.addTableShardingValue("t_order", "order_id", i);
                    PreparedStatement preparedStatement = connection.prepareStatement(getDatabaseTestSQL().getInsertWithAllPlaceholdersSql());
                    preparedStatement.setInt(1, i);
                    preparedStatement.setInt(2, j);
                    preparedStatement.setString(3, "insert");
                    preparedStatement.executeUpdate();
                }
            }
        }
        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.setMasterRouteOnly();
            assertDataSet("insert", "insert");
        }
    }
    
    @Test
    public void assertInsertWithoutPlaceholder() throws SQLException, DatabaseUnitException {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                try (Connection connection = shardingDataSource.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(String.format(getDatabaseTestSQL().getInsertWithoutPlaceholderSql(), i, j));
                    preparedStatement.executeUpdate();
                }
            }
        }
        assertDataSet("insert", "insert");
    }
    
    @Test
    public void assertInsertWithPlaceholdersForShardingKeys() throws SQLException, DatabaseUnitException {
        String sql = "INSERT INTO `t_order` (`order_id`, `user_id`, `status`) VALUES (%s, %s, ?)";
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                try (Connection connection = shardingDataSource.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(String.format(getDatabaseTestSQL().getInsertWithPartialPlaceholdersSql(), i, j));
                    preparedStatement.setString(1, "insert");
                    preparedStatement.executeUpdate();
                }
            }
        }
        assertDataSet("insert", "insert");
    }
    
    @Test
    public void assertUpdateWithoutAlias() throws SQLException, DatabaseUnitException {
        for (int i = 10; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                try (Connection connection = shardingDataSource.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(replacePreparedStatement(getDatabaseTestSQL().getUpdateWithoutAliasSql()));
                    preparedStatement.setString(1, "updated");
                    preparedStatement.setInt(2, i * 100 + j);
                    preparedStatement.setInt(3, i);
                    assertThat(preparedStatement.executeUpdate(), is(1));
                }
            }
        }
        assertDataSet("update", "updated");
    }
    
    @Test
    public void assertUpdateWithAlias() throws SQLException, DatabaseUnitException {
        if (isAliasSupport()) {
            for (int i = 10; i < 20; i++) {
                for (int j = 0; j < 10; j++) {
                    try (Connection connection = shardingDataSource.getConnection()) {
                        PreparedStatement preparedStatement = connection.prepareStatement(getDatabaseTestSQL().getUpdateWithAliasSql());
                        preparedStatement.setString(1, "updated");
                        preparedStatement.setInt(2, i * 100 + j);
                        preparedStatement.setInt(3, i);
                        assertThat(preparedStatement.executeUpdate(), is(1));
                    }
                }
            }
            assertDataSet("update", "updated");
        }
    }
    
    @Test
    public void assertDeleteWithoutAlias() throws SQLException, DatabaseUnitException {
        for (int i = 10; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                try (Connection connection = shardingDataSource.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(replacePreparedStatement(getDatabaseTestSQL().getDeleteWithoutAliasSql()));
                    preparedStatement.setInt(1, i * 100 + j);
                    preparedStatement.setInt(2, i);
                    preparedStatement.setString(3, "init_master");
                    assertThat(preparedStatement.executeUpdate(), is(1));
                }
            }
        }
        assertDataSet("delete", "init");
    }
    
    protected void assertDataSet(final String expectedDataSetPattern, final String status) throws SQLException, DatabaseUnitException {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertDataSet(String.format("integrate/dataset/masterslave/expect/%s/master_%s.xml", expectedDataSetPattern, i),
                        shardingDataSource.getConnection().getConnection(String.format("ms_%s", i), SQLType.INSERT), 
                        String.format("t_order_%s", j), String.format(getDatabaseTestSQL().getAssertSelectShardingTablesWithStatusSql(), j), status);
            }
        }
    }
}

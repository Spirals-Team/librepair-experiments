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

package com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource;

import com.dangdang.ddframe.rdb.sharding.api.strategy.slave.RoundRobinSlaveLoadBalanceStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.slave.SlaveLoadBalanceStrategy;
import com.dangdang.ddframe.rdb.sharding.constant.SQLType;
import com.dangdang.ddframe.rdb.sharding.hint.HintManagerHolder;
import com.dangdang.ddframe.rdb.sharding.jdbc.adapter.AbstractDataSourceAdapter;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 支持读写分离的数据源.
 *
 * @author zhangliang
 */
@RequiredArgsConstructor
public final class MasterSlaveDataSource extends AbstractDataSourceAdapter {
    
    private static final ThreadLocal<Boolean> DML_FLAG = new ThreadLocal<Boolean>() {
        
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };
    
    private final String name;
    
    private final DataSource masterDataSource;
    
    private final List<DataSource> slaveDataSources;
    
    private final SlaveLoadBalanceStrategy slaveLoadBalanceStrategy = new RoundRobinSlaveLoadBalanceStrategy();
    
    /**
     * 获取主或从节点的数据源名称.
     *
     * @param dataSourceName 数据源名称
     * @param sqlType SQL类型
     * @return 主或从节点的数据源名称
     */
    public static String getDataSourceName(final String dataSourceName, final SQLType sqlType) {
        return isMasterRoute(sqlType) ? getMasterDataSourceName(dataSourceName) : getSlaveDataSourceName(dataSourceName);
    }
    
    private static boolean isMasterRoute(final SQLType sqlType) {
        return SQLType.SELECT != sqlType || DML_FLAG.get() || HintManagerHolder.isMasterRouteOnly();
    }
    
    private static String getMasterDataSourceName(final String dataSourceName) {
        return Joiner.on("-").join(dataSourceName, "MASTER");
    }
    
    private static String getSlaveDataSourceName(final String dataSourceName) {
        return Joiner.on("-").join(dataSourceName, "SLAVE");
    }
    
    /**
     * 获取主或从节点的数据源.
     *
     * @param sqlType SQL类型
     * @return 主或从节点的数据源
     */
    public DataSource getDataSource(final SQLType sqlType) {
        if (isMasterRoute(sqlType)) {
            DML_FLAG.set(true);
            return masterDataSource;
        }
        return slaveLoadBalanceStrategy.getDataSource(name, slaveDataSources);
    }
    
    /**
     * 获取数据库名称.
     * 
     * @return 数据库名称
     * @throws SQLException SQL异常
     */
    public String getDatabaseProductName() throws SQLException {
        String result;
        try (Connection masterConnection = masterDataSource.getConnection()) {
            result = masterConnection.getMetaData().getDatabaseProductName();
        }
        for (DataSource each : slaveDataSources) {
            String slaveDatabaseProductName;
            try (Connection slaveConnection = each.getConnection()) {
                slaveDatabaseProductName = slaveConnection.getMetaData().getDatabaseProductName();    
            }
            Preconditions.checkState(result.equals(slaveDatabaseProductName), String.format("Database type inconsistent with '%s' and '%s'", result, slaveDatabaseProductName));
        }
        return result;
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        throw new UnsupportedOperationException("Master slave data source cannot support get connection directly.");
    }
    
    /**
     * 重置更新标记.
     */
    public static void resetDMLFlag() {
        DML_FLAG.remove();
    }
}

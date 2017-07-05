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

package com.dangdang.ddframe.rdb.sharding.merger.resultset.memory.row;

import com.google.common.base.Preconditions;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果集数据行抽象类.
 * 
 * @author gaohongtao
 * @author zhangliang
 */
public abstract class AbstractResultSetRow implements ResultSetRow {
    
    private final Object[] data;
    
    public AbstractResultSetRow(final ResultSet resultSet) throws SQLException {
        data = load(resultSet);
    }
    
    private Object[] load(final ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        Object[] result = new Object[columnCount];
        for (int i = 0; i < columnCount; i++) {
            result[i] = resultSet.getObject(i + 1);
        }
        return result;
    }
    
    protected final void setCell(final int columnIndex, final Object value) {
        data[columnIndex - 1] = value;
    }
    
    @Override
    public final Object getCell(final int columnIndex) {
        Preconditions.checkArgument(columnIndex > 0 && columnIndex < data.length + 1);
        return data[columnIndex - 1];
    }
}

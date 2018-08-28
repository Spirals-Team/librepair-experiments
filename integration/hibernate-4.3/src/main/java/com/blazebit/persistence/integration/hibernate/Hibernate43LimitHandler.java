/*
 * Copyright 2014 - 2018 Blazebit.
 *
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
 */

package com.blazebit.persistence.integration.hibernate;

import com.blazebit.persistence.spi.DbmsDialect;
import com.blazebit.persistence.spi.DbmsLimitHandler;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.engine.spi.RowSelection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Christian Beikov
 * @since 1.2.0
 */
public class Hibernate43LimitHandler implements LimitHandler {

    private final DbmsLimitHandler limitHandler;
    private final String sql;
    private final Integer limit;
    private final Integer offset;

    public Hibernate43LimitHandler(Dialect dialect, DbmsDialect dbmsDialect, String sql, RowSelection selection) {
        this.limitHandler = dbmsDialect.createLimitHandler();
        this.sql = sql;

        if (selection == null || selection.getMaxRows() == null || selection.getMaxRows().intValue() == Integer.MAX_VALUE) {
            this.limit = null;
        } else {
            this.limit = selection.getMaxRows();
        }
        if (selection == null || selection.getFirstRow() == null || selection.getFirstRow().intValue() < 1) {
            this.offset = null;
        } else {
            this.offset = selection.getFirstRow();
        }
    }

    @Override
    public boolean supportsLimit() {
        return limitHandler.supportsLimit();
    }

    @Override
    public boolean supportsLimitOffset() {
        return limitHandler.supportsLimitOffset();
    }

    @Override
    public String getProcessedSql() {
        return limitHandler.applySql(sql, false, limit, offset);
    }

    @Override
    public int bindLimitParametersAtStartOfQuery(PreparedStatement statement, int index) throws SQLException {
        return limitHandler.bindLimitParametersAtStartOfQuery(limit, offset, statement, index);
    }

    @Override
    public int bindLimitParametersAtEndOfQuery(PreparedStatement statement, int index) throws SQLException {
        return limitHandler.bindLimitParametersAtEndOfQuery(limit, offset, statement, index);
    }

    @Override
    public void setMaxRows(PreparedStatement statement) throws SQLException {
        limitHandler.setMaxRows(limit, offset, statement);
    }

}

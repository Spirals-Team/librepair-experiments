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

package com.blazebit.persistence.impl.dialect;

import java.util.Map;

import com.blazebit.persistence.spi.DbmsModificationState;
import com.blazebit.persistence.spi.DbmsStatementType;
import com.blazebit.persistence.spi.ValuesStrategy;

/**
 * @author Christian Beikov
 * @since 1.2.0
 */
public class H2DbmsDialect extends DefaultDbmsDialect {

    public H2DbmsDialect() {
    }

    public H2DbmsDialect(Map<Class<?>, String> childSqlTypes) {
        super(childSqlTypes);
    }

    @Override
    public boolean supportsReturningAllGeneratedKeys() {
        return false;
    }
    
    @Override
    public boolean supportsWithClause() {
        return true;
    }

    @Override
    public boolean supportsNonRecursiveWithClause() {
        return false;
    }

    @Override
    public String getWithClause(boolean recursive) {
        return "with recursive";
    }

    @Override
    public Map<String, String> appendExtendedSql(StringBuilder sqlSb, DbmsStatementType statementType, boolean isSubquery, boolean isEmbedded, StringBuilder withClause, String limit, String offset, String[] returningColumns, Map<DbmsModificationState, String> includedModificationStates) {
        boolean addParenthesis = isSubquery && sqlSb.length() > 0 && sqlSb.charAt(0) != '(';
        if (addParenthesis) {
            sqlSb.insert(0, '(');
        }
        
        if (isSubquery && returningColumns != null) {
            throw new IllegalArgumentException("Returning columns in a subquery is not possible for this dbms!");
        }
        
        // NOTE: this only works for insert and select statements, but H2 does not support CTEs in modification queries anyway so it's ok
        if (withClause != null) {
            sqlSb.insert(indexOfIgnoreCase(sqlSb, "select"), withClause);
        }
        if (limit != null) {
            appendLimit(sqlSb, isSubquery, limit, offset);
        }
        
        if (addParenthesis) {
            sqlSb.append(')');
        }
        
        return null;
    }
    
    @Override
    public boolean supportsWithClauseInModificationQuery() {
        return false;
    }

    @Override
    public ValuesStrategy getValuesStrategy() {
        return ValuesStrategy.SELECT_VALUES;
    }
}

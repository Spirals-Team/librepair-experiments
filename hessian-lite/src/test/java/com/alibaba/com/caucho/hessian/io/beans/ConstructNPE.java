package com.alibaba.com.caucho.hessian.io.beans;

import java.sql.SQLException;

/**
 * <a href="https://github.com/apache/incubator-dubbo/issues/210">#210</a>
 * @see org.springframework.jdbc.UncategorizedSQLException
 */
public class ConstructNPE extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String sql;

    public ConstructNPE(String task, String sql, SQLException ex) {
        super(task + "; uncategorized SQLException for SQL [" + sql + "]; SQL state [" +
                ex.getSQLState() + "]; error code [" + ex.getErrorCode() + "]; " + ex.getMessage(), ex);
        this.sql = sql;
    }

    public SQLException getSQLException() {
        return (SQLException) getCause();
    }

    public String getSql() {
        return this.sql;
    }
}
package org.sql2o;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.sql2o.quirks.Quirks;

/**
 * Iterator for a {@link java.sql.ResultSet}. Tricky part here is getting {@link #hasNext()}
 * to work properly, meaning it can be called multiple times without calling {@link #next()}.
 *
 * @author aldenquimby@gmail.com
 */
@Slf4j
public class PojoResultSetIterator<T> extends ResultSetIteratorBase<T> {
    private ResultSetHandler<T> handler;

    public PojoResultSetIterator(ResultSet rs, boolean isCaseSensitive, Quirks quirks,
        ResultSetHandlerFactory<T> factory) {
        super(rs, isCaseSensitive, quirks);
        try {
            this.handler = factory.newResultSetHandler(rs.getMetaData());
            log.debug("" + rs.getMetaData().getColumnCount());
        } catch (SQLException e) {
            throw new Sql2oException("Database error: " + e.getMessage(), e);
        }
    }

    public PojoResultSetIterator(ResultSet rs, boolean isCaseSensitive, Quirks quirks,
        ResultSetHandler<T> handler) {
        super(rs, isCaseSensitive, quirks);
        this.handler = handler;
    }

    @Override
    protected T readNext() throws SQLException {
        return handler.handle(rs);
    }
}

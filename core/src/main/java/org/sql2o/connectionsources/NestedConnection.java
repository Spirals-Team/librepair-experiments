package org.sql2o.connectionsources;

import java.sql.Connection;
import java.sql.SQLException;
import org.sql2o.logging.LocalLoggerFactory;
import org.sql2o.logging.Logger;

/**
 * Created by nickl on 09.01.17.
 */
class NestedConnection extends WrappedConnection {

    private final static Logger logger = LocalLoggerFactory.getLogger(NestedConnection.class);

    private boolean autocommit = true;
    private boolean commited = false;

    NestedConnection(Connection source) {
        super(source);
    }

    @Override
    public void commit() {
        commited = true;
        //do nothing, parent connection should be committed
    }

    @Override
    public void rollback() throws SQLException {
        if (!commited) {
            logger.warn(
                "rollback of nested transaction leads to rollback of parent transaction. Maybe it is not wat you want.");
            super.rollback(); //probably it's worth to use savepoints
        }
    }

    @Override
    public void close() {
        //do nothing, parent connection should be closed by someone who cares
    }

    @Override
    public void setTransactionIsolation(int level) {
        //do nothing, parent connection should be configured
    }

    @Override
    public boolean getAutoCommit() {
        return autocommit;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) {
        this.autocommit = autoCommit;
    }
}

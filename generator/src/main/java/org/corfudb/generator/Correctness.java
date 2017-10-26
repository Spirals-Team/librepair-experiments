package org.corfudb.generator;

import ch.qos.logback.classic.Logger;
import org.corfudb.runtime.object.transactions.TransactionalContext;
import org.slf4j.LoggerFactory;

/**
 * Created by rmichoud on 10/9/17.
 */
public class Correctness {

    static Logger correctness = (Logger) LoggerFactory.getLogger("correctness");

    public static void recordOperation(String operation, boolean transactionPrefix) {
        if (transactionPrefix) {
            operation = "Tx" + operation;
            correctness.info("{}, {}", operation,
                    TransactionalContext.getCurrentContext().getSnapshotTimestamp());
        } else {
            correctness.info(operation);
        }

    }

}

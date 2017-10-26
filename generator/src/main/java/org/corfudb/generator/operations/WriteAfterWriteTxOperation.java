package org.corfudb.generator.operations;

import ch.qos.logback.classic.Logger;
import org.corfudb.generator.Correctness;
import org.corfudb.generator.State;
import org.corfudb.runtime.exceptions.TransactionAbortedException;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by rmichoud on 10/6/17.
 */
public class WriteAfterWriteTxOperation extends Operation {

    public WriteAfterWriteTxOperation(State state) {
        super(state);
        shortName = "TxWaw";
    }

    @Override
    public void execute() {

        Correctness.recordOperation("TxWAW, start", false);
        long timestamp;
        state.startWriteAfterWriteTx();

        int numOperations = state.getOperationCount().sample(1).get(0);
        List<Operation> operations = state.getOperations().sample(numOperations);

        for (int x = 0; x < operations.size(); x++) {
            if (operations.get(x) instanceof org.corfudb.generator.operations.OptimisticTxOperation
                    || operations.get(x) instanceof SnapshotTxOperation
                    || operations.get(x) instanceof NestedTxOperation)
            {
                continue;
            }

            operations.get(x).execute();
        }
        try {
            timestamp = state.stopTx();
        } catch (TransactionAbortedException tae) {
            Correctness.recordOperation("TxWAW, aborted", false);
            throw tae;
        }

        String commitRecord = String.format("TxAWA, end, %s", timestamp);
        Correctness.recordOperation(commitRecord, false);
    }
}

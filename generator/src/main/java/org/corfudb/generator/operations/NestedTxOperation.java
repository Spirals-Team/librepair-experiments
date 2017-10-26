package org.corfudb.generator.operations;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.corfudb.generator.Correctness;
import org.corfudb.generator.State;
import org.corfudb.runtime.exceptions.TransactionAbortedException;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by rmichoud on 7/26/17.
 */
@Slf4j
public class NestedTxOperation extends Operation {

    Logger correctness = (Logger) LoggerFactory.getLogger("correctness");
    private final static int maxNest = 20;
    public NestedTxOperation(State state) {
        super(state);
    }

    @Override
    public void execute() {
        Correctness.recordOperation("TxNest, start", false);
        state.startOptimisticTx();

        int numNested = state.getOperationCount().sample(1).get(0);
        int nestedTxToStop = numNested;
        for (int i = 0; i < numNested && i < maxNest; i++) {
            try{
            state.startOptimisticTx();
            int numOperations = state.getOperationCount().sample(1).get(0);
            List<Operation> operations = state.getOperations().sample(numOperations);

                log.info("Start nested Tx of " + numOperations + "operations");
                log.info("Nesting " + i);
            for (int x = 0; x < operations.size(); x++) {
                if (operations.get(x) instanceof OptimisticTxOperation ||
                        operations.get(x) instanceof SnapshotTxOperation
            || operations.get(x) instanceof NestedTxOperation) {
                    continue;
                }
                operations.get(x).execute();
            }
            } catch (TransactionAbortedException tae) {
                log.warn("Transaction Aborted", tae);
                log.info("Nesting: " + i);
                nestedTxToStop--;
            }
        }

        for (int i = 0; i < nestedTxToStop; i++) {
            state.stopTx();
        }
        long version;
        try {
            version = state.stopTx();
        } catch (TransactionAbortedException tae) {
            Correctness.recordOperation("TxNest, aborted", false);
            throw tae;
        }
        String commitRecord = String.format("TxNest, end, %s", version);
        Correctness.recordOperation(commitRecord, false);
    }
}

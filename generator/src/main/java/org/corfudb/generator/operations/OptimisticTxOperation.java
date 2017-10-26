package org.corfudb.generator.operations;

import java.util.List;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.corfudb.generator.Correctness;
import org.corfudb.generator.State;
import org.corfudb.runtime.exceptions.TransactionAbortedException;
import org.slf4j.LoggerFactory;

/**
 * Created by maithem on 7/14/17.
 */
@Slf4j
public class OptimisticTxOperation extends Operation {

    Logger correctness = (Logger) LoggerFactory.getLogger("correctness");

    public OptimisticTxOperation(State state) {
        super(state);
        shortName = "TxOpt";
    }

    @Override
    public void execute() {
        try {
            Correctness.recordOperation("TxOpt, start", false);
            long timestamp;
            state.startOptimisticTx();

            int numOperations = state.getOperationCount().sample(1).get(0);
            List<Operation> operations = state.getOperations().sample(numOperations);

            for (int x = 0; x < operations.size(); x++) {
                if (operations.get(x) instanceof OptimisticTxOperation
                        || operations.get(x) instanceof SnapshotTxOperation
                        || operations.get(x) instanceof NestedTxOperation)
                {
                    continue;
                }

                operations.get(x).execute();
            }

            timestamp = state.stopTx();
            String commitRecord = String.format("TxOpt, end, %s", timestamp);
            Correctness.recordOperation(commitRecord, false);
        } catch (TransactionAbortedException tae) {
            Correctness.recordOperation("TxOpt, aborted", false);
//            throw tae;
        }



    }
}

package org.corfudb.generator.operations;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.corfudb.generator.Correctness;
import org.corfudb.generator.State;
import org.corfudb.runtime.exceptions.TransactionAbortedException;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by box on 7/15/17.
 */
@Slf4j
public class SnapshotTxOperation extends Operation {


    public SnapshotTxOperation(State state) {
        super(state);
        shortName = "TxSnap";
    }

    @Override
    public void execute() {
        try {
            long trimMark = state.getTrimMark();
            Random rand = new Random();
            long delta = (long) rand.nextInt(10) + 1;

            // Safety Hack for not having snapshot in the future

            long current_max = state.getRuntime().getSequencerView()
                    .nextToken(Collections.emptySet(), 0)
                    .getToken().getTokenValue();

            long snapShotAddress = Long.min(trimMark + delta, current_max);


            Correctness.recordOperation("TxSnap, start", false);
            state.startSnapshotTx(snapShotAddress);

            int numOperations = state.getOperationCount().sample(1).get(0);
            List<Operation> operations = state.getOperations().sample(numOperations);

            for (int x = 0; x < operations.size(); x++) {
                if (operations.get(x) instanceof OptimisticTxOperation
                        || operations.get(x) instanceof SnapshotTxOperation
                        || operations.get(x) instanceof RemoveOperation
                        || operations.get(x) instanceof WriteOperation
                        || operations.get(x) instanceof NestedTxOperation) {
                    continue;
                }

                operations.get(x).execute();
            }

            state.stopTx();
            Correctness.recordOperation("TxSnap, end", false);
        } catch (TransactionAbortedException tae) {
            Correctness.recordOperation("TxSnap, aborted", false);
//            throw tae;
        }


    }
}

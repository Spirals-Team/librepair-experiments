package org.corfudb.infrastructure.orchestrator;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.corfudb.protocols.wireprotocol.ILogData;
import org.corfudb.protocols.wireprotocol.LogData;
import org.corfudb.protocols.wireprotocol.RangeWriteMsg;
import org.corfudb.protocols.wireprotocol.orchestrator.AddNodeRequest;
import org.corfudb.protocols.wireprotocol.orchestrator.Request;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.clients.LogUnitClient;
import org.corfudb.runtime.view.Layout;
import org.corfudb.util.CFUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.NotThreadSafe;

import static org.corfudb.format.Types.OrchestratorRequestType.ADD_NODE;

/**
 * A definition of a workflow that adds a new node to the cluster.
 *
 * @author Maithem
 */
@NotThreadSafe
@Slf4j
public class AddNodeWorkflow implements Workflow {

    final AddNodeRequest request;

    private Layout newLayout;

    public AddNodeWorkflow(Request request) {
        this.request = (AddNodeRequest) request;
    }

    @Override
    public String getName() {
        return ADD_NODE.toString();
    }

    @Override
    public List<Action> getActions() {
        return Arrays.asList(new BootstrapNode(),
                new AddNodeToLayout(),
                new StateTransfer(),
                new MergeSegments());
    }

    class BootstrapNode implements Action {
        @Override
        public String getName() {
            return "BootstrapNode";
        }

        @Override
        public void execute(@Nonnull CorfuRuntime runtime) throws Exception {
            runtime.getLayoutManagementView().bootstrapNewNode(request.getEndpoint());
        }
    }

    /**
     * This action adds a new node to the layout. If it is also
     * added as a logunit server, then in addition to adding
     * the node the address space segment is split at the
     * tail determined during the layout modification.
     */
    class AddNodeToLayout implements Action {
        @Override
        public String getName() {
            return "AddNodeToLayout";
        }

        @Override
        public void execute(@Nonnull CorfuRuntime runtime) throws Exception {
            Layout currentLayout = (Layout) runtime.getLayoutView().getLayout().clone();
            runtime.getLayoutManagementView().addNode(currentLayout, request.getEndpoint(),
                    true, true,
                    true, false,
                    0);
            runtime.invalidateLayout();
            newLayout = (Layout) runtime.getLayoutView().getLayout().clone();
        }
    }

    public void stateTransfer(String endpoint, CorfuRuntime runtime, Layout.LayoutSegment segment) {

        final long chunkSize = 2500;

        for (long chunkStart = segment.getStart(); chunkStart < segment.getEnd()
                ; chunkStart = chunkStart + chunkSize) {
            long chunkEnd = Math.min((chunkStart + chunkSize - 1), segment.getEnd() - 1);

            Map<Long, ILogData> dataMap = runtime.getAddressSpaceView()
                    .cacheFetch(ContiguousSet.create(
                            Range.closed(chunkStart, chunkEnd),
                            DiscreteDomain.longs()));

            List<LogData> entries = new ArrayList<>();
            for (long x = chunkStart; x <= chunkEnd; x++) {
                if (dataMap.get(x) == null) {
                    log.error("Missing address {} in range {}-{}", x, chunkStart, chunkEnd);
                    throw new RuntimeException("Missing address");
                }
                entries.add((LogData) dataMap.get(x));
            }

            // Write segment chunk to the new logunit
            boolean replicationSuccess = CFUtils.getUninterruptibly(runtime
                    .getRouter(endpoint)
                    .getClient(LogUnitClient.class)
                    .writeRange(entries));

            if (!replicationSuccess) {
                throw new IllegalStateException("Replication failure.");
            }

            log.info("stateTransfer: Replicated address chunk [{}, {}]",
                    chunkStart, chunkEnd);
        }
    }


    /**
     * Copies the split segment to the new node, if it
     * is the new node also participates as a logging unit.
     */
    class StateTransfer implements Action {
        @Override
        public String getName() {
            return "StateTransfer";
        }

        @Override
        public void execute(@Nonnull CorfuRuntime runtime) throws Exception {
            // Transfer the replicated segment to the new node
            stateTransfer(request.getEndpoint(), runtime, newLayout.getSegment(0));
        }
    }

    /**
     * Merges the fragmented segment if the AddNodeToLayout action caused any
     * segments to split
     */
    class MergeSegments implements Action {
        @Override
        public String getName() {
            return "MergeSegments";
        }

        @Override
        public void execute(@Nonnull CorfuRuntime runtime) throws Exception {
            // Transfer the replicated segment to the new node
            runtime.getLayoutManagementView().mergeSegments(newLayout);
        }
    }
}

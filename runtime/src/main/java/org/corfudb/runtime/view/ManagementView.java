package org.corfudb.runtime.view;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.corfudb.protocols.wireprotocol.orchestrator.CreateWorkflowResponse;
import org.corfudb.protocols.wireprotocol.orchestrator.QueryResponse;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.clients.ManagementClient;
import org.corfudb.runtime.exceptions.NetworkException;
import org.corfudb.util.Sleep;
import org.corfudb.util.Utils;

/**
 * A view of the Management Service to manage reconfigurations of the Corfu Cluster.
 *
 * <p>Created by zlokhandwala on 11/20/17.</p>
 */
@Slf4j
public class ManagementView extends AbstractView {

    static final Duration WORKFLOW_TIMEOUT = Duration.ofMinutes(10);
    static final Duration QUERY_INTERVAL = Duration.ofSeconds(1);
    static final int WORKFLOW_RETRY = 3;

    public ManagementView(@NonNull CorfuRuntime runtime) {
        super(runtime);
    }

    void waitForWorkflow(UUID workflow, ManagementClient client) {
        long tries = WORKFLOW_TIMEOUT.getSeconds() / QUERY_INTERVAL.getSeconds();
        log.info("waitForWorkflow: num tries {}", tries);
        for (long x = 0; x < tries; x++) {
            try {
                if (!client.queryRequest(workflow).isActive()) {
                    break;
                }
            } catch (NetworkException e) {
                log.warn("waitForWorkflow: Network exception while waiting for {} on try {}", workflow, x);
            }
            Sleep.sleepUninterruptibly(QUERY_INTERVAL);
            log.info("waitForWorkflow: waiting for {} on try {}", workflow, x);
        }
    }

    Layout getMaxLayout(CorfuRuntime rt) {
        Layout max = rt.getLayoutView().getLayout();
        for (int x = 0; x < 3; x++) {
            rt.invalidateLayout();
            Layout tmp = rt.getLayoutView().getLayout();
            if (tmp.getEpoch() > max.getEpoch()) {
                max = tmp;
            }
        }
        return max;
    }

    /**
     * Add a new node to the existing cluster.
     *
     * @param endpointToAdd Endpoint of the new node to be added to the cluster.
     * @return True if completed successfully.
     */
    public boolean addNode(String endpointToAdd) {

        for (int x = 0; x < WORKFLOW_RETRY; x++) {
            runtime.invalidateLayout();
            Layout layout = runtime.getLayoutView().getLayout();
            List<String> logServers = layout.getSegments().get(0).getStripes().get(0).getLogServers();
            String orchestratorEndpoint = logServers.get(logServers.size() - 1);
            ManagementClient client = runtime.getRouter(orchestratorEndpoint)
                    .getClient(ManagementClient.class);

            CreateWorkflowResponse resp;
            try {
                resp = client.addNodeRequest(endpointToAdd);
                log.info("addNode: Workflow id {} for {}", resp.workflowId, endpointToAdd);
            } catch (NetworkException e) {
                log.warn("addNode: Exception while requesting to add {}", endpointToAdd, e);
                continue;
            }

            waitForWorkflow(resp.getWorkflowId(), client);

            Layout newLayout = getMaxLayout(runtime);
            if (newLayout.getAllServers().contains(endpointToAdd)) {
                return true;
            }
            log.warn("addNode: Couldn't find {} in {}, retrying...", endpointToAdd, newLayout, x);
        }

        log.warn("addNode: Failed to add node {}", endpointToAdd);
        return false;
    }
}

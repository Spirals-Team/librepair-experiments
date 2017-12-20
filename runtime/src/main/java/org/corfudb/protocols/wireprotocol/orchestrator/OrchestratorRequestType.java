package org.corfudb.protocols.wireprotocol.orchestrator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type of requests that can be made to the Orchestrator Service.
 * 
 * @author Maithem
 */
@AllArgsConstructor
public enum OrchestratorRequestType {

    /**
     * Query a workflow id
     */
    QUERY(0),

    /**
     * Add a new node to the cluster
     */
    ADD_NODE(1);

    @Getter
    public int type;

    /**
     * Create a workflow instance from an orchestrator request
     *
     * @param request Orchestrator request
     * @return Workflow instance
     */
    public AbstractWorkflow getWorkflow(OrchestratorRequest request) {
        Request payload = request.getRequest();
        switch (payload.getType()) {
            case ADD_NODE: return new AddNodeWorkflow(payload);
            default:
                throw new IllegalArgumentException("Unknown request type " + payload.getType());
        }
    }

    /**
     * Map an int to an enum.
     */
    static final Map<Integer, OrchestratorRequestType> typeMap =
            Arrays.stream(OrchestratorRequestType.values())
                    .collect(Collectors.toMap(OrchestratorRequestType::getType, Function.identity()));
}

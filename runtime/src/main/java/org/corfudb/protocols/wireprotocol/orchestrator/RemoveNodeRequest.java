package org.corfudb.protocols.wireprotocol.orchestrator;

import lombok.Getter;

import java.nio.charset.StandardCharsets;

import static org.corfudb.protocols.wireprotocol.orchestrator.OrchestratorRequestType.REMOVE_NODE;

/**
 * @author Maithem
 */
public class RemoveNodeRequest implements CreateRequest {
    @Getter
    public String endpoint;

    public RemoveNodeRequest(String endpoint) {
        this.endpoint = endpoint;
    }

    public RemoveNodeRequest(byte[] buf) {
        endpoint = new String(buf, StandardCharsets.UTF_8);
    }

    @Override
    public OrchestratorRequestType getType() {
        return REMOVE_NODE;
    }

    @Override
    public byte[] getSerialized() {
        return endpoint.getBytes(StandardCharsets.UTF_8);
    }
}

package org.apache.storm.messaging;

import org.apache.storm.Config;
import org.apache.storm.daemon.worker.WorkerState;
import org.apache.storm.task.GeneralTopologyContext;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeserializingConnectionCallbackTest {
    private static final byte [] messageBytes = new byte[3];
    private static TaskMessage message;

    @Before
    public void setUp() throws Exception {
        // Setup a test message
        message = mock(TaskMessage.class);
        when(message.task()).thenReturn(456); // destination taskId
        when(message.message()).thenReturn(messageBytes);
    }
    
    
    private void verifyEmptyMetricsHelper(Object metrics) {
        assertTrue(metrics != null && metrics instanceof Map);
        assertTrue(((Map) metrics).isEmpty());
    }
    
    @Test
    public void testUpdateMetricsConfigOff() {
        Map config = new HashMap();
        config.put(Config.TOPOLOGY_SERIALIZED_MESSAGE_SIZE_METRICS, Boolean.FALSE);
        DeserializingConnectionCallback withoutMetrics = 
            new DeserializingConnectionCallback(config, mock(GeneralTopologyContext.class), mock(
            WorkerState.ILocalTransferCallback.class));

        // Starting empty
        verifyEmptyMetricsHelper(withoutMetrics.getValueAndReset());

        // Add our messages and verify no metrics are recorded  
        withoutMetrics.updateMetrics(123, message);
        verifyEmptyMetricsHelper(withoutMetrics.getValueAndReset());
    }
    
    @Test
    public void testUpdateMetricsConfigOn() {
        Map config = new HashMap();
        config.put(Config.TOPOLOGY_SERIALIZED_MESSAGE_SIZE_METRICS, Boolean.TRUE);
        DeserializingConnectionCallback withMetrics =
            new DeserializingConnectionCallback(config, mock(GeneralTopologyContext.class), mock(
                WorkerState.ILocalTransferCallback.class));

        // Starting empty
        Object metrics = withMetrics.getValueAndReset();
        verifyEmptyMetricsHelper(metrics);

        // Add messages
        withMetrics.updateMetrics(123, message);
        withMetrics.updateMetrics(123, message);
        
        // Verify recorded messages size metrics 
        metrics = withMetrics.getValueAndReset();
        assertTrue(metrics != null && metrics instanceof Map);
        assertEquals(6L, ((Map) metrics).get("123-456"));
    }
}

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.storm.topology;

import org.apache.storm.generated.GlobalStreamId;
import org.apache.storm.generated.Grouping;
import org.apache.storm.spout.CheckpointSpout;
import org.apache.storm.state.KeyValueState;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.tuple.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.apache.storm.spout.CheckPointState.Action.*;
import static org.apache.storm.spout.CheckpointSpout.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link StatefulBoltExecutor}
 */
public class StatefulBoltExecutorTest {
    private StatefulBoltExecutor<KeyValueState<String, String>> executor;
    private IStatefulBolt<KeyValueState<String, String>> mockBolt;
    private TopologyContext mockTopologyContext;
    private Tuple mockTuple;
    private Tuple mockCheckpointTuple;
    private Map<String, Object> mockStormConf = new HashMap<>();
    private OutputCollector mockOutputCollector;
    private KeyValueState<String, String> mockState;
    
    @Before
    public void setUp() throws Exception {
        mockBolt = Mockito.mock(IStatefulBolt.class);
        executor = new StatefulBoltExecutor<>(mockBolt);
        mockTopologyContext = Mockito.mock(TopologyContext.class);
        mockOutputCollector = Mockito.mock(OutputCollector.class);
        mockState = Mockito.mock(KeyValueState.class);
        Mockito.when(mockTopologyContext.getThisComponentId()).thenReturn("test");
        Mockito.when(mockTopologyContext.getThisTaskId()).thenReturn(1);
        GlobalStreamId globalStreamId = new GlobalStreamId("test", CheckpointSpout.CHECKPOINT_STREAM_ID);
        Map<GlobalStreamId, Grouping> thisSources = Collections.singletonMap(globalStreamId, mock(Grouping.class));
        Mockito.when(mockTopologyContext.getThisSources()).thenReturn(thisSources);
        Mockito.when(mockTopologyContext.getComponentTasks(Mockito.any())).thenReturn(Collections.singletonList(1));
        mockTuple = Mockito.mock(Tuple.class);
        mockCheckpointTuple = Mockito.mock(Tuple.class);
        executor.prepare(mockStormConf, mockTopologyContext, mockOutputCollector, mockState);
    }

    @Test
    public void testHandleTupleBeforeInit() throws Exception {
        Mockito.when(mockTuple.getSourceStreamId()).thenReturn("default");
        executor.execute(mockTuple);
        Mockito.verify(mockBolt, Mockito.times(0)).execute(Mockito.any(Tuple.class));
    }

    @Test
    public void testHandleTuple() throws Exception {
        Mockito.when(mockTuple.getSourceStreamId()).thenReturn("default");
        executor.execute(mockTuple);
        Mockito.when(mockCheckpointTuple.getSourceStreamId()).thenReturn(CheckpointSpout.CHECKPOINT_STREAM_ID);
        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(INITSTATE);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(0));
        Mockito.doNothing().when(mockOutputCollector).ack(mockCheckpointTuple);
        executor.execute(mockCheckpointTuple);
        Mockito.verify(mockBolt, Mockito.times(1)).execute(mockTuple);
        Mockito.verify(mockBolt, Mockito.times(1)).initState(Mockito.any(KeyValueState.class));
    }

    @Test
    public void testRollback() throws Exception {
        Mockito.when(mockTuple.getSourceStreamId()).thenReturn("default");
        executor.execute(mockTuple);
        Mockito.when(mockCheckpointTuple.getSourceStreamId()).thenReturn(CheckpointSpout.CHECKPOINT_STREAM_ID);
        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(ROLLBACK);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(0));
        Mockito.doNothing().when(mockOutputCollector).ack(mockCheckpointTuple);
        executor.execute(mockCheckpointTuple);
        Mockito.verify(mockState, Mockito.times(1)).rollback();
    }

    @Test
    public void testCommit() throws Exception {
        Mockito.when(mockTuple.getSourceStreamId()).thenReturn("default");
        executor.execute(mockTuple);
        Mockito.when(mockCheckpointTuple.getSourceStreamId()).thenReturn(CheckpointSpout.CHECKPOINT_STREAM_ID);
        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(COMMIT);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(0));
        Mockito.doNothing().when(mockOutputCollector).ack(mockCheckpointTuple);
        executor.execute(mockCheckpointTuple);
        Mockito.verify(mockBolt, Mockito.times(1)).preCommit(new Long(0));
        Mockito.verify(mockState, Mockito.times(1)).commit(new Long(0));
    }

    @Test
    public void testPrepareAndRollbackBeforeInitstate() throws Exception {
        Mockito.when(mockTuple.getSourceStreamId()).thenReturn("default");
        executor.execute(mockTuple);
        Mockito.when(mockCheckpointTuple.getSourceStreamId()).thenReturn(CheckpointSpout.CHECKPOINT_STREAM_ID);
        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(PREPARE);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(100));
        executor.execute(mockCheckpointTuple);
        Mockito.verify(mockOutputCollector, Mockito.times(1)).fail(mockCheckpointTuple);

        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(ROLLBACK);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(100));
        Mockito.doNothing().when(mockOutputCollector).ack(mockCheckpointTuple);
        executor.execute(mockCheckpointTuple);
        Mockito.verify(mockState, Mockito.times(1)).rollback();
    }

    @Test
    public void testCommitBeforeInitstate() throws Exception {
        Mockito.when(mockTuple.getSourceStreamId()).thenReturn("default");
        Mockito.when(mockCheckpointTuple.getSourceStreamId()).thenReturn(CheckpointSpout.CHECKPOINT_STREAM_ID);
        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(COMMIT);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(100));
        executor.execute(mockCheckpointTuple);
        Mockito.verify(mockOutputCollector, Mockito.times(1)).ack(mockCheckpointTuple);

        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(ROLLBACK);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(100));
        executor.execute(mockCheckpointTuple);
        Mockito.verify(mockState, Mockito.times(1)).rollback();
    }

    @Test
    public void testPrepareAndCommit() throws Exception {
        Mockito.when(mockTuple.getSourceStreamId()).thenReturn("default");
        Mockito.when(mockCheckpointTuple.getSourceStreamId()).thenReturn(CheckpointSpout.CHECKPOINT_STREAM_ID);
        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(INITSTATE);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(0));
        executor.execute(mockCheckpointTuple);

        executor.execute(mockTuple);
        Mockito.when(mockCheckpointTuple.getSourceStreamId()).thenReturn(CheckpointSpout.CHECKPOINT_STREAM_ID);
        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(PREPARE);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(100));
        executor.execute(mockCheckpointTuple);
        executor.execute(mockTuple);
        Mockito.when(mockCheckpointTuple.getValueByField(CHECKPOINT_FIELD_ACTION)).thenReturn(COMMIT);
        Mockito.when(mockCheckpointTuple.getLongByField(CHECKPOINT_FIELD_TXID)).thenReturn(new Long(100));
        executor.execute(mockCheckpointTuple);
        mockOutputCollector.ack(mockTuple);
        Mockito.verify(mockState, Mockito.times(1)).commit(new Long(100));
        Mockito.verify(mockBolt, Mockito.times(2)).execute(mockTuple);
        Mockito.verify(mockOutputCollector, Mockito.times(1)).ack(mockTuple);
    }
}

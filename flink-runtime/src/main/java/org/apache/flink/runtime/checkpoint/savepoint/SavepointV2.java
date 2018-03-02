/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.checkpoint.savepoint;

import org.apache.flink.runtime.checkpoint.MasterState;
import org.apache.flink.runtime.checkpoint.OperatorState;
import org.apache.flink.runtime.checkpoint.OperatorSubtaskState;
import org.apache.flink.runtime.checkpoint.SubtaskState;
import org.apache.flink.runtime.checkpoint.TaskState;
import org.apache.flink.runtime.executiongraph.ExecutionJobVertex;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.state.ChainedStateHandle;
import org.apache.flink.runtime.state.OperatorStateHandle;
import org.apache.flink.runtime.state.KeyedStateHandle;
import org.apache.flink.util.Preconditions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * The persistent checkpoint metadata, format version 2.
 * his format was introduced with Flink 1.3.0.
 */
public class SavepointV2 implements Savepoint {

	/** The savepoint version. */
	public static final int VERSION = 2;

	/** The checkpoint ID */
	private final long checkpointId;

	/**
	 * The task states 
	 * @deprecated Only kept for backwards-compatibility with versions < 1.3. Will be removed in the future. 
	 */
	@Deprecated
	private final Collection<TaskState> taskStates;

	/** The operator states */
	private final Collection<OperatorState> operatorStates;

	/** The states generated by the CheckpointCoordinator */
	private final Collection<MasterState> masterStates;

	/** @deprecated Only kept for backwards-compatibility with versions < 1.3. Will be removed in the future. */
	@Deprecated
	public SavepointV2(long checkpointId, Collection<TaskState> taskStates) {
		this(
			checkpointId, 
			null,
			checkNotNull(taskStates, "taskStates"),
			Collections.<MasterState>emptyList()
		);
	}

	public SavepointV2(long checkpointId, Collection<OperatorState> operatorStates, Collection<MasterState> masterStates) {
		this(
			checkpointId,
			checkNotNull(operatorStates, "operatorStates"),
			null,
			masterStates
		);
	}

	private SavepointV2(
			long checkpointId,
			Collection<OperatorState> operatorStates,
			Collection<TaskState> taskStates,
			Collection<MasterState> masterStates) {

		this.checkpointId = checkpointId;
		this.operatorStates = operatorStates;
		this.taskStates = taskStates;
		this.masterStates = checkNotNull(masterStates, "masterStates");
	}

	@Override
	public int getVersion() {
		return VERSION;
	}

	@Override
	public long getCheckpointId() {
		return checkpointId;
	}

	@Override
	public Collection<OperatorState> getOperatorStates() {
		return operatorStates;
	}

	@Override
	public Collection<TaskState> getTaskStates() {
		return taskStates;
	}

	@Override
	public Collection<MasterState> getMasterStates() {
		return masterStates;
	}

	@Override
	public void dispose() throws Exception {
		for (OperatorState operatorState : operatorStates) {
			operatorState.discardState();
		}
		operatorStates.clear();
		masterStates.clear();
	}

	@Override
	public String toString() {
		return "Checkpoint Metadata (version=" + VERSION + ')';
	}

	/**
	 * Converts the {@link Savepoint} containing {@link TaskState TaskStates} to an equivalent savepoint containing
	 * {@link OperatorState OperatorStates}.
	 *
	 * @param savepoint savepoint to convert
	 * @param tasks     map of all vertices and their job vertex ids
	 * @return converted completed checkpoint
	 * @deprecated Only kept for backwards-compatibility with versions < 1.3. Will be removed in the future.
	 * */
	@Deprecated
	public static Savepoint convertToOperatorStateSavepointV2(
			Map<JobVertexID, ExecutionJobVertex> tasks,
			Savepoint savepoint) {

		if (savepoint.getOperatorStates() != null) {
			return savepoint;
		}

		boolean expandedToLegacyIds = false;

		Map<OperatorID, OperatorState> operatorStates = new HashMap<>(savepoint.getTaskStates().size() << 1);

		for (TaskState taskState : savepoint.getTaskStates()) {
			ExecutionJobVertex jobVertex = tasks.get(taskState.getJobVertexID());

			// on the first time we can not find the execution job vertex for an id, we also consider alternative ids,
			// for example as generated from older flink versions, to provide backwards compatibility.
			if (jobVertex == null && !expandedToLegacyIds) {
				tasks = ExecutionJobVertex.includeLegacyJobVertexIDs(tasks);
				jobVertex = tasks.get(taskState.getJobVertexID());
				expandedToLegacyIds = true;
			}

			if (jobVertex == null) {
				throw new IllegalStateException(
					"Could not find task for state with ID " + taskState.getJobVertexID() + ". " +
					"When migrating a savepoint from a version < 1.3 please make sure that the topology was not " +
					"changed through removal of a stateful operator or modification of a chain containing a stateful " +
					"operator.");
			}

			List<OperatorID> operatorIDs = jobVertex.getOperatorIDs();

			Preconditions.checkArgument(
				jobVertex.getParallelism() == taskState.getParallelism(),
				"Detected change in parallelism during migration for task " + jobVertex.getJobVertexId() +"." +
					"When migrating a savepoint from a version < 1.3 please make sure that no changes were made " +
					"to the parallelism of stateful operators.");

			Preconditions.checkArgument(
				operatorIDs.size() == taskState.getChainLength(),
				"Detected change in chain length during migration for task " + jobVertex.getJobVertexId() +". " +
					"When migrating a savepoint from a version < 1.3 please make sure that the topology was not " +
					"changed by modification of a chain containing a stateful operator.");

			for (int subtaskIndex = 0; subtaskIndex < jobVertex.getParallelism(); subtaskIndex++) {
				SubtaskState subtaskState;
				try {
					subtaskState = taskState.getState(subtaskIndex);
				} catch (Exception e) {
					throw new IllegalStateException(
						"Could not find subtask with index " + subtaskIndex + " for task " + jobVertex.getJobVertexId() + ". " +
						"When migrating a savepoint from a version < 1.3 please make sure that no changes were made " +
						"to the parallelism of stateful operators.",
						e);
				}

				if (subtaskState == null) {
					continue;
				}

				ChainedStateHandle<OperatorStateHandle> partitioneableState =
					subtaskState.getManagedOperatorState();
				ChainedStateHandle<OperatorStateHandle> rawOperatorState =
					subtaskState.getRawOperatorState();

				for (int chainIndex = 0; chainIndex < taskState.getChainLength(); chainIndex++) {

					// task consists of multiple operators so we have to break the state apart
					for (int operatorIndex = 0; operatorIndex < operatorIDs.size(); operatorIndex++) {
						OperatorID operatorID = operatorIDs.get(operatorIndex);
						OperatorState operatorState = operatorStates.get(operatorID);

						if (operatorState == null) {
							operatorState = new OperatorState(
								operatorID,
								jobVertex.getParallelism(),
								jobVertex.getMaxParallelism());
							operatorStates.put(operatorID, operatorState);
						}

						KeyedStateHandle managedKeyedState = null;
						KeyedStateHandle rawKeyedState = null;

						// only the head operator retains the keyed state
						if (operatorIndex == operatorIDs.size() - 1) {
							managedKeyedState = subtaskState.getManagedKeyedState();
							rawKeyedState = subtaskState.getRawKeyedState();
						}

						OperatorSubtaskState operatorSubtaskState = new OperatorSubtaskState(
							partitioneableState != null ? partitioneableState.get(operatorIndex) : null,
							rawOperatorState != null ? rawOperatorState.get(operatorIndex) : null,
							managedKeyedState,
							rawKeyedState);

						operatorState.putState(subtaskIndex, operatorSubtaskState);
					}
				}
			}
		}

		return new SavepointV2(
			savepoint.getCheckpointId(),
			operatorStates.values(),
			savepoint.getMasterStates());
	}
}

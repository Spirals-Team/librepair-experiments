/*
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

package org.apache.flink.runtime.state;

import org.apache.flink.core.io.VersionedIOReadableWritable;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataOutputView;
import org.apache.flink.runtime.state.metainfo.StateMetaInfoSnapshot;
import org.apache.flink.runtime.state.metainfo.StateMetaInfoReader;
import org.apache.flink.runtime.state.metainfo.StateMetaInfoSnapshotReadersWriters;
import org.apache.flink.util.Preconditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.flink.runtime.state.metainfo.StateMetaInfoSnapshotReadersWriters.CURRENT_STATE_META_INFO_SNAPSHOT_VERSION;

/**
 * Serialization proxy for all meta data in operator state backends. In the future we might also requiresMigration the actual state
 * serialization logic here.
 */
public class OperatorBackendSerializationProxy extends VersionedIOReadableWritable {

	public static final int VERSION = 4;

	private List<StateMetaInfoSnapshot> operatorStateMetaInfoSnapshots;
	private List<StateMetaInfoSnapshot> broadcastStateMetaInfoSnapshots;
	private ClassLoader userCodeClassLoader;

	public OperatorBackendSerializationProxy(ClassLoader userCodeClassLoader) {
		this.userCodeClassLoader = Preconditions.checkNotNull(userCodeClassLoader);
	}

	public OperatorBackendSerializationProxy(
			List<StateMetaInfoSnapshot> operatorStateMetaInfoSnapshots,
			List<StateMetaInfoSnapshot> broadcastStateMetaInfoSnapshots) {

		this.operatorStateMetaInfoSnapshots = Preconditions.checkNotNull(operatorStateMetaInfoSnapshots);
		this.broadcastStateMetaInfoSnapshots = Preconditions.checkNotNull(broadcastStateMetaInfoSnapshots);
		Preconditions.checkArgument(
				operatorStateMetaInfoSnapshots.size() <= Short.MAX_VALUE &&
						broadcastStateMetaInfoSnapshots.size() <= Short.MAX_VALUE
		);
	}

	@Override
	public int getVersion() {
		return VERSION;
	}

	@Override
	public int[] getCompatibleVersions() {
		return new int[] {VERSION, 3, 2, 1};
	}

	@Override
	public void write(DataOutputView out) throws IOException {
		super.write(out);
		writeStateMetaInfoSnapshots(operatorStateMetaInfoSnapshots, out);
		writeStateMetaInfoSnapshots(broadcastStateMetaInfoSnapshots, out);
	}

	private void writeStateMetaInfoSnapshots(
		List<StateMetaInfoSnapshot> snapshots,
		DataOutputView out) throws IOException {
		out.writeShort(snapshots.size());
		for (StateMetaInfoSnapshot state : snapshots) {
			StateMetaInfoSnapshotReadersWriters.getWriter().writeStateMetaInfoSnapshot(state, out);
		}
	}

	@Override
	public void read(DataInputView in) throws IOException {
		super.read(in);

		final int proxyReadVersion = getReadVersion();
		final int metaInfoReadVersion = proxyReadVersion > 3 ?
			CURRENT_STATE_META_INFO_SNAPSHOT_VERSION : proxyReadVersion;

		final StateMetaInfoReader stateMetaInfoReader = StateMetaInfoSnapshotReadersWriters.getReader(
			metaInfoReadVersion,
			StateMetaInfoSnapshotReadersWriters.StateTypeHint.OPERATOR_STATE);

		int numOperatorStates = in.readShort();
		operatorStateMetaInfoSnapshots = new ArrayList<>(numOperatorStates);
		for (int i = 0; i < numOperatorStates; i++) {
			operatorStateMetaInfoSnapshots.add(
				stateMetaInfoReader.readStateMetaInfoSnapshot(in, userCodeClassLoader));
		}

		if (proxyReadVersion >= 3) {
			// broadcast states did not exist prior to version 3
			int numBroadcastStates = in.readShort();
			broadcastStateMetaInfoSnapshots = new ArrayList<>(numBroadcastStates);
			for (int i = 0; i < numBroadcastStates; i++) {
				broadcastStateMetaInfoSnapshots.add(
					stateMetaInfoReader.readStateMetaInfoSnapshot(in, userCodeClassLoader));
			}
		} else {
			broadcastStateMetaInfoSnapshots = new ArrayList<>();
		}
	}

	public List<StateMetaInfoSnapshot> getOperatorStateMetaInfoSnapshots() {
		return operatorStateMetaInfoSnapshots;
	}

	public List<StateMetaInfoSnapshot> getBroadcastStateMetaInfoSnapshots() {
		return broadcastStateMetaInfoSnapshots;
	}
}

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

package org.apache.flink.streaming.connectors.fs.bucketing_new;

import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.ResumableWriter;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.util.Preconditions;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Javadoc.
 */
public class BucketStateSerializer implements SimpleVersionedSerializer<Bucket.BucketState> {

	private static final int MAGIC_NUMBER = 0x1e764b79;

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private final SimpleVersionedSerializer<ResumableWriter.Resumable> serializer;

	public BucketStateSerializer(SimpleVersionedSerializer<ResumableWriter.Resumable> serializer) {
		this.serializer = Preconditions.checkNotNull(serializer);
	}

	@Override
	public int getVersion() {
		return 1;
	}

	@Override
	public byte[] serialize(Bucket.BucketState state) throws IOException {
		int sizeInBytes = 0;

		// serializing the path
		byte[] serializedPath = state.getBucketPath().getPath().getBytes(CHARSET);
		sizeInBytes += Integer.BYTES + serializedPath.length;

		// serializing the current resumable
		ResumableWriter.Resumable current = state.getCurrent();
		byte[] currentResumable = null;
		if (state.getCurrent() != null) {
			final byte[] serResumable = serializer.serialize(current);
			currentResumable = serResumable;
			sizeInBytes += 1 + Integer.BYTES + serResumable.length; // 1 for the flag if the current is not null and the rest for the actual resumable and its size
		} else {
			sizeInBytes += 1; // this will be to mark non-existences
		}

		// serializing the resumables per checkpoint
		Map<Long, List<byte[]>> serializedResumablesPerCheckpoint =
				new HashMap<>(state.getPendingPerCheckpoint().size());

		sizeInBytes += Integer.BYTES; // for the size of the map

		for (Map.Entry<Long, List<ResumableWriter.Resumable>> entry: state.getPendingPerCheckpoint().entrySet()) {
			final List<ResumableWriter.Resumable> resumables = entry.getValue();

			if (!resumables.isEmpty()) {
				long checkpointId = entry.getKey();
				sizeInBytes += Long.BYTES + Integer.BYTES; // for the checkpointId and the size of the list

				List<byte[]> serializedResumables = new ArrayList<>(resumables.size());
				for (ResumableWriter.Resumable resumable : resumables) {
					byte[] serResumable = serializer.serialize(resumable);
					serializedResumables.add(serResumable);
					sizeInBytes += Integer.BYTES + serResumable.length; // for the actual resumable and its size
				}
				serializedResumablesPerCheckpoint.put(checkpointId, serializedResumables);
			}
		}

		final byte[] targetBytes = new byte[Integer.BYTES + sizeInBytes]; // the 4 bytes are for the MAGIC NUMBER (marker)
		final ByteBuffer bb = ByteBuffer.wrap(targetBytes).order(ByteOrder.LITTLE_ENDIAN);

		bb.putInt(MAGIC_NUMBER);

		// put the path
		bb.putInt(serializedPath.length);
		bb.put(serializedPath);

		// put the current open part file
		if (currentResumable != null) {
			bb.put((byte) 1);
			bb.putInt(currentResumable.length);
			bb.put(currentResumable);
		} else {
			bb.put((byte) 0);
		}

		// put the map of pending files per checkpoint
		bb.putInt(state.getPendingPerCheckpoint().size());
		for (Map.Entry<Long, List<byte[]>> resumablesPerCheckpoint: serializedResumablesPerCheckpoint.entrySet()) {
			long checkpointId = resumablesPerCheckpoint.getKey();
			List<byte[]> resumables = resumablesPerCheckpoint.getValue();

			bb.putLong(checkpointId);
			bb.putInt(resumables.size());

			for (byte[] res: resumables) {
				bb.putInt(res.length);
				bb.put(res);
			}
		}
		return targetBytes;
	}

	@Override
	public Bucket.BucketState deserialize(int version, byte[] serialized) throws IOException {
		switch (version) {
			case 1:
				return deserializeV1(serialized);
			default:
				throw new IOException("Unrecognized version or corrupt state: " + version);
		}
	}

	private Bucket.BucketState deserializeV1(byte[] serialized) throws IOException {
		final ByteBuffer bb = ByteBuffer.wrap(serialized).order(ByteOrder.LITTLE_ENDIAN);

		if (bb.getInt() != MAGIC_NUMBER) {
			throw new IOException("Corrupt data: Unexpected magic number.");
		}

		// first get the path
		final byte[] pathBytes = new byte[bb.getInt()];
		bb.get(pathBytes);
		final String bucketPathStr = new String(pathBytes, CHARSET);
		final Path bucketPath = new Path(bucketPathStr);

		// then get the current resumable stream
		ResumableWriter.Resumable current = null;
		if (bb.get() == 1) {
			final byte[] currentResumableBytes = new byte[bb.getInt()];
			bb.get(currentResumableBytes);

			int version = serializer.getVersion();
			current = serializer.deserialize(version, currentResumableBytes); // TODO: 7/2/18 how do the versions work???
		}

		int mapSize = bb.getInt();
		Map<Long, List<ResumableWriter.Resumable>> resumablesPerCheckpoint = new HashMap<>(mapSize);
		for (int i = 0; i < mapSize; i++) {
			long checkpointId = bb.getLong();
			int noOfResumables = bb.getInt();

			List<ResumableWriter.Resumable> resumables = new ArrayList<>(noOfResumables);
			for (int j = 0; j < noOfResumables; j++) {
				final byte[] currentResumableBytes = new byte[bb.getInt()];
				bb.get(currentResumableBytes);

				int version = serializer.getVersion();
				current = serializer.deserialize(version, currentResumableBytes);
				resumables.add(current);
			}
			resumablesPerCheckpoint.put(checkpointId, resumables);
		}
		return new Bucket.BucketState(bucketPath, current, resumablesPerCheckpoint);
	}
}
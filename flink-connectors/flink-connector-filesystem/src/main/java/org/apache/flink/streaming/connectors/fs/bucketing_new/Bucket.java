package org.apache.flink.streaming.connectors.fs.bucketing_new;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.ResumableFsDataOutputStream;
import org.apache.flink.core.fs.ResumableWriter;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.connectors.fs.bucketing_new.writers.Writer;
import org.apache.flink.util.Preconditions;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Bucket<IN> {

	private static final String PART_PREFIX = "part";

	private final FileSystem fileSystem;

	private final Path bucketPath;

	private int subtaskIndex;

	private long partCounter;

	private long creationTime;

	private long lastWrittenTime;

	private final long maxPathSize;

	private final long rolloverTime;

	private final long inactivityTime;

	private final Writer<IN> writer;

	private ResumableFsDataOutputStream currentPartStream;

	private List<ResumableWriter.Resumable> pending = new ArrayList<>();

	private Map<Long, List<ResumableWriter.Resumable>> pendingPerCheckpoint = new HashMap<>();

	public Bucket(FileSystem fileSystem,
				  int subtaskIndex,
				  Path bucketPath,
				  long initialPartCounter,
				  long maxPartSize,
				  long rolloverTime,
				  long inactivityTime,
				  Writer<IN> writer,
				  BucketState bucketstate) throws IOException {

		this(fileSystem, subtaskIndex, bucketPath, initialPartCounter, maxPartSize, rolloverTime, inactivityTime, writer);

		final ResumableWriter.Resumable resumable = bucketstate.getCurrent();
		final ResumableWriter fsWriter = fileSystem.createResumableWriter();

		this.currentPartStream = fsWriter.resume(resumable);
		this.pendingPerCheckpoint = bucketstate.getPendingPerCheckpoint();
	}
	
	public Bucket(
			FileSystem fileSystem,
			int subtaskIndex,
			Path bucketPath,
			long initialPartCounter,
			long batchSize,
			long rolloverTime,
			long inactivityTime,
			Writer<IN> writer) {

		this.fileSystem = Preconditions.checkNotNull(fileSystem);
		this.subtaskIndex = subtaskIndex;
		this.bucketPath = Preconditions.checkNotNull(bucketPath);
		this.partCounter = initialPartCounter;
		this.maxPathSize = batchSize;
		this.rolloverTime = rolloverTime;
		this.inactivityTime = inactivityTime;
		this.writer = Preconditions.checkNotNull(writer);
	}

	public long getPartCounter() {
		return partCounter;
	}

	public boolean isActive() {
		return isOpen() || !pending.isEmpty() || !pendingPerCheckpoint.isEmpty();
	}

	public boolean isOpen() {
		return currentPartStream != null;
	}

	public void write(IN element, long currentTime) throws IOException {
		if (shouldRoll(currentTime)) {
			startNewChunk(currentTime);
		}
		Preconditions.checkState(isOpen(), "Bucket is not open.");

		writer.write(element, currentPartStream);
		lastWrittenTime = currentTime;
	}

	private void startNewChunk(final long currentTime) throws IOException {
		if (isOpen()) {
			closeCurrentChunk();
			pending.add(currentPartStream.persist());
		}

		this.currentPartStream = fileSystem.createResumableWriter().open(getNewPartPath());
		this.creationTime = currentTime;
		this.partCounter++;
	}

	private boolean shouldRoll(long currentTime) throws IOException {
		if (!isOpen()) {
			return true;
		}

		long writePosition = currentPartStream.getPos();
		if (writePosition > maxPathSize) {
			return true;
		}

		return (currentTime - creationTime > rolloverTime);
	}

	public void closeCurrentChunk() throws IOException {
		currentPartStream.close();
		pending.add(currentPartStream.persist());
		currentPartStream = null;
	}

	public void rollByTime(long currentTime) throws IOException {
		if (currentTime - creationTime > rolloverTime ||
				currentTime - lastWrittenTime > inactivityTime) {
			closeCurrentChunk();
		}
	}

	public void commitUpToCheckpoint(long checkpointId) throws IOException {
		Preconditions.checkNotNull(fileSystem);

		Iterator<Map.Entry<Long, List<ResumableWriter.Resumable>>> it =
				pendingPerCheckpoint.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<Long, List<ResumableWriter.Resumable>> entry = it.next();
			if (entry.getKey() <= checkpointId) {
				for (ResumableWriter.Resumable resumable : entry.getValue()) {
					final ResumableWriter fsWriter = fileSystem.createResumableWriter();
					fsWriter.resume(resumable).closeAndPublish();
				}
				it.remove();
			}
		}
	}

	public BucketState snapshot(long checkpointId) throws IOException {
		ResumableWriter.Resumable resumable = null;
		if (currentPartStream != null) {
			resumable = currentPartStream.persist();
		}
		pendingPerCheckpoint.put(checkpointId, pending);
		pending = new ArrayList<>();
		return new BucketState(bucketPath, resumable, pendingPerCheckpoint);
	}

	private Path getNewPartPath() {
		return new Path(bucketPath, PART_PREFIX + "-" + subtaskIndex + "-" + partCounter);
	}

	public static class BucketState {

		private final Path bucketPath;

		private final ResumableWriter.Resumable current;

		private final Map<Long, List<ResumableWriter.Resumable>> pendingPerCheckpoint;

		public BucketState(
				final Path bucketPath,
				final ResumableWriter.Resumable current,
				final Map<Long, List<ResumableWriter.Resumable>> pendingPerCheckpoint
		) {
			this.bucketPath = Preconditions.checkNotNull(bucketPath);
			this.current = current;
			this.pendingPerCheckpoint = Preconditions.checkNotNull(pendingPerCheckpoint);
		}

		public Path getBucketPath() {
			return bucketPath;
		}

		public ResumableWriter.Resumable getCurrent() {
			return current;
		}

		public Map<Long, List<ResumableWriter.Resumable>> getPendingPerCheckpoint() {
			return pendingPerCheckpoint;
		}
	}

	public static class BucketStateSerializer implements SimpleVersionedSerializer<BucketState> {

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
		public byte[] serialize(BucketState state) throws IOException {
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
		public BucketState deserialize(int version, byte[] serialized) throws IOException {
			switch (version) {
				case 1:
					return deserializeV1(serialized);
				default:
					throw new IOException("Unrecognized version or corrupt state: " + version);
			}
		}

		private BucketState deserializeV1(byte[] serialized) throws IOException {
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
			return new BucketState(bucketPath, current, resumablesPerCheckpoint);
		}
	}
}

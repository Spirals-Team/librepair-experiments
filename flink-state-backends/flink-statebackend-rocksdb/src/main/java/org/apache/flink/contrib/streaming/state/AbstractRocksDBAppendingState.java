package org.apache.flink.contrib.streaming.state;

import org.apache.flink.api.common.state.State;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.core.memory.ByteArrayInputStreamWithPos;
import org.apache.flink.core.memory.DataInputViewStreamWrapper;
import org.apache.flink.core.memory.DataOutputViewStreamWrapper;
import org.apache.flink.runtime.state.internal.InternalAppendingState;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDBException;

import java.io.IOException;

abstract class AbstractRocksDBAppendingState <K, N, IN, SV, OUT, S extends State>
	extends AbstractRocksDBState<K, N, SV, S>
	implements InternalAppendingState<K, N, IN, SV, OUT> {

	/**
	 * Creates a new RocksDB backed state.
	 *
	 * @param columnFamily        The RocksDB column family that this state is associated to.
	 * @param namespaceSerializer The serializer for the namespace.
	 * @param valueSerializer     The serializer for the state.
	 * @param defaultValue        The default value for the state.
	 * @param backend             The backend for which this state is bind to.
	 */
	protected AbstractRocksDBAppendingState(
		ColumnFamilyHandle columnFamily,
		TypeSerializer<N> namespaceSerializer,
		TypeSerializer<SV> valueSerializer,
		SV defaultValue,
		RocksDBKeyedStateBackend<K> backend) {
		super(columnFamily, namespaceSerializer, valueSerializer, defaultValue, backend);
	}

	@Override
	public SV getInternal() throws IOException {
		try {
			writeCurrentKeyWithGroupAndNamespace();
			byte[] key = keySerializationStream.toByteArray();
			byte[] valueBytes = backend.db.get(columnFamily, key);
			if (valueBytes == null) {
				return null;
			}
			return valueSerializer.deserialize(new DataInputViewStreamWrapper(new ByteArrayInputStreamWithPos(valueBytes)));
		} catch (IOException | RocksDBException e) {
			throw new IOException("Error while retrieving data from RocksDB", e);
		}
	}

	@Override
	public void updateInternal(SV valueToStore) throws IOException {
		try {
			// prepare the current key and namespace for RocksDB lookup
			writeCurrentKeyWithGroupAndNamespace();
			final byte[] key = keySerializationStream.toByteArray();
			keySerializationStream.reset();

			// serialize new value
			final DataOutputViewStreamWrapper out = new DataOutputViewStreamWrapper(keySerializationStream);
			valueSerializer.serialize(valueToStore, out);

			// write the new value to RocksDB
			backend.db.put(columnFamily, writeOptions, key, keySerializationStream.toByteArray());
		}
		catch (IOException | RocksDBException e) {
			throw new IOException("Error while adding value to RocksDB", e);
		}
	}
}

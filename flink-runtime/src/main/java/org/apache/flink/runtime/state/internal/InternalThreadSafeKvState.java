package org.apache.flink.runtime.state.internal;

import org.apache.flink.annotation.VisibleForTesting;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.runtime.query.KvStateInfo;
import org.apache.flink.util.Preconditions;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class InternalThreadSafeKvState<K, N, V> implements InternalKvState<N> {

	private final KvStateInfo<K, N, V> stateInfo;
	private final boolean areSerializersStateless;

	private final ConcurrentMap<Thread, KvStateInfo<K, N, V>> serializerCache = new ConcurrentHashMap<>(4);

	public InternalThreadSafeKvState(
			final TypeSerializer<K> keySerializer,
			final TypeSerializer<N> namespaceSerializer,
			final TypeSerializer<V> valueSerializer
	) {
		this.stateInfo = new KvStateInfo<>(
				Preconditions.checkNotNull(keySerializer),
				Preconditions.checkNotNull(namespaceSerializer),
				Preconditions.checkNotNull(valueSerializer)
		);
		this.areSerializersStateless = stateInfo == stateInfo.duplicate();
	}

	public KvStateInfo<K, N, V> getInfoForCurrentThread() {
		return areSerializersStateless
				? stateInfo
				: serializerCache.computeIfAbsent(Thread.currentThread(), t -> stateInfo.duplicate());
	}

	/**
	 * Returns the serialized value for the given key and namespace.
	 *
	 * <p>If no value is associated with key and namespace, <code>null</code>
	 * is returned.
	 *
	 * <p><b>TO IMPLEMENTERS:</b> This method is called by multiple threads. Anything
	 * stateful (e.g. serializers) should be either duplicated or protected from undesired
	 * consequences of concurrent invocations.
	 *
	 * @param serializedKeyAndNamespace Serialized key and namespace
	 * @return Serialized value or <code>null</code> if no value is associated with the key and namespace.
	 *
	 * @throws Exception Exceptions during serialization are forwarded
	 */
	public abstract byte[] getSerializedValue(byte[] serializedKeyAndNamespace) throws Exception;

	public void clearCache() {
		serializerCache.clear();
	}

	@VisibleForTesting
	public int getCacheSize() {
		return serializerCache.size();
	}

}

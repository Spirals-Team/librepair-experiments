package org.apache.flink.runtime.state.heap;

import org.apache.flink.api.common.state.AppendingState;
import org.apache.flink.api.common.state.State;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.runtime.state.internal.InternalAppendingState;

/**
 * Base class for {@link AppendingState} ({@link InternalAppendingState}) that is stored on the heap.
 *
 * @param <K> The type of the key.
 * @param <N> The type of the namespace.
 * @param <IN> The type of the input elements.
 * @param <SV> The type of the values in the state.
 * @param <OUT> The type of the output elements.
 * @param <S> The type of State
 */
public abstract class AbstractHeapAppendingState<K, N, IN, SV, OUT, S extends State>
	extends AbstractHeapState<K, N, SV, S>
	implements InternalAppendingState<K, N, IN, SV, OUT> {
	/**
	 * Creates a new key/value state for the given hash map of key/value pairs.
	 *
	 * @param stateTable          The state table for which this state is associated to.
	 * @param keySerializer       The serializer for the keys.
	 * @param valueSerializer     The serializer for the state.
	 * @param namespaceSerializer The serializer for the namespace.
	 * @param defaultValue        The default value for the state.
	 */
	protected AbstractHeapAppendingState(
		StateTable<K, N, SV> stateTable,
		TypeSerializer<K> keySerializer,
		TypeSerializer<SV> valueSerializer,
		TypeSerializer<N> namespaceSerializer,
		SV defaultValue) {
		super(stateTable, keySerializer, valueSerializer, namespaceSerializer, defaultValue);
	}

	@Override
	public SV getInternal() {
		return stateTable.get(currentNamespace);
	}

	@Override
	public void updateInternal(SV valueToStore) {
		stateTable.put(currentNamespace, valueToStore);
	}
}

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

package org.apache.flink.cep.nfa;

import org.apache.flink.api.common.typeutils.CompatibilityResult;
import org.apache.flink.api.common.typeutils.CompatibilityUtil;
import org.apache.flink.api.common.typeutils.CompositeTypeSerializerConfigSnapshot;
import org.apache.flink.api.common.typeutils.TypeDeserializerAdapter;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.TypeSerializerConfigSnapshot;
import org.apache.flink.api.common.typeutils.UnloadableDummyTypeSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.cep.nfa.compiler.NFAStateNameHandler;
import org.apache.flink.cep.nfa.sharedbuffer.EventWrapper;
import org.apache.flink.cep.nfa.sharedbuffer.NodeId;
import org.apache.flink.cep.nfa.sharedbuffer.SharedBufferEdge;
import org.apache.flink.cep.nfa.sharedbuffer.SharedBufferNode;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.DataOutputView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @deprecated everything in this class is deprecated. Those are only migration procedures from older versions.
 */
@Deprecated
public class SharedBuffer<V> {

	private final Map<Tuple2<String, ValueTimeWrapper<V>>, NodeId> mappingContext;
	private Map<Long, EventWrapper<V>> eventsBuffer;
	private Map<NodeId, SharedBufferNode> pages;

	public Map<Long, EventWrapper<V>> getEventsBuffer() {
		return eventsBuffer;
	}

	public Map<NodeId, SharedBufferNode> getPages() {
		return pages;
	}

	public SharedBuffer(Map<Long, EventWrapper<V>> eventsBuffer,
		Map<NodeId, SharedBufferNode> pages,
		Map<Tuple2<String, ValueTimeWrapper<V>>, NodeId> mappingContext) {
		this.eventsBuffer = eventsBuffer;
		this.pages = pages;
		this.mappingContext = mappingContext;
	}

	public NodeId getNodeId(String prevState, long timestamp, int counter, V event) {
		return mappingContext.get(Tuple2.of(NFAStateNameHandler.getOriginalNameFromInternal(prevState),
			new ValueTimeWrapper<>(event, timestamp, counter)));
	}

	/**
	 * Wrapper for a value-timestamp pair.
	 *
	 * @param <V> Type of the value
	 */
	private static class ValueTimeWrapper<V> {

		private final V value;
		private final long timestamp;
		private final int counter;

		ValueTimeWrapper(final V value, final long timestamp, final int counter) {
			this.value = value;
			this.timestamp = timestamp;
			this.counter = counter;
		}

		/**
		 * Returns a counter used to disambiguate between different accepted
		 * elements with the same value and timestamp that refer to the same
		 * looping state.
		 */
		public int getCounter() {
			return counter;
		}

		public V getValue() {
			return value;
		}

		public long getTimestamp() {
			return timestamp;
		}

		@Override
		public String toString() {
			return "ValueTimeWrapper(" + value + ", " + timestamp + ", " + counter + ")";
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ValueTimeWrapper)) {
				return false;
			}

			@SuppressWarnings("unchecked")
			ValueTimeWrapper<V> other = (ValueTimeWrapper<V>) obj;

			return timestamp == other.getTimestamp()
				&& Objects.equals(value, other.getValue())
				&& counter == other.getCounter();
		}

		@Override
		public int hashCode() {
			return (int) (31 * (31 * (timestamp ^ timestamp >>> 32) + value.hashCode()) + counter);
		}

		public static <V> ValueTimeWrapper<V> deserialize(
			final TypeSerializer<V> valueSerializer,
			final DataInputView source) throws IOException {

			final V value = valueSerializer.deserialize(source);
			final long timestamp = source.readLong();
			final int counter = source.readInt();

			return new ValueTimeWrapper<>(value, timestamp, counter);
		}
	}

	/**
	 * The {@link TypeSerializerConfigSnapshot} serializer configuration to be stored with the managed state.
	 */
	public static final class SharedBufferSerializerConfigSnapshot<K, V> extends CompositeTypeSerializerConfigSnapshot {

		private static final int VERSION = 1;

		/** This empty constructor is required for deserializing the configuration. */
		public SharedBufferSerializerConfigSnapshot() {
		}

		public SharedBufferSerializerConfigSnapshot(
			final TypeSerializer<K> keySerializer,
			final TypeSerializer<V> valueSerializer,
			final TypeSerializer<DeweyNumber> versionSerializer) {

			super(keySerializer, valueSerializer, versionSerializer);
		}

		@Override
		public int getVersion() {
			return VERSION;
		}
	}

	/**
	 * A {@link TypeSerializer} for the {@link SharedBuffer}.
	 */
	public static class SharedBufferSerializer<K, V> extends TypeSerializer<SharedBuffer<V>> {

		private static final long serialVersionUID = -3254176794680331560L;

		private final TypeSerializer<K> keySerializer;
		private final TypeSerializer<V> valueSerializer;
		private final TypeSerializer<DeweyNumber> versionSerializer;

		public SharedBufferSerializer(
			final TypeSerializer<K> keySerializer,
			final TypeSerializer<V> valueSerializer) {
			this(keySerializer, valueSerializer, new DeweyNumber.DeweyNumberSerializer());
		}

		public SharedBufferSerializer(
			final TypeSerializer<K> keySerializer,
			final TypeSerializer<V> valueSerializer,
			final TypeSerializer<DeweyNumber> versionSerializer) {

			this.keySerializer = keySerializer;
			this.valueSerializer = valueSerializer;
			this.versionSerializer = versionSerializer;
		}

		public TypeSerializer<DeweyNumber> getVersionSerializer() {
			return versionSerializer;
		}

		public TypeSerializer<K> getKeySerializer() {
			return keySerializer;
		}

		public TypeSerializer<V> getValueSerializer() {
			return valueSerializer;
		}

		@Override
		public boolean isImmutableType() {
			return false;
		}

		@Override
		public SharedBufferSerializer<K, V> duplicate() {
			return new SharedBufferSerializer<>(keySerializer.duplicate(), valueSerializer.duplicate());
		}

		@Override
		public SharedBuffer<V> createInstance() {
			throw new UnsupportedOperationException();
		}

		@Override
		public SharedBuffer<V> copy(SharedBuffer<V> from) {
			throw new UnsupportedOperationException();
		}

		@Override
		public SharedBuffer<V> copy(
			SharedBuffer<V> from,
			SharedBuffer<V> reuse) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getLength() {
			return -1;
		}

		@Override
		public void serialize(SharedBuffer<V> record, DataOutputView target) throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public SharedBuffer<V> deserialize(DataInputView source) throws IOException {
//			List<SharedBufferEntry<K, V>> entryList = new ArrayList<>();
//			Map<K, SharedBufferPage<K, V>> pages = new HashMap<>();

			List<Tuple2<NodeId, SharedBufferNode>> entries = new ArrayList<>();
			Map<ValueTimeWrapper<V>, Long> values = new HashMap<>();
			Map<Long, EventWrapper<V>> valuesWithIds = new HashMap<>();
			Map<Tuple2<String, ValueTimeWrapper<V>>, NodeId> mappingContext = new HashMap<>();
			long totalEvents = 0;
			int totalPages = source.readInt();

			for (int i = 0; i < totalPages; i++) {
				// key of the page
				K stateName = keySerializer.deserialize(source);

				int numberEntries = source.readInt();
				for (int j = 0; j < numberEntries; j++) {
					ValueTimeWrapper<V> wrapper = ValueTimeWrapper.deserialize(valueSerializer, source);
					Long eventId = values.get(wrapper);
					if (eventId == null) {
						eventId = totalEvents;
						values.put(wrapper, eventId);
						valuesWithIds.put(eventId, new EventWrapper<>(wrapper.value, 1));
						totalEvents += 1;
					} else {
						EventWrapper<V> eventWrapper = valuesWithIds.get(eventId);
						eventWrapper.getLock().lock();
					}

					NodeId nodeId = new NodeId(eventId, wrapper.timestamp, (String) stateName);
					int refCount = source.readInt();

					entries.add(Tuple2.of(nodeId, new SharedBufferNode(refCount)));
					mappingContext.put(Tuple2.of((String) stateName, wrapper), nodeId);
				}
			}

			// read the edges of the shared buffer entries
			int totalEdges = source.readInt();

			for (int j = 0; j < totalEdges; j++) {
				int sourceIdx = source.readInt();

				int targetIdx = source.readInt();

				DeweyNumber version = versionSerializer.deserialize(source);

				// We've already deserialized the shared buffer entry. Simply read its ID and
				// retrieve the buffer entry from the list of entries
				Tuple2<NodeId, SharedBufferNode> sourceEntry = entries.get(sourceIdx);
				Tuple2<NodeId, SharedBufferNode> targetEntry =
					targetIdx < 0 ? Tuple2.of(null, null) : entries.get(targetIdx);
				sourceEntry.f1.addEdge(new SharedBufferEdge(targetEntry.f0, version));
			}

			Map<NodeId, SharedBufferNode> entriesMap = entries.stream().collect(Collectors.toMap(e -> e.f0, e -> e.f1));

			return new SharedBuffer<>(valuesWithIds, entriesMap, mappingContext);
		}

		@Override
		public SharedBuffer<V> deserialize(SharedBuffer<V> reuse, DataInputView source) throws IOException {
			return deserialize(source);
		}

		@Override
		public void copy(DataInputView source, DataOutputView target) throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}

			if (obj == null || !Objects.equals(obj.getClass(), getClass())) {
				return false;
			}

			SharedBufferSerializer other = (SharedBufferSerializer) obj;
			return
				Objects.equals(keySerializer, other.getKeySerializer()) &&
					Objects.equals(valueSerializer, other.getValueSerializer()) &&
					Objects.equals(versionSerializer, other.getVersionSerializer());
		}

		@Override
		public boolean canEqual(Object obj) {
			return true;
		}

		@Override
		public int hashCode() {
			return 37 * keySerializer.hashCode() + valueSerializer.hashCode();
		}

		@Override
		public TypeSerializerConfigSnapshot snapshotConfiguration() {
			return new SharedBufferSerializerConfigSnapshot<>(
				keySerializer,
				valueSerializer,
				versionSerializer);
		}

		@Override
		public CompatibilityResult<SharedBuffer<V>> ensureCompatibility(TypeSerializerConfigSnapshot configSnapshot) {
			if (configSnapshot instanceof SharedBufferSerializerConfigSnapshot) {
				List<Tuple2<TypeSerializer<?>, TypeSerializerConfigSnapshot>> serializerConfigSnapshots =
					((SharedBufferSerializerConfigSnapshot) configSnapshot).getNestedSerializersAndConfigs();

				CompatibilityResult<K> keyCompatResult = CompatibilityUtil.resolveCompatibilityResult(
					serializerConfigSnapshots.get(0).f0,
					UnloadableDummyTypeSerializer.class,
					serializerConfigSnapshots.get(0).f1,
					keySerializer);

				CompatibilityResult<V> valueCompatResult = CompatibilityUtil.resolveCompatibilityResult(
					serializerConfigSnapshots.get(1).f0,
					UnloadableDummyTypeSerializer.class,
					serializerConfigSnapshots.get(1).f1,
					valueSerializer);

				CompatibilityResult<DeweyNumber> versionCompatResult = CompatibilityUtil.resolveCompatibilityResult(
					serializerConfigSnapshots.get(2).f0,
					UnloadableDummyTypeSerializer.class,
					serializerConfigSnapshots.get(2).f1,
					versionSerializer);

				if (!keyCompatResult.isRequiresMigration() && !valueCompatResult.isRequiresMigration() &&
					!versionCompatResult.isRequiresMigration()) {
					return CompatibilityResult.compatible();
				} else {
					if (keyCompatResult.getConvertDeserializer() != null
						&& valueCompatResult.getConvertDeserializer() != null
						&& versionCompatResult.getConvertDeserializer() != null) {
						return CompatibilityResult.requiresMigration(
							new SharedBufferSerializer<>(
								new TypeDeserializerAdapter<>(keyCompatResult.getConvertDeserializer()),
								new TypeDeserializerAdapter<>(valueCompatResult.getConvertDeserializer()),
								new TypeDeserializerAdapter<>(versionCompatResult.getConvertDeserializer())
							));
					}
				}
			}

			return CompatibilityResult.requiresMigration();
		}
	}
}

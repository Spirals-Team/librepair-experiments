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

package org.apache.flink.contrib.streaming.state;

import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.core.memory.ByteArrayInputStreamWithPos;
import org.apache.flink.core.memory.ByteArrayOutputStreamWithPos;
import org.apache.flink.core.memory.DataInputViewStreamWrapper;
import org.apache.flink.core.memory.DataOutputViewStreamWrapper;
import org.apache.flink.runtime.state.InternalPriorityQueue;
import org.apache.flink.runtime.state.heap.HeapPriorityQueueElement;
import org.apache.flink.util.CloseableIterator;
import org.apache.flink.util.FlinkRuntimeException;

import org.apache.flink.shaded.guava18.com.google.common.primitives.UnsignedBytes;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * A priority queue with set semantics, implemented on top of RocksDB. This uses a {@link TreeSet} to cache the bytes
 * of up to the first n elements from RocksDB in memory to reduce interaction with RocksDB, in particular seek
 * operations. Cache uses a simple write-through policy.
 *
 * @param <E> the type of the contained elements in the queue.
 */
public class RocksDBCachingPriorityQueueSet<E extends HeapPriorityQueueElement>
	implements InternalPriorityQueue<E>, HeapPriorityQueueElement {

	/** Serialized empty value to insert into RocksDB. */
	private static final byte[] DUMMY_BYTES = new byte[] {};

	/** Comparator for byte arrays. */
	private static final Comparator<byte[]> LEXICOGRAPHIC_BYTE_COMPARATOR = UnsignedBytes.lexicographicalComparator();

	/** The RocksDB instance that serves as store. */
	@Nonnull
	private final RocksDB db;

	/** Handle to the column family of the RocksDB instance in which the elements are stored. */
	@Nonnull
	private final ColumnFamilyHandle columnFamilyHandle;

	/**
	 * Serializer for the contained elements. The lexicographical order of the bytes of serialized objects must be
	 * aligned with their logical order.
	 */
	@Nonnull
	private final TypeSerializer<E> byteOrderProducingSerializer;

	/** Wrapper to batch all writes to RocksDB. */
	@Nonnull
	private final RocksDBWriteBatchWrapper batchWrapper;

	/** The key-group id in serialized form. */
	@Nonnull
	private final byte[] groupPrefixBytes;

	/** Output stream that helps to serialize elements. */
	@Nonnull
	private final ByteArrayOutputStreamWithPos outputStream;

	/** Output view that helps to serialize elements, must wrap the output stream. */
	@Nonnull
	private final DataOutputViewStreamWrapper outputView;

	@Nonnull
	private final ByteArrayInputStreamWithPos inputStream;

	@Nonnull
	private final DataInputViewStreamWrapper inputView;

	/** In memory cache that holds a partial view on the head of the RocksDB content. */
	@Nonnull
	private final TreeSet<byte[]> orderedCache;

	/** This holds the key that we use to seek to the first element in RocksDB, to improve seek/iterator performance. */
	@Nonnull
	private byte[] seekHint;

	/** Cache for the head element in de-serialized form. */
	@Nullable
	private E peekCache;

	/** The maximum number of elements contained in the ordered cache. */
	private final int maxCacheSize;

	/** This flag is true if there could be elements in the backend that are not in the cache (false positives ok). */
	private boolean storeOnlyElements;

	/** Index for management as a {@link HeapPriorityQueueElement}. */
	private int internalIndex;

	public RocksDBCachingPriorityQueueSet(
		@Nonnegative int keyGroupId,
		@Nonnegative int keyGroupPrefixBytes,
		@Nonnull RocksDB db,
		@Nonnull ColumnFamilyHandle columnFamilyHandle,
		@Nonnull TypeSerializer<E> byteOrderProducingSerializer,
		@Nonnull ByteArrayOutputStreamWithPos outputStream,
		@Nonnull ByteArrayInputStreamWithPos inputStream,
		@Nonnull RocksDBWriteBatchWrapper batchWrapper,
		@Nonnegative int maxCacheSize) {
		this.db = db;
		this.columnFamilyHandle = columnFamilyHandle;
		this.byteOrderProducingSerializer = byteOrderProducingSerializer;
		this.outputStream = outputStream;
		this.inputStream = inputStream;
		this.batchWrapper = batchWrapper;
		this.maxCacheSize = maxCacheSize;
		this.outputView = new DataOutputViewStreamWrapper(outputStream);
		this.inputView = new DataInputViewStreamWrapper(inputStream);
		this.groupPrefixBytes = createKeyGroupBytes(keyGroupId, keyGroupPrefixBytes);
		this.seekHint = groupPrefixBytes;
		this.orderedCache = new TreeSet<>(LEXICOGRAPHIC_BYTE_COMPARATOR);
		this.internalIndex = HeapPriorityQueueElement.NOT_CONTAINED;
		this.storeOnlyElements = true;
	}

	@Nullable
	@Override
	public E peek() {

		checkRefillCacheFromStore();

		if (peekCache != null) {
			return peekCache;
		} else if (orderedCache.isEmpty()) {
			return null;
		} else {
			peekCache = deserializeElement(orderedCache.first());
			return peekCache;
		}
	}

	@Nullable
	@Override
	public E poll() {

		checkRefillCacheFromStore();

		if (orderedCache.isEmpty()) {
			return null;
		}

		final byte[] firstBytes = orderedCache.pollFirst();

		assert firstBytes != null; // cannot happen, silence the warning

		// write-through sync
		try {
			batchWrapper.remove(columnFamilyHandle, firstBytes);
		} catch (RocksDBException e) {
			throw new FlinkRuntimeException(e);
		}

		if (orderedCache.isEmpty()) {
			seekHint = firstBytes;
		}

		if (peekCache != null) {
			E fromCache = peekCache;
			peekCache = null;
			return fromCache;
		} else {
			return deserializeElement(firstBytes);
		}
	}

	@Override
	public boolean add(@Nonnull E toAdd) {

		checkRefillCacheFromStore();

		final byte[] toAddBytes = serializeElement(toAdd);
		final byte[] oldHead = !orderedCache.isEmpty() ? orderedCache.first() : null;

		// write-through sync
		try {
			batchWrapper.put(columnFamilyHandle, toAddBytes, DUMMY_BYTES);
		} catch (RocksDBException e) {
			throw new FlinkRuntimeException(e);
		}

		final boolean cacheFull = isCacheFull();

		if ((!cacheFull && !storeOnlyElements) ||
			LEXICOGRAPHIC_BYTE_COMPARATOR.compare(toAddBytes, orderedCache.last()) < 0) {

			if (cacheFull) {
				// we drop the element with lowest priority from the cache
				orderedCache.pollLast();
				// the dropped element is now only in the store
				storeOnlyElements = true;
			}

			orderedCache.add(toAddBytes);

			if (oldHead != orderedCache.first()) {
				peekCache = null;
				return true;
			} else {
				return false;
			}

		} else {
			// we only added to the store
			storeOnlyElements = true;
			return false;
		}
	}

	@Override
	public boolean remove(@Nonnull E toRemove) {

		checkRefillCacheFromStore();

		if (orderedCache.isEmpty()) {
			return false;
		}

		final byte[] toRemoveBytes = serializeElement(toRemove);
		final byte[] oldHead = orderedCache.first();

		// write-through sync
		try {
			batchWrapper.remove(columnFamilyHandle, toRemoveBytes);
		} catch (RocksDBException e) {
			throw new FlinkRuntimeException(e);
		}

		orderedCache.remove(toRemoveBytes);

		if (orderedCache.isEmpty()) {
			seekHint = toRemoveBytes;
			peekCache = null;
			return true;
		}

		if (oldHead != orderedCache.first()) {
			peekCache = null;
			return true;
		}

		return false;
	}

	@Override
	public void addAll(@Nullable Collection<? extends E> toAdd) {

		if (toAdd == null) {
			return;
		}

		for (E element : toAdd) {
			add(element);
		}
	}

	@Override
	public boolean isEmpty() {
		checkRefillCacheFromStore();
		return orderedCache.isEmpty();
	}

	@Nonnull
	@Override
	public CloseableIterator<E> iterator() {
		return new DeserializingIteratorWrapper(orderedBytesIterator());
	}

	/**
	 * This implementation comes at a relatively high cost per invocation. It should not be called repeatedly when it is
	 * clear that the value did not change. Currently this is only truly used to realize certain higher-level tests.
	 */
	@Override
	public int size() {

		if (storeOnlyElements) {
			int count = 0;
			try (final RocksToJavaIteratorAdapter iterator = orderedBytesIterator()) {
				while (iterator.hasNext()) {
					iterator.next();
					++count;
				}
			}
			return count;
		} else {
			return orderedCache.size();
		}
	}

	@Override
	public int getInternalIndex() {
		return internalIndex;
	}

	@Override
	public void setInternalIndex(int newIndex) {
		this.internalIndex = newIndex;
	}

	private RocksToJavaIteratorAdapter orderedBytesIterator() {
		flushWriteBatch();
		return new RocksToJavaIteratorAdapter(
			new RocksIteratorWrapper(
				db.newIterator(columnFamilyHandle)));
	}

	/**
	 * Ensures that recent writes are flushed and reflect in the RocksDB instance.
	 */
	private void flushWriteBatch() {
		try {
			batchWrapper.flush();
		} catch (RocksDBException e) {
			throw new FlinkRuntimeException(e);
		}
	}

	private boolean isCacheFull() {
		return orderedCache.size() >= maxCacheSize;
	}

	private void checkRefillCacheFromStore() {
		if (storeOnlyElements && orderedCache.isEmpty()) {
			try (final RocksToJavaIteratorAdapter iterator = orderedBytesIterator()) {
				for (int i = maxCacheSize; --i >= 0 && iterator.hasNext();) {
					orderedCache.add(iterator.next());
				}
				storeOnlyElements = iterator.hasNext();
			} catch (Exception e) {
				throw new FlinkRuntimeException("Exception while refilling store from iterator.", e);
			}
		}
	}

	private static boolean isPrefixWith(byte[] bytes, byte[] prefixBytes) {
		for (int i = 0; i < prefixBytes.length; ++i) {
			if (bytes[i] != prefixBytes[i]) {
				return false;
			}
		}
		return true;
	}

	private byte[] createKeyGroupBytes(int keyGroupId, int numPrefixBytes) {

		outputStream.reset();

		try {
			RocksDBKeySerializationUtils.writeKeyGroup(keyGroupId, numPrefixBytes, outputView);
		} catch (IOException e) {
			throw new FlinkRuntimeException("Could not write key-group bytes.", e);
		}

		return outputStream.toByteArray();
	}

	private byte[] serializeElement(E element) {
		try {
			outputStream.reset();
			outputView.write(groupPrefixBytes);
			byteOrderProducingSerializer.serialize(element, outputView);
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new FlinkRuntimeException("Error while serializing the element.", e);
		}
	}

	private E deserializeElement(byte[] bytes) {
		try {
			inputStream.setBuffer(bytes, 0, bytes.length);
			inputView.skipBytes(groupPrefixBytes.length);
			return byteOrderProducingSerializer.deserialize(inputView);
		} catch (IOException e) {
			throw new FlinkRuntimeException("Error while deserializing the element.", e);
		}
	}

	private class DeserializingIteratorWrapper implements CloseableIterator<E> {

		@Nonnull
		private final CloseableIterator<byte[]> bytesIterator;

		private DeserializingIteratorWrapper(@Nonnull CloseableIterator<byte[]> bytesIterator) {
			this.bytesIterator = bytesIterator;
		}

		@Override
		public void close() throws Exception {
			bytesIterator.close();
		}

		@Override
		public boolean hasNext() {
			return bytesIterator.hasNext();
		}

		@Override
		public E next() {
			return deserializeElement(bytesIterator.next());
		}
	}

	/**
	 * Adapter between RocksDB iterator and Java iterator. This is also closeable to release the native resources after
	 * use.
	 */
	private class RocksToJavaIteratorAdapter implements CloseableIterator<byte[]> {

		/** The RocksDb iterator to which we forward ops. */
		@Nonnull
		private final RocksIteratorWrapper iterator;

		/** Cache for the current element of the iteration. */
		@Nullable
		private byte[] currentElement;

		private RocksToJavaIteratorAdapter(@Nonnull RocksIteratorWrapper iterator) {
			this.iterator = iterator;
			try {
				// We use our knowledge about the lower bound to issue a seek that is as close to the first element in
				// the key-group as possible, i.e. we generate the next possible key after seekHint by appending one
				// zero-byte.
				iterator.seek(Arrays.copyOf(seekHint, seekHint.length + 1));
				currentElement = nextElementIfAvailable();
			} catch (Exception ex) {
				// ensure resource cleanup also in the face of (runtime) exceptions in the constructor.
				iterator.close();
				throw new FlinkRuntimeException("Could not initialize ordered iterator.", ex);
			}
		}

		@Override
		public void close() {
			iterator.close();
		}

		@Override
		public boolean hasNext() {
			return currentElement != null;
		}

		@Override
		public byte[] next() {
			final byte[] returnElement = this.currentElement;
			if (returnElement == null) {
				throw new NoSuchElementException("Iterator has no more elements!");
			}
			iterator.next();
			currentElement = nextElementIfAvailable();
			return returnElement;
		}

		private byte[] nextElementIfAvailable() {
			final byte[] elementBytes;
			return iterator.isValid()
				&& isPrefixWith((elementBytes = iterator.key()), groupPrefixBytes) ? elementBytes : null;
		}
	}
}

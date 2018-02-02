/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.streaming.runtime.io;

import org.apache.flink.runtime.io.network.partition.consumer.BufferOrEvent;

import org.apache.flink.annotation.Internal;
import javax.annotation.Nullable;

import java.util.ArrayDeque;

/**
 * The buffer blocker takes the buffers and events from a data stream and adds them to a memory queue.
 * After a number of elements have been buffered, the blocker can "roll over": It presents the buffered
 * elements as a readable sequence, and creates a new memory queue.
 */
@Internal
public class CreditBasedBufferBlocker {

	/** The page size, to let this reader instantiate properly sized memory segments. */
	private final int pageSize;

	/** The number of bytes buffered since the last roll over. */
	private long bytesBuffered;

	/** The current memory queue used for caching the buffers or events. */
	private ArrayDeque<BufferOrEvent> currentBuffers;

	/**
	 * Creates a new buffer blocker, caching the buffers or events in memory queue.
	 *
	 * @param pageSize The page size used to estimate the buffered size.
	 */
	public CreditBasedBufferBlocker(int pageSize) {
		this.pageSize = pageSize;
		this.currentBuffers = new ArrayDeque<BufferOrEvent>();
	}

	/**
	 * Adds a buffer or event to the buffered queue.
	 *
	 * @param boe The buffer or event to be added into the queue.
	 */
	public void add(BufferOrEvent boe) {
		bytesBuffered += pageSize;

		currentBuffers.add(boe);
	}

	/**
	 * Starts a new buffered sequence of buffers and event and returns the current buffered sequence of buffers
	 * for reading. This method returns {@code null}, if nothing was added since the creation, or the last call to
	 * this method.
	 *
	 * <p>The BufferOrEventSequence returned by this method is safe for concurrent consumption with
	 * any previously returned sequence.
	 *
	 * @return The readable sequence of buffers and events, or 'null', if nothing was added.
	 */
	public BufferOrEventSequence rollOver() {
		if (bytesBuffered == 0) {
			return null;
		}

		BufferOrEventSequence currentSequence = new BufferOrEventSequence(currentBuffers, bytesBuffered);
		currentBuffers = new ArrayDeque<BufferOrEvent>();
		bytesBuffered = 0L;

		return currentSequence;
	}

	/**
	 * Cleans up all the buffer resources in the current queue.
	 */
	public void close() {
		BufferOrEvent boe;
		while ((boe = currentBuffers.poll()) != null) {
			if (boe.isBuffer()) {
				boe.getBuffer().recycleBuffer();
			}
		}
	}

	/**
	 * Gets the number of bytes buffered in the current queue.
	 *
	 * @return the number of bytes buffered in the current queue.
	 */
	public long getBytesWritten() {
		return bytesBuffered;
	}

	// ------------------------------------------------------------------------

	/**
	 * This class represents a sequence of queued buffers and events, created by the
	 * {@link CreditBasedBufferBlocker}.
	 *
	 * <p>The sequence of buffers and events can be read back using the method
	 * {@link #getNext()}.
	 */
	public static class BufferOrEventSequence {

		/** The sequence of buffers and events to be consumed by {@link CreditBasedBarrierBuffer}.*/
		private final ArrayDeque<BufferOrEvent> queuedBuffers;

		/** The total size of the buffered queue. */
		private final long size;

		/**
		 * Creates a reader that reads a sequence of buffers and events.
		 *
		 * @param size The total size of the buffered sequence.
		 */
		BufferOrEventSequence(ArrayDeque<BufferOrEvent> buffers, long size) {
			this.queuedBuffers = buffers;
			this.size = size;
		}

		/**
		 * Gets the next BufferOrEvent from the buffered sequence, or {@code null}, if the
		 * sequence is exhausted.
		 *
		 * @return The next BufferOrEvent from the buffered sequence, or {@code null} (end of sequence).
		 */
		@Nullable
		public BufferOrEvent getNext() {
			return queuedBuffers.poll();
		}

		/**
		 * Cleans up all the buffer resources held by this buffered sequence.
		 */
		public void cleanup() {
			BufferOrEvent boe;
			while ((boe = queuedBuffers.poll()) != null) {
				if (boe.isBuffer()) {
					boe.getBuffer().recycleBuffer();
				}
			}
		}

		/**
		 * Gets the size of this buffered sequence.
		 */
		public long size() {
			return size;
		}
	}
}

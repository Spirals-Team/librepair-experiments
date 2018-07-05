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

package org.apache.flink.streaming.api.operators;

/**
 *
 * @param <K>
 * @param <N>
 * @param <T>
 */
public class TimerState<K, N, T extends InternalTimer<K, N>> {

//	@Nonnull
//	private final InternalPriorityQueue<T> internalPriorityQueue;
//
//	@Nonnull
//	InternalTimer.TimerFactory<K, N, T> timerFactory;
//
//	@Nonnull
//	private final TimerSerializer<K, N, T> timerSerializer;
//
//	public TimerState(
//		@Nonnull InternalPriorityQueue<T> internalPriorityQueue,
//		@Nonnull TimerSerializer<K, N, T> timerSerializer) {
//
//		this.internalPriorityQueue = internalPriorityQueue;
//		this.timerSerializer = timerSerializer;
//		this.timerFactory = timerSerializer.getTimerFactory();
//	}
//
//	@Nullable
//	public T poll() {
//		return internalPriorityQueue.poll();
//	}
//
//	@Nullable
//	public T peek() {
//		return internalPriorityQueue.peek();
//	}
//
//	public boolean add(long timestamp, @Nonnull K key, @Nonnull N namespace) {
//		return internalPriorityQueue.add(timerFactory.create(timestamp, key, namespace));
//	}
//
//	public boolean remove(long timestamp, @Nonnull K key, @Nonnull N namespace) {
//		return internalPriorityQueue.remove(timerFactory.create(timestamp, key, namespace));
//	}
//
//	public boolean isEmpty() {
//		return internalPriorityQueue.isEmpty();
//	}
//
//	@Nonnegative
//	public int size() {
//		return internalPriorityQueue.size();
//	}
//
//	public void addAll(@Nullable Collection<? extends T> toAdd) {
//		internalPriorityQueue.addAll(toAdd);
//	}
//
//	@Nonnull
//	public CloseableIterator<T> iterator() {
//		return internalPriorityQueue.iterator();
//	}
}

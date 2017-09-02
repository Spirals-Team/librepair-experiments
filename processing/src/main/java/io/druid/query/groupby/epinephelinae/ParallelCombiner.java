/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.query.groupby.epinephelinae;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import io.druid.collections.ResourceHolder;
import io.druid.java.util.common.CloseableIterators;
import io.druid.java.util.common.ISE;
import io.druid.java.util.common.Pair;
import io.druid.java.util.common.io.Closer;
import io.druid.java.util.common.parsers.CloseableIterator;
import io.druid.query.AbstractPrioritizedCallable;
import io.druid.query.QueryInterruptedException;
import io.druid.query.aggregation.AggregatorFactory;
import io.druid.query.dimension.DimensionSpec;
import io.druid.query.groupby.epinephelinae.Grouper.Entry;
import io.druid.query.groupby.epinephelinae.Grouper.KeySerdeFactory;
import io.druid.query.monomorphicprocessing.RuntimeShapeInspector;
import io.druid.segment.ColumnSelectorFactory;
import io.druid.segment.DimensionSelector;
import io.druid.segment.DoubleColumnSelector;
import io.druid.segment.FloatColumnSelector;
import io.druid.segment.LongColumnSelector;
import io.druid.segment.ObjectColumnSelector;
import io.druid.segment.column.ColumnCapabilities;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * ParallelCombiner builds a combining tree which asynchronously aggregates input entries.  Each node of the combining
 * tree is a combining task executed in parallel which aggregates inputs from the child nodes.
 */
public class ParallelCombiner<KeyType>
{
  private static final int MINIMUM_COMBINE_DEGREE = 2;

  private final Supplier<ResourceHolder<ByteBuffer>> combineBufferSupplier;
  private final AggregatorFactory[] combiningFactories;
  private final KeySerdeFactory<KeyType> combineKeySerdeFactory;
  private final ListeningExecutorService executor;
  private final Comparator<Entry<KeyType>> keyObjComparator;
  private final int concurrencyHint;
  private final int priority;

  public ParallelCombiner(
      Supplier<ResourceHolder<ByteBuffer>> combineBufferSupplier,
      AggregatorFactory[] combiningFactories,
      KeySerdeFactory<KeyType> combineKeySerdeFactory,
      ListeningExecutorService executor,
      boolean sortHasNonGroupingFields,
      int concurrencyHint,
      int priority
  )
  {
    this.combineBufferSupplier = combineBufferSupplier;
    this.combiningFactories = combiningFactories;
    this.combineKeySerdeFactory = combineKeySerdeFactory;
    this.executor = executor;
    this.keyObjComparator = combineKeySerdeFactory.objectComparator(sortHasNonGroupingFields);;
    this.concurrencyHint = concurrencyHint;
    this.priority = priority;
  }

  /**
   * Build a combining tree for the input iterators which combine input entries asynchronously.  Each node in the tree
   * is a combining task which iterates through child iterators, aggregates the inputs from those iterators, and returns
   * an iterator for the result of aggregation.
   * <p>
   * This method is called when data is spilled and thus streaming combine is preferred to avoid too many disk accesses.
   *
   * @return an iterator of the root grouper of the combining tree
   */
  public CloseableIterator<Entry<KeyType>> combine(
      List<? extends CloseableIterator<Entry<KeyType>>> sortedIterators,
      List<String> mergedDictionary
  )
  {
    // CombineBuffer is initialized when this method is called and closed after the result iterator is done
    final ResourceHolder<ByteBuffer> combineBufferHolder = combineBufferSupplier.get();
    final ByteBuffer combineBuffer = combineBufferHolder.get();
    final int minimumRequiredBufferCapacity = StreamingMergeSortedGrouper.requiredBufferCapacity(
        combineKeySerdeFactory.factorizeWithDictionary(mergedDictionary),
        combiningFactories
    );
    // We want to maximize the parallelism while the size of buffer slice is greater than the minimum buffer size
    // required by StreamingMergeSortedGrouper. Here, we find the degree of the cominbing tree and the required number
    // of buffers maximizing the degree of parallelism.
    final Pair<Integer, Integer> degreeAndNumBuffers = findCombineDegreeAndNumBuffers(
        combineBuffer,
        minimumRequiredBufferCapacity,
        concurrencyHint,
        sortedIterators.size()
    );

    final int combineDegree = degreeAndNumBuffers.lhs;
    final int numBuffers = degreeAndNumBuffers.rhs;
    final int sliceSize = combineBuffer.capacity() / numBuffers;

    final Supplier<ByteBuffer> bufferSupplier = createCombineBufferSupplier(combineBuffer, numBuffers, sliceSize);

    final Pair<CloseableIterator<Entry<KeyType>>, List<Future>> combineIteratorAndFutures = buildCombineTree(
        sortedIterators,
        bufferSupplier,
        combiningFactories,
        combineDegree,
        mergedDictionary
    );

    final CloseableIterator<Entry<KeyType>> combineIterator = combineIteratorAndFutures.lhs;
    final List<Future> combineFutures = combineIteratorAndFutures.rhs;

    final Closer closer = Closer.create();
    closer.register(combineBufferHolder);
    closer.register(() -> checkCombineFutures(combineFutures));

    return CloseableIterators.wrap(combineIterator, closer);
  }

  private static void checkCombineFutures(List<Future> combineFutures)
  {
    for (Future future : combineFutures) {
      try {
        if (!future.isDone()) {
          // Cancel futures if close() for the iterator is called early due to some reason (e.g., test failure)
          future.cancel(true);
        } else {
          future.get();
        }
      }
      catch (InterruptedException | CancellationException e) {
        throw new QueryInterruptedException(e);
      }
      catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static Supplier<ByteBuffer> createCombineBufferSupplier(
      ByteBuffer combineBuffer,
      int numBuffers,
      int sliceSize
  )
  {
    return new Supplier<ByteBuffer>()
    {
      private int i = 0;

      @Override
      public ByteBuffer get()
      {
        if (i < numBuffers) {
          return Groupers.getSlice(combineBuffer, sliceSize, i++);
        } else {
          throw new ISE("Requested number of buffer slices exceeds the planned one");
        }
      }
    };
  }

  /**
   * Find a minimum size of the buffer slice and corresponding combining degree and number of slices.  Note that each
   * node in the combining tree is executed by different threads.  This method assumes that higher degree of parallelism
   * can exploit better performance and find such a shape of the combining tree.
   *
   * @param combineBuffer                 entire buffer used for combining tree
   * @param requiredMinimumBufferCapacity minimum buffer capacity for {@link StreamingMergeSortedGrouper}
   * @param concurrencyHint               available degree of parallelism
   * @param numLeafNodes                  number of leaf nodes of combining tree
   *
   * @return a pair of degree and number of buffers if found.
   */
  private static Pair<Integer, Integer> findCombineDegreeAndNumBuffers(
      ByteBuffer combineBuffer,
      int requiredMinimumBufferCapacity,
      int concurrencyHint,
      int numLeafNodes
  )
  {
    for (int degree = MINIMUM_COMBINE_DEGREE; degree <= numLeafNodes; degree++) {
      final int requiredBufferNum = computeRequiredBufferNum(numLeafNodes, degree);
      if (requiredBufferNum <= concurrencyHint) {
        final int expectedSliceSize = combineBuffer.capacity() / requiredBufferNum;
        if (expectedSliceSize >= requiredMinimumBufferCapacity) {
          return Pair.of(degree, requiredBufferNum);
        }
      }
    }

    throw new ISE("Cannot find a proper combine degree");
  }

  /**
   * Recursively compute the number of required buffers for a combining tree in a top-down manner.  Since each node of
   * the combining tree represents a combining task and each combining task requires one buffer, the number of required
   * buffers is the number of nodes of the combining tree.
   *
   * @param numLeafNodes  number of leaf nodes
   * @param combineDegree combine degree
   *
   * @return minimum number of buffers required for combining tree
   *
   * @see {@link #buildCombineTree(List, Supplier, AggregatorFactory[], int, List)}
   */
  private static int computeRequiredBufferNum(int numLeafNodes, int combineDegree)
  {
    if (numLeafNodes > combineDegree) {
      final int numLeafNodesPerChild = (numLeafNodes + combineDegree - 1) / combineDegree; // ceiling
      int sum = 1; // count for the current node
      for (int i = 0; i < combineDegree; i++) {
        // further compute for child nodes
        sum += computeRequiredBufferNum(
            Math.min(numLeafNodesPerChild, numLeafNodes - i * numLeafNodesPerChild),
            combineDegree
        );
      }

      return sum;
    } else {
      return 1;
    }
  }

  /**
   * Recursively build a combining tree in a top-down manner.  Each node of the tree is a task that combines input
   * iterators asynchronously.
   *
   * @param sortedIterators    sorted iterators
   * @param bufferSupplier     combining buffer supplier
   * @param combiningFactories array of combining aggregator factories
   * @param combineDegree      combining degree
   * @param dictionary         merged dictionary
   *
   * @return a pair of an iterator of the root of the combining tree and a list of futures of all executed combining
   * tasks
   */
  private Pair<CloseableIterator<Entry<KeyType>>, List<Future>> buildCombineTree(
      List<? extends CloseableIterator<Entry<KeyType>>> sortedIterators,
      Supplier<ByteBuffer> bufferSupplier,
      AggregatorFactory[] combiningFactories,
      int combineDegree,
      List<String> dictionary
  )
  {
    final int numIterators = sortedIterators.size();
    if (numIterators > combineDegree) {
      final List<CloseableIterator<Entry<KeyType>>> childIterators = new ArrayList<>(combineDegree);
      final List<Future> combineFutures = new ArrayList<>(combineDegree + 1);

      final int iteratorsPerChild = (numIterators + combineDegree - 1) / combineDegree; // ceiling
      for (int i = 0; i < combineDegree; i++) {
        final Pair<CloseableIterator<Entry<KeyType>>, List<Future>> childIteratorAndFutures = buildCombineTree(
            sortedIterators.subList(i * iteratorsPerChild, Math.min(numIterators, (i + 1) * iteratorsPerChild)),
            bufferSupplier,
            combiningFactories,
            combineDegree,
            dictionary
        );
        childIterators.add(childIteratorAndFutures.lhs);
        combineFutures.addAll(childIteratorAndFutures.rhs);
      }
      final Pair<CloseableIterator<Entry<KeyType>>, Future> iteratorAndFuture = runCombiner(
          childIterators,
          bufferSupplier.get(),
          combiningFactories,
          dictionary
      );
      combineFutures.add(iteratorAndFuture.rhs);
      return new Pair<>(iteratorAndFuture.lhs, combineFutures);
    } else {
      final Pair<CloseableIterator<Entry<KeyType>>, Future> iteratorAndFuture = runCombiner(
          sortedIterators,
          bufferSupplier.get(),
          combiningFactories,
          dictionary
      );
      return new Pair<>(iteratorAndFuture.lhs, Collections.singletonList(iteratorAndFuture.rhs));
    }
  }

  private Pair<CloseableIterator<Entry<KeyType>>, Future> runCombiner(
      List<? extends CloseableIterator<Entry<KeyType>>> iterators,
      ByteBuffer combineBuffer,
      AggregatorFactory[] combiningFactories,
      List<String> dictionary
  )
  {
    final SettableColumnSelectorFactory settableColumnSelectorFactory =
        new SettableColumnSelectorFactory(combiningFactories);
    final StreamingMergeSortedGrouper<KeyType> grouper = new StreamingMergeSortedGrouper<>(
        Suppliers.ofInstance(combineBuffer),
        combineKeySerdeFactory.factorizeWithDictionary(dictionary),
        settableColumnSelectorFactory,
        combiningFactories
    );

    final ListenableFuture future = executor.submit(
        new AbstractPrioritizedCallable<Void>(priority)
        {
          @Override
          public Void call() throws Exception
          {
            grouper.init();

            try (
                CloseableIterator<Entry<KeyType>> mergedIterator = CloseableIterators.mergeSorted(
                    iterators,
                    keyObjComparator
                )
            ) {
              while (mergedIterator.hasNext()) {
                final Entry<KeyType> next = mergedIterator.next();

                settableColumnSelectorFactory.set(next.values);
                grouper.aggregate(next.key); // grouper always returns ok or throws an exception
                settableColumnSelectorFactory.set(null);
              }
            }
            catch (IOException e) {
              throw Throwables.propagate(e);
            }

            grouper.finish();
            return null;
          }
        }
    );

    return new Pair<>(grouper.iterator(), future);
  }

  private static class SettableColumnSelectorFactory implements ColumnSelectorFactory
  {
    private static final int UNKNOWN_COLUMN_INDEX = -1;
    private final Object2IntMap<String> columnIndexMap;

    private Object[] values;

    SettableColumnSelectorFactory(AggregatorFactory[] aggregatorFactories)
    {
      columnIndexMap = new Object2IntArrayMap<>(aggregatorFactories.length);
      columnIndexMap.defaultReturnValue(UNKNOWN_COLUMN_INDEX);
      for (int i = 0; i < aggregatorFactories.length; i++) {
        columnIndexMap.put(aggregatorFactories[i].getName(), i);
      }
    }

    public void set(Object[] values)
    {
      this.values = values;
    }

    private int checkAndGetColumnIndex(String columnName)
    {
      final int columnIndex = columnIndexMap.getInt(columnName);
      Preconditions.checkState(
          columnIndex != UNKNOWN_COLUMN_INDEX,
          "Cannot find a proper column index for column[%s]",
          columnName
      );
      return columnIndex;
    }

    @Override
    public DimensionSelector makeDimensionSelector(DimensionSpec dimensionSpec)
    {
      throw new UnsupportedOperationException();
    }

    @Override
    public FloatColumnSelector makeFloatColumnSelector(String columnName)
    {
      return new FloatColumnSelector()
      {
        @Override
        public float getFloat()
        {
          return ((Number) values[checkAndGetColumnIndex(columnName)]).floatValue();
        }

        @Override
        public void inspectRuntimeShape(RuntimeShapeInspector inspector)
        {
          // do nothing
        }
      };
    }

    @Override
    public LongColumnSelector makeLongColumnSelector(String columnName)
    {
      return new LongColumnSelector()
      {
        @Override
        public long getLong()
        {
          return ((Number) values[checkAndGetColumnIndex(columnName)]).longValue();
        }

        @Override
        public void inspectRuntimeShape(RuntimeShapeInspector inspector)
        {
          // do nothing
        }
      };
    }

    @Override
    public DoubleColumnSelector makeDoubleColumnSelector(String columnName)
    {
      return new DoubleColumnSelector()
      {
        @Override
        public double getDouble()
        {
          return ((Number) values[checkAndGetColumnIndex(columnName)]).doubleValue();
        }

        @Override
        public void inspectRuntimeShape(RuntimeShapeInspector inspector)
        {
          // do nothing
        }
      };
    }

    @Override
    public ObjectColumnSelector makeObjectColumnSelector(String columnName)
    {
      return new ObjectColumnSelector()
      {
        @Override
        public Class classOfObject()
        {
          return Object.class;
        }

        @Override
        public Object get()
        {
          return values[checkAndGetColumnIndex(columnName)];
        }
      };
    }

    @Override
    public ColumnCapabilities getColumnCapabilities(String column)
    {
      throw new UnsupportedOperationException();
    }
  }
}

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.operator;

import com.facebook.presto.array.BooleanBigArray;
import com.facebook.presto.array.IntBigArray;
import com.facebook.presto.array.ObjectBigArray;
import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.block.BlockBuilder;
import com.facebook.presto.spi.function.RankingWindowFunction;
import com.facebook.presto.spi.function.WindowFunctionSignature;
import com.facebook.presto.spi.function.WindowIndex;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.spi.type.TypeSignature;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import io.airlift.slice.Slice;
import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectHeapPriorityQueue;
import org.openjdk.jol.info.ClassLayout;

import javax.annotation.Nullable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.facebook.presto.spi.type.TypeSignature.parseTypeSignature;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Verify.verify;
import static io.airlift.slice.SizeOf.sizeOf;
import static java.util.Objects.requireNonNull;

/**
 * This class finds the top N rows defined by {@param comparator} for each group specified by {@param groupByHash}.
 * Additional RankingWindowFunctions are supported for callers to produce extra output channels.
 * For now, only RowNumberFunction and NTileFunction are supported by default.
 * All other RankingWindowFunctions are supported by turning on {@param computePeerGroupInfo}.
 */
public class GroupedTopNBuilder
{
    private static final long INSTANCE_SIZE = ClassLayout.parseClass(GroupedTopNBuilder.class).instanceSize();
    // compact a page when 50% of its positions are unreferenced
    private static final int COMPACT_THRESHOLD = 2;

    private final List<Type> sourceTypes;
    private final int topN;
    private final List<RankingWindowFunction> rankingFunctions;
    private final List<Type> rankingFunctionReturnTypes;

    @Nullable
    private final GroupByHash groupByHash;  // null value to denote a single group

    // a map of heaps, each of which records the top N rows
    private final ObjectBigArray<RowHeap> groupedRows;
    // a list of input pages, each of which has information of which row in which heap references which position
    private final ObjectBigArray<PageReference> pageReferences;
    // for heap element comparison
    private final Comparator<Row> comparator;
    // when there is no row referenced in a page, it will be removed instead of compacted; use a list to record those empty slots to reuse them
    private final IntFIFOQueue emptyPageReferenceSlots;

    // keeps track sizes of input pages and heaps
    private long memorySizeInBytes;
    private int currentPageCount;

    // TODO: turn this on to support the full set of RankingWindowFunctions
    private boolean computePeerGroupInfo;

    public GroupedTopNBuilder(
            List<Type> sourceTypes,
            PageComparator comparator,
            int topN,
            List<RankingWindowFunction> rankingFunctions,
            List<Type> rankingFunctionReturnTypes,
            @Nullable GroupByHash groupByHash)
    {
        this.sourceTypes = requireNonNull(sourceTypes, "sourceTypes is null");
        checkArgument(topN > 0, "topN must be > 0");
        this.topN = topN;
        this.rankingFunctions = requireNonNull(rankingFunctions, "rankingFunctions is null");
        this.rankingFunctionReturnTypes = requireNonNull(rankingFunctionReturnTypes, "rankingFunctionReturnTypes is null");
        checkArgument(rankingFunctionReturnTypes.size() == rankingFunctions.size());

        // verify the claimed return types match the given return types
        for (int i = 0; i < rankingFunctions.size(); i++) {
            WindowFunctionSignature functionSignature = rankingFunctions.get(i).getClass().getDeclaredAnnotation(WindowFunctionSignature.class);
            TypeSignature registeredType = parseTypeSignature(functionSignature.returnType());
            TypeSignature providedType = rankingFunctionReturnTypes.get(i).getTypeSignature();
            checkArgument(
                    registeredType.equals(providedType),
                    "expect return type %s for function %s but got %s",
                    registeredType.toString(),
                    functionSignature.name(),
                    providedType.toString());
        }

        this.groupByHash = groupByHash;

        this.groupedRows = new ObjectBigArray<>();
        this.pageReferences = new ObjectBigArray<>();
        this.comparator = (left, right) -> requireNonNull(comparator, "comparator is null").compareTo(
                pageReferences.get(left.getPageId()).getPage(),
                left.getPosition(),
                pageReferences.get(right.getPageId()).getPage(),
                right.getPosition());
        this.emptyPageReferenceSlots = new IntFIFOQueue();
    }

    public Work<?> processPage(Page page)
    {
        if (groupByHash != null) {
            return new TransformWork<>(
                    groupByHash.getGroupIds(page),
                    groupIds -> {
                        processPage(page, groupIds);
                        return null;
                    });
        }
        return new TransformWork<>(
                // Use a dummy value to trigger the transformation
                new CompletedWork<>(0),
                placeHolder -> {
                    processPage(page, null);
                    return null;
                });
    }

    public Iterator<Page> buildResult()
    {
        return new AbstractIterator<Page>()
        {
            private final PageBuilder pageBuilder = new PageBuilder(new ImmutableList.Builder<Type>().addAll(sourceTypes).addAll(rankingFunctionReturnTypes).build());
            // we may have 0 groups if there is no input page processed
            private final int groupCount = groupByHash == null ? Math.min(1, currentPageCount) : groupByHash.getGroupCount();

            private int currentGroupNumber;
            private long currentGroupSizeInBytes;

            // the row number of the current position in the group
            private int currentPosition;
            // number of rows in the group
            private int currentGroupSize;

            private ObjectBigArray<Row> currentRows = nextGroupedRows();

            // for peer group; used only when computePeerGroupInfo is true
            // TODO: use interval sets and think about how to turn this on by default
            private IntBigArray peerGroupCounts;
            private BooleanBigArray newPeerGroups;

            @Override
            protected Page computeNext()
            {
                pageBuilder.reset();
                while (!pageBuilder.isFull()) {
                    if (currentRows == null) {
                        // no more groups
                        break;
                    }
                    if (currentPosition == currentGroupSize) {
                        // the current group has produced all its rows
                        memorySizeInBytes -= currentGroupSizeInBytes;
                        currentPosition = 0;
                        currentRows = nextGroupedRows();
                        continue;
                    }

                    Row row = currentRows.get(currentPosition);
                    for (int i = 0; i < sourceTypes.size(); i++) {
                        sourceTypes.get(i).appendTo(pageReferences.get(row.getPageId()).getPage().getBlock(i), row.getPosition(), pageBuilder.getBlockBuilder(i));
                    }

                    boolean newPeerGroup = computePeerGroupInfo && newPeerGroups.get(currentPosition);
                    int peerGroupCount = computePeerGroupInfo ? peerGroupCounts.get(currentPosition) : 1;
                    for (int i = 0; i < rankingFunctionReturnTypes.size(); i++) {
                        rankingFunctions.get(i).processRow(pageBuilder.getBlockBuilder(i + sourceTypes.size()), newPeerGroup, peerGroupCount, currentPosition);
                    }
                    pageBuilder.declarePosition();
                    currentPosition++;

                    // deference the row; no need to compact the pages but remove them if completely unused
                    PageReference pageReference = pageReferences.get(row.getPageId());
                    pageReference.dereference(row.getPosition());
                    if (pageReference.getUsedPositionCount() == 0) {
                        pageReferences.set(row.getPageId(), null);
                        memorySizeInBytes -= pageReference.getEstimatedSizeInBytes();
                    }
                }

                if (pageBuilder.isEmpty()) {
                    return endOfData();
                }
                return pageBuilder.build();
            }

            private ObjectBigArray<Row> nextGroupedRows()
            {
                if (currentGroupNumber < groupCount) {
                    RowHeap rows = groupedRows.get(currentGroupNumber);
                    verify(rows != null && !rows.isEmpty(), "impossible to have inserted a group without a witness row");
                    groupedRows.set(currentGroupNumber, null);
                    currentGroupSizeInBytes = rows.getEstimatedSizeInBytes();
                    currentGroupNumber++;
                    currentGroupSize = rows.size();

                    // sort output rows in a big array in case there are too many rows
                    ObjectBigArray<Row> sortedRows = new ObjectBigArray<>();
                    sortedRows.ensureCapacity(currentGroupSize);
                    int index = currentGroupSize - 1;
                    while (!rows.isEmpty()) {
                        sortedRows.set(index, rows.dequeue());
                        index--;
                    }

                    if (computePeerGroupInfo) {
                        computePeerGroupInfo(sortedRows);
                    }

                    // reset all ranking functions
                    WindowIndex windowIndex = new TopNWindowIndex(sortedRows, currentGroupSize);
                    for (int i = 0; i < rankingFunctionReturnTypes.size(); i++) {
                        rankingFunctions.get(i).reset(windowIndex);
                    }
                    return sortedRows;
                }
                return null;
            }

            private void computePeerGroupInfo(ObjectBigArray<Row> sortedRows)
            {
                verify(computePeerGroupInfo);
                peerGroupCounts = new IntBigArray();
                peerGroupCounts.ensureCapacity(currentGroupSize);
                newPeerGroups = new BooleanBigArray();
                newPeerGroups.ensureCapacity(currentGroupSize);

                int peerGroupStart = 0;
                newPeerGroups.set(0, true);
                for (int i = 1; i < currentGroupSize; i++) {
                    if (comparator.compare(sortedRows.get(i - 1), sortedRows.get(i)) == 0) {
                        newPeerGroups.set(i, false);
                        continue;
                    }
                    newPeerGroups.set(i, true);
                    int peerGroupCount = i - peerGroupStart;
                    for (int j = peerGroupStart; j < i; j++) {
                        peerGroupCounts.set(j, peerGroupCount);
                    }
                    peerGroupStart = i;
                }

                int peerGroupCount = currentGroupSize - peerGroupStart;
                for (int j = peerGroupStart; j < currentGroupSize; j++) {
                    peerGroupCounts.set(j, peerGroupCount);
                }
            }
        };
    }

    public void compact()
    {
        for (int i = 0; i < currentPageCount; i++) {
            if (pageReferences.get(i) != null) {
                pageReferences.get(i).compact();
            }
        }
        compactPages(
                new AbstractIterator<Integer>()
                {
                    private int pageId;

                    @Override
                    protected Integer computeNext()
                    {
                        for (int i = pageId; i < currentPageCount; i++) {
                            if (pageReferences.get(i) != null) {
                                pageId = i + 1;
                                return i;
                            }
                        }
                        return endOfData();
                    }
                });
    }

    public long getEstimatedSizeInBytes()
    {
        long hashMemorySizeInBytes = groupByHash == null ? 0 : groupByHash.getEstimatedSize();
        return INSTANCE_SIZE +
                memorySizeInBytes +
                hashMemorySizeInBytes +
                groupedRows.sizeOf() +
                pageReferences.sizeOf() +
                emptyPageReferenceSlots.getEstimatedSizeInBytes();
    }

    @VisibleForTesting
    public List<Page> getBufferedPages()
    {
        ImmutableList.Builder<Page> builder = new ImmutableList.Builder<>();
        for (int i = 0; i < currentPageCount; i++) {
            if (pageReferences.get(i) != null) {
                builder.add(pageReferences.get(i).getPage());
            }
        }
        return builder.build();
    }

    @VisibleForTesting
    public void enablePeerGroupInfo()
    {
        computePeerGroupInfo = true;
    }

    private void processPage(Page newPage, @Nullable GroupByIdBlock groupIds)
    {
        verify(groupIds != null || groupByHash == null);

        // save the new page
        PageReference newPageReference = new PageReference(newPage);
        memorySizeInBytes += newPageReference.getEstimatedSizeInBytes();
        int newPageId;
        if (emptyPageReferenceSlots.isEmpty()) {
            // all the previous slots are full; create a new one
            pageReferences.ensureCapacity(currentPageCount + 1);
            newPageId = currentPageCount;
            currentPageCount++;
        }
        else {
            // reuse a previously removed page's slot
            newPageId = emptyPageReferenceSlots.dequeueInt();
        }
        verify(pageReferences.get(newPageId) == null, "should not overwrite a non-empty slot");
        pageReferences.set(newPageId, newPageReference);

        // update the affected heaps and record candidate pages that need compaction
        Set<Integer> pagesToCompact = new IntOpenHashSet();
        for (int position = 0; position < newPage.getPositionCount(); position++) {
            long groupId = groupIds != null ? groupIds.getGroupId(position) : 0L;
            groupedRows.ensureCapacity(groupId + 1);

            RowHeap rows = groupedRows.get(groupId);
            if (rows == null) {
                // a new group
                rows = new RowHeap(Ordering.from(comparator).reversed());
                groupedRows.set(groupId, rows);
            }
            else {
                // update an existing group;
                // remove the memory usage for this group for now; add it back after update
                memorySizeInBytes -= rows.getEstimatedSizeInBytes();
            }

            if (rows.size() < topN) {
                // still have space for the current group
                Row row = new Row(newPageId, position);
                rows.enqueue(row);
                newPageReference.reference(row);
            }
            else {
                // may compare with the topN-th element with in the heap to decide if update is necessary
                Row previousRow = rows.first();
                Row newRow = new Row(newPageId, position);
                if (comparator.compare(newRow, previousRow) < 0) {
                    // update reference and the heap
                    rows.dequeue();
                    PageReference previousPageReference = pageReferences.get(previousRow.getPageId());
                    previousPageReference.dereference(previousRow.getPosition());
                    newPageReference.reference(newRow);
                    rows.enqueue(newRow);

                    // compact a page if it is not the current input page and the reference count is below the threshold
                    if (previousPageReference.getPage() != newPage &&
                            previousPageReference.getUsedPositionCount() * COMPACT_THRESHOLD < previousPageReference.getPage().getPositionCount() &&
                            !pagesToCompact.contains(previousRow.getPageId())) {
                        pagesToCompact.add(previousRow.getPageId());
                    }
                }
            }

            memorySizeInBytes += rows.getEstimatedSizeInBytes();
        }

        // may compact the new page as well
        if (newPageReference.getUsedPositionCount() * COMPACT_THRESHOLD < newPage.getPositionCount()) {
            verify(!pagesToCompact.contains(newPageId));
            pagesToCompact.add(newPageId);
        }

        compactPages(pagesToCompact.iterator());
    }

    private void compactPages(Iterator<Integer> pageIds)
    {
        while (pageIds.hasNext()) {
            int pageId = pageIds.next();
            PageReference pageReference = pageReferences.get(pageId);
            if (pageReference.getUsedPositionCount() == 0) {
                pageReferences.set(pageId, null);
                emptyPageReferenceSlots.enqueue(pageId);
                memorySizeInBytes -= pageReference.getEstimatedSizeInBytes();
            }
            else {
                memorySizeInBytes -= pageReference.getEstimatedSizeInBytes();
                pageReference.compact();
                memorySizeInBytes += pageReference.getEstimatedSizeInBytes();
            }
        }
    }

    private class Row
    {
        private final int pageId;
        private int position;

        private Row(int pageId, int position)
        {
            this.pageId = pageId;
            reset(position);
        }

        public void reset(int position)
        {
            this.position = position;
        }

        public int getPageId()
        {
            return pageId;
        }

        public int getPosition()
        {
            return position;
        }
    }

    private static class PageReference
    {
        private static final long INSTANCE_SIZE = ClassLayout.parseClass(PageReference.class).instanceSize();

        private Page page;
        private Row[] reference;

        private int usedPositionCount;

        public PageReference(Page page)
        {
            reset(requireNonNull(page, "page is null"), new Row[page.getPositionCount()]);
        }

        public void reference(Row row)
        {
            int position = row.getPosition();
            reference[position] = row;
            usedPositionCount++;
        }

        public void dereference(int position)
        {
            checkArgument(reference[position] != null && usedPositionCount > 0);
            reference[position] = null;
            usedPositionCount--;
        }

        public int getUsedPositionCount()
        {
            return usedPositionCount;
        }

        public void compact()
        {
            checkState(usedPositionCount > 0);

            if (usedPositionCount == page.getPositionCount()) {
                return;
            }

            // re-assign reference
            Row[] newReference = new Row[usedPositionCount];
            int[] positions = new int[usedPositionCount];
            int index = 0;
            for (int i = 0; i < page.getPositionCount(); i++) {
                if (reference[i] != null) {
                    newReference[index] = reference[i];
                    positions[index] = i;
                    index++;
                }
            }
            verify(index == usedPositionCount);

            // compact page
            Block[] blocks = new Block[page.getChannelCount()];
            for (int i = 0; i < page.getChannelCount(); i++) {
                Block block = page.getBlock(i);
                blocks[i] = block.copyPositions(positions, 0, usedPositionCount);
            }

            // update all the elements in the heaps that reference the current page
            for (int i = 0; i < usedPositionCount; i++) {
                // this does not change the elements in the heap;
                // it only updates the value of the elements; while keeping the same order
                newReference[i].reset(i);
            }
            reset(new Page(usedPositionCount, blocks), newReference);
        }

        public Page getPage()
        {
            return page;
        }

        public long getEstimatedSizeInBytes()
        {
            return page.getRetainedSizeInBytes() + sizeOf(reference) + INSTANCE_SIZE;
        }

        private void reset(Page page, Row[] reference)
        {
            this.page = page;
            this.reference = reference;
        }
    }

    // this class is for precise memory tracking
    private static class IntFIFOQueue
            extends IntArrayFIFOQueue
    {
        private static final long INSTANCE_SIZE = ClassLayout.parseClass(IntFIFOQueue.class).instanceSize();

        private long getEstimatedSizeInBytes()
        {
            return INSTANCE_SIZE + sizeOf(array);
        }
    }

    // this class is for precise memory tracking
    private static class RowHeap
            extends ObjectHeapPriorityQueue<Row>
    {
        private static final long INSTANCE_SIZE = ClassLayout.parseClass(RowHeap.class).instanceSize();
        private static final long ROW_ENTRY_SIZE = ClassLayout.parseClass(Row.class).instanceSize();

        private RowHeap(Comparator<Row> comparator)
        {
            super(1, comparator);
        }

        private long getEstimatedSizeInBytes()
        {
            return INSTANCE_SIZE + sizeOf(heap) + size() * ROW_ENTRY_SIZE;
        }
    }

    private class TopNWindowIndex
            implements WindowIndex
    {
        private final ObjectBigArray<Row> sortedRows;
        private final int size;

        private TopNWindowIndex(ObjectBigArray<Row> sortedRows, int size)
        {
            this.sortedRows = requireNonNull(sortedRows, "sortedRows is null");
            this.size = size;
        }

        @Override
        public int size()
        {
            return size;
        }

        @Override
        public boolean isNull(int channel, int position)
        {
            Row row = getRow(position);
            PageReference pageReference = pageReferences.get(row.getPageId());
            checkState(pageReference != null, "pageReference has already been removed");
            Block block = pageReference.getPage().getBlock(channel);
            return block.isNull(row.getPosition());
        }

        @Override
        public boolean getBoolean(int channel, int position)
        {
            throw new UnsupportedOperationException("TopNWindowIndex does not support getBoolean");
        }

        @Override
        public long getLong(int channel, int position)
        {
            Row row = getRow(position);
            PageReference pageReference = pageReferences.get(row.getPageId());
            checkState(pageReference != null, "pageReference has already been removed");
            Block block = pageReference.getPage().getBlock(channel);
            return block.getLong(row.getPosition(), 0);
        }

        @Override
        public double getDouble(int channel, int position)
        {
            throw new UnsupportedOperationException("TopNWindowIndex does not support getDouble");
        }

        @Override
        public Slice getSlice(int channel, int position)
        {
            throw new UnsupportedOperationException("TopNWindowIndex does not support getSlice");
        }

        @Override
        public Block getSingleValueBlock(int channel, int position)
        {
            throw new UnsupportedOperationException("TopNWindowIndex does not support getSingleValueBlock");
        }

        @Override
        public Object getObject(int channel, int position)
        {
            throw new UnsupportedOperationException("TopNWindowIndex does not support getObject");
        }

        @Override
        public void appendTo(int channel, int position, BlockBuilder output)
        {
            throw new UnsupportedOperationException("TopNWindowIndex does not support appendTo");
        }

        private Row getRow(int position)
        {
            checkArgument(position < size);
            return sortedRows.get(position);
        }
    }
}

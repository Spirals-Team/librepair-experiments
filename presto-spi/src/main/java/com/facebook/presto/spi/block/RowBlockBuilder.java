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

package com.facebook.presto.spi.block;

import com.facebook.presto.spi.type.Type;
import org.openjdk.jol.info.ClassLayout;

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static com.facebook.presto.spi.block.BlockUtil.calculateBlockResetSize;
import static io.airlift.slice.SizeOf.sizeOf;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class RowBlockBuilder
        extends AbstractRowBlock
        implements BlockBuilder
{
    private static final int INSTANCE_SIZE = ClassLayout.parseClass(RowBlockBuilder.class).instanceSize();

    @Nullable
    private BlockBuilderStatus blockBuilderStatus;

    private int positionCount;
    private int[] columnBlockOffsets;
    private boolean[] rowIsNull;
    private final BlockBuilder[] columnBlockBuilders;

    private boolean currentEntryOpened;

    public RowBlockBuilder(List<Type> columnTypes, BlockBuilderStatus blockBuilderStatus, int expectedEntries)
    {
        this(
                blockBuilderStatus,
                columnTypes.stream()
                        .map(type -> type.createBlockBuilder(blockBuilderStatus, expectedEntries))
                        .toArray(BlockBuilder[]::new),
                new int[expectedEntries + 1],
                new boolean[expectedEntries]
        );
    }

    private RowBlockBuilder(@Nullable BlockBuilderStatus blockBuilderStatus, BlockBuilder[] columnBlockBuilders, int[] columnBlockOffsets, boolean[] rowIsNull)
    {
        super(columnBlockBuilders.length);

        this.blockBuilderStatus = blockBuilderStatus;
        this.positionCount = 0;
        this.columnBlockOffsets = requireNonNull(columnBlockOffsets, "columnBlockOffsets is null");
        this.rowIsNull = requireNonNull(rowIsNull, "rowIsNull is null");
        this.columnBlockBuilders = requireNonNull(columnBlockBuilders, "columnBlockBuilders is null");
    }

    @Override
    protected Block getColumnBlock(int columnIndex)
    {
        return columnBlockBuilders[columnIndex];
    }

    @Override
    protected int[] getColumnBlockOffsets()
    {
        return columnBlockOffsets;
    }

    @Override
    protected int getOffsetBase()
    {
        return 0;
    }

    @Override
    protected boolean[] getRowIsNull()
    {
        return rowIsNull;
    }

    @Override
    public int getPositionCount()
    {
        return positionCount;
    }

    @Override
    public long getSizeInBytes()
    {
        long sizeInBytes = (Integer.BYTES + Byte.BYTES) * (long) positionCount;
        for (int i = 0; i < numColumns; i++) {
            sizeInBytes += columnBlockBuilders[i].getSizeInBytes();
        }
        return sizeInBytes;
    }

    @Override
    public long getRetainedSizeInBytes()
    {
        long size = INSTANCE_SIZE + sizeOf(columnBlockOffsets) + sizeOf(rowIsNull);
        for (int i = 0; i < numColumns; i++) {
            size += columnBlockBuilders[i].getRetainedSizeInBytes();
        }
        if (blockBuilderStatus != null) {
            size += BlockBuilderStatus.INSTANCE_SIZE;
        }
        return size;
    }

    @Override
    public void retainedBytesForEachPart(BiConsumer<Object, Long> consumer)
    {
        for (int i = 0; i < numColumns; i++) {
            consumer.accept(columnBlockBuilders[i], columnBlockBuilders[i].getRetainedSizeInBytes());
        }
        consumer.accept(columnBlockOffsets, sizeOf(columnBlockOffsets));
        consumer.accept(rowIsNull, sizeOf(rowIsNull));
        consumer.accept(this, (long) INSTANCE_SIZE);
    }

    @Override
    public SingleRowBlockWriter beginBlockEntry()
    {
        if (currentEntryOpened) {
            throw new IllegalStateException("Expected current entry to be closed but was opened");
        }
        currentEntryOpened = true;
        return new SingleRowBlockWriter(columnBlockBuilders[0].getPositionCount() * numColumns, columnBlockBuilders);
    }

    @Override
    public BlockBuilder closeEntry()
    {
        if (!currentEntryOpened) {
            throw new IllegalStateException("Expected entry to be opened but was closed");
        }

        entryAdded(false);
        currentEntryOpened = false;
        return this;
    }

    @Override
    public BlockBuilder appendNull()
    {
        if (currentEntryOpened) {
            throw new IllegalStateException("Current entry must be closed before a null can be written");
        }

        entryAdded(true);
        return this;
    }

    private void entryAdded(boolean isNull)
    {
        if (rowIsNull.length <= positionCount) {
            int newSize = BlockUtil.calculateNewArraySize(rowIsNull.length);
            rowIsNull = Arrays.copyOf(rowIsNull, newSize);
            columnBlockOffsets = Arrays.copyOf(columnBlockOffsets, newSize + 1);
        }

        if (isNull) {
            columnBlockOffsets[positionCount + 1] = columnBlockOffsets[positionCount];
        }
        else {
            columnBlockOffsets[positionCount + 1] = columnBlockOffsets[positionCount] + 1;
        }
        rowIsNull[positionCount] = isNull;
        positionCount++;

        for (int i = 0; i < numColumns; i++) {
            if (columnBlockBuilders[i].getPositionCount() != columnBlockOffsets[positionCount]) {
                throw new IllegalStateException(format("column %s has unexpected position count. Expected: %s, actual: %s", i, columnBlockOffsets[positionCount], columnBlockBuilders[i].getPositionCount()));
            }
        }

        if (blockBuilderStatus != null) {
            blockBuilderStatus.addBytes(Integer.BYTES + Byte.BYTES);
        }
    }

    @Override
    public Block build()
    {
        if (currentEntryOpened) {
            throw new IllegalStateException("Current entry must be closed before the block can be built");
        }
        Block[] columnBlocks = new Block[numColumns];
        for (int i = 0; i < numColumns; i++) {
            columnBlocks[i] = columnBlockBuilders[i].build();
        }
        return new RowBlock(0, positionCount, rowIsNull, columnBlockOffsets, columnBlocks);
    }

    @Override
    public String toString()
    {
        return format("MapBlockBuilder{numColumns=%d, positionCount=%d", numColumns, getPositionCount());
    }

    @Override
    public BlockBuilder writeObject(Object value)
    {
        if (currentEntryOpened) {
            throw new IllegalStateException("Expected current entry to be closed but was opened");
        }
        currentEntryOpened = true;

        Block block = (Block) value;
        int blockPositionCount = block.getPositionCount();
        if (blockPositionCount != numColumns) {
            throw new IllegalArgumentException(format("block position count (%s) is not equal to number of columns (%s)", blockPositionCount, numColumns));
        }
        for (int i = 0; i < blockPositionCount; i++) {
            if (block.isNull(i)) {
                columnBlockBuilders[i].appendNull();
            }
            else {
                block.writePositionTo(i, columnBlockBuilders[i]);
                columnBlockBuilders[i].closeEntry();
            }
        }
        return this;
    }

    @Override
    public BlockBuilder newBlockBuilderLike(BlockBuilderStatus blockBuilderStatus)
    {
        int newSize = calculateBlockResetSize(getPositionCount());
        BlockBuilder[] newColumnBlockBuilders = new BlockBuilder[numColumns];
        for (int i = 0; i < numColumns; i++) {
            newColumnBlockBuilders[i] = columnBlockBuilders[i].newBlockBuilderLike(blockBuilderStatus);
        }
        return new RowBlockBuilder(blockBuilderStatus, newColumnBlockBuilders, new int[newSize + 1], new boolean[newSize]);
    }
}

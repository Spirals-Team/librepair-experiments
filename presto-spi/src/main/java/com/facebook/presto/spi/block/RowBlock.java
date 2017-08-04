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

import org.openjdk.jol.info.ClassLayout;

import java.util.function.BiConsumer;

import static io.airlift.slice.SizeOf.sizeOf;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class RowBlock
        extends AbstractRowBlock
{
    private static final int INSTANCE_SIZE = ClassLayout.parseClass(RowBlock.class).instanceSize();

    private final int startOffset;
    private final int positionCount;

    private final boolean[] rowIsNull;
    private final int[] columnBlockOffsets;
    private final Block[] columnBlocks;

    private long sizeInBytes;
    private final long retainedSizeInBytes;

    RowBlock(int startOffset, int positionCount, boolean[] rowIsNull, int[] columnBlockOffsets, Block[] columnBlocks)
    {
        super(columnBlocks.length);

        this.startOffset = startOffset;
        this.positionCount = positionCount;
        this.rowIsNull = requireNonNull(rowIsNull, "rowIsNull is null");
        this.columnBlockOffsets = requireNonNull(columnBlockOffsets, "columnBlockOffsets is null");
        this.columnBlocks = requireNonNull(columnBlocks, "columnBlocks is null");
        int firstColumnBlockPositionCount = columnBlocks[0].getPositionCount();
        for (int i = 1; i < columnBlocks.length; i++) {
            if (firstColumnBlockPositionCount != columnBlocks[i].getPositionCount()) {
                throw new IllegalArgumentException(format("length of column blocks differ: column 0: %s, block %s: %s", firstColumnBlockPositionCount, i, columnBlocks[i].getPositionCount()));
            }
        }

        this.sizeInBytes = -1;
        long retainedSizeInBytes = INSTANCE_SIZE + sizeOf(columnBlockOffsets) + sizeOf(rowIsNull);
        for (Block columnBlock : columnBlocks) {
            retainedSizeInBytes += columnBlock.getRetainedSizeInBytes();
        }
        this.retainedSizeInBytes = retainedSizeInBytes;
    }

    @Override
    protected Block getColumnBlock(int columnIndex)
    {
        return columnBlocks[columnIndex];
    }

    @Override
    protected int[] getColumnBlockOffsets()
    {
        return columnBlockOffsets;
    }

    @Override
    protected int getOffsetBase()
    {
        return startOffset;
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
        // this is racy but is safe because sizeInBytes is an long and the calculation is stable
        if (sizeInBytes < 0) {
            calculateSize();
        }
        return sizeInBytes;
    }

    private void calculateSize()
    {
        int startColumnBlockOffset = columnBlockOffsets[startOffset];
        int endColumnBlockOffset = columnBlockOffsets[startOffset + positionCount];
        int columnBlockLength = endColumnBlockOffset - startColumnBlockOffset;

        long sizeInBytes = (Integer.BYTES + Byte.BYTES) * (long) positionCount;
        for (int i = 0; i < numColumns; i++) {
            sizeInBytes += columnBlocks[i].getRegionSizeInBytes(startColumnBlockOffset, columnBlockLength);
        }
        this.sizeInBytes = sizeInBytes;
    }

    @Override
    public long getRetainedSizeInBytes()
    {
        return retainedSizeInBytes;
    }

    @Override
    public void retainedBytesForEachPart(BiConsumer<Object, Long> consumer)
    {
        for (int i = 0; i < numColumns; i++) {
            consumer.accept(columnBlocks[i], columnBlocks[i].getRetainedSizeInBytes());
        }
        consumer.accept(columnBlockOffsets, sizeOf(columnBlockOffsets));
        consumer.accept(rowIsNull, sizeOf(rowIsNull));
        consumer.accept(this, (long) INSTANCE_SIZE);
    }

    @Override
    protected Block[] getColumnBlocks()
    {
        return columnBlocks;
    }

    @Override
    public String toString()
    {
        return format("RowBlock{numColumns=%d, positionCount=%d", numColumns, getPositionCount());
    }
}

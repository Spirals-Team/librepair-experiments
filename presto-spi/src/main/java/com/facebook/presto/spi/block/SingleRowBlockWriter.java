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

import io.airlift.slice.Slice;
import org.openjdk.jol.info.ClassLayout;

import java.util.function.BiConsumer;

import static java.lang.String.format;

public class SingleRowBlockWriter
        extends AbstractSingleRowBlock
        implements BlockBuilder
{
    private static final int INSTANCE_SIZE = ClassLayout.parseClass(SingleRowBlockWriter.class).instanceSize();

    private final BlockBuilder[] columnBlockBuilders;
    private final long initialBlockBuilderSize;
    private int positionsWritten;

    private int currentColumnIndexToWrite;

    SingleRowBlockWriter(int startOffset, BlockBuilder[] columnBlockBuilders)
    {
        super(startOffset, columnBlockBuilders.length);
        this.columnBlockBuilders = columnBlockBuilders;
        long initialBlockBuilderSize = 0;
        for (int i = 0; i < columnBlockBuilders.length; i++) {
            initialBlockBuilderSize += columnBlockBuilders[i].getSizeInBytes();
        }
        this.initialBlockBuilderSize = initialBlockBuilderSize;
    }

    @Override
    protected Block getColumnBlock(int columnIndex)
    {
        return columnBlockBuilders[columnIndex];
    }

    @Override
    public long getSizeInBytes()
    {
        long currentBlockBuilderSize = 0;
        for (int i = 0; i < numColumns; i++) {
            currentBlockBuilderSize += columnBlockBuilders[i].getSizeInBytes();
        }
        return currentBlockBuilderSize - initialBlockBuilderSize;
    }

    @Override
    public long getRetainedSizeInBytes()
    {
        long size = INSTANCE_SIZE;
        for (int i = 0; i < numColumns; i++) {
            size += columnBlockBuilders[i].getRetainedSizeInBytes();
        }
        return size;
    }

    @Override
    public void retainedBytesForEachPart(BiConsumer<Object, Long> consumer)
    {
        for (int i = 0; i < numColumns; i++) {
            consumer.accept(columnBlockBuilders[i], columnBlockBuilders[i].getRetainedSizeInBytes());
        }
        consumer.accept(this, (long) INSTANCE_SIZE);
    }

    @Override
    public BlockBuilder writeByte(int value)
    {
        checkColumnIndexToWrite();
        columnBlockBuilders[currentColumnIndexToWrite].writeByte(value);
        return this;
    }

    @Override
    public BlockBuilder writeShort(int value)
    {
        checkColumnIndexToWrite();
        columnBlockBuilders[currentColumnIndexToWrite].writeShort(value);
        return this;
    }

    @Override
    public BlockBuilder writeInt(int value)
    {
        checkColumnIndexToWrite();
        columnBlockBuilders[currentColumnIndexToWrite].writeInt(value);
        return this;
    }

    @Override
    public BlockBuilder writeLong(long value)
    {
        checkColumnIndexToWrite();
        columnBlockBuilders[currentColumnIndexToWrite].writeLong(value);
        return this;
    }

    @Override
    public BlockBuilder writeBytes(Slice source, int sourceIndex, int length)
    {
        checkColumnIndexToWrite();
        columnBlockBuilders[currentColumnIndexToWrite].writeBytes(source, sourceIndex, length);
        return this;
    }

    @Override
    public BlockBuilder writeObject(Object value)
    {
        checkColumnIndexToWrite();
        columnBlockBuilders[currentColumnIndexToWrite].writeObject(value);
        return this;
    }

    @Override
    public BlockBuilder beginBlockEntry()
    {
        checkColumnIndexToWrite();
        return columnBlockBuilders[currentColumnIndexToWrite].beginBlockEntry();
    }

    @Override
    public BlockBuilder appendNull()
    {
        checkColumnIndexToWrite();
        columnBlockBuilders[currentColumnIndexToWrite].appendNull();
        entryAdded();
        return this;
    }

    @Override
    public BlockBuilder closeEntry()
    {
        checkColumnIndexToWrite();
        columnBlockBuilders[currentColumnIndexToWrite].closeEntry();
        entryAdded();
        return this;
    }

    private void entryAdded()
    {
        currentColumnIndexToWrite++;
        positionsWritten++;
    }

    @Override
    public int getPositionCount()
    {
        return positionsWritten;
    }

    @Override
    public BlockEncoding getEncoding()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block build()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public BlockBuilder newBlockBuilderLike(BlockBuilderStatus blockBuilderStatus)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        return format("RowBlock{SingleMapBlockWriter=%d, positionCount=%d", numColumns, getPositionCount());
    }

    private void checkColumnIndexToWrite()
    {
        if (currentColumnIndexToWrite >= numColumns) {
            throw new IllegalStateException("currentColumnIndexToWrite is not valid");
        }
    }
}

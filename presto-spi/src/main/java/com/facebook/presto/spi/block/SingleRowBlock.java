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

import static java.lang.String.format;

public class SingleRowBlock
        extends AbstractSingleRowBlock
{
    private static final int INSTANCE_SIZE = ClassLayout.parseClass(SingleRowBlock.class).instanceSize();

    private final Block[] columnBlocks;

    SingleRowBlock(int cellOffset, Block[] columnBlocks)
    {
        super(cellOffset, columnBlocks.length);
        this.columnBlocks = columnBlocks;
    }

    @Override
    protected Block getColumnBlock(int columnIndex)
    {
        return columnBlocks[columnIndex];
    }

    @Override
    public int getPositionCount()
    {
        return numColumns;
    }

    @Override
    public long getSizeInBytes()
    {
        long sizeInBytes = 0;
        for (int i = 0; i < numColumns; i++) {
            sizeInBytes += getColumnBlock(i).getSizeInBytes();
        }
        return sizeInBytes;
    }

    @Override
    public long getRetainedSizeInBytes()
    {
        long retainedSizeInBytes = INSTANCE_SIZE;
        for (int i = 0; i < numColumns; i++) {
            retainedSizeInBytes += getColumnBlock(i).getRetainedSizeInBytes();
        }
        return retainedSizeInBytes;
    }

    @Override
    public void retainedBytesForEachPart(BiConsumer<Object, Long> consumer)
    {
        for (int i = 0; i < numColumns; i++) {
            consumer.accept(columnBlocks[i], columnBlocks[i].getRetainedSizeInBytes());
        }
        consumer.accept(this, (long) INSTANCE_SIZE);
    }

    @Override
    public BlockEncoding getEncoding()
    {
        BlockEncoding[] columnBlockEncodings = new BlockEncoding[numColumns];
        for (int i = 0; i < numColumns; i++) {
            columnBlockEncodings[i] = columnBlocks[i].getEncoding();
        }
        return new SingleRowBlockEncoding(columnBlockEncodings);
    }

    public int getOffset()
    {
        return startOffset;
    }

    @Override
    public String toString()
    {
        return format("SingleRowBlock{numColumns=%d}", numColumns);
    }
}

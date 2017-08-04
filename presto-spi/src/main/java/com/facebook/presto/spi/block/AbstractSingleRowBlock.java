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

import java.util.List;

public abstract class AbstractSingleRowBlock
        implements Block
{
    // in AbstractSingleRowBlock, offset is position-based (consider as cell-based), not entry-based.
    protected final int startOffset;

    protected final int numColumns;

    protected abstract Block getColumnBlock(int columnIndex);

    public AbstractSingleRowBlock(int startOffset, int numColumns)
    {
        this.startOffset = startOffset;
        this.numColumns = numColumns;
    }

    private int getAbsolutePosition(int position)
    {
        if (position < 0 || position >= getPositionCount()) {
            throw new IllegalArgumentException("position is not valid");
        }
        return position + startOffset;
    }

    @Override
    public boolean isNull(int position)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).isNull(position / numColumns);
    }

    @Override
    public byte getByte(int position, int offset)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).getByte(position / numColumns, offset);
    }

    @Override
    public short getShort(int position, int offset)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).getShort(position / numColumns, offset);
    }

    @Override
    public int getInt(int position, int offset)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).getInt(position / numColumns, offset);
    }

    @Override
    public long getLong(int position, int offset)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).getLong(position / numColumns, offset);
    }

    @Override
    public Slice getSlice(int position, int offset, int length)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).getSlice(position / numColumns, offset, length);
    }

    @Override
    public int getSliceLength(int position)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).getSliceLength(position / numColumns);
    }

    @Override
    public int compareTo(int position, int offset, int length, Block otherBlock, int otherPosition, int otherOffset, int otherLength)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).compareTo(position / numColumns, offset, length, otherBlock, otherPosition, otherOffset, otherLength);
    }

    @Override
    public boolean bytesEqual(int position, int offset, Slice otherSlice, int otherOffset, int length)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).bytesEqual(position / numColumns, offset, otherSlice, otherOffset, length);
    }

    @Override
    public int bytesCompare(int position, int offset, int length, Slice otherSlice, int otherOffset, int otherLength)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).bytesCompare(position / numColumns, offset, length, otherSlice, otherOffset, otherLength);
    }

    @Override
    public void writeBytesTo(int position, int offset, int length, BlockBuilder blockBuilder)
    {
        position = getAbsolutePosition(position);
        getColumnBlock(position % numColumns).writeBytesTo(position / numColumns, offset, length, blockBuilder);
    }

    @Override
    public boolean equals(int position, int offset, Block otherBlock, int otherPosition, int otherOffset, int length)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).equals(position / numColumns, offset, otherBlock, otherPosition, otherOffset, length);
    }

    @Override
    public long hash(int position, int offset, int length)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).hash(position / numColumns, offset, length);
    }

    @Override
    public <T> T getObject(int position, Class<T> clazz)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).getObject(position / numColumns, clazz);
    }

    @Override
    public void writePositionTo(int position, BlockBuilder blockBuilder)
    {
        position = getAbsolutePosition(position);
        getColumnBlock(position % numColumns).writePositionTo(position / numColumns, blockBuilder);
    }

    @Override
    public Block getSingleValueBlock(int position)
    {
        position = getAbsolutePosition(position);
        return getColumnBlock(position % numColumns).getSingleValueBlock(position / numColumns);
    }

    @Override
    public long getRegionSizeInBytes(int position, int length)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block copyPositions(List<Integer> positions)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getRegion(int positionOffset, int length)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block copyRegion(int position, int length)
    {
        throw new UnsupportedOperationException();
    }
}

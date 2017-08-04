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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractRowBlock
        implements Block
{
    protected final int numColumns;

    protected abstract Block getColumnBlock(int columnIndex);

    protected abstract int[] getColumnBlockOffsets();

    protected abstract int getOffsetBase();

    protected abstract boolean[] getRowIsNull();

    protected int getColumnBlockOffset(int position)
    {
        return getColumnBlockOffsets()[position + getOffsetBase()];
    }

    AbstractRowBlock(int numColumns)
    {
        if (numColumns <= 0) {
            throw new IllegalArgumentException("Number of columns in RowBlock must be positive");
        }
        this.numColumns = numColumns;
    }

    @Override
    public RowBlockEncoding getEncoding()
    {
        BlockEncoding[] columnBlockEncodings = new BlockEncoding[numColumns];
        for (int i = 0; i < numColumns; i++) {
            columnBlockEncodings[i] = getColumnBlock(i).getEncoding();
        }
        return new RowBlockEncoding(columnBlockEncodings);
    }

    @Override
    public Block copyPositions(List<Integer> positions)
    {
        int[] newOffsets = new int[positions.size() + 1];
        boolean[] newRowIsNull = new boolean[positions.size()];

        List<Integer> columnBlockPositions = new ArrayList<>(positions.size());
        int newPosition = 0;
        for (int position : positions) {
            if (isNull(position)) {
                newRowIsNull[newPosition] = true;
                newOffsets[newPosition + 1] = newOffsets[newPosition];
            }
            else {
                newOffsets[newPosition + 1] = newOffsets[newPosition] + 1;
                columnBlockPositions.add(getColumnBlockOffset(position));
            }
            newPosition++;
        }

        Block[] newColumnBlocks = new Block[numColumns];
        for (int i = 0; i < numColumns; i++) {
            newColumnBlocks[i] = getColumnBlock(i).copyPositions(columnBlockPositions);
        }
        return new RowBlock(0, positions.size(), newRowIsNull, newOffsets, newColumnBlocks);
    }

    @Override
    public Block getRegion(int position, int length)
    {
        int positionCount = getPositionCount();
        if (position < 0 || length < 0 || position + length > positionCount) {
            throw new IndexOutOfBoundsException("Invalid position " + position + " in block with " + positionCount + " positions");
        }

        if (position == 0 && length == positionCount) {
            return this;
        }

        return new RowBlock(position + getOffsetBase(), length, getRowIsNull(), getColumnBlockOffsets(), getColumnBlocks());
    }

    @Override
    public long getRegionSizeInBytes(int position, int length)
    {
        int positionCount = getPositionCount();
        if (position < 0 || length < 0 || position + length > positionCount) {
            throw new IndexOutOfBoundsException("Invalid position " + position + " in block with " + positionCount + " positions");
        }

        int startColumnBlockOffset = getColumnBlockOffset(position);
        int endColumnBlockOffset = getColumnBlockOffset(position + length);
        int columnBlockLength = endColumnBlockOffset - startColumnBlockOffset;

        long regionSizeInBytes = (Integer.BYTES + Byte.BYTES) * (long) length;
        for (int i = 0; i < numColumns; i++) {
            regionSizeInBytes += getColumnBlock(i).getRegionSizeInBytes(startColumnBlockOffset, columnBlockLength);
        }
        return regionSizeInBytes;
    }

    @Override
    public Block copyRegion(int position, int length)
    {
        int positionCount = getPositionCount();
        if (position < 0 || length < 0 || position + length > positionCount) {
            throw new IndexOutOfBoundsException("Invalid position " + position + " in block with " + positionCount + " positions");
        }

        int startColumnBlockOffset = getColumnBlockOffset(position);
        int endColumnBlockOffset = getColumnBlockOffset(position + length);
        int columnBlockLength = endColumnBlockOffset - startColumnBlockOffset;
        Block[] newColumnBlocks = new Block[numColumns];
        for (int i = 0; i < numColumns; i++) {
            newColumnBlocks[i] = getColumnBlock(i).copyRegion(startColumnBlockOffset, columnBlockLength);
        }

        int[] newOffsets = new int[length + 1];
        for (int i = 1; i < newOffsets.length; i++) {
            newOffsets[i] = getColumnBlockOffset(position + i) - startColumnBlockOffset;
        }
        boolean[] newRowIsNull = Arrays.copyOfRange(getRowIsNull(), position + getOffsetBase(), position + getOffsetBase() + length);
        return new RowBlock(0, length, newRowIsNull, newOffsets, newColumnBlocks);
    }

    @Override
    public <T> T getObject(int position, Class<T> clazz)
    {
        if (clazz != Block.class) {
            throw new IllegalArgumentException("clazz must be Block.class");
        }
        checkReadablePosition(position);

        return clazz.cast(new SingleRowBlock(getColumnBlockOffset(position) * numColumns, getColumnBlocks()));
    }

    @Override
    public void writePositionTo(int position, BlockBuilder blockBuilder)
    {
        checkReadablePosition(position);
        BlockBuilder entryBuilder = blockBuilder.beginBlockEntry();
        int columnBlockOffset = getColumnBlockOffset(position);
        for (int i = 0; i < numColumns; i++) {
            if (getColumnBlock(i).isNull(columnBlockOffset)) {
                entryBuilder.appendNull();
            }
            else {
                getColumnBlock(i).writePositionTo(columnBlockOffset, entryBuilder);
                entryBuilder.closeEntry();
            }
        }
    }

    @Override
    public Block getSingleValueBlock(int position)
    {
        checkReadablePosition(position);

        int startColumnBlockOffset = getColumnBlockOffset(position);
        int endColumnBlockOffset = getColumnBlockOffset(position + 1);
        int columnBlockLength = endColumnBlockOffset - startColumnBlockOffset;
        Block[] newColumnBlocks = new Block[numColumns];
        for (int i = 0; i < numColumns; i++) {
            newColumnBlocks[i] = getColumnBlock(i).copyRegion(startColumnBlockOffset, columnBlockLength);
        }
        boolean[] newRowIsNull = new boolean[] {isNull(position)};
        int[] newOffsets = new int[] {0, columnBlockLength};

        return new RowBlock(0, 1, newRowIsNull, newOffsets, newColumnBlocks);
    }

    @Override
    public boolean isNull(int position)
    {
        checkReadablePosition(position);
        return getRowIsNull()[position + getOffsetBase()];
    }

    // This method should be overridden when more efficient implementation is possible.
    protected Block[] getColumnBlocks()
    {
        Block[] columnBlocks = new Block[numColumns];
        for (int i = 0; i < numColumns; i++) {
            columnBlocks[i] = getColumnBlock(i);
        }
        return columnBlocks;
    }

    private void checkReadablePosition(int position)
    {
        if (position < 0 || position >= getPositionCount()) {
            throw new IllegalArgumentException("position is not valid");
        }
    }
}

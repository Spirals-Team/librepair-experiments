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

import com.facebook.presto.spi.type.TypeManager;
import io.airlift.slice.SliceInput;
import io.airlift.slice.SliceOutput;

import static io.airlift.slice.Slices.wrappedIntArray;
import static java.util.Objects.requireNonNull;

public class RowBlockEncoding
        implements BlockEncoding
{
    public static final BlockEncodingFactory<RowBlockEncoding> FACTORY = new MapBlockEncodingFactory();
    private static final String NAME = "ROW";

    private final BlockEncoding[] columnBlockEncodings;

    public RowBlockEncoding(BlockEncoding[] columnBlockEncodings)
    {
        this.columnBlockEncodings = requireNonNull(columnBlockEncodings, "columnBlockEncodings is null");
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public void writeBlock(SliceOutput sliceOutput, Block block)
    {
        AbstractRowBlock rowBlock = (AbstractRowBlock) block;

        if (rowBlock.numColumns != columnBlockEncodings.length) {
            throw new IllegalArgumentException(
                    "argument block differs in length (" + rowBlock.numColumns + ") with this encoding (" + columnBlockEncodings.length + ")");
        }

        int positionCount = rowBlock.getPositionCount();

        int offsetBase = rowBlock.getOffsetBase();
        int[] columnBlockOffsets = rowBlock.getColumnBlockOffsets();

        int startColumnBlockOffset = columnBlockOffsets[offsetBase];
        int endColumnBlockOffset = columnBlockOffsets[offsetBase + positionCount];
        for (int i = 0; i < columnBlockEncodings.length; i++) {
            columnBlockEncodings[i].writeBlock(sliceOutput, rowBlock.getColumnBlock(i).getRegion(startColumnBlockOffset, endColumnBlockOffset - startColumnBlockOffset));
        }

        sliceOutput.appendInt(positionCount);
        for (int position = 0; position < positionCount + 1; position++) {
            sliceOutput.writeInt(columnBlockOffsets[offsetBase + position] - startColumnBlockOffset);
        }
        EncoderUtil.encodeNullsAsBits(sliceOutput, block);
    }

    @Override
    public Block readBlock(SliceInput sliceInput)
    {
        Block[] columnBlocks = new Block[columnBlockEncodings.length];
        for (int i = 0; i < columnBlockEncodings.length; i++) {
            columnBlocks[i] = columnBlockEncodings[i].readBlock(sliceInput);
        }

        int positionCount = sliceInput.readInt();
        int[] columnBlockOffsets = new int[positionCount + 1];
        sliceInput.readBytes(wrappedIntArray(columnBlockOffsets));
        boolean[] rowIsNull = EncoderUtil.decodeNullBits(sliceInput, positionCount);
        return new RowBlock(0, positionCount, rowIsNull, columnBlockOffsets, columnBlocks);
    }

    @Override
    public BlockEncodingFactory getFactory()
    {
        return FACTORY;
    }

    public static class MapBlockEncodingFactory
            implements BlockEncodingFactory<RowBlockEncoding>
    {
        @Override
        public String getName()
        {
            return NAME;
        }

        @Override
        public RowBlockEncoding readEncoding(TypeManager typeManager, BlockEncodingSerde serde, SliceInput input)
        {
            int numColumns = input.readInt();
            BlockEncoding[] columnBlockEncodings = new BlockEncoding[numColumns];
            for (int i = 0; i < numColumns; i++) {
                columnBlockEncodings[i] = serde.readBlockEncoding(input);
            }
            return new RowBlockEncoding(columnBlockEncodings);
        }

        @Override
        public void writeEncoding(BlockEncodingSerde serde, SliceOutput output, RowBlockEncoding blockEncoding)
        {
            output.appendInt(blockEncoding.columnBlockEncodings.length);
            for (BlockEncoding columnBlockEncoding : blockEncoding.columnBlockEncodings) {
                serde.writeBlockEncoding(output, columnBlockEncoding);
            }
        }
    }
}

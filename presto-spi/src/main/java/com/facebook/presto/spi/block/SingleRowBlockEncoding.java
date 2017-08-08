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

import static java.util.Objects.requireNonNull;

public class SingleRowBlockEncoding
        implements BlockEncoding
{
    public static final BlockEncodingFactory<SingleRowBlockEncoding> FACTORY = new SingleRowBlockEncodingFactory();
    private static final String NAME = "ROW_ELEMENT";

    private final BlockEncoding[] columnBlockEncodings;

    public SingleRowBlockEncoding(BlockEncoding[] columnBlockEncodings)
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
        SingleRowBlock singleRowBlock = (SingleRowBlock) block;
        int columnOffset = singleRowBlock.getOffset() / columnBlockEncodings.length;
        for (int i = 0; i < columnBlockEncodings.length; i++) {
            columnBlockEncodings[i].writeBlock(sliceOutput, singleRowBlock.getColumnBlock(i).getRegion(columnOffset, 1));
        }
    }

    @Override
    public Block readBlock(SliceInput sliceInput)
    {
        Block[] columnBlocks = new Block[columnBlockEncodings.length];
        for (int i = 0; i < columnBlocks.length; i++) {
            columnBlocks[i] = columnBlockEncodings[i].readBlock(sliceInput);
        }
        return new SingleRowBlock(0, columnBlocks);
    }

    @Override
    public BlockEncodingFactory getFactory()
    {
        return FACTORY;
    }

    public static class SingleRowBlockEncodingFactory
            implements BlockEncodingFactory<SingleRowBlockEncoding>
    {
        @Override
        public String getName()
        {
            return NAME;
        }

        @Override
        public SingleRowBlockEncoding readEncoding(TypeManager typeManager, BlockEncodingSerde serde, SliceInput input)
        {
            int numColumns = input.readInt();
            BlockEncoding[] columnBlockEncodings = new BlockEncoding[numColumns];
            for (int i = 0; i < numColumns; i++) {
                columnBlockEncodings[i] = serde.readBlockEncoding(input);
            }
            return new SingleRowBlockEncoding(columnBlockEncodings);
        }

        @Override
        public void writeEncoding(BlockEncodingSerde serde, SliceOutput output, SingleRowBlockEncoding blockEncoding)
        {
            output.appendInt(blockEncoding.columnBlockEncodings.length);
            for (BlockEncoding columnBlockEncoding : blockEncoding.columnBlockEncodings) {
                serde.writeBlockEncoding(output, columnBlockEncoding);
            }
        }
    }
}

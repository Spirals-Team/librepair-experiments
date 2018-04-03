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

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static com.facebook.presto.spi.block.BlockUtil.calculateBlockResetSize;
import static com.facebook.presto.spi.block.BlockUtil.checkArrayRange;
import static com.facebook.presto.spi.block.BlockUtil.checkValidRegion;
import static io.airlift.slice.SizeOf.sizeOf;
import static java.lang.Math.max;

public class ShortArrayBlockBuilder
        implements BlockBuilder
{
    private static final int INSTANCE_SIZE = ClassLayout.parseClass(ShortArrayBlockBuilder.class).instanceSize();

    @Nullable
    private BlockBuilderStatus blockBuilderStatus;
    private boolean initialized;
    private int initialEntryCount;

    private int positionCount;

    // it is assumed that these arrays are the same length
    private boolean[] valueIsNull = new boolean[0];
    private short[] values = new short[0];

    private long retainedSizeInBytes;

    public ShortArrayBlockBuilder(@Nullable BlockBuilderStatus blockBuilderStatus, int expectedEntries)
    {
        this.blockBuilderStatus = blockBuilderStatus;
        this.initialEntryCount = max(expectedEntries, 1);

        updateDataSize();
    }

    @Override
    public BlockBuilder writeShort(int value)
    {
        if (values.length <= positionCount) {
            growCapacity();
        }

        values[positionCount] = (short) value;

        positionCount++;
        if (blockBuilderStatus != null) {
            blockBuilderStatus.addBytes(Byte.BYTES + Short.BYTES);
        }
        return this;
    }

    @Override
    public BlockBuilder closeEntry()
    {
        return this;
    }

    @Override
    public BlockBuilder appendNull()
    {
        if (values.length <= positionCount) {
            growCapacity();
        }

        valueIsNull[positionCount] = true;

        positionCount++;
        if (blockBuilderStatus != null) {
            blockBuilderStatus.addBytes(Byte.BYTES + Short.BYTES);
        }
        return this;
    }

    @Override
    public Block build()
    {
        return new ShortArrayBlock(positionCount, valueIsNull, values);
    }

    @Override
    public BlockBuilder newBlockBuilderLike(BlockBuilderStatus blockBuilderStatus)
    {
        return new ShortArrayBlockBuilder(blockBuilderStatus, calculateBlockResetSize(positionCount));
    }

    private void growCapacity()
    {
        int newSize;
        if (initialized) {
            newSize = BlockUtil.calculateNewArraySize(values.length);
        }
        else {
            newSize = initialEntryCount;
            initialized = true;
        }

        valueIsNull = Arrays.copyOf(valueIsNull, newSize);
        values = Arrays.copyOf(values, newSize);
        updateDataSize();
    }

    private void updateDataSize()
    {
        retainedSizeInBytes = INSTANCE_SIZE + sizeOf(valueIsNull) + sizeOf(values);
        if (blockBuilderStatus != null) {
            retainedSizeInBytes += BlockBuilderStatus.INSTANCE_SIZE;
        }
    }

    @Override
    public long getSizeInBytes()
    {
        return (Short.BYTES + Byte.BYTES) * (long) positionCount;
    }

    @Override
    public long getRegionSizeInBytes(int position, int length)
    {
        return (Short.BYTES + Byte.BYTES) * (long) length;
    }

    @Override
    public long getRetainedSizeInBytes()
    {
        return retainedSizeInBytes;
    }

    @Override
    public void retainedBytesForEachPart(BiConsumer<Object, Long> consumer)
    {
        consumer.accept(values, sizeOf(values));
        consumer.accept(valueIsNull, sizeOf(valueIsNull));
        consumer.accept(this, (long) INSTANCE_SIZE);
    }

    @Override
    public int getPositionCount()
    {
        return positionCount;
    }

    @Override
    public short getShort(int position, int offset)
    {
        checkReadablePosition(position);
        if (offset != 0) {
            throw new IllegalArgumentException("offset must be zero");
        }
        return values[position];
    }

    @Override
    public boolean isNull(int position)
    {
        checkReadablePosition(position);
        return valueIsNull[position];
    }

    @Override
    public void writePositionTo(int position, BlockBuilder blockBuilder)
    {
        checkReadablePosition(position);
        blockBuilder.writeShort(values[position]);
    }

    @Override
    public Block getSingleValueBlock(int position)
    {
        checkReadablePosition(position);
        return new ShortArrayBlock(
                1,
                new boolean[] {valueIsNull[position]},
                new short[] {values[position]});
    }

    @Override
    public Block copyPositions(int[] positions, int offset, int length)
    {
        checkArrayRange(positions, offset, length);

        boolean[] newValueIsNull = new boolean[length];
        short[] newValues = new short[length];
        for (int i = 0; i < length; i++) {
            int position = positions[offset + i];
            checkReadablePosition(position);
            newValueIsNull[i] = valueIsNull[position];
            newValues[i] = values[position];
        }
        return new ShortArrayBlock(length, newValueIsNull, newValues);
    }

    @Override
    public Block getRegion(int positionOffset, int length)
    {
        checkValidRegion(getPositionCount(), positionOffset, length);

        return new ShortArrayBlock(positionOffset, length, valueIsNull, values);
    }

    @Override
    public Block copyRegion(int positionOffset, int length)
    {
        checkValidRegion(getPositionCount(), positionOffset, length);

        boolean[] newValueIsNull = Arrays.copyOfRange(valueIsNull, positionOffset, positionOffset + length);
        short[] newValues = Arrays.copyOfRange(values, positionOffset, positionOffset + length);
        return new ShortArrayBlock(length, newValueIsNull, newValues);
    }

    @Override
    public BlockEncoding getEncoding()
    {
        return new ShortArrayBlockEncoding();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("ShortArrayBlockBuilder{");
        sb.append("positionCount=").append(getPositionCount());
        sb.append('}');
        return sb.toString();
    }

    private void checkReadablePosition(int position)
    {
        if (position < 0 || position >= getPositionCount()) {
            throw new IllegalArgumentException("position is not valid");
        }
    }
}

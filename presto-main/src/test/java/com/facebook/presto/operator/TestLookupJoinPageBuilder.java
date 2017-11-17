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

import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.block.BlockBuilder;
import com.facebook.presto.spi.block.BlockBuilderStatus;
import com.facebook.presto.spi.type.Type;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import java.util.List;

import static com.facebook.presto.spi.type.BigintType.BIGINT;
import static java.util.Arrays.setAll;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TestLookupJoinPageBuilder
{
    @Test
    public void testPageBuilder()
            throws Exception
    {
        int entries = 10_000;
        BlockBuilder blockBuilder = BIGINT.createBlockBuilder(new BlockBuilderStatus(), entries);
        for (int i = 0; i < entries; i++) {
            BIGINT.writeLong(blockBuilder, i);
        }
        Block block = blockBuilder.build();
        Page page = new Page(block, block);

        JoinProbe probe = new TestJoinProbe(page);
        LookupSource lookupSource = new TestLookupSource(ImmutableList.of(BIGINT, BIGINT), page);
        LookupJoinPageBuilder lookupJoinPageBuilder = new LookupJoinPageBuilder(ImmutableList.of(BIGINT, BIGINT));

        int joinPosition = 0;
        while (!lookupJoinPageBuilder.isFull()) {
            lookupJoinPageBuilder.appendRow(probe, lookupSource, joinPosition++);
            lookupJoinPageBuilder.appendNullForBuild(probe);
            if (!probe.advanceNextPosition()) {
                break;
            }
        }
        assertFalse(lookupJoinPageBuilder.isEmpty());

        Page output = lookupJoinPageBuilder.build(probe);
        assertEquals(output.getChannelCount(), 4);
        for (int i = 0; i < output.getPositionCount(); i++) {
            assertFalse(output.getBlock(0).isNull(i));
            assertFalse(output.getBlock(1).isNull(i));
            assertEquals(output.getBlock(0).getLong(i, 0), i / 2);
            assertEquals(output.getBlock(1).getLong(i, 0), i / 2);
            if (i % 2 == 0) {
                assertFalse(output.getBlock(2).isNull(i));
                assertFalse(output.getBlock(3).isNull(i));
                assertEquals(output.getBlock(2).getLong(i, 0), i / 2);
                assertEquals(output.getBlock(3).getLong(i, 0), i / 2);
            }
            else {
                assertTrue(output.getBlock(2).isNull(i));
                assertTrue(output.getBlock(3).isNull(i));
            }
        }
        assertTrue(lookupJoinPageBuilder.toString().contains("positionCount=" + output.getPositionCount()));

        lookupJoinPageBuilder.reset();
        assertTrue(lookupJoinPageBuilder.isEmpty());
    }

    private final class TestLookupSource
            implements LookupSource
    {
        private final List<Type> types;
        private final Page page;

        public TestLookupSource(List<Type> types, Page page)
        {
            this.types = types;
            this.page = page;
        }

        @Override
        public int getChannelCount()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getJoinPositionCount()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public long joinPositionWithinPartition(long joinPosition)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getInMemorySizeInBytes()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getJoinPosition(int position, Page page, Page allChannelsPage, long rawHash)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getJoinPosition(int position, Page hashChannelsPage, Page allChannelsPage)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getNextJoinPosition(long currentJoinPosition, int probePosition, Page allProbeChannelsPage)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isJoinPositionEligible(long currentJoinPosition, int probePosition, Page allProbeChannelsPage)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void appendTo(long position, PageBuilder pageBuilder, int outputChannelOffset)
        {
            for (int i = 0; i < types.size(); i++) {
                types.get(i).appendTo(page.getBlock(i), (int) position, pageBuilder.getBlockBuilder(i));
            }
        }

        @Override
        public void close()
        {
        }
    }

    private final class TestJoinProbe
            implements JoinProbe
    {
        private final Page page;
        private int position = 0;

        public TestJoinProbe(Page page)
        {
            this.page = page;
        }

        @Override
        public int getOutputChannelCount()
        {
            return page.getChannelCount();
        }

        @Override
        public int[] getOutputChannels()
        {
            int[] channels = new int[getOutputChannelCount()];
            setAll(channels, i -> i);
            return channels;
        }

        @Override
        public boolean advanceNextPosition()
        {
            position++;
            return position < page.getPositionCount();
        }

        @Override
        public long getCurrentJoinPosition(LookupSource lookupSource)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getPosition()
        {
            return position;
        }

        @Override
        public Page getPage()
        {
            return page;
        }
    }
}

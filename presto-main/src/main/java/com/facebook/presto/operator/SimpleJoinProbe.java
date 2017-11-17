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
import com.facebook.presto.spi.block.Block;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static com.facebook.presto.spi.type.BigintType.BIGINT;

public class SimpleJoinProbe
        implements JoinProbe
{
    public static class SimpleJoinProbeFactory
            implements JoinProbeFactory
    {
        private int[] probeOutputChannels;
        private List<Integer> probeJoinChannels;
        private final OptionalInt probeHashChannel;

        public SimpleJoinProbeFactory(int[] probeOutputChannels, List<Integer> probeJoinChannels, OptionalInt probeHashChannel)
        {
            this.probeOutputChannels = probeOutputChannels;
            this.probeJoinChannels = probeJoinChannels;
            this.probeHashChannel = probeHashChannel;
        }

        @Override
        public JoinProbe createJoinProbe(Page page)
        {
            return new SimpleJoinProbe(probeOutputChannels, page, probeJoinChannels, probeHashChannel);
        }
    }

    private final int[] probeOutputChannels;
    private final int positionCount;
    private final Block[] blocks;
    private final Block[] probeBlocks;
    private final Page page;
    private final Page probePage;
    private final Optional<Block> probeHashBlock;

    private int position = -1;

    private SimpleJoinProbe(int[] probeOutputChannels, Page page, List<Integer> probeJoinChannels, OptionalInt hashChannel)
    {
        this.probeOutputChannels = probeOutputChannels;
        this.positionCount = page.getPositionCount();
        this.blocks = new Block[page.getChannelCount()];
        this.probeBlocks = new Block[probeJoinChannels.size()];

        for (int i = 0; i < page.getChannelCount(); i++) {
            blocks[i] = page.getBlock(i);
        }

        for (int i = 0; i < probeJoinChannels.size(); i++) {
            probeBlocks[i] = blocks[probeJoinChannels.get(i)];
        }
        this.page = page;
        this.probePage = new Page(page.getPositionCount(), probeBlocks);
        this.probeHashBlock = hashChannel.isPresent() ? Optional.of(page.getBlock(hashChannel.getAsInt())) : Optional.empty();
    }

    @Override
    public int getOutputChannelCount()
    {
        return probeOutputChannels.length;
    }

    @Override
    public boolean advanceNextPosition()
    {
        position++;
        return position < positionCount;
    }

    @Override
    public int[] getOutputChannels()
    {
        return probeOutputChannels;
    }

    @Override
    public long getCurrentJoinPosition(LookupSource lookupSource)
    {
        if (currentRowContainsNull()) {
            return -1;
        }
        if (probeHashBlock.isPresent()) {
            long rawHash = BIGINT.getLong(probeHashBlock.get(), position);
            return lookupSource.getJoinPosition(position, probePage, page, rawHash);
        }
        return lookupSource.getJoinPosition(position, probePage, page);
    }

    private boolean currentRowContainsNull()
    {
        for (Block probeBlock : probeBlocks) {
            if (probeBlock.isNull(position)) {
                return true;
            }
        }
        return false;
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

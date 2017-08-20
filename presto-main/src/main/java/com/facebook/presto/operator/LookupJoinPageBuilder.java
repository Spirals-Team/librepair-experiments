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
import com.facebook.presto.spi.block.DictionaryBlock;
import com.facebook.presto.spi.block.LazyBlock;
import com.facebook.presto.spi.type.Type;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.facebook.presto.spi.block.PageBuilderStatus.DEFAULT_MAX_PAGE_SIZE_IN_BYTES;
import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * The page builder creates a page with dictionary blocks only
 * the build side blocks are copied from the build pages;
 * while the probe side blocks are dictionary views on top of the probe pages
 * TODO: may extend the build side to dictionary as well
 */
public class LookupJoinPageBuilder
{
    private final IntArrayList probeIndexBuilder = new IntArrayList();
    private final Set<Integer> probeIndices = new HashSet<>();
    private final PageBuilder buildPageBuilder;
    private final int buildOutputChannelCount;
    private int probeBlockBytes;

    public LookupJoinPageBuilder(List<Type> buildTypes)
    {
        this.buildPageBuilder = new PageBuilder(buildTypes);
        this.buildOutputChannelCount = buildTypes.size();
    }

    public boolean isFull()
    {
        return probeBlockBytes + buildPageBuilder.getSizeInBytes() >= DEFAULT_MAX_PAGE_SIZE_IN_BYTES || buildPageBuilder.isFull();
    }

    public boolean isEmpty()
    {
        return probeIndexBuilder.isEmpty() && buildPageBuilder.isEmpty();
    }

    public void reset()
    {
        probeIndexBuilder.clear();
        probeIndices.clear();
        buildPageBuilder.reset();
        probeBlockBytes = 0;
    }

    /**
     * append the index for the probe and copy the row for the build
     */
    public void appendRow(JoinProbe probe, LookupSource lookupSource, long joinPosition)
    {
        buildPageBuilder.declarePosition();
        // write probe columns
        appendProbeIndex(probe);
        // write build columns
        lookupSource.appendTo(joinPosition, buildPageBuilder, 0);
    }

    /**
     * append the index for the probe and append nulls for the build
     */
    public void appendNull(JoinProbe probe, LookupSource lookupSource)
    {
        buildPageBuilder.declarePosition();
        // write probe columns
        appendProbeIndex(probe);
        // write nulls into build columns
        for (int i = 0; i < lookupSource.getChannelCount(); i++) {
            buildPageBuilder.getBlockBuilder(i).appendNull();
        }
    }

    public Page build(JoinProbe probe)
    {
        Block[] blocks = new Block[probe.getOutputChannelCount() + buildOutputChannelCount];
        int[] probeIndices = probeIndexBuilder.toIntArray();
        for (int i = 0; i < probe.getOutputChannelCount(); i++) {
            Block block = probe.getPage().getBlock(probe.getOutputChannels()[i]);
            if (block instanceof LazyBlock) {
                // TODO: on one hand, we shouldn't load the block at this point; on the other, we cannot wrap a lazy block inside a dictionary
                block = ((LazyBlock) block).getBlock();
            }
            blocks[i] = block instanceof DictionaryBlock ? ((DictionaryBlock) block).mask(probeIndices) : new DictionaryBlock(block, probeIndices);
        }

        int[] dictionaryIds = new int[buildPageBuilder.getPositionCount()];
        for (int i = 0; i < buildPageBuilder.getPositionCount(); i++) {
            dictionaryIds[i] = i;
        }
        for (int i = 0; i < buildOutputChannelCount; i++) {
            // make sure all the blocks are dictionary blocks
            blocks[probe.getOutputChannelCount() + i] = new DictionaryBlock(buildPageBuilder.getBlockBuilder(i).build(), dictionaryIds);
        }
        return new Page(buildPageBuilder.getPositionCount(), blocks);
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("estimatedSize", probeBlockBytes + buildPageBuilder.getSizeInBytes())
                .add("positionCount", buildPageBuilder.getPositionCount())
                .toString();
    }

    private void appendProbeIndex(JoinProbe probe)
    {
        int position = probe.getPosition();
        probeIndexBuilder.add(position);
        if (probeIndices.contains(position)) {
            return;
        }
        probeIndices.add(position);
        for (int index : probe.getOutputChannels()) {
            // be aware that getRegionSizeInBytes could be expensive
            probeBlockBytes += probe.getPage().getBlock(index).getRegionSizeInBytes(position, 1);
        }
    }
}

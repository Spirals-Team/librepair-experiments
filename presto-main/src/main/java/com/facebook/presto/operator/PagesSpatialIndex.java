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

import com.esri.core.geometry.ogc.OGCGeometry;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.type.Type;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.index.strtree.STRtree;
import io.airlift.slice.Slice;
import it.unimi.dsi.fastutil.longs.LongArrayList;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.presto.geospatial.GeometryUtils.deserialize;
import static com.facebook.presto.operator.SyntheticAddress.decodePosition;
import static com.facebook.presto.operator.SyntheticAddress.decodeSliceIndex;
import static java.util.Objects.requireNonNull;

public class PagesSpatialIndex
{
    private final LongArrayList addresses;
    private final List<Type> types;
    private final List<Integer> outputChannels;
    private final List<List<Block>> channels;
    private final STRtree rtree = new STRtree();

    private static final class GeometryWithAddress
    {
        private final OGCGeometry ogcGeometry;
        private final long address;

        private GeometryWithAddress(OGCGeometry ogcGeometry, long address)
        {
            this.ogcGeometry = ogcGeometry;
            this.address = address;
        }

        public OGCGeometry getOgcGeometry()
        {
            return ogcGeometry;
        }

        public long getAddress()
        {
            return address;
        }
    }

    public PagesSpatialIndex(
            LongArrayList addresses,
            List<Type> types,
            List<Integer> outputChannels,
            List<List<Block>> channels,
            List<Block> geometryChannel)
    {
        this.addresses = requireNonNull(addresses, "addresses is null");
        this.types = types;
        this.outputChannels = outputChannels;
        this.channels = requireNonNull(channels, "channels is null");
        requireNonNull(geometryChannel, "geometryChannel is null");

        for (int position = 0; position < addresses.size(); position++) {
            long pageAddress = addresses.getLong(position);
            int blockIndex = decodeSliceIndex(pageAddress);
            int blockPosition = decodePosition(pageAddress);

            Block block = geometryChannel.get(blockIndex);
            Slice slice = block.getSlice(blockPosition, 0, block.getSliceLength(blockPosition));
            OGCGeometry ogcGeometry = deserialize(slice);

            rtree.insert(getEnvelope(ogcGeometry), new GeometryWithAddress(ogcGeometry, pageAddress));
        }

        rtree.query(new Envelope(0.0, 0.0, 0.0, 0.0));
    }

    private static Envelope getEnvelope(OGCGeometry ogcGeometry)
    {
        com.esri.core.geometry.Envelope env = new com.esri.core.geometry.Envelope();
        ogcGeometry.getEsriGeometry().queryEnvelope(env);

        return new Envelope(env.getXMin(), env.getXMax(), env.getYMin(), env.getYMax());
    }

    // Returns an array of addresses from PagesIndex.valueAddresses
    public List<Long> findJoinAddresses(OGCGeometry probeGeometry)
    {
        final List<Long> matchingAddresses = new ArrayList<>();

        Envelope envelope = getEnvelope(probeGeometry);
        rtree.query(envelope, o -> {
            GeometryWithAddress geometryWithAddress = (GeometryWithAddress) o;
            // TODO Add support for intersects and distance-within relationships
            if (geometryWithAddress.getOgcGeometry().contains(probeGeometry)) {
                matchingAddresses.add(geometryWithAddress.getAddress());
            }
        });

        return matchingAddresses;
    }

    public void appendTo(long joinAddress, PageBuilder pageBuilder, int outputChannelOffset)
    {
        int blockIndex = decodeSliceIndex(joinAddress);
        int blockPosition = decodePosition(joinAddress);

        for (int outputIndex : outputChannels) {
            Type type = types.get(outputIndex);
            List<Block> channel = channels.get(outputIndex);
            Block block = channel.get(blockIndex);
            type.appendTo(block, blockPosition, pageBuilder.getBlockBuilder(outputChannelOffset));
            outputChannelOffset++;
        }
    }
}

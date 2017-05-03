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
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparator;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkState;
import static io.airlift.slice.SizeOf.sizeOf;
import static java.util.Objects.requireNonNull;

/**
 * This class assumes that lessThanFunction is a superset of the whole filtering
 * condition used in a join. In other words, we can use SortedPositionLinks
 * with following join condition:
 *
 * filterFunction_1(...) AND filterFunction_2(....) AND ... AND filterFunction_n(...)
 *
 * by passing any of the filterFunction_i to the SortedPositionLinks. We could not
 * do that for join condition like:
 *
 * filterFunction_1(...) OR filterFunction_2(....) OR ... OR filterFunction_n(...)
 *
 * To use lessThanFunction in this class, it must be an expression in form of:
 *
 * f(probeColumn1, probeColumn2, ..., probeColumnN) COMPARE g(buildColumn1, ..., buildColumnN)
 *
 * where COMPARE is one of: < <= > >=
 *
 * That allows us to define an order of the elements in positionLinks (this defining which
 * element is smaller) using g(...) function and to perform a binary search using
 * f(probePosition) value.
 */
public final class SortedPositionLinks
        implements PositionLinks
{
    public static class Builder implements PositionLinks.Builder
    {
        private final Int2ObjectMap<IntArrayList> positionLinks;
        private final int size;
        private final IntComparator comparator;

        public Builder(int size, IntComparator comparator)
        {
            this.comparator = comparator;
            this.size = size;
            positionLinks = new Int2ObjectOpenHashMap<>();
        }

        @Override
        public int link(int from, int to)
        {
            // make sure that from value is the smaller one
            if (comparator.compare(from, to) > 0) {
                // _from_ is larger so, just add to current chain _to_
                List<Integer> links = positionLinks.computeIfAbsent(to, key -> new IntArrayList());
                links.add(from);
                return to;
            }
            else {
                // _to_ is larger so, move the chain to _from_
                IntArrayList links = positionLinks.remove(to);
                if (links == null) {
                    links = new IntArrayList();
                }
                links.add(to);
                checkState(positionLinks.putIfAbsent(from, links) == null, "sorted links is corrupted");
                return from;
            }
        }

        @Override
        public Function<Optional<JoinFilterFunction>, PositionLinks> build()
        {
            ArrayPositionLinks.Builder builder = ArrayPositionLinks.builder(size);
            int[][] sortedPositionLinks = new int[size][];

            for (Int2ObjectMap.Entry<IntArrayList> entry : positionLinks.int2ObjectEntrySet()) {
                int key = entry.getIntKey();
                IntArrayList positions = entry.getValue();
                positions.sort(comparator);

                sortedPositionLinks[key] = new int[positions.size()];
                for (int i = 0; i < positions.size(); i++) {
                    sortedPositionLinks[key][i] = positions.get(i);
                }

                // ArrayPositionsLinks.Builder::link builds position links from
                // tail to head, so we must add them in descending order to have
                // smallest element as a head
                for (int i = positions.size() - 2; i >= 0; i--) {
                    builder.link(positions.get(i), positions.get(i + 1));
                }

                // add link from starting position to position links chain
                if (!positions.isEmpty()) {
                    builder.link(key, positions.get(0));
                }
            }

            return lessThanFunction -> {
                checkState(lessThanFunction.isPresent(), "Using SortedPositionLinks without lessThanFunction");
                return new SortedPositionLinks(
                        builder.build().apply(lessThanFunction),
                        sortedPositionLinks,
                        lessThanFunction.get());
            };
        }
    }

    private final PositionLinks positionLinks;
    private final int[][] sortedPositionLinks;
    private final JoinFilterFunction lessThanFunction;
    private final long sizeInBytes;

    private SortedPositionLinks(PositionLinks positionLinks, int[][] sortedPositionLinks, JoinFilterFunction lessThanFunction)
    {
        this.positionLinks = requireNonNull(positionLinks, "positionLinks is null");
        this.sortedPositionLinks = requireNonNull(sortedPositionLinks, "sortedPositionLinks is null");
        this.lessThanFunction = requireNonNull(lessThanFunction, "lessThanFunction is null");
        this.sizeInBytes = positionLinks.getSizeInBytes() + sizeOf(sortedPositionLinks);
    }

    public static Builder builder(int size, IntComparator comparator)
    {
        return new Builder(size, comparator);
    }

    @Override
    public int next(int position, int probePosition, Page allProbeChannelsPage)
    {
        int nextPosition = positionLinks.next(position, probePosition, allProbeChannelsPage);
        if (nextPosition < 0) {
            return -1;
        }
        // break a position links chain if next position should be filtered out
        if (applyLessThanFunction(nextPosition, probePosition, allProbeChannelsPage)) {
            return nextPosition;
        }
        return -1;
    }

    @Override
    public int start(int startingPosition, int probePosition, Page allProbeChannelsPage)
    {
        // check if filtering function to startingPosition
        if (applyLessThanFunction(startingPosition, probePosition, allProbeChannelsPage)) {
            return startingPosition;
        }

        if (sortedPositionLinks[startingPosition] == null) {
            return -1;
        }

        int left = 0;
        int right = sortedPositionLinks[startingPosition].length - 1;

        // do a binary search for the first position for which filter function applies
        int offset = lowerBound(startingPosition, left, right, probePosition, allProbeChannelsPage);
        if (offset < 0) {
            return -1;
        }
        if (!applyLessThanFunction(startingPosition, offset, probePosition, allProbeChannelsPage)) {
            return -1;
        }
        return sortedPositionLinks[startingPosition][offset];
    }

    /**
     * Find the first element in position links that is NOT smaller than probePosition
     */
    private int lowerBound(int startingPosition, int first, int last, int probePosition, Page allProbeChannelsPage)
    {
        int middle;
        int step;
        int count = last - first;
        while (count > 0) {
            step = count / 2;
            middle = first + step;
            if (!applyLessThanFunction(startingPosition, middle, probePosition, allProbeChannelsPage)) {
                first = ++middle;
                count -= step + 1;
            }
            else {
                count = step;
            }
        }
        return first;
    }

    @Override
    public long getSizeInBytes()
    {
        return sizeInBytes;
    }

    private boolean applyLessThanFunction(int leftPosition, int leftOffset, int rightPosition, Page rightPage)
    {
        return applyLessThanFunction(sortedPositionLinks[leftPosition][leftOffset], rightPosition, rightPage);
    }

    private boolean applyLessThanFunction(long leftPosition, int rightPosition, Page rightPage)
    {
        return lessThanFunction.filter((int) leftPosition, rightPosition, rightPage);
    }
}

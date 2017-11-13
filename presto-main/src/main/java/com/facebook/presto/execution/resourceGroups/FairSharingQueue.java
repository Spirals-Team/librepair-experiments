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
package com.facebook.presto.execution.resourceGroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

final class FairSharingQueue<E>
        implements Queue<E>
{
    private final Map<E, Node<E>> index = new LinkedHashMap<>();

    public boolean addOrUpdate(E element, Usage usage)
    {
        Node<E> node = index.get(element);
        if (node != null) {
            node.update(usage);
            return false;
        }

        node = new Node<>(element, usage);
        index.put(element, node);
        return true;
    }

    @Override
    public E peek()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(E element)
    {
        return index.containsKey(element);
    }

    @Override
    public boolean remove(E element)
    {
        Node<E> node = index.remove(element);
        return node != null;
    }

    @Override
    public E poll()
    {
        Collection<Node<E>> candidates = index.values();
        long totalShare = 0;
        long totalUtilization = 1; // prevent / by zero

        for (Node<E> candidate : candidates) {
            totalShare += candidate.getShare();
            totalUtilization += candidate.getUtilization();
        }

        List<Node<E>> winners = new ArrayList<>();
        double winnerFactor = 1;

        for (Node<E> candidate : candidates) {
            double share = 1.0 * candidate.getUtilization() / totalUtilization;
            double expectedShare = 1.0 * candidate.getShare() / totalShare;
            double candidateFactor = share / expectedShare;

            if (candidateFactor <= winnerFactor) {
                if (candidateFactor < winnerFactor) {
                    winnerFactor = candidateFactor;
                    winners.clear();
                }
                winners.add(candidate);
            }
        }

        return pickWinner(winners);
    }

    private E pickWinner(List<Node<E>> winners)
    {
        if (winners.isEmpty()) {
            return null;
        }

        if (winners.size() == 1) {
            return winners.get(0).getValue();
        }

        long totalShares = winners.stream()
                .mapToLong(Node::getShare)
                .sum();

        long winningTicket = ThreadLocalRandom.current().nextLong(0, totalShares);
        long count = 0;
        for (Node<E> winner : winners) {
            if (count + winner.getShare() > winningTicket) {
                return winner.getValue();
            }
            count += winner.getShare();
        }

        throw new IllegalStateException("A winner should already have been picked");
    }

    @Override
    public int size()
    {
        return index.size();
    }

    @Override
    public boolean isEmpty()
    {
        return index.isEmpty();
    }

    @Override
    public Iterator<E> iterator()
    {
        return index.keySet().iterator();
    }

    public static class Usage
    {
        private int share;
        private int utilization;

        public Usage(int share, int utilization)
        {
            checkArgument(share > 0, "share must be positive");
            checkArgument(utilization >= 0, "usage must be zero or positive");

            this.share = share;
            this.utilization = utilization;
        }

        public int getShare()
        {
            return share;
        }

        public int getUtilization()
        {
            return utilization;
        }
    }

    private static final class Node<E>
    {
        private E value;
        private Usage usage;

        private Node(E value, Usage usage)
        {
            this.value = requireNonNull(value, "value is null");
            this.usage = requireNonNull(usage, "usage is null");
        }

        public E getValue()
        {
            return value;
        }

        public void update(Usage usage)
        {
            this.usage = usage;
        }

        public int getShare()
        {
            return usage.getShare();
        }

        public int getUtilization()
        {
            return usage.getUtilization();
        }
    }
}

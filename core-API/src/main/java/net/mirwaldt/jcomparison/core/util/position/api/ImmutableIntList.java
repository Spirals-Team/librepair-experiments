package net.mirwaldt.jcomparison.core.util.position.api;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public interface ImmutableIntList extends List<Integer> {
    int getInt(int index);

    @Override
    default boolean add(Integer integer) {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    @Override
    default boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    @Override
    default boolean addAll(Collection<? extends Integer> c) {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    @Override
    default boolean addAll(int index, Collection<? extends Integer> c) {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    @Override
    default void clear() {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    @Override
    default Integer set(int index, Integer element) {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    @Override
    default void add(int index, Integer element) {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    @Override
    default Integer remove(int index) {
        throw new UnsupportedOperationException("Not supported for type " + this.getClass().getName());
    }

    IntListComparator COMPARATOR = new IntListComparator();

    default IntListComparator comparator() {
        return COMPARATOR;
    }

    class IntListComparator implements Comparator<ImmutableIntList> {

        @Override
        public int compare(ImmutableIntList leftIntList, ImmutableIntList rightIntList) {
            return compareIntLists(leftIntList, rightIntList);
        }

        public static int compareIntLists(ImmutableIntList leftIntList, ImmutableIntList rightIntList) {
            if(leftIntList == null || rightIntList == null) {
                if(leftIntList == rightIntList) {
                    return 0;
                } else if(leftIntList == null) {
                    return -1;
                } else {
                    return 1;
                }
            } else if(leftIntList == rightIntList) { // handle identity separately and faster
                return 0;
            } else if(leftIntList.size() == rightIntList.size()) {
                for (int index = 0; index < leftIntList.size(); index++) {
                    final int leftInt = leftIntList.getInt(index);
                    final int rightInt = rightIntList.getInt(index);

                    final int comparatorResult = Integer.compare(leftInt, rightInt);

                    if(comparatorResult != 0) {
                        return comparatorResult;
                    }
                }

                return 0;
            } else {
                return Integer.compare(leftIntList.size(), rightIntList.size());
            }
        }
    }
}

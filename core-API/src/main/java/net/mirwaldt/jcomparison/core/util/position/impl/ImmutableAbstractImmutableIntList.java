package net.mirwaldt.jcomparison.core.util.position.impl;

import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
public abstract class ImmutableAbstractImmutableIntList implements ImmutableIntList, Comparable<ImmutableIntList> {

    abstract protected List<Integer> convertToList();

    @Override
    public int compareTo(ImmutableIntList otherImmutableIntList) {
        return IntListComparator.compareIntLists(this, otherImmutableIntList);
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<Integer> iterator() {
        return convertToList().iterator();
    }

    @Override
    public Object[] toArray() {
        return convertToList().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return convertToList().toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return convertToList().containsAll(c);
    }

    @Override
    public Integer get(int index) {
        return getInt(index);
    }

    @Override
    public ListIterator<Integer> listIterator() {
        return convertToList().listIterator();
    }

    @Override
    public ListIterator<Integer> listIterator(int index) {
        return convertToList().listIterator(index);
    }

    @Override
    public List<Integer> subList(int fromIndex, int toIndex) {
        return convertToList().subList(fromIndex, toIndex);
    }
}

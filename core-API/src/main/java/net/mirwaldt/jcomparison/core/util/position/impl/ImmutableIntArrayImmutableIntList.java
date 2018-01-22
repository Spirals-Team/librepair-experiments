package net.mirwaldt.jcomparison.core.util.position.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class ImmutableIntArrayImmutableIntList extends ImmutableAbstractImmutableIntList {
    private final int[] intArray;

    public ImmutableIntArrayImmutableIntList(int[] intArray) {
        if(intArray == null) {
            throw new NullPointerException("null not allowed as constructor parameter for an int array!");
        }
        this.intArray = intArray;
    }

    public static ImmutableIntArrayImmutableIntList newImmutableIntArrayIntList(int...intArray) {
        return new ImmutableIntArrayImmutableIntList(intArray);
    }

    @Override
    public int getInt(int index) {
        if(0 <= index && index < intArray.length) {
            return intArray[index];
        } else {
            // will cause an exception
            convertToList().get(index);
            return -1;
        }
    }

    @Override
    protected List<Integer> convertToList() {
        List<Integer> arrayList = new ArrayList<>();
        for (int i : intArray) {
            arrayList.add(i);
        }
        return arrayList;
    }

    @Override
    public int size() {
        return intArray.length;
    }

    @Override
    public int indexOf(Object o) {
        return convertToList().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return convertToList().lastIndexOf(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (o instanceof ImmutableIntArrayImmutableIntList) {
            ImmutableIntArrayImmutableIntList integers = (ImmutableIntArrayImmutableIntList) o;

            return Arrays.equals(intArray, integers.intArray);
        }

        if (!(o instanceof List)) {
            return false;
        } else {
            List<?> otherList = (List<?>) o;
            if (otherList.size() == intArray.length) {
                int index = 0;
                for (Object otherElement : otherList) {
                    if(otherElement == null || !otherElement.equals(intArray[index++])) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(intArray);
    }

    @Override
    public String toString() {
        return Arrays.toString(intArray);
    }
}

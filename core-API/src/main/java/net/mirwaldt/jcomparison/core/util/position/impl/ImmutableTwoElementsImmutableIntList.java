package net.mirwaldt.jcomparison.core.util.position.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ImmutableTwoElementsImmutableIntList extends ImmutableAbstractImmutableIntList {
    private int firstInt;
    private int secondInt;

    public ImmutableTwoElementsImmutableIntList(int firstInt, int secondInt) {
        this.firstInt = firstInt;
        this.secondInt = secondInt;
    }

    @Override
    public int getInt(int index) {
        if (index == 0) {
            return firstInt;
        }
        if (index == 1) {
            return secondInt;
        } else {
            // will cause an exception
            convertToList().get(index);
            return -1;
        }
    }

    @Override
    public int indexOf(Object o) {
        if (o instanceof Integer) {
            Integer i = (Integer) o;
            if (firstInt == i) {
                return 0;
            } else if (secondInt == i) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o instanceof Integer) {
            Integer i = (Integer) o;
            if (secondInt == i) {
                return 1;
            } else if (firstInt == i) {
                return 0;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    protected List<Integer> convertToList() {
        List<Integer> list = new LinkedList<>();
        list.add(firstInt);
        list.add(secondInt);
        return Collections.unmodifiableList(list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (o instanceof ImmutableTwoElementsImmutableIntList) {
            ImmutableTwoElementsImmutableIntList integers = (ImmutableTwoElementsImmutableIntList) o;

            if (firstInt != integers.firstInt) return false;
            return secondInt == integers.secondInt;
        }

        if (!(o instanceof List)) {
            return false;
        } else {
            List<?> otherList = (List<?>) o;
            if (otherList.size() == 2) {
                Object otherFirstElement = otherList.get(0);
                Object otherSecondElement = otherList.get(1);
                return otherFirstElement != null && otherSecondElement != null
                        && otherFirstElement.equals(firstInt) && otherSecondElement.equals(secondInt);
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        int result = firstInt;
        result = 31 * result + secondInt;
        return result;
    }

    @Override
    public String toString() {
        return "[" + firstInt + ", " + secondInt + "]";
    }
}

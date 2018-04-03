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
public class ImmutableOneElementImmutableIntList extends ImmutableAbstractImmutableIntList {
    private int oneInt;

    public ImmutableOneElementImmutableIntList(int oneInt) {
        this.oneInt = oneInt;
    }

    @Override
    public int getInt(int index) {
        if (index == 0) {
            return oneInt;
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
            if (oneInt == i) {
                return 0;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return indexOf(o);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    protected List<Integer> convertToList() {
        List<Integer> list = new LinkedList<>();
        list.add(oneInt);
        return Collections.unmodifiableList(list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof ImmutableOneElementImmutableIntList) {
            ImmutableOneElementImmutableIntList integers = (ImmutableOneElementImmutableIntList) o;

            return oneInt == integers.oneInt;
        }

        if (!(o instanceof List)) {
            return false;
        } else {
            List<?> otherList = (List<?>) o;
            if (otherList.size() == 1) {
                Object otherElement = otherList.get(0);
                return otherElement != null && otherElement.equals(oneInt);
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return oneInt;
    }

    @Override
    public String toString() {
        return "[" + oneInt + "]";
    }
}

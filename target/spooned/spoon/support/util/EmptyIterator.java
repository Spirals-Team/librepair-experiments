/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.util;


public class EmptyIterator<E> implements java.util.Iterator<E> {
    private static final spoon.support.util.EmptyIterator<java.lang.Object> EMPTY_ITERATOR = new spoon.support.util.EmptyIterator<>();

    public static <T> java.util.Iterator<T> instance() {
        return ((java.util.Iterator<T>) (spoon.support.util.EmptyIterator.EMPTY_ITERATOR));
    }

    EmptyIterator() {
    }

    @java.lang.Override
    public boolean hasNext() {
        return false;
    }

    @java.lang.Override
    public E next() {
        throw new java.util.NoSuchElementException();
    }

    @java.lang.Override
    public void remove() {
        throw new java.lang.IllegalStateException();
    }
}


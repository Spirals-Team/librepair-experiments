package net.coljate.collection.primitive;

import net.coljate.collection.Collection;

import java.util.Iterator;

public interface CharCollection extends CharContainer, Collection<Character> {

    @Override
    default boolean contains(char c) {
        final CharIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (c == iterator.nextChar()) {
                return true;
            }
        }
        return false;
    }

    @Override
    default boolean contains(final Object object) {
        return CharContainer.super.contains(object);
    }

    @Override
    CharIterator iterator();

    interface CharIterator extends Iterator<Character> {

        char nextChar();

        @Override
        default Character next() {
            return this.nextChar();
        }

    }

}

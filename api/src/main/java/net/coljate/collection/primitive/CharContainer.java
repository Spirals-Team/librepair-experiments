package net.coljate.collection.primitive;

import net.coljate.Container;

public interface CharContainer extends Container {

    boolean contains(char c);

    @Override
    default boolean contains(final Object object) {
        return object instanceof Character
                && this.contains(((Character) object).charValue());
    }

}

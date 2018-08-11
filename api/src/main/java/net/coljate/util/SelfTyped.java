package net.coljate.util;

import javax.annotation.Nonnull;

/**
 *
 * @author Ollie
 */
public interface SelfTyped<T extends SelfTyped<T>> {

    @SuppressWarnings("unchecked")
    @Nonnull
    default T self() {
        return (T) this;
    }

}

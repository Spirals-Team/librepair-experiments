package net.coljate.map;

import java.util.Objects;

/**
 *
 * @author Ollie
 */
public abstract class AbstractEntry<K, V> implements Entry<K, V> {

    @Override
    public boolean equals(final Object object) {
        return object instanceof Entry
                && this.equals((Entry<?, ?>) object);
    }

    protected boolean equals(final Entry<?, ?> that) {
        return Objects.equals(this.key(), that.key())
                && Objects.equals(this.value(), that.value());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.key());
        hash = 89 * hash + Objects.hashCode(this.value());
        return hash;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":[" + key() + '=' + value() + ']';
    }

}

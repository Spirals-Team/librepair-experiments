package net.coljate.util.iterator;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class IteratorsTest {

    @Test
    public void testConcat() {

        final Object o1 = new Object();
        final Iterator<Object> i1 = Iterators.of(o1);

        final Object o2 = new Object();
        final Iterator<Object> i2 = Iterators.of(o2);

        final Iterator<Object> concat = Iterators.concat(i1, i2);

        assertTrue(concat.hasNext());
        assertThat(concat.next()).isEqualTo(o1);
        assertTrue(concat.hasNext());
        assertThat(concat.next()).isEqualTo(o2);
        assertFalse(concat.hasNext());

    }

}

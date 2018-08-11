package net.coljate.util;

import net.coljate.util.iterator.Iterators;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class IteratorsTest {

    @Test
    public void testFilter() {
        
        final Object o1 = new Object(), o2 = new Object(), o3 = new Object();
        final Iterator<Object> iterator = Iterators.filter(java.util.Arrays.asList(o1, o2, o3).iterator(), e -> e != o2);

        assertTrue(iterator.hasNext());
        assertThat(iterator.next()).isEqualTo(o1);

        assertTrue(iterator.hasNext());
        assertThat(iterator.next()).isEqualTo(o3);

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);

    }

}

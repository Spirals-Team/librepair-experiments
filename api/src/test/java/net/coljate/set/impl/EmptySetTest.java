package net.coljate.set.impl;

import net.coljate.collection.EmptyTest;
import net.coljate.set.ImmutableSetTest;
import net.coljate.set.SetTest;
import org.junit.jupiter.api.Disabled;

import java.util.Spliterator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EmptySetTest implements ImmutableSetTest<Object>, SetTest.ZeroElementTests<Object>, EmptyTest<Object> {

    @Override
    @Disabled
    public int testSpliterator_Characteristics() {
        final int characteristics = this.createTestCollection().spliterator().characteristics();
        assertThat(characteristics & Spliterator.IMMUTABLE).isEqualTo(Spliterator.IMMUTABLE);
        return characteristics;
    }

    @Override
    public EmptySet<Object> createTestCollection() {
        return EmptySet.instance();
    }

}

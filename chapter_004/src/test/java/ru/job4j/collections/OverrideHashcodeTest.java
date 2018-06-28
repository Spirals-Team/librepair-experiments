package ru.job4j.collections;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OverrideHashcodeTest {
    @Test
    public void whenOverrideHashcodeThenElementsIsEquals() {
        OverrideHashcode obj1 = new OverrideHashcode(1, 2);
        OverrideHashcode obj2 = new OverrideHashcode(2, 3);
        OverrideHashcode obj3 = new OverrideHashcode(2, 3);
        assertThat(obj3.hashCode() == obj2.hashCode(), is(true));
        assertThat(obj1.hashCode() == obj2.hashCode(), is(false));
    }
}
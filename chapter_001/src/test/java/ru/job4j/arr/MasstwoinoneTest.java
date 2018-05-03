package ru.job4j.arr;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
public class MasstwoinoneTest {
    @Test
    public void testirovanieArraMASSIVtest() {
        int[] array = {1, 4, 5, 260};
        int[] array2 = {2, 200, 6300};
        int[] array3 = Masstwoinone.ssortirovkaMassivavodin(array, array2);
        int[] expected = {1, 2, 4, 5, 200, 260, 6300};
        assertThat(array3, is(expected));
    }
}

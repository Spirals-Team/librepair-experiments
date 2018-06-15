package ru.job4j.array;

        import org.junit.Test;

        import static org.hamcrest.core.Is.is;
        import static org.junit.Assert.assertThat;

public class ArraySortChekTest {
    @Test
    public void isSort() {
        ArraySortCheck s = new ArraySortCheck();
        int[] result = s.sort(new int[]{5, 4, 3, 2, 1});
        int[] expectResult = {5, 4, 3, 2, 1 };
        assertThat(result, is(expectResult));
    }

}
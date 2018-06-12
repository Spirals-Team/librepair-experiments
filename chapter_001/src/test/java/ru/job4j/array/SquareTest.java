package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SquareTest {
    @Test
    public void eachMemberOfArrayIsSquare() {
        Square square = new Square();
        int[] compare = {1, 4, 9, 16, 25};
        assertThat(square.calculate(5), is(compare));
    }
}
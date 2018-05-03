package ru.job4j.array;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
public class StringContainsTest {

    @Test
    public void testirovanieArrayTurnFOReach() {
        String origins = "vfylfnrhelмамаklsjfksdfjk";
        String sub = "мам";
        boolean k = new StringContains().stringContainsValid(origins, sub);
        assertThat(k, is(true));
    }
}
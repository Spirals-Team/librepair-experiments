package ru.job4j.inheritance;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EngineerTest {
    @Test
    public void firstTest() {
        Engineer engineer = new Engineer();
        engineer.setField("software");
        String result = engineer.tellAboutMyself();
        String expected = "I am software engineer";
        assertThat(result, is(expected));
    }
}

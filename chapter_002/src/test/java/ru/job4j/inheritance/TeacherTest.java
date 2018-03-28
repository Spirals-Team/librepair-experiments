package ru.job4j.inheritance;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TeacherTest {
    @Test
    public void firstTest() {
        Teacher teacher = new Teacher();
        teacher.setSubject("math");
        String result = teacher.tellAboutMyself();
        String expected = "I teach math";
        assertThat(result, is(expected));
    }
}

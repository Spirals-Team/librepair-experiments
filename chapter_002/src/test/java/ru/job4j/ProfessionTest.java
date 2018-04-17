package ru.job4j;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 * @author Yury Matskevich (y.n.matskevich@gmail.com)
 * @version $Id$
 */

public class ProfessionTest {
    @Test
    public void whenTakeNameThenTreeEchoPlusName() {
        Teacher teacher = new Teacher("Алексей", new Diploma("БГМУ", "Математика", 1999));
        Student student = new Student("Игорь", new Knowledge(0));
        teacher.teach(student, 10);
        assertThat(student.getPercent().getPercentOfKnowledge(), is(10.0));
    }
}

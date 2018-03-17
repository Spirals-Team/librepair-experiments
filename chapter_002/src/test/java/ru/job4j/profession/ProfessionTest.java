package ru.job4j.profession;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 03.09.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class ProfessionTest {
    /**
     * Тест.
     */
    @Test
    public void whenTeachesThenREsultString() {
        Teacher teacher = new Teacher("Петров", 33, "Патологоанатомии");
        Engineer engineer = new Engineer("Иванов", 25, "радиослесарь");
        String testTeacing = teacher.teaches(engineer);
        String ex = "Преподователь Петров обучает Иванов курсу Патологоанатомии";
        assertThat(testTeacing, is(ex));
    }
}

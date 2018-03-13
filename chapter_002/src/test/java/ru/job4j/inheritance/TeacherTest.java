package ru.job4j.inheritance;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * test class Teacher.
 */
public class TeacherTest {
    /**
     * Test teach.
     */
    @Test
    public void whenTeacherTeachStudent() {

        Human humanTeacher = new Human();
        humanTeacher.setName("Сергей");
        Teacher teacher = new Teacher();
        teacher.setHuman(humanTeacher);

        Human humanStudent = new Human();
        humanStudent.setName("Кеша");
        Student student = new Student();
        student.setHuman(humanStudent);

        String result = teacher.teach(student);

        String expected = "Учитель Сергей обучил студента: Кеша";
        assertThat(result, is(expected));
    }

}

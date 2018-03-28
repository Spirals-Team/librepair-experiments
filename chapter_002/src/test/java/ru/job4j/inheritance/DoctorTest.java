package ru.job4j.inheritance;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DoctorTest{
    @Test
    public void firstTest() {
        Doctor doctor = new Doctor();
        Engineer engineer = new Engineer();
        doctor.setName("John");
        engineer.setName("Bob");
        String result = doctor.healEngineer(engineer);
        String expected = "Doctor John heals Bob";
        assertThat(result, is(expected));
    }

    @Test
    public void secondTest() {
        Doctor doctor = new Doctor();
        Teacher teacher = new Teacher();
        doctor.setName("Nov");
        teacher.setName("June");
        String result = doctor.healTeacher(teacher);
        String expected = "Doctor Nov heals June";
        assertThat(result, is(expected));
    }
}

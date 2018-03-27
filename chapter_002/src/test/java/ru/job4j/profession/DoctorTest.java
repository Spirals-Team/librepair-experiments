package ru.job4j.profession;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 29.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class DoctorTest {
    Doctor doctor;
    Engineer engineer;
    Teacher teacher;
    @Before
    public void start() {
        this.doctor = new Doctor("Ivan", 35, "Hirurg");
        this.engineer = new Engineer("Kolea", 20, "mehanik");
        this.teacher = new Teacher("Vasea", 40, "matematic");
    }
    @Test
    public void heal() throws Exception {
       String result = doctor.heal(engineer);
       assertThat(result, is("Ivan лечит Kolea"));
    }

    @Test
    public void proect() {
        String res = engineer.proect("house");
        assertThat(res, is("Kolea проэктирует house"));
    }
    @Test
    public void teaches() {
        String res = teacher.teaches(engineer);
        assertThat(res, is("Преподователь Vasea обучает Kolea курсу matematic"));
    }
}
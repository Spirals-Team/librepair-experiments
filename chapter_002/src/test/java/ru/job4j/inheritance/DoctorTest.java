package ru.job4j.inheritance;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * test Doctor.
 */
public class DoctorTest {
    /**
     * test Doctor.
     */
    @Test
    public void whenDoctorHealHuman() {
        Human human = new Human();
        human.setName("Сергей");

        Human humanDoctor = new Human();
        humanDoctor.setName("Иван");
        Doctor doctor = new Doctor();
        doctor.setHuman(humanDoctor);

        String result = doctor.heal(human);

        String expected = "Доктор Иван лечит Сергей";
        assertThat(result, is(expected));
    }
}

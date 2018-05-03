package ru.job4j.prof;
/**
 * тестируем методы доктора
 * диагностика пациента
 * и попытка вылечить пациента)
 * после повторная диагностика пациента
 */

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import ru.job4j.prof.dopclass.*;

public class DoctorTest {

    @Test
    public void pacientDiagnoztikLechenie() {
        Diagnoz expected;
        Doctor inna = new Doctor("Инна", Sex.Ж, 10, Education.ДОКТОРАНТУРА, Position.ЛОР, 20);
        Pacient kostia = new Pacient("Константин", Sex.М, 40);
        inna.pacientDiagnoztika(kostia);

        if (kostia.getHealth() < 70.0) {
            expected = Diagnoz.БОЛЕН;
        } else {
            expected = Diagnoz.ЗДОРОВ;
        }
        assertThat(kostia.getDiagnoz(), is(expected));

        double expected2 = kostia.getHealth() + 30;
        inna.lecarsPacient(kostia);
        assertThat(kostia.getHealth(), is(expected2));

        inna.pacientDiagnoztika(kostia);
        if (kostia.getHealth() < 70.0) {
            expected = Diagnoz.БОЛЕН;
        } else {
            expected = Diagnoz.ЗДОРОВ;
        }
        assertThat(kostia.getDiagnoz(), is(expected));
    }
}
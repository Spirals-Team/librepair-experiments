package ru.job4j.inherit;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
* Test Doctor.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class DoctorTest {
	/**
	* return string.
	*/
	@Test
	public void whenPatientComeToDoctorThenMishaHealTanya() {
		Doctor doctor = new Doctor("Misha");
		Patient patient = new Patient("Tanya");
		String result = doctor.heal(patient);
		String expect = "Доктор Misha лечит Tanya";
		assertThat(result, is(expect));
	}
}
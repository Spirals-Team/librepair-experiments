package ru.job4j.inherit;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
* Test Engineer.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class EngineerTest {
	/**
	* return string.
	*/
	@Test
	public void whenUserComeToEngineerThenVasyaAdviceAndrey() {
		Engineer engineer = new Engineer("Vasya");
		User user = new User("Andrey");
		String result = engineer.advice(user);
		String expect = "Специалист Vasya проводит консультацию Andrey";
		assertThat(result, is(expect));
	}
}
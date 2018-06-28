package ru.job4j.max;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MaxTest {
   @Test
	public void whenFirstLessSecond() {
		Max maxim = new Max();
		int result = maxim.max(1, 2);
		assertThat(result, is(2));
	}
	
	@Test
	public void whenFirstLessOther() {
		Max maxim = new Max();
		int result = maxim.max(1, 2, 5);
		assertThat(result, is(5));
	}
}
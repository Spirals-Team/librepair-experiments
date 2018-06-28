package ru.job4j;

import org.junit.Test;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ContainsTest {
	@Test
    public void whenPrivetThenIve() {
		Contains contain = new Contains();
		String origin = "Привет";
		String sub = "иве";
		boolean res1 = true;
		boolean res2 = false;
		assertThat(contain.contains(origin, sub), is(true));
    }
	
	@Test
    public void whenByeThenBye() {
		Contains contain = new Contains();
		String origin = "Пока";
		String sub = "Пока";
		assertThat(contain.contains(origin, sub), is(true));
    }
	
	@Test
    public void whenByeThenFalse() {
		Contains contain = new Contains();
		String origin = "Пока";
		String sub = "ора";
		assertThat(contain.contains(origin, sub), is(false));
	}
}
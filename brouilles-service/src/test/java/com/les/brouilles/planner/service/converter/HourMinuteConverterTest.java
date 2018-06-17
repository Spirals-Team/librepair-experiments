package com.les.brouilles.planner.service.converter;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HourMinuteConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		final Date date = new Date();

		final Date retour = HourMinuteConverter.convertirDateEtHeureEnDate(date, "17:35");

		System.out.println(date);
		System.out.println(retour);
	}

}

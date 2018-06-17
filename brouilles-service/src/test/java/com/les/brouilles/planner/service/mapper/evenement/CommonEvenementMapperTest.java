package com.les.brouilles.planner.service.mapper.evenement;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class CommonEvenementMapperTest {

	private final CommonEvenementMapper instance = new CommonEvenementMapper();

	// @Test
	// public void testGetHeureFromHeureMinute() throws Exception {
	// assertEquals(3, instance.getHeureFromHeureMinute("03:45"));
	// }
	//
	// @Test
	// public void testGetMinuteFromHeureMinute() throws Exception {
	// assertEquals(45, instance.getMinuteFromHeureMinute("03:45"));
	// }

	@Test
	public void test() {
		final Calendar calendarLundi = Calendar.getInstance();
		calendarLundi.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendarLundi.set(Calendar.HOUR_OF_DAY, 0);
		calendarLundi.set(Calendar.MINUTE, 0);
		calendarLundi.set(Calendar.SECOND, 0);

		final Date lundi = calendarLundi.getTime();

		final Calendar calendarDimanche = Calendar.getInstance();
		calendarDimanche.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendarDimanche.set(Calendar.HOUR_OF_DAY, 23);
		calendarDimanche.set(Calendar.MINUTE, 59);
		calendarDimanche.set(Calendar.SECOND, 59);
		final Date dimanche = calendarDimanche.getTime();

		final Calendar now = Calendar.getInstance();
		final Date today = now.getTime();

		int a = 1;
		a++;
	}

}

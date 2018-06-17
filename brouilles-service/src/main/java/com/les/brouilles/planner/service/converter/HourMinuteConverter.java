package com.les.brouilles.planner.service.converter;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.les.brouilles.planner.service.exception.BrouillesHeureMinuteConversionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HourMinuteConverter {

	private HourMinuteConverter() {

	}

	public static Date convertirDateEtHeureEnDate(final Date date, final String heureStr) {

		final int heure = getHeureFromHeureMinute(heureStr);
		final int minute = getMinuteFromHeureMinute(heureStr);

		final Instant instantDebut = date.toInstant();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(Date.from(instantDebut));
		cal.add(Calendar.HOUR, heure - date.getHours());
		cal.add(Calendar.MINUTE, minute - date.getMinutes());

		return Date.from(cal.toInstant());
	}

	public static String extraireHeureDeDate(final Date date) {
		return DateFormatUtils.format(date, "HH:mm", TimeZone.getTimeZone("Europe/Paris"));
	}

	// --------------------- private methods ------------------------

	private static int getHeureFromHeureMinute(final String heureMinute) {

		int hour = 0;
		try {
			hour = Integer.valueOf(heureMinute.split(":")[0]);
		} catch (final Exception e) {
			throw new BrouillesHeureMinuteConversionException("Conversion impossible de " + heureMinute + " en heures");
		}

		log.debug("hour={}", hour);
		return hour;
	}

	private static int getMinuteFromHeureMinute(final String heureMinute) {

		int minute = 0;
		try {
			minute = Integer.valueOf(heureMinute.split(":")[1]);
		} catch (final Exception e) {
			throw new BrouillesHeureMinuteConversionException(
					"Conversion impossible de " + heureMinute + " en minutes");

		}
		log.debug("minute={}", minute);
		return minute;
	}
}

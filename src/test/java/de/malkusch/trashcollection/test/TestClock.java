package de.malkusch.trashcollection.test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public final class TestClock extends Clock {

	private Clock subject;

	public TestClock() {
		subject = Clock.systemDefaultZone();
	}

	public void fix(LocalDateTime offset) {
		ZoneId zoneId = ZoneId.of("Europe/Berlin");
		ZoneOffset zoneOffset = zoneId.getRules().getOffset(offset);
		subject = Clock.fixed(offset.toInstant(zoneOffset), zoneId);
	}

	public void release() {
		subject = Clock.systemDefaultZone();
	}

	@Override
	public ZoneId getZone() {
		return subject.getZone();
	}

	@Override
	public Clock withZone(ZoneId zone) {
		return subject.withZone(zone);
	}

	@Override
	public Instant instant() {
		return subject.instant();
	}

}

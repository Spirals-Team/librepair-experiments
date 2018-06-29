package de.malkusch.trashcollection.infrastructure.schedule.ical;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.malkusch.trashcollection.model.TrashCan;
import de.malkusch.trashcollection.model.TrashCollection;
import de.malkusch.trashcollection.model.TrashCollectionScheduleService;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.component.VEvent;

@Service
final class ICalTrashCollectionScheduleService implements TrashCollectionScheduleService {

	private final VEventToTrashCanConverter vEventConverter;
	private final VEventRepository vEventRepository;
	private final Clock clock;

	@Autowired
	ICalTrashCollectionScheduleService(VEventRepository vEventRepository, Clock clock,
			VEventToTrashCanConverter vEventConverter) throws IOException, ParserException {

		this.vEventRepository = vEventRepository;
		this.vEventConverter = vEventConverter;
		this.clock = clock;
	}

	@Override
	public TrashCollection findNextCollection() {
		return findCollectionAfter(tomorrow());
	}

	@Override
	public TrashCollection findNextCollectionAfter(TrashCollection lastCollection) {
		DateTime lastCollectionDate = toDateTime(lastCollection.collectionDate().plusDays(1));
		return findCollectionAfter(lastCollectionDate);
	}

	private TrashCollection findCollectionAfter(DateTime dateTime) {
		Dur twoMonths = new Dur(8);
		Period searchPeriod = new Period(dateTime, twoMonths);
		Period nextCollectionPeriod = findEarliestPeriod(searchPeriod);

		@SuppressWarnings("unchecked")
		Predicate<VEvent>[] rules = new Predicate[] { new PeriodRule<>(oneDay(nextCollectionPeriod)) };
		Filter<VEvent> filter = new Filter<>(rules, Filter.MATCH_ALL);

		Collection<VEvent> events = filter.filter(vEventRepository.findAll());
		return toTrashCollection(nextCollectionPeriod, events);
	}

	private Period findEarliestPeriod(Period period) {
		Collection<VEvent> events = vEventRepository.findAll();
		return events.stream().flatMap(event -> event.calculateRecurrenceSet(period).stream()).min(Period::compareTo)
				.orElseThrow(IllegalStateException::new);
	}

	private TrashCollection toTrashCollection(Period period, Collection<VEvent> events) {
		TrashCan[] trashCans = events.stream().map(vEventConverter::toTrashCan).toArray(TrashCan[]::new);
		LocalDate date = period.getStart().toInstant().atZone(clock.getZone()).toLocalDate();
		return new TrashCollection(date, trashCans);
	}

	private static Period oneDay(Period period) {
		return new Period(period.getStart(), new Dur(1, 0, 0, 0));
	}

	private DateTime toDateTime(LocalDate date) {
		return new DateTime(Date.from(ZonedDateTime.of(date.atStartOfDay(), clock.getZone()).toInstant()));
	}

	private DateTime tomorrow() {
		return toDateTime(LocalDate.now(clock).plusDays(1));
	}

}

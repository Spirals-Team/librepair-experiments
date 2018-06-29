package de.malkusch.trashcollection.infrastructure.schedule.ical;

import static de.malkusch.trashcollection.model.TrashCan.Type.ORGANIC;
import static de.malkusch.trashcollection.model.TrashCan.Type.PAPER;
import static de.malkusch.trashcollection.model.TrashCan.Type.PLASTIC;
import static de.malkusch.trashcollection.model.TrashCan.Type.RESIDUAL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import de.malkusch.trashcollection.infrastructure.schedule.ical.ICalTrashCollectionScheduleService;
import de.malkusch.trashcollection.infrastructure.schedule.ical.RothConverter;
import de.malkusch.trashcollection.infrastructure.schedule.ical.VEventRepository;
import de.malkusch.trashcollection.infrastructure.schedule.ical.VEventToTrashCanConverter;
import de.malkusch.trashcollection.infrastructure.urlservice.OpenUrlService;
import de.malkusch.trashcollection.model.TrashCan;
import de.malkusch.trashcollection.model.TrashCan.Type;
import de.malkusch.trashcollection.model.TrashCollection;
import de.malkusch.trashcollection.model.TrashCollectionScheduleService;
import net.fortuna.ical4j.data.ParserException;

@RunWith(Theories.class)
public class ICalTrashCollectionScheduleServiceTest {

	@DataPoints("testFindNextCollection")
	public static Object[][] testFindNextCollection = {
			{ "2017-05-11T00:00:00", trashCollection("2017-05-26", RESIDUAL, ORGANIC) },
			{ "2017-05-11T23:59:59", trashCollection("2017-05-26", RESIDUAL, ORGANIC) },
			{ "2017-05-12T00:00:00", trashCollection("2017-05-26", RESIDUAL, ORGANIC) },
			{ "2017-05-25T00:00:00", trashCollection("2017-05-26", RESIDUAL, ORGANIC) },
			{ "2017-05-25T23:59:59", trashCollection("2017-05-26", RESIDUAL, ORGANIC) },
			{ "2017-05-26T00:00:00", trashCollection("2017-06-01", PAPER, PLASTIC) },
			{ "2017-07-04T23:59:59", trashCollection("2017-07-05", PAPER, PLASTIC) },
			{ "2017-07-05T00:00:00", trashCollection("2017-07-06", RESIDUAL, ORGANIC) },
			{ "2017-07-05T23:59:59", trashCollection("2017-07-06", RESIDUAL, ORGANIC) },
			{ "2017-07-06T00:00:00", trashCollection("2017-07-20", RESIDUAL, ORGANIC) }, };

	@Theory
	public void testFindNextCollection(@FromDataPoints("testFindNextCollection") final Object[] testData) {
		LocalDateTime today = LocalDateTime.parse((String) testData[0]);
		TrashCollectionScheduleService service = buildService(today);

		TrashCollection next = service.findNextCollection();

		TrashCollection expected = (TrashCollection) testData[1];
		assertEquals(expected, next);
	}

	@DataPoints("testFindNextCollectionAfter")
	public static Object[][] testFindNextCollectionAfter = {
			{ trashCollection("2017-05-26", RESIDUAL, ORGANIC), trashCollection("2017-06-01", PAPER, PLASTIC) },
			{ trashCollection("2017-06-01", PAPER, PLASTIC), trashCollection("2017-06-09", RESIDUAL, ORGANIC) },
			{ trashCollection("2017-06-22", RESIDUAL, ORGANIC), trashCollection("2017-07-05", PAPER, PLASTIC) },
			{ trashCollection("2017-07-05", PAPER, PLASTIC), trashCollection("2017-07-06", RESIDUAL, ORGANIC) }, };

	@Theory
	public void testFindNextCollectionAfter(@FromDataPoints("testFindNextCollectionAfter") final Object[] testData) {
		TrashCollection current = (TrashCollection) testData[0];
		TrashCollectionScheduleService service = buildService();

		TrashCollection next = service.findNextCollectionAfter(current);

		TrashCollection expected = (TrashCollection) testData[1];
		assertEquals(expected, next);
	}

	private static final TrashCollection trashCollection(String date, Type... types) {
		TrashCan[] trashcans = Stream.of(types).map(TrashCan::new).toArray(TrashCan[]::new);
		return new TrashCollection(LocalDate.parse(date), trashcans);
	}

	private static final ICalTrashCollectionScheduleService buildService() {
		return buildService(LocalDateTime.now());
	}

	private static final ICalTrashCollectionScheduleService buildService(LocalDateTime offset) {
		try {
			ZoneId zoneId = ZoneId.of("Europe/Berlin");
			ZoneOffset zoneOffset = zoneId.getRules().getOffset(offset);
			Clock clock = Clock.fixed(offset.toInstant(zoneOffset), zoneId);
			URL url = new URL("http://example.org/");
			VEventToTrashCanConverter trashCanConversionService = new RothConverter();

			OpenUrlService urlService = mock(OpenUrlService.class);
			when(urlService.open(url))
					.thenReturn(ICalTrashCollectionScheduleServiceTest.class.getResourceAsStream("/schedule.ics"));
			VEventRepository repository = new VEventRepository(url, urlService);

			return new ICalTrashCollectionScheduleService(repository, clock, trashCanConversionService);

		} catch (IOException | ParserException e) {
			throw new IllegalStateException(e);
		}
	}

}

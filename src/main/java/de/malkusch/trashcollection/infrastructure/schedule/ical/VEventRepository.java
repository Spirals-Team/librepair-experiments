package de.malkusch.trashcollection.infrastructure.schedule.ical;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.malkusch.trashcollection.infrastructure.urlservice.OpenUrlService;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;

@Service
final class VEventRepository {

	private final URL url;
	private final OpenUrlService urlService;
	private Collection<VEvent> vEvents;
	private static final Logger LOGGER = LoggerFactory.getLogger(VEventRepository.class);

	@Autowired
	VEventRepository(@Value("${schedule.uri}") URL url, OpenUrlService urlService) throws IOException, ParserException {
		this.url = url;
		this.urlService = urlService;
		vEvents = downloadVEvents();
	}

	@Scheduled(cron = "59 59 23 * * *")
	synchronized void update() throws IOException, ParserException {
		vEvents = downloadVEvents();
		LOGGER.info("Updated trash collection schedule");
	}

	private Collection<VEvent> downloadVEvents() throws IOException, ParserException {
		try (InputStream stream = urlService.open(url)) {
			CalendarBuilder builder = new CalendarBuilder();
			Calendar calendar = builder.build(stream);
			Collection<VEvent> vEvents = calendar.getComponents(Component.VEVENT);
			return vEvents;
		}
	}

	public Collection<VEvent> findAll() {
		return Collections.unmodifiableCollection(vEvents);
	}

}

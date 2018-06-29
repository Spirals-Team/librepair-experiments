package de.malkusch.trashcollection.infrastructure.schedule.ical;

import static de.malkusch.trashcollection.model.TrashCan.Type.ORGANIC;
import static de.malkusch.trashcollection.model.TrashCan.Type.PAPER;
import static de.malkusch.trashcollection.model.TrashCan.Type.PLASTIC;
import static de.malkusch.trashcollection.model.TrashCan.Type.RESIDUAL;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.malkusch.trashcollection.model.TrashCan;
import net.fortuna.ical4j.model.component.VEvent;

@Service
@Profile("roth")
final class RothConverter implements VEventToTrashCanConverter {

	@Override
	public TrashCan toTrashCan(VEvent event) {
		String summary = event.getSummary().getValue();

		if (summary.contains("Altpapier")) {
			return new TrashCan(PAPER);

		} else if (summary.contains("Gelber Sack")) {
			return new TrashCan(PLASTIC);

		} else if (summary.contains("Biomüll")) {
			return new TrashCan(ORGANIC);

		} else if (summary.contains("Restmüll")) {
			return new TrashCan(RESIDUAL);
		}

		throw new IllegalStateException(String.format("Could not convert '%s' into trashcan", summary));
	}

}

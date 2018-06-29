package de.malkusch.trashcollection.infrastructure.schedule.ical;

import de.malkusch.trashcollection.model.TrashCan;
import net.fortuna.ical4j.model.component.VEvent;

interface VEventToTrashCanConverter {

	TrashCan toTrashCan(VEvent event);

}

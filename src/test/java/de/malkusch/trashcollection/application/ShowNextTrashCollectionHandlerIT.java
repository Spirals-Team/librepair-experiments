package de.malkusch.trashcollection.application;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import de.malkusch.trashcollection.application.shownext.ShowNextTrashCollectionHandler;
import de.malkusch.trashcollection.application.shownext.ShowNextTrashCollectionResult;
import de.malkusch.trashcollection.model.TrashCollectionScheduleService;
import de.malkusch.trashcollection.test.IntegrationTest;

@IntegrationTest
@RunWith(SpringRunner.class)
public class ShowNextTrashCollectionHandlerIT {

	@Autowired
	private ShowNextTrashCollectionHandler handler;

	@Autowired
	private TrashCollectionScheduleService scheduleService;

	@Test
	public void shouldInitiallyHaveNextTrashCollection() {
		LocalDate expected = scheduleService.findNextCollection().collectionDate();
		ShowNextTrashCollectionResult result = handler.show();

		LocalDate actual = result.collectionDate;
		assertEquals(expected, actual);
	}

}

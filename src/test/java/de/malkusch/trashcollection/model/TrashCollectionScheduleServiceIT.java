package de.malkusch.trashcollection.model;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.malkusch.trashcollection.model.TrashCollection;
import de.malkusch.trashcollection.model.TrashCollectionScheduleService;
import de.malkusch.trashcollection.test.IntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class TrashCollectionScheduleServiceIT {

	@Autowired
	private TrashCollectionScheduleService service;

	@Test
	public void shouldFindCollectionInFuture() {
		TrashCollection next = service.findNextCollection();
		assertTrue(next.collectionDate().isAfter(LocalDate.now()));
	}

	@Test
	public void shouldFindCollectionAfterCurrentCollection() {
		TrashCollection current = service.findNextCollection();
		TrashCollection next = service.findNextCollectionAfter(current);
		assertTrue(next.collectionDate().isAfter(current.collectionDate()));
	}

}

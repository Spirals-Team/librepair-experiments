package de.malkusch.trashcollection.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class WaitForNextTrashCollectionService {

	private final TrashCollectionScheduleService scheduleService;

	@Autowired
	WaitForNextTrashCollectionService(TrashCollectionScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public void waitForNextTrashCollection(NextTrashCollection nextTrashCollection) {
		TrashCollection next = scheduleService.findNextCollectionAfter(nextTrashCollection.trashCollection());
		nextTrashCollection.waitForNextTrashCollection(next);
	}

}

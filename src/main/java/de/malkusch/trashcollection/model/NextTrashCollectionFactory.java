package de.malkusch.trashcollection.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class NextTrashCollectionFactory {

	private final TrashCollectionScheduleService scheduleService;

	@Autowired
	NextTrashCollectionFactory(TrashCollectionScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public NextTrashCollection build() {
		TrashCollection nextCollection = scheduleService.findNextCollection();
		return new NextTrashCollection(nextCollection);
	}

}

package de.malkusch.trashcollection.model;

public interface TrashCollectionScheduleService {

	TrashCollection findNextCollection();

	TrashCollection findNextCollectionAfter(TrashCollection lastCollection);

}

package de.malkusch.trashcollection.model;

import java.time.LocalDate;
import java.util.Objects;

public final class NextTrashCollection {

	private TrashCollection next;

	NextTrashCollection(TrashCollection nextCollection) {
		this.next = Objects.requireNonNull(nextCollection);
	}

	public LocalDate preparationDate() {
		return next.collectionDate().minusDays(1);
	}

	public TrashCollection trashCollection() {
		return next;
	}

	void waitForNextTrashCollection(TrashCollection nextCollection) {
		Objects.requireNonNull(nextCollection);
		LocalDate nextCollectionDate = nextCollection.collectionDate();
		if (!nextCollectionDate.isAfter(next.collectionDate())) {
			throw new IllegalArgumentException("Next trash collection must be after current trash collection");
		}
		this.next = nextCollection;
	}

}

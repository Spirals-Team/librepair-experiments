package de.malkusch.trashcollection.application.shownext;

import java.time.LocalDate;
import java.util.stream.Stream;

import de.malkusch.trashcollection.model.NextTrashCollection;
import de.malkusch.trashcollection.model.TrashCan;

public final class ShowNextTrashCollectionResult {

	public final String[] trashCans;
	public final LocalDate collectionDate;
	public final LocalDate preparationDate;

	ShowNextTrashCollectionResult(NextTrashCollection nextTrashCollection) {
		trashCans = Stream.of(nextTrashCollection.trashCollection().trashCans()).map(TrashCan::toString)
				.toArray(String[]::new);
		preparationDate = nextTrashCollection.preparationDate();
		collectionDate = nextTrashCollection.trashCollection().collectionDate();
	}

}

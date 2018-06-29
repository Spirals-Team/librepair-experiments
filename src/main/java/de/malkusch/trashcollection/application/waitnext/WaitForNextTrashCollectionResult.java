package de.malkusch.trashcollection.application.waitnext;

import java.time.LocalDate;

import de.malkusch.trashcollection.model.NextTrashCollection;

public final class WaitForNextTrashCollectionResult {

	public final LocalDate preparationDate;

	WaitForNextTrashCollectionResult(NextTrashCollection nextTrashCollection) {
		preparationDate = nextTrashCollection.preparationDate();
	}

}

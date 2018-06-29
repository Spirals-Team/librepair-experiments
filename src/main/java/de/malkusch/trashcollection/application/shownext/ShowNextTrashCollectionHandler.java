package de.malkusch.trashcollection.application.shownext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.malkusch.trashcollection.model.NextTrashCollection;
import de.malkusch.trashcollection.model.NextTrashCollectionRepository;

@Service
public final class ShowNextTrashCollectionHandler {

	private final NextTrashCollectionRepository repository;

	@Autowired
	ShowNextTrashCollectionHandler(NextTrashCollectionRepository repository) {
		this.repository = repository;
	}

	public ShowNextTrashCollectionResult show() {
		NextTrashCollection next = repository.find();
		return new ShowNextTrashCollectionResult(next);
	}

}

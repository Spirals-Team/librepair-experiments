package de.malkusch.trashcollection.application.waitnext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.malkusch.trashcollection.model.NextTrashCollection;
import de.malkusch.trashcollection.model.NextTrashCollectionRepository;
import de.malkusch.trashcollection.model.WaitForNextTrashCollectionService;

@Service
public final class WaitForNextTrashCollectionHandler {

	private final WaitForNextTrashCollectionService service;
	private final NextTrashCollectionRepository repository;

	@Autowired
	WaitForNextTrashCollectionHandler(WaitForNextTrashCollectionService service,
			NextTrashCollectionRepository repository) {

		this.repository = repository;
		this.service = service;
	}

	public WaitForNextTrashCollectionResult waitForNextTrashCollection() {
		NextTrashCollection nextTrashCollection = repository.find();
		service.waitForNextTrashCollection(nextTrashCollection);
		repository.save(nextTrashCollection);

		return new WaitForNextTrashCollectionResult(nextTrashCollection);
	}

}

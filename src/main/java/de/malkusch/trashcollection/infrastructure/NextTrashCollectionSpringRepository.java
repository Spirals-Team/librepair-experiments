package de.malkusch.trashcollection.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.malkusch.trashcollection.model.NextTrashCollection;
import de.malkusch.trashcollection.model.NextTrashCollectionFactory;
import de.malkusch.trashcollection.model.NextTrashCollectionRepository;

@Service
final class NextTrashCollectionSpringRepository implements NextTrashCollectionRepository {

	private final NextTrashCollection nextTrashCollection;

	@Autowired
	NextTrashCollectionSpringRepository(NextTrashCollectionFactory factory) {
		nextTrashCollection = factory.build();
	}

	@Override
	public NextTrashCollection find() {
		return nextTrashCollection;
	}

	@Override
	public synchronized void save(NextTrashCollection nextTrashCollection) {
	}

}

package de.malkusch.trashcollection.model;

public interface NextTrashCollectionRepository {

	NextTrashCollection find();
	
	void save(NextTrashCollection nextTrashCollection);

}

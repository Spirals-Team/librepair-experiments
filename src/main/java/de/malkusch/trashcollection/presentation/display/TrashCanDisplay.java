package de.malkusch.trashcollection.presentation.display;

public interface TrashCanDisplay {

	void test();
	
	void showTrashCans(String... trashCans);

	void showWaiting();
	
	void showError(Exception error);

}

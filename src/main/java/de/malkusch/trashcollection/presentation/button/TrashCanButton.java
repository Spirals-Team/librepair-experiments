package de.malkusch.trashcollection.presentation.button;

public interface TrashCanButton {

	void waitForPressed(PressedListener listener);
	
	void stopWaiting();

	@FunctionalInterface
	public interface PressedListener {
		void onPressed();
	}

}

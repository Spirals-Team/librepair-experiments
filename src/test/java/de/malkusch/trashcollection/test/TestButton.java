package de.malkusch.trashcollection.test;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.malkusch.trashcollection.presentation.button.TrashCanButton;

@Component
@Profile("test")
public final class TestButton implements TrashCanButton {

	private PressedListener listener;

	@Override
	public void waitForPressed(PressedListener listener) {
		this.listener = listener;
	}

	public void press() {
		if (listener != null) {
			listener.onPressed();
		}
	}

	@Override
	public void stopWaiting() {
		listener = null;
	}

}

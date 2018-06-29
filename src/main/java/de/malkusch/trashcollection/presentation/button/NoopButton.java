package de.malkusch.trashcollection.presentation.button;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
final class NoopButton implements TrashCanButton {

	@Override
	public void waitForPressed(PressedListener listener) {
	}

	@Override
	public void stopWaiting() {
	}

}

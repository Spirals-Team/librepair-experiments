package de.malkusch.trashcollection.infrastructure.pi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import de.malkusch.trashcollection.presentation.button.TrashCanButton;

final class PiButton implements TrashCanButton {

	enum Pull {

		DOWN(PinPullResistance.PULL_DOWN), UP(PinPullResistance.PULL_UP);

		private final PinPullResistance resistance;

		private Pull(PinPullResistance resistance) {
			this.resistance = resistance;
		}
	}

	private final GpioPinDigitalInput button;
	private static final Logger LOGGER = LoggerFactory.getLogger(PiButton.class);

	PiButton(GpioController controller, int pinAddress, Pull pull) {
		Pin pin = PinFactory.createDigitalPin(pinAddress);
		button = controller.provisionDigitalInputPin(pin, pull.resistance);
		button.setShutdownOptions(true);
	}

	@Override
	public void waitForPressed(PressedListener listener) {
		GpioPinListenerDigital piListener = e -> {
			LOGGER.debug("Button pressed [{}, {}]", e.getState(), e.getEdge());
			listener.onPressed();
		};
		button.addListener(piListener);
	}

	@Override
	public void stopWaiting() {
		button.removeAllListeners();
	}

	GpioPin pin() {
		return button;
	}

}

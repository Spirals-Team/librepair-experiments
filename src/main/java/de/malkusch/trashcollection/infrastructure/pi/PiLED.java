package de.malkusch.trashcollection.infrastructure.pi;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

final class PiLED {

	private final String trashCan;
	private final GpioPinDigitalOutput pin;

	PiLED(String trashCan, int pinAddress, GpioController gpio) {
		this.trashCan = trashCan;
		pin = gpio.provisionDigitalOutputPin(PinFactory.createDigitalPin(pinAddress), trashCan, PinState.LOW);
		pin.setShutdownOptions(true, PinState.LOW);
	}

	String trashCan() {
		return trashCan;
	}

	GpioPin pin() {
		return pin;
	}

	void turnOn() {
		pin.high();
	}

	void turnOff() {
		pin.low();
	}

}

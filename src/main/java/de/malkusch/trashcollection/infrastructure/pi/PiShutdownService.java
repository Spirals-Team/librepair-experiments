package de.malkusch.trashcollection.infrastructure.pi;

import com.pi4j.io.gpio.GpioController;

final class PiShutdownService implements AutoCloseable {

	private final GpioController controller;

	PiShutdownService(GpioController controller) {
		this.controller = controller;
	}

	@Override
	public void close() {
		controller.shutdown();
	}

}

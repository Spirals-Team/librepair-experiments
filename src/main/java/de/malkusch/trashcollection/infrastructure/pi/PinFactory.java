package de.malkusch.trashcollection.infrastructure.pi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinProvider;
import com.pi4j.platform.PlatformManager;

/**
 * A platform abstraction to create pins.
 * 
 * For me it seems the P4J library doesn't abstract the platform well enough.
 * There is the concept of Pin which is needed to use GPIO. However to get such
 * an instance, one must use a static method of one of those PinProvider
 * subclasses (which fits best for the platform). However, those static classes
 * put each pin instance into a static map which is shared amongst all
 * providers. To make this work correctly, one has only to use the PinProvider
 * of the actual platform. This becomes challenging, when the software has to
 * load all platforms (i.e. because it potentially supports all of them).
 * 
 * This class is a hack to mitigate that issue. It will not use any of those
 * PinProviders and put a pin for the configured platform.
 */
final class PinFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(PinFactory.class);

	static Pin createDigitalPin(int address) {
		String gpioProviderName = PlatformManager.getPlatform().getGpioProvider().getName();
		String name = String.format("GPIO %s", address);

		LOGGER.debug("Creating pin. Name: {}, Address: {}, Platform: '{}'", name, address, gpioProviderName);
		return PinProviderAccess.createDigitalPin(gpioProviderName, address, name);
	}

	private static final class PinProviderAccess extends PinProvider {
		protected static Pin createDigitalPin(String providerName, int address, String name) {
			return PinProvider.createDigitalPin(providerName, address, name);
		}
	}

}

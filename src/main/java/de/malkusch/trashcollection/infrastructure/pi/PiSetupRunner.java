package de.malkusch.trashcollection.infrastructure.pi;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.pi4j.io.gpio.GpioPin;

@Order(Ordered.HIGHEST_PRECEDENCE)
final class PiSetupRunner implements ApplicationRunner {

	private final PiButton button;
	private final Collection<PiLED> leds;

	PiSetupRunner(PiButton button, Collection<PiLED> leds) {
		this.button = button;
		this.leds = leds;
	}

	@Override
	public void run(ApplicationArguments args) throws InterruptedException {
		if (!args.containsOption("setup")) {
			return;
		}

		System.out.println("Connect these pins: GND, 3.3V, button");
		System.out.printf("Connect the button pin to %s%n", format(button.pin()));
		waitForButtonPressed();

		System.out.println("Connect 5V");
		waitForButtonPressed();

		for (PiLED led : leds) {
			System.out.printf("Connect one of the LED pins for trash can %s to %s%n", led.trashCan(),
					format(led.pin()));
			led.turnOn();
			waitForButtonPressed();
			led.turnOff();
		}
	}

	private static String format(GpioPin pin) {
		return String.format("%s (WiringPi)", pin.getPin());
	}

	private static final long DEBOUNCE_MILLIS = TimeUnit.SECONDS.toMillis(1);

	private void waitForButtonPressed() throws InterruptedException {
		Thread.sleep(DEBOUNCE_MILLIS);
		final CountDownLatch buttonLatch = new CountDownLatch(1);
		button.waitForPressed(buttonLatch::countDown);
		System.out.println("Press the button");

		buttonLatch.await();

		button.stopWaiting();
	}

}

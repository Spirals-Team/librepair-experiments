package de.malkusch.trashcollection.infrastructure.pi;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.malkusch.trashcollection.presentation.display.TrashCanDisplay;

final class PiDisplay implements TrashCanDisplay {

	private final Map<String, PiLED> leds;

	PiDisplay(Collection<PiLED> leds) {
		this.leds = leds.stream().collect(Collectors.toMap(PiLED::trashCan, Function.identity()));
	}

	@Override
	public void showTrashCans(String... trashCans) {
		turnAllOff();
		Stream.of(trashCans).map(leds::get).forEach(PiLED::turnOn);
	}

	@Override
	public void showWaiting() {
		turnAllOff();
	}

	private void turnAllOff() {
		leds.values().forEach(PiLED::turnOff);
	}

	@Override
	public void test() {
		leds.values().forEach(PiLED::turnOn);
	}

	@Override
	public void showError(Exception error) {
		leds.values().forEach(PiLED::turnOn);
	}

}

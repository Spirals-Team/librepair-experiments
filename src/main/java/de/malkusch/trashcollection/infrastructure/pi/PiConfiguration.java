package de.malkusch.trashcollection.infrastructure.pi;

import static de.malkusch.trashcollection.model.TrashCan.Type.ORGANIC;
import static de.malkusch.trashcollection.model.TrashCan.Type.PAPER;
import static de.malkusch.trashcollection.model.TrashCan.Type.PLASTIC;
import static de.malkusch.trashcollection.model.TrashCan.Type.RESIDUAL;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;

import de.malkusch.trashcollection.infrastructure.pi.PiButton.Pull;
import de.malkusch.trashcollection.presentation.display.TrashCanDisplay;

@Profile("pi")
@Configuration
class PiConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(PiConfiguration.class);

	@Bean
	public GpioController gpioController(@Value("${pi.platform:}") String platformId)
			throws PlatformAlreadyAssignedException {

		if (!StringUtils.isEmpty(platformId)) {
			if (platformId.equals("bpi-m1+")) {
				LOGGER.info(
						"BPI-M1+ requires an installed WiringPi library from https://github.com/BPI-SINOVOIP/BPI-WiringPi");
				System.setProperty("pi4j.linking", "dynamic");
				platformId = "bananapro";
			}

			Platform platform = Platform.fromId(platformId);
			PlatformManager.setPlatform(platform);
			LOGGER.info("Platform set to {}", platform.label());
		}
		return GpioFactory.getInstance();
	}

	@Bean
	public PiShutdownService piShutdownService(GpioController controller) {
		return new PiShutdownService(controller);
	}

	@Bean
	public PiButton buttonService(GpioController controller, @Value("${pi.button.pin}") int buttonAddress,
			@Value("${pi.button.pull}") String pull) {

		return new PiButton(controller, buttonAddress, Pull.valueOf(pull));
	}

	@Bean
	public TrashCanDisplay display(Collection<PiLED> leds) {
		return new PiDisplay(leds);
	}

	@Bean
	public PiSetupRunner setupRunner(PiButton button, Collection<PiLED> leds) {
		return new PiSetupRunner(button, leds);
	}

	@Bean
	public PiLED paperLED(@Value("${pi.led.paper}") int pinAddress, GpioController controller) {
		return new PiLED(PAPER.toString(), pinAddress, controller);
	}

	@Bean
	public PiLED plasticLED(@Value("${pi.led.plastic}") int pinAddress, GpioController controller) {
		return new PiLED(PLASTIC.toString(), pinAddress, controller);
	}

	@Bean
	public PiLED organicLED(@Value("${pi.led.organic}") int pinAddress, GpioController controller) {
		return new PiLED(ORGANIC.toString(), pinAddress, controller);
	}

	@Bean
	public PiLED residualLED(@Value("${pi.led.residual}") int pinAddress, GpioController controller) {
		return new PiLED(RESIDUAL.toString(), pinAddress, controller);
	}

}

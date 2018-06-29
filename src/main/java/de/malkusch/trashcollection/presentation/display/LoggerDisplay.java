package de.malkusch.trashcollection.presentation.display;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({ "dev", "test" })
@Component
public class LoggerDisplay implements TrashCanDisplay {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerDisplay.class);

	@Override
	public void showTrashCans(String... trashCans) {
		LOGGER.info("Prepare these trash cans: {}", Arrays.toString(trashCans));
	}

	@Override
	public void showWaiting() {
		LOGGER.info("Waiting for next collection");
	}

	@Override
	public void test() {
		LOGGER.info("Test display");
	}

	@Override
	public void showError(Exception error) {
		LOGGER.error(error.getMessage(), error);
	}

}

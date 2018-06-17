package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

public class BrouillesPostItNonTrouveException extends RuntimeException {

	private static final String MESSAGE = "Le post-it avec id {0} n'a pas été trouvé";

	public BrouillesPostItNonTrouveException(final Long id) {
		super(MessageFormat.format(MESSAGE, id));
	}
}

package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

public class BrouillesRepasNonTrouveException extends RuntimeException {

	private static final String MESSAGE = "Le repas avec id {0} n'a pas été trouvé";

	public BrouillesRepasNonTrouveException(final Long id) {
		super(MessageFormat.format(MESSAGE, id));
	}
}

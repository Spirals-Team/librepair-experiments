package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

public class BrouillesLiberteNonTrouveException extends RuntimeException {

	private static final String MESSAGE = "Le repas liberté avec id {0} n'a pas été trouvé";

	public BrouillesLiberteNonTrouveException(final Long id) {
		super(MessageFormat.format(MESSAGE, id));
	}
}

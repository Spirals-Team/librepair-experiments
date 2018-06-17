package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

public class BrouillesClientNonTrouveException extends RuntimeException {

	private static final String MESSAGE = "Le client avec id {0} n'a pas été trouvé";

	public BrouillesClientNonTrouveException(final Long id) {
		super(MessageFormat.format(MESSAGE, id));
	}
}

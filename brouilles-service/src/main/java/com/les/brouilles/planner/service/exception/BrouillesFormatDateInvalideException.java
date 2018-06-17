package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

public class BrouillesFormatDateInvalideException extends RuntimeException {

	private static final String MESSAGE = "La date {0} n'est pas valide";

	public BrouillesFormatDateInvalideException(final String date) {
		super(MessageFormat.format(MESSAGE, date));
	}
}

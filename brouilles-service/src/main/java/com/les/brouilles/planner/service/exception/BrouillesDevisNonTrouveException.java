package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

public class BrouillesDevisNonTrouveException extends RuntimeException {

	private static final String MESSAGE = "Le devis avec id {0} n'a pas été trouvé";

	public BrouillesDevisNonTrouveException(final Long id) {
		super(MessageFormat.format(MESSAGE, id));
	}
}

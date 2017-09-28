/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.visitor;

import java.util.ArrayDeque;

import spoon.SpoonException;

/**
 * The special {@link PrinterTokenWriter} implementation,
 * detects whether some identifier, keyword, literal, operator or separator.
 * Comments, white spaces, tabs and new lines are ignored, because they can appear in the middle of printed elements.
 * was written since last call of {@link #snapshotLength()}
 */
class SnapshotPrinterTokenWriter extends PrinterTokenWriterDelegate<SnapshotPrinterTokenWriter> {

	private int countOfTokens = 0;
	private ArrayDeque<Integer> lengths = new ArrayDeque<>();

	SnapshotPrinterTokenWriter(PrinterTokenWriter next) {
		super(next);
	}

	@Override
	protected void onToken(TokenType tokenType, Object token) {
		switch (tokenType) {
			case IDENTIFIER:
			case KEYWORD:
			case LITERAL:
			case OPERATOR:
			case SEPARATOR:
			case CODE_SNIPPET:
				countOfTokens++;
				break;
			case COMMENT:
			case INCREMENT_TABS:
			case DECREMENT_TABS:
			case TABS:
			case NEW_LINE:
			case WHITESPACE:
				break;
			default:
				throw new SpoonException("Unexpected token type: " + tokenType.name());
		}
	}

	/** stores the length of the printer */
	public void snapshotLength() {
		//if the buffer contains some data then flush them first to send that token and use correct `countOfTokens`
		lengths.addLast(countOfTokens);
	}

	/** returns true if something has been written since the last call to snapshotLength() */
	public boolean hasNewContent() {
		return lengths.pollLast() < countOfTokens;
	}

	public SnapshotPrinterTokenWriter writeSpace() {
		writeWhitespace(" ");
		return this;
	}
}

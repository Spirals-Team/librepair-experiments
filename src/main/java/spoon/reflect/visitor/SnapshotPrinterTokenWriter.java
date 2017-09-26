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

/**
 * The special {@link PrinterTokenWriter} implementation,
 * detects whether some identifier, keyword, literal, operator or separator.
 * Comments, white spaces, tabs and new lines are ignored, because they can appear in the middle of printed elements.
 * was written since last call of {@link #snapshotLength()}
 */
class SnapshotPrinterTokenWriter extends PrinterTokenWriterDelegate {

	SnapshotPrinterTokenWriter(PrinterTokenWriter next) {
		super(next);
	}

	private int countOfTokens = 0;

	@Override
	public PrinterTokenWriterDelegate writeIdentifier(String token) {
		countOfTokens++;
		return super.writeIdentifier(token);
	}

	@Override
	public PrinterTokenWriterDelegate writeKeyword(String token) {
		countOfTokens++;
		return super.writeKeyword(token);
	}

	@Override
	public PrinterTokenWriterDelegate writeLiteral(String token) {
		countOfTokens++;
		return super.writeLiteral(token);
	}

	@Override
	public PrinterTokenWriterDelegate writeOperator(String token) {
		countOfTokens++;
		return super.writeOperator(token);
	}

	@Override
	public PrinterTokenWriterDelegate writeSeparator(String token) {
		countOfTokens++;
		return super.writeSeparator(token);
	}

	private ArrayDeque<Integer> lengths = new ArrayDeque<>();

	/** stores the length of the printer */
	public void snapshotLength() {
		//if the buffer contains some data then flush them first to send that token and use correct `countOfTokens`
		lengths.addLast(countOfTokens);
	}

	/** returns true if something has been written since the last call to snapshotLength() */
	public boolean hasNewContent() {
		return lengths.pollLast() < countOfTokens;
	}
}

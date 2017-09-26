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

import spoon.SpoonException;
import spoon.reflect.code.CtComment;

/**
 * {@link PrinterTokenWriter} implementation, which simply sends all tokens to next {@link PrinterTokenWriter}
 * Inherit your own implementation from this class to manipulates print tokens - e.g. to change formatting of printed output
 */
class PrinterTokenWriterDelegate implements PrinterTokenWriter {

	private PrinterTokenWriter next;

	PrinterTokenWriterDelegate() {
	}

	PrinterTokenWriterDelegate(PrinterTokenWriter next) {
		setNext(next);
	}

	@Override
	public PrinterHelper getPrinterHelper() {
		return next.getPrinterHelper();
	}

	public PrinterTokenWriter getNext() {
		return next;
	}

	public void setNext(PrinterTokenWriter next) {
		if (next == null) {
			throw new SpoonException("Next PrinterTokenWriter must not be null");
		}
		this.next = next;
	}

	@Override
	public void reset() {
		next.reset();
	}

	@Override
	public PrinterTokenWriterDelegate writeWhitespace(String token) {
		next.writeWhitespace(token);
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate writeSeparator(String token) {
		next.writeSeparator(token);
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate writeOperator(String token) {
		next.writeOperator(token);
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate writeLiteral(String token) {
		next.writeLiteral(token);
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate writeKeyword(String token) {
		next.writeKeyword(token);
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate writeIdentifier(String token) {
		next.writeIdentifier(token);
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate writeCodeSnippet(String token) {
		next.writeCodeSnippet(token);
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate writeComment(CtComment comment) {
		next.writeComment(comment);
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate writeln() {
		next.writeln();
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate writeTabs() {
		next.writeTabs();
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate incTab() {
		next.incTab();
		return this;
	}

	@Override
	public PrinterTokenWriterDelegate decTab() {
		next.decTab();
		return this;
	}

	@Override
	public String toString() {
		return getPrinterHelper().toString();
	}
}

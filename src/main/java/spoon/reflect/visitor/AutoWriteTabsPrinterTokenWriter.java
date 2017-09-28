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

/**
 * The special {@link PrinterTokenWriter} implementation,
 * ignores all writeTabs tokens produced by {@link DefaultJavaPrettyPrinter}.
 * This implementation automatically correctly writes tabs at places where they belongs to.
 * - calling writeTabs is no more responsibility of developer of {@link DefaultJavaPrettyPrinter}
 */
class AutoWriteTabsPrinterTokenWriter extends PrinterTokenWriterDelegate<AutoWriteTabsPrinterTokenWriter> {

	boolean shouldWriteTabs = true;

	AutoWriteTabsPrinterTokenWriter(PrinterTokenWriter next) {
		super(next);
	}

	@Override
	public AutoWriteTabsPrinterTokenWriter writeTabs() {
		//ignore that call. DJPP does not have to call it now at all - we can remove all these calls from DJPP sources -> easier maintenance
		return this;
	}

	@Override
	protected void onToken(spoon.reflect.visitor.PrinterTokenWriterDelegate.TokenType tokenType, Object token) {
		if (tokenType == TokenType.NEW_LINE) {
			//mark that it should write tabs after new line
			shouldWriteTabs = true;
		} else if (shouldWriteTabs && isTabCounterChangingToken(tokenType) == false) {
			/*
			 * the last token was new line. Now it is going to write some text. Write tabs to have text aligned by expected number of tabs.
			 * Note that tabs are not written if there is more empty new lines
			 */
			next.writeTabs();
			shouldWriteTabs = false;
		}
	}

	private boolean isTabCounterChangingToken(TokenType tokenType) {
		return tokenType == TokenType.INCREMENT_TABS || tokenType == TokenType.DECREMENT_TABS;
	}

	@Override
	public void reset() {
		shouldWriteTabs = true;
		super.reset();
	}
}

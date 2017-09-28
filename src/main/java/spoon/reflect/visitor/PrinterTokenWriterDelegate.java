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
public class PrinterTokenWriterDelegate<T extends PrinterTokenWriterDelegate<?>> implements PrinterTokenWriter {

	protected PrinterTokenWriter next;

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

	/**
	 * Defines type of token produced by PrettyPrinter
	 */
	public enum TokenType {
		/**
		 * One of the separator characters (){}[];,.:@=&lt;&gt;?
		 */
		SEPARATOR,
		/**
		 * The operator of an expression. For example "==", "*", ...
		 */
		OPERATOR,
		/**
		 * java keyword. For example "class", "public", ...
		 */
		KEYWORD,
		/**
		 * identifier. For example "TheNameOfMyClass_Or_VariableEtc"
		 */
		IDENTIFIER,
		/**
		 * all the spaces, new lines and tabs
		 */
		WHITESPACE,
		/**
		 * the java literals. For example
		 * "I am string"
		 * 'c'
		 * -4785.45
		 * 4456.65e10
		 * 0x00101011B
		 */
		LITERAL,
		/**
		 * The java comment. The token is instance of {@link CtComment}
		 */
		COMMENT,
		/**
		 * Special case of printing of CtCodeSnippetStatement.
		 * The tokens are not resolved here!
		 * Note:  the token value is {@link CtComment}
		 */
		CODE_SNIPPET,
		/**
		 * represents new line
		 * Note:  there is no token value expected here
		 */
		NEW_LINE,
		/**
		 * prints the indentation - expected number of tabs or spaces
		 * Note:  there is no token value expected here
		 */
		TABS,
		/**
		 * increments the indentation
		 * Note:  there is no token value expected here
		 */
		INCREMENT_TABS,
		/**
		 * decrements the indentation
		 * Note:  there is no token value expected here
		 */
		DECREMENT_TABS;
	}

	/**
	 * Called at the beginning of each token handler
	 */
	protected void onToken(TokenType tokenType, Object token) {
	}

	@Override
	public T writeWhitespace(String token) {
		onToken(TokenType.WHITESPACE, token);
		next.writeWhitespace(token);
		return (T) this;
	}

	@Override
	public T writeSeparator(String token) {
		onToken(TokenType.SEPARATOR, token);
		next.writeSeparator(token);
		return (T) this;
	}

	@Override
	public T writeOperator(String token) {
		onToken(TokenType.OPERATOR, token);
		next.writeOperator(token);
		return (T) this;
	}

	@Override
	public T writeLiteral(String token) {
		onToken(TokenType.LITERAL, token);
		next.writeLiteral(token);
		return (T) this;
	}

	@Override
	public T writeKeyword(String token) {
		onToken(TokenType.KEYWORD, token);
		next.writeKeyword(token);
		return (T) this;
	}

	@Override
	public T writeIdentifier(String token) {
		onToken(TokenType.IDENTIFIER, token);
		next.writeIdentifier(token);
		return (T) this;
	}

	@Override
	public T writeCodeSnippet(String token) {
		onToken(TokenType.CODE_SNIPPET, token);
		next.writeCodeSnippet(token);
		return (T) this;
	}

	@Override
	public T writeComment(CtComment comment) {
		onToken(TokenType.COMMENT, comment);
		next.writeComment(comment);
		return (T) this;
	}

	@Override
	public T writeln() {
		onToken(TokenType.NEW_LINE, null);
		next.writeln();
		return (T) this;
	}

	@Override
	public T writeTabs() {
		onToken(TokenType.TABS, null);
		next.writeTabs();
		return (T) this;
	}

	@Override
	public T incTab() {
		onToken(TokenType.INCREMENT_TABS, null);
		next.incTab();
		return (T) this;
	}

	@Override
	public T decTab() {
		onToken(TokenType.DECREMENT_TABS, null);
		next.decTab();
		return (T) this;
	}
}

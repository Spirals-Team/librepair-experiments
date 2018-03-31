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
package spoon.support.reflect.cu.position;

import spoon.SpoonException;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.visitor.printer.change.SourcePositionUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * This immutable class represents the position of a Java program element in a source
 * file.
 */
public class SourcePositionImpl implements SourcePosition, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Search the line number corresponding to a specific position
	 */
	protected int searchLineNumber(int position) {
		if (lineSeparatorPositions == null) {
			return 1;
		}
		int length = lineSeparatorPositions.length;
		if (length == 0) {
			return -1;
		}
		int g = 0, d = length - 1;
		int m = 0, start;
		while (g <= d) {
			m = (g + d) / 2;
			if (position < (start = lineSeparatorPositions[m])) {
				d = m - 1;
			} else if (position > start) {
				g = m + 1;
			} else {
				return m + 1;
			}
		}
		if (position < lineSeparatorPositions[m]) {
			return m + 1;
		}
		return m + 2;
	}

	/**
	 * Search the column number
	 */
	private int searchColumnNumber(int position) {
		if (lineSeparatorPositions == null) {
			return -1;
		}
		int length = lineSeparatorPositions.length;
		if (length == 0) {
			return -1;
		}
		int i = 0;
		for (i = 0; i < lineSeparatorPositions.length - 1; i++) {
			if (lineSeparatorPositions[i] < position && (lineSeparatorPositions[i + 1] > position)) {
				return position - lineSeparatorPositions[i];
			}
		}
		int tabCount = 0;
		int tabSize = 0;
		if (getCompilationUnit() != null) {
			tabSize = getCompilationUnit().getFactory().getEnvironment().getTabulationSize();
			String source = getCompilationUnit().getOriginalSourceCode();
			for (int j = lineSeparatorPositions[i]; j < position; j++) {
				if (source.charAt(j) == '\t') {
					tabCount++;
				}
			}
		}
		return (position - lineSeparatorPositions[i]) - tabCount + (tabCount * tabSize);
	}

	/** The position of the first byte of this element (incl. documentation and modifiers) */
	private final int sourceStart;

	/** The position of the last byte of this element */
	private final int sourceEnd;

	/** The line number of the start of the element, if appropriate (eg the method name).
	 * Computed lazily by {@link #getLine()}
	 */
	private int sourceStartline = -1;

	/**
	 * The index of line breaks, as computed by JDT.
	 * Used to compute line numbers afterwards.
	 */
	int[] lineSeparatorPositions;

	/** The file for this position, same pointer as in the compilation unit, but required for serialization. */
	private File file;

	private SourcePositionImpl nextSibling;
	private SourcePositionImpl firstChild;

	public SourcePositionImpl(CompilationUnit compilationUnit, int sourceStart, int sourceEnd, int[] lineSeparatorPositions) {
		super();
		if (sourceStart < 0 || sourceEnd < 0) {
			throw new SpoonException("Invalid source position");
		}
		this.compilationUnit = compilationUnit;
		if (compilationUnit != null) {
			this.file = compilationUnit.getFile();
		}
		this.sourceEnd = sourceEnd;
		this.sourceStart = sourceStart;
		this.lineSeparatorPositions = lineSeparatorPositions;
	}

	public int getColumn() {
		return searchColumnNumber(sourceStart);
	}

	public int getEndColumn() {
		return searchColumnNumber(sourceEnd);
	}

	public File getFile() {
		if (compilationUnit == null) {
			return file;
		}
		return compilationUnit.getFile();
	}

	public int getLine() {
		if (sourceStartline == -1) {
			this.sourceStartline = searchLineNumber(this.sourceStart);
		}
		return sourceStartline;
	}

	public int getEndLine() {
		return searchLineNumber(sourceEnd);
	}

	public int getSourceEnd() {
		return this.sourceEnd;
	}

	public int getSourceStart() {
		return this.sourceStart;
	}

	/**
	 * Returns a string representation of this position in the form
	 * "sourcefile:line", or "sourcefile" if no line number is available.
	 */
	@Override
	public String toString() {
		if (getFile() == null) {
			return "(unknown file)";
		}
		int ln = getLine();
		return ((ln >= 1) ? "(" + getFile().getAbsolutePath().replace('\\', '/').replace("C:/", "/") + ":" + ln + ")" : getFile().getAbsolutePath().replace('\\', '/').replace("C:/", "/"))
				+ "\n" + getSourceInfo();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SourcePosition)) {
			return false;
		}
		SourcePosition s = (SourcePosition) obj;
		return (getFile() == null ? s.getFile() == null : getFile().equals(s.getFile())) && getLine() == s.getLine() && getColumn() == s.getColumn();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getLine();
		result = prime * result + getColumn();
		result = prime * result + (getFile() != null ? getFile().hashCode() : 1);
		return result;
	}

	transient CompilationUnit compilationUnit;

	public CompilationUnit getCompilationUnit() {
		return compilationUnit;
	}

	protected String getFragment(int start, int end) {
		return "|" + start + ";" + end + "|" + getCompilationUnit().getOriginalSourceCode().substring(start, end + 1) + "|";
	}

	public String getSourceFragment() {
		return getFragment(getSourceStart(), getSourceEnd());
	}

	public String getSourceInfo() {
		return getSourceFragment();
	}

	@Override
	public SourcePositionImpl getNextSibling() {
		return nextSibling;
	}

	@Override
	public SourcePositionImpl getFirstChild() {
		return firstChild;
	}

	/**
	 * Builds tree of {@link SourcePosition} elements
	 * @param element the root element of the tree
	 */
	public static void buildTreeOfSourcePositions(CtElement element) {
		SourcePosition sp = element.getPosition();
		Set<CtElement> allElements = Collections.newSetFromMap(new IdentityHashMap<>());
		//TODO check that it is called only once
		new CtScanner() {
			Deque<CtElement> parents = new ArrayDeque<>();
			@Override
			protected void enter(CtElement e) {
				if (allElements.add(e) == false) {
					throw new SpoonException("The element is referenced twice");
				}
				addChild(parents.peek(), e);
				parents.push(e);
			}
			@Override
			protected void exit(CtElement e) {
				parents.pop();
			}
		}.scan(element);
	}
	private static void addChild(CtElement parentElement, CtElement childElement) {
		SourcePosition childSP = childElement.getPosition();
		if (childSP instanceof SourcePositionImpl) {
			SourcePositionImpl childSPI = (SourcePositionImpl) childSP;
			childSPI.checkValid();
			SourcePositionImpl parentSP = SourcePositionUtils.getMyOrParentsSourcePosition(parentElement);
			if (parentSP != null) {
				if (parentSP != childSPI) {
					int cmp = parentSP.compare(childSPI);
					if (cmp == 0) {
						parentSP.addChild(childSPI);
					} else {
						SourcePosition.class.getClass();
					}
				}
				//else these two elements has same instance of SourcePosition.
				//It is probably OK
			}
			return;
		}
		//do not connect that undefined source position
		return;
	}

	private void addChild(SourcePositionImpl child) {
		if (firstChild == null) {
			firstChild = child;
		} else {
			firstChild.addNextSibling(child);
		}
	}

	private void addNextSibling(SourcePositionImpl sibling) {
		if (nextSibling == sibling || sibling == this) {
			throw new SpoonException("SourcePositionImpl#addNextSibling must not be called twice for the same SourcePosition");
		}
		if (sibling.toString().indexOf("|1048;1065|processors == null|") >= 0) {
			this.getClass();
		}
		if (nextSibling == null) {
			nextSibling = sibling;
		} else {
			int cmp = nextSibling.compare(sibling);
			if (cmp == 1) {
				//sibling is after nextSibling
				nextSibling.addNextSibling(sibling);
			} else if (cmp == -1) {
				//sibling is before nextSibling
				//append sibling before nextSibling
				sibling.nextSibling = nextSibling;
				nextSibling = sibling;
			} else {
				//sibling is child of nextSibling
				nextSibling.addChild(sibling);
			}
		}
	}

	/**
	 * compares this and other
	 * @param other other {@link SourcePosition}
	 * @return
	 * 		-1 - if the other is before this
	 * 		 0 - if the other is child of this or equal to this (which means it is child too)
	 * 		 1 - if other is after this
	 * throws {@link SpoonException} if intervals overlap or start/end is negative
	 */
	private int compare(SourcePosition other) {
		if (other == this) {
			throw new SpoonException("SourcePositionImpl#addNextSibling must not be called twice for the same SourcePosition");
		}
		if (getSourceEnd() < other.getSourceStart()) {
			//other is after this
			return 1;
		}
		if (other.getSourceEnd() < getSourceStart()) {
			//other is before this
			return -1;
		}
		if (getSourceStart() <= other.getSourceStart() && getSourceEnd() >= other.getSourceEnd()) {
			return 0;
		}
		throw new SpoonException("Cannot compare this: [" + getSourceStart() + ", " + getSourceEnd() + "] with other: [\" + other.getSourceStart() + \", \" + other.getSourceEnd() + \"]");
	}

	private void checkValid() {
		if (getSourceEnd() < 0 || getSourceEnd() < 0) {
			throw new SpoonException("Unexpected negative source position");
		}
		if (getSourceStart() > getSourceEnd()) {
			throw new SpoonException("Invalid start/end. Start is after end.");
		}
	}

}

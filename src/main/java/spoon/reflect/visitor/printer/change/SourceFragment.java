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
package spoon.reflect.visitor.printer.change;

import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.printer.change.SourcePositionUtils.FragmentDescriptor;

/**
 * Represents a part of source code of an {@link CtElement}
 */
class SourceFragment  {

	private final SourcePosition sourcePosition;
	private final FragmentType fragmentType;
	private final int start;
	private final int end;
	private boolean modified = false;
	FragmentDescriptor fragmentDescriptor;

	SourceFragment(SourcePosition sourcePosition, FragmentType fragmentType, int start, int end) {
		super();
		this.sourcePosition = sourcePosition;
		this.fragmentType = fragmentType;
		this.start = start;
		this.end = end;
	}

	public FragmentType getFragmentType() {
		return fragmentType;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public SourcePosition getSourcePosition() {
		return sourcePosition;
	}

	@Override
	public String toString() {
		CompilationUnit cu = sourcePosition.getCompilationUnit();
		if (cu != null) {
			String src = cu.getOriginalSourceCode();
			if (src != null) {
				return src.substring(start, end);
			}
		}
		return null;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}
}

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

import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.cu.Source;

import java.io.File;


/**
 * This immutable class represents the source of Java program element
 */
public class SourceImpl implements Source {

	private static final long serialVersionUID = 2L;

	/** The file for this position, same pointer as in the compilation unit, but required for serialization. */
	private File file;

	public SourceImpl(CompilationUnit compilationUnit) {
		super();
		this.compilationUnit = compilationUnit;
		if (compilationUnit != null) {
			this.file = compilationUnit.getFile();
		}
	}

	public File getFile() {
		if (compilationUnit == null) {
			return file;
		}
		return compilationUnit.getFile();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Source)) {
			return false;
		}
		Source s = (Source) obj;
		return (getFile() == null ? s.getFile() == null : getFile().equals(s.getFile()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getFile() != null ? getFile().hashCode() : 1);
		return result;
	}

	transient CompilationUnit compilationUnit;

	public CompilationUnit getCompilationUnit() {
		return compilationUnit;
	}
}

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
package spoon.reflect.cu.position;


/**
 * This class represents the position of a program element in a source file.
 */
public class NoSourcePosition implements java.io.Serializable , spoon.reflect.cu.SourcePosition {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public java.io.File getFile() {
        return null;
    }

    @java.lang.Override
    public spoon.reflect.cu.CompilationUnit getCompilationUnit() {
        return null;
    }

    @java.lang.Override
    public boolean isValidPosition() {
        return false;
    }

    @java.lang.Override
    public int getLine() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getEndLine() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getColumn() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getEndColumn() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getSourceEnd() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getSourceStart() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "(unknown file)";
    }
}


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


/**
 * This class represents the position of a Java program element in a source
 * file.
 */
public class DeclarationSourcePositionImpl extends spoon.support.reflect.cu.position.SourcePositionImpl implements java.io.Serializable , spoon.reflect.cu.position.DeclarationSourcePosition {
    private static final long serialVersionUID = 1L;

    private int modifierSourceEnd;

    private int modifierSourceStart;

    private int declarationSourceStart;

    private int declarationSourceEnd;

    public DeclarationSourcePositionImpl(spoon.reflect.cu.CompilationUnit compilationUnit, int sourceStart, int sourceEnd, int modifierSourceStart, int modifierSourceEnd, int declarationSourceStart, int declarationSourceEnd, int[] lineSeparatorPositions) {
        super(compilationUnit, sourceStart, sourceEnd, lineSeparatorPositions);
        spoon.support.reflect.cu.position.SourcePositionImpl.checkArgsAreAscending(declarationSourceStart, modifierSourceStart, (modifierSourceEnd + 1), sourceStart, (sourceEnd + 1), (declarationSourceEnd + 1));
        this.modifierSourceStart = modifierSourceStart;
        this.declarationSourceStart = declarationSourceStart;
        this.declarationSourceEnd = declarationSourceEnd;
        if ((this.modifierSourceStart) == 0) {
            this.modifierSourceStart = declarationSourceStart;
        }
        this.modifierSourceEnd = modifierSourceEnd;
    }

    @java.lang.Override
    public int getSourceEnd() {
        return declarationSourceEnd;
    }

    @java.lang.Override
    public int getSourceStart() {
        return declarationSourceStart;
    }

    @java.lang.Override
    public int getModifierSourceStart() {
        return modifierSourceStart;
    }

    @java.lang.Override
    public int getNameStart() {
        return super.getSourceStart();
    }

    @java.lang.Override
    public int getNameEnd() {
        return super.getSourceEnd();
    }

    public void setModifierSourceEnd(int modifierSourceEnd) {
        this.modifierSourceEnd = modifierSourceEnd;
    }

    @java.lang.Override
    public int getModifierSourceEnd() {
        return modifierSourceEnd;
    }

    public int getEndLine() {
        return searchLineNumber(declarationSourceEnd);
    }

    @java.lang.Override
    public java.lang.String getSourceDetails() {
        return ((((super.getSourceDetails()) + "\nmodifier = ") + (getFragment(getModifierSourceStart(), getModifierSourceEnd()))) + "\nname = ") + (getFragment(getNameStart(), getNameEnd()));
    }
}


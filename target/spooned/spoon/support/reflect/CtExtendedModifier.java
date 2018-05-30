/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 * <p>
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 * <p>
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.reflect;


/**
 * Represents a modifier (eg "public").
 * When a modifier is "implicit", it does not appear in the source code (eg public for interface methods)
 * ModifierKind in kept for sake of full backward-compatibility.
 */
public class CtExtendedModifier implements java.io.Serializable {
    private boolean implicit;

    private spoon.reflect.declaration.ModifierKind kind;

    private spoon.reflect.cu.SourcePosition position;

    public CtExtendedModifier(spoon.reflect.declaration.ModifierKind kind) {
        this.kind = kind;
    }

    public CtExtendedModifier(spoon.reflect.declaration.ModifierKind kind, boolean implicit) {
        this(kind);
        this.implicit = implicit;
    }

    public boolean isImplicit() {
        return implicit;
    }

    public void setImplicit(boolean implicit) {
        this.implicit = implicit;
    }

    public spoon.reflect.declaration.ModifierKind getKind() {
        return kind;
    }

    public void setKind(spoon.reflect.declaration.ModifierKind kind) {
        this.kind = kind;
    }

    public spoon.reflect.cu.SourcePosition getPosition() {
        if ((position) == null) {
            return spoon.reflect.cu.SourcePosition.NOPOSITION;
        }
        return position;
    }

    public void setPosition(spoon.reflect.cu.SourcePosition position) {
        this.position = position;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if ((this) == o) {
            return true;
        }
        if ((o == null) || ((getClass()) != (o.getClass()))) {
            return false;
        }
        spoon.support.reflect.CtExtendedModifier that = ((spoon.support.reflect.CtExtendedModifier) (o));
        return ((implicit) == (that.implicit)) && ((kind) == (that.kind));
    }

    @java.lang.Override
    public int hashCode() {
        int result = (implicit) ? 1 : 0;
        result = (31 * result) + ((kind) != null ? kind.hashCode() : 0);
        return result;
    }
}


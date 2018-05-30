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
package spoon.support.visitor.equals;


/**
 * Used to check equality between an element and another one.
 */
public class EqualsVisitor extends spoon.reflect.visitor.CtBiScannerDefault {
    public static boolean equals(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        return new spoon.support.visitor.equals.EqualsVisitor().checkEquals(element, other);
    }

    protected final spoon.support.visitor.equals.EqualsChecker checker;

    private spoon.reflect.path.CtRole lastRole = null;

    public EqualsVisitor() {
        this(new spoon.support.visitor.equals.EqualsChecker());
    }

    public EqualsVisitor(spoon.support.visitor.equals.EqualsChecker checker) {
        this.checker = checker;
    }

    @java.lang.Override
    protected void enter(spoon.reflect.declaration.CtElement e) {
        super.enter(e);
        spoon.reflect.declaration.CtElement other = stack.peek();
        checker.setOther(other);
        try {
            checker.scan(e);
        } catch (spoon.support.visitor.equals.NotEqualException ex) {
            fail(((checker.getNotEqualRole()) == null ? lastRole : checker.getNotEqualRole()), e, other);
        }
    }

    protected boolean isNotEqual = false;

    protected spoon.reflect.path.CtRole notEqualRole;

    protected java.lang.Object notEqualElement;

    protected java.lang.Object notEqualOther;

    @java.lang.Override
    protected void biScan(spoon.reflect.path.CtRole role, java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements, java.util.Collection<? extends spoon.reflect.declaration.CtElement> others) {
        if (isNotEqual) {
            return;
        }
        if (elements == null) {
            if (others != null) {
                fail(role, elements, others);
            }
            return;
        }else
            if (others == null) {
                fail(role, elements, others);
                return;
            }

        if ((elements.size()) != (others.size())) {
            fail(role, elements, others);
            return;
        }
        super.biScan(role, elements, others);
    }

    @java.lang.Override
    public void biScan(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        biScan(null, element, other);
    }

    @java.lang.Override
    public void biScan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        if (isNotEqual) {
            return;
        }
        if (element == null) {
            if (other != null) {
                fail(role, element, other);
                return;
            }
            return;
        }else
            if (other == null) {
                fail(role, element, other);
                return;
            }

        if (element == other) {
            return;
        }
        try {
            lastRole = role;
            super.biScan(element, other);
        } catch (java.lang.ClassCastException e) {
            fail(role, element, other);
        } finally {
            lastRole = null;
        }
        return;
    }

    protected boolean fail(spoon.reflect.path.CtRole role, java.lang.Object element, java.lang.Object other) {
        isNotEqual = true;
        notEqualRole = role;
        notEqualElement = element;
        notEqualOther = other;
        return true;
    }

    /**
     *
     *
     * @param element
     * 		first to be compared element
     * @param other
     * 		second to be compared element
     * @return true if `element` and `other` are equal. If false then see
     * {@link #getNotEqualElement()}, {@link #getNotEqualOther()} and {@link #getNotEqualRole()} for details
     */
    public boolean checkEquals(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        biScan(element, other);
        return !(isNotEqual);
    }

    /**
     *
     *
     * @return true if {@link #checkEquals(CtElement, CtElement)} are equal. If false then see
     * {@link #getNotEqualElement()}, {@link #getNotEqualOther()} and {@link #getNotEqualRole()} for details
     */
    public boolean isEqual() {
        return !(isNotEqual);
    }

    /**
     *
     *
     * @return role on which the element and other element were not equal
     */
    public spoon.reflect.path.CtRole getNotEqualRole() {
        return notEqualRole;
    }

    /**
     *
     *
     * @return element or collection which was not equal
     */
    public java.lang.Object getNotEqualElement() {
        return notEqualElement;
    }

    /**
     *
     *
     * @return other element or collection which was not equal
     */
    public java.lang.Object getNotEqualOther() {
        return notEqualOther;
    }
}


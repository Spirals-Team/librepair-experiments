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
package spoon.reflect.code;


/**
 * This abstract code element represents all the statements, which can be part
 * of a block.
 *
 * @see spoon.reflect.code.CtBlock
 */
public interface CtStatement extends spoon.reflect.code.CtCodeElement {
    /**
     * Inserts a statement after the current statement.
     */
    <T extends spoon.reflect.code.CtStatement> T insertAfter(spoon.reflect.code.CtStatement statement) throws spoon.reflect.declaration.ParentNotInitializedException;

    /**
     * Inserts a statement list before the current statement.
     */
    <T extends spoon.reflect.code.CtStatement> T insertAfter(spoon.reflect.code.CtStatementList statements) throws spoon.reflect.declaration.ParentNotInitializedException;

    /**
     * Inserts a statement given as parameter before the current statement
     * (this).
     */
    <T extends spoon.reflect.code.CtStatement> T insertBefore(spoon.reflect.code.CtStatement statement) throws spoon.reflect.declaration.ParentNotInitializedException;

    /**
     * Inserts a statement list before the current statement.
     */
    <T extends spoon.reflect.code.CtStatement> T insertBefore(spoon.reflect.code.CtStatementList statements) throws spoon.reflect.declaration.ParentNotInitializedException;

    /**
     * Gets the label of this statement if defined.
     *
     * @return the label's name (null if undefined)
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.LABEL)
    java.lang.String getLabel();

    /**
     * Sets the label of this statement.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.LABEL)
    <T extends spoon.reflect.code.CtStatement> T setLabel(java.lang.String label);

    @java.lang.Override
    spoon.reflect.code.CtStatement clone();
}


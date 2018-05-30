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
 * This code element represents a list of statements. It is not a valid Java
 * program element and is never used directly, on contrary to
 * a {@link spoon.reflect.code.CtBlock}.
 */
public interface CtStatementList extends java.lang.Iterable<spoon.reflect.code.CtStatement> , spoon.reflect.code.CtCodeElement {
    /**
     * Returns the statement list.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.STATEMENT)
    java.util.List<spoon.reflect.code.CtStatement> getStatements();

    /**
     * Sets the statement list.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T setStatements(java.util.List<spoon.reflect.code.CtStatement> statements);

    /**
     * Adds a statement at the end of the list.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T addStatement(spoon.reflect.code.CtStatement statement);

    /**
     * Inserts the given statement at a specific position in the list of statements
     * Shifts the statement currently at that position (if any) and any subsequent statements to the right (adds one to their indices).
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T addStatement(int index, spoon.reflect.code.CtStatement statement);

    /**
     * Inserts the given statement at the beginning of the block.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T insertBegin(spoon.reflect.code.CtStatement statement);

    /**
     * Inserts the given statement list at the beginning of the block.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T insertBegin(spoon.reflect.code.CtStatementList statements);

    /**
     * Inserts the given statement at the end of the block.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T insertEnd(spoon.reflect.code.CtStatement statement);

    /**
     * Inserts the given statements at the end of the block.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    <T extends spoon.reflect.code.CtStatementList> T insertEnd(spoon.reflect.code.CtStatementList statements);

    /**
     * Inserts the given statement before a set of insertion points given by a
     * filter.
     */
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatementList> T insertBefore(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatement statement);

    /**
     * Inserts the given statement list before a set of insertion points given
     * by a filter.
     */
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatementList> T insertBefore(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatementList statements);

    /**
     * Inserts the given statement after a set of insertion points given by a
     * filter.
     */
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatementList> T insertAfter(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatement statement);

    /**
     * Inserts the given statement list after a set of insertion points given by
     * a filter.
     */
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatementList> T insertAfter(spoon.reflect.visitor.Filter<? extends spoon.reflect.code.CtStatement> insertionPoints, spoon.reflect.code.CtStatementList statements);

    /**
     * Gets the ith statement of this block.
     */
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatement> T getStatement(int i);

    /**
     * Gets the last statement of this block.
     */
    @spoon.support.DerivedProperty
    <T extends spoon.reflect.code.CtStatement> T getLastStatement();

    /**
     * Removes a statement.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.STATEMENT)
    void removeStatement(spoon.reflect.code.CtStatement statement);

    @java.lang.Override
    spoon.reflect.code.CtStatementList clone();
}


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
 * This code element defines a <code>try</code> statement.
 *
 * Example:
 * <pre>
 *     try {
 *     	System.out.println("foo");
 *     } catch (Exception ignore) {}
 * </pre>
 */
public interface CtTry extends spoon.reflect.code.CtBodyHolder , spoon.reflect.code.CtStatement , spoon.template.TemplateParameter<java.lang.Void> {
    /**
     * Gets the <i>catchers</i> of this <code>try</code>.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CATCH)
    java.util.List<spoon.reflect.code.CtCatch> getCatchers();

    /**
     * Sets the <i>catchers</i> of this <code>try</code>.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CATCH)
    <T extends spoon.reflect.code.CtTry> T setCatchers(java.util.List<spoon.reflect.code.CtCatch> catchers);

    /**
     * Adds a catch block.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CATCH)
    <T extends spoon.reflect.code.CtTry> T addCatcher(spoon.reflect.code.CtCatch catcher);

    /**
     * Removes a catch block.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CATCH)
    boolean removeCatcher(spoon.reflect.code.CtCatch catcher);

    /**
     * Gets the try body.
     */
    @java.lang.Override
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> getBody();

    /**
     * Gets the <i>finalizer</i> block of this <code>try</code> (
     * <code>finally</code> part).
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FINALIZER)
    spoon.reflect.code.CtBlock<?> getFinalizer();

    /**
     * Sets the <i>finalizer</i> block of this <code>try</code> (
     * <code>finally</code> part).
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FINALIZER)
    <T extends spoon.reflect.code.CtTry> T setFinalizer(spoon.reflect.code.CtBlock<?> finalizer);

    @java.lang.Override
    spoon.reflect.code.CtTry clone();
}


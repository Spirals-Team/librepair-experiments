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
package spoon.reflect.declaration;


/**
 * This element defines a method declaration.
 */
public interface CtMethod<T> extends spoon.reflect.declaration.CtExecutable<T> , spoon.reflect.declaration.CtFormalTypeDeclarer , spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtTypeMember {
    /**
     *
     *
     * @param superMethod
     * 		to be checked method
     * @return true if this method overrides `superMethod`.<br>
     * Returns true for itself too.
     * <pre>
     * assertTrue(this.isOverriding(this))
     * </pre>
     */
    boolean isOverriding(spoon.reflect.declaration.CtMethod<?> superMethod);

    /**
     * Checks if the method is a default method. Default method can be in interfaces from
     * Java 8: http://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_DEFAULT)
    boolean isDefaultMethod();

    /**
     * Sets the default value state of a method.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_DEFAULT)
    <C extends spoon.reflect.declaration.CtMethod<T>> C setDefaultMethod(boolean defaultMethod);

    @java.lang.Override
    spoon.reflect.declaration.CtMethod<T> clone();

    /**
     * Returns the top-most methods in the hierarchy defining this method
     * (in super class and super interfaces).
     * Returns the empty collection if defined here for the first time.
     */
    java.util.Collection<spoon.reflect.declaration.CtMethod<?>> getTopDefinitions();

    /**
     * Copy the method, where copy means cloning + porting all the references of the old method to the new method (important for recursive methods).
     * The copied method is added to the type, with a suffix "Copy".
     *
     * A new unique method name is given for each copy, and this method can be called several times.
     *
     * If you want to rename the new method, use {@link Refactoring#changeMethodName(CtMethod, String)} (and not {@link #setSimpleName(String)}, which does not update the references)
     */
    spoon.reflect.declaration.CtMethod<?> copyMethod();
}


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
 * This element defines an anonymous executable block declaration in a class.
 *
 * @see spoon.reflect.declaration.CtClass
 */
public interface CtAnonymousExecutable extends spoon.reflect.declaration.CtExecutable<java.lang.Void> , spoon.reflect.declaration.CtTypeMember {
    @java.lang.Override
    spoon.reflect.declaration.CtAnonymousExecutable clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtExecutable<java.lang.Void>> T setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtExecutable<java.lang.Void>> T setParameters(java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<java.lang.Void> type);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtExecutable<java.lang.Void>> T addParameter(spoon.reflect.declaration.CtParameter<?> parameter);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtExecutable<java.lang.Void>> T addThrownType(spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> throwType);
}


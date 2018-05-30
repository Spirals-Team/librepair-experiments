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
package spoon.reflect;


/**
 * represents a Java program, modeled by a set of compile-time (Ct) objects
 * where each object is a program element (for instance, a CtClass represents a class).
 */
public interface CtModel extends java.io.Serializable , spoon.reflect.visitor.chain.CtQueryable {
    /**
     * returns the root package of the unnamed module
     */
    spoon.reflect.declaration.CtPackage getRootPackage();

    /**
     * returns all top-level types of the model
     */
    java.util.Collection<spoon.reflect.declaration.CtType<?>> getAllTypes();

    /**
     * returns all packages of the model
     */
    java.util.Collection<spoon.reflect.declaration.CtPackage> getAllPackages();

    /**
     * Returns the unnamed module.
     */
    spoon.reflect.declaration.CtModule getUnnamedModule();

    /**
     * returns all modules of the model
     */
    java.util.Collection<spoon.reflect.declaration.CtModule> getAllModules();

    /**
     * process this model with the given processor
     */
    void processWith(spoon.processing.Processor<?> processor);

    /**
     * Returns all the model elements matching the filter.
     */
    <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getElements(spoon.reflect.visitor.Filter<E> filter);
}


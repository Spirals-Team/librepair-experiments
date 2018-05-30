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
 * This element defines a package declaration. The packages are represented by a
 * tree.
 */
public interface CtPackage extends spoon.reflect.declaration.CtNamedElement , spoon.reflect.declaration.CtShadowable {
    /**
     * The separator for a string representation of a package.
     */
    java.lang.String PACKAGE_SEPARATOR = ".";

    char PACKAGE_SEPARATOR_CHAR = '.';

    /**
     * The name for the top level package.
     */
    java.lang.String TOP_LEVEL_PACKAGE_NAME = "unnamed package";

    /**
     * Gets the declaring module.
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtModule getDeclaringModule();

    /**
     * Gets the declaring package of the current one. Returns null if the package is not yet in another one.
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtPackage getDeclaringPackage();

    /**
     * Searches a child package by name.
     *
     * @param name
     * 		the simple name of searched package
     * @return the found package or null
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    spoon.reflect.declaration.CtPackage getPackage(java.lang.String name);

    /**
     * Gets the set of included child packages.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    java.util.Set<spoon.reflect.declaration.CtPackage> getPackages();

    /**
     * Returns the fully qualified name of this package. This is also known as
     * the package's <i>canonical</i> name.
     *
     * @return the fully qualified name of this package, or the empty string if
     * this is the unnamed package
     */
    java.lang.String getQualifiedName();

    /* (non-Javadoc)

    @see spoon.reflect.declaration.CtNamedElement#getReference()
     */
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtPackageReference getReference();

    /**
     * Finds a top-level type by name.
     *
     * @return the found type or null
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    <T extends spoon.reflect.declaration.CtType<?>> T getType(java.lang.String simpleName);

    /**
     * Returns the set of the top-level types in this package.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    java.util.Set<spoon.reflect.declaration.CtType<?>> getTypes();

    /**
     * Adds a type to this package.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    <T extends spoon.reflect.declaration.CtPackage> T addType(spoon.reflect.declaration.CtType<?> type);

    /**
     * Removes a type from this package.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    void removeType(spoon.reflect.declaration.CtType<?> type);

    /**
     * Sets the children defined in this package
     *
     * @param pack
     * 		new set of child packages
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    <T extends spoon.reflect.declaration.CtPackage> T setPackages(java.util.Set<spoon.reflect.declaration.CtPackage> pack);

    /**
     * add a subpackage
     *
     * @param pack
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    <T extends spoon.reflect.declaration.CtPackage> T addPackage(spoon.reflect.declaration.CtPackage pack);

    /**
     * remove a subpackage
     *
     * @param pack
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    boolean removePackage(spoon.reflect.declaration.CtPackage pack);

    /**
     * Sets the types defined in the package.
     *
     * @param types
     * 		new Set of types
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    <T extends spoon.reflect.declaration.CtPackage> T setTypes(java.util.Set<spoon.reflect.declaration.CtType<?>> types);

    @java.lang.Override
    spoon.reflect.declaration.CtPackage clone();

    /**
     * Returns {@code true} if this is an <i>unnamed</i> Java package.
     * See JLS §7.4.2. Unnamed Packages.
     */
    boolean isUnnamedPackage();
}


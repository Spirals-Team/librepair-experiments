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
package spoon.reflect.visitor.filter;


/**
 * Expects a {@link CtTypeInformation} as input
 * and produces all super classes and super interfaces recursively.<br>
 * The output is produced in following order:
 * <ol>
 * <li>input type. if `includingSelf==true`
 * <li>all interfaces of type recursively
 * <li>parent class of type
 * <li>goto 1: using  parent class as input type
 * </ol>
 */
public class SuperInheritanceHierarchyFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtTypeInformation> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean includingSelf = false;

    private boolean includingInterfaces = true;

    private spoon.reflect.visitor.chain.CtQuery query;

    private boolean failOnClassNotFound = false;

    private spoon.reflect.visitor.chain.CtScannerListener listener;

    private boolean returnTypeReferences = false;

    /**
     * Super inheritance hierarchy scanning listener.
     * Use it instead of {@link CtScannerListener}
     * if you need to know whether visited type reference is class or interface
     */
    private static class Listener implements spoon.reflect.visitor.chain.CtScannerListener {
        /**
         * Called before the scanner enters an type
         *
         * @param typeRef
         * 		the type reference to be scanned.
         * @param isClass
         * 		true if type reference refers to class, false if it is an interface
         * @return a {@link ScanningMode} that drives how the scanner processes this element and its children.
         * For instance, returning {@link ScanningMode#SKIP_ALL} causes that element and all children to be skipped and {@link #exit(CtElement)} are be NOT called for that element.
         */
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.reference.CtTypeReference<?> typeRef, boolean isClass) {
            return enter(((spoon.reflect.declaration.CtElement) (typeRef)));
        }

        /**
         * This method is called after the element and all its children have been visited.
         * This method is NOT called if an exception is thrown in {@link #enter(CtElement)} or during the scanning of the element or any of its children element.
         * This method is NOT called for an element for which {@link #enter(CtElement)} returned {@link ScanningMode#SKIP_ALL}.
         *
         * @param typeRef
         * 		the type reference that has just been scanned.
         * @param isClass
         * 		true if type reference refers to class, false if it is an interface
         */
        public void exit(spoon.reflect.reference.CtTypeReference<?> typeRef, boolean isClass) {
            exit(((spoon.reflect.declaration.CtElement) (typeRef)));
        }

        @java.lang.Override
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
            return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
        }

        @java.lang.Override
        public void exit(spoon.reflect.declaration.CtElement element) {
        }
    }

    /**
     * The mapping function created using this constructor
     * will visit each super class and super interface
     * following super hierarchy. It can happen
     * that some interfaces will be visited more then once
     * if they are in super inheritance hierarchy more then once.<br>
     * Use second constructor if you want to visit each interface only once.
     */
    public SuperInheritanceHierarchyFunction() {
    }

    /**
     * The mapping function created using this constructor
     * will visit each super class and super interface
     * following super hierarchy. It is assured
     * that interfaces will be visited only once
     * even if they are in super inheritance hierarchy more then once.<br>
     *
     * @param visitedSet
     * 		assures that each class/interface is visited only once
     * 		The types which are already contained in `visitedSet` are not visited
     * 		and not returned by this mapping function.
     */
    public SuperInheritanceHierarchyFunction(java.util.Set<java.lang.String> visitedSet) {
        listener = new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.DistinctTypeListener(visitedSet);
    }

    /**
     * Implementation of {@link CtScannerListener},
     * which is used to assure that each interface is visited only once.
     * It can be extended to implement more powerful listener
     */
    public static class DistinctTypeListener extends spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener {
        java.util.Set<java.lang.String> visitedSet;

        public DistinctTypeListener(java.util.Set<java.lang.String> visitedSet) {
            this.visitedSet = visitedSet;
        }

        @java.lang.Override
        public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
            if (visitedSet.add(((spoon.reflect.declaration.CtTypeInformation) (element)).getQualifiedName())) {
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }
            return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
        }

        @java.lang.Override
        public void exit(spoon.reflect.declaration.CtElement element) {
        }
    }

    /**
     *
     *
     * @param includingSelf
     * 		if true then input element is sent to output too. By default it is false.
     */
    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    /**
     *
     *
     * @param includingInterfaces
     * 		if false then interfaces are not visited - only super classes. By default it is true.
     */
    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction includingInterfaces(boolean includingInterfaces) {
        this.includingInterfaces = includingInterfaces;
        return this;
    }

    /**
     * configures whether {@link CtType} or {@link CtTypeReference} instances are returned by this mapping function
     *
     * @param returnTypeReferences
     * 		if true then {@link CtTypeReference} instances are returned by this mapping function
     * @return this to support fluent API
     */
    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction returnTypeReferences(boolean returnTypeReferences) {
        this.returnTypeReferences = returnTypeReferences;
        return this;
    }

    /**
     * The listener evens are called in this order:
     * <ol>
     * <li> enter(input element)
     * <li> return input element
     * <li> enter/exit for each super interface of input element recursively
     * <li> call 1-5) recursively where input element is super class of input element
     * <li> exit(input element)
     * </ol>
     * Note: this order is assured and some algorithms already depend on it!
     *
     * @param listener
     * 		the implementation of {@link CtScannerListener}, which will listen for enter/exit of {@link CtTypeReference} during type hierarchy scanning
     * @return this to support fluent API
     */
    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction setListener(spoon.reflect.visitor.chain.CtScannerListener listener) {
        if ((this.listener) != null) {
            throw new spoon.SpoonException("Cannot register listener on instance created with constructor which accepts the Set<String>. Use the no parameter constructor if listener has to be registered");
        }
        this.listener = listener;
        return this;
    }

    /**
     *
     *
     * @param failOnClassNotFound
     * 		sets whether processing should throw an exception if class is missing in noClassPath mode
     */
    public spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction failOnClassNotFound(boolean failOnClassNotFound) {
        this.failOnClassNotFound = failOnClassNotFound;
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtTypeInformation input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        spoon.reflect.reference.CtTypeReference<?> typeRef;
        spoon.reflect.declaration.CtType<?> type;
        // detect whether input is a class or something else (e.g. interface)
        boolean isClass;
        if (input instanceof spoon.reflect.declaration.CtType) {
            type = ((spoon.reflect.declaration.CtType<?>) (input));
            typeRef = type.getReference();
        }else {
            typeRef = ((spoon.reflect.reference.CtTypeReference<?>) (input));
            try {
                type = typeRef.getTypeDeclaration();
            } catch (spoon.support.SpoonClassNotFoundException e) {
                if ((typeRef.getFactory().getEnvironment().getNoClasspath()) == false) {
                    throw e;
                }
                type = null;
            }
        }
        // if the type is unknown, than we expect it is interface, otherwise we would visit java.lang.Object too, even for interfaces
        isClass = (type == null) ? false : type instanceof spoon.reflect.declaration.CtClass;
        if ((isClass == false) && ((includingInterfaces) == false)) {
            // the input is interface, but this scanner should visit only interfaces. Finish
            return;
        }
        spoon.reflect.visitor.chain.ScanningMode mode = enter(typeRef, isClass);
        if (mode == (spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL)) {
            // listener decided to not visit that input. Finish
            return;
        }
        if (includingSelf) {
            sendResult(typeRef, outputConsumer);
            if (query.isTerminated()) {
                mode = spoon.reflect.visitor.chain.ScanningMode.SKIP_CHILDREN;
            }
        }
        if (mode == (spoon.reflect.visitor.chain.ScanningMode.NORMAL)) {
            if (isClass == false) {
                visitSuperInterfaces(typeRef, outputConsumer);
            }else {
                // call visitSuperClasses only for input of type class. The contract of visitSuperClasses requires that
                visitSuperClasses(typeRef, outputConsumer, includingInterfaces);
            }
        }
        exit(typeRef, isClass);
    }

    /**
     * calls `outputConsumer.accept(superClass)` for all super classes of superType.
     *
     * @param superTypeRef
     * 		the reference to a class. This method is called only for classes. Never for interface
     * @param includingInterfaces
     * 		if true then all superInterfaces of each type are sent to `outputConsumer` too.
     */
    protected void visitSuperClasses(spoon.reflect.reference.CtTypeReference<?> superTypeRef, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer, boolean includingInterfaces) {
        if (java.lang.Object.class.getName().equals(superTypeRef.getQualifiedName())) {
            // java.lang.Object has no interface or super classes
            return;
        }
        if (includingInterfaces) {
            visitSuperInterfaces(superTypeRef, outputConsumer);
            if (query.isTerminated()) {
                return;
            }
        }
        spoon.reflect.reference.CtTypeReference<?> superClassRef = superTypeRef.getSuperclass();
        if (superClassRef == null) {
            // only CtClasses extend object,
            // this method is called only for classes (not for interfaces) so we know we can visit java.lang.Object now too
            superClassRef = superTypeRef.getFactory().Type().OBJECT;
        }
        spoon.reflect.visitor.chain.ScanningMode mode = enter(superClassRef, true);
        if (mode == (spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL)) {
            return;
        }
        sendResult(superClassRef, outputConsumer);
        if ((mode == (spoon.reflect.visitor.chain.ScanningMode.NORMAL)) && ((query.isTerminated()) == false)) {
            visitSuperClasses(superClassRef, outputConsumer, includingInterfaces);
        }
        exit(superClassRef, true);
    }

    /**
     * calls `outputConsumer.accept(interface)` for all superInterfaces of type recursively.
     */
    protected void visitSuperInterfaces(spoon.reflect.reference.CtTypeReference<?> type, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        java.util.Set<spoon.reflect.reference.CtTypeReference<?>> superInterfaces;
        try {
            superInterfaces = type.getSuperInterfaces();
        } catch (spoon.support.SpoonClassNotFoundException e) {
            if (failOnClassNotFound) {
                throw e;
            }
            spoon.Launcher.LOGGER.warn(((("Cannot load class: " + (type.getQualifiedName())) + " with class loader ") + (java.lang.Thread.currentThread().getContextClassLoader())));
            return;
        }
        for (spoon.reflect.reference.CtTypeReference<?> ifaceRef : superInterfaces) {
            spoon.reflect.visitor.chain.ScanningMode mode = enter(ifaceRef, false);
            if (mode == (spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL)) {
                continue;
            }
            sendResult(ifaceRef, outputConsumer);
            if ((mode == (spoon.reflect.visitor.chain.ScanningMode.NORMAL)) && ((query.isTerminated()) == false)) {
                visitSuperInterfaces(ifaceRef, outputConsumer);
            }
            exit(ifaceRef, false);
            if (query.isTerminated()) {
                return;
            }
        }
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }

    private spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.reference.CtTypeReference<?> type, boolean isClass) {
        if ((listener) == null) {
            return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
        }
        if ((listener) instanceof spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener) {
            spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener typeListener = ((spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener) (listener));
            return typeListener.enter(type, isClass);
        }
        return listener.enter(type);
    }

    private void exit(spoon.reflect.reference.CtTypeReference<?> type, boolean isClass) {
        if ((listener) != null) {
            if ((listener) instanceof spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener) {
                ((spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction.Listener) (listener)).exit(type, isClass);
            }else {
                listener.exit(type);
            }
        }
    }

    protected void sendResult(spoon.reflect.reference.CtTypeReference<?> typeRef, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        if (returnTypeReferences) {
            outputConsumer.accept(typeRef);
        }else {
            spoon.reflect.declaration.CtType<?> type;
            try {
                type = typeRef.getTypeDeclaration();
            } catch (spoon.support.SpoonClassNotFoundException e) {
                if (failOnClassNotFound) {
                    throw e;
                }
                spoon.Launcher.LOGGER.warn(((("Cannot load class: " + (typeRef.getQualifiedName())) + " with class loader ") + (java.lang.Thread.currentThread().getContextClassLoader())));
                return;
            }
            outputConsumer.accept(type);
        }
    }
}


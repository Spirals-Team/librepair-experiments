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
package spoon.support.visitor;


/**
 * Expects a {@link CtPackage} as input
 * and  upon calls to forEachSubTypeInPackage produces all sub classes and sub interfaces, which extends or implements super type(s) provided in constructor and stored as `targetSuperTypes`.<br>
 *
 * The repeated processing of this mapping function on the same input returns only newly found sub types.
 * The instance of {@link SubInheritanceHierarchyResolver} returns found sub types only once.
 * So repeated call with same input package returns nothing.
 * Create and use new instance of {@link SubInheritanceHierarchyResolver} if you need to scan the subtype hierarchy again.
 */
public class SubInheritanceHierarchyResolver {
    /**
     * where the subtypes will be looked for
     */
    private spoon.reflect.declaration.CtPackage inputPackage;

    /**
     * whether interfaces are included in the result
     */
    private boolean includingInterfaces = true;

    /**
     * Set of qualified names of all super types whose sub types we are searching for.
     * Each found sub type is added to this set too
     */
    private java.util.Set<java.lang.String> targetSuperTypes = new java.util.HashSet<>();

    /**
     * if true then we have to check if type is a subtype of superClass or superInterfaces too
     * if false then it is enough to search in superClass hierarchy only (faster)
     */
    private boolean hasSuperInterface = false;

    private boolean failOnClassNotFound = false;

    public SubInheritanceHierarchyResolver(spoon.reflect.declaration.CtPackage input) {
        inputPackage = input;
    }

    /**
     * Add another super type to this mapping function.
     * Using this function you can search parallel in more sub type hierarchies.
     *
     * @param superType
     * 		- the type whose sub types will be returned by this mapping function too.
     */
    public spoon.support.visitor.SubInheritanceHierarchyResolver addSuperType(spoon.reflect.declaration.CtTypeInformation superType) {
        targetSuperTypes.add(superType.getQualifiedName());
        if ((hasSuperInterface) == false) {
            hasSuperInterface = superType.isInterface();
        }
        return this;
    }

    /**
     *
     *
     * @param includingInterfaces
     * 		if false then interfaces are not visited - only super classes. By default it is true.
     */
    public spoon.support.visitor.SubInheritanceHierarchyResolver includingInterfaces(boolean includingInterfaces) {
        this.includingInterfaces = includingInterfaces;
        return this;
    }

    /**
     *
     *
     * @param failOnClassNotFound
     * 		sets whether processing should throw an exception if class is missing in noClassPath mode
     */
    public spoon.support.visitor.SubInheritanceHierarchyResolver failOnClassNotFound(boolean failOnClassNotFound) {
        this.failOnClassNotFound = failOnClassNotFound;
        return this;
    }

    /**
     * Calls `outputConsumer.apply(subType)` for each sub type of the targetSuperTypes that are found in `inputPackage`.
     * Each sub type is returned only once.
     * It makes sense to call this method again for example after new super types are added
     * by {@link #addSuperType(CtTypeInformation)}.
     *
     * 	If this method is called again with same input and configuration, nothing in sent to outputConsumer
     *
     * @param outputConsumer
     * 		the consumer for found sub types
     */
    public <T extends spoon.reflect.declaration.CtType<?>> void forEachSubTypeInPackage(final spoon.reflect.visitor.chain.CtConsumer<T> outputConsumer) {
        /* Set of qualified names of all visited types, independent on whether they are sub types or not. */
        final java.util.Set<java.lang.String> allVisitedTypeNames = new java.util.HashSet<>();
        /* the queue of types whose super inheritance hierarchy we are just visiting.
        They are potential sub types of an `targetSuperTypes`
         */
        final java.util.Deque<spoon.reflect.reference.CtTypeReference<?>> currentSubTypes = new java.util.ArrayDeque<>();
        // algorithm
        // 1) query step: scan input package for sub classes and sub interfaces
        final spoon.reflect.visitor.chain.CtQuery q = inputPackage.map(new spoon.reflect.visitor.filter.CtScannerFunction());
        // 2) query step: visit only required CtTypes
        if (includingInterfaces) {
            // the client is interested in sub inheritance hierarchy of interfaces too. Check interfaces, classes, enums, Annotations, but not CtTypeParameters.
            q.select(spoon.support.visitor.SubInheritanceHierarchyResolver.typeFilter);
        }else {
            // the client is not interested in sub inheritance hierarchy of interfaces. Check only classes and enums.
            q.select(spoon.support.visitor.SubInheritanceHierarchyResolver.classFilter);
        }
        /* 3) query step: for each found CtType, visit it's super inheritance hierarchy and search there for a type which is equal to one of targetSuperTypes.
        If found then all sub types in hierarchy (variable `currentSubTypes`) are sub types of targetSuperTypes. So return them
         */
        q.map(/* listen for types in super inheritance hierarchy
        1) to collect `currentSubTypes`
        2) to check if we have already found a targetSuperType
        3) if found then send `currentSubTypes` to `outputConsumer` and skip visiting of further super types
         */
        // if there is any interface between `targetSuperTypes`, then we have to check superInterfaces too
        new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingInterfaces(hasSuperInterface).failOnClassNotFound(failOnClassNotFound).setListener(new spoon.reflect.visitor.chain.CtScannerListener() {
            @java.lang.Override
            public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
                final spoon.reflect.reference.CtTypeReference<?> typeRef = ((spoon.reflect.reference.CtTypeReference<?>) (element));
                java.lang.String qName = typeRef.getQualifiedName();
                if (targetSuperTypes.contains(qName)) {
                    /* FOUND! we are in super inheritance hierarchy, which extends from an searched super type(s).
                    All `currentSubTypes` are sub types of searched super type
                     */
                    while ((currentSubTypes.size()) > 0) {
                        final spoon.reflect.reference.CtTypeReference<?> currentTypeRef = currentSubTypes.pop();
                        java.lang.String currentQName = currentTypeRef.getQualifiedName();
                        /* Send them to outputConsumer and add then as targetSuperTypes too, to perform faster with detection of next sub types. */
                        if (!(targetSuperTypes.contains(currentQName))) {
                            targetSuperTypes.add(currentQName);
                            outputConsumer.accept(((T) (currentTypeRef.getTypeDeclaration())));
                        }
                    } 
                    // we do not have to go deeper into super inheritance hierarchy. Skip visiting of further super types
                    // but continue visiting of siblings (do not terminate query)
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                if ((allVisitedTypeNames.add(qName)) == false) {
                    /* this type was already visited, by another way. So it is not sub type of `targetSuperTypes`.
                    Stop visiting it's inheritance hierarchy.
                     */
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                /* This type was not visited yet.
                We still do not know whether this type is a sub type of any target super type(s)
                continue searching in super inheritance hierarchy
                 */
                currentSubTypes.push(typeRef);
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement element) {
                spoon.reflect.declaration.CtTypeInformation type = ((spoon.reflect.declaration.CtTypeInformation) (element));
                if ((currentSubTypes.isEmpty()) == false) {
                    // remove current type, which is not a sub type of targetSuperTypes from the currentSubTypes
                    spoon.reflect.declaration.CtTypeInformation stackType = currentSubTypes.pop();
                    if (stackType != type) {
                        // the enter/exit was not called consistently. There is a bug in SuperInheritanceHierarchyFunction
                        throw new spoon.SpoonException("CtScannerListener#exit was not called after enter.");
                    }
                }
            }
        })).forEach(new spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtType<?>>() {
            @java.lang.Override
            public void accept(spoon.reflect.declaration.CtType<?> type) {
                // we do not care about types visited by query `q`.
                // the result of whole mapping function was already produced by `sendResult` call
                // but we have to consume all these results to let query running
            }
        });
    }

    /**
     * accept all {@link CtType} excluding {@link CtTypeParameter}
     */
    private static final spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> typeFilter = new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>>() {
        @java.lang.Override
        public boolean matches(spoon.reflect.declaration.CtType<?> type) {
            if (type instanceof spoon.reflect.declaration.CtTypeParameter) {
                return false;
            }
            return true;
        }
    };

    /**
     * Accept all {@link CtClass}, {@link CtEnum}
     */
    private static final spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtClass<?>> classFilter = new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtClass<?>>(spoon.reflect.declaration.CtClass.class);
}


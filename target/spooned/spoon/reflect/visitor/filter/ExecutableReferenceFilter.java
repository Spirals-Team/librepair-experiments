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
 * This filter matches all the {@link CtExecutableReference} referencing defined one or more {@link CtExecutable}s.
 */
public class ExecutableReferenceFilter implements spoon.reflect.visitor.Filter<spoon.reflect.reference.CtExecutableReference<?>> {
    private java.util.Map<spoon.reflect.declaration.CtExecutable<?>, spoon.reflect.declaration.CtExecutable<?>> executables = new java.util.IdentityHashMap<>();

    private java.util.Set<java.lang.String> typeQualifiedNames = new java.util.HashSet<>();

    private java.util.Set<java.lang.String> methodNames = new java.util.HashSet<>();

    /**
     * Creates a new executable reference filter.
     *
     * Call {@link #addExecutable(CtExecutable)} to define executables
     * whose references it matches.
     */
    public ExecutableReferenceFilter() {
    }

    /**
     * Creates a new executable reference filter.
     *
     * @param executable
     * 		the executable whose references it matches
     */
    public ExecutableReferenceFilter(spoon.reflect.declaration.CtExecutable<?> executable) {
        addExecutable(executable);
    }

    /**
     * Add next {@link CtExecutable} whose {@link CtExecutableReference}s has to be matched
     *
     * @param executable
     * 		searched {@link CtExecutable} instance
     * @return this to support fluent API
     */
    public spoon.reflect.visitor.filter.ExecutableReferenceFilter addExecutable(spoon.reflect.declaration.CtExecutable<?> executable) {
        executables.put(executable, executable);
        if (executable instanceof spoon.reflect.declaration.CtTypeMember) {
            spoon.reflect.declaration.CtType<?> declType = ((spoon.reflect.declaration.CtTypeMember) (executable)).getDeclaringType();
            if (declType == null) {
                throw new spoon.SpoonException("Cannot search for executable reference, which has no declaring type");
            }
            typeQualifiedNames.add(declType.getQualifiedName());
            if (executable instanceof spoon.reflect.declaration.CtMethod<?>) {
                methodNames.add(((spoon.reflect.declaration.CtMethod<?>) (executable)).getSimpleName());
            }
        }
        return this;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.reference.CtExecutableReference<?> execRef) {
        if (execRef.getSimpleName().startsWith(spoon.reflect.reference.CtExecutableReference.LAMBDA_NAME_PREFIX)) {
            // reference to lambda
            return executables.containsKey(execRef.getDeclaration());
        }else {
            // reference to constructor or method
            if (typeQualifiedNames.contains(execRef.getDeclaringType().getQualifiedName())) {
                if ((spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME.equals(execRef.getSimpleName())) || (methodNames.contains(execRef.getSimpleName()))) {
                    return executables.containsKey(execRef.getDeclaration());
                }
            }
        }
        return false;
    }
}


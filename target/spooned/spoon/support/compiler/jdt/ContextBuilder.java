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
package spoon.support.compiler.jdt;


public class ContextBuilder {
    java.util.Deque<java.lang.String> annotationValueName = new java.util.ArrayDeque<>();

    java.util.List<spoon.reflect.reference.CtTypeReference<?>> casts = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.CASTS_CONTAINER_DEFAULT_CAPACITY);

    org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration compilationunitdeclaration;

    spoon.reflect.cu.CompilationUnit compilationUnitSpoon;

    java.util.Deque<java.lang.String> label = new java.util.ArrayDeque<>();

    boolean isBuildLambda = false;

    boolean ignoreComputeImports = false;

    /**
     * Stack of all parents elements
     */
    java.util.Deque<spoon.support.compiler.jdt.ASTPair> stack = new java.util.ArrayDeque<>();

    private final spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder;

    ContextBuilder(spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder) {
        this.jdtTreeBuilder = jdtTreeBuilder;
    }

    @java.lang.SuppressWarnings("unchecked")
    void enter(spoon.reflect.declaration.CtElement e, org.eclipse.jdt.internal.compiler.ast.ASTNode node) {
        stack.push(new spoon.support.compiler.jdt.ASTPair(e, node));
        if ((!(e instanceof spoon.reflect.declaration.CtPackage)) || (((compilationUnitSpoon.getFile()) != null) && (compilationUnitSpoon.getFile().getName().equals(spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_PACKAGE_DECLARATION)))) {
            if (((compilationunitdeclaration) != null) && (!(e.isImplicit()))) {
                e.setPosition(this.jdtTreeBuilder.getPositionBuilder().buildPositionCtElement(e, node));
            }
        }
        spoon.support.compiler.jdt.ASTPair pair = stack.peek();
        spoon.reflect.declaration.CtElement current = pair.element;
        if (current instanceof spoon.reflect.code.CtExpression) {
            while (!(casts.isEmpty())) {
                ((spoon.reflect.code.CtExpression<?>) (current)).addTypeCast(casts.remove(0));
            } 
        }
        if ((current instanceof spoon.reflect.code.CtStatement) && (!(this.label.isEmpty()))) {
            ((spoon.reflect.code.CtStatement) (current)).setLabel(this.label.pop());
        }
        try {
            if ((((e instanceof spoon.reflect.declaration.CtTypedElement) && (!(e instanceof spoon.reflect.code.CtConstructorCall))) && (!(e instanceof spoon.reflect.code.CtCatchVariable))) && (node instanceof org.eclipse.jdt.internal.compiler.ast.Expression)) {
                if ((((spoon.reflect.declaration.CtTypedElement<?>) (e)).getType()) == null) {
                    ((spoon.reflect.declaration.CtTypedElement<java.lang.Object>) (e)).setType(this.jdtTreeBuilder.getReferencesBuilder().getTypeReference(((org.eclipse.jdt.internal.compiler.ast.Expression) (node)).resolvedType));
                }
            }
        } catch (java.lang.UnsupportedOperationException ignore) {
            // For some element, we throw an UnsupportedOperationException when we call setType().
        }
    }

    void exit(org.eclipse.jdt.internal.compiler.ast.ASTNode node) {
        spoon.support.compiler.jdt.ASTPair pair = stack.pop();
        if ((pair.node) != node) {
            throw new java.lang.RuntimeException(((("Inconsistent Stack " + node) + "\n") + (pair.node)));
        }
        spoon.reflect.declaration.CtElement current = pair.element;
        if (!(stack.isEmpty())) {
            this.jdtTreeBuilder.getExiter().setChild(current);
            this.jdtTreeBuilder.getExiter().setChild(pair.node);
            this.jdtTreeBuilder.getExiter().scan(stack.peek().element);
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    <T> spoon.reflect.code.CtLocalVariable<T> getLocalVariableDeclaration(final java.lang.String name) {
        final java.lang.Class<spoon.reflect.code.CtLocalVariable<T>> clazz = ((java.lang.Class<spoon.reflect.code.CtLocalVariable<T>>) (jdtTreeBuilder.getFactory().Core().createLocalVariable().getClass()));
        final spoon.reflect.code.CtLocalVariable<T> localVariable = this.<T, spoon.reflect.code.CtLocalVariable<T>>getVariableDeclaration(name, clazz);
        if (localVariable == null) {
            // note: this happens when using the new try(vardelc) structure
            this.jdtTreeBuilder.getLogger().error(java.lang.String.format("Could not find declaration for local variable %s at %s", name, stack.peek().element.getPosition()));
        }
        return localVariable;
    }

    @java.lang.SuppressWarnings("unchecked")
    <T> spoon.reflect.code.CtCatchVariable<T> getCatchVariableDeclaration(final java.lang.String name) {
        final java.lang.Class<spoon.reflect.code.CtCatchVariable<T>> clazz = ((java.lang.Class<spoon.reflect.code.CtCatchVariable<T>>) (jdtTreeBuilder.getFactory().Core().createCatchVariable().getClass()));
        final spoon.reflect.code.CtCatchVariable<T> catchVariable = this.<T, spoon.reflect.code.CtCatchVariable<T>>getVariableDeclaration(name, clazz);
        if (catchVariable == null) {
            // note: this happens when using the new try(vardelc) structure
            this.jdtTreeBuilder.getLogger().error(java.lang.String.format("Could not find declaration for catch variable %s at %s", name, stack.peek().element.getPosition()));
        }
        return catchVariable;
    }

    <T> spoon.reflect.declaration.CtVariable<T> getVariableDeclaration(final java.lang.String name) {
        final spoon.reflect.declaration.CtVariable<T> variable = this.<T, spoon.reflect.declaration.CtVariable<T>>getVariableDeclaration(name, null);
        if (variable == null) {
            // note: this happens when using the new try(vardelc) structure
            this.jdtTreeBuilder.getLogger().error(java.lang.String.format("Could not find declaration for variable %s at %s", name, stack.peek().element.getPosition()));
        }
        return variable;
    }

    @java.lang.SuppressWarnings("unchecked")
    private <T, U extends spoon.reflect.declaration.CtVariable<T>> U getVariableDeclaration(final java.lang.String name, final java.lang.Class<U> clazz) {
        final spoon.reflect.factory.CoreFactory coreFactory = jdtTreeBuilder.getFactory().Core();
        final spoon.reflect.factory.TypeFactory typeFactory = jdtTreeBuilder.getFactory().Type();
        final spoon.reflect.factory.ClassFactory classFactory = jdtTreeBuilder.getFactory().Class();
        final spoon.reflect.factory.InterfaceFactory interfaceFactory = jdtTreeBuilder.getFactory().Interface();
        final spoon.reflect.factory.FieldFactory fieldFactory = jdtTreeBuilder.getFactory().Field();
        final spoon.support.compiler.jdt.ReferenceBuilder referenceBuilder = jdtTreeBuilder.getReferencesBuilder();
        final spoon.compiler.Environment environment = jdtTreeBuilder.getFactory().getEnvironment();
        // there is some extra work to do if we are looking for CtFields (and subclasses)
        final boolean lookingForFields = (clazz == null) || (coreFactory.createField().getClass().isAssignableFrom(clazz));
        // try to find the variable on stack beginning with the most recent element
        for (final spoon.support.compiler.jdt.ASTPair astPair : stack) {
            // the variable may have been declared directly by one of these elements
            final spoon.support.compiler.jdt.ContextBuilder.ScopeRespectingVariableScanner<U> scanner = new spoon.support.compiler.jdt.ContextBuilder.ScopeRespectingVariableScanner(name, clazz);
            astPair.element.accept(scanner);
            if ((scanner.getResult()) != null) {
                return scanner.getResult();
            }
            // the variable may have been declared in a super class/interface
            if (lookingForFields && ((astPair.node) instanceof org.eclipse.jdt.internal.compiler.ast.TypeDeclaration)) {
                final org.eclipse.jdt.internal.compiler.ast.TypeDeclaration nodeDeclaration = ((org.eclipse.jdt.internal.compiler.ast.TypeDeclaration) (astPair.node));
                final java.util.Deque<org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding> referenceBindings = new java.util.ArrayDeque<>();
                // add super class if any
                if (((nodeDeclaration.superclass) != null) && ((nodeDeclaration.superclass.resolvedType) instanceof org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding)) {
                    referenceBindings.push(((org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding) (nodeDeclaration.superclass.resolvedType)));
                }
                // add interfaces if any
                if ((nodeDeclaration.superInterfaces) != null) {
                    for (final org.eclipse.jdt.internal.compiler.ast.TypeReference tr : nodeDeclaration.superInterfaces) {
                        if ((tr.resolvedType) instanceof org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding) {
                            referenceBindings.push(((org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding) (tr.resolvedType)));
                        }
                    }
                }
                while (!(referenceBindings.isEmpty())) {
                    final org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding referenceBinding = referenceBindings.pop();
                    for (final org.eclipse.jdt.internal.compiler.lookup.FieldBinding fieldBinding : referenceBinding.fields()) {
                        if (name.equals(new java.lang.String(fieldBinding.readableName()))) {
                            final java.lang.String qualifiedNameOfParent = new java.lang.String(referenceBinding.readableName());
                            final spoon.reflect.declaration.CtType parentOfField = (referenceBinding.isClass()) ? classFactory.create(qualifiedNameOfParent) : interfaceFactory.create(qualifiedNameOfParent);
                            U field = ((U) (fieldFactory.create(parentOfField, java.util.EnumSet.noneOf(spoon.reflect.declaration.ModifierKind.class), referenceBuilder.getTypeReference(fieldBinding.type), name)));
                            return field.setExtendedModifiers(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(fieldBinding.modifiers, true, false));
                        }
                    }
                    // add super class if any
                    final org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding superclass = referenceBinding.superclass();
                    if (superclass != null) {
                        referenceBindings.push(superclass);
                    }
                    // add interfaces if any
                    final org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding[] interfaces = referenceBinding.superInterfaces();
                    if (interfaces != null) {
                        for (org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding rb : interfaces) {
                            referenceBindings.push(rb);
                        }
                    }
                } 
            }
        }
        // the variable may have been imported statically from another class/interface
        if (lookingForFields) {
            final spoon.reflect.reference.CtReference potentialReferenceToField = referenceBuilder.getDeclaringReferenceFromImports(name.toCharArray());
            if ((potentialReferenceToField != null) && (potentialReferenceToField instanceof spoon.reflect.reference.CtTypeReference)) {
                final spoon.reflect.reference.CtTypeReference typeReference = ((spoon.reflect.reference.CtTypeReference) (potentialReferenceToField));
                try {
                    final java.lang.Class classOfType = typeReference.getActualClass();
                    if (classOfType != null) {
                        final spoon.reflect.declaration.CtType declaringTypeOfField = (typeReference.isInterface()) ? interfaceFactory.get(classOfType) : classFactory.get(classOfType);
                        final spoon.reflect.declaration.CtField field = declaringTypeOfField.getField(name);
                        if (field != null) {
                            return ((U) (field));
                        }
                    }
                } catch (final spoon.support.SpoonClassNotFoundException scnfe) {
                    // in noclasspath mode we do some heuristics to determine if `name` could be a
                    // field that has been imported statically from another class (or interface).
                    if (environment.getNoClasspath()) {
                        // if `potentialReferenceToField` is a `CtTypeReference` then `name` must
                        // have been imported statically. Otherwise, `potentialReferenceToField`
                        // would be a CtPackageReference!
                        // if `name` consists only of upper case characters separated by '_', we
                        // assume a constant value according to JLS.
                        if (name.toUpperCase().equals(name)) {
                            final spoon.reflect.declaration.CtType parentOfField = classFactory.create(typeReference.getQualifiedName());
                            // it is the best thing we can do
                            final spoon.reflect.declaration.CtField field = coreFactory.createField();
                            field.setParent(parentOfField);
                            field.setSimpleName(name);
                            // it is the best thing we can do
                            field.setType(typeFactory.nullType());
                            return ((U) (field));
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * An {@link EarlyTerminatingScanner} that is supposed to find a {@link CtVariable} with
     * specific name respecting the current scope given by {@link ContextBuilder#stack}.
     *
     * @param <T>
     * 		The actual type of the {@link CtVariable} we are looking for. Examples include
     * 		{@link CtLocalVariable}, {@link CtField}, and so on.
     */
    private class ScopeRespectingVariableScanner<T extends spoon.reflect.declaration.CtVariable> extends spoon.reflect.visitor.EarlyTerminatingScanner<T> {
        /**
         * The class object of {@link T} that is required to filter particular elements in
         * {@link #scan(CtElement)}.
         */
        private final java.lang.Class<T> clazz;

        /**
         * The name of the variable we are looking for ({@link CtVariable#getSimpleName()}).
         */
        final java.lang.String name;

        /**
         * Creates a new {@link EarlyTerminatingScanner} that tries to find a {@link CtVariable}
         * with name {@code pName} (using {@link CtVariable#getSimpleName()}) and upper type bound
         * {@code pType}.
         *
         * @param pName	The
         * 		name of the variable we are looking for.
         * @param pType	{@link
         * 		T}'s class object ({@link Object#getClass()}). {@link null} values
         * 		are permitted and indicate that we are looking for any subclass of
         * 		{@link CtVariable} (including {@link CtVariable} itself).
         */
        ScopeRespectingVariableScanner(final java.lang.String pName, final java.lang.Class<T> pType) {
            clazz = ((java.lang.Class<T>) ((pType == null) ? spoon.reflect.declaration.CtVariable.class : pType));
            name = pName;
        }

        @java.lang.Override
        public void scan(final spoon.reflect.declaration.CtElement element) {
            if ((element != null) && (clazz.isAssignableFrom(element.getClass()))) {
                final T potentialVariable = ((T) (element));
                if (name.equals(potentialVariable.getSimpleName())) {
                    // Since the AST is not completely available yet, we can not check if element's
                    // parent (ep) contains the innermost element of `stack` (ie). Therefore, we
                    // have to check if one of the following condition holds:
                    // 
                    // 1) Does `stack` contain `ep`?
                    // 2) Is `ep` the body of one of `stack`'s CtExecutable elements?
                    // 
                    // The first condition is easy to see. If `stack` contains `ep` then `ep` and
                    // all it's declared variables are in scope of `ie`. Unfortunately, there is a
                    // special case in which a variable (a CtLocalVariable) has been declared in a
                    // block (CtBlock) of, for instance, a method. Such a block is not contained in
                    // `stack`. This peculiarity calls for the second condition.
                    final spoon.reflect.declaration.CtElement parentOfPotentialVariable = potentialVariable.getParent();
                    for (final spoon.support.compiler.jdt.ASTPair astPair : stack) {
                        if ((astPair.element) == parentOfPotentialVariable) {
                            finish(potentialVariable);
                            return;
                        }else
                            if ((astPair.element) instanceof spoon.reflect.declaration.CtExecutable) {
                                final spoon.reflect.declaration.CtExecutable executable = ((spoon.reflect.declaration.CtExecutable) (astPair.element));
                                if ((executable.getBody()) == parentOfPotentialVariable) {
                                    finish(potentialVariable);
                                    return;
                                }
                            }

                    }
                }
            }
            super.scan(element);
        }

        private void finish(final T element) {
            setResult(element);
            terminate();
        }
    }
}


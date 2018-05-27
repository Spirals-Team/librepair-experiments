package spoon.support.compiler.jdt;


public class ContextBuilder {
    java.util.Deque<java.lang.String> annotationValueName = new java.util.ArrayDeque<>();

    java.util.List<spoon.reflect.reference.CtTypeReference<?>> casts = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.CASTS_CONTAINER_DEFAULT_CAPACITY);

    org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration compilationunitdeclaration;

    spoon.reflect.cu.CompilationUnit compilationUnitSpoon;

    java.util.Deque<java.lang.String> label = new java.util.ArrayDeque<>();

    boolean isBuildLambda = false;

    boolean ignoreComputeImports = false;

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
            this.jdtTreeBuilder.getLogger().error(java.lang.String.format("Could not find declaration for local variable %s at %s", name, stack.peek().element.getPosition()));
        }
        return localVariable;
    }

    @java.lang.SuppressWarnings("unchecked")
    <T> spoon.reflect.code.CtCatchVariable<T> getCatchVariableDeclaration(final java.lang.String name) {
        final java.lang.Class<spoon.reflect.code.CtCatchVariable<T>> clazz = ((java.lang.Class<spoon.reflect.code.CtCatchVariable<T>>) (jdtTreeBuilder.getFactory().Core().createCatchVariable().getClass()));
        final spoon.reflect.code.CtCatchVariable<T> catchVariable = this.<T, spoon.reflect.code.CtCatchVariable<T>>getVariableDeclaration(name, clazz);
        if (catchVariable == null) {
            this.jdtTreeBuilder.getLogger().error(java.lang.String.format("Could not find declaration for catch variable %s at %s", name, stack.peek().element.getPosition()));
        }
        return catchVariable;
    }

    <T> spoon.reflect.declaration.CtVariable<T> getVariableDeclaration(final java.lang.String name) {
        final spoon.reflect.declaration.CtVariable<T> variable = this.<T, spoon.reflect.declaration.CtVariable<T>>getVariableDeclaration(name, null);
        if (variable == null) {
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
        final boolean lookingForFields = (clazz == null) || (coreFactory.createField().getClass().isAssignableFrom(clazz));
        for (final spoon.support.compiler.jdt.ASTPair astPair : stack) {
            final spoon.support.compiler.jdt.ContextBuilder.ScopeRespectingVariableScanner<U> scanner = new spoon.support.compiler.jdt.ContextBuilder.ScopeRespectingVariableScanner(name, clazz);
            astPair.element.accept(scanner);
            if ((scanner.getResult()) != null) {
                return scanner.getResult();
            }
            if (lookingForFields && ((astPair.node) instanceof org.eclipse.jdt.internal.compiler.ast.TypeDeclaration)) {
                final org.eclipse.jdt.internal.compiler.ast.TypeDeclaration nodeDeclaration = ((org.eclipse.jdt.internal.compiler.ast.TypeDeclaration) (astPair.node));
                final java.util.Deque<org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding> referenceBindings = new java.util.ArrayDeque<>();
                if (((nodeDeclaration.superclass) != null) && ((nodeDeclaration.superclass.resolvedType) instanceof org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding)) {
                    referenceBindings.push(((org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding) (nodeDeclaration.superclass.resolvedType)));
                }
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
                    final org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding superclass = referenceBinding.superclass();
                    if (superclass != null) {
                        referenceBindings.push(superclass);
                    }
                    final org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding[] interfaces = referenceBinding.superInterfaces();
                    if (interfaces != null) {
                        for (org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding rb : interfaces) {
                            referenceBindings.push(rb);
                        }
                    }
                } 
            }
        }
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
                    if (environment.getNoClasspath()) {
                        if (name.toUpperCase().equals(name)) {
                            final spoon.reflect.declaration.CtType parentOfField = classFactory.create(typeReference.getQualifiedName());
                            final spoon.reflect.declaration.CtField field = coreFactory.createField();
                            field.setParent(parentOfField);
                            field.setSimpleName(name);
                            field.setType(typeFactory.nullType());
                            return ((U) (field));
                        }
                    }
                }
            }
        }
        return null;
    }

    private class ScopeRespectingVariableScanner<T extends spoon.reflect.declaration.CtVariable> extends spoon.reflect.visitor.EarlyTerminatingScanner<T> {
        private final java.lang.Class<T> clazz;

        final java.lang.String name;

        ScopeRespectingVariableScanner(final java.lang.String pName, final java.lang.Class<T> pType) {
            clazz = ((java.lang.Class<T>) ((pType == null) ? spoon.reflect.declaration.CtVariable.class : pType));
            name = pName;
        }

        @java.lang.Override
        public void scan(final spoon.reflect.declaration.CtElement element) {
            if ((element != null) && (clazz.isAssignableFrom(element.getClass()))) {
                final T potentialVariable = ((T) (element));
                if (name.equals(potentialVariable.getSimpleName())) {
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


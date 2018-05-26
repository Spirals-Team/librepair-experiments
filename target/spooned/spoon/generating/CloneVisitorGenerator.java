package spoon.generating;


public class CloneVisitorGenerator extends spoon.processing.AbstractManualProcessor {
    private static final java.lang.String TARGET_CLONE_PACKAGE = "spoon.support.visitor.clone";

    private static final java.lang.String TARGET_CLONE_TYPE = "CloneVisitor";

    private static final java.lang.String TARGET_BUILDER_CLONE_TYPE = "CloneBuilder";

    private static final java.lang.String GENERATING_CLONE_PACKAGE = "spoon.generating.clone";

    private static final java.lang.String GENERATING_CLONE = (spoon.generating.CloneVisitorGenerator.GENERATING_CLONE_PACKAGE) + ".CloneVisitorTemplate";

    private static final java.lang.String GENERATING_BUILDER_CLONE = (spoon.generating.CloneVisitorGenerator.GENERATING_CLONE_PACKAGE) + ".CloneBuilderTemplate";

    @java.lang.Override
    public void process() {
        final spoon.reflect.declaration.CtClass<java.lang.Object> target = createCloneVisitor();
        final spoon.reflect.declaration.CtClass<java.lang.Object> targetBuilder = createCloneBuilder();
        final spoon.reflect.factory.Factory factory = target.getFactory();
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> cloneBuilder = factory.Type().createReference("spoon.support.visitor.clone.CloneBuilder");
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> cloneBuilderType = factory.Code().createTypeAccess(cloneBuilder);
        final spoon.reflect.code.CtVariableAccess<java.lang.Object> builderFieldAccess = factory.Code().createVariableRead(factory.Field().createReference(target.getReference(), cloneBuilder, "builder"), false);
        final spoon.reflect.code.CtVariableAccess<java.lang.Object> tailorerFieldAccess = factory.Code().createVariableRead(factory.Field().createReference(target.getReference(), cloneBuilder, "tailorer"), false);
        final spoon.reflect.code.CtVariableAccess<java.lang.Object> cloneHelperFieldAccess = factory.Code().createVariableRead(factory.Field().createReference(target.getReference(), cloneBuilder, "cloneHelper"), false);
        final spoon.reflect.reference.CtFieldReference<java.lang.Object> other = factory.Field().createReference(((spoon.reflect.declaration.CtField) (target.getField("other"))));
        final spoon.reflect.code.CtVariableAccess otherRead = factory.Code().createVariableRead(other, true);
        new spoon.reflect.visitor.CtScanner() {
            private final java.util.List<java.lang.String> internals = java.util.Collections.singletonList("CtCircularTypeReference");

            @java.lang.Override
            public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> element) {
                if (!(element.getSimpleName().startsWith("visitCt"))) {
                    return;
                }
                spoon.reflect.declaration.CtMethod<T> clone = element.clone();
                final spoon.reflect.declaration.CtParameter<spoon.reflect.declaration.CtElement> ctParameter = ((spoon.reflect.declaration.CtParameter<spoon.reflect.declaration.CtElement>) (element.getParameters().get(0)));
                final spoon.reflect.code.CtVariableAccess<spoon.reflect.declaration.CtElement> elementVarRead = factory.Code().createVariableRead(ctParameter.getReference(), false);
                final spoon.reflect.code.CtInvocation cloneBuilderInvocation = createCloneBuilderBuildInvocation(elementVarRead);
                final spoon.reflect.code.CtLocalVariable localCloningElement = createLocalCloningElement(ctParameter.getType(), createFactoryInvocation(elementVarRead));
                final spoon.reflect.code.CtVariableAccess localVarRead = factory.Code().createVariableRead(localCloningElement.getReference(), false);
                for (int i = 1; i < ((clone.getBody().getStatements().size()) - 1); i++) {
                    java.util.List<spoon.reflect.code.CtExpression> invArgs = ((spoon.reflect.code.CtInvocation) (clone.getBody().getStatement(i))).getArguments();
                    if ((invArgs.size()) <= 1) {
                        throw new java.lang.RuntimeException(((((("You forget the role argument in line " + i) + " of method ") + (element.getSimpleName())) + " from ") + (element.getDeclaringType().getQualifiedName())));
                    }
                    final spoon.reflect.code.CtInvocation targetInvocation = ((spoon.reflect.code.CtInvocation) (invArgs.get(1)));
                    if (("getValue".equals(targetInvocation.getExecutable().getSimpleName())) && ("CtLiteral".equals(targetInvocation.getExecutable().getDeclaringType().getSimpleName()))) {
                        clone.getBody().getStatement((i--)).delete();
                        continue;
                    }
                    clone.getBody().getStatement(i).replace(createSetter(((spoon.reflect.code.CtInvocation) (clone.getBody().getStatement(i))), localVarRead));
                }
                clone.getBody().getStatement(0).delete();
                clone.getBody().getStatement(((clone.getBody().getStatements().size()) - 1)).delete();
                clone.getBody().insertBegin(localCloningElement);
                clone.getBody().insertEnd(createCloneBuilderCopyInvocation(elementVarRead, localVarRead));
                clone.getBody().insertEnd(createTailorerScanInvocation(elementVarRead, localVarRead));
                clone.getBody().insertEnd(factory.Code().createVariableAssignment(other, false, localVarRead));
                final spoon.reflect.code.CtComment comment = factory.Core().createComment();
                comment.setCommentType(spoon.reflect.code.CtComment.CommentType.INLINE);
                comment.setContent(("auto-generated, see " + (spoon.generating.CloneVisitorGenerator.class.getName())));
                clone.addComment(comment);
                target.addMethod(clone);
            }

            private spoon.reflect.code.CtInvocation<?> createSetter(spoon.reflect.code.CtInvocation scanInvocation, spoon.reflect.code.CtVariableAccess<spoon.reflect.declaration.CtElement> elementVarRead) {
                final spoon.reflect.code.CtInvocation<?> getter = ((spoon.reflect.code.CtInvocation<?>) (scanInvocation.getArguments().get(1)));
                final java.lang.String getterName = getter.getExecutable().getSimpleName();
                final spoon.reflect.reference.CtExecutableReference<java.lang.Object> setterRef = factory.Executable().createReference((("void CtElement#set" + (getterName.substring(3, getterName.length()))) + "()"));
                final spoon.reflect.reference.CtExecutableReference<java.lang.Object> cloneRef = factory.Executable().createReference("CtElement spoon.support.visitor.equals.CloneHelper#clone()");
                final spoon.reflect.code.CtInvocation<java.lang.Object> cloneInv = factory.Code().createInvocation(null, cloneRef, getter);
                cloneInv.setTarget(cloneHelperFieldAccess);
                return factory.Code().createInvocation(elementVarRead, setterRef, cloneInv);
            }

            private <T> spoon.reflect.code.CtLocalVariable<T> createLocalCloningElement(spoon.reflect.reference.CtTypeReference<T> typeReference, spoon.reflect.code.CtInvocation<T> ctInvocation) {
                return factory.Code().createLocalVariable(typeReference, ("a" + (typeReference.getSimpleName())), ctInvocation);
            }

            private spoon.reflect.code.CtInvocation<spoon.support.visitor.clone.CloneBuilder> createCloneBuilderBuildInvocation(spoon.reflect.code.CtVariableAccess<spoon.reflect.declaration.CtElement> elementAccess) {
                final spoon.reflect.reference.CtExecutableReference<spoon.support.visitor.clone.CloneBuilder> buildExecRef = factory.Executable().createReference("CloneBuilder CtElement#build()");
                return factory.Code().createInvocation(cloneBuilderType, buildExecRef, builderFieldAccess, elementAccess, createFactoryInvocation(elementAccess.clone()));
            }

            private spoon.reflect.code.CtInvocation<spoon.support.visitor.clone.CloneBuilder> createCloneBuilderCopyInvocation(spoon.reflect.code.CtVariableAccess<spoon.reflect.declaration.CtElement> elementVarRead, spoon.reflect.code.CtVariableAccess<spoon.reflect.declaration.CtElement> elementVarRead2) {
                final spoon.reflect.reference.CtExecutableReference<spoon.support.visitor.clone.CloneBuilder> buildExecRef = factory.Executable().createReference("CloneBuilder #copy()");
                return factory.Code().createInvocation(builderFieldAccess, buildExecRef, elementVarRead, elementVarRead2);
            }

            private spoon.reflect.code.CtInvocation<spoon.support.visitor.clone.CloneBuilder> createTailorerScanInvocation(spoon.reflect.code.CtVariableAccess elementVarRead, spoon.reflect.code.CtVariableAccess localVarRead) {
                final spoon.reflect.reference.CtExecutableReference<spoon.support.visitor.clone.CloneBuilder> buildExecRef = factory.Executable().createReference("CloneHelper #tailor()");
                return factory.Code().createInvocation(cloneHelperFieldAccess, buildExecRef, elementVarRead, localVarRead);
            }

            private spoon.reflect.code.CtInvocation createFactoryInvocation(spoon.reflect.code.CtVariableAccess<spoon.reflect.declaration.CtElement> elementAccess) {
                final java.lang.String typeName = elementAccess.getType().getSimpleName();
                final spoon.reflect.code.CtInvocation<java.lang.Object> getFactory = factory.Code().createInvocation(null, factory.Executable().createReference("Factory CtElement#getFactory()"));
                getFactory.setTarget(elementAccess);
                final java.lang.String factoryName = (internals.contains(typeName)) ? "Internal" : "Core";
                final spoon.reflect.code.CtInvocation<java.lang.Object> coreFactory = factory.Code().createInvocation(getFactory, factory.Executable().createReference((("CoreFactory Factory#" + factoryName) + "()")));
                return factory.Code().createInvocation(coreFactory, factory.Executable().createReference((("CoreFactory CtElement#create" + (typeName.substring(2, typeName.length()))) + "()")));
            }
        }.scan(getFactory().Class().get(spoon.reflect.visitor.CtScanner.class));
        new spoon.reflect.visitor.CtScanner() {
            private final java.util.List<java.lang.String> excludesAST = java.util.Arrays.asList("spoon.support.reflect.declaration.CtTypeInformationImpl", "spoon.support.reflect.code.CtAbstractInvocationImpl", "spoon.support.reflect.declaration.CtTypedElementImpl", "spoon.support.reflect.declaration.CtVariableImpl", "spoon.support.reflect.reference.CtActualTypeContainerImpl", "spoon.support.reflect.code.CtCFlowBreakImpl", "spoon.support.reflect.code.CtLabelledFlowBreakImpl", "spoon.support.reflect.declaration.CtCodeSnippetImpl", "spoon.support.reflect.declaration.CtFormalTypeDeclarerImpl", "spoon.support.reflect.declaration.CtGenericElementImpl", "spoon.support.reflect.reference.CtGenericElementReferenceImpl", "spoon.support.reflect.declaration.CtModifiableImpl", "spoon.support.reflect.declaration.CtMultiTypedElementImpl", "spoon.support.reflect.declaration.CtTypeMemberImpl", "spoon.support.reflect.code.CtRHSReceiverImpl", "spoon.support.reflect.declaration.CtShadowableImpl", "spoon.support.reflect.code.CtBodyHolderImpl", "spoon.support.reflect.declaration.CtModuleDirectiveImpl");

            private final java.util.List<java.lang.String> excludesFields = java.util.Arrays.asList("factory", "elementValues", "target", "metadata");

            private final spoon.reflect.reference.CtTypeReference<java.util.List> LIST_REFERENCE = factory.Type().createReference(java.util.List.class);

            private final spoon.reflect.reference.CtTypeReference<java.util.Collection> COLLECTION_REFERENCE = factory.Type().createReference(java.util.Collection.class);

            private final spoon.reflect.reference.CtTypeReference<java.util.Set> SET_REFERENCE = factory.Type().createReference(java.util.Set.class);

            private final spoon.reflect.reference.CtTypeReference<spoon.reflect.declaration.CtElement> CTELEMENT_REFERENCE = factory.Type().createReference(spoon.reflect.declaration.CtElement.class);

            private final spoon.reflect.declaration.CtClass<?> GETTER_TEMPLATE_MATCHER_CLASS = factory.Class().get(((spoon.generating.CloneVisitorGenerator.GENERATING_CLONE_PACKAGE) + ".GetterTemplateMatcher"));

            private final spoon.reflect.declaration.CtClass<?> SETTER_TEMPLATE_MATCHER_CLASS = factory.Class().get(((spoon.generating.CloneVisitorGenerator.GENERATING_CLONE_PACKAGE) + ".SetterTemplateMatcher"));

            @java.lang.Override
            public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> element) {
                if ((!(element.getSimpleName().startsWith("visitCt"))) && (!(element.getSimpleName().startsWith("scanCt")))) {
                    return;
                }
                if ("scanCtVisitable".equals(element.getSimpleName())) {
                    return;
                }
                final java.lang.String qualifiedNameOfImplClass = ("spoon.support" + (element.getParameters().get(0).getType().getQualifiedName().substring(5))) + "Impl";
                if (excludesAST.contains(qualifiedNameOfImplClass)) {
                    return;
                }
                final spoon.reflect.declaration.CtType<?> declaration = factory.Class().get(qualifiedNameOfImplClass);
                if (declaration == null) {
                    throw new spoon.SpoonException(((qualifiedNameOfImplClass + " doesn't have declaration in the source path for ") + (element.getSignature())));
                }
                spoon.reflect.declaration.CtMethod<T> clone = element.clone();
                clone.getBody().getStatements().clear();
                for (spoon.reflect.declaration.CtField<?> ctField : declaration.getFields()) {
                    if (excludesFields.contains(ctField.getSimpleName())) {
                        continue;
                    }
                    if (isConstantOrStatic(ctField)) {
                        continue;
                    }
                    if (isSubTypeOfCtElement(ctField.getType())) {
                        continue;
                    }
                    final spoon.reflect.declaration.CtMethod<?> setterOfField = getSetterOf(ctField);
                    final spoon.reflect.code.CtInvocation<?> setterInvocation = createSetterInvocation(element.getParameters().get(0).getType(), setterOfField, createGetterInvocation(element.getParameters().get(0), getGetterOf(ctField)));
                    final java.util.List<spoon.reflect.declaration.CtMethod<?>> methodsToAvoid = getCtMethodThrowUnsupportedOperation(setterOfField);
                    if ((methodsToAvoid.size()) > 0) {
                        clone.getBody().addStatement(createProtectionToException(setterInvocation, methodsToAvoid));
                    }else {
                        clone.getBody().addStatement(setterInvocation);
                    }
                }
                if ((clone.getBody().getStatements().size()) > 0) {
                    clone.getBody().insertEnd(createSuperInvocation(element));
                    final spoon.reflect.code.CtComment comment = factory.Core().createComment();
                    comment.setCommentType(spoon.reflect.code.CtComment.CommentType.INLINE);
                    comment.setContent(("auto-generated, see " + (spoon.generating.CloneVisitorGenerator.class.getName())));
                    clone.addComment(comment);
                    targetBuilder.addMethod(clone);
                }
            }

            private spoon.reflect.code.CtIf createProtectionToException(spoon.reflect.code.CtInvocation<?> setterInvocation, java.util.List<spoon.reflect.declaration.CtMethod<?>> methodsAvoid) {
                final spoon.reflect.code.CtIf anIf = factory.Core().createIf();
                anIf.setCondition(factory.Core().createUnaryOperator().setOperand(createBinaryConditions(methodsAvoid)).setKind(spoon.reflect.code.UnaryOperatorKind.NOT));
                anIf.setThenStatement(factory.Code().createCtBlock(setterInvocation));
                return anIf;
            }

            private spoon.reflect.code.CtExpression<java.lang.Object> createBinaryConditions(java.util.List<spoon.reflect.declaration.CtMethod<?>> methodsAvoid) {
                spoon.reflect.code.CtExpression<java.lang.Object> left = null;
                spoon.reflect.code.CtExpression<java.lang.Object> right;
                for (int i = 0; i < (methodsAvoid.size()); i++) {
                    final spoon.reflect.declaration.CtInterface<?> ctInterface = getInterfaceOf(methodsAvoid.get(i).getDeclaringType());
                    if (i == 0) {
                        left = factory.Code().createBinaryOperator(otherRead, factory.Code().createTypeAccess(ctInterface.getReference()), spoon.reflect.code.BinaryOperatorKind.INSTANCEOF);
                    }else {
                        right = factory.Code().createBinaryOperator(otherRead, factory.Code().createTypeAccess(ctInterface.getReference()), spoon.reflect.code.BinaryOperatorKind.INSTANCEOF);
                        left = factory.Code().createBinaryOperator(left, right, spoon.reflect.code.BinaryOperatorKind.OR);
                    }
                }
                return left;
            }

            private java.util.List<spoon.reflect.declaration.CtMethod<?>> getCtMethodThrowUnsupportedOperation(spoon.reflect.declaration.CtMethod<?> method) {
                final java.util.List<spoon.reflect.declaration.CtMethod<?>> avoid = new java.util.ArrayList<>();
                final spoon.reflect.declaration.CtInterface<?> ctInterface = getInterfaceOf(method.getDeclaringType());
                if (ctInterface == null) {
                    return avoid;
                }
                final spoon.reflect.declaration.CtMethod<?> declarationMethod = getMethodByCtMethod(ctInterface, method);
                for (spoon.reflect.declaration.CtMethod<?> ctMethod : spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.OverridingMethodFilter(declarationMethod))) {
                    if (!(avoidThrowUnsupportedOperationException(ctMethod))) {
                        avoid.add(ctMethod);
                    }
                }
                return avoid;
            }

            private boolean avoidThrowUnsupportedOperationException(spoon.reflect.declaration.CtMethod<?> candidate) {
                if ((candidate.getBody().getStatements().size()) != 1) {
                    return true;
                }
                if (!((candidate.getBody().getStatement(0)) instanceof spoon.reflect.code.CtThrow)) {
                    return true;
                }
                spoon.reflect.code.CtThrow ctThrow = candidate.getBody().getStatement(0);
                if (!((ctThrow.getThrownExpression()) instanceof spoon.reflect.code.CtConstructorCall)) {
                    return true;
                }
                final spoon.reflect.code.CtConstructorCall<? extends java.lang.Throwable> thrownExpression = ((spoon.reflect.code.CtConstructorCall<? extends java.lang.Throwable>) (ctThrow.getThrownExpression()));
                if (!(thrownExpression.getType().equals(factory.Type().createReference(java.lang.UnsupportedOperationException.class)))) {
                    return true;
                }
                return false;
            }

            private spoon.reflect.declaration.CtMethod<?> getMethodByCtMethod(spoon.reflect.declaration.CtType<?> ctType, spoon.reflect.declaration.CtMethod<?> method) {
                for (spoon.reflect.declaration.CtMethod<?> ctMethod : ctType.getAllMethods()) {
                    if (!(method.getSimpleName().equals(ctMethod.getSimpleName()))) {
                        continue;
                    }
                    boolean cont = (method.getParameters().size()) == (ctMethod.getParameters().size());
                    for (int i = 0; cont && (i < (method.getParameters().size())); i++) {
                        if (!(method.getParameters().get(i).getType().equals(ctMethod.getParameters().get(i).getType()))) {
                            cont = false;
                        }
                    }
                    if (cont) {
                        return ctMethod;
                    }
                }
                throw new java.lang.AssertionError(((("Can't find method " + (method.getSignature())) + " in the given interface ") + (ctType.getQualifiedName())));
            }

            private spoon.reflect.declaration.CtInterface<?> getInterfaceOf(spoon.reflect.declaration.CtType<?> declaringType) {
                final spoon.reflect.reference.CtTypeReference<?>[] interfaces = declaringType.getSuperInterfaces().toArray(new spoon.reflect.reference.CtTypeReference[declaringType.getSuperInterfaces().size()]);
                for (spoon.reflect.reference.CtTypeReference<?> anInterface : interfaces) {
                    if (anInterface.getSimpleName().equals(declaringType.getSimpleName().substring(0, ((declaringType.getSimpleName().length()) - 4)))) {
                        return ((spoon.reflect.declaration.CtInterface<?>) (anInterface.getDeclaration()));
                    }
                }
                throw new java.lang.AssertionError(("You should have the interface for the implementation " + (declaringType.getQualifiedName())));
            }

            private <T> spoon.reflect.code.CtInvocation<T> createSuperInvocation(spoon.reflect.declaration.CtMethod<T> element) {
                return factory.Code().createInvocation(factory.Core().createSuperAccess(), element.getReference(), factory.Code().createVariableRead(element.getParameters().get(0).getReference(), false));
            }

            private spoon.reflect.code.CtInvocation<?> createSetterInvocation(spoon.reflect.reference.CtTypeReference<?> type, spoon.reflect.declaration.CtMethod<?> setter, spoon.reflect.code.CtInvocation<?> getter) {
                return factory.Code().createInvocation(otherRead.clone().addTypeCast(type), setter.getReference(), getter);
            }

            private spoon.reflect.code.CtInvocation<?> createGetterInvocation(spoon.reflect.declaration.CtParameter<?> element, spoon.reflect.declaration.CtMethod<?> getter) {
                return factory.Code().createInvocation(factory.Code().createVariableRead(element.getReference(), false), getter.getReference());
            }

            private <T> spoon.reflect.declaration.CtMethod<?> getSetterOf(final spoon.reflect.declaration.CtField<T> ctField) {
                if (ctField.getType().equals(getFactory().createCtTypeReference(spoon.support.reflect.CtModifierHandler.class))) {
                    return ctField.getDeclaringType().getMethodsByName("setModifiers").get(0);
                }
                for (spoon.reflect.declaration.CtMethod<?> ctMethod : ctField.getDeclaringType().getMethods()) {
                    if ((ctMethod.getSimpleName().startsWith("set")) && (ctMethod.getSimpleName().toLowerCase().contains(ctField.getSimpleName().toLowerCase()))) {
                        if ((ctMethod.getParameters().size()) != 1) {
                            continue;
                        }
                        if (!(ctMethod.getParameters().get(0).getType().equals(ctField.getType()))) {
                            continue;
                        }
                        return ctMethod;
                    }
                }
                SETTER_TEMPLATE_MATCHER_CLASS.getMethod("setElement", factory.Type().BOOLEAN_PRIMITIVE).getBody();
                final java.util.List<spoon.reflect.declaration.CtMethod> matchers = ctField.getDeclaringType().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtMethod>(spoon.reflect.declaration.CtMethod.class) {
                    @java.lang.Override
                    public boolean matches(spoon.reflect.declaration.CtMethod element) {
                        final spoon.reflect.code.CtBlock body = element.getBody();
                        if ((body.getStatements().size()) != 3) {
                            return false;
                        }
                        if ((body.getStatement(1)) instanceof spoon.reflect.code.CtAssignment) {
                            final spoon.reflect.code.CtExpression assigned = ((spoon.reflect.code.CtAssignment) (body.getStatement(1))).getAssigned();
                            if (!(assigned instanceof spoon.reflect.code.CtFieldAccess)) {
                                return false;
                            }
                            if (!(((spoon.reflect.code.CtFieldAccess) (assigned)).getVariable().getSimpleName().equals(ctField.getSimpleName()))) {
                                return false;
                            }
                        }else {
                            return false;
                        }
                        return true;
                    }
                });
                if ((matchers.size()) != 1) {
                    throw new spoon.SpoonException(((("Get more than one setter. Please make an more ingenious method to get setter method. " + (matchers.size())) + " ") + ctField));
                }
                return matchers.get(0);
            }

            private <T> spoon.reflect.declaration.CtMethod<?> getGetterOf(spoon.reflect.declaration.CtField<T> ctField) {
                if (ctField.getType().equals(getFactory().createCtTypeReference(spoon.support.reflect.CtModifierHandler.class))) {
                    return ctField.getDeclaringType().getMethod("getModifiers");
                }
                for (spoon.reflect.declaration.CtMethod<?> ctMethod : ctField.getDeclaringType().getMethods()) {
                    if (((ctMethod.getSimpleName().startsWith("get")) || (ctMethod.getSimpleName().startsWith("is"))) && (ctMethod.getSimpleName().toLowerCase().contains(ctField.getSimpleName().toLowerCase()))) {
                        if (!(ctMethod.getType().equals(ctField.getType()))) {
                            continue;
                        }
                        if ((ctMethod.getParameters().size()) != 0) {
                            continue;
                        }
                        return ctMethod;
                    }
                }
                final spoon.reflect.code.CtBlock<?> templateRoot = GETTER_TEMPLATE_MATCHER_CLASS.getMethod("getElement").getBody();
                ((spoon.reflect.code.CtReturn) (templateRoot.getStatement(0))).setReturnedExpression(factory.Code().createVariableRead(ctField.getReference(), true));
                java.util.List<spoon.reflect.declaration.CtMethod> matchers = ctField.getDeclaringType().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtMethod>(spoon.reflect.declaration.CtMethod.class) {
                    @java.lang.Override
                    public boolean matches(spoon.reflect.declaration.CtMethod element) {
                        return element.getBody().toString().equals(templateRoot.toString());
                    }
                });
                if (matchers.isEmpty()) {
                    throw new spoon.SpoonException(("No getter found for field " + ctField));
                }
                if ((matchers.size()) > 1) {
                    throw new spoon.SpoonException((("Get more than one getter (" + (org.apache.commons.lang3.StringUtils.join(matchers, ";"))) + "). Please make an more ingenious method to get getter method."));
                }
                return matchers.get(0);
            }

            private boolean isSubTypeOfCtElement(spoon.reflect.reference.CtTypeReference<?> type) {
                if ((!(type.isPrimitive())) && (!(type.equals(factory.Type().STRING)))) {
                    if (type.isSubtypeOf(factory.Type().createReference(spoon.reflect.declaration.CtElement.class))) {
                        return true;
                    }
                    if (((type.getQualifiedName().equals(LIST_REFERENCE.getQualifiedName())) || (type.getQualifiedName().equals(COLLECTION_REFERENCE.getQualifiedName()))) || (type.getQualifiedName().equals(SET_REFERENCE.getQualifiedName()))) {
                        if (type.getActualTypeArguments().get(0).isSubtypeOf(CTELEMENT_REFERENCE)) {
                            return true;
                        }
                    }
                }
                return false;
            }

            private boolean isConstantOrStatic(spoon.reflect.declaration.CtField<?> ctField) {
                return (ctField.getModifiers().contains(spoon.reflect.declaration.ModifierKind.FINAL)) || (ctField.getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC));
            }
        }.scan(getFactory().Class().get(spoon.reflect.visitor.CtInheritanceScanner.class));
    }

    private spoon.reflect.declaration.CtClass<java.lang.Object> createCloneVisitor() {
        final spoon.reflect.declaration.CtPackage aPackage = getFactory().Package().getOrCreate(spoon.generating.CloneVisitorGenerator.TARGET_CLONE_PACKAGE);
        final spoon.reflect.declaration.CtClass<java.lang.Object> target = getFactory().Class().get(spoon.generating.CloneVisitorGenerator.GENERATING_CLONE);
        target.setSimpleName(spoon.generating.CloneVisitorGenerator.TARGET_CLONE_TYPE);
        target.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        aPackage.addType(target);
        final java.util.List<spoon.reflect.reference.CtTypeReference> references = target.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtTypeReference>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference reference) {
                return spoon.generating.CloneVisitorGenerator.GENERATING_CLONE.equals(reference.getQualifiedName());
            }
        });
        for (spoon.reflect.reference.CtTypeReference reference : references) {
            reference.setSimpleName(spoon.generating.CloneVisitorGenerator.TARGET_CLONE_TYPE);
            reference.setPackage(aPackage.getReference());
        }
        target.getConstructors().forEach(( c) -> c.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC));
        return target;
    }

    private spoon.reflect.declaration.CtClass<java.lang.Object> createCloneBuilder() {
        final spoon.reflect.declaration.CtPackage aPackage = getFactory().Package().getOrCreate(spoon.generating.CloneVisitorGenerator.TARGET_CLONE_PACKAGE);
        final spoon.reflect.declaration.CtClass<java.lang.Object> target = getFactory().Class().get(spoon.generating.CloneVisitorGenerator.GENERATING_BUILDER_CLONE);
        target.setSimpleName(spoon.generating.CloneVisitorGenerator.TARGET_BUILDER_CLONE_TYPE);
        target.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        aPackage.addType(target);
        final java.util.List<spoon.reflect.reference.CtTypeReference> references = target.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtTypeReference>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference reference) {
                return spoon.generating.CloneVisitorGenerator.GENERATING_BUILDER_CLONE.equals(reference.getQualifiedName());
            }
        });
        for (spoon.reflect.reference.CtTypeReference reference : references) {
            reference.setSimpleName(spoon.generating.CloneVisitorGenerator.TARGET_BUILDER_CLONE_TYPE);
            reference.setPackage(aPackage.getReference());
        }
        return target;
    }
}


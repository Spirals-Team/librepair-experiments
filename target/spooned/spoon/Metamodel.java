package spoon;


public class Metamodel {
    private Metamodel() {
    }

    public static java.util.Set<spoon.reflect.declaration.CtType<?>> getAllMetamodelInterfaces() {
        java.util.Set<spoon.reflect.declaration.CtType<?>> result = new java.util.HashSet<>();
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        result.add(factory.Type().get(spoon.reflect.code.BinaryOperatorKind.class));
        result.add(factory.Type().get(spoon.reflect.code.CtAbstractInvocation.class));
        result.add(factory.Type().get(spoon.reflect.code.CtAnnotationFieldAccess.class));
        result.add(factory.Type().get(spoon.reflect.code.CtArrayAccess.class));
        result.add(factory.Type().get(spoon.reflect.code.CtArrayRead.class));
        result.add(factory.Type().get(spoon.reflect.code.CtArrayWrite.class));
        result.add(factory.Type().get(spoon.reflect.code.CtAssert.class));
        result.add(factory.Type().get(spoon.reflect.code.CtAssignment.class));
        result.add(factory.Type().get(spoon.reflect.code.CtBinaryOperator.class));
        result.add(factory.Type().get(spoon.reflect.code.CtBlock.class));
        result.add(factory.Type().get(spoon.reflect.code.CtBodyHolder.class));
        result.add(factory.Type().get(spoon.reflect.code.CtBreak.class));
        result.add(factory.Type().get(spoon.reflect.code.CtCFlowBreak.class));
        result.add(factory.Type().get(spoon.reflect.code.CtCase.class));
        result.add(factory.Type().get(spoon.reflect.code.CtCatch.class));
        result.add(factory.Type().get(spoon.reflect.code.CtCatchVariable.class));
        result.add(factory.Type().get(spoon.reflect.code.CtCodeElement.class));
        result.add(factory.Type().get(spoon.reflect.code.CtCodeSnippetExpression.class));
        result.add(factory.Type().get(spoon.reflect.code.CtCodeSnippetStatement.class));
        result.add(factory.Type().get(spoon.reflect.code.CtComment.class));
        result.add(factory.Type().get(spoon.reflect.code.CtConditional.class));
        result.add(factory.Type().get(spoon.reflect.code.CtConstructorCall.class));
        result.add(factory.Type().get(spoon.reflect.code.CtContinue.class));
        result.add(factory.Type().get(spoon.reflect.code.CtDo.class));
        result.add(factory.Type().get(spoon.reflect.code.CtExecutableReferenceExpression.class));
        result.add(factory.Type().get(spoon.reflect.code.CtExpression.class));
        result.add(factory.Type().get(spoon.reflect.code.CtFieldAccess.class));
        result.add(factory.Type().get(spoon.reflect.code.CtFieldRead.class));
        result.add(factory.Type().get(spoon.reflect.code.CtFieldWrite.class));
        result.add(factory.Type().get(spoon.reflect.code.CtFor.class));
        result.add(factory.Type().get(spoon.reflect.code.CtForEach.class));
        result.add(factory.Type().get(spoon.reflect.code.CtIf.class));
        result.add(factory.Type().get(spoon.reflect.code.CtInvocation.class));
        result.add(factory.Type().get(spoon.reflect.code.CtJavaDoc.class));
        result.add(factory.Type().get(spoon.reflect.code.CtJavaDocTag.class));
        result.add(factory.Type().get(spoon.reflect.code.CtLabelledFlowBreak.class));
        result.add(factory.Type().get(spoon.reflect.code.CtLambda.class));
        result.add(factory.Type().get(spoon.reflect.code.CtLiteral.class));
        result.add(factory.Type().get(spoon.reflect.code.CtLocalVariable.class));
        result.add(factory.Type().get(spoon.reflect.code.CtLoop.class));
        result.add(factory.Type().get(spoon.reflect.code.CtNewArray.class));
        result.add(factory.Type().get(spoon.reflect.code.CtNewClass.class));
        result.add(factory.Type().get(spoon.reflect.code.CtOperatorAssignment.class));
        result.add(factory.Type().get(spoon.reflect.code.CtRHSReceiver.class));
        result.add(factory.Type().get(spoon.reflect.code.CtReturn.class));
        result.add(factory.Type().get(spoon.reflect.code.CtStatement.class));
        result.add(factory.Type().get(spoon.reflect.code.CtStatementList.class));
        result.add(factory.Type().get(spoon.reflect.code.CtSuperAccess.class));
        result.add(factory.Type().get(spoon.reflect.code.CtSwitch.class));
        result.add(factory.Type().get(spoon.reflect.code.CtSynchronized.class));
        result.add(factory.Type().get(spoon.reflect.code.CtTargetedExpression.class));
        result.add(factory.Type().get(spoon.reflect.code.CtThisAccess.class));
        result.add(factory.Type().get(spoon.reflect.code.CtThrow.class));
        result.add(factory.Type().get(spoon.reflect.code.CtTry.class));
        result.add(factory.Type().get(spoon.reflect.code.CtTryWithResource.class));
        result.add(factory.Type().get(spoon.reflect.code.CtTypeAccess.class));
        result.add(factory.Type().get(spoon.reflect.code.CtUnaryOperator.class));
        result.add(factory.Type().get(spoon.reflect.code.CtVariableAccess.class));
        result.add(factory.Type().get(spoon.reflect.code.CtVariableRead.class));
        result.add(factory.Type().get(spoon.reflect.code.CtVariableWrite.class));
        result.add(factory.Type().get(spoon.reflect.code.CtWhile.class));
        result.add(factory.Type().get(spoon.reflect.code.UnaryOperatorKind.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtAnnotatedElementType.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtAnnotation.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtAnnotationMethod.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtAnnotationType.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtAnonymousExecutable.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtClass.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtCodeSnippet.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtConstructor.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtElement.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtEnum.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtEnumValue.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtExecutable.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtField.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtFormalTypeDeclarer.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtInterface.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtMethod.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtModifiable.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtMultiTypedElement.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtNamedElement.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtPackage.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtParameter.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtShadowable.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtType.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtTypeInformation.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtTypeMember.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtTypeParameter.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtTypedElement.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtVariable.class));
        result.add(factory.Type().get(spoon.reflect.declaration.ModifierKind.class));
        result.add(factory.Type().get(spoon.reflect.declaration.ParentNotInitializedException.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtActualTypeContainer.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtArrayTypeReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtCatchVariableReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtExecutableReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtFieldReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtIntersectionTypeReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtLocalVariableReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtPackageReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtParameterReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtTypeParameterReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtTypeReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtUnboundVariableReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtVariableReference.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtWildcardReference.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtImport.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtImportKind.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtModule.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtModuleRequirement.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtPackageExport.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtProvidedService.class));
        result.add(factory.Type().get(spoon.reflect.reference.CtModuleReference.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtUsedService.class));
        result.add(factory.Type().get(spoon.reflect.declaration.CtModuleDirective.class));
        return result;
    }

    public static java.util.Collection<spoon.Metamodel.Type> getAllMetamodelTypes() {
        return spoon.Metamodel.typesByName.values();
    }

    public static spoon.Metamodel.Type getMetamodelTypeByClass(java.lang.Class<? extends spoon.reflect.declaration.CtElement> clazz) {
        return spoon.Metamodel.typesByClass.get(clazz);
    }

    public static class Type {
        private final java.lang.String name;

        private final java.lang.Class<? extends spoon.reflect.declaration.CtElement> modelClass;

        private final java.lang.Class<? extends spoon.reflect.declaration.CtElement> modelInterface;

        private final java.util.List<spoon.Metamodel.Field> fields;

        private final java.util.Map<spoon.reflect.path.CtRole, spoon.Metamodel.Field> fieldsByRole;

        private Type(java.lang.String name, java.lang.Class<? extends spoon.reflect.declaration.CtElement> modelInterface, java.lang.Class<? extends spoon.reflect.declaration.CtElement> modelClass, java.util.function.Consumer<spoon.Metamodel.FieldMaker> fieldsCreator) {
            super();
            this.name = name;
            this.modelClass = modelClass;
            this.modelInterface = modelInterface;
            java.util.List<spoon.Metamodel.Field> fields = new java.util.ArrayList<>();
            this.fields = java.util.Collections.unmodifiableList(fields);
            fieldsCreator.accept(new spoon.Metamodel.FieldMaker() {
                @java.lang.Override
                public spoon.Metamodel.FieldMaker field(spoon.reflect.path.CtRole role, boolean derived, boolean unsettable) {
                    fields.add(new spoon.Metamodel.Field(spoon.Metamodel.Type.this, role, derived, unsettable));
                    return this;
                }
            });
            java.util.Map<spoon.reflect.path.CtRole, spoon.Metamodel.Field> fieldsByRole = new java.util.LinkedHashMap<>(fields.size());
            fields.forEach(( f) -> fieldsByRole.put(f.getRole(), f));
            this.fieldsByRole = java.util.Collections.unmodifiableMap(fieldsByRole);
        }

        public java.lang.String getName() {
            return name;
        }

        public java.lang.Class<? extends spoon.reflect.declaration.CtElement> getModelClass() {
            return modelClass;
        }

        public java.lang.Class<? extends spoon.reflect.declaration.CtElement> getModelInterface() {
            return modelInterface;
        }

        @java.lang.Override
        public java.lang.String toString() {
            return getName();
        }

        public java.util.List<spoon.Metamodel.Field> getFields() {
            return fields;
        }

        public spoon.Metamodel.Field getField(spoon.reflect.path.CtRole role) {
            return fieldsByRole.get(role);
        }
    }

    public static class Field {
        private final spoon.Metamodel.Type owner;

        private final spoon.reflect.path.CtRole role;

        private final spoon.reflect.meta.RoleHandler roleHandler;

        private final boolean derived;

        private final boolean unsettable;

        private Field(spoon.Metamodel.Type owner, spoon.reflect.path.CtRole role, boolean derived, boolean unsettable) {
            super();
            this.owner = owner;
            this.role = role;
            this.derived = derived;
            this.unsettable = unsettable;
            this.roleHandler = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(owner.modelClass, role);
        }

        public spoon.Metamodel.Type getOwner() {
            return owner;
        }

        public spoon.reflect.path.CtRole getRole() {
            return role;
        }

        public spoon.reflect.meta.RoleHandler getRoleHandler() {
            return roleHandler;
        }

        public boolean isDerived() {
            return derived;
        }

        public boolean isUnsettable() {
            return unsettable;
        }

        public <T, U> U getValue(T element) {
            return roleHandler.getValue(element);
        }

        public <T, U> void setValue(T element, U value) {
            roleHandler.setValue(element, value);
        }

        public java.lang.Class<?> getValueClass() {
            return roleHandler.getValueClass();
        }

        public spoon.reflect.meta.ContainerKind getContainerKind() {
            return roleHandler.getContainerKind();
        }

        @java.lang.Override
        public java.lang.String toString() {
            return ((getOwner().toString()) + "#") + (getRole().getCamelCaseName());
        }
    }

    private interface FieldMaker {
        spoon.Metamodel.FieldMaker field(spoon.reflect.path.CtRole role, boolean derived, boolean unsettable);
    }

    private static final java.util.Map<java.lang.String, spoon.Metamodel.Type> typesByName = new java.util.HashMap<>();

    private static final java.util.Map<java.lang.Class<?>, spoon.Metamodel.Type> typesByClass = new java.util.HashMap<>();

    static {
        java.util.List<spoon.Metamodel.Type> types = new java.util.ArrayList<>();
        spoon.Metamodel.initTypes(types);
        types.forEach(( type) -> {
            spoon.Metamodel.typesByName.put(type.getName(), type);
            spoon.Metamodel.typesByClass.put(type.getModelClass(), type);
            spoon.Metamodel.typesByClass.put(type.getModelInterface(), type);
        });
    }

    private static void initTypes(java.util.List<spoon.Metamodel.Type> types) {
        types.add(new spoon.Metamodel.Type("CtConditional", spoon.reflect.code.CtConditional.class, spoon.support.reflect.code.CtConditionalImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CONDITION, false, false).field(spoon.reflect.path.CtRole.THEN, false, false).field(spoon.reflect.path.CtRole.ELSE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.CAST, false, false)));
        types.add(new spoon.Metamodel.Type("CtProvidedService", spoon.reflect.declaration.CtProvidedService.class, spoon.support.reflect.declaration.CtProvidedServiceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.SERVICE_TYPE, false, false).field(spoon.reflect.path.CtRole.IMPLEMENTATION_TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtParameter", spoon.reflect.declaration.CtParameter.class, spoon.support.reflect.declaration.CtParameterImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.IS_VARARGS, false, false).field(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, true, true).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtWhile", spoon.reflect.code.CtWhile.class, spoon.support.reflect.code.CtWhileImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtTypeReference", spoon.reflect.reference.CtTypeReference.class, spoon.support.reflect.reference.CtTypeReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, true, true).field(spoon.reflect.path.CtRole.INTERFACE, true, true).field(spoon.reflect.path.CtRole.SUPER_TYPE, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.PACKAGE_REF, false, false).field(spoon.reflect.path.CtRole.DECLARING_TYPE, false, false).field(spoon.reflect.path.CtRole.TYPE_ARGUMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true)));
        types.add(new spoon.Metamodel.Type("CtCatchVariableReference", spoon.reflect.reference.CtCatchVariableReference.class, spoon.support.reflect.reference.CtCatchVariableReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtContinue", spoon.reflect.code.CtContinue.class, spoon.support.reflect.code.CtContinueImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.TARGET_LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtInterface", spoon.reflect.declaration.CtInterface.class, spoon.support.reflect.declaration.CtInterfaceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.SUPER_TYPE, true, true).field(spoon.reflect.path.CtRole.NESTED_TYPE, true, false).field(spoon.reflect.path.CtRole.METHOD, true, false).field(spoon.reflect.path.CtRole.FIELD, true, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.INTERFACE, false, false).field(spoon.reflect.path.CtRole.TYPE_PARAMETER, false, false).field(spoon.reflect.path.CtRole.TYPE_MEMBER, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtAssignment", spoon.reflect.code.CtAssignment.class, spoon.support.reflect.code.CtAssignmentImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.ASSIGNED, false, false).field(spoon.reflect.path.CtRole.ASSIGNMENT, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtBinaryOperator", spoon.reflect.code.CtBinaryOperator.class, spoon.support.reflect.code.CtBinaryOperatorImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.OPERATOR_KIND, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.LEFT_OPERAND, false, false).field(spoon.reflect.path.CtRole.RIGHT_OPERAND, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtEnumValue", spoon.reflect.declaration.CtEnumValue.class, spoon.support.reflect.declaration.CtEnumValueImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.ASSIGNMENT, true, true).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtModuleRequirement", spoon.reflect.declaration.CtModuleRequirement.class, spoon.support.reflect.declaration.CtModuleRequirementImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.MODULE_REF, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtForEach", spoon.reflect.code.CtForEach.class, spoon.support.reflect.code.CtForEachImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.FOREACH_VARIABLE, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtConstructor", spoon.reflect.declaration.CtConstructor.class, spoon.support.reflect.declaration.CtConstructorImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, true, true).field(spoon.reflect.path.CtRole.TYPE, true, true).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.PARAMETER, false, false).field(spoon.reflect.path.CtRole.THROWN, false, false).field(spoon.reflect.path.CtRole.TYPE_PARAMETER, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtSuperAccess", spoon.reflect.code.CtSuperAccess.class, spoon.support.reflect.code.CtSuperAccessImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false).field(spoon.reflect.path.CtRole.VARIABLE, false, false)));
        types.add(new spoon.Metamodel.Type("CtAnonymousExecutable", spoon.reflect.declaration.CtAnonymousExecutable.class, spoon.support.reflect.declaration.CtAnonymousExecutableImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, true, true).field(spoon.reflect.path.CtRole.TYPE, true, true).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.PARAMETER, true, true).field(spoon.reflect.path.CtRole.THROWN, true, true).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtComment", spoon.reflect.code.CtComment.class, spoon.support.reflect.code.CtCommentImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.COMMENT_CONTENT, false, false).field(spoon.reflect.path.CtRole.COMMENT_TYPE, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtWildcardReference", spoon.reflect.reference.CtWildcardReference.class, spoon.support.reflect.reference.CtWildcardReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, true, true).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_UPPER, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, true, true).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.INTERFACE, true, true).field(spoon.reflect.path.CtRole.SUPER_TYPE, true, true).field(spoon.reflect.path.CtRole.TYPE_ARGUMENT, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.PACKAGE_REF, false, false).field(spoon.reflect.path.CtRole.DECLARING_TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.BOUNDING_TYPE, false, false)));
        types.add(new spoon.Metamodel.Type("CtThisAccess", spoon.reflect.code.CtThisAccess.class, spoon.support.reflect.code.CtThisAccessImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false)));
        types.add(new spoon.Metamodel.Type("CtArrayWrite", spoon.reflect.code.CtArrayWrite.class, spoon.support.reflect.code.CtArrayWriteImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtPackageReference", spoon.reflect.reference.CtPackageReference.class, spoon.support.reflect.reference.CtPackageReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtJavaDoc", spoon.reflect.code.CtJavaDoc.class, spoon.support.reflect.code.CtJavaDocImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.COMMENT_CONTENT, false, false).field(spoon.reflect.path.CtRole.COMMENT_TYPE, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.COMMENT_TAG, false, false)));
        types.add(new spoon.Metamodel.Type("CtArrayRead", spoon.reflect.code.CtArrayRead.class, spoon.support.reflect.code.CtArrayReadImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtStatementList", spoon.reflect.code.CtStatementList.class, spoon.support.reflect.code.CtStatementListImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.STATEMENT, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtVariableWrite", spoon.reflect.code.CtVariableWrite.class, spoon.support.reflect.code.CtVariableWriteImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.VARIABLE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtParameterReference", spoon.reflect.reference.CtParameterReference.class, spoon.support.reflect.reference.CtParameterReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtOperatorAssignment", spoon.reflect.code.CtOperatorAssignment.class, spoon.support.reflect.code.CtOperatorAssignmentImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.OPERATOR_KIND, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.ASSIGNED, false, false).field(spoon.reflect.path.CtRole.ASSIGNMENT, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtAnnotationFieldAccess", spoon.reflect.code.CtAnnotationFieldAccess.class, spoon.support.reflect.code.CtAnnotationFieldAccessImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false).field(spoon.reflect.path.CtRole.VARIABLE, false, false)));
        types.add(new spoon.Metamodel.Type("CtUnboundVariableReference", spoon.reflect.reference.CtUnboundVariableReference.class, spoon.support.reflect.reference.CtUnboundVariableReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.ANNOTATION, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false)));
        types.add(new spoon.Metamodel.Type("CtAnnotationMethod", spoon.reflect.declaration.CtAnnotationMethod.class, spoon.support.reflect.declaration.CtAnnotationMethodImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.BODY, true, true).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.IS_DEFAULT, false, false).field(spoon.reflect.path.CtRole.PARAMETER, true, true).field(spoon.reflect.path.CtRole.THROWN, true, true).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.TYPE_PARAMETER, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtClass", spoon.reflect.declaration.CtClass.class, spoon.support.reflect.declaration.CtClassImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, true, true).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.NESTED_TYPE, true, false).field(spoon.reflect.path.CtRole.CONSTRUCTOR, true, false).field(spoon.reflect.path.CtRole.METHOD, true, false).field(spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE, true, false).field(spoon.reflect.path.CtRole.FIELD, true, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.SUPER_TYPE, false, false).field(spoon.reflect.path.CtRole.INTERFACE, false, false).field(spoon.reflect.path.CtRole.TYPE_PARAMETER, false, false).field(spoon.reflect.path.CtRole.TYPE_MEMBER, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtBlock", spoon.reflect.code.CtBlock.class, spoon.support.reflect.code.CtBlockImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.STATEMENT, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtPackage", spoon.reflect.declaration.CtPackage.class, spoon.support.reflect.declaration.CtPackageImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.SUB_PACKAGE, false, false).field(spoon.reflect.path.CtRole.CONTAINED_TYPE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtTryWithResource", spoon.reflect.code.CtTryWithResource.class, spoon.support.reflect.code.CtTryWithResourceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TRY_RESOURCE, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.CATCH, false, false).field(spoon.reflect.path.CtRole.FINALIZER, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtAssert", spoon.reflect.code.CtAssert.class, spoon.support.reflect.code.CtAssertImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CONDITION, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtSwitch", spoon.reflect.code.CtSwitch.class, spoon.support.reflect.code.CtSwitchImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.CASE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtTry", spoon.reflect.code.CtTry.class, spoon.support.reflect.code.CtTryImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.CATCH, false, false).field(spoon.reflect.path.CtRole.FINALIZER, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtSynchronized", spoon.reflect.code.CtSynchronized.class, spoon.support.reflect.code.CtSynchronizedImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtImport", spoon.reflect.declaration.CtImport.class, spoon.support.reflect.declaration.CtImportImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.IMPORT_REFERENCE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtTypeParameterReference", spoon.reflect.reference.CtTypeParameterReference.class, spoon.support.reflect.reference.CtTypeParameterReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_UPPER, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, true, true).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.INTERFACE, true, true).field(spoon.reflect.path.CtRole.SUPER_TYPE, true, true).field(spoon.reflect.path.CtRole.TYPE_ARGUMENT, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.PACKAGE_REF, false, false).field(spoon.reflect.path.CtRole.DECLARING_TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.BOUNDING_TYPE, false, false)));
        types.add(new spoon.Metamodel.Type("CtInvocation", spoon.reflect.code.CtInvocation.class, spoon.support.reflect.code.CtInvocationImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.TYPE_ARGUMENT, true, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false).field(spoon.reflect.path.CtRole.EXECUTABLE_REF, false, false).field(spoon.reflect.path.CtRole.ARGUMENT, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtCodeSnippetExpression", spoon.reflect.code.CtCodeSnippetExpression.class, spoon.support.reflect.code.CtCodeSnippetExpressionImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.SNIPPET, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false)));
        types.add(new spoon.Metamodel.Type("CtFieldWrite", spoon.reflect.code.CtFieldWrite.class, spoon.support.reflect.code.CtFieldWriteImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false).field(spoon.reflect.path.CtRole.VARIABLE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtUnaryOperator", spoon.reflect.code.CtUnaryOperator.class, spoon.support.reflect.code.CtUnaryOperatorImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.OPERATOR_KIND, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtExecutableReference", spoon.reflect.reference.CtExecutableReference.class, spoon.support.reflect.reference.CtExecutableReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_STATIC, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.DECLARING_TYPE, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.ARGUMENT_TYPE, false, false).field(spoon.reflect.path.CtRole.TYPE_ARGUMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true)));
        types.add(new spoon.Metamodel.Type("CtFor", spoon.reflect.code.CtFor.class, spoon.support.reflect.code.CtForImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.FOR_INIT, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.FOR_UPDATE, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtVariableRead", spoon.reflect.code.CtVariableRead.class, spoon.support.reflect.code.CtVariableReadImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.VARIABLE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtTypeParameter", spoon.reflect.declaration.CtTypeParameter.class, spoon.support.reflect.declaration.CtTypeParameterImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, true, true).field(spoon.reflect.path.CtRole.INTERFACE, true, true).field(spoon.reflect.path.CtRole.TYPE_MEMBER, true, true).field(spoon.reflect.path.CtRole.NESTED_TYPE, true, true).field(spoon.reflect.path.CtRole.METHOD, true, true).field(spoon.reflect.path.CtRole.FIELD, true, true).field(spoon.reflect.path.CtRole.TYPE_PARAMETER, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.SUPER_TYPE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtLocalVariable", spoon.reflect.code.CtLocalVariable.class, spoon.support.reflect.code.CtLocalVariableImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.ASSIGNMENT, true, true).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtIf", spoon.reflect.code.CtIf.class, spoon.support.reflect.code.CtIfImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CONDITION, false, false).field(spoon.reflect.path.CtRole.THEN, false, false).field(spoon.reflect.path.CtRole.ELSE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtModule", spoon.reflect.declaration.CtModule.class, spoon.support.reflect.declaration.CtModuleImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.REQUIRED_MODULE, true, false).field(spoon.reflect.path.CtRole.EXPORTED_PACKAGE, true, false).field(spoon.reflect.path.CtRole.OPENED_PACKAGE, true, false).field(spoon.reflect.path.CtRole.SERVICE_TYPE, true, false).field(spoon.reflect.path.CtRole.PROVIDED_SERVICE, true, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.MODULE_DIRECTIVE, false, false).field(spoon.reflect.path.CtRole.SUB_PACKAGE, false, false)));
        types.add(new spoon.Metamodel.Type("CtPackageExport", spoon.reflect.declaration.CtPackageExport.class, spoon.support.reflect.declaration.CtPackageExportImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.OPENED_PACKAGE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.PACKAGE_REF, false, false).field(spoon.reflect.path.CtRole.MODULE_REF, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtConstructorCall", spoon.reflect.code.CtConstructorCall.class, spoon.support.reflect.code.CtConstructorCallImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.TYPE_ARGUMENT, true, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.EXECUTABLE_REF, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false).field(spoon.reflect.path.CtRole.ARGUMENT, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtCase", spoon.reflect.code.CtCase.class, spoon.support.reflect.code.CtCaseImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.STATEMENT, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtModuleReference", spoon.reflect.reference.CtModuleReference.class, spoon.support.reflect.reference.CtModuleReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtCatch", spoon.reflect.code.CtCatch.class, spoon.support.reflect.code.CtCatchImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.PARAMETER, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtArrayTypeReference", spoon.reflect.reference.CtArrayTypeReference.class, spoon.support.reflect.reference.CtArrayTypeReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, true, true).field(spoon.reflect.path.CtRole.INTERFACE, true, true).field(spoon.reflect.path.CtRole.SUPER_TYPE, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.PACKAGE_REF, false, false).field(spoon.reflect.path.CtRole.DECLARING_TYPE, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.TYPE_ARGUMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtMethod", spoon.reflect.declaration.CtMethod.class, spoon.support.reflect.declaration.CtMethodImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.IS_DEFAULT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE_PARAMETER, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.PARAMETER, false, false).field(spoon.reflect.path.CtRole.THROWN, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtLambda", spoon.reflect.code.CtLambda.class, spoon.support.reflect.code.CtLambdaImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.PARAMETER, false, false).field(spoon.reflect.path.CtRole.THROWN, true, true).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtNewArray", spoon.reflect.code.CtNewArray.class, spoon.support.reflect.code.CtNewArrayImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.DIMENSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtUsedService", spoon.reflect.declaration.CtUsedService.class, spoon.support.reflect.declaration.CtUsedServiceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.SERVICE_TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtIntersectionTypeReference", spoon.reflect.reference.CtIntersectionTypeReference.class, spoon.support.reflect.reference.CtIntersectionTypeReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, true, true).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.INTERFACE, true, true).field(spoon.reflect.path.CtRole.SUPER_TYPE, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.PACKAGE_REF, false, false).field(spoon.reflect.path.CtRole.DECLARING_TYPE, false, false).field(spoon.reflect.path.CtRole.TYPE_ARGUMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.BOUND, false, false)));
        types.add(new spoon.Metamodel.Type("CtThrow", spoon.reflect.code.CtThrow.class, spoon.support.reflect.code.CtThrowImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtLiteral", spoon.reflect.code.CtLiteral.class, spoon.support.reflect.code.CtLiteralImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.VALUE, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtReturn", spoon.reflect.code.CtReturn.class, spoon.support.reflect.code.CtReturnImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtJavaDocTag", spoon.reflect.code.CtJavaDocTag.class, spoon.support.reflect.code.CtJavaDocTagImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.COMMENT_CONTENT, false, false).field(spoon.reflect.path.CtRole.DOCUMENTATION_TYPE, false, false).field(spoon.reflect.path.CtRole.JAVADOC_TAG_VALUE, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtField", spoon.reflect.declaration.CtField.class, spoon.support.reflect.declaration.CtFieldImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.ASSIGNMENT, true, true).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtTypeAccess", spoon.reflect.code.CtTypeAccess.class, spoon.support.reflect.code.CtTypeAccessImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, true).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.ACCESSED_TYPE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtCodeSnippetStatement", spoon.reflect.code.CtCodeSnippetStatement.class, spoon.support.reflect.code.CtCodeSnippetStatementImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.SNIPPET, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtDo", spoon.reflect.code.CtDo.class, spoon.support.reflect.code.CtDoImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.EXPRESSION, false, false).field(spoon.reflect.path.CtRole.BODY, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtAnnotation", spoon.reflect.declaration.CtAnnotation.class, spoon.support.reflect.declaration.CtAnnotationImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.CAST, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION_TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.VALUE, false, false)));
        types.add(new spoon.Metamodel.Type("CtFieldRead", spoon.reflect.code.CtFieldRead.class, spoon.support.reflect.code.CtFieldReadImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false).field(spoon.reflect.path.CtRole.VARIABLE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtBreak", spoon.reflect.code.CtBreak.class, spoon.support.reflect.code.CtBreakImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.TARGET_LABEL, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtFieldReference", spoon.reflect.reference.CtFieldReference.class, spoon.support.reflect.reference.CtFieldReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_FINAL, false, false).field(spoon.reflect.path.CtRole.IS_STATIC, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.DECLARING_TYPE, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtEnum", spoon.reflect.declaration.CtEnum.class, spoon.support.reflect.declaration.CtEnumImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, true, true).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.SUPER_TYPE, true, true).field(spoon.reflect.path.CtRole.NESTED_TYPE, true, false).field(spoon.reflect.path.CtRole.CONSTRUCTOR, true, false).field(spoon.reflect.path.CtRole.METHOD, true, false).field(spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE, true, false).field(spoon.reflect.path.CtRole.FIELD, true, false).field(spoon.reflect.path.CtRole.TYPE_PARAMETER, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.INTERFACE, false, false).field(spoon.reflect.path.CtRole.TYPE_MEMBER, false, false).field(spoon.reflect.path.CtRole.VALUE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtNewClass", spoon.reflect.code.CtNewClass.class, spoon.support.reflect.code.CtNewClassImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.TYPE, true, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.LABEL, false, false).field(spoon.reflect.path.CtRole.TYPE_ARGUMENT, true, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.EXECUTABLE_REF, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false).field(spoon.reflect.path.CtRole.ARGUMENT, false, false).field(spoon.reflect.path.CtRole.NESTED_TYPE, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtLocalVariableReference", spoon.reflect.reference.CtLocalVariableReference.class, spoon.support.reflect.reference.CtLocalVariableReferenceImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.COMMENT, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false)));
        types.add(new spoon.Metamodel.Type("CtAnnotationType", spoon.reflect.declaration.CtAnnotationType.class, spoon.support.reflect.declaration.CtAnnotationTypeImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.IS_SHADOW, false, false).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.INTERFACE, true, true).field(spoon.reflect.path.CtRole.SUPER_TYPE, true, true).field(spoon.reflect.path.CtRole.NESTED_TYPE, true, false).field(spoon.reflect.path.CtRole.METHOD, true, false).field(spoon.reflect.path.CtRole.FIELD, true, false).field(spoon.reflect.path.CtRole.TYPE_PARAMETER, true, true).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE_MEMBER, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false)));
        types.add(new spoon.Metamodel.Type("CtCatchVariable", spoon.reflect.code.CtCatchVariable.class, spoon.support.reflect.code.CtCatchVariableImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.NAME, false, false).field(spoon.reflect.path.CtRole.TYPE, true, true).field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, true, true).field(spoon.reflect.path.CtRole.MODIFIER, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.MULTI_TYPE, false, false)));
        types.add(new spoon.Metamodel.Type("CtExecutableReferenceExpression", spoon.reflect.code.CtExecutableReferenceExpression.class, spoon.support.reflect.code.CtExecutableReferenceExpressionImpl.class, ( fm) -> fm.field(spoon.reflect.path.CtRole.IS_IMPLICIT, false, false).field(spoon.reflect.path.CtRole.POSITION, false, false).field(spoon.reflect.path.CtRole.COMMENT, false, false).field(spoon.reflect.path.CtRole.ANNOTATION, false, false).field(spoon.reflect.path.CtRole.TYPE, false, false).field(spoon.reflect.path.CtRole.CAST, false, false).field(spoon.reflect.path.CtRole.EXECUTABLE_REF, false, false).field(spoon.reflect.path.CtRole.TARGET, false, false)));
    }
}


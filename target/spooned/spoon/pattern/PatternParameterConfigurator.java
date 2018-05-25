package spoon.pattern;


@spoon.support.Experimental
public class PatternParameterConfigurator {
    private final spoon.pattern.PatternBuilder patternBuilder;

    private final java.util.Map<java.lang.String, spoon.pattern.internal.parameter.AbstractParameterInfo> parameterInfos;

    private spoon.pattern.internal.parameter.AbstractParameterInfo currentParameter;

    private java.util.List<spoon.reflect.declaration.CtElement> substitutedNodes = new java.util.ArrayList<>();

    private spoon.pattern.ConflictResolutionMode conflictResolutionMode = spoon.pattern.ConflictResolutionMode.FAIL;

    PatternParameterConfigurator(spoon.pattern.PatternBuilder patternBuilder, java.util.Map<java.lang.String, spoon.pattern.internal.parameter.AbstractParameterInfo> parameterInfos) {
        this.patternBuilder = patternBuilder;
        this.parameterInfos = parameterInfos;
    }

    public spoon.pattern.ConflictResolutionMode getConflictResolutionMode() {
        return conflictResolutionMode;
    }

    public spoon.pattern.PatternParameterConfigurator setConflictResolutionMode(spoon.pattern.ConflictResolutionMode conflictResolutionMode) {
        this.conflictResolutionMode = conflictResolutionMode;
        return this;
    }

    public spoon.reflect.visitor.chain.CtQueryable queryModel() {
        return patternBuilder.patternQuery;
    }

    private spoon.pattern.internal.parameter.AbstractParameterInfo getParameterInfo(java.lang.String parameterName, boolean createIfNotExist) {
        spoon.pattern.internal.parameter.AbstractParameterInfo pi = parameterInfos.get(parameterName);
        if (pi == null) {
            pi = new spoon.pattern.internal.parameter.MapParameterInfo(parameterName).setValueConvertor(patternBuilder.getDefaultValueConvertor());
            parameterInfos.put(parameterName, pi);
        }
        return pi;
    }

    public spoon.pattern.PatternParameterConfigurator parameter(java.lang.String paramName) {
        currentParameter = getParameterInfo(paramName, true);
        substitutedNodes.clear();
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator setMinOccurence(int minOccurence) {
        currentParameter.setMinOccurences(minOccurence);
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator setMaxOccurence(int maxOccurence) {
        if ((maxOccurence == (spoon.pattern.internal.parameter.ParameterInfo.UNLIMITED_OCCURENCES)) || ((maxOccurence > 1) && ((currentParameter.isMultiple()) == false))) {
            throw new spoon.SpoonException("Cannot set maxOccurences > 1 for single value parameter. Call setMultiple(true) first.");
        }
        currentParameter.setMaxOccurences(maxOccurence);
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator setMatchingStrategy(spoon.pattern.Quantifier quantifier) {
        currentParameter.setMatchingStrategy(quantifier);
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator setValueType(java.lang.Class<?> valueType) {
        currentParameter.setParameterValueType(valueType);
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator setContainerKind(spoon.reflect.meta.ContainerKind containerKind) {
        currentParameter.setContainerKind(containerKind);
        return this;
    }

    public spoon.pattern.internal.parameter.ParameterInfo getCurrentParameter() {
        if ((currentParameter) == null) {
            throw new spoon.SpoonException("Parameter name must be defined first by call of #parameter(String) method.");
        }
        return currentParameter;
    }

    public spoon.pattern.PatternParameterConfigurator byType(java.lang.Class<?> type) {
        return byType(type.getName());
    }

    public spoon.pattern.PatternParameterConfigurator byType(java.lang.String typeQualifiedName) {
        return byType(patternBuilder.getFactory().Type().createReference(typeQualifiedName));
    }

    public spoon.pattern.PatternParameterConfigurator byType(spoon.reflect.reference.CtTypeReference<?> type) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        queryModel().filterChildren((spoon.reflect.reference.CtTypeReference<?> typeRef) -> typeRef.equals(type)).forEach((spoon.reflect.reference.CtTypeReference<?> typeRef) -> {
            addSubstitutionRequest(pi, typeRef);
        });
        java.lang.String typeQName = type.getQualifiedName();
        spoon.reflect.declaration.CtType<?> type2 = queryModel().filterChildren((spoon.reflect.declaration.CtType<?> t) -> t.getQualifiedName().equals(typeQName)).first();
        if (type2 != null) {
            addSubstitutionRequest(pi, type2, spoon.reflect.path.CtRole.NAME);
        }
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byLocalType(spoon.reflect.declaration.CtType<?> searchScope, java.lang.String localTypeSimpleName) {
        spoon.reflect.reference.CtTypeReference<?> nestedType = spoon.pattern.PatternBuilder.getLocalTypeRefBySimpleName(searchScope, localTypeSimpleName);
        if (nestedType == null) {
            throw new spoon.SpoonException((("Template parameter " + localTypeSimpleName) + " doesn't match to any local type"));
        }
        byType(nestedType);
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byVariable(java.lang.String variableName) {
        spoon.reflect.declaration.CtVariable<?> var = queryModel().map(new spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction(variableName)).first();
        if (var != null) {
            byVariable(var);
        }
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byVariable(spoon.reflect.declaration.CtVariable<?> variable) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        spoon.reflect.visitor.chain.CtQueryable root = queryModel();
        if (patternBuilder.isInModel(variable)) {
            root = variable;
        }
        root.map(new spoon.reflect.visitor.filter.VariableReferenceFunction(variable)).forEach((spoon.reflect.reference.CtVariableReference<?> varRef) -> {
            addSubstitutionRequest(pi, varRef);
        });
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byInvocation(spoon.reflect.declaration.CtMethod<?> method) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        queryModel().filterChildren(new spoon.reflect.visitor.filter.InvocationFilter(method)).forEach((spoon.reflect.code.CtInvocation<?> inv) -> {
            addSubstitutionRequest(pi, inv);
        });
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byFieldAccessOnVariable(java.lang.String varName) {
        spoon.reflect.declaration.CtVariable<?> var = queryModel().map(new spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction(varName)).first();
        if (var != null) {
            createPatternParameterForVariable(var);
        }else {
            java.util.List<spoon.reflect.declaration.CtVariable<?>> vars = queryModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, varName)).list();
            if ((vars.size()) > 1) {
                throw new spoon.SpoonException(("Ambiguous variable " + varName));
            }else
                if ((vars.size()) == 1) {
                    createPatternParameterForVariable(vars.get(0));
                }

        }
        return this;
    }

    private void createPatternParameterForVariable(spoon.reflect.declaration.CtVariable<?> variable) {
        spoon.reflect.visitor.chain.CtQueryable searchScope;
        if (patternBuilder.isInModel(variable)) {
            addSubstitutionRequest(parameter(variable.getSimpleName()).getCurrentParameter(), variable);
            searchScope = variable;
        }else {
            searchScope = queryModel();
        }
        searchScope.map(new spoon.reflect.visitor.filter.VariableReferenceFunction(variable)).forEach((spoon.reflect.reference.CtVariableReference<?> varRef) -> {
            spoon.reflect.code.CtFieldRead<?> fieldRead = varRef.getParent(spoon.reflect.code.CtFieldRead.class);
            if (fieldRead != null) {
                addSubstitutionRequest(parameter(fieldRead.getVariable().getSimpleName()).getCurrentParameter(), fieldRead);
            }else {
                addSubstitutionRequest(parameter(varRef.getSimpleName()).getCurrentParameter(), varRef);
            }
        });
    }

    public spoon.pattern.PatternParameterConfigurator byTemplateParameter() {
        return byTemplateParameter(null);
    }

    @java.lang.Deprecated
    public spoon.pattern.PatternParameterConfigurator byTemplateParameter(java.util.Map<java.lang.String, java.lang.Object> parameterValues) {
        spoon.reflect.declaration.CtType<?> templateType = patternBuilder.getTemplateTypeRef().getTypeDeclaration();
        templateType.map(new spoon.reflect.visitor.filter.AllTypeMembersFunction()).forEach((spoon.reflect.declaration.CtTypeMember typeMember) -> {
            configureByTemplateParameter(templateType, parameterValues, typeMember);
        });
        return this;
    }

    private void configureByTemplateParameter(spoon.reflect.declaration.CtType<?> templateType, java.util.Map<java.lang.String, java.lang.Object> parameterValues, spoon.reflect.declaration.CtTypeMember typeMember) {
        spoon.reflect.factory.Factory f = typeMember.getFactory();
        spoon.reflect.reference.CtTypeReference<spoon.reflect.reference.CtTypeReference> typeReferenceRef = f.Type().createReference(spoon.reflect.reference.CtTypeReference.class);
        spoon.reflect.reference.CtTypeReference<spoon.reflect.code.CtStatement> ctStatementRef = f.Type().createReference(spoon.reflect.code.CtStatement.class);
        spoon.reflect.reference.CtTypeReference<spoon.template.TemplateParameter> templateParamRef = f.Type().createReference(spoon.template.TemplateParameter.class);
        spoon.template.Parameter param = typeMember.getAnnotation(spoon.template.Parameter.class);
        if (param != null) {
            if (typeMember instanceof spoon.reflect.declaration.CtField) {
                spoon.reflect.declaration.CtField<?> paramField = ((spoon.reflect.declaration.CtField<?>) (typeMember));
                java.lang.String fieldName = typeMember.getSimpleName();
                java.lang.String stringMarker = (((param.value()) != null) && ((param.value().length()) > 0)) ? param.value() : fieldName;
                java.lang.String parameterName = stringMarker;
                spoon.reflect.reference.CtTypeReference<?> paramType = paramField.getType();
                if ((paramType.isSubtypeOf(f.Type().ITERABLE)) || (paramType instanceof spoon.reflect.reference.CtArrayTypeReference<?>)) {
                    parameter(parameterName).setContainerKind(spoon.reflect.meta.ContainerKind.LIST).byNamedElement(stringMarker).byReferenceName(stringMarker);
                }else
                    if ((paramType.isSubtypeOf(typeReferenceRef)) || (paramType.getQualifiedName().equals(java.lang.Class.class.getName()))) {
                        spoon.reflect.reference.CtTypeReference<?> nestedType = spoon.pattern.PatternBuilder.getLocalTypeRefBySimpleName(templateType, stringMarker);
                        if (nestedType != null) {
                            parameter(parameterName).byType(nestedType);
                        }
                        parameter(parameterName).byVariable(paramField);
                    }else
                        if (paramType.getQualifiedName().equals(java.lang.String.class.getName())) {
                            spoon.reflect.reference.CtTypeReference<?> nestedType = spoon.pattern.PatternBuilder.getLocalTypeRefBySimpleName(templateType, stringMarker);
                            if (nestedType != null) {
                                parameter(parameterName).byType(nestedType);
                            }
                        }else
                            if (paramType.isSubtypeOf(templateParamRef)) {
                                parameter(parameterName).byTemplateParameterReference(paramField);
                                templateType.getMethodsByName(stringMarker).forEach(( m) -> {
                                    parameter(parameterName).byInvocation(m);
                                });
                            }else
                                if (paramType.isSubtypeOf(ctStatementRef)) {
                                    templateType.getMethodsByName(stringMarker).forEach(( m) -> {
                                        parameter(parameterName).setContainerKind(spoon.reflect.meta.ContainerKind.LIST).byInvocation(m);
                                    });
                                }else {
                                    parameter(parameterName).byVariable(paramField);
                                }




                if ((paramType.getQualifiedName().equals(java.lang.Object.class.getName())) && (parameterValues != null)) {
                    java.lang.Object value = parameterValues.get(parameterName);
                    if ((value instanceof spoon.reflect.code.CtLiteral) || (value instanceof spoon.reflect.reference.CtTypeReference)) {
                        spoon.pattern.internal.parameter.ParameterInfo pi = parameter(parameterName).getCurrentParameter();
                        queryModel().filterChildren((spoon.reflect.code.CtInvocation<?> inv) -> {
                            return inv.getExecutable().getSimpleName().equals(stringMarker);
                        }).forEach((spoon.reflect.code.CtInvocation<?> inv) -> {
                            addSubstitutionRequest(pi, inv);
                        });
                    }
                }
                parameter(parameterName).setConflictResolutionMode(spoon.pattern.ConflictResolutionMode.KEEP_OLD_NODE).bySubstring(stringMarker);
                if (parameterValues != null) {
                    addInlineStatements(fieldName, parameterValues.get(parameterName));
                }
            }else {
                throw new spoon.SpoonException((("Template Parameter annotation on " + (typeMember.getClass().getName())) + " is not supported"));
            }
        }else
            if ((typeMember instanceof spoon.reflect.declaration.CtField<?>) && (((spoon.reflect.declaration.CtField<?>) (typeMember)).getType().isSubtypeOf(templateParamRef))) {
                spoon.reflect.declaration.CtField<?> field = ((spoon.reflect.declaration.CtField<?>) (typeMember));
                java.lang.String parameterName = typeMember.getSimpleName();
                java.lang.Object value = (parameterValues == null) ? null : parameterValues.get(parameterName);
                java.lang.Class valueType = null;
                boolean multiple = false;
                if (value != null) {
                    valueType = value.getClass();
                    if (value instanceof spoon.reflect.code.CtBlock) {
                        multiple = true;
                    }
                }
                parameter(parameterName).setValueType(valueType).setContainerKind((multiple ? spoon.reflect.meta.ContainerKind.LIST : spoon.reflect.meta.ContainerKind.SINGLE)).byTemplateParameterReference(field);
                if (parameterValues != null) {
                    addInlineStatements(parameterName, parameterValues.get(parameterName));
                }
            }

    }

    private void addInlineStatements(java.lang.String variableName, java.lang.Object paramValue) {
        if ((paramValue != null) && (paramValue.getClass().isArray())) {
            patternBuilder.configureInlineStatements(( sb) -> {
                sb.setFailOnMissingParameter(false);
                sb.inlineIfOrForeachReferringTo(variableName);
            });
        }
    }

    @java.lang.Deprecated
    public spoon.pattern.PatternParameterConfigurator byParameterValues(java.util.Map<java.lang.String, java.lang.Object> parameterValues) {
        if (parameterValues != null) {
            spoon.reflect.declaration.CtType<?> templateType = patternBuilder.getTemplateTypeRef().getTypeDeclaration();
            parameterValues.forEach(( paramName, paramValue) -> {
                if ((isSubstituted(paramName)) == false) {
                    if (paramValue instanceof spoon.reflect.reference.CtTypeReference<?>) {
                        parameter(paramName).setConflictResolutionMode(spoon.pattern.ConflictResolutionMode.KEEP_OLD_NODE).byLocalType(templateType, paramName);
                    }
                    parameter(paramName).setConflictResolutionMode(spoon.pattern.ConflictResolutionMode.KEEP_OLD_NODE).bySubstring(paramName);
                }
            });
        }
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byTemplateParameterReference(spoon.reflect.declaration.CtVariable<?> variable) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        queryModel().map(new spoon.reflect.visitor.filter.VariableReferenceFunction(variable)).forEach((spoon.reflect.reference.CtVariableReference<?> varRef) -> {
            spoon.reflect.code.CtVariableAccess<?> varAccess = ((spoon.reflect.code.CtVariableAccess<?>) (varRef.getParent()));
            spoon.reflect.declaration.CtElement invocationOfS = varAccess.getParent();
            if (invocationOfS instanceof spoon.reflect.code.CtInvocation<?>) {
                spoon.reflect.code.CtInvocation<?> invocation = ((spoon.reflect.code.CtInvocation<?>) (invocationOfS));
                if ("S".equals(invocation.getExecutable().getSimpleName())) {
                    addSubstitutionRequest(pi, invocation);
                    return;
                }
            }
            throw new spoon.SpoonException("TemplateParameter reference is NOT used as target of invocation of TemplateParameter#S()");
        });
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byString(java.lang.String stringMarker) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        new spoon.pattern.PatternParameterConfigurator.StringAttributeScanner() {
            @java.lang.Override
            protected void visitStringAttribute(spoon.reflect.meta.RoleHandler roleHandler, spoon.reflect.declaration.CtElement element, java.lang.String value) {
                if (stringMarker.equals(value)) {
                    addSubstitutionRequest(pi, element, roleHandler.getRole());
                }
            }

            protected void visitStringAttribute(spoon.reflect.meta.RoleHandler roleHandler, spoon.reflect.declaration.CtElement element, java.lang.String mapEntryKey, spoon.reflect.declaration.CtElement mapEntryValue) {
                if (stringMarker.equals(mapEntryKey)) {
                    patternBuilder.modifyNodeOfAttributeOfElement(element, roleHandler.getRole(), conflictResolutionMode, ( oldAttrNode) -> {
                        if (oldAttrNode instanceof spoon.pattern.internal.node.MapEntryNode) {
                            spoon.pattern.internal.node.MapEntryNode mapEntryNode = ((spoon.pattern.internal.node.MapEntryNode) (oldAttrNode));
                            return new spoon.pattern.internal.node.MapEntryNode(new spoon.pattern.internal.node.ParameterNode(pi), ((spoon.pattern.internal.node.MapEntryNode) (oldAttrNode)).getValue());
                        }
                        return oldAttrNode;
                    });
                }
            }
        }.scan(patternBuilder.getPatternModel());
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator bySubstring(java.lang.String stringMarker) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        new spoon.pattern.PatternParameterConfigurator.StringAttributeScanner() {
            @java.lang.Override
            protected void visitStringAttribute(spoon.reflect.meta.RoleHandler roleHandler, spoon.reflect.declaration.CtElement element, java.lang.String value) {
                if ((value != null) && ((value.indexOf(stringMarker)) >= 0)) {
                    addSubstitutionRequest(pi, element, roleHandler.getRole(), stringMarker);
                }
            }

            protected void visitStringAttribute(spoon.reflect.meta.RoleHandler roleHandler, spoon.reflect.declaration.CtElement element, java.lang.String mapEntryKey, spoon.reflect.declaration.CtElement mapEntryValue) {
                if ((mapEntryKey != null) && ((mapEntryKey.indexOf(stringMarker)) >= 0)) {
                    patternBuilder.modifyNodeOfAttributeOfElement(element, roleHandler.getRole(), conflictResolutionMode, ( oldAttrNode) -> {
                        java.util.List<spoon.pattern.internal.node.RootNode> nodes = ((spoon.pattern.internal.node.ListOfNodes) (oldAttrNode)).getNodes();
                        for (int i = 0; i < (nodes.size()); i++) {
                            spoon.pattern.internal.node.RootNode node = nodes.get(i);
                            if (node instanceof spoon.pattern.internal.node.MapEntryNode) {
                                spoon.pattern.internal.node.MapEntryNode mapEntryNode = ((spoon.pattern.internal.node.MapEntryNode) (node));
                                nodes.set(i, new spoon.pattern.internal.node.MapEntryNode(spoon.pattern.internal.node.StringNode.setReplaceMarker(mapEntryNode.getKey(), stringMarker, pi), mapEntryNode.getValue()));
                            }
                        }
                        return oldAttrNode;
                    });
                }
            }
        }.scan(patternBuilder.getPatternModel());
        return this;
    }

    private static abstract class StringAttributeScanner extends spoon.reflect.visitor.CtScanner {
        private static java.util.List<spoon.reflect.meta.RoleHandler> stringAttributeRoleHandlers = new java.util.ArrayList<>();

        static {
            spoon.reflect.meta.impl.RoleHandlerHelper.forEachRoleHandler(( rh) -> {
                if (rh.getValueClass().isAssignableFrom(java.lang.String.class)) {
                    spoon.pattern.PatternParameterConfigurator.StringAttributeScanner.stringAttributeRoleHandlers.add(rh);
                }
                if ((rh.getContainerKind()) == (spoon.reflect.meta.ContainerKind.MAP)) {
                    spoon.pattern.PatternParameterConfigurator.StringAttributeScanner.stringAttributeRoleHandlers.add(rh);
                }
            });
        }

        @java.lang.Override
        public void scan(spoon.reflect.declaration.CtElement element) {
            visitStringAttribute(element);
            super.scan(element);
        }

        private void visitStringAttribute(spoon.reflect.declaration.CtElement element) {
            for (spoon.reflect.meta.RoleHandler roleHandler : spoon.pattern.PatternParameterConfigurator.StringAttributeScanner.stringAttributeRoleHandlers) {
                if (roleHandler.getTargetType().isInstance(element)) {
                    java.lang.Object value = roleHandler.getValue(element);
                    if (value instanceof java.lang.String) {
                        visitStringAttribute(roleHandler, element, ((java.lang.String) (value)));
                    }else
                        if (value instanceof java.util.Map) {
                            for (java.util.Map.Entry<java.lang.String, spoon.reflect.declaration.CtElement> e : ((java.util.Map<java.lang.String, spoon.reflect.declaration.CtElement>) (value)).entrySet()) {
                                visitStringAttribute(roleHandler, element, ((java.lang.String) (e.getKey())), e.getValue());
                            }
                        }

                }
            }
        }

        protected abstract void visitStringAttribute(spoon.reflect.meta.RoleHandler roleHandler, spoon.reflect.declaration.CtElement element, java.lang.String value);

        protected abstract void visitStringAttribute(spoon.reflect.meta.RoleHandler roleHandler, spoon.reflect.declaration.CtElement element, java.lang.String mapEntryKey, spoon.reflect.declaration.CtElement mapEntryValue);
    }

    public spoon.pattern.PatternParameterConfigurator byNamedElement(java.lang.String simpleName) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        queryModel().filterChildren((spoon.reflect.declaration.CtNamedElement named) -> simpleName.equals(named.getSimpleName())).forEach((spoon.reflect.declaration.CtNamedElement named) -> {
            addSubstitutionRequest(pi, named);
        });
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byReferenceName(java.lang.String simpleName) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        queryModel().filterChildren((spoon.reflect.reference.CtReference ref) -> simpleName.equals(ref.getSimpleName())).forEach((spoon.reflect.reference.CtReference ref) -> {
            addSubstitutionRequest(pi, ref);
        });
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byFilter(spoon.reflect.visitor.Filter<?> filter) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        queryModel().filterChildren(filter).forEach((spoon.reflect.declaration.CtElement ele) -> {
            addSubstitutionRequest(pi, ele);
        });
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator byRole(spoon.reflect.path.CtRole role, spoon.reflect.visitor.Filter<?> filter) {
        spoon.pattern.internal.parameter.ParameterInfo pi = getCurrentParameter();
        queryModel().filterChildren(filter).forEach((spoon.reflect.declaration.CtElement ele) -> {
            addSubstitutionRequest(pi, ele, role);
        });
        return this;
    }

    public <T> spoon.pattern.PatternParameterConfigurator byCondition(java.lang.Class<T> type, java.util.function.Predicate<T> matchCondition) {
        currentParameter.setMatchCondition(type, matchCondition);
        return this;
    }

    public spoon.pattern.PatternParameterConfigurator matchInlinedStatements() {
        spoon.pattern.InlinedStatementConfigurator sb = new spoon.pattern.InlinedStatementConfigurator(patternBuilder);
        for (spoon.reflect.declaration.CtElement ctElement : substitutedNodes) {
            sb.byElement(ctElement);
        }
        return this;
    }

    public boolean isSubstituted(java.lang.String paramName) {
        if ((patternBuilder.getParameterInfo(paramName)) == null) {
            return false;
        }
        spoon.pattern.internal.parameter.ParameterInfo pi = getParameterInfo(paramName, false);
        if (pi == null) {
            return false;
        }
        class Result {
            boolean isUsed = false;
        }
        Result result = new Result();
        patternBuilder.forEachNodeOfParameter(pi, ( parameterized) -> result.isUsed = true);
        return result.isUsed;
    }

    void addSubstitutionRequest(spoon.pattern.internal.parameter.ParameterInfo parameter, spoon.reflect.declaration.CtElement element) {
        substitutedNodes.add(element);
        spoon.pattern.PatternParameterConfigurator.ParameterElementPair pep = getSubstitutedNodeOfElement(parameter, element);
        patternBuilder.setNodeOfElement(pep.element, new spoon.pattern.internal.node.ParameterNode(pep.parameter), conflictResolutionMode);
        if ((patternBuilder.isAutoSimplifySubstitutions()) && (pep.element.isParentInitialized())) {
            spoon.pattern.internal.node.RootNode node = patternBuilder.getOptionalPatternNode(pep.element.getParent());
            if (node != null) {
                node.setSimplifyGenerated(true);
            }
        }
    }

    void addSubstitutionRequest(spoon.pattern.internal.parameter.ParameterInfo parameter, spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole attributeRole) {
        patternBuilder.setNodeOfAttributeOfElement(element, attributeRole, new spoon.pattern.internal.node.ParameterNode(parameter), conflictResolutionMode);
    }

    void addSubstitutionRequest(spoon.pattern.internal.parameter.ParameterInfo parameter, spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole attributeRole, java.lang.String subStringMarker) {
        patternBuilder.modifyNodeOfAttributeOfElement(element, attributeRole, conflictResolutionMode, ( oldAttrNode) -> {
            return spoon.pattern.internal.node.StringNode.setReplaceMarker(oldAttrNode, subStringMarker, parameter);
        });
    }

    public static class ParameterElementPair {
        final spoon.pattern.internal.parameter.ParameterInfo parameter;

        final spoon.reflect.declaration.CtElement element;

        public ParameterElementPair(spoon.pattern.internal.parameter.ParameterInfo parameter, spoon.reflect.declaration.CtElement element) {
            super();
            this.parameter = parameter;
            this.element = element;
        }

        public spoon.pattern.PatternParameterConfigurator.ParameterElementPair copyAndSet(spoon.pattern.internal.parameter.ParameterInfo param) {
            return new spoon.pattern.PatternParameterConfigurator.ParameterElementPair(param, element);
        }

        public spoon.pattern.PatternParameterConfigurator.ParameterElementPair copyAndSet(spoon.reflect.declaration.CtElement element) {
            return new spoon.pattern.PatternParameterConfigurator.ParameterElementPair(parameter, element);
        }
    }

    private spoon.pattern.PatternParameterConfigurator.ParameterElementPair getSubstitutedNodeOfElement(spoon.pattern.internal.parameter.ParameterInfo parameter, spoon.reflect.declaration.CtElement element) {
        spoon.pattern.PatternParameterConfigurator.ParameterElementPair parameterElementPair = new spoon.pattern.PatternParameterConfigurator.ParameterElementPair(parameter, element);
        parameterElementPair = transformVariableAccessToVariableReference(parameterElementPair);
        parameterElementPair = transformArrayAccess(parameterElementPair);
        parameterElementPair = transformTemplateParameterInvocationOfS(parameterElementPair);
        parameterElementPair = transformExecutableRefToInvocation(parameterElementPair);
        parameterElementPair = transformCtReturnIfNeeded(parameterElementPair);
        parameterElementPair = getLastImplicitParent(parameterElementPair);
        return parameterElementPair;
    }

    private spoon.pattern.PatternParameterConfigurator.ParameterElementPair transformArrayAccess(spoon.pattern.PatternParameterConfigurator.ParameterElementPair pep) {
        spoon.reflect.declaration.CtElement element = pep.element;
        if (element.isParentInitialized()) {
            spoon.reflect.declaration.CtElement parent = element.getParent();
            if (parent instanceof spoon.reflect.code.CtArrayAccess<?, ?>) {
                spoon.reflect.code.CtArrayAccess<?, ?> arrayAccess = ((spoon.reflect.code.CtArrayAccess<?, ?>) (parent));
                spoon.reflect.code.CtExpression<?> expr = arrayAccess.getIndexExpression();
                if (expr instanceof spoon.reflect.code.CtLiteral<?>) {
                    spoon.reflect.code.CtLiteral<?> idxLiteral = ((spoon.reflect.code.CtLiteral<?>) (expr));
                    java.lang.Object idx = idxLiteral.getValue();
                    if (idx instanceof java.lang.Number) {
                        return new spoon.pattern.PatternParameterConfigurator.ParameterElementPair(new spoon.pattern.internal.parameter.ListParameterInfo(((java.lang.Number) (idx)).intValue(), pep.parameter), arrayAccess);
                    }
                }
            }
        }
        return pep;
    }

    private spoon.pattern.PatternParameterConfigurator.ParameterElementPair transformVariableAccessToVariableReference(spoon.pattern.PatternParameterConfigurator.ParameterElementPair pep) {
        if ((pep.element) instanceof spoon.reflect.reference.CtVariableReference<?>) {
            spoon.reflect.reference.CtVariableReference<?> varRef = ((spoon.reflect.reference.CtVariableReference<?>) (pep.element));
            return pep.copyAndSet(varRef.getParent());
        }
        return pep;
    }

    private spoon.pattern.PatternParameterConfigurator.ParameterElementPair transformTemplateParameterInvocationOfS(spoon.pattern.PatternParameterConfigurator.ParameterElementPair pep) {
        spoon.reflect.declaration.CtElement element = pep.element;
        if (element.isParentInitialized()) {
            spoon.reflect.declaration.CtElement parent = element.getParent();
            if (parent instanceof spoon.reflect.code.CtInvocation<?>) {
                spoon.reflect.code.CtInvocation<?> invocation = ((spoon.reflect.code.CtInvocation<?>) (parent));
                spoon.reflect.reference.CtExecutableReference<?> executableRef = invocation.getExecutable();
                if (executableRef.getSimpleName().equals("S")) {
                    if (spoon.template.TemplateParameter.class.getName().equals(executableRef.getDeclaringType().getQualifiedName())) {
                        return pep.copyAndSet(invocation);
                    }
                }
            }
        }
        return pep;
    }

    private spoon.pattern.PatternParameterConfigurator.ParameterElementPair transformExecutableRefToInvocation(spoon.pattern.PatternParameterConfigurator.ParameterElementPair pep) {
        spoon.reflect.declaration.CtElement element = pep.element;
        if (element instanceof spoon.reflect.reference.CtExecutableReference<?>) {
            spoon.reflect.reference.CtExecutableReference<?> execRef = ((spoon.reflect.reference.CtExecutableReference<?>) (element));
            if (element.isParentInitialized()) {
                spoon.reflect.declaration.CtElement parent = execRef.getParent();
                if (parent instanceof spoon.reflect.code.CtInvocation<?>) {
                    return pep.copyAndSet(parent);
                }
            }
        }
        return pep;
    }

    private spoon.pattern.PatternParameterConfigurator.ParameterElementPair transformCtReturnIfNeeded(spoon.pattern.PatternParameterConfigurator.ParameterElementPair pep) {
        spoon.reflect.declaration.CtElement element = pep.element;
        if ((element.isParentInitialized()) && ((element.getParent()) instanceof spoon.reflect.code.CtReturn<?>)) {
            java.lang.Class<?> valueType = pep.parameter.getParameterValueType();
            if ((valueType != null) && (spoon.reflect.code.CtBlock.class.isAssignableFrom(valueType))) {
                return pep.copyAndSet(element.getParent());
            }
        }
        return pep;
    }

    private spoon.pattern.PatternParameterConfigurator.ParameterElementPair getLastImplicitParent(spoon.pattern.PatternParameterConfigurator.ParameterElementPair pep) {
        spoon.reflect.declaration.CtElement element = pep.element;
        while (element.isParentInitialized()) {
            spoon.reflect.declaration.CtElement parent = element.getParent();
            if (((parent instanceof spoon.reflect.code.CtBlock) == false) || ((parent.isImplicit()) == false)) {
                break;
            }
            element = parent;
        } 
        return pep.copyAndSet(element);
    }
}


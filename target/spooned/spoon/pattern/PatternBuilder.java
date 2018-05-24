package spoon.pattern;


@spoon.support.Experimental
public class PatternBuilder {
    public static final java.lang.String TARGET_TYPE = "targetType";

    public static spoon.pattern.PatternBuilder create(java.util.List<spoon.reflect.declaration.CtElement> patternModel) {
        return new spoon.pattern.PatternBuilder(patternModel);
    }

    public static spoon.pattern.PatternBuilder create(spoon.reflect.declaration.CtElement... elems) {
        return new spoon.pattern.PatternBuilder(java.util.Arrays.asList(elems));
    }

    private final java.util.List<spoon.reflect.declaration.CtElement> patternModel;

    private final spoon.pattern.internal.node.ListOfNodes patternNodes;

    private final java.util.Map<spoon.reflect.declaration.CtElement, spoon.pattern.internal.node.RootNode> patternElementToSubstRequests = new java.util.IdentityHashMap<>();

    private final java.util.Set<spoon.pattern.internal.node.RootNode> explicitNodes = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());

    private spoon.reflect.reference.CtTypeReference<?> templateTypeRef;

    private final java.util.Map<java.lang.String, spoon.pattern.internal.parameter.AbstractParameterInfo> parameterInfos = new java.util.HashMap<>();

    spoon.reflect.visitor.chain.CtQueryable patternQuery;

    private spoon.pattern.internal.ValueConvertor valueConvertor;

    private boolean addGeneratedBy = false;

    private boolean autoSimplifySubstitutions = false;

    private boolean built = false;

    static class PatternQuery implements spoon.reflect.visitor.chain.CtQueryable {
        private final spoon.reflect.factory.QueryFactory queryFactory;

        private final java.util.List<spoon.reflect.declaration.CtElement> modelElements;

        PatternQuery(spoon.reflect.factory.QueryFactory queryFactory, java.util.List<spoon.reflect.declaration.CtElement> modelElements) {
            this.queryFactory = queryFactory;
            this.modelElements = modelElements;
        }

        @java.lang.Override
        public <R extends spoon.reflect.declaration.CtElement> spoon.reflect.visitor.chain.CtQuery filterChildren(spoon.reflect.visitor.Filter<R> filter) {
            return queryFactory.createQuery(modelElements).filterChildren(filter);
        }

        @java.lang.Override
        public <I, R> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtFunction<I, R> function) {
            return queryFactory.createQuery(modelElements).map(function);
        }

        @java.lang.Override
        public <I> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtConsumableFunction<I> queryStep) {
            return queryFactory.createQuery(modelElements).map(queryStep);
        }
    }

    private PatternBuilder(java.util.List<spoon.reflect.declaration.CtElement> template) {
        this.templateTypeRef = getDeclaringTypeRef(template);
        this.patternModel = java.util.Collections.unmodifiableList(new java.util.ArrayList<>(template));
        if (template == null) {
            throw new spoon.SpoonException("Cannot create a Pattern from an null model");
        }
        this.valueConvertor = new spoon.pattern.internal.ValueConvertorImpl();
        patternNodes = spoon.pattern.internal.node.ElementNode.create(this.patternModel, patternElementToSubstRequests);
        patternQuery = new spoon.pattern.PatternBuilder.PatternQuery(getFactory().Query(), patternModel);
        if ((this.templateTypeRef) != null) {
            configurePatternParameters(( pb) -> {
                pb.parameter(spoon.pattern.PatternBuilder.TARGET_TYPE).byType(this.templateTypeRef).setValueType(spoon.reflect.reference.CtTypeReference.class);
            });
        }
    }

    private spoon.reflect.reference.CtTypeReference<?> getDeclaringTypeRef(java.util.List<spoon.reflect.declaration.CtElement> template) {
        spoon.reflect.declaration.CtType<?> type = null;
        for (spoon.reflect.declaration.CtElement ctElement : template) {
            spoon.reflect.declaration.CtType t;
            if (ctElement instanceof spoon.reflect.declaration.CtType) {
                t = ((spoon.reflect.declaration.CtType) (ctElement));
                type = mergeType(type, t);
            }
            t = ctElement.getParent(spoon.reflect.declaration.CtType.class);
            if (t != null) {
                type = mergeType(type, t);
            }
        }
        return type == null ? null : type.getReference();
    }

    private spoon.reflect.declaration.CtType<?> mergeType(spoon.reflect.declaration.CtType<?> type, spoon.reflect.declaration.CtType t) {
        if (type == null) {
            return t;
        }
        if (type == t) {
            return type;
        }
        if (type.hasParent(t)) {
            return t;
        }
        if (t.hasParent(type)) {
            return type;
        }
        throw new spoon.SpoonException("The pattern on nested types are not supported.");
    }

    spoon.pattern.internal.node.RootNode getOptionalPatternNode(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole... roles) {
        return getPatternNode(true, element, roles);
    }

    spoon.pattern.internal.node.RootNode getPatternNode(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole... roles) {
        return getPatternNode(false, element, roles);
    }

    private spoon.pattern.internal.node.RootNode getPatternNode(boolean optional, spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole... roles) {
        spoon.pattern.internal.node.RootNode node = patternElementToSubstRequests.get(element);
        for (spoon.reflect.path.CtRole role : roles) {
            if (node instanceof spoon.pattern.internal.node.ElementNode) {
                spoon.pattern.internal.node.ElementNode elementNode = ((spoon.pattern.internal.node.ElementNode) (node));
                node = elementNode.getNodeOfRole(role);
                if (node == null) {
                    if (optional) {
                        return null;
                    }
                    throw new spoon.SpoonException((("The role " + role) + " resolved to null Node"));
                }
            }else {
                if (optional) {
                    return null;
                }
                throw new spoon.SpoonException(((("The role " + role) + " can't be resolved on Node of class ") + (node.getClass())));
            }
        }
        if (node == null) {
            if (optional) {
                return null;
            }
            throw new spoon.SpoonException("There is no Node for element");
        }
        return node;
    }

    void modifyNodeOfElement(spoon.reflect.declaration.CtElement element, spoon.pattern.ConflictResolutionMode conflictMode, java.util.function.Function<spoon.pattern.internal.node.RootNode, spoon.pattern.internal.node.RootNode> elementNodeChanger) {
        spoon.pattern.internal.node.RootNode oldNode = patternElementToSubstRequests.get(element);
        spoon.pattern.internal.node.RootNode newNode = elementNodeChanger.apply(oldNode);
        if (newNode == null) {
            throw new spoon.SpoonException("Removing of Node is not supported");
        }
        handleConflict(conflictMode, oldNode, newNode, ( tobeUsedNode) -> {
            if ((patternNodes.replaceNode(oldNode, tobeUsedNode)) == false) {
                if (conflictMode == (spoon.pattern.ConflictResolutionMode.KEEP_OLD_NODE)) {
                    return;
                }
                throw new spoon.SpoonException("Old node was not found");
            }
            patternElementToSubstRequests.put(element, tobeUsedNode);
        });
    }

    void modifyNodeOfAttributeOfElement(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, spoon.pattern.ConflictResolutionMode conflictMode, java.util.function.Function<spoon.pattern.internal.node.RootNode, spoon.pattern.internal.node.RootNode> elementNodeChanger) {
        modifyNodeOfElement(element, conflictMode, ( node) -> {
            if (node instanceof spoon.pattern.internal.node.ElementNode) {
                spoon.pattern.internal.node.ElementNode elementNode = ((spoon.pattern.internal.node.ElementNode) (node));
                spoon.pattern.internal.node.RootNode oldAttrNode = elementNode.getNodeOfRole(role);
                spoon.pattern.internal.node.RootNode newAttrNode = elementNodeChanger.apply(oldAttrNode);
                if (newAttrNode == null) {
                    throw new spoon.SpoonException("Removing of Node is not supported");
                }
                handleConflict(conflictMode, oldAttrNode, newAttrNode, ( tobeUsedNode) -> {
                    elementNode.setNodeOfRole(role, tobeUsedNode);
                });
                return node;
            }
            if (conflictMode == (spoon.pattern.ConflictResolutionMode.KEEP_OLD_NODE)) {
                return node;
            }
            throw new spoon.SpoonException(("The Node of atttribute of element cannot be set because element has a Node of class: " + (node.getClass().getName())));
        });
    }

    private void handleConflict(spoon.pattern.ConflictResolutionMode conflictMode, spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode, java.util.function.Consumer<spoon.pattern.internal.node.RootNode> applyNewNode) {
        if (oldNode != newNode) {
            if (conflictMode == (spoon.pattern.ConflictResolutionMode.APPEND)) {
                if ((oldNode instanceof spoon.pattern.internal.node.ListOfNodes) == false) {
                    oldNode = new spoon.pattern.internal.node.ListOfNodes(new java.util.ArrayList<>(java.util.Arrays.asList(oldNode)));
                }
                if (newNode instanceof spoon.pattern.internal.node.ListOfNodes) {
                    ((spoon.pattern.internal.node.ListOfNodes) (oldNode)).getNodes().addAll(((spoon.pattern.internal.node.ListOfNodes) (newNode)).getNodes());
                }else {
                    ((spoon.pattern.internal.node.ListOfNodes) (oldNode)).getNodes().add(newNode);
                }
                explicitNodes.add(oldNode);
                explicitNodes.add(newNode);
                applyNewNode.accept(oldNode);
                return;
            }
            if (explicitNodes.contains(oldNode)) {
                if (conflictMode == (spoon.pattern.ConflictResolutionMode.FAIL)) {
                    throw new spoon.SpoonException(((("Can't replace once assigned Node " + oldNode) + " by a ") + newNode));
                }
                if (conflictMode == (spoon.pattern.ConflictResolutionMode.KEEP_OLD_NODE)) {
                    return;
                }
            }
            explicitNodes.remove(oldNode);
            explicitNodes.add(newNode);
            applyNewNode.accept(newNode);
        }
    }

    void setNodeOfElement(spoon.reflect.declaration.CtElement element, spoon.pattern.internal.node.RootNode node, spoon.pattern.ConflictResolutionMode conflictMode) {
        modifyNodeOfElement(element, conflictMode, ( oldNode) -> {
            return node;
        });
    }

    void setNodeOfAttributeOfElement(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, spoon.pattern.internal.node.RootNode node, spoon.pattern.ConflictResolutionMode conflictMode) {
        modifyNodeOfAttributeOfElement(element, role, conflictMode, ( oldAttrNode) -> {
            return node;
        });
    }

    boolean isInModel(spoon.reflect.declaration.CtElement element) {
        if (element != null) {
            for (spoon.reflect.declaration.CtElement patternElement : patternModel) {
                if ((element == patternElement) || (element.hasParent(patternElement))) {
                    return true;
                }
            }
        }
        return false;
    }

    public spoon.pattern.Pattern build() {
        if (built) {
            throw new spoon.SpoonException("The Pattern may be built only once");
        }
        built = true;
        patternElementToSubstRequests.clear();
        return new spoon.pattern.Pattern(getFactory(), new spoon.pattern.internal.node.ModelNode(patternNodes.getNodes())).setAddGeneratedBy(isAddGeneratedBy());
    }

    static java.util.List<? extends spoon.reflect.declaration.CtElement> bodyToStatements(spoon.reflect.code.CtStatement statementOrBlock) {
        if (statementOrBlock instanceof spoon.reflect.code.CtBlock) {
            return ((spoon.reflect.code.CtBlock<?>) (statementOrBlock)).getStatements();
        }
        return java.util.Collections.singletonList(statementOrBlock);
    }

    spoon.pattern.internal.ValueConvertor getDefaultValueConvertor() {
        return valueConvertor;
    }

    spoon.pattern.PatternBuilder setDefaultValueConvertor(spoon.pattern.internal.ValueConvertor valueConvertor) {
        this.valueConvertor = valueConvertor;
        return this;
    }

    public spoon.pattern.PatternBuilder configurePatternParameters() {
        configurePatternParameters(( pb) -> {
            pb.setConflictResolutionMode(spoon.pattern.ConflictResolutionMode.KEEP_OLD_NODE);
            pb.queryModel().filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtVariableReference.class)).forEach((spoon.reflect.reference.CtVariableReference<?> varRef) -> {
                spoon.reflect.declaration.CtVariable<?> var = varRef.getDeclaration();
                if ((var == null) || ((isInModel(var)) == false)) {
                    spoon.pattern.internal.parameter.ParameterInfo parameter = pb.parameter(varRef.getSimpleName()).getCurrentParameter();
                    pb.addSubstitutionRequest(parameter, varRef);
                }
            });
        });
        return this;
    }

    public spoon.pattern.PatternBuilder configurePatternParameters(java.util.function.Consumer<spoon.pattern.PatternParameterConfigurator> parametersBuilder) {
        spoon.pattern.PatternParameterConfigurator pb = new spoon.pattern.PatternParameterConfigurator(this, parameterInfos);
        parametersBuilder.accept(pb);
        return this;
    }

    spoon.pattern.PatternBuilder configureLocalParameters(java.util.function.Consumer<spoon.pattern.PatternParameterConfigurator> parametersBuilder) {
        spoon.pattern.PatternParameterConfigurator pb = new spoon.pattern.PatternParameterConfigurator(this, new java.util.HashMap<>());
        parametersBuilder.accept(pb);
        return this;
    }

    public spoon.pattern.PatternBuilder configureInlineStatements(java.util.function.Consumer<spoon.pattern.InlinedStatementConfigurator> consumer) {
        spoon.pattern.InlinedStatementConfigurator sb = new spoon.pattern.InlinedStatementConfigurator(this);
        consumer.accept(sb);
        return this;
    }

    static spoon.reflect.reference.CtTypeReference<?> getLocalTypeRefBySimpleName(spoon.reflect.declaration.CtType<?> templateType, java.lang.String typeSimpleName) {
        spoon.reflect.declaration.CtType<?> type = templateType.getNestedType(typeSimpleName);
        if (type != null) {
            return type.getReference();
        }
        type = templateType.getPackage().getType(typeSimpleName);
        if (type != null) {
            return type.getReference();
        }
        java.util.Set<java.lang.String> typeQNames = new java.util.HashSet<>();
        templateType.filterChildren((spoon.reflect.reference.CtTypeReference<?> ref) -> typeSimpleName.equals(ref.getSimpleName())).forEach((spoon.reflect.reference.CtTypeReference<?> ref) -> typeQNames.add(ref.getQualifiedName()));
        if ((typeQNames.size()) > 1) {
            throw new spoon.SpoonException(((("The type parameter " + typeSimpleName) + " is ambiguous. It matches multiple types: ") + typeQNames));
        }
        if ((typeQNames.size()) == 1) {
            return templateType.getFactory().Type().createReference(typeQNames.iterator().next());
        }
        return null;
    }

    spoon.pattern.internal.parameter.AbstractParameterInfo getParameterInfo(java.lang.String parameterName) {
        return parameterInfos.get(parameterName);
    }

    protected spoon.reflect.factory.Factory getFactory() {
        if ((templateTypeRef) != null) {
            return templateTypeRef.getFactory();
        }
        if ((patternModel.size()) > 0) {
            return patternModel.get(0).getFactory();
        }
        throw new spoon.SpoonException("PatternBuilder has no CtElement to provide a Factory");
    }

    private static void checkTemplateType(spoon.reflect.declaration.CtType<?> type) {
        if (type == null) {
            throw new spoon.SpoonException("Cannot create Pattern from null Template type.");
        }
        if (type.isShadow()) {
            throw new spoon.SpoonException("Cannot create Pattern from shadow Template type. Add sources of Template type into spoon model.");
        }
    }

    java.util.List<spoon.reflect.declaration.CtElement> getPatternModel() {
        return patternModel;
    }

    void forEachNodeOfParameter(spoon.pattern.internal.parameter.ParameterInfo parameter, java.util.function.Consumer<spoon.pattern.internal.node.RootNode> consumer) {
        patternNodes.forEachParameterInfo(( paramInfo, vr) -> {
            if (paramInfo == parameter) {
                consumer.accept(vr);
            }
        });
    }

    private boolean isAddGeneratedBy() {
        return addGeneratedBy;
    }

    public spoon.pattern.PatternBuilder setAddGeneratedBy(boolean addGeneratedBy) {
        this.addGeneratedBy = addGeneratedBy;
        return this;
    }

    public boolean isAutoSimplifySubstitutions() {
        return autoSimplifySubstitutions;
    }

    public spoon.pattern.PatternBuilder setAutoSimplifySubstitutions(boolean autoSimplifySubstitutions) {
        this.autoSimplifySubstitutions = autoSimplifySubstitutions;
        return this;
    }

    spoon.reflect.reference.CtTypeReference<?> getTemplateTypeRef() {
        return templateTypeRef;
    }
}


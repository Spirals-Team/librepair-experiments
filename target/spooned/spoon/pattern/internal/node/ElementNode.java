package spoon.pattern.internal.node;


public class ElementNode extends spoon.pattern.internal.node.AbstractPrimitiveMatcher {
    public static spoon.pattern.internal.node.ElementNode create(spoon.reflect.declaration.CtElement element, java.util.Map<spoon.reflect.declaration.CtElement, spoon.pattern.internal.node.RootNode> patternElementToSubstRequests) {
        spoon.Metamodel.Type mmConcept = spoon.Metamodel.getMetamodelTypeByClass(element.getClass());
        spoon.pattern.internal.node.ElementNode elementNode = new spoon.pattern.internal.node.ElementNode(mmConcept, element);
        if ((patternElementToSubstRequests.put(element, elementNode)) != null) {
            throw new spoon.SpoonException("Each pattern element can have only one implicit Node.");
        }
        for (spoon.Metamodel.Field mmField : mmConcept.getFields()) {
            if (mmField.isDerived()) {
                continue;
            }
            elementNode.setNodeOfRole(mmField.getRole(), spoon.pattern.internal.node.ElementNode.create(mmField.getContainerKind(), mmField.getValue(element), patternElementToSubstRequests));
        }
        return elementNode;
    }

    public static spoon.pattern.internal.node.ListOfNodes create(java.util.List<?> objects, java.util.Map<spoon.reflect.declaration.CtElement, spoon.pattern.internal.node.RootNode> patternElementToSubstRequests) {
        if (objects == null) {
            objects = java.util.Collections.emptyList();
        }
        return spoon.pattern.internal.node.ElementNode.listOfNodesToNode(objects.stream().map(( i) -> spoon.pattern.internal.node.ElementNode.create(i, patternElementToSubstRequests)).collect(java.util.stream.Collectors.toList()));
    }

    public static spoon.pattern.internal.node.ListOfNodes create(java.util.Set<?> templates, java.util.Map<spoon.reflect.declaration.CtElement, spoon.pattern.internal.node.RootNode> patternElementToSubstRequests) {
        if (templates == null) {
            templates = java.util.Collections.emptySet();
        }
        java.util.List<spoon.pattern.internal.node.RootNode> constantMatchers = new java.util.ArrayList<>(templates.size());
        java.util.List<spoon.pattern.internal.node.RootNode> variableMatchers = new java.util.ArrayList<>();
        for (java.lang.Object template : templates) {
            spoon.pattern.internal.node.RootNode matcher = spoon.pattern.internal.node.ElementNode.create(template, patternElementToSubstRequests);
            if (matcher instanceof spoon.pattern.internal.node.ElementNode) {
                constantMatchers.add(matcher);
            }else {
                variableMatchers.add(matcher);
            }
        }
        constantMatchers.addAll(variableMatchers);
        return spoon.pattern.internal.node.ElementNode.listOfNodesToNode(constantMatchers);
    }

    public static spoon.pattern.internal.node.ListOfNodes create(java.util.Map<java.lang.String, ?> map, java.util.Map<spoon.reflect.declaration.CtElement, spoon.pattern.internal.node.RootNode> patternElementToSubstRequests) {
        if (map == null) {
            map = java.util.Collections.emptyMap();
        }
        java.util.List<spoon.pattern.internal.node.MapEntryNode> constantMatchers = new java.util.ArrayList<>(map.size());
        java.util.List<spoon.pattern.internal.node.MapEntryNode> variableMatchers = new java.util.ArrayList<>();
        spoon.pattern.internal.matcher.Matchers last = null;
        for (java.util.Map.Entry<?, ?> entry : map.entrySet()) {
            spoon.pattern.internal.node.MapEntryNode mem = new spoon.pattern.internal.node.MapEntryNode(spoon.pattern.internal.node.ElementNode.create(entry.getKey(), patternElementToSubstRequests), spoon.pattern.internal.node.ElementNode.create(entry.getValue(), patternElementToSubstRequests));
            if ((mem.getKey()) == (entry.getKey())) {
                constantMatchers.add(mem);
            }else {
                variableMatchers.add(mem);
            }
        }
        constantMatchers.addAll(variableMatchers);
        return spoon.pattern.internal.node.ElementNode.listOfNodesToNode(constantMatchers);
    }

    private static spoon.pattern.internal.node.RootNode create(java.lang.Object object, java.util.Map<spoon.reflect.declaration.CtElement, spoon.pattern.internal.node.RootNode> patternElementToSubstRequests) {
        if (object instanceof spoon.reflect.declaration.CtElement) {
            return spoon.pattern.internal.node.ElementNode.create(((spoon.reflect.declaration.CtElement) (object)), patternElementToSubstRequests);
        }
        return new spoon.pattern.internal.node.ConstantNode<java.lang.Object>(object);
    }

    private static spoon.pattern.internal.node.RootNode create(spoon.reflect.meta.ContainerKind containerKind, java.lang.Object templates, java.util.Map<spoon.reflect.declaration.CtElement, spoon.pattern.internal.node.RootNode> patternElementToSubstRequests) {
        switch (containerKind) {
            case LIST :
                return spoon.pattern.internal.node.ElementNode.create(((java.util.List) (templates)), patternElementToSubstRequests);
            case SET :
                return spoon.pattern.internal.node.ElementNode.create(((java.util.Set) (templates)), patternElementToSubstRequests);
            case MAP :
                return spoon.pattern.internal.node.ElementNode.create(((java.util.Map) (templates)), patternElementToSubstRequests);
            case SINGLE :
                return spoon.pattern.internal.node.ElementNode.create(templates, patternElementToSubstRequests);
        }
        throw new spoon.SpoonException(("Unexpected RoleHandler containerKind: " + containerKind));
    }

    @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
    private static spoon.pattern.internal.node.ListOfNodes listOfNodesToNode(java.util.List<? extends spoon.pattern.internal.node.RootNode> nodes) {
        return new spoon.pattern.internal.node.ListOfNodes(((java.util.List) (nodes)));
    }

    private spoon.reflect.declaration.CtElement templateElement;

    private spoon.Metamodel.Type elementType;

    private java.util.Map<spoon.Metamodel.Field, spoon.pattern.internal.node.RootNode> roleToNode = new java.util.HashMap<>();

    public ElementNode(spoon.Metamodel.Type elementType, spoon.reflect.declaration.CtElement templateElement) {
        super();
        this.elementType = elementType;
        this.templateElement = templateElement;
    }

    @java.lang.Override
    public boolean replaceNode(spoon.pattern.internal.node.RootNode oldNode, spoon.pattern.internal.node.RootNode newNode) {
        for (java.util.Map.Entry<spoon.Metamodel.Field, spoon.pattern.internal.node.RootNode> e : roleToNode.entrySet()) {
            spoon.pattern.internal.node.RootNode node = e.getValue();
            if (node == oldNode) {
                e.setValue(newNode);
                return true;
            }
            if (node.replaceNode(oldNode, newNode)) {
                return true;
            }
        }
        return false;
    }

    public java.util.Map<spoon.Metamodel.Field, spoon.pattern.internal.node.RootNode> getRoleToNode() {
        return (roleToNode) == null ? java.util.Collections.emptyMap() : java.util.Collections.unmodifiableMap(roleToNode);
    }

    public spoon.pattern.internal.node.RootNode getNodeOfRole(spoon.reflect.path.CtRole attributeRole) {
        return roleToNode.get(getFieldOfRole(attributeRole));
    }

    public spoon.pattern.internal.node.RootNode setNodeOfRole(spoon.reflect.path.CtRole role, spoon.pattern.internal.node.RootNode newAttrNode) {
        return roleToNode.put(getFieldOfRole(role), newAttrNode);
    }

    public spoon.pattern.internal.node.RootNode getOrCreateNodeOfRole(spoon.reflect.path.CtRole role, java.util.Map<spoon.reflect.declaration.CtElement, spoon.pattern.internal.node.RootNode> patternElementToSubstRequests) {
        spoon.pattern.internal.node.RootNode node = getNodeOfRole(role);
        if (node == null) {
            spoon.Metamodel.Field mmField = elementType.getField(role);
            if ((mmField == null) || (mmField.isDerived())) {
                throw new spoon.SpoonException(((("The role " + role) + " doesn't exist or is derived for ") + (elementType)));
            }
            node = spoon.pattern.internal.node.ElementNode.create(mmField.getContainerKind(), null, patternElementToSubstRequests);
            setNodeOfRole(role, node);
        }
        return node;
    }

    public <T> T getValueOfRole(spoon.reflect.path.CtRole role, java.lang.Class<T> type) {
        spoon.pattern.internal.node.RootNode node = getNodeOfRole(role);
        if (node instanceof spoon.pattern.internal.node.ConstantNode) {
            spoon.pattern.internal.node.ConstantNode cn = ((spoon.pattern.internal.node.ConstantNode) (node));
            if (type.isInstance(cn.getTemplateNode())) {
                return ((T) (cn.getTemplateNode()));
            }
        }
        return null;
    }

    private spoon.Metamodel.Field getFieldOfRole(spoon.reflect.path.CtRole role) {
        spoon.Metamodel.Field mmField = elementType.getField(role);
        if (mmField == null) {
            throw new spoon.SpoonException(((("CtRole." + (role.name())) + " isn't available for ") + (elementType)));
        }
        if (mmField.isDerived()) {
            throw new spoon.SpoonException((((("CtRole." + (role.name())) + " is derived in ") + (elementType)) + " so it can't be used for matching or generating"));
        }
        return mmField;
    }

    @java.lang.Override
    public void forEachParameterInfo(java.util.function.BiConsumer<spoon.pattern.internal.parameter.ParameterInfo, spoon.pattern.internal.node.RootNode> consumer) {
        if ((roleToNode) != null) {
            for (spoon.pattern.internal.node.RootNode node : roleToNode.values()) {
                node.forEachParameterInfo(consumer);
            }
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <U> void generateTargets(spoon.pattern.internal.DefaultGenerator generator, spoon.pattern.internal.ResultHolder<U> result, spoon.support.util.ImmutableMap parameters) {
        spoon.reflect.declaration.CtElement clone = generator.getFactory().Core().create(elementType.getModelInterface());
        generateSingleNodeAttributes(generator, clone, parameters);
        generator.applyGeneratedBy(clone, generator.getGeneratedByComment(templateElement));
        result.addResult(((U) (clone)));
    }

    protected void generateSingleNodeAttributes(spoon.pattern.internal.DefaultGenerator generator, spoon.reflect.declaration.CtElement clone, spoon.support.util.ImmutableMap parameters) {
        for (java.util.Map.Entry<spoon.Metamodel.Field, spoon.pattern.internal.node.RootNode> e : getRoleToNode().entrySet()) {
            spoon.Metamodel.Field mmField = e.getKey();
            switch (mmField.getContainerKind()) {
                case SINGLE :
                    mmField.setValue(clone, generator.generateSingleTarget(e.getValue(), parameters, mmField.getValueClass()));
                    break;
                case LIST :
                    mmField.setValue(clone, generator.generateTargets(e.getValue(), parameters, mmField.getValueClass()));
                    break;
                case SET :
                    mmField.setValue(clone, new java.util.LinkedHashSet<>(generator.generateTargets(e.getValue(), parameters, mmField.getValueClass())));
                    break;
                case MAP :
                    mmField.setValue(clone, entriesToMap(generator.generateTargets(e.getValue(), parameters, java.util.Map.Entry.class)));
                    break;
            }
        }
    }

    private <T> java.util.Map<java.lang.String, T> entriesToMap(java.util.List<java.util.Map.Entry> entries) {
        java.util.Map<java.lang.String, T> map = new java.util.LinkedHashMap<>(entries.size());
        for (java.util.Map.Entry<java.lang.String, T> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    @java.lang.Override
    public spoon.support.util.ImmutableMap matchTarget(java.lang.Object target, spoon.support.util.ImmutableMap parameters) {
        if (target == null) {
            return null;
        }
        if ((target.getClass()) != (elementType.getModelClass())) {
            return null;
        }
        for (java.util.Map.Entry<spoon.Metamodel.Field, spoon.pattern.internal.node.RootNode> e : roleToNode.entrySet()) {
            parameters = matchesRole(parameters, ((spoon.reflect.declaration.CtElement) (target)), e.getKey(), e.getValue());
            if (parameters == null) {
                return null;
            }
        }
        return parameters;
    }

    protected spoon.support.util.ImmutableMap matchesRole(spoon.support.util.ImmutableMap parameters, spoon.reflect.declaration.CtElement target, spoon.Metamodel.Field mmField, spoon.pattern.internal.node.RootNode attrNode) {
        if ((spoon.pattern.internal.node.ElementNode.isMatchingRole(mmField.getRole(), elementType.getModelInterface())) == false) {
            return parameters;
        }
        spoon.pattern.internal.matcher.TobeMatched tobeMatched;
        if (attrNode instanceof spoon.pattern.internal.node.ParameterNode) {
            tobeMatched = spoon.pattern.internal.matcher.TobeMatched.create(parameters, spoon.reflect.meta.ContainerKind.SINGLE, mmField.getValue(target));
        }else {
            tobeMatched = spoon.pattern.internal.matcher.TobeMatched.create(parameters, mmField.getContainerKind(), mmField.getValue(target));
        }
        return spoon.pattern.internal.matcher.TobeMatched.getMatchedParameters(attrNode.matchTargets(tobeMatched, spoon.pattern.internal.node.RootNode.MATCH_ALL));
    }

    private static final java.util.Map<spoon.reflect.path.CtRole, java.lang.Class[]> roleToSkippedClass = new java.util.HashMap<>();

    static {
        spoon.pattern.internal.node.ElementNode.roleToSkippedClass.put(spoon.reflect.path.CtRole.COMMENT, new java.lang.Class[]{ java.lang.Object.class });
        spoon.pattern.internal.node.ElementNode.roleToSkippedClass.put(spoon.reflect.path.CtRole.POSITION, new java.lang.Class[]{ java.lang.Object.class });
        spoon.pattern.internal.node.ElementNode.roleToSkippedClass.put(spoon.reflect.path.CtRole.TYPE, new java.lang.Class[]{ spoon.reflect.reference.CtExecutableReference.class });
        spoon.pattern.internal.node.ElementNode.roleToSkippedClass.put(spoon.reflect.path.CtRole.DECLARING_TYPE, new java.lang.Class[]{ spoon.reflect.reference.CtExecutableReference.class });
    }

    private static boolean isMatchingRole(spoon.reflect.path.CtRole role, java.lang.Class<?> targetClass) {
        java.lang.Class<?>[] classes = spoon.pattern.internal.node.ElementNode.roleToSkippedClass.get(role);
        if (classes != null) {
            for (java.lang.Class<?> cls : classes) {
                if (cls.isAssignableFrom(targetClass)) {
                    return false;
                }
            }
        }
        return true;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((elementType.getName()) + ": ") + (super.toString());
    }

    public spoon.Metamodel.Type getElementType() {
        return elementType;
    }

    public void setElementType(spoon.Metamodel.Type elementType) {
        this.elementType = elementType;
    }

    @java.lang.Override
    public spoon.pattern.Quantifier getMatchingStrategy() {
        return spoon.pattern.Quantifier.POSSESSIVE;
    }

    @java.lang.Override
    public boolean isTryNextMatch(spoon.support.util.ImmutableMap parameters) {
        return false;
    }
}


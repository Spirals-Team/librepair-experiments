package spoon.pattern.internal;


public class PatternPrinter extends spoon.pattern.internal.DefaultGenerator {
    private static final spoon.reflect.factory.Factory DEFAULT_FACTORY = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());

    static {
        spoon.pattern.internal.PatternPrinter.DEFAULT_FACTORY.getEnvironment().setCommentEnabled(true);
    }

    private java.util.List<spoon.pattern.internal.PatternPrinter.ParamOnElement> params = new java.util.ArrayList<>();

    public PatternPrinter() {
        super(spoon.pattern.internal.PatternPrinter.DEFAULT_FACTORY, null);
    }

    public java.lang.String printNode(spoon.pattern.internal.node.RootNode node) {
        java.util.List<java.lang.Object> generated = generateTargets(node, ((spoon.support.util.ImmutableMap) (null)), null);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (java.lang.Object ele : generated) {
            sb.append(ele.toString()).append('\n');
        }
        return sb.toString();
    }

    @java.lang.Override
    public <T> void generateTargets(spoon.pattern.internal.node.RootNode node, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        int firstResultIdx = result.getResults().size();
        if (node instanceof spoon.pattern.internal.node.InlineNode) {
            ((spoon.pattern.internal.node.InlineNode) (node)).generateInlineTargets(this, result, parameters);
        }else {
            super.generateTargets(node, result, parameters);
        }
        T firstResult = getFirstResult(result, firstResultIdx);
        if (firstResult instanceof spoon.reflect.declaration.CtElement) {
            if (node instanceof spoon.pattern.internal.node.ElementNode) {
                spoon.pattern.internal.node.ElementNode elementNode = ((spoon.pattern.internal.node.ElementNode) (node));
                java.util.List<spoon.pattern.internal.PatternPrinter.ParamOnElement> paramsOnElement = new java.util.ArrayList<>();
                for (java.util.Map.Entry<spoon.Metamodel.Field, spoon.pattern.internal.node.RootNode> e : elementNode.getRoleToNode().entrySet()) {
                    spoon.Metamodel.Field mmField = e.getKey();
                    foreachNode(e.getValue(), ( attrNode) -> {
                        if ((attrNode instanceof spoon.pattern.internal.node.ConstantNode) || (attrNode instanceof spoon.pattern.internal.node.ElementNode)) {
                            return;
                        }
                        paramsOnElement.add(new spoon.pattern.internal.PatternPrinter.ParamOnElement(((spoon.reflect.declaration.CtElement) (firstResult)), mmField.getRole(), attrNode));
                    });
                }
                addParameterCommentTo(((spoon.reflect.declaration.CtElement) (firstResult)), paramsOnElement.toArray(new spoon.pattern.internal.PatternPrinter.ParamOnElement[paramsOnElement.size()]));
            }else
                if (node instanceof spoon.pattern.internal.node.ParameterNode) {
                    addParameterCommentTo(((spoon.reflect.declaration.CtElement) (firstResult)), new spoon.pattern.internal.PatternPrinter.ParamOnElement(((spoon.reflect.declaration.CtElement) (firstResult)), node));
                }

        }
    }

    private void foreachNode(spoon.pattern.internal.node.RootNode rootNode, java.util.function.Consumer<spoon.pattern.internal.node.RootNode> consumer) {
        if (rootNode instanceof spoon.pattern.internal.node.ListOfNodes) {
            spoon.pattern.internal.node.ListOfNodes list = ((spoon.pattern.internal.node.ListOfNodes) (rootNode));
            for (spoon.pattern.internal.node.RootNode node : list.getNodes()) {
                foreachNode(node, consumer);
            }
        }else {
            consumer.accept(rootNode);
        }
    }

    private boolean isCommentVisible(java.lang.Object obj) {
        if (obj instanceof spoon.reflect.declaration.CtElement) {
            spoon.Metamodel.Type mmType = spoon.Metamodel.getMetamodelTypeByClass(((java.lang.Class) (obj.getClass())));
            spoon.Metamodel.Field mmCommentField = mmType.getField(spoon.reflect.path.CtRole.COMMENT);
            if ((mmCommentField != null) && ((mmCommentField.isDerived()) == false)) {
                return true;
            }
        }
        return false;
    }

    private <T> T getFirstResult(spoon.pattern.internal.ResultHolder<T> result, int firstResultIdx) {
        java.util.List<T> results = result.getResults();
        if (firstResultIdx < (results.size())) {
            return results.get(firstResultIdx);
        }
        return null;
    }

    @java.lang.Override
    public <T> void getValueAs(spoon.pattern.internal.parameter.ParameterInfo parameterInfo, spoon.pattern.internal.ResultHolder<T> result, spoon.support.util.ImmutableMap parameters) {
        java.lang.Object obj = generatePatternParameterElement(parameterInfo, result.getRequiredClass());
        if (obj != null) {
            result.addResult(((T) (obj)));
        }
    }

    private void addParameterCommentTo(spoon.reflect.declaration.CtElement ele, spoon.pattern.internal.PatternPrinter.ParamOnElement... paramsOnElement) {
        for (spoon.pattern.internal.PatternPrinter.ParamOnElement paramOnElement : paramsOnElement) {
            if ((isNodeContained(paramOnElement.node)) == false) {
                params.add(paramOnElement);
            }
        }
        if ((isCommentVisible(ele)) && ((params.size()) > 0)) {
            ele.addComment(ele.getFactory().Code().createComment(getSubstitutionRequestsDescription(ele, params), spoon.reflect.code.CtComment.CommentType.BLOCK));
            params.clear();
        }
    }

    private boolean isNodeContained(spoon.pattern.internal.node.RootNode node) {
        for (spoon.pattern.internal.PatternPrinter.ParamOnElement paramOnElement : params) {
            if ((paramOnElement.node) == node) {
                return true;
            }
        }
        return false;
    }

    private <T> T generatePatternParameterElement(spoon.pattern.internal.parameter.ParameterInfo parameterInfo, java.lang.Class<T> type) {
        if (type != null) {
            if (type.isAssignableFrom(spoon.reflect.code.CtInvocation.class)) {
                return ((T) (factory.createInvocation(factory.createThisAccess(factory.Type().objectType(), true), factory.createExecutableReference().setSimpleName(parameterInfo.getName()))));
            }
            if (type.isAssignableFrom(spoon.reflect.code.CtLocalVariable.class)) {
                return ((T) (factory.createLocalVariable(factory.Type().objectType(), parameterInfo.getName(), null)));
            }
            if (type.isAssignableFrom(java.lang.String.class)) {
                return ((T) (parameterInfo.getName()));
            }
        }
        return null;
    }

    private static class ParamOnElement {
        final spoon.reflect.declaration.CtElement sourceElement;

        final spoon.pattern.internal.node.RootNode node;

        final spoon.reflect.path.CtRole role;

        ParamOnElement(spoon.reflect.declaration.CtElement sourceElement, spoon.pattern.internal.node.RootNode node) {
            this(sourceElement, null, node);
        }

        ParamOnElement(spoon.reflect.declaration.CtElement sourceElement, spoon.reflect.path.CtRole role, spoon.pattern.internal.node.RootNode node) {
            this.sourceElement = sourceElement;
            this.role = role;
            this.node = node;
        }

        @java.lang.Override
        public java.lang.String toString() {
            if ((role) == null) {
                return (((sourceElement.getClass().getName()) + ": ${") + (node.toString())) + "}";
            }else {
                return ((((sourceElement.getClass().getName()) + "/") + (role)) + ": ") + (node.toString());
            }
        }
    }

    private java.lang.String getSubstitutionRequestsDescription(spoon.reflect.declaration.CtElement ele, java.util.List<spoon.pattern.internal.PatternPrinter.ParamOnElement> requestsOnPos) {
        java.util.Map<java.lang.String, spoon.pattern.internal.PatternPrinter.ParamOnElement> reqByPath = new java.util.TreeMap<>();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (spoon.pattern.internal.PatternPrinter.ParamOnElement reqPos : requestsOnPos) {
            sb.setLength(0);
            appendPathIn(sb, reqPos.sourceElement, ele);
            if ((reqPos.role) != null) {
                sb.append("/").append(reqPos.role.getCamelCaseName());
            }
            java.lang.String path = sb.toString();
            reqByPath.put(path, reqPos);
        }
        spoon.reflect.visitor.PrinterHelper printer = new spoon.reflect.visitor.PrinterHelper(getFactory().getEnvironment());
        printer.setLineSeparator("\n");
        printer.write(spoon.pattern.internal.PatternPrinter.getElementTypeName(ele)).incTab();
        for (java.util.Map.Entry<java.lang.String, spoon.pattern.internal.PatternPrinter.ParamOnElement> e : reqByPath.entrySet()) {
            printer.writeln();
            printer.write(e.getKey()).write('/');
            printer.write(" <= ").write(e.getValue().node.toString());
        }
        return printer.toString();
    }

    private boolean appendPathIn(java.lang.StringBuilder sb, spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement parent) {
        if ((element != parent) && (element != null)) {
            spoon.reflect.path.CtRole roleInParent = element.getRoleInParent();
            if (roleInParent == null) {
                return false;
            }
            if (appendPathIn(sb, element.getParent(), parent)) {
                sb.append("/").append(spoon.pattern.internal.PatternPrinter.getElementTypeName(element.getParent()));
            }
            sb.append(".").append(roleInParent.getCamelCaseName());
            return true;
        }
        return false;
    }

    static java.lang.String getElementTypeName(spoon.reflect.declaration.CtElement element) {
        java.lang.String name = element.getClass().getSimpleName();
        if (name.endsWith("Impl")) {
            return name.substring(0, ((name.length()) - 4));
        }
        return name;
    }
}


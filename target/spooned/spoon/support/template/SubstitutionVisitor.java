package spoon.support.template;


@java.lang.Deprecated
public class SubstitutionVisitor extends spoon.reflect.visitor.CtScanner {
    private static final java.lang.Object NULL_VALUE = new java.lang.Object();

    private spoon.support.template.SubstitutionVisitor.Context context;

    private class InheritanceSustitutionScanner extends spoon.reflect.visitor.CtInheritanceScanner {
        InheritanceSustitutionScanner() {
        }

        @java.lang.Override
        public void visitCtComment(spoon.reflect.code.CtComment e) {
            e.setContent(context.substituteName(e.getContent()));
            super.visitCtComment(e);
        }

        @java.lang.SuppressWarnings("unchecked")
        @java.lang.Override
        public <T> void visitCtLiteral(spoon.reflect.code.CtLiteral<T> e) {
            java.lang.Object value = e.getValue();
            if (value instanceof java.lang.String) {
                e.setValue(((T) (context.substituteName(((java.lang.String) (value))))));
            }
            super.visitCtLiteral(e);
        }

        @java.lang.Override
        public void scanCtNamedElement(spoon.reflect.declaration.CtNamedElement element) {
            java.lang.Object value = context.getParameterValue(element.getSimpleName());
            if (value != null) {
                if (value instanceof java.lang.String) {
                    element.setSimpleName(context.substituteName(element.getSimpleName()));
                }else
                    if ((value instanceof spoon.reflect.reference.CtTypeReference) && (element instanceof spoon.reflect.declaration.CtType)) {
                        element.setSimpleName(((spoon.reflect.reference.CtTypeReference) (value)).getSimpleName());
                    }else {
                        java.util.List<? extends spoon.reflect.declaration.CtNamedElement> values = getParameterValueAsListOfClones(element.getClass(), value);
                        throw context.replace(element, values);
                    }

            }else {
                element.setSimpleName(context.substituteName(element.getSimpleName()));
            }
            super.scanCtNamedElement(element);
        }

        @java.lang.Override
        public void scanCtReference(spoon.reflect.reference.CtReference reference) {
            java.lang.Object value = context.getParameterValue(reference.getSimpleName());
            if (value != null) {
                if (reference instanceof spoon.reflect.reference.CtTypeReference) {
                    spoon.reflect.reference.CtTypeReference<?> typeReference = ((spoon.reflect.reference.CtTypeReference<?>) (reference));
                    boolean paramHasActualTypeArguments = value instanceof spoon.reflect.reference.CtTypeReference;
                    spoon.reflect.reference.CtTypeReference<?> tr = spoon.support.template.SubstitutionVisitor.getParameterValueAsTypeReference(factory, value);
                    if (paramHasActualTypeArguments) {
                        typeReference.setActualTypeArguments(tr.getActualTypeArguments());
                    }
                    typeReference.setPackage(tr.getPackage());
                    typeReference.setSimpleName(tr.getSimpleName());
                    typeReference.setDeclaringType(tr.getDeclaringType());
                }else {
                    if (value instanceof java.lang.String) {
                        reference.setSimpleName(context.substituteName(reference.getSimpleName()));
                    }else {
                        spoon.reflect.code.CtExpression<?> expr = reference.getParent(spoon.reflect.code.CtExpression.class);
                        java.util.List<spoon.reflect.code.CtCodeElement> values = getParameterValueAsListOfClones(spoon.reflect.code.CtCodeElement.class, value);
                        throw context.replace(expr, values);
                    }
                }
            }else {
                reference.setSimpleName(context.substituteName(reference.getSimpleName()));
            }
            super.scanCtReference(reference);
        }

        @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
        @java.lang.Override
        public void visitCtForEach(spoon.reflect.code.CtForEach foreach) {
            if ((foreach.getExpression()) instanceof spoon.reflect.code.CtFieldAccess) {
                spoon.reflect.code.CtFieldAccess<?> fa = ((spoon.reflect.code.CtFieldAccess<?>) (foreach.getExpression()));
                java.lang.Object value = context.getParameterValue(fa.getVariable().getSimpleName());
                if (value != null) {
                    spoon.support.template.SubstitutionVisitor.Context localContext = createContext();
                    java.util.List<spoon.reflect.code.CtExpression> list = getParameterValueAsListOfClones(spoon.reflect.code.CtExpression.class, value);
                    spoon.reflect.code.CtBlock<?> foreachBlock = ((spoon.reflect.code.CtBlock<?>) (foreach.getBody()));
                    java.lang.String newParamName = foreach.getVariable().getSimpleName();
                    java.util.List<spoon.reflect.code.CtStatement> newStatements = new java.util.ArrayList<>();
                    for (spoon.reflect.code.CtExpression element : list) {
                        localContext.putParameter(newParamName, element);
                        for (spoon.reflect.code.CtStatement st : foreachBlock.getStatements()) {
                            newStatements.addAll(localContext.substitute(st.clone()));
                        }
                    }
                    throw context.replace(foreach, newStatements);
                }
            }
            super.visitCtForEach(foreach);
        }

        @java.lang.Override
        public <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead) {
            visitFieldAccess(fieldRead);
        }

        @java.lang.Override
        public <T> void visitCtFieldWrite(spoon.reflect.code.CtFieldWrite<T> fieldWrite) {
            visitFieldAccess(fieldWrite);
        }

        @java.lang.SuppressWarnings({ "rawtypes" })
        private <T> void visitFieldAccess(final spoon.reflect.code.CtFieldAccess<T> fieldAccess) {
            spoon.reflect.reference.CtFieldReference<?> ref = fieldAccess.getVariable();
            if ("length".equals(ref.getSimpleName())) {
                if ((fieldAccess.getTarget()) instanceof spoon.reflect.code.CtFieldAccess) {
                    ref = ((spoon.reflect.code.CtFieldAccess<?>) (fieldAccess.getTarget())).getVariable();
                    java.lang.Object value = context.getParameterValue(ref.getSimpleName());
                    if (value != null) {
                        java.util.List<java.lang.Object> list = getParameterValueAsNewList(value);
                        throw context.replace(fieldAccess, ((spoon.reflect.code.CtExpression) (fieldAccess.getFactory().Code().createLiteral(list.size()))));
                    }
                }
            }
            java.lang.Object v = context.getParameterValue(ref.getSimpleName());
            if (v != null) {
                java.lang.Object value = getParameterValueAtIndex(java.lang.Object.class, v, spoon.support.template.Parameters.getIndex(fieldAccess));
                spoon.reflect.code.CtExpression toReplace = fieldAccess;
                if ((fieldAccess.getParent()) instanceof spoon.reflect.code.CtArrayAccess) {
                    toReplace = ((spoon.reflect.code.CtExpression) (fieldAccess.getParent()));
                }
                if (!(value instanceof spoon.template.TemplateParameter)) {
                    if (value instanceof java.lang.Class) {
                        throw context.replace(toReplace, factory.Code().createClassAccess(factory.Type().createReference(((java.lang.Class<?>) (value)).getName())));
                    }else
                        if (value instanceof java.lang.Enum) {
                            spoon.reflect.reference.CtTypeReference<?> enumType = factory.Type().createReference(value.getClass());
                            spoon.reflect.code.CtFieldRead<?> enumValueAccess = ((spoon.reflect.code.CtFieldRead<?>) (factory.Code().createVariableRead(factory.Field().createReference(enumType, enumType, ((java.lang.Enum<?>) (value)).name()), true)));
                            enumValueAccess.setTarget(factory.Code().createTypeAccess(enumType));
                            throw context.replace(toReplace, enumValueAccess);
                        }else
                            if ((value != null) && (value.getClass().isArray())) {
                                throw context.replace(toReplace, factory.Code().createLiteralArray(((java.lang.Object[]) (value))));
                            }else
                                if ((fieldAccess == toReplace) && (value instanceof java.lang.String)) {
                                }else {
                                    throw context.replace(toReplace, factory.Code().createLiteral(value));
                                }



                }else {
                    throw context.replace(toReplace, ((spoon.reflect.declaration.CtElement) (value)));
                }
            }
        }

        @java.lang.SuppressWarnings("unchecked")
        @java.lang.Override
        public <T> void visitCtInvocation(spoon.reflect.code.CtInvocation<T> invocation) {
            if (invocation.getExecutable().isOverriding(S)) {
                spoon.reflect.code.CtFieldAccess<?> fa = null;
                if ((invocation.getTarget()) instanceof spoon.reflect.code.CtFieldAccess) {
                    fa = ((spoon.reflect.code.CtFieldAccess<?>) (invocation.getTarget()));
                }
                if (((invocation.getTarget()) instanceof spoon.reflect.code.CtArrayAccess) && ((((spoon.reflect.code.CtArrayAccess<?, spoon.reflect.code.CtExpression<?>>) (invocation.getTarget())).getTarget()) instanceof spoon.reflect.code.CtFieldAccess)) {
                    fa = ((spoon.reflect.code.CtFieldAccess<?>) (((spoon.reflect.code.CtArrayAccess<?, spoon.reflect.code.CtExpression<?>>) (invocation.getTarget())).getTarget()));
                }
                if ((fa != null) && (((fa.getTarget()) == null) || ((fa.getTarget()) instanceof spoon.reflect.code.CtThisAccess))) {
                    spoon.reflect.code.CtCodeElement r = getParameterValueAtIndex(spoon.reflect.code.CtCodeElement.class, context.getParameterValue(fa.getVariable().getSimpleName()), spoon.support.template.Parameters.getIndex(fa));
                    java.util.List<spoon.reflect.code.CtCodeElement> subst = null;
                    if (r != null) {
                        subst = createContext().substitute(r);
                    }else {
                        subst = java.util.Collections.<spoon.reflect.code.CtCodeElement>emptyList();
                    }
                    throw context.replace(invocation, subst);
                }
            }
            super.visitCtInvocation(invocation);
        }
    }

    private spoon.reflect.factory.Factory factory;

    private spoon.support.template.SubstitutionVisitor.InheritanceSustitutionScanner inheritanceScanner;

    private spoon.reflect.reference.CtExecutableReference<?> S;

    private boolean addGeneratedBy = false;

    @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
    public SubstitutionVisitor(spoon.reflect.factory.Factory f, spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        this(f, spoon.support.template.Parameters.getTemplateParametersAsMap(f, targetType, template));
        if (template instanceof spoon.template.AbstractTemplate) {
            addGeneratedBy(((spoon.template.AbstractTemplate) (template)).isAddGeneratedBy());
        }
    }

    public SubstitutionVisitor(spoon.reflect.factory.Factory f, java.util.Map<java.lang.String, java.lang.Object> templateParameters) {
        this.inheritanceScanner = new spoon.support.template.SubstitutionVisitor.InheritanceSustitutionScanner();
        this.factory = f;
        S = factory.Executable().createReference(factory.Type().createReference(spoon.template.TemplateParameter.class), factory.Type().createTypeParameterReference("T"), "S");
        this.context = new spoon.support.template.SubstitutionVisitor.Context(null).putParameters(templateParameters);
    }

    public boolean isAddGeneratedBy() {
        return addGeneratedBy;
    }

    public spoon.support.template.SubstitutionVisitor addGeneratedBy(boolean addGeneratedBy) {
        this.addGeneratedBy = addGeneratedBy;
        return this;
    }

    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        try {
            inheritanceScanner.scan(element);
            super.scan(element);
        } catch (spoon.support.template.DoNotFurtherTemplateThisElement ignore) {
            if (element != (ignore.skipped)) {
                throw ignore;
            }
        }
    }

    public <E extends spoon.reflect.declaration.CtElement> java.util.List<E> substitute(E element) {
        final java.util.Map<spoon.reflect.declaration.CtElement, java.lang.String> elementToGeneratedByComment = (addGeneratedBy) ? new java.util.IdentityHashMap<spoon.reflect.declaration.CtElement, java.lang.String>() : null;
        if (addGeneratedBy) {
            final spoon.reflect.visitor.CtInheritanceScanner internalScanner = new spoon.reflect.visitor.CtInheritanceScanner() {
                public void scanCtTypeMember(spoon.reflect.declaration.CtTypeMember typeMeber) {
                    elementToGeneratedByComment.put(typeMeber, spoon.support.template.SubstitutionVisitor.getGeneratedByComment(typeMeber));
                }
            };
            new spoon.reflect.visitor.CtScanner() {
                @java.lang.Override
                public void scan(spoon.reflect.declaration.CtElement p_element) {
                    internalScanner.scan(p_element);
                    super.scan(p_element);
                }
            }.scan(element);
        }
        java.util.List<E> result = createContext().substitute(element);
        if (addGeneratedBy) {
            spoon.support.template.SubstitutionVisitor.applyGeneratedByComments(elementToGeneratedByComment);
        }
        return result;
    }

    private static java.lang.String getGeneratedByComment(spoon.reflect.declaration.CtElement ele) {
        spoon.reflect.cu.SourcePosition pos = ele.getPosition();
        if (pos != null) {
            spoon.reflect.cu.CompilationUnit cu = pos.getCompilationUnit();
            if (cu != null) {
                spoon.reflect.declaration.CtType<?> mainType = cu.getMainType();
                if (mainType != null) {
                    java.lang.StringBuilder result = new java.lang.StringBuilder();
                    result.append("Generated by ");
                    result.append(mainType.getQualifiedName());
                    spoon.support.template.SubstitutionVisitor.appendInnerTypedElements(result, mainType, ele);
                    result.append('(');
                    result.append(mainType.getSimpleName());
                    result.append(".java:");
                    result.append(pos.getLine());
                    result.append(')');
                    return result.toString();
                }
            }
        }
        return null;
    }

    private static void appendInnerTypedElements(java.lang.StringBuilder result, spoon.reflect.declaration.CtType<?> mainType, spoon.reflect.declaration.CtElement ele) {
        spoon.reflect.declaration.CtTypeMember typeMember = spoon.support.template.SubstitutionVisitor.getFirst(ele, spoon.reflect.declaration.CtTypeMember.class);
        if ((typeMember != null) && (typeMember != mainType)) {
            if (typeMember.isParentInitialized()) {
                spoon.support.template.SubstitutionVisitor.appendInnerTypedElements(result, mainType, typeMember.getParent());
            }
            if (typeMember instanceof spoon.reflect.declaration.CtType) {
                result.append('$');
            }else {
                result.append('#');
            }
            result.append(typeMember.getSimpleName());
        }
    }

    private static void applyGeneratedByComments(java.util.Map<spoon.reflect.declaration.CtElement, java.lang.String> elementToGeneratedByComment) {
        for (java.util.Map.Entry<spoon.reflect.declaration.CtElement, java.lang.String> e : elementToGeneratedByComment.entrySet()) {
            spoon.support.template.SubstitutionVisitor.addGeneratedByComment(e.getKey(), e.getValue());
        }
    }

    private static void addGeneratedByComment(spoon.reflect.declaration.CtElement ele, java.lang.String generatedBy) {
        if (generatedBy == null) {
            return;
        }
        java.lang.String EOL = java.lang.System.getProperty("line.separator");
        spoon.reflect.code.CtComment comment = spoon.support.template.SubstitutionVisitor.getJavaDoc(ele);
        java.lang.String content = comment.getContent();
        if ((content.trim().length()) > 0) {
            content += EOL + EOL;
        }
        content += generatedBy;
        comment.setContent(content);
    }

    private static spoon.reflect.code.CtComment getJavaDoc(spoon.reflect.declaration.CtElement ele) {
        for (spoon.reflect.code.CtComment comment : ele.getComments()) {
            if ((comment.getCommentType()) == (spoon.reflect.code.CtComment.CommentType.JAVADOC)) {
                return comment;
            }
        }
        spoon.reflect.code.CtComment c = ele.getFactory().Code().createComment("", spoon.reflect.code.CtComment.CommentType.JAVADOC);
        ele.addComment(c);
        return c;
    }

    @java.lang.SuppressWarnings("unchecked")
    private static <T extends spoon.reflect.declaration.CtElement> T getFirst(spoon.reflect.declaration.CtElement ele, java.lang.Class<T> clazz) {
        if (ele != null) {
            if (clazz.isAssignableFrom(ele.getClass())) {
                return ((T) (ele));
            }
            if (ele.isParentInitialized()) {
                return spoon.support.template.SubstitutionVisitor.getFirst(ele.getParent(), clazz);
            }
        }
        return null;
    }

    @java.lang.SuppressWarnings("unchecked")
    private <T> java.util.List<T> getParameterValueAsListOfClones(java.lang.Class<T> itemClass, java.lang.Object parameterValue) {
        java.util.List<java.lang.Object> list = getParameterValueAsNewList(parameterValue);
        for (int i = 0; i < (list.size()); i++) {
            list.set(i, getParameterValueAsClass(itemClass, list.get(i)));
        }
        return ((java.util.List<T>) (list));
    }

    private java.util.List<java.lang.Object> getParameterValueAsNewList(java.lang.Object parameterValue) {
        java.util.List<java.lang.Object> list = new java.util.ArrayList<>();
        if (parameterValue != null) {
            if (parameterValue instanceof java.lang.Object[]) {
                for (java.lang.Object item : ((java.lang.Object[]) (parameterValue))) {
                    list.add(item);
                }
            }else
                if (parameterValue instanceof java.lang.Iterable) {
                    for (java.lang.Object item : ((java.lang.Iterable<java.lang.Object>) (parameterValue))) {
                        list.add(item);
                    }
                }else {
                    if ((parameterValue != null) && (parameterValue != (spoon.support.template.SubstitutionVisitor.NULL_VALUE))) {
                        list.add(parameterValue);
                    }
                }

        }
        return list;
    }

    @java.lang.SuppressWarnings("unchecked")
    private <T> T getParameterValueAsClass(java.lang.Class<T> itemClass, java.lang.Object parameterValue) {
        if ((parameterValue == null) || (parameterValue == (spoon.support.template.SubstitutionVisitor.NULL_VALUE))) {
            return null;
        }
        if (itemClass.isInstance(parameterValue)) {
            if (spoon.reflect.declaration.CtElement.class.isAssignableFrom(itemClass)) {
                parameterValue = ((spoon.reflect.declaration.CtElement) (parameterValue)).clone();
            }
            return ((T) (parameterValue));
        }
        if (itemClass.isAssignableFrom(spoon.reflect.code.CtCodeElement.class)) {
            if (parameterValue instanceof spoon.reflect.reference.CtTypeReference) {
                spoon.reflect.reference.CtTypeReference<?> tr = ((spoon.reflect.reference.CtTypeReference<?>) (parameterValue));
                return ((T) (factory.Code().createClassAccess(tr)));
            }
            if (parameterValue instanceof java.lang.String) {
                return ((T) (factory.Code().createLiteral(((java.lang.String) (parameterValue)))));
            }
        }
        throw new spoon.SpoonException(((("Parameter value has unexpected class: " + (parameterValue.getClass().getName())) + ". Expected class is: ") + (itemClass.getName())));
    }

    private java.lang.String getParameterValueAsString(java.lang.Object parameterValue) {
        if (parameterValue == null) {
            return null;
        }
        if (parameterValue instanceof java.lang.String) {
            return ((java.lang.String) (parameterValue));
        }else
            if (parameterValue instanceof spoon.reflect.declaration.CtNamedElement) {
                return ((spoon.reflect.declaration.CtNamedElement) (parameterValue)).getSimpleName();
            }else
                if (parameterValue instanceof spoon.reflect.reference.CtReference) {
                    return ((spoon.reflect.reference.CtReference) (parameterValue)).getSimpleName();
                }else
                    if (parameterValue instanceof java.lang.Class) {
                        return ((java.lang.Class) (parameterValue)).getSimpleName();
                    }else
                        if (parameterValue instanceof spoon.reflect.code.CtInvocation) {
                            return spoon.support.template.SubstitutionVisitor.getShortSignatureForJavadoc(((spoon.reflect.code.CtInvocation<?>) (parameterValue)).getExecutable());
                        }else
                            if (parameterValue instanceof spoon.reflect.reference.CtExecutableReference) {
                                return spoon.support.template.SubstitutionVisitor.getShortSignatureForJavadoc(((spoon.reflect.reference.CtExecutableReference<?>) (parameterValue)));
                            }else
                                if (parameterValue instanceof spoon.reflect.declaration.CtExecutable) {
                                    return spoon.support.template.SubstitutionVisitor.getShortSignatureForJavadoc(((spoon.reflect.declaration.CtExecutable<?>) (parameterValue)).getReference());
                                }else
                                    if (parameterValue instanceof spoon.reflect.code.CtLiteral) {
                                        java.lang.Object val = ((spoon.reflect.code.CtLiteral<java.lang.Object>) (parameterValue)).getValue();
                                        return val == null ? null : val.toString();
                                    }







        throw new spoon.SpoonException((("Parameter value has unexpected class: " + (parameterValue.getClass().getName())) + ", whose conversion to String is not supported"));
    }

    private static java.lang.String getShortSignatureForJavadoc(spoon.reflect.reference.CtExecutableReference<?> ref) {
        spoon.support.visitor.SignaturePrinter sp = new spoon.support.visitor.SignaturePrinter();
        sp.writeNameAndParameters(ref);
        return ((ref.getDeclaringType().getSimpleName()) + (spoon.reflect.declaration.CtExecutable.EXECUTABLE_SEPARATOR)) + (sp.getSignature());
    }

    @java.lang.SuppressWarnings("unchecked")
    private static <T> spoon.reflect.reference.CtTypeReference<T> getParameterValueAsTypeReference(spoon.reflect.factory.Factory factory, java.lang.Object parameterValue) {
        if ((parameterValue == null) || (parameterValue == (spoon.support.template.SubstitutionVisitor.NULL_VALUE))) {
            throw new spoon.SpoonException("The null value is not valid substitution for CtTypeReference");
        }
        if (parameterValue instanceof java.lang.Class) {
            return factory.Type().createReference(((java.lang.Class<T>) (parameterValue)));
        }else
            if (parameterValue instanceof spoon.reflect.reference.CtTypeReference) {
                return ((spoon.reflect.reference.CtTypeReference<T>) (parameterValue)).clone();
            }else
                if (parameterValue instanceof spoon.reflect.declaration.CtType) {
                    return ((spoon.reflect.declaration.CtType<T>) (parameterValue)).getReference();
                }else
                    if (parameterValue instanceof java.lang.String) {
                        return factory.Type().createReference(((java.lang.String) (parameterValue)));
                    }else {
                        throw new java.lang.RuntimeException("unsupported reference substitution");
                    }



    }

    private <T> T getParameterValueAtIndex(java.lang.Class<T> itemClass, java.lang.Object parameterValue, java.lang.Integer index) {
        if (index != null) {
            java.util.List<java.lang.Object> list = getParameterValueAsNewList(parameterValue);
            if ((list.size()) > index) {
                return getParameterValueAsClass(itemClass, list.get(index));
            }
            return null;
        }
        return getParameterValueAsClass(itemClass, parameterValue);
    }

    private spoon.support.template.SubstitutionVisitor.Context createContext() {
        return new spoon.support.template.SubstitutionVisitor.Context(this.context);
    }

    private class Context {
        private final spoon.support.template.SubstitutionVisitor.Context parentContext;

        private spoon.reflect.declaration.CtElement input;

        private java.util.List<spoon.reflect.declaration.CtElement> result;

        private java.util.Map<java.lang.String, java.lang.Object> parameterNameToValue;

        private Context(spoon.support.template.SubstitutionVisitor.Context parent) {
            this.parentContext = parent;
        }

        private spoon.support.template.SubstitutionVisitor.Context putParameter(java.lang.String name, java.lang.Object value) {
            if ((parameterNameToValue) == null) {
                parameterNameToValue = new java.util.LinkedHashMap<>();
            }
            if (value == null) {
                value = spoon.support.template.SubstitutionVisitor.NULL_VALUE;
            }
            parameterNameToValue.put(name, value);
            return this;
        }

        private spoon.support.template.SubstitutionVisitor.Context putParameters(java.util.Map<java.lang.String, java.lang.Object> parameters) {
            if ((parameters != null) && ((parameters.isEmpty()) == false)) {
                for (java.util.Map.Entry<java.lang.String, java.lang.Object> e : parameters.entrySet()) {
                    putParameter(e.getKey(), e.getValue());
                }
            }
            return this;
        }

        private java.lang.Object getParameterValue(java.lang.String parameterName) {
            if ((parameterNameToValue) != null) {
                java.lang.Object value = parameterNameToValue.get(parameterName);
                if (value != null) {
                    return value;
                }
            }
            if ((parentContext) != null) {
                return parentContext.getParameterValue(parameterName);
            }
            return null;
        }

        private <E extends spoon.reflect.declaration.CtElement> spoon.support.template.DoNotFurtherTemplateThisElement replace(spoon.reflect.declaration.CtElement toBeReplaced, E replacement) {
            return replace(toBeReplaced, (replacement == null ? java.util.Collections.<E>emptyList() : java.util.Collections.<E>singletonList(replacement)));
        }

        @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
        private <E extends spoon.reflect.declaration.CtElement> spoon.support.template.DoNotFurtherTemplateThisElement replace(spoon.reflect.declaration.CtElement toBeReplaced, java.util.List<E> replacements) {
            spoon.reflect.declaration.CtElement parentOfReplacement = (toBeReplaced.isParentInitialized()) ? toBeReplaced.getParent() : null;
            if (parentOfReplacement instanceof spoon.reflect.code.CtReturn) {
                if (((replacements.size()) == 1) && ((replacements.get(0)) instanceof spoon.reflect.code.CtBlock)) {
                    replacements = ((java.util.List) (((spoon.reflect.code.CtBlock) (replacements.get(0))).getStatements()));
                }
                if ((replacements.size()) > 1) {
                    return context._replace(parentOfReplacement, replacements);
                }else
                    if (((replacements.size()) == 1) && (((replacements.get(0)) instanceof spoon.reflect.code.CtExpression) == false)) {
                        return context._replace(parentOfReplacement, replacements);
                    }

            }
            return context._replace(toBeReplaced, replacements);
        }

        @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
        private <E extends spoon.reflect.declaration.CtElement> spoon.support.template.DoNotFurtherTemplateThisElement _replace(spoon.reflect.declaration.CtElement toBeReplaced, java.util.List<E> replacements) {
            if ((input) == toBeReplaced) {
                if ((result) != null) {
                    throw new spoon.SpoonException("Illegal state. SubstitutionVisitor.Context#result was already replaced!");
                }
                result = ((java.util.List) (replacements));
            }else {
                toBeReplaced.replace(replacements);
            }
            return new spoon.support.template.DoNotFurtherTemplateThisElement(toBeReplaced);
        }

        @java.lang.SuppressWarnings("unchecked")
        private <E extends spoon.reflect.declaration.CtElement> java.util.List<E> substitute(E element) {
            if ((input) != null) {
                throw new spoon.SpoonException("Illegal state. SubstitutionVisitor.Context#input is already set.");
            }
            input = element;
            result = null;
            if ((context) != (parentContext)) {
                throw new spoon.SpoonException("Illegal state. Context != parentContext");
            }
            try {
                context = this;
                scan(element);
                if ((result) != null) {
                    return ((java.util.List<E>) (result));
                }
                return java.util.Collections.<E>singletonList(((E) (input)));
            } finally {
                context = this.parentContext;
                input = null;
            }
        }

        private java.lang.String substituteName(java.lang.String name) {
            if (name == null) {
                return null;
            }
            if ((parameterNameToValue) != null) {
                for (java.util.Map.Entry<java.lang.String, java.lang.Object> e : parameterNameToValue.entrySet()) {
                    java.lang.String pname = e.getKey();
                    if (name.contains(pname)) {
                        java.lang.String value = getParameterValueAsString(e.getValue());
                        name = name.replace(pname, value);
                    }
                }
            }
            if ((parentContext) != null) {
                name = parentContext.substituteName(name);
            }
            return name;
        }
    }
}


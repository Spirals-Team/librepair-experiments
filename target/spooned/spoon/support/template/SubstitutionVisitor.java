package spoon.support.template;


/**
 * This visitor implements the substitution engine of Spoon templates.
 */
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

        /**
         * Replaces parameters in element names (even if detected as a
         * substring).
         */
        @java.lang.Override
        public void scanCtNamedElement(spoon.reflect.declaration.CtNamedElement element) {
            java.lang.Object value = context.getParameterValue(element.getSimpleName());
            if (value != null) {
                // the parameter value is a type reference and the element is a type. Replace name of the type
                // this named element has to be replaced by zero one or more other elements
                if (value instanceof java.lang.String) {
                    // the parameter value is a String. It is the case of substitution of the name only
                    // replace parameter (sub)strings in simplename
                    element.setSimpleName(context.substituteName(element.getSimpleName()));
                }// the parameter value is a type reference and the element is a type. Replace name of the type
                // this named element has to be replaced by zero one or more other elements
                else
                    if ((value instanceof spoon.reflect.reference.CtTypeReference) && (element instanceof spoon.reflect.declaration.CtType)) {
                        element.setSimpleName(((spoon.reflect.reference.CtTypeReference) (value)).getSimpleName());
                    }else {
                        java.util.List<? extends spoon.reflect.declaration.CtNamedElement> values = getParameterValueAsListOfClones(element.getClass(), value);
                        throw context.replace(element, values);
                    }

            }else {
                // try to substitute substring of the name
                element.setSimpleName(context.substituteName(element.getSimpleName()));
            }
            super.scanCtNamedElement(element);
        }

        @java.lang.Override
        public void scanCtReference(spoon.reflect.reference.CtReference reference) {
            java.lang.Object value = context.getParameterValue(reference.getSimpleName());
            if (value != null) {
                if (reference instanceof spoon.reflect.reference.CtTypeReference) {
                    /**
                     * Replaces type parameters and references to the template type with
                     * references to the target type.
                     */
                    // replace type parameters
                    spoon.reflect.reference.CtTypeReference<?> typeReference = ((spoon.reflect.reference.CtTypeReference<?>) (reference));
                    boolean paramHasActualTypeArguments = value instanceof spoon.reflect.reference.CtTypeReference;
                    spoon.reflect.reference.CtTypeReference<?> tr = spoon.support.template.SubstitutionVisitor.getParameterValueAsTypeReference(factory, value);
                    if (paramHasActualTypeArguments) {
                        // the origin parameter has actual type arguments, apply them
                        typeReference.setActualTypeArguments(tr.getActualTypeArguments());
                    }
                    typeReference.setPackage(tr.getPackage());
                    typeReference.setSimpleName(tr.getSimpleName());
                    typeReference.setDeclaringType(tr.getDeclaringType());
                }else {
                    if (value instanceof java.lang.String) {
                        // the parameter value is a String. It is the case of substitution of the name only
                        // replace parameter (sub)strings in simplename
                        reference.setSimpleName(context.substituteName(reference.getSimpleName()));
                    }else {
                        // we have to replace the expression by another expression or statement
                        spoon.reflect.code.CtExpression<?> expr = reference.getParent(spoon.reflect.code.CtExpression.class);
                        java.util.List<spoon.reflect.code.CtCodeElement> values = getParameterValueAsListOfClones(spoon.reflect.code.CtCodeElement.class, value);
                        // TODO we might check consistency here, but we need to know context of the expr. Is it Statement or Expression?
                        // replace expression with statements or expressions
                        throw context.replace(expr, values);
                    }
                }
            }else {
                // try to substitute substring of the name
                reference.setSimpleName(context.substituteName(reference.getSimpleName()));
            }
            super.scanCtReference(reference);
        }

        /**
         * statically inline foreach
         */
        @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
        @java.lang.Override
        public void visitCtForEach(spoon.reflect.code.CtForEach foreach) {
            if ((foreach.getExpression()) instanceof spoon.reflect.code.CtFieldAccess) {
                spoon.reflect.code.CtFieldAccess<?> fa = ((spoon.reflect.code.CtFieldAccess<?>) (foreach.getExpression()));
                java.lang.Object value = context.getParameterValue(fa.getVariable().getSimpleName());
                if (value != null) {
                    // create local context which holds local substitution parameter
                    spoon.support.template.SubstitutionVisitor.Context localContext = createContext();
                    java.util.List<spoon.reflect.code.CtExpression> list = getParameterValueAsListOfClones(spoon.reflect.code.CtExpression.class, value);
                    // ForEach always contains CtBlock. In some cases it is implicit.
                    spoon.reflect.code.CtBlock<?> foreachBlock = ((spoon.reflect.code.CtBlock<?>) (foreach.getBody()));
                    java.lang.String newParamName = foreach.getVariable().getSimpleName();
                    java.util.List<spoon.reflect.code.CtStatement> newStatements = new java.util.ArrayList<>();
                    for (spoon.reflect.code.CtExpression element : list) {
                        // for each item of foreach expression copy foreach body and substitute it is using local context containing new parameter
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
                        // the items of this list are not cloned
                        java.util.List<java.lang.Object> list = getParameterValueAsNewList(value);
                        throw context.replace(fieldAccess, ((spoon.reflect.code.CtExpression) (fieldAccess.getFactory().Code().createLiteral(list.size()))));
                    }
                }
            }
            // Object v = context.getParameterValue(Parameters.getParameterName(ref));
            java.lang.Object v = context.getParameterValue(ref.getSimpleName());
            if (v != null) {
                // replace direct field parameter accesses
                java.lang.Object value = getParameterValueAtIndex(java.lang.Object.class, v, spoon.support.template.Parameters.getIndex(fieldAccess));
                spoon.reflect.code.CtExpression toReplace = fieldAccess;
                if ((fieldAccess.getParent()) instanceof spoon.reflect.code.CtArrayAccess) {
                    toReplace = ((spoon.reflect.code.CtExpression) (fieldAccess.getParent()));
                }
                if (!(value instanceof spoon.template.TemplateParameter)) {
                    /* If the value is type String, then it is ambiguous request, because:
                    A) sometime client wants to replace parameter field access by String literal

                    @Parameter
                    String field = "x"

                    System.printLn(field) //is substitutes as: System.printLn("x")

                    but in the case of local variables it already behaves like this
                    {
                    		int field;
                    		System.printLn(field) //is substitutes as: System.printLn(x)
                    }

                    B) sometime client wants to keep field access and just substitute field name

                    @Parameter("field")
                    String fieldName = "x"

                    System.printLn(field) //is substitutes as: System.printLn(x)

                    ----------------------

                    The case B is more clear and is compatible with substitution of name of local variable, method name, etc.
                    And case A can be easily modeled using this clear code

                    @Parameter
                    String field = "x"
                    System.printLn("field") //is substitutes as: System.printLn("x")
                    System.printLn(field) //is substitutes as: System.printLn("x") because the parameter `field` is constructed with literal value
                     */
                    // New implementation always replaces the name of the accessed field
                    // so do nothing here. The string substitution is handled by #scanCtReference
                    if (value instanceof java.lang.Class) {
                        throw context.replace(toReplace, factory.Code().createClassAccess(factory.Type().createReference(((java.lang.Class<?>) (value)).getName())));
                    }/* If the value is type String, then it is ambiguous request, because:
                    A) sometime client wants to replace parameter field access by String literal

                    @Parameter
                    String field = "x"

                    System.printLn(field) //is substitutes as: System.printLn("x")

                    but in the case of local variables it already behaves like this
                    {
                    		int field;
                    		System.printLn(field) //is substitutes as: System.printLn(x)
                    }

                    B) sometime client wants to keep field access and just substitute field name

                    @Parameter("field")
                    String fieldName = "x"

                    System.printLn(field) //is substitutes as: System.printLn(x)

                    ----------------------

                    The case B is more clear and is compatible with substitution of name of local variable, method name, etc.
                    And case A can be easily modeled using this clear code

                    @Parameter
                    String field = "x"
                    System.printLn("field") //is substitutes as: System.printLn("x")
                    System.printLn(field) //is substitutes as: System.printLn("x") because the parameter `field` is constructed with literal value
                     */
                    // New implementation always replaces the name of the accessed field
                    // so do nothing here. The string substitution is handled by #scanCtReference
                    else
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

        /**
         * Replaces _xx_.S().
         */
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

    /**
     * Creates new substitution visitor based on instance of Template,
     * which defines template model and template parameters
     *
     * @param f
     * 		the factory
     * @param targetType
     * 		the target type of the substitution (can be null)
     * @param template
     * 		the template that holds the parameter values
     */
    @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
    public SubstitutionVisitor(spoon.reflect.factory.Factory f, spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        this(f, spoon.support.template.Parameters.getTemplateParametersAsMap(f, targetType, template));
        if (template instanceof spoon.template.AbstractTemplate) {
            addGeneratedBy(((spoon.template.AbstractTemplate) (template)).isAddGeneratedBy());
        }
    }

    /**
     * Creates new substitution visitor
     * with substitution model (doesn't have to implement {@link Template}) type
     * and the substitution parameters (doesn't have to be bound to {@link TemplateParameter} or {@link Parameter}).
     *
     * @param f
     * 		the factory
     * @param templateParameters
     * 		the parameter names and values which will be used during substitution
     */
    public SubstitutionVisitor(spoon.reflect.factory.Factory f, java.util.Map<java.lang.String, java.lang.Object> templateParameters) {
        this.inheritanceScanner = new spoon.support.template.SubstitutionVisitor.InheritanceSustitutionScanner();
        this.factory = f;
        S = factory.Executable().createReference(factory.Type().createReference(spoon.template.TemplateParameter.class), factory.Type().createTypeParameterReference("T"), "S");
        this.context = new spoon.support.template.SubstitutionVisitor.Context(null).putParameters(templateParameters);
    }

    /**
     *
     *
     * @return true if the template engine ({@link SubstitutionVisitor}) adds Generated by ... comments into generated code
     */
    public boolean isAddGeneratedBy() {
        return addGeneratedBy;
    }

    /**
     *
     *
     * @param addGeneratedBy
     * 		if true the template engine ({@link SubstitutionVisitor}) will add Generated by ... comments into generated code
     */
    public spoon.support.template.SubstitutionVisitor addGeneratedBy(boolean addGeneratedBy) {
        this.addGeneratedBy = addGeneratedBy;
        return this;
    }

    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        try {
            // doing the templating of this element
            inheritanceScanner.scan(element);
            // and then scan the children for doing the templating as well in them
            super.scan(element);
        } catch (spoon.support.template.DoNotFurtherTemplateThisElement ignore) {
            if (element != (ignore.skipped)) {
                // we have to skip more
                throw ignore;
            }
        }
    }

    /**
     * Substitutes all template parameters of element and returns substituted element.
     *
     * @param element
     * 		to be substituted model
     * @return substituted model
     */
    public <E extends spoon.reflect.declaration.CtElement> java.util.List<E> substitute(E element) {
        final java.util.Map<spoon.reflect.declaration.CtElement, java.lang.String> elementToGeneratedByComment = (addGeneratedBy) ? new java.util.IdentityHashMap<spoon.reflect.declaration.CtElement, java.lang.String>() : null;
        if (addGeneratedBy) {
            /* collect 'generated by' comments for each type member of the substituted element, before the substitution is done,
            so we know the origin names of the members.
             */
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
            // add generated by comments after substitution, otherwise they would be substituted in comments too.
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

    /**
     * 1) Converts `parameterValue` to List using these rules
     * <ul>
     * <li>Array is converted to List .
     * <li>{@link Iterable} is converted to List .
     * <li>Single item is add to list.
     * </ul>
     * 2) assures that each list item has expected type `itemClass`
     * 3) if itemClass is sub type of CtElement then clones it
     *
     * @param itemClass
     * 		the type of the items of resulting list.
     * 		If some item cannot be converted to the itemClass then {@link SpoonException} is thrown
     * @param parameterValue
     * 		a value of an template parameter
     * @return list where each item is assured to be of type itemClass
     */
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

    /**
     * 1) Assures that parameterValue has expected type `itemClass`
     * 2) if itemClass is sub type of CtElement then clones parameterValue
     *
     * @param itemClass
     * 		required return class
     * @param parameterValue
     * 		a value of an template parameter
     * @return parameterValue cast (in future potentially converted) to itemClass
     */
    @java.lang.SuppressWarnings("unchecked")
    private <T> T getParameterValueAsClass(java.lang.Class<T> itemClass, java.lang.Object parameterValue) {
        if ((parameterValue == null) || (parameterValue == (spoon.support.template.SubstitutionVisitor.NULL_VALUE))) {
            return null;
        }
        if (itemClass.isInstance(parameterValue)) {
            if (spoon.reflect.declaration.CtElement.class.isAssignableFrom(itemClass)) {
                /* the cloning is defined by itemClass and not by parameterValue,
                because there are cases when we do not want to clone parameterValue.
                In this case itemClass == Object.class
                 */
                parameterValue = ((spoon.reflect.declaration.CtElement) (parameterValue)).clone();
            }
            return ((T) (parameterValue));
        }
        if (itemClass.isAssignableFrom(spoon.reflect.code.CtCodeElement.class)) {
            if (parameterValue instanceof spoon.reflect.reference.CtTypeReference) {
                // convert type reference into code element as class access
                spoon.reflect.reference.CtTypeReference<?> tr = ((spoon.reflect.reference.CtTypeReference<?>) (parameterValue));
                return ((T) (factory.Code().createClassAccess(tr)));
            }
            if (parameterValue instanceof java.lang.String) {
                // convert String to code element as Literal
                return ((T) (factory.Code().createLiteral(((java.lang.String) (parameterValue)))));
            }
        }
        throw new spoon.SpoonException(((("Parameter value has unexpected class: " + (parameterValue.getClass().getName())) + ". Expected class is: ") + (itemClass.getName())));
    }

    /**
     *
     *
     * @param parameterValue
     * 		a value of an template parameter
     * @return parameter value converted to String
     */
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

    /* return the typical Javadoc style link Foo#method(). The class name is not fully qualified. */
    private static java.lang.String getShortSignatureForJavadoc(spoon.reflect.reference.CtExecutableReference<?> ref) {
        spoon.support.visitor.SignaturePrinter sp = new spoon.support.visitor.SignaturePrinter();
        sp.writeNameAndParameters(ref);
        return ((ref.getDeclaringType().getSimpleName()) + (spoon.reflect.declaration.CtExecutable.EXECUTABLE_SEPARATOR)) + (sp.getSignature());
    }

    /**
     * Converts `parameterValue` to {@link CtTypeReference}.
     * It assures that new reference is returned.
     * If parameterValue is already a {@link CtTypeReference}, then it is cloned.
     *
     * @param factory
     * 		a Spoon factory used to create CtTypeReference instance - if needed
     * @param parameterValue
     * 		a value of an template parameter
     * @return parameter value converted to {@link CtTypeReference}
     */
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

    /**
     * 1a) If index is null, then parameterValue must be a single item, which will be converted to itemClass
     * 1b) If index is a number, then parameterValue is converted to List, the index-th item is converted to itemClass
     * 2) if itemClass is sub type of CtElement then returned element is a clone
     *
     * @param itemClass
     * 		required return class
     * @param parameterValue
     * 		a value of an template parameter
     * @param index
     * 		index of item from the list, or null if item is not expected to be a list
     * @return parameterValue (optionally item from the list) cast (in future potentially converted) to itemClass
     */
    private <T> T getParameterValueAtIndex(java.lang.Class<T> itemClass, java.lang.Object parameterValue, java.lang.Integer index) {
        if (index != null) {
            // convert to list, but do not clone
            java.util.List<java.lang.Object> list = getParameterValueAsNewList(parameterValue);
            if ((list.size()) > index) {
                // convert and clone the returned item
                return getParameterValueAsClass(itemClass, list.get(index));
            }
            return null;
        }
        // convert and clone the returned item
        return getParameterValueAsClass(itemClass, parameterValue);
    }

    private spoon.support.template.SubstitutionVisitor.Context createContext() {
        // by default each new context has same input like parent and modifies same collection like parent context
        return new spoon.support.template.SubstitutionVisitor.Context(this.context);
    }

    private class Context {
        private final spoon.support.template.SubstitutionVisitor.Context parentContext;

        /**
         * represents root element, which is target of the substitution.
         * It can be substituted too.
         */
        private spoon.reflect.declaration.CtElement input;

        /**
         * represents replacement of the `input`.
         * it is null if input was not replaced
         */
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
                // replace return too, because return expression cannot contain CtBlock
                if ((replacements.size()) > 1) {
                    // replace return too, because return expression cannot contain more statements
                    return context._replace(parentOfReplacement, replacements);
                }// replace return too, because return expression cannot contain CtBlock
                else
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


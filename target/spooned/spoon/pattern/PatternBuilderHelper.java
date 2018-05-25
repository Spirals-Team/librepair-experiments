package spoon.pattern;


@spoon.support.Experimental
public class PatternBuilderHelper {
    private final spoon.reflect.declaration.CtType<?> patternType;

    private spoon.reflect.declaration.CtType<?> clonedPatternType;

    private java.util.List<spoon.reflect.declaration.CtElement> elements = null;

    public PatternBuilderHelper(spoon.reflect.declaration.CtType<?> templateTemplate) {
        this.patternType = templateTemplate;
    }

    private spoon.reflect.declaration.CtType<?> getClonedPatternType() {
        if ((clonedPatternType) == null) {
            clonedPatternType = patternType.clone();
            if (patternType.isParentInitialized()) {
                clonedPatternType.setParent(patternType.getParent());
            }
        }
        return clonedPatternType;
    }

    public spoon.pattern.PatternBuilderHelper setTypeMember(java.lang.String typeMemberName) {
        setTypeMember(( tm) -> typeMemberName.equals(tm.getSimpleName()));
        return this;
    }

    private spoon.pattern.PatternBuilderHelper setTypeMember(spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtTypeMember> filter) {
        setElements(getByFilter(filter));
        return this;
    }

    private java.util.List<spoon.reflect.declaration.CtElement> getClonedElements() {
        if ((elements) == null) {
            throw new spoon.SpoonException("Template model is not defined yet");
        }
        for (java.util.ListIterator<spoon.reflect.declaration.CtElement> iter = elements.listIterator(); iter.hasNext();) {
            spoon.reflect.declaration.CtElement ele = iter.next();
            if ((ele.getRoleInParent()) != null) {
                iter.set(ele.clone());
            }
        }
        return elements;
    }

    public spoon.pattern.PatternBuilderHelper setBodyOfMethod(java.lang.String methodName) {
        setBodyOfMethod(( tm) -> methodName.equals(tm.getSimpleName()));
        return this;
    }

    private void setBodyOfMethod(spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtMethod<?>> filter) {
        spoon.reflect.code.CtBlock<?> body = getOneByFilter(filter).getBody();
        setElements(body.getStatements());
    }

    public void setReturnExpressionOfMethod(java.lang.String methodName) {
        setReturnExpressionOfMethod(( tm) -> methodName.equals(tm.getSimpleName()));
    }

    private void setReturnExpressionOfMethod(spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtMethod<?>> filter) {
        spoon.reflect.declaration.CtMethod<?> method = getOneByFilter(filter);
        spoon.reflect.code.CtBlock<?> body = method.getBody();
        if ((body.getStatements().size()) != 1) {
            throw new spoon.SpoonException(((("The body of " + (method.getSignature())) + " must contain exactly one statement. But there is:\n") + (body.toString())));
        }
        spoon.reflect.code.CtStatement firstStatement = body.getStatements().get(0);
        if ((firstStatement instanceof spoon.reflect.code.CtReturn<?>) == false) {
            throw new spoon.SpoonException(((("The body of " + (method.getSignature())) + " must contain return statement. But there is:\n") + (body.toString())));
        }
        elements.add(((spoon.reflect.code.CtReturn<?>) (firstStatement)).getReturnedExpression());
    }

    private <T extends spoon.reflect.declaration.CtElement> java.util.List<T> getByFilter(spoon.reflect.visitor.Filter<T> filter) {
        java.util.List<T> elements = patternType.filterChildren(filter).list();
        if ((elements == null) || (elements.isEmpty())) {
            throw new spoon.SpoonException(("Element not found in " + (patternType.getShortRepresentation())));
        }
        return elements;
    }

    private <T extends spoon.reflect.declaration.CtElement> T getOneByFilter(spoon.reflect.visitor.Filter<T> filter) {
        java.util.List<T> elements = getByFilter(filter);
        if ((elements.size()) != 1) {
            throw new spoon.SpoonException(("Only one element must be selected, but there are: " + elements));
        }
        return elements.get(0);
    }

    public spoon.pattern.PatternBuilderHelper keepTypeMembers(spoon.reflect.visitor.Filter<? super spoon.reflect.declaration.CtElement> filter) {
        for (spoon.reflect.declaration.CtTypeMember ctTypeMember : new java.util.ArrayList<>(getClonedPatternType().getTypeMembers())) {
            if ((filter.matches(ctTypeMember)) == false) {
                ctTypeMember.delete();
            }
        }
        return this;
    }

    public spoon.pattern.PatternBuilderHelper removeSuperClass() {
        getClonedPatternType().setSuperclass(null);
        return this;
    }

    public java.util.List<spoon.reflect.declaration.CtElement> getPatternElements() {
        return elements;
    }

    @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
    private void setElements(java.util.List<? extends spoon.reflect.declaration.CtElement> template) {
        this.elements = ((java.util.List) (template));
    }
}


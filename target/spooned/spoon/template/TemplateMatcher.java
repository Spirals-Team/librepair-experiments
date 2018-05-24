package spoon.template;


public class TemplateMatcher implements spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement> {
    private final spoon.pattern.Pattern pattern;

    private final spoon.reflect.declaration.CtElement templateRoot;

    private spoon.support.util.ImmutableMap matches;

    public TemplateMatcher(spoon.reflect.declaration.CtElement templateRoot) {
        this(templateRoot, templateRoot.getParent(spoon.reflect.declaration.CtClass.class));
    }

    public TemplateMatcher(spoon.reflect.declaration.CtElement templateRoot, spoon.reflect.declaration.CtClass<?> templateType) {
        this.pattern = spoon.template.TemplateBuilder.createPattern(templateRoot, templateType, null).build();
        this.templateRoot = templateRoot;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.declaration.CtElement element) {
        spoon.pattern.internal.node.ModelNode patternModel = pattern.getModelValueResolver();
        if (element == (templateRoot)) {
            return false;
        }
        matches = spoon.pattern.internal.matcher.TobeMatched.getMatchedParameters(patternModel.matchAllWith(spoon.pattern.internal.matcher.TobeMatched.create(new spoon.support.util.ImmutableMapImpl(), spoon.reflect.meta.ContainerKind.SINGLE, element)));
        return (matches) != null;
    }

    public spoon.support.util.ImmutableMap getMatches() {
        return matches;
    }

    public <T extends spoon.reflect.declaration.CtElement> java.util.List<T> find(final spoon.reflect.declaration.CtElement targetRoot) {
        return targetRoot.filterChildren(this).list();
    }

    public void forEachMatch(spoon.reflect.declaration.CtElement rootElement, spoon.reflect.visitor.chain.CtConsumer<spoon.pattern.Match> consumer) {
        pattern.forEachMatch(rootElement, consumer);
    }
}


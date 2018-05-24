package spoon.pattern;


@spoon.support.Experimental
public class Pattern {
    private spoon.pattern.internal.node.ModelNode modelValueResolver;

    private boolean addGeneratedBy = false;

    private final spoon.reflect.factory.Factory factory;

    Pattern(spoon.reflect.factory.Factory factory, spoon.pattern.internal.node.ModelNode modelValueResolver) {
        this.modelValueResolver = modelValueResolver;
        this.factory = factory;
    }

    public spoon.pattern.internal.node.ModelNode getModelValueResolver() {
        return modelValueResolver;
    }

    public java.util.Map<java.lang.String, spoon.pattern.internal.parameter.ParameterInfo> getParameterInfos() {
        java.util.Map<java.lang.String, spoon.pattern.internal.parameter.ParameterInfo> parameters = new java.util.HashMap<>();
        modelValueResolver.forEachParameterInfo(( parameter, valueResolver) -> {
            spoon.pattern.internal.parameter.ParameterInfo existingParameter = parameters.get(parameter.getName());
            if (existingParameter != null) {
                if (existingParameter == parameter) {
                    return;
                }
                throw new spoon.SpoonException(("There is already a parameter: " + (parameter.getName())));
            }
            parameters.put(parameter.getName(), parameter);
        });
        return java.util.Collections.unmodifiableMap(parameters);
    }

    public spoon.pattern.Generator generator() {
        return new spoon.pattern.internal.DefaultGenerator(factory, this).setAddGeneratedBy(addGeneratedBy);
    }

    public void forEachMatch(java.lang.Object input, spoon.reflect.visitor.chain.CtConsumer<spoon.pattern.Match> consumer) {
        if (input == null) {
            return;
        }
        if (input.getClass().isArray()) {
            input = java.util.Arrays.asList(((java.lang.Object[]) (input)));
        }
        spoon.pattern.internal.matcher.MatchingScanner scanner = new spoon.pattern.internal.matcher.MatchingScanner(modelValueResolver, consumer);
        spoon.support.util.ImmutableMap parameters = new spoon.support.util.ImmutableMapImpl();
        if (input instanceof java.util.Collection<?>) {
            scanner.scan(null, ((java.util.Collection<spoon.reflect.declaration.CtElement>) (input)));
        }else
            if (input instanceof java.util.Map) {
                scanner.scan(null, ((java.util.Map<java.lang.String, ?>) (input)));
            }else {
                scanner.scan(null, ((spoon.reflect.declaration.CtElement) (input)));
            }

    }

    public java.util.List<spoon.pattern.Match> getMatches(spoon.reflect.declaration.CtElement root) {
        java.util.List<spoon.pattern.Match> matches = new java.util.ArrayList<>();
        forEachMatch(root, ( match) -> {
            matches.add(match);
        });
        return matches;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return modelValueResolver.toString();
    }

    boolean isAddGeneratedBy() {
        return addGeneratedBy;
    }

    spoon.pattern.Pattern setAddGeneratedBy(boolean addGeneratedBy) {
        this.addGeneratedBy = addGeneratedBy;
        return this;
    }
}


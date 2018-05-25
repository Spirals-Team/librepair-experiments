package spoon.pattern.internal.node;


abstract class AbstractPrimitiveMatcher extends spoon.pattern.internal.node.AbstractRepeatableMatcher implements spoon.pattern.internal.node.PrimitiveMatcher {
    protected AbstractPrimitiveMatcher() {
    }

    @java.lang.Override
    public spoon.pattern.internal.matcher.TobeMatched matchAllWith(spoon.pattern.internal.matcher.TobeMatched tobeMatched) {
        return tobeMatched.matchNext(( target, parameters) -> {
            return matchTarget(target, tobeMatched.getParameters());
        });
    }
}


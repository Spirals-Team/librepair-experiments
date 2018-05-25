package spoon.pattern.internal.matcher;


public class MatchingScanner extends spoon.reflect.visitor.EarlyTerminatingScanner<java.lang.Void> {
    private final spoon.pattern.internal.node.ListOfNodes pattern;

    private spoon.reflect.visitor.chain.CtConsumer<? super spoon.pattern.Match> matchConsumer;

    public MatchingScanner(spoon.pattern.internal.node.ListOfNodes pattern, spoon.reflect.visitor.chain.CtConsumer<? super spoon.pattern.Match> matchConsumer) {
        this.pattern = pattern;
        this.matchConsumer = matchConsumer;
    }

    @java.lang.Override
    public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
        if ((searchMatchInList(role, java.util.Collections.singletonList(element), false)) == 0) {
            super.scan(role, element);
        }
    }

    @java.lang.Override
    public void scan(spoon.reflect.path.CtRole role, java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
        if (elements == null) {
            return;
        }
        if (elements instanceof java.util.List<?>) {
            searchMatchInList(role, ((java.util.List<? extends spoon.reflect.declaration.CtElement>) (elements)), true);
        }else
            if (elements instanceof java.util.Set<?>) {
                searchMatchInSet(role, ((java.util.Set<? extends spoon.reflect.declaration.CtElement>) (elements)));
            }else {
                throw new spoon.SpoonException(("Unexpected Collection type " + (elements.getClass())));
            }

    }

    private int searchMatchInList(spoon.reflect.path.CtRole role, java.util.List<? extends spoon.reflect.declaration.CtElement> list, boolean scanChildren) {
        int matchCount = 0;
        if ((list.size()) > 0) {
            spoon.pattern.internal.matcher.TobeMatched tobeMatched = spoon.pattern.internal.matcher.TobeMatched.create(new spoon.support.util.ImmutableMapImpl(), spoon.reflect.meta.ContainerKind.LIST, list);
            while (tobeMatched.hasTargets()) {
                spoon.pattern.internal.matcher.TobeMatched nextTobeMatched = pattern.matchAllWith(tobeMatched);
                if (nextTobeMatched != null) {
                    java.util.List<?> matchedTargets = tobeMatched.getMatchedTargets(nextTobeMatched);
                    if ((matchedTargets.size()) > 0) {
                        matchCount++;
                        matchConsumer.accept(new spoon.pattern.Match(matchedTargets, nextTobeMatched.getParameters()));
                        tobeMatched = nextTobeMatched.copyAndSetParams(new spoon.support.util.ImmutableMapImpl());
                        continue;
                    }
                }
                if (scanChildren) {
                    super.scan(role, tobeMatched.getTargets().get(0));
                }
                tobeMatched = tobeMatched.removeTarget(0);
            } 
        }
        return matchCount;
    }

    private void searchMatchInSet(spoon.reflect.path.CtRole role, java.util.Set<? extends spoon.reflect.declaration.CtElement> set) {
        if ((set.size()) > 0) {
            spoon.pattern.internal.matcher.TobeMatched tobeMatched = spoon.pattern.internal.matcher.TobeMatched.create(new spoon.support.util.ImmutableMapImpl(), spoon.reflect.meta.ContainerKind.SET, set);
            while (tobeMatched.hasTargets()) {
                spoon.pattern.internal.matcher.TobeMatched nextTobeMatched = pattern.matchAllWith(tobeMatched);
                if (nextTobeMatched != null) {
                    java.util.List<?> matchedTargets = tobeMatched.getMatchedTargets(nextTobeMatched);
                    if ((matchedTargets.size()) > 0) {
                        matchConsumer.accept(new spoon.pattern.Match(matchedTargets, nextTobeMatched.getParameters()));
                        tobeMatched = nextTobeMatched;
                        continue;
                    }
                }
                break;
            } 
            for (java.lang.Object object : tobeMatched.getTargets()) {
                super.scan(role, object);
            }
        }
    }

    @java.lang.Override
    public void scan(spoon.reflect.path.CtRole role, java.util.Map<java.lang.String, ? extends spoon.reflect.declaration.CtElement> elements) {
        super.scan(role, elements);
    }
}


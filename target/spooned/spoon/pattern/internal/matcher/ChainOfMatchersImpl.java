package spoon.pattern.internal.matcher;


public class ChainOfMatchersImpl implements spoon.pattern.internal.matcher.Matchers {
    private final spoon.pattern.internal.node.RootNode firstMatcher;

    private final spoon.pattern.internal.matcher.Matchers next;

    public static spoon.pattern.internal.matcher.Matchers create(java.util.List<? extends spoon.pattern.internal.node.RootNode> items, spoon.pattern.internal.matcher.Matchers next) {
        return spoon.pattern.internal.matcher.ChainOfMatchersImpl.createFromList(next, items, 0);
    }

    private static spoon.pattern.internal.matcher.Matchers createFromList(spoon.pattern.internal.matcher.Matchers next, java.util.List<? extends spoon.pattern.internal.node.RootNode> items, int idx) {
        spoon.pattern.internal.node.RootNode matcher;
        while (true) {
            if (idx >= (items.size())) {
                return next;
            }
            matcher = items.get(idx);
            if (matcher != null) {
                break;
            }
            idx++;
        } 
        return new spoon.pattern.internal.matcher.ChainOfMatchersImpl(matcher, spoon.pattern.internal.matcher.ChainOfMatchersImpl.createFromList(next, items, (idx + 1)));
    }

    private ChainOfMatchersImpl(spoon.pattern.internal.node.RootNode firstMatcher, spoon.pattern.internal.matcher.Matchers next) {
        super();
        if (firstMatcher == null) {
            throw new spoon.SpoonException("The firstMatcher Node MUST NOT be null");
        }
        this.firstMatcher = firstMatcher;
        if (next == null) {
            throw new spoon.SpoonException("The next Node MUST NOT be null");
        }
        this.next = next;
    }

    @java.lang.Override
    public spoon.pattern.internal.matcher.TobeMatched matchAllWith(spoon.pattern.internal.matcher.TobeMatched targets) {
        return firstMatcher.matchTargets(targets, next);
    }
}


package spoon.pattern.internal.node;


abstract class AbstractRepeatableMatcher extends spoon.pattern.internal.node.AbstractNode implements spoon.pattern.internal.node.RepeatableMatcher {
    @java.lang.Override
    public spoon.pattern.internal.matcher.TobeMatched matchTargets(spoon.pattern.internal.matcher.TobeMatched targets, spoon.pattern.internal.matcher.Matchers next) {
        if ((isRepeatable()) == false) {
            boolean isMandatory = isMandatory(targets.getParameters());
            spoon.pattern.internal.matcher.TobeMatched tmp = matchAllWith(targets);
            if (tmp == null) {
                if (isMandatory) {
                    return null;
                }
            }else {
                targets = tmp;
            }
            return next.matchAllWith(targets);
        }
        while (isMandatory(targets.getParameters())) {
            spoon.pattern.internal.matcher.TobeMatched tmp = matchAllWith(targets);
            if (tmp == null) {
                return null;
            }
            if ((isTryNextMatch(tmp.getParameters())) == false) {
                return next.matchAllWith(tmp);
            }
            targets = tmp;
        } 
        return matchOptionalTargets(targets, next);
    }

    private spoon.pattern.internal.matcher.TobeMatched matchOptionalTargets(spoon.pattern.internal.matcher.TobeMatched targets, spoon.pattern.internal.matcher.Matchers next) {
        if ((isTryNextMatch(targets.getParameters())) == false) {
            return next.matchAllWith(targets);
        }
        switch (getMatchingStrategy()) {
            case GREEDY :
                {
                    {
                        spoon.pattern.internal.matcher.TobeMatched match = matchAllWith(targets);
                        if (match != null) {
                            match = matchOptionalTargets(match, next);
                            if (match != null) {
                                return match;
                            }
                        }
                    }
                    return next.matchAllWith(targets);
                }
            case RELUCTANT :
                {
                    {
                        spoon.pattern.internal.matcher.TobeMatched match = next.matchAllWith(targets);
                        if (match != null) {
                            return match;
                        }
                    }
                    spoon.pattern.internal.matcher.TobeMatched match = matchAllWith(targets);
                    if (match == null) {
                        return null;
                    }
                    return matchOptionalTargets(match, next);
                }
            case POSSESSIVE :
                while (isTryNextMatch(targets.getParameters())) {
                    spoon.pattern.internal.matcher.TobeMatched tmp = matchAllWith(targets);
                    if (tmp == null) {
                        if (isMandatory(targets.getParameters())) {
                            return null;
                        }
                        break;
                    }
                    targets = tmp;
                } 
                return next.matchAllWith(targets);
        }
        throw new spoon.SpoonException(("Unsupported quantifier " + (getMatchingStrategy())));
    }
}


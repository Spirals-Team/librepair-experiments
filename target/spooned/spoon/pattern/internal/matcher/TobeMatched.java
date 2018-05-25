package spoon.pattern.internal.matcher;


public class TobeMatched {
    private final spoon.support.util.ImmutableMap parameters;

    private final java.util.List<?> targets;

    private final boolean ordered;

    public static spoon.pattern.internal.matcher.TobeMatched create(spoon.support.util.ImmutableMap parameters, spoon.reflect.meta.ContainerKind containerKind, java.lang.Object target) {
        switch (containerKind) {
            case LIST :
                return new spoon.pattern.internal.matcher.TobeMatched(parameters, ((java.util.List<java.lang.Object>) (target)), true);
            case SET :
                return new spoon.pattern.internal.matcher.TobeMatched(parameters, ((java.util.Set<java.lang.Object>) (target)), false);
            case MAP :
                return new spoon.pattern.internal.matcher.TobeMatched(parameters, ((java.util.Map<java.lang.String, java.lang.Object>) (target)));
            case SINGLE :
                return new spoon.pattern.internal.matcher.TobeMatched(parameters, target);
        }
        throw new spoon.SpoonException(("Unexpected RoleHandler containerKind: " + containerKind));
    }

    private TobeMatched(spoon.support.util.ImmutableMap parameters, java.lang.Object target) {
        this.parameters = parameters;
        this.targets = java.util.Collections.singletonList(target);
        this.ordered = true;
    }

    private TobeMatched(spoon.support.util.ImmutableMap parameters, java.util.Collection<?> targets, boolean ordered) {
        this.parameters = parameters;
        this.targets = (targets == null) ? java.util.Collections.emptyList() : java.util.Collections.unmodifiableList(new java.util.ArrayList<>(targets));
        this.ordered = ordered;
    }

    private TobeMatched(spoon.support.util.ImmutableMap parameters, java.util.Map<java.lang.String, ?> targets) {
        this.parameters = parameters;
        this.targets = (targets == null) ? java.util.Collections.emptyList() : java.util.Collections.unmodifiableList(new java.util.ArrayList<>(targets.entrySet()));
        this.ordered = false;
    }

    private TobeMatched(spoon.support.util.ImmutableMap parameters, java.util.List<?> targets, boolean ordered, int tobeRemovedIndex) {
        this.parameters = parameters;
        this.targets = new java.util.ArrayList<>(targets);
        if (tobeRemovedIndex >= 0) {
            this.targets.remove(tobeRemovedIndex);
        }
        this.ordered = ordered;
    }

    public spoon.support.util.ImmutableMap getParameters() {
        return parameters;
    }

    public java.util.List<?> getTargets() {
        return targets;
    }

    public java.util.List<?> getMatchedTargets(spoon.pattern.internal.matcher.TobeMatched tobeMatchedTargets) {
        int nrOfMatches = (getTargets().size()) - (tobeMatchedTargets.getTargets().size());
        if (nrOfMatches >= 0) {
            if (nrOfMatches == 0) {
                return java.util.Collections.emptyList();
            }
            java.util.List<java.lang.Object> matched = new java.util.ArrayList(nrOfMatches);
            for (java.lang.Object target : getTargets()) {
                if (containsSame(tobeMatchedTargets.getTargets(), target)) {
                    continue;
                }
                matched.add(target);
            }
            if ((matched.size()) == nrOfMatches) {
                return matched;
            }
        }
        throw new spoon.SpoonException("Invalid input `originTobeMatched`");
    }

    private boolean containsSame(java.util.List<?> items, java.lang.Object object) {
        for (java.lang.Object item : items) {
            if (item == object) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTargets() {
        return (targets.size()) > 0;
    }

    public spoon.pattern.internal.matcher.TobeMatched copyAndSetParams(spoon.support.util.ImmutableMap newParams) {
        if ((parameters) == newParams) {
            return this;
        }
        return new spoon.pattern.internal.matcher.TobeMatched(newParams, targets, ordered, (-1));
    }

    public spoon.pattern.internal.matcher.TobeMatched matchNext(java.util.function.BiFunction<java.lang.Object, spoon.support.util.ImmutableMap, spoon.support.util.ImmutableMap> matcher) {
        if (targets.isEmpty()) {
            return null;
        }
        if (ordered) {
            spoon.support.util.ImmutableMap parameters = matcher.apply(targets.get(0), getParameters());
            if (parameters != null) {
                return removeTarget(parameters, 0);
            }
            return null;
        }else {
            int idxOfMatch = 0;
            while (idxOfMatch < (targets.size())) {
                spoon.support.util.ImmutableMap parameters = matcher.apply(targets.get(idxOfMatch), getParameters());
                if (parameters != null) {
                    return removeTarget(parameters, idxOfMatch);
                }
                idxOfMatch++;
            } 
            return null;
        }
    }

    public static spoon.support.util.ImmutableMap getMatchedParameters(spoon.pattern.internal.matcher.TobeMatched remainingMatch) {
        return remainingMatch == null ? null : remainingMatch.getParameters();
    }

    public spoon.pattern.internal.matcher.TobeMatched removeTarget(int idxOfTobeRemovedTarget) {
        return removeTarget(parameters, idxOfTobeRemovedTarget);
    }

    public spoon.pattern.internal.matcher.TobeMatched removeTarget(spoon.support.util.ImmutableMap parameters, int idxOfTobeRemovedTarget) {
        return new spoon.pattern.internal.matcher.TobeMatched(parameters, targets, ordered, idxOfTobeRemovedTarget);
    }

    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Parameters:\n----------------\n").append(parameters).append("\nTobe matched target elements\n-----------------------\n");
        for (int i = 0; i < (targets.size()); i++) {
            sb.append('\n').append((i + 1)).append('/').append(targets.size()).append(": ").append(targets.get(i));
        }
        return sb.toString();
    }
}


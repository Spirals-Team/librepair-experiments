package spoon.pattern;


public class Match {
    private final java.util.List<? extends java.lang.Object> matchingElements;

    private final spoon.support.util.ImmutableMap parameters;

    public Match(java.util.List<? extends java.lang.Object> matches, spoon.support.util.ImmutableMap parameters) {
        this.parameters = parameters;
        this.matchingElements = matches;
    }

    public java.util.List<spoon.reflect.declaration.CtElement> getMatchingElements() {
        return getMatchingElements(spoon.reflect.declaration.CtElement.class);
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> java.util.List<T> getMatchingElements(java.lang.Class<T> clazz) {
        for (java.lang.Object object : matchingElements) {
            if ((object != null) && ((clazz.isInstance(object)) == false)) {
                throw new spoon.SpoonException(((("Match contains a " + (object.getClass())) + " which cannot be cast to ") + clazz));
            }
        }
        return ((java.util.List<T>) (matchingElements));
    }

    public spoon.reflect.declaration.CtElement getMatchingElement() {
        return getMatchingElement(spoon.reflect.declaration.CtElement.class, true);
    }

    public <T> T getMatchingElement(java.lang.Class<T> clazz) {
        return getMatchingElement(clazz, true);
    }

    private <T> T getMatchingElement(java.lang.Class<T> clazz, boolean failIfMany) {
        if (matchingElements.isEmpty()) {
            return null;
        }
        if (failIfMany && ((matchingElements.size()) != 1)) {
            throw new spoon.SpoonException("There is more then one match");
        }
        java.lang.Object object = matchingElements.get(0);
        if ((object != null) && ((clazz.isInstance(object)) == false)) {
            throw new spoon.SpoonException(((("Match contains a " + (object.getClass())) + " which cannot be cast to ") + clazz));
        }
        return clazz.cast(object);
    }

    public spoon.support.util.ImmutableMap getParameters() {
        return parameters;
    }

    public java.util.Map<java.lang.String, java.lang.Object> getParametersMap() {
        return parameters.asMap();
    }

    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{\n");
        sb.append(parameters.toString());
        sb.append("\n}\n----------");
        for (int i = 0; i < (matchingElements.size()); i++) {
            sb.append("\n").append((i + 1)).append(") ").append(matchingElements.get(i));
        }
        return sb.toString();
    }
}


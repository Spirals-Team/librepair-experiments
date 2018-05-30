package spoon.reflect.visitor.filter;


public class RegexFilter<E extends spoon.reflect.declaration.CtElement> implements spoon.reflect.visitor.Filter<E> {
    private final java.util.regex.Pattern regex;

    public RegexFilter(java.lang.String regex) {
        if (regex == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.regex = java.util.regex.Pattern.compile(regex);
    }

    public boolean matches(E element) {
        java.util.regex.Matcher m = regex.matcher(element.toString());
        return m.matches();
    }

    public java.lang.Class<spoon.reflect.declaration.CtElement> getType() {
        return spoon.reflect.declaration.CtElement.class;
    }
}


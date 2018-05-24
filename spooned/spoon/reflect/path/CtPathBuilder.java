package spoon.reflect.path;


public class CtPathBuilder {
    private java.util.List<spoon.reflect.path.impl.CtPathElement> elements = new java.util.LinkedList<>();

    public spoon.reflect.path.CtPathBuilder name(java.lang.String name, java.lang.String[]... args) {
        spoon.reflect.path.impl.CtNamedPathElement e = new spoon.reflect.path.impl.CtNamedPathElement(name);
        if (args != null) {
            for (java.lang.String[] arg : args) {
                e.addArgument(arg[0], arg[1]);
            }
        }
        elements.add(e);
        return this;
    }

    public spoon.reflect.path.CtPathBuilder wildcard() {
        return name(spoon.reflect.path.impl.CtNamedPathElement.WILDCARD);
    }

    public spoon.reflect.path.CtPathBuilder recursiveWildcard() {
        return name(spoon.reflect.path.impl.CtNamedPathElement.RECURSIVE_WILCARD);
    }

    public <T extends spoon.reflect.declaration.CtElement> spoon.reflect.path.CtPathBuilder type(java.lang.Class<T> type, java.lang.String[]... args) {
        spoon.reflect.path.impl.CtTypedNameElement e = new spoon.reflect.path.impl.CtTypedNameElement(type);
        if (args != null) {
            for (java.lang.String[] arg : args) {
                e.addArgument(arg[0], arg[1]);
            }
        }
        elements.add(e);
        return this;
    }

    public spoon.reflect.path.CtPathBuilder role(spoon.reflect.path.CtRole role, java.lang.String[]... args) {
        spoon.reflect.path.impl.CtRolePathElement e = new spoon.reflect.path.impl.CtRolePathElement(role);
        if (args != null) {
            for (java.lang.String[] arg : args) {
                e.addArgument(arg[0], arg[1]);
            }
        }
        elements.add(e);
        return this;
    }

    public spoon.reflect.path.CtPath build() {
        spoon.reflect.path.impl.CtPathImpl path = new spoon.reflect.path.impl.CtPathImpl();
        for (spoon.reflect.path.impl.CtPathElement el : elements) {
            path.addLast(el);
        }
        return path;
    }
}


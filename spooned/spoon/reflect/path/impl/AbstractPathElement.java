package spoon.reflect.path.impl;


public abstract class AbstractPathElement<P extends spoon.reflect.declaration.CtElement, T extends spoon.reflect.declaration.CtElement> implements spoon.reflect.path.impl.CtPathElement<P, T> {
    private java.util.Map<java.lang.String, java.lang.String> arguments = new java.util.TreeMap<>();

    public java.util.Map<java.lang.String, java.lang.String> getArguments() {
        return arguments;
    }

    @java.lang.Override
    public <C extends spoon.reflect.path.impl.CtPathElement<P, T>> C addArgument(java.lang.String key, java.lang.String value) {
        arguments.put(key, value);
        return ((C) (this));
    }

    java.util.Collection<spoon.reflect.declaration.CtElement> getChilds(spoon.reflect.declaration.CtElement element) {
        final java.util.Collection<spoon.reflect.declaration.CtElement> elements = new java.util.ArrayList<>();
        if (element != null) {
            element.accept(new spoon.reflect.visitor.CtScanner() {
                @java.lang.Override
                public void scan(spoon.reflect.declaration.CtElement element) {
                    elements.add(element);
                }
            });
        }
        return elements;
    }

    protected java.lang.String getParamString() {
        if (arguments.isEmpty()) {
            return "";
        }
        java.lang.StringBuilder builder = new java.lang.StringBuilder("[");
        for (java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.String>> iter = arguments.entrySet().iterator(); iter.hasNext();) {
            java.util.Map.Entry<java.lang.String, java.lang.String> value = iter.next();
            builder.append((((value.getKey()) + "=") + (value.getValue())));
            if (iter.hasNext()) {
                builder.append(";");
            }
        }
        return builder.append("]").toString();
    }
}


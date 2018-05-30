package spoon.metamodel;


public enum MMMethodKind {
    GET((-1),false,1,( m) -> ((m.getParameters().size()) == 0) && ((m.getSimpleName().startsWith("get")) || (m.getSimpleName().startsWith("is")))), SET(0,false,1,( m) -> ((m.getParameters().size()) == 1) && (m.getSimpleName().startsWith("set"))), ADD_FIRST(0,true,10,( m) -> {
        if ((m.getParameters().size()) == 1) {
            if ((m.getSimpleName().startsWith("add")) || (m.getSimpleName().startsWith("insert"))) {
                if ((m.getSimpleName().endsWith("AtTop")) || (m.getSimpleName().endsWith("Begin"))) {
                    return true;
                }
            }
        }
        return false;
    }), ADD_LAST(0,true,1,( m) -> {
        if ((m.getParameters().size()) == 1) {
            if ((m.getSimpleName().startsWith("add")) || (m.getSimpleName().startsWith("insert"))) {
                return true;
            }
        }
        return false;
    }), ADD_ON(1,true,1,( m) -> {
        if (((m.getParameters().size()) == 2) && (m.getParameters().get(0).getType().getSimpleName().equals("int"))) {
            if ((m.getSimpleName().startsWith("add")) || (m.getSimpleName().startsWith("insert"))) {
                return true;
            }
        }
        return false;
    }), REMOVE(0,true,1,( m) -> ((m.getParameters().size()) == 1) && (m.getSimpleName().startsWith("remove"))), GET_BY((-1),true,1,( m) -> ((m.getSimpleName().startsWith("get")) && ((m.getParameters().size()) == 1)) && (m.getParameters().get(0).getType().getQualifiedName().equals(java.lang.String.class.getName()))), OTHER((-2),false,0,( m) -> true);
    private final java.util.function.Predicate<spoon.reflect.declaration.CtMethod<?>> detector;

    private final int level;

    private final boolean multi;

    private final int valueParameterIndex;

    MMMethodKind(int valueParameterIndex, boolean multi, int level, java.util.function.Predicate<spoon.reflect.declaration.CtMethod<?>> detector) {
        this.multi = multi;
        this.level = level;
        this.detector = detector;
        this.valueParameterIndex = valueParameterIndex;
    }

    public boolean isMulti() {
        return multi;
    }

    public int getValueParameterIndex() {
        return valueParameterIndex;
    }

    public static spoon.metamodel.MMMethodKind valueOf(spoon.reflect.declaration.CtMethod<?> method) {
        spoon.metamodel.MMMethodKind result = spoon.metamodel.MMMethodKind.OTHER;
        for (spoon.metamodel.MMMethodKind k : spoon.metamodel.MMMethodKind.values()) {
            if ((k.detector.test(method)) && ((result.level) < (k.level))) {
                if ((result.level) == (k.level)) {
                    throw new spoon.SpoonException(((((("Ambiguous method kinds " + (result.name())) + " X ") + (k.name())) + " for method ") + (method.getSignature())));
                }
                result = k;
            }
        }
        return result;
    }
}


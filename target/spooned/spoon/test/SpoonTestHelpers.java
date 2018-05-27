package spoon.test;


public class SpoonTestHelpers {
    private SpoonTestHelpers() {
    }

    public static java.util.List<spoon.reflect.declaration.CtType<? extends spoon.reflect.declaration.CtElement>> getAllInstantiableMetamodelInterfaces() {
        return new spoon.test.metamodel.SpoonMetaModel(new java.io.File("src/main/java")).getAllInstantiableMetamodelInterfaces();
    }

    public static boolean isMetamodelRelatedType(spoon.reflect.reference.CtTypeReference<?> typeReference) {
        spoon.reflect.reference.CtTypeReference<java.lang.Object> ctElRef = typeReference.getFactory().Code().createCtTypeReference(spoon.reflect.declaration.CtElement.class);
        if (typeReference.isSubtypeOf(ctElRef)) {
            return true;
        }
        if (((typeReference.getActualTypeArguments().size()) > 0) && ("?".equals(typeReference.getActualTypeArguments().get(0).getQualifiedName()))) {
            return false;
        }
        return ((typeReference.getActualTypeArguments().size()) > 0) && (typeReference.getActualTypeArguments().get(0).getTypeDeclaration().isSubtypeOf(ctElRef));
    }

    private static boolean containsMethodBasedOnName(java.util.List<spoon.reflect.declaration.CtMethod<?>> l, spoon.reflect.declaration.CtMethod setter) {
        for (spoon.reflect.declaration.CtMethod<?> m : l) {
            if (m.getSimpleName().equals(setter.getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    public static java.util.List<spoon.reflect.declaration.CtMethod<?>> getAllMetamodelMethods(spoon.reflect.declaration.CtType<?> baseType) {
        java.util.List<spoon.reflect.declaration.CtMethod<?>> result = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtMethod<?> m : baseType.getMethods()) {
            if (!(spoon.test.SpoonTestHelpers.containsMethodBasedOnName(result, m))) {
                result.add(m);
            }
        }
        for (spoon.reflect.reference.CtTypeReference<?> itf : baseType.getSuperInterfaces()) {
            for (spoon.reflect.declaration.CtMethod<?> up : spoon.test.SpoonTestHelpers.getAllSetters(itf.getTypeDeclaration())) {
                if (!(spoon.test.SpoonTestHelpers.containsMethodBasedOnName(result, up))) {
                    result.add(up);
                }
            }
        }
        return result;
    }

    public static java.util.List<spoon.reflect.declaration.CtMethod<?>> getAllSetters(spoon.reflect.declaration.CtType<?> baseType) {
        java.util.List<spoon.reflect.declaration.CtMethod<?>> result = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtMethod<?> m : spoon.test.SpoonTestHelpers.getAllMetamodelMethods(baseType)) {
            if ("setParent".equals(m.getSimpleName())) {
                continue;
            }
            if (!((m.getSimpleName().startsWith("set")) || (m.getSimpleName().startsWith("add")))) {
                continue;
            }
            if (m.hasAnnotation(spoon.support.UnsettableProperty.class)) {
                continue;
            }
            if ((m.getParameters().size()) != 1) {
                continue;
            }
            if (!(spoon.test.SpoonTestHelpers.isMetamodelRelatedType(m.getParameters().get(0).getType()))) {
                continue;
            }
            result.add(m);
        }
        return result;
    }

    public static spoon.reflect.declaration.CtMethod<?> getSetterOf(spoon.reflect.declaration.CtType<?> baseType, spoon.reflect.declaration.CtMethod<?> getter) {
        java.lang.String setterName = getter.getSimpleName().replaceFirst("^get", "set");
        java.lang.Object[] tentativeSetters = baseType.getAllMethods().stream().filter(( x) -> x.getSimpleName().equals(setterName)).toArray();
        if ((tentativeSetters.length) == 0) {
            return null;
        }
        for (java.lang.Object o : tentativeSetters) {
            if ((baseType.getPackage().getElements(new spoon.reflect.visitor.filter.OverridingMethodFilter(((spoon.reflect.declaration.CtMethod<?>) (o)))).size()) == 0) {
                return ((spoon.reflect.declaration.CtMethod<?>) (o));
            }
        }
        return ((spoon.reflect.declaration.CtMethod<?>) (tentativeSetters[0]));
    }

    public static boolean isMetamodelProperty(spoon.reflect.declaration.CtType<?> baseType, spoon.reflect.declaration.CtMethod<?> m) {
        return (((m.getSimpleName().startsWith("get")) && ((m.getParameters().size()) == 0)) && (!(m.hasAnnotation(spoon.support.DerivedProperty.class)))) && (spoon.test.SpoonTestHelpers.isMetamodelRelatedType(m.getType()));
    }
}


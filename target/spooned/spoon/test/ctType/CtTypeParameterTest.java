package spoon.test.ctType;


public class CtTypeParameterTest {
    @org.junit.Test
    public void testTypeErasure() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> ctModel = ((spoon.reflect.declaration.CtClass<?>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.ctType.testclasses.ErasureModelA.class)));
        checkType(ctModel);
    }

    private void checkType(spoon.reflect.declaration.CtType<?> type) throws java.lang.NoSuchFieldException, java.lang.SecurityException {
        java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters = type.getFormalCtTypeParameters();
        for (spoon.reflect.declaration.CtTypeParameter ctTypeParameter : formalTypeParameters) {
            checkTypeParamErasureOfType(ctTypeParameter, type.getActualClass());
        }
        for (spoon.reflect.declaration.CtTypeMember member : type.getTypeMembers()) {
            if (member instanceof spoon.reflect.declaration.CtFormalTypeDeclarer) {
                spoon.reflect.declaration.CtFormalTypeDeclarer ftDecl = ((spoon.reflect.declaration.CtFormalTypeDeclarer) (member));
                formalTypeParameters = ftDecl.getFormalCtTypeParameters();
                if (member instanceof spoon.reflect.declaration.CtExecutable<?>) {
                    spoon.reflect.declaration.CtExecutable<?> exec = ((spoon.reflect.declaration.CtExecutable<?>) (member));
                    for (spoon.reflect.declaration.CtTypeParameter ctTypeParameter : formalTypeParameters) {
                        checkTypeParamErasureOfExecutable(ctTypeParameter);
                    }
                    for (spoon.reflect.declaration.CtParameter<?> param : exec.getParameters()) {
                        checkParameterErasureOfExecutable(param);
                    }
                }else
                    if (member instanceof spoon.reflect.declaration.CtType<?>) {
                        spoon.reflect.declaration.CtType<?> nestedType = ((spoon.reflect.declaration.CtType<?>) (member));
                        checkType(nestedType);
                    }

            }
        }
    }

    private void checkTypeParamErasureOfType(spoon.reflect.declaration.CtTypeParameter typeParam, java.lang.Class<?> clazz) throws java.lang.NoSuchFieldException, java.lang.SecurityException {
        java.lang.reflect.Field field = clazz.getDeclaredField(("param" + (typeParam.getSimpleName())));
        org.junit.Assert.assertEquals(("TypeErasure of type param " + (getTypeParamIdentification(typeParam))), field.getType().getName(), typeParam.getTypeErasure().getQualifiedName());
    }

    private void checkTypeParamErasureOfExecutable(spoon.reflect.declaration.CtTypeParameter typeParam) throws java.lang.NoSuchFieldException, java.lang.SecurityException {
        spoon.reflect.declaration.CtExecutable<?> exec = ((spoon.reflect.declaration.CtExecutable<?>) (typeParam.getParent()));
        spoon.reflect.declaration.CtParameter<?> param = exec.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtParameter.class, ("param" + (typeParam.getSimpleName())))).first();
        org.junit.Assert.assertNotNull(((("Missing param" + (typeParam.getSimpleName())) + " in ") + (exec.getSignature())), param);
        int paramIdx = exec.getParameters().indexOf(param);
        java.lang.Class declClass = exec.getParent(spoon.reflect.declaration.CtType.class).getActualClass();
        java.lang.reflect.Executable declExec;
        if (exec instanceof spoon.reflect.declaration.CtConstructor) {
            declExec = declClass.getDeclaredConstructors()[0];
        }else {
            declExec = getMethodByName(declClass, exec.getSimpleName());
        }
        java.lang.Class<?> paramType = declExec.getParameterTypes()[paramIdx];
        org.junit.Assert.assertEquals(("TypeErasure of executable param " + (getTypeParamIdentification(typeParam))), paramType.getName(), typeParam.getTypeErasure().toString());
    }

    private void checkParameterErasureOfExecutable(spoon.reflect.declaration.CtParameter<?> param) {
        spoon.reflect.declaration.CtExecutable<?> exec = param.getParent();
        spoon.reflect.reference.CtTypeReference<?> typeErasure = param.getType().getTypeErasure();
        int paramIdx = exec.getParameters().indexOf(param);
        java.lang.Class declClass = exec.getParent(spoon.reflect.declaration.CtType.class).getActualClass();
        java.lang.reflect.Executable declExec;
        if (exec instanceof spoon.reflect.declaration.CtConstructor) {
            declExec = declClass.getDeclaredConstructors()[0];
        }else {
            declExec = getMethodByName(declClass, exec.getSimpleName());
        }
        java.lang.Class<?> paramType = declExec.getParameterTypes()[paramIdx];
        org.junit.Assert.assertEquals(0, typeErasure.getActualTypeArguments().size());
        org.junit.Assert.assertEquals(((("TypeErasure of executable " + (exec.getSignature())) + " parameter ") + (param.getSimpleName())), paramType.getName(), typeErasure.getQualifiedName());
    }

    private java.lang.reflect.Executable getMethodByName(java.lang.Class declClass, java.lang.String simpleName) {
        for (java.lang.reflect.Method method : declClass.getDeclaredMethods()) {
            if (method.getName().equals(simpleName)) {
                return method;
            }
        }
        org.junit.Assert.fail(((("Method " + simpleName) + " not found in ") + (declClass.getName())));
        return null;
    }

    private java.lang.String getTypeParamIdentification(spoon.reflect.declaration.CtTypeParameter typeParam) {
        java.lang.String result = ("<" + (typeParam.getSimpleName())) + ">";
        spoon.reflect.declaration.CtFormalTypeDeclarer l_decl = typeParam.getParent(spoon.reflect.declaration.CtFormalTypeDeclarer.class);
        if (l_decl instanceof spoon.reflect.declaration.CtType) {
            return (((spoon.reflect.declaration.CtType) (l_decl)).getQualifiedName()) + result;
        }
        if (l_decl instanceof spoon.reflect.declaration.CtExecutable) {
            spoon.reflect.declaration.CtExecutable exec = ((spoon.reflect.declaration.CtExecutable) (l_decl));
            if (exec instanceof spoon.reflect.declaration.CtMethod) {
                result = (exec.getSignature()) + result;
            }
            return ((exec.getParent(spoon.reflect.declaration.CtType.class).getQualifiedName()) + "#") + result;
        }
        throw new java.lang.AssertionError();
    }

    @org.junit.Test
    public void testTypeSame() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> ctModel = ((spoon.reflect.declaration.CtClass<?>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.ctType.testclasses.ErasureModelA.class)));
        spoon.reflect.declaration.CtTypeParameter tpA = ctModel.getFormalCtTypeParameters().get(0);
        spoon.reflect.declaration.CtTypeParameter tpB = ctModel.getFormalCtTypeParameters().get(1);
        spoon.reflect.declaration.CtTypeParameter tpC = ctModel.getFormalCtTypeParameters().get(2);
        spoon.reflect.declaration.CtTypeParameter tpD = ctModel.getFormalCtTypeParameters().get(3);
        spoon.reflect.declaration.CtConstructor<?> ctModelCons = ctModel.getConstructors().iterator().next();
        spoon.reflect.declaration.CtMethod<?> ctModelMethod = ctModel.getMethodsByName("method").get(0);
        spoon.reflect.declaration.CtMethod<?> ctModelMethod2 = ctModel.getMethodsByName("method2").get(0);
        spoon.reflect.declaration.CtClass<?> ctModelB = ctModel.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "ModelB")).first();
        spoon.reflect.declaration.CtTypeParameter tpA2 = ctModelB.getFormalCtTypeParameters().get(0);
        spoon.reflect.declaration.CtTypeParameter tpB2 = ctModelB.getFormalCtTypeParameters().get(1);
        spoon.reflect.declaration.CtTypeParameter tpC2 = ctModelB.getFormalCtTypeParameters().get(2);
        spoon.reflect.declaration.CtTypeParameter tpD2 = ctModelB.getFormalCtTypeParameters().get(3);
        spoon.reflect.declaration.CtConstructor<?> ctModelBCons = ctModelB.getConstructors().iterator().next();
        spoon.reflect.declaration.CtMethod<?> ctModelBMethod = ctModelB.getMethodsByName("method").get(0);
        checkIsSame(ctModel.getFormalCtTypeParameters(), ctModelB.getFormalCtTypeParameters(), true);
        checkIsSame(ctModelCons.getFormalCtTypeParameters(), ctModelBCons.getFormalCtTypeParameters(), true);
        checkIsSame(ctModelMethod.getFormalCtTypeParameters(), ctModelBMethod.getFormalCtTypeParameters(), true);
        checkIsSame(ctModelCons.getFormalCtTypeParameters(), ctModelBMethod.getFormalCtTypeParameters(), false);
        checkIsSame(ctModelMethod.getFormalCtTypeParameters(), ctModelMethod2.getFormalCtTypeParameters(), true);
        spoon.reflect.declaration.CtClass<?> ctModelC = ctModel.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "ModelC")).first();
    }

    private void checkIsSame(java.util.List<spoon.reflect.declaration.CtTypeParameter> tps1, java.util.List<spoon.reflect.declaration.CtTypeParameter> tps2, boolean isSameOnSameIndex) {
        for (int i = 0; i < (tps1.size()); i++) {
            spoon.reflect.declaration.CtTypeParameter tp1 = tps1.get(i);
            for (int j = 0; j < (tps2.size()); j++) {
                spoon.reflect.declaration.CtTypeParameter tp2 = tps2.get(j);
                if ((i == j) && isSameOnSameIndex) {
                    checkIsSame(tp1, tp2);
                }else {
                    checkIsNotSame(tp1, tp2);
                }
            }
        }
    }

    private void checkIsSame(spoon.reflect.declaration.CtTypeParameter tp1, spoon.reflect.declaration.CtTypeParameter tp2) {
        org.junit.Assert.assertTrue(((spoon.test.ctType.CtTypeParameterTest.isSame(tp1, tp2, false, true)) || (spoon.test.ctType.CtTypeParameterTest.isSame(tp2, tp1, false, true))));
    }

    private void checkIsNotSame(spoon.reflect.declaration.CtTypeParameter tp1, spoon.reflect.declaration.CtTypeParameter tp2) {
        org.junit.Assert.assertFalse(((spoon.test.ctType.CtTypeParameterTest.isSame(tp1, tp2, false, true)) || (spoon.test.ctType.CtTypeParameterTest.isSame(tp2, tp1, false, true))));
    }

    private static boolean isSame(spoon.reflect.declaration.CtTypeParameter thisType, spoon.reflect.declaration.CtTypeParameter thatType, boolean canTypeErasure, boolean checkMethodOverrides) {
        spoon.reflect.reference.CtTypeReference<?> thatAdaptedType = thisType.getFactory().Type().createTypeAdapter(thisType.getTypeParameterDeclarer()).adaptType(thatType);
        if (thatAdaptedType == null) {
            return false;
        }
        return thisType.getQualifiedName().equals(thatAdaptedType.getQualifiedName());
    }
}


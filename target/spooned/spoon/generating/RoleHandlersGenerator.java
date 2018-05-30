package spoon.generating;


public class RoleHandlersGenerator extends spoon.processing.AbstractManualProcessor {
    public static final java.lang.String TARGET_PACKAGE = "spoon.reflect.meta.impl";

    java.util.Map<java.lang.String, spoon.metamodel.MetamodelProperty> methodsByTypeRoleHandler = new java.util.HashMap<>();

    @java.lang.Override
    public void process() {
        spoon.metamodel.SpoonMetaModel metaModel = new spoon.metamodel.SpoonMetaModel(getFactory());
        java.util.List<spoon.metamodel.MetamodelProperty> superFields = new java.util.ArrayList<>();
        metaModel.getConcepts().forEach(( mmConcept) -> {
            mmConcept.getRoleToProperty().forEach(( role, rim) -> {
                addUniqueObject(superFields, rim.getRootSuperField());
            });
        });
        superFields.sort(( a, b) -> {
            int d = a.getRole().name().compareTo(b.getRole().name());
            if (d != 0) {
                return d;
            }
            return a.getOwnerConcept().getName().compareTo(b.getOwnerConcept().getName());
        });
        spoon.reflect.visitor.PrinterHelper concept = new spoon.reflect.visitor.PrinterHelper(getFactory().getEnvironment());
        superFields.forEach(( mmField) -> {
            concept.write((((mmField.getOwnerConcept().getName()) + " CtRole.") + (mmField.getRole().name()))).writeln().incTab().write("ItemType: ").write(mmField.getValueType().toString()).writeln();
            for (spoon.metamodel.MMMethodKind mk : spoon.metamodel.MMMethodKind.values()) {
                spoon.metamodel.MMMethod mmMethod = mmField.getMethod(mk);
                if (mmMethod != null) {
                    concept.write(mk.name()).write(": ").write(mmMethod.getSignature()).write(" : ").write(mmMethod.getReturnType().toString()).writeln();
                }
            }
            concept.decTab();
            concept.write("----------------------------------------------------------").writeln();
        });
        try (java.io.Writer w = new java.io.OutputStreamWriter(new java.io.FileOutputStream(file("target/report/concept.txt")))) {
            w.write(concept.toString());
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        }
        spoon.reflect.declaration.CtType<?> template = getTemplate("spoon.generating.meta.ModelRoleHandlerTemplate");
        spoon.reflect.declaration.CtClass<?> modelRoleHandlersClass = spoon.template.Substitution.createTypeFromTemplate(((spoon.generating.RoleHandlersGenerator.TARGET_PACKAGE) + ".ModelRoleHandlers"), template, new java.util.HashMap<>());
        spoon.reflect.code.CtNewArray<?> roleHandlersFieldExpr = ((spoon.reflect.code.CtNewArray<?>) (modelRoleHandlersClass.getField("roleHandlers").getDefaultExpression()));
        superFields.forEach(( rim) -> {
            java.util.Map<java.lang.String, java.lang.Object> params = new java.util.HashMap<>();
            params.put("$getterName$", rim.getMethod(spoon.metamodel.MMMethodKind.GET).getName());
            if ((rim.getMethod(spoon.metamodel.MMMethodKind.SET)) != null) {
                params.put("$setterName$", rim.getMethod(spoon.metamodel.MMMethodKind.SET).getName());
            }
            params.put("$Role$", getFactory().Type().createReference(spoon.reflect.path.CtRole.class));
            params.put("ROLE", rim.getRole().name());
            params.put("$TargetType$", rim.getOwnerConcept().getModelInterface().getReference());
            params.put("AbstractHandler", getRoleHandlerSuperTypeQName(rim));
            params.put("Node", rim.getOwnerConcept().getModelInterface().getReference());
            params.put("ValueType", fixMainValueType((getRoleHandlerSuperTypeQName(rim).endsWith("SingleHandler") ? rim.getValueType() : rim.getItemValueType())));
            spoon.reflect.declaration.CtClass<?> modelRoleHandlerClass = spoon.template.Substitution.createTypeFromTemplate(getHandlerName(rim), getTemplate("spoon.generating.meta.RoleHandlerTemplate"), params);
            if ((rim.getMethod(spoon.metamodel.MMMethodKind.SET)) == null) {
                modelRoleHandlerClass.getMethodsByName("setValue").forEach(( m) -> m.delete());
            }
            modelRoleHandlerClass.addModifier(spoon.reflect.declaration.ModifierKind.STATIC);
            modelRoleHandlersClass.addNestedType(modelRoleHandlerClass);
            roleHandlersFieldExpr.addElement(getFactory().createCodeSnippetExpression((("new " + (modelRoleHandlerClass.getSimpleName())) + "()")));
        });
    }

    private spoon.reflect.reference.CtTypeReference<?> fixMainValueType(spoon.reflect.reference.CtTypeReference<?> valueType) {
        valueType = fixValueType(valueType);
        if (valueType instanceof spoon.reflect.reference.CtWildcardReference) {
            return getFactory().Type().OBJECT;
        }
        return valueType;
    }

    private spoon.reflect.reference.CtTypeReference<?> fixValueType(spoon.reflect.reference.CtTypeReference<?> valueType) {
        valueType = valueType.clone();
        if (valueType instanceof spoon.reflect.reference.CtTypeParameterReference) {
            if (valueType instanceof spoon.reflect.reference.CtWildcardReference) {
                spoon.reflect.reference.CtTypeReference<?> boundingType = ((spoon.reflect.reference.CtTypeParameterReference) (valueType)).getBoundingType();
                if (boundingType instanceof spoon.reflect.reference.CtTypeParameterReference) {
                    ((spoon.reflect.reference.CtTypeParameterReference) (valueType)).setBoundingType(null);
                }
                return valueType;
            }
            spoon.reflect.reference.CtTypeParameterReference tpr = ((spoon.reflect.reference.CtTypeParameterReference) (valueType));
            return getFactory().createWildcardReference();
        }
        for (int i = 0; i < (valueType.getActualTypeArguments().size()); i++) {
            valueType.getActualTypeArguments().set(i, fixValueType(valueType.getActualTypeArguments().get(i)));
        }
        valueType = valueType.box();
        return valueType;
    }

    private spoon.reflect.declaration.CtType<?> getTemplate(java.lang.String templateQName) {
        spoon.reflect.declaration.CtType<?> template = getFactory().Class().get(templateQName);
        return template;
    }

    private java.io.File file(java.lang.String name) {
        java.io.File f = new java.io.File(name);
        f.getParentFile().mkdirs();
        return f;
    }

    private static boolean containsObject(java.lang.Iterable<? extends java.lang.Object> iter, java.lang.Object o) {
        for (java.lang.Object object : iter) {
            if (object == o) {
                return true;
            }
        }
        return false;
    }

    private <T> boolean addUniqueObject(java.util.Collection<T> col, T o) {
        if (spoon.generating.RoleHandlersGenerator.containsObject(col, o)) {
            return false;
        }
        col.add(o);
        return true;
    }

    java.lang.String getHandlerName(spoon.metamodel.MetamodelProperty field) {
        java.lang.String typeName = field.getOwnerConcept().getName();
        return ((typeName + "_") + (field.getRole().name())) + "_RoleHandler";
    }

    public java.lang.String getRoleHandlerSuperTypeQName(spoon.metamodel.MetamodelProperty field) {
        switch (field.getValueContainerType()) {
            case LIST :
                return "spoon.reflect.meta.impl.ListHandler";
            case SET :
                return "spoon.reflect.meta.impl.SetHandler";
            case MAP :
                return "spoon.reflect.meta.impl.MapHandler";
            case SINGLE :
                return "spoon.reflect.meta.impl.SingleHandler";
        }
        throw new spoon.SpoonException(("Unexpected value container type: " + (field.getValueContainerType().name())));
    }
}


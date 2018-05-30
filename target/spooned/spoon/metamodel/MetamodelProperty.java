package spoon.metamodel;


public class MetamodelProperty {
    private final java.lang.String name;

    private final spoon.reflect.path.CtRole role;

    private final spoon.metamodel.MetamodelConcept ownerConcept;

    private spoon.reflect.meta.ContainerKind valueContainerType;

    private spoon.reflect.reference.CtTypeReference<?> valueType;

    private spoon.reflect.reference.CtTypeReference<?> itemValueType;

    private java.lang.Boolean derived;

    private java.lang.Boolean unsettable;

    private java.util.Map<spoon.metamodel.MMMethodKind, java.util.List<spoon.metamodel.MMMethod>> methodsByKind = new java.util.HashMap<>();

    private final java.util.List<spoon.metamodel.MMMethod> roleMethods = new java.util.ArrayList<>();

    private final java.util.Map<java.lang.String, spoon.metamodel.MMMethod> roleMethodsBySignature = new java.util.HashMap<>();

    private final java.util.List<spoon.metamodel.MetamodelProperty> superProperties = new java.util.ArrayList<>();

    private java.util.List<spoon.metamodel.MMMethodKind> ambiguousMethodKinds = new java.util.ArrayList<>();

    MetamodelProperty(java.lang.String name, spoon.reflect.path.CtRole role, spoon.metamodel.MetamodelConcept ownerConcept) {
        super();
        this.name = name;
        this.role = role;
        this.ownerConcept = ownerConcept;
    }

    void addMethod(spoon.reflect.declaration.CtMethod<?> method) {
        spoon.metamodel.MMMethod mmMethod = getOwnMethod(method, true);
        mmMethod.addOwnMethod(method);
    }

    spoon.metamodel.MMMethod getOwnMethod(spoon.reflect.declaration.CtMethod<?> method, boolean createIfNotExist) {
        for (spoon.metamodel.MMMethod mmMethod : roleMethods) {
            if (mmMethod.overrides(method)) {
                return mmMethod;
            }
        }
        if (createIfNotExist) {
            spoon.metamodel.MMMethod mmMethod = new spoon.metamodel.MMMethod(this, method);
            roleMethods.add(mmMethod);
            spoon.metamodel.SpoonMetaModel.getOrCreate(methodsByKind, mmMethod.getMethodKind(), () -> new java.util.ArrayList<>()).add(mmMethod);
            spoon.metamodel.MMMethod conflict = roleMethodsBySignature.put(mmMethod.getSignature(), mmMethod);
            if (conflict != null) {
                throw new spoon.SpoonException(((((("Conflict on " + (getOwnerConcept().getName())) + ".") + (name)) + " method signature: ") + (mmMethod.getSignature())));
            }
            return mmMethod;
        }
        return null;
    }

    void addSuperField(spoon.metamodel.MetamodelProperty superMMField) {
        if (spoon.metamodel.SpoonMetaModel.addUniqueObject(superProperties, superMMField)) {
            for (spoon.metamodel.MMMethod superMethod : superMMField.getRoleMethods()) {
                getOwnMethod(superMethod.getFirstOwnMethod(getOwnerConcept()), true).addSuperMethod(superMethod);
            }
        }
    }

    public java.lang.String getName() {
        return name;
    }

    public spoon.reflect.path.CtRole getRole() {
        return role;
    }

    public spoon.metamodel.MetamodelConcept getOwnerConcept() {
        return ownerConcept;
    }

    public spoon.reflect.meta.ContainerKind getValueContainerType() {
        return valueContainerType;
    }

    public spoon.reflect.reference.CtTypeReference<?> getValueType() {
        if ((valueType) == null) {
            throw new spoon.SpoonException("Model is not initialized yet");
        }
        return valueType;
    }

    spoon.reflect.reference.CtTypeReference<?> detectValueType() {
        spoon.metamodel.MMMethod mmGetMethod = getMethod(spoon.metamodel.MMMethodKind.GET);
        if (mmGetMethod == null) {
            throw new spoon.SpoonException(((("No getter exists for " + (getOwnerConcept().getName())) + ".") + (getName())));
        }
        spoon.metamodel.MMMethod mmSetMethod = getMethod(spoon.metamodel.MMMethodKind.SET);
        if (mmSetMethod == null) {
            return mmGetMethod.getReturnType();
        }
        spoon.reflect.reference.CtTypeReference<?> getterValueType = mmGetMethod.getReturnType();
        spoon.reflect.reference.CtTypeReference<?> setterValueType = mmSetMethod.getValueType();
        if (getterValueType.equals(setterValueType)) {
            return mmGetMethod.getReturnType();
        }
        if ((spoon.metamodel.MetamodelProperty.containerKindOf(getterValueType.getActualClass())) != (spoon.reflect.meta.ContainerKind.SINGLE)) {
            getterValueType = spoon.metamodel.MetamodelProperty.getItemValueType(getterValueType);
            setterValueType = spoon.metamodel.MetamodelProperty.getItemValueType(setterValueType);
        }
        if (getterValueType.equals(setterValueType)) {
            return mmGetMethod.getReturnType();
        }
        if (getterValueType.isSubtypeOf(setterValueType)) {
            return mmSetMethod.getValueType();
        }
        throw new spoon.SpoonException(((("Incompatible getter and setter for " + (getOwnerConcept().getName())) + ".") + (getName())));
    }

    void setValueType(spoon.reflect.reference.CtTypeReference<?> valueType) {
        spoon.reflect.factory.Factory f = valueType.getFactory();
        if (valueType instanceof spoon.reflect.reference.CtTypeParameterReference) {
            valueType = ((spoon.reflect.reference.CtTypeParameterReference) (valueType)).getBoundingType();
            if (valueType == null) {
                valueType = f.Type().OBJECT;
            }
        }
        if (valueType.isImplicit()) {
            valueType = valueType.clone();
            valueType.setImplicit(false);
        }
        this.valueType = valueType;
        this.valueContainerType = spoon.metamodel.MetamodelProperty.containerKindOf(valueType.getActualClass());
        if ((valueContainerType) != (spoon.reflect.meta.ContainerKind.SINGLE)) {
            itemValueType = spoon.metamodel.MetamodelProperty.getItemValueType(valueType);
        }else {
            itemValueType = valueType;
        }
    }

    public spoon.reflect.reference.CtTypeReference<?> getItemValueType() {
        if ((itemValueType) == null) {
            getValueType();
        }
        return itemValueType;
    }

    public void setItemValueType(spoon.reflect.reference.CtTypeReference<?> itemValueType) {
        this.itemValueType = itemValueType;
    }

    public spoon.metamodel.MMMethod getMethod(spoon.metamodel.MMMethodKind kind) {
        java.util.List<spoon.metamodel.MMMethod> ms = getMethods(kind);
        return (ms.size()) > 0 ? ms.get(0) : null;
    }

    public java.util.List<spoon.metamodel.MMMethod> getMethods(spoon.metamodel.MMMethodKind kind) {
        java.util.List<spoon.metamodel.MMMethod> ms = methodsByKind.get(kind);
        return ms == null ? java.util.Collections.emptyList() : java.util.Collections.unmodifiableList(ms);
    }

    public void forEachUnhandledMethod(java.util.function.Consumer<spoon.reflect.declaration.CtMethod<?>> consumer) {
        methodsByKind.forEach(( kind, mmMethods) -> {
            if (kind == (spoon.metamodel.MMMethodKind.OTHER)) {
                mmMethods.forEach(( mmMethod) -> mmMethod.getOwnMethods().forEach(consumer));
            }else {
                if ((mmMethods.size()) > 1) {
                    mmMethods.subList(1, mmMethods.size()).forEach(( mmMethod) -> mmMethod.getOwnMethods().forEach(consumer));
                }
            }
        });
    }

    void sortByBestMatch() {
        for (spoon.metamodel.MMMethodKind mk : spoon.metamodel.MMMethodKind.values()) {
            sortByBestMatch(mk);
        }
    }

    void sortByBestMatch(spoon.metamodel.MMMethodKind key) {
        java.util.List<spoon.metamodel.MMMethod> methods = methodsByKind.get(key);
        if ((methods != null) && ((methods.size()) > 1)) {
            int idx = getIdxOfBestMatch(methods, key);
            if (idx >= 0) {
                if (idx > 0) {
                    methods.add(0, methods.remove(idx));
                }
            }else {
                ambiguousMethodKinds.add(key);
            }
        }
    }

    private int getIdxOfBestMatch(java.util.List<spoon.metamodel.MMMethod> methods, spoon.metamodel.MMMethodKind key) {
        spoon.metamodel.MMMethod mmMethod = methods.get(0);
        if ((mmMethod.getMethod().getParameters().size()) == 0) {
            return getIdxOfBestMatchByReturnType(methods, key);
        }else {
            spoon.metamodel.MMMethod mmGetMethod = getMethod(spoon.metamodel.MMMethodKind.GET);
            if (mmGetMethod == null) {
                return -1;
            }
            return getIdxOfBestMatchByInputParameter(methods, key, mmGetMethod.getReturnType());
        }
    }

    private int getIdxOfBestMatchByReturnType(java.util.List<spoon.metamodel.MMMethod> methods, spoon.metamodel.MMMethodKind key) {
        if ((methods.size()) > 2) {
            throw new spoon.SpoonException(("Resolving of more then 2 conflicting getters is not supported. There are: " + (methods.toString())));
        }
        spoon.reflect.reference.CtTypeReference<?> returnType1 = methods.get(0).getMethod().getType();
        spoon.reflect.reference.CtTypeReference<?> returnType2 = methods.get(1).getMethod().getType();
        spoon.reflect.factory.Factory f = returnType1.getFactory();
        boolean is1Iterable = returnType1.isSubtypeOf(f.Type().ITERABLE);
        boolean is2Iterable = returnType2.isSubtypeOf(f.Type().ITERABLE);
        if (is1Iterable != is2Iterable) {
            if (is1Iterable) {
                if (isIterableOf(returnType1, returnType2)) {
                    return 0;
                }
            }else {
                if (isIterableOf(returnType2, returnType1)) {
                    return 1;
                }
            }
        }
        return -1;
    }

    private boolean isIterableOf(spoon.reflect.reference.CtTypeReference<?> iterableType, spoon.reflect.reference.CtTypeReference<?> itemType) {
        spoon.reflect.reference.CtTypeReference<?> iterableItemType = spoon.metamodel.MetamodelProperty.getItemValueType(iterableType);
        if (iterableItemType != null) {
            return itemType.isSubtypeOf(iterableItemType);
        }
        return false;
    }

    private int getIdxOfBestMatchByInputParameter(java.util.List<spoon.metamodel.MMMethod> methods, spoon.metamodel.MMMethodKind key, spoon.reflect.reference.CtTypeReference<?> expectedValueType) {
        int idx = -1;
        spoon.metamodel.MetamodelProperty.MatchLevel maxMatchLevel = null;
        spoon.reflect.reference.CtTypeReference<?> newValueType = null;
        if (key.isMulti()) {
            expectedValueType = spoon.metamodel.MetamodelProperty.getItemValueType(expectedValueType);
        }
        for (int i = 0; i < (methods.size()); i++) {
            spoon.metamodel.MMMethod mMethod = methods.get(i);
            spoon.metamodel.MetamodelProperty.MatchLevel matchLevel = getMatchLevel(expectedValueType, mMethod.getValueType());
            if (matchLevel != null) {
                if (idx == (-1)) {
                    idx = i;
                    maxMatchLevel = matchLevel;
                    newValueType = mMethod.getValueType();
                }else {
                    if ((maxMatchLevel.ordinal()) < (matchLevel.ordinal())) {
                        idx = i;
                        maxMatchLevel = matchLevel;
                        newValueType = mMethod.getValueType();
                    }else
                        if (maxMatchLevel == matchLevel) {
                            return -1;
                        }

                }
            }
        }
        return idx;
    }

    private static spoon.reflect.reference.CtTypeReference<?> getItemValueType(spoon.reflect.reference.CtTypeReference<?> valueType) {
        spoon.reflect.meta.ContainerKind valueContainerType = spoon.metamodel.MetamodelProperty.containerKindOf(valueType.getActualClass());
        if (valueContainerType == (spoon.reflect.meta.ContainerKind.SINGLE)) {
            return null;
        }
        spoon.reflect.reference.CtTypeReference<?> itemValueType;
        if (valueContainerType == (spoon.reflect.meta.ContainerKind.MAP)) {
            if ((java.lang.String.class.getName().equals(valueType.getActualTypeArguments().get(0).getQualifiedName())) == false) {
                throw new spoon.SpoonException(("Unexpected container of type: " + (valueType.toString())));
            }
            itemValueType = valueType.getActualTypeArguments().get(1);
        }else {
            itemValueType = valueType.getActualTypeArguments().get(0);
        }
        if (itemValueType instanceof spoon.reflect.reference.CtTypeParameterReference) {
            itemValueType = ((spoon.reflect.reference.CtTypeParameterReference) (itemValueType)).getBoundingType();
            if (itemValueType == null) {
                itemValueType = valueType.getFactory().Type().OBJECT;
            }
        }
        return itemValueType;
    }

    private enum MatchLevel {
        SUBTYPE, ERASED_EQUALS, EQUALS;}

    private spoon.metamodel.MetamodelProperty.MatchLevel getMatchLevel(spoon.reflect.reference.CtTypeReference<?> expectedType, spoon.reflect.reference.CtTypeReference<?> realType) {
        if (expectedType.equals(realType)) {
            return spoon.metamodel.MetamodelProperty.MatchLevel.EQUALS;
        }
        if (expectedType.getTypeErasure().equals(realType.getTypeErasure())) {
            return spoon.metamodel.MetamodelProperty.MatchLevel.ERASED_EQUALS;
        }
        if (expectedType.isSubtypeOf(realType)) {
            return spoon.metamodel.MetamodelProperty.MatchLevel.SUBTYPE;
        }
        return null;
    }

    private spoon.reflect.reference.CtTypeReference<?> getMapValueType(spoon.reflect.reference.CtTypeReference<?> valueType) {
        if (valueType != null) {
            spoon.reflect.factory.Factory f = valueType.getFactory();
            if ((valueType.isSubtypeOf(f.Type().MAP)) && ((valueType.getActualTypeArguments().size()) == 2)) {
                return valueType.getActualTypeArguments().get(1);
            }
        }
        return null;
    }

    public boolean isDerived() {
        if ((derived) == null) {
            if (isUnsettable()) {
                derived = true;
                return true;
            }
            spoon.metamodel.MMMethod getter = getMethod(spoon.metamodel.MMMethodKind.GET);
            if (getter == null) {
                throw new spoon.SpoonException(("No getter defined for " + (this)));
            }
            spoon.reflect.reference.CtTypeReference<spoon.support.DerivedProperty> derivedProperty = getter.getMethod().getFactory().createCtTypeReference(spoon.support.DerivedProperty.class);
            boolean isConreteMethod = false;
            for (spoon.reflect.declaration.CtMethod<?> ctMethod : getter.getOwnMethods()) {
                if ((ctMethod.getAnnotation(derivedProperty)) != null) {
                    derived = java.lang.Boolean.TRUE;
                    return true;
                }
                isConreteMethod = isConreteMethod || ((ctMethod.getBody()) != null);
            }
            if (isConreteMethod) {
                derived = java.lang.Boolean.FALSE;
                return false;
            }
            derived = java.lang.Boolean.FALSE;
            for (spoon.metamodel.MetamodelProperty superField : superProperties) {
                if (superField.isDerived()) {
                    derived = java.lang.Boolean.TRUE;
                    break;
                }
            }
        }
        return derived;
    }

    public boolean isUnsettable() {
        if ((unsettable) == null) {
            spoon.metamodel.MMMethod setter = getMethod(spoon.metamodel.MMMethodKind.SET);
            if (setter == null) {
                unsettable = java.lang.Boolean.TRUE;
                return true;
            }
            spoon.reflect.reference.CtTypeReference<spoon.support.UnsettableProperty> unsettableProperty = setter.getMethod().getFactory().createCtTypeReference(spoon.support.UnsettableProperty.class);
            boolean isConreteMethod = false;
            for (spoon.reflect.declaration.CtMethod<?> ctMethod : setter.getOwnMethods()) {
                if ((ctMethod.getAnnotation(unsettableProperty)) != null) {
                    unsettable = java.lang.Boolean.TRUE;
                    return true;
                }
                isConreteMethod = isConreteMethod || ((ctMethod.getBody()) != null);
            }
            if (isConreteMethod) {
                unsettable = java.lang.Boolean.FALSE;
                return false;
            }
            unsettable = java.lang.Boolean.FALSE;
            for (spoon.metamodel.MetamodelProperty superField : superProperties) {
                if (superField.isUnsettable()) {
                    unsettable = java.lang.Boolean.TRUE;
                    break;
                }
            }
        }
        return unsettable;
    }

    public java.util.List<spoon.metamodel.MMMethod> getRoleMethods() {
        return java.util.Collections.unmodifiableList(roleMethods);
    }

    public java.util.Map<java.lang.String, spoon.metamodel.MMMethod> getRoleMethodsBySignature() {
        return java.util.Collections.unmodifiableMap(roleMethodsBySignature);
    }

    public java.util.List<spoon.metamodel.MetamodelProperty> getSuperFields() {
        return java.util.Collections.unmodifiableList(superProperties);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return (((((ownerConcept.getName()) + "#") + (getName())) + "<") + (valueType)) + ">";
    }

    public spoon.metamodel.MetamodelProperty getRootSuperField() {
        java.util.List<spoon.metamodel.MetamodelProperty> potentialRootSuperFields = new java.util.ArrayList<>();
        if ((roleMethods.size()) > 0) {
            potentialRootSuperFields.add(this);
        }
        superProperties.forEach(( superField) -> {
            spoon.metamodel.SpoonMetaModel.addUniqueObject(potentialRootSuperFields, superField.getRootSuperField());
        });
        int idx = 0;
        if ((potentialRootSuperFields.size()) > 1) {
            boolean needsSetter = (getMethod(spoon.metamodel.MMMethodKind.SET)) != null;
            spoon.reflect.reference.CtTypeReference<?> expectedValueType = this.getValueType().getTypeErasure();
            for (int i = 1; i < (potentialRootSuperFields.size()); i++) {
                spoon.metamodel.MetamodelProperty superField = potentialRootSuperFields.get(i);
                if ((superField.getValueType().getTypeErasure().equals(expectedValueType)) == false) {
                    break;
                }
                if (needsSetter && ((superField.getMethod(spoon.metamodel.MMMethodKind.SET)) == null)) {
                    break;
                }
                idx = i;
            }
        }
        return potentialRootSuperFields.get(idx);
    }

    private static spoon.reflect.meta.ContainerKind containerKindOf(java.lang.Class<?> valueClass) {
        if (java.util.List.class.isAssignableFrom(valueClass)) {
            return spoon.reflect.meta.ContainerKind.LIST;
        }
        if (java.util.Map.class.isAssignableFrom(valueClass)) {
            return spoon.reflect.meta.ContainerKind.MAP;
        }
        if (java.util.Set.class.isAssignableFrom(valueClass)) {
            return spoon.reflect.meta.ContainerKind.SET;
        }
        return spoon.reflect.meta.ContainerKind.SINGLE;
    }
}


package spoon.support.reflect.declaration;


public class CtAnnotationImpl<A extends java.lang.annotation.Annotation> extends spoon.support.reflect.code.CtExpressionImpl<A> implements spoon.reflect.declaration.CtAnnotation<A> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.ANNOTATION_TYPE)
    spoon.reflect.reference.CtTypeReference<A> annotationType;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.VALUE)
    private java.util.Map<java.lang.String, spoon.reflect.code.CtExpression> elementValues = new java.util.TreeMap() {
        @java.lang.Override
        public java.util.Set<java.util.Map.Entry<java.lang.String, spoon.reflect.code.CtExpression>> entrySet() {
            java.util.Set<java.util.Map.Entry<java.lang.String, spoon.reflect.code.CtExpression>> result = new java.util.TreeSet<java.util.Map.Entry<java.lang.String, spoon.reflect.code.CtExpression>>(new java.util.Comparator<java.util.Map.Entry<java.lang.String, spoon.reflect.code.CtExpression>>() {
                final spoon.support.comparator.CtLineElementComparator comp = new spoon.support.comparator.CtLineElementComparator();

                @java.lang.Override
                public int compare(java.util.Map.Entry<java.lang.String, spoon.reflect.code.CtExpression> o1, java.util.Map.Entry<java.lang.String, spoon.reflect.code.CtExpression> o2) {
                    return comp.compare(o1.getValue(), o2.getValue());
                }
            });
            result.addAll(super.entrySet());
            return result;
        }
    };

    public CtAnnotationImpl() {
        super();
    }

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtAnnotation(this);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, java.lang.Object value) {
        if (value instanceof spoon.reflect.code.CtExpression) {
            return addValueExpression(elementName, ((spoon.reflect.code.CtExpression<?>) (value)));
        }
        return this.addValueExpression(elementName, convertValueToExpression(value));
    }

    private spoon.reflect.code.CtExpression convertValueToExpression(java.lang.Object value) {
        spoon.reflect.code.CtExpression res;
        if (value.getClass().isArray()) {
            res = getFactory().Core().createNewArray();
            java.lang.Object[] values = ((java.lang.Object[]) (value));
            res.setType(getFactory().Type().createArrayReference(getFactory().Type().createReference(value.getClass().getComponentType())));
            for (java.lang.Object o : values) {
                ((spoon.reflect.code.CtNewArray) (res)).addElement(convertValueToExpression(o));
            }
        }else
            if (value instanceof java.util.Collection) {
                res = getFactory().Core().createNewArray();
                java.util.Collection values = ((java.util.Collection) (value));
                res.setType(getFactory().Type().createArrayReference(getFactory().Type().createReference(values.toArray()[0].getClass())));
                for (java.lang.Object o : values) {
                    ((spoon.reflect.code.CtNewArray) (res)).addElement(convertValueToExpression(o));
                }
            }else
                if (value instanceof java.lang.Class) {
                    res = getFactory().Code().createClassAccess(getFactory().Type().createReference(((java.lang.Class) (value))));
                }else
                    if (value instanceof java.lang.reflect.Field) {
                        spoon.reflect.reference.CtFieldReference<java.lang.Object> variable = getFactory().Field().createReference(((java.lang.reflect.Field) (value)));
                        variable.setStatic(true);
                        spoon.reflect.code.CtTypeAccess target = getFactory().Code().createTypeAccess(getFactory().Type().createReference(((java.lang.reflect.Field) (value)).getDeclaringClass()));
                        spoon.reflect.code.CtFieldRead fieldRead = getFactory().Core().createFieldRead();
                        fieldRead.setVariable(variable);
                        fieldRead.setTarget(target);
                        fieldRead.setType(target.getAccessedType());
                        res = fieldRead;
                    }else
                        if ((isPrimitive(value.getClass())) || (value instanceof java.lang.String)) {
                            res = getFactory().Code().createLiteral(value);
                        }else
                            if (value.getClass().isEnum()) {
                                final spoon.reflect.reference.CtTypeReference declaringClass = getFactory().Type().createReference(((java.lang.Enum) (value)).getDeclaringClass());
                                final spoon.reflect.reference.CtFieldReference variableRef = getFactory().Field().createReference(declaringClass, declaringClass, ((java.lang.Enum) (value)).name());
                                spoon.reflect.code.CtTypeAccess target = getFactory().Code().createTypeAccess(declaringClass);
                                spoon.reflect.code.CtFieldRead fieldRead = getFactory().Core().createFieldRead();
                                fieldRead.setVariable(variableRef);
                                fieldRead.setTarget(target);
                                fieldRead.setType(declaringClass);
                                res = fieldRead;
                            }else {
                                throw new spoon.SpoonException("Please, submit a valid value.");
                            }





        return res;
    }

    private boolean isPrimitive(java.lang.Class c) {
        return ((((((((c.isPrimitive()) || (c == (java.lang.Byte.class))) || (c == (java.lang.Short.class))) || (c == (java.lang.Integer.class))) || (c == (java.lang.Long.class))) || (c == (java.lang.Float.class))) || (c == (java.lang.Double.class))) || (c == (java.lang.Boolean.class))) || (c == (java.lang.Character.class));
    }

    private <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValueExpression(java.lang.String elementName, spoon.reflect.code.CtExpression<?> expression) {
        if (elementValues.containsKey(elementName)) {
            final spoon.reflect.code.CtExpression ctExpression = ((spoon.reflect.code.CtExpression) (elementValues.get(elementName)));
            if (ctExpression instanceof spoon.reflect.code.CtNewArray) {
                if (expression instanceof spoon.reflect.code.CtNewArray) {
                    java.util.List<spoon.reflect.code.CtExpression<?>> elements = ((spoon.reflect.code.CtNewArray) (expression)).getElements();
                    for (spoon.reflect.code.CtExpression expInArray : elements) {
                        ((spoon.reflect.code.CtNewArray) (ctExpression)).addElement(expInArray);
                    }
                }else {
                    ((spoon.reflect.code.CtNewArray) (ctExpression)).addElement(expression);
                }
            }else {
                spoon.reflect.code.CtNewArray<java.lang.Object> newArray = getFactory().Core().createNewArray();
                newArray.setType(ctExpression.getType());
                newArray.setParent(this);
                newArray.addElement(ctExpression);
                newArray.addElement(expression);
                elementValues.put(elementName, newArray);
            }
        }else {
            expression.setParent(this);
            getFactory().getEnvironment().getModelChangeListener().onMapAdd(this, spoon.reflect.path.CtRole.VALUE, this.elementValues, elementName, expression);
            elementValues.put(elementName, expression);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.code.CtLiteral<?> value) {
        return addValueExpression(elementName, value);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.code.CtNewArray<? extends spoon.reflect.code.CtExpression> value) {
        return addValueExpression(elementName, value);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.code.CtFieldAccess<?> value) {
        return addValueExpression(elementName, value);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.declaration.CtAnnotation<?> value) {
        return addValueExpression(elementName, value);
    }

    @java.lang.SuppressWarnings("unchecked")
    private java.lang.Object convertValue(java.lang.Object value) {
        if (value instanceof spoon.reflect.reference.CtFieldReference) {
            java.lang.Class<?> c = null;
            try {
                c = ((spoon.reflect.reference.CtFieldReference<?>) (value)).getDeclaringType().getActualClass();
            } catch (java.lang.Exception e) {
                return ((spoon.reflect.code.CtLiteral<?>) (((spoon.reflect.reference.CtFieldReference<?>) (value)).getDeclaration().getDefaultExpression().partiallyEvaluate())).getValue();
            }
            if (((spoon.reflect.reference.CtFieldReference<?>) (value)).getSimpleName().equals("class")) {
                return c;
            }
            spoon.reflect.declaration.CtField<?> field = ((spoon.reflect.reference.CtFieldReference<?>) (value)).getDeclaration();
            if (java.lang.Enum.class.isAssignableFrom(c)) {
                return java.lang.Enum.valueOf(((java.lang.Class<? extends java.lang.Enum>) (c)), ((spoon.reflect.reference.CtFieldReference<?>) (value)).getSimpleName());
            }
            if (field != null) {
                return convertValue(field.getDefaultExpression());
            }else {
                try {
                    return ((java.lang.reflect.Field) (((spoon.reflect.reference.CtFieldReference<?>) (value)).getActualField())).get(null);
                } catch (java.lang.Exception e) {
                    spoon.Launcher.LOGGER.error(e.getMessage(), e);
                }
                return null;
            }
        }else
            if (value instanceof spoon.reflect.code.CtFieldAccess) {
                return convertValue(((spoon.reflect.code.CtFieldAccess<?>) (value)).getVariable());
            }else
                if (value instanceof spoon.reflect.code.CtNewArray) {
                    spoon.reflect.code.CtNewArray<?> arrayExpression = ((spoon.reflect.code.CtNewArray<?>) (value));
                    java.lang.Class<?> componentType = arrayExpression.getType().getActualClass().getComponentType();
                    java.util.List<spoon.reflect.code.CtExpression<?>> elements = arrayExpression.getElements();
                    java.lang.Object array = java.lang.reflect.Array.newInstance(componentType, elements.size());
                    for (int i = 0; i < (elements.size()); i++) {
                        java.lang.reflect.Array.set(array, i, this.convertValue(elements.get(i)));
                    }
                    return array;
                }else
                    if (value instanceof spoon.reflect.declaration.CtAnnotation) {
                        return ((spoon.reflect.declaration.CtAnnotation<?>) (value)).getActualAnnotation();
                    }else
                        if (value instanceof spoon.reflect.code.CtLiteral) {
                            return ((spoon.reflect.code.CtLiteral<?>) (value)).getValue();
                        }else
                            if (value instanceof spoon.reflect.code.CtCodeElement) {
                                spoon.reflect.eval.PartialEvaluator eval = getFactory().Eval().createPartialEvaluator();
                                java.lang.Object ret = eval.evaluate(((spoon.reflect.code.CtCodeElement) (value)));
                                return this.convertValue(ret);
                            }else
                                if (value instanceof spoon.reflect.reference.CtTypeReference) {
                                    return ((spoon.reflect.reference.CtTypeReference<?>) (value)).getActualClass();
                                }






        return value;
    }

    private java.lang.Class<?> getElementType(java.lang.String name) {
        spoon.reflect.declaration.CtType<?> t = getAnnotationType().getDeclaration();
        if (t != null) {
            spoon.reflect.declaration.CtMethod<?> method = t.getMethod(name);
            return method.getType().getActualClass();
        }
        java.lang.Class<?> c = getAnnotationType().getActualClass();
        for (java.lang.reflect.Method m : c.getMethods()) {
            if (m.getName().equals(name)) {
                return m.getReturnType();
            }
        }
        return null;
    }

    public spoon.reflect.reference.CtTypeReference<A> getAnnotationType() {
        return annotationType;
    }

    private java.lang.Object getDefaultValue(java.lang.String fieldName) {
        java.lang.Object ret = null;
        spoon.reflect.declaration.CtAnnotationType<?> at = ((spoon.reflect.declaration.CtAnnotationType<?>) (getAnnotationType().getDeclaration()));
        if (at != null) {
            spoon.reflect.declaration.CtAnnotationMethod<?> f = ((spoon.reflect.declaration.CtAnnotationMethod) (at.getMethod(fieldName)));
            ret = f.getDefaultExpression();
        }
        return ret;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> T getElementValue(java.lang.String key) {
        java.lang.Object ret = this.elementValues.get(key);
        if (ret == null) {
            ret = getDefaultValue(key);
        }
        if (ret == null) {
            ret = getReflectValue(key);
        }
        java.lang.Class<?> type = getElementType(key);
        ret = this.convertValue(ret);
        if (type.isPrimitive()) {
            if ((type == (boolean.class)) && ((ret.getClass()) != (boolean.class))) {
                ret = java.lang.Boolean.parseBoolean(ret.toString());
            }else
                if ((type == (byte.class)) && ((ret.getClass()) != (byte.class))) {
                    ret = java.lang.Byte.parseByte(ret.toString());
                }else
                    if ((type == (char.class)) && ((ret.getClass()) != (char.class))) {
                        ret = ret.toString().charAt(0);
                    }else
                        if ((type == (double.class)) && ((ret.getClass()) != (double.class))) {
                            ret = java.lang.Double.parseDouble(ret.toString());
                        }else
                            if ((type == (float.class)) && ((ret.getClass()) != (float.class))) {
                                ret = java.lang.Float.parseFloat(ret.toString());
                            }else
                                if ((type == (int.class)) && ((ret.getClass()) != (int.class))) {
                                    ret = java.lang.Integer.parseInt(ret.toString());
                                }else
                                    if ((type == (long.class)) && ((ret.getClass()) != (long.class))) {
                                        ret = java.lang.Long.parseLong(ret.toString());
                                    }else
                                        if ((type == (short.class)) && ((ret.getClass()) != (short.class))) {
                                            ret = java.lang.Short.parseShort(ret.toString());
                                        }







        }
        if (((type.isArray()) && (ret != null)) && ((ret.getClass()) != type)) {
            final java.lang.Object array = java.lang.reflect.Array.newInstance(ret.getClass(), 1);
            ((java.lang.Object[]) (array))[0] = ret;
            ret = array;
        }
        return ((T) (ret));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtExpression> T getValue(java.lang.String key) {
        return ((T) (this.elementValues.get(key)));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtExpression> T getWrappedValue(java.lang.String key) {
        spoon.reflect.code.CtExpression ctExpression = this.getValue(key);
        if (ctExpression instanceof spoon.reflect.code.CtLiteral) {
            spoon.reflect.reference.CtTypeReference typeReference = this.getAnnotationType();
            spoon.reflect.declaration.CtType type = typeReference.getTypeDeclaration();
            if (type != null) {
                spoon.reflect.declaration.CtMethod method = type.getMethod(key);
                if (method != null) {
                    spoon.reflect.reference.CtTypeReference returnType = method.getType();
                    if (returnType instanceof spoon.reflect.reference.CtArrayTypeReference) {
                        spoon.reflect.code.CtNewArray newArray = getFactory().Core().createNewArray();
                        spoon.reflect.reference.CtArrayTypeReference typeReference2 = this.getFactory().createArrayTypeReference();
                        typeReference2.setComponentType(ctExpression.getType().clone());
                        newArray.setType(typeReference2);
                        newArray.addElement(ctExpression.clone());
                        return ((T) (newArray));
                    }
                }
            }
        }
        return ((T) (ctExpression));
    }

    public java.util.Map<java.lang.String, java.lang.Object> getElementValues() {
        java.util.TreeMap<java.lang.String, java.lang.Object> res = new java.util.TreeMap<>();
        for (java.util.Map.Entry<java.lang.String, spoon.reflect.code.CtExpression> elementValue : elementValues.entrySet()) {
            res.put(elementValue.getKey(), elementValue.getValue());
        }
        return res;
    }

    @java.lang.Override
    public java.util.Map<java.lang.String, spoon.reflect.code.CtExpression> getValues() {
        return java.util.Collections.unmodifiableMap(elementValues);
    }

    private java.lang.Object getReflectValue(java.lang.String fieldname) {
        try {
            java.lang.Class<?> c = getAnnotationType().getActualClass();
            java.lang.reflect.Method m = c.getMethod(fieldname);
            return m.getDefaultValue();
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <T extends spoon.reflect.declaration.CtAnnotation<A>> T setAnnotationType(spoon.reflect.reference.CtTypeReference<? extends java.lang.annotation.Annotation> annotationType) {
        if (annotationType != null) {
            annotationType.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TYPE, annotationType, this.annotationType);
        this.annotationType = ((spoon.reflect.reference.CtTypeReference<A>) (annotationType));
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtAnnotation<A>> T setElementValues(java.util.Map<java.lang.String, java.lang.Object> values) {
        getFactory().getEnvironment().getModelChangeListener().onMapDeleteAll(this, spoon.reflect.path.CtRole.VALUE, this.elementValues, new java.util.HashMap<>(elementValues));
        this.elementValues.clear();
        for (java.util.Map.Entry<java.lang.String, java.lang.Object> e : values.entrySet()) {
            addValue(e.getKey(), e.getValue());
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtAnnotation<A>> T setValues(java.util.Map<java.lang.String, spoon.reflect.code.CtExpression> values) {
        getFactory().getEnvironment().getModelChangeListener().onMapDeleteAll(this, spoon.reflect.path.CtRole.VALUE, this.elementValues, new java.util.HashMap<>(elementValues));
        this.elementValues.clear();
        for (java.util.Map.Entry<java.lang.String, spoon.reflect.code.CtExpression> e : values.entrySet()) {
            addValue(e.getKey(), e.getValue());
        }
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtElement getAnnotatedElement() {
        return this.getParent();
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtAnnotatedElementType getAnnotatedElementType() {
        spoon.reflect.declaration.CtElement annotatedElement = this.getAnnotatedElement();
        return spoon.reflect.declaration.CtAnnotation.getAnnotatedElementTypeForCtElement(annotatedElement);
    }

    @java.lang.SuppressWarnings("unchecked")
    public A getActualAnnotation() {
        class AnnotationInvocationHandler implements java.lang.reflect.InvocationHandler {
            spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation;

            AnnotationInvocationHandler(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation) {
                super();
                this.annotation = annotation;
            }

            public java.lang.Object invoke(java.lang.Object proxy, java.lang.reflect.Method method, java.lang.Object[] args) throws java.lang.Throwable {
                java.lang.String fieldname = method.getName();
                if ("toString".equals(fieldname)) {
                    return spoon.support.reflect.declaration.CtAnnotationImpl.this.toString();
                }else
                    if ("annotationType".equals(fieldname)) {
                        return annotation.getAnnotationType().getActualClass();
                    }

                java.lang.Object ret = getElementValue(fieldname);
                if (ret instanceof spoon.reflect.code.CtLiteral<?>) {
                    spoon.reflect.code.CtLiteral<?> l = ((spoon.reflect.code.CtLiteral<?>) (ret));
                    return l.getValue();
                }
                return ret;
            }
        }
        return ((A) (java.lang.reflect.Proxy.newProxyInstance(annotationType.getActualClass().getClassLoader(), new java.lang.Class[]{ annotationType.getActualClass() }, new AnnotationInvocationHandler(this))));
    }

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_SHADOW)
    boolean isShadow;

    @java.lang.Override
    public boolean isShadow() {
        return isShadow;
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtShadowable> E setShadow(boolean isShadow) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_SHADOW, isShadow, this.isShadow);
        this.isShadow = isShadow;
        return ((E) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtAnnotation<A> clone() {
        return ((spoon.reflect.declaration.CtAnnotation<A>) (super.clone()));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> getTypeCasts() {
        return spoon.support.reflect.declaration.CtElementImpl.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.code.CtExpression<A>> C setTypeCasts(java.util.List<spoon.reflect.reference.CtTypeReference<?>> casts) {
        return ((C) (this));
    }
}


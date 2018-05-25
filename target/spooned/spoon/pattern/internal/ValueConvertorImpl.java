package spoon.pattern.internal;


public class ValueConvertorImpl implements spoon.pattern.internal.ValueConvertor {
    public ValueConvertorImpl() {
    }

    @java.lang.Override
    public <T> T getValueAs(spoon.reflect.factory.Factory factory, java.lang.Object value, java.lang.Class<T> valueClass) {
        if (valueClass.isInstance(value)) {
            return cloneIfNeeded(valueClass.cast(value));
        }
        if (spoon.reflect.code.CtExpression.class.isAssignableFrom(valueClass)) {
            if (value instanceof java.lang.Class) {
                return ((T) (factory.Code().createClassAccess(factory.Type().createReference(((java.lang.Class) (value))))));
            }
            if (value instanceof spoon.reflect.reference.CtTypeReference) {
                spoon.reflect.reference.CtTypeReference<?> tr = ((spoon.reflect.reference.CtTypeReference<?>) (value));
                return ((T) (factory.Code().createClassAccess(tr)));
            }
            if (((((value == null) || (value instanceof java.lang.String)) || (value instanceof java.lang.Number)) || (value instanceof java.lang.Boolean)) || (value instanceof java.lang.Character)) {
                return ((T) (factory.Code().createLiteral(value)));
            }
            if (value.getClass().isArray()) {
                java.lang.Class<?> itemClass = value.getClass().getComponentType();
                if (spoon.reflect.code.CtExpression.class.isAssignableFrom(itemClass)) {
                    spoon.reflect.code.CtNewArray<java.lang.Object> arr = factory.Core().createNewArray().setType(factory.Type().objectType());
                    for (spoon.reflect.code.CtExpression expr : ((spoon.reflect.code.CtExpression[]) (value))) {
                        arr.addElement(expr);
                    }
                    return ((T) (arr));
                }
                @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
                spoon.reflect.code.CtNewArray<?> arr = factory.Core().createNewArray().setType(factory.Type().createArrayReference(itemClass.getName()));
                for (java.lang.Object v : ((java.lang.Object[]) (value))) {
                    if (((((v == null) || (v instanceof java.lang.String)) || (v instanceof java.lang.Number)) || (v instanceof java.lang.Boolean)) || (v instanceof java.lang.Character)) {
                        arr.addElement(factory.Code().createLiteral(v));
                    }else {
                        throw new spoon.SpoonException(((("Parameter value item class: " + (v.getClass().getName())) + " cannot be converted to class is: ") + (valueClass.getName())));
                    }
                }
                return ((T) (arr));
            }
        }
        if (spoon.reflect.code.CtStatement.class.isAssignableFrom(valueClass)) {
            if (value == null) {
                return null;
            }
            if (value instanceof java.util.List) {
                java.util.List list = ((java.util.List) (value));
                if ((list.size()) == 0) {
                    return null;
                }
                if ((list.size()) == 1) {
                    return ((T) (list.get(0)));
                }
                spoon.reflect.code.CtBlock block = factory.createBlock();
                block.setImplicit(true);
                for (spoon.reflect.code.CtStatement statement : ((java.lang.Iterable<spoon.reflect.code.CtStatement>) (value))) {
                    block.addStatement(statement);
                }
                return ((T) (block));
            }
        }
        if (valueClass.equals(java.lang.String.class)) {
            if (value instanceof spoon.reflect.declaration.CtNamedElement) {
                return ((T) (((spoon.reflect.declaration.CtNamedElement) (value)).getSimpleName()));
            }else
                if (value instanceof spoon.reflect.reference.CtReference) {
                    return ((T) (((spoon.reflect.reference.CtReference) (value)).getSimpleName()));
                }else
                    if (value instanceof java.lang.Class) {
                        return ((T) (((java.lang.Class) (value)).getSimpleName()));
                    }else
                        if (value instanceof spoon.reflect.code.CtInvocation) {
                            return ((T) (spoon.pattern.internal.ValueConvertorImpl.getShortSignatureForJavadoc(((spoon.reflect.code.CtInvocation<?>) (value)).getExecutable())));
                        }else
                            if (value instanceof spoon.reflect.reference.CtExecutableReference) {
                                return ((T) (spoon.pattern.internal.ValueConvertorImpl.getShortSignatureForJavadoc(((spoon.reflect.reference.CtExecutableReference<?>) (value)))));
                            }else
                                if (value instanceof spoon.reflect.declaration.CtExecutable) {
                                    return ((T) (spoon.pattern.internal.ValueConvertorImpl.getShortSignatureForJavadoc(((spoon.reflect.declaration.CtExecutable<?>) (value)).getReference())));
                                }else
                                    if (value instanceof spoon.reflect.code.CtLiteral) {
                                        java.lang.Object val = ((spoon.reflect.code.CtLiteral<java.lang.Object>) (value)).getValue();
                                        return val == null ? null : ((T) (val.toString()));
                                    }else
                                        if (value instanceof java.lang.Enum) {
                                            return ((T) (((java.lang.Enum) (value)).name()));
                                        }







            throw new spoon.SpoonException((("Parameter value has unexpected class: " + (value.getClass().getName())) + ", whose conversion to String is not supported"));
        }
        if (spoon.reflect.reference.CtTypeReference.class.isAssignableFrom(valueClass)) {
            if (value == null) {
                throw new spoon.SpoonException("The null value is not valid substitution for CtTypeReference");
            }
            if (value instanceof java.lang.Class) {
                return ((T) (factory.Type().createReference(((java.lang.Class<?>) (value)))));
            }else
                if (value instanceof spoon.reflect.reference.CtTypeReference) {
                    return ((T) (((spoon.reflect.reference.CtTypeReference<?>) (value)).clone()));
                }else
                    if (value instanceof spoon.reflect.declaration.CtType) {
                        return ((T) (((spoon.reflect.declaration.CtType<?>) (value)).getReference()));
                    }else
                        if (value instanceof java.lang.String) {
                            return ((T) (factory.Type().createReference(((java.lang.String) (value)))));
                        }else {
                            throw new java.lang.RuntimeException("unsupported reference substitution");
                        }



        }
        throw new spoon.SpoonException(((("Parameter value class: " + (value.getClass().getName())) + " cannot be converted to class is: ") + (valueClass.getName())));
    }

    private static java.lang.String getShortSignatureForJavadoc(spoon.reflect.reference.CtExecutableReference<?> ref) {
        spoon.support.visitor.SignaturePrinter sp = new spoon.support.visitor.SignaturePrinter();
        sp.writeNameAndParameters(ref);
        return ((ref.getDeclaringType().getSimpleName()) + (spoon.reflect.declaration.CtExecutable.EXECUTABLE_SEPARATOR)) + (sp.getSignature());
    }

    @java.lang.SuppressWarnings("unchecked")
    protected <T> T cloneIfNeeded(T value) {
        if (value instanceof spoon.reflect.declaration.CtElement) {
            return ((T) (((spoon.reflect.declaration.CtElement) (value)).clone()));
        }
        return value;
    }
}


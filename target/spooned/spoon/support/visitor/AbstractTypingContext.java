package spoon.support.visitor;


abstract class AbstractTypingContext implements spoon.support.visitor.GenericTypeAdapter {
    protected AbstractTypingContext() {
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> adaptType(spoon.reflect.declaration.CtTypeInformation type) {
        spoon.reflect.reference.CtTypeReference<?> result;
        boolean isCopy = false;
        if (type instanceof spoon.reflect.reference.CtTypeReference<?>) {
            if (type instanceof spoon.reflect.reference.CtTypeParameterReference) {
                return adaptTypeParameterReference(((spoon.reflect.reference.CtTypeParameterReference) (type)));
            }
            result = ((spoon.reflect.reference.CtTypeReference<?>) (type));
        }else {
            if (type instanceof spoon.reflect.declaration.CtTypeParameter) {
                return adaptTypeParameter(((spoon.reflect.declaration.CtTypeParameter) (type)));
            }
            spoon.reflect.declaration.CtType<?> t = ((spoon.reflect.declaration.CtType<?>) (type));
            result = t.getFactory().Type().createReference(t, true);
            isCopy = true;
        }
        if ((result.getActualTypeArguments().size()) > 0) {
            if (isCopy == false) {
                spoon.reflect.declaration.CtElement parent = result.getParent();
                result = result.clone();
                result.setParent(parent);
                java.util.List<spoon.reflect.reference.CtTypeReference<?>> actTypeArgs = new java.util.ArrayList<>(result.getActualTypeArguments());
                for (int i = 0; i < (actTypeArgs.size()); i++) {
                    spoon.reflect.reference.CtTypeReference adaptedTypeArgs = adaptType(actTypeArgs.get(i));
                    if (adaptedTypeArgs != null) {
                        actTypeArgs.set(i, adaptedTypeArgs.clone());
                    }
                }
                result.setActualTypeArguments(actTypeArgs);
            }
        }
        return result;
    }

    private spoon.reflect.reference.CtTypeReference<?> adaptTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference typeParamRef) {
        if (typeParamRef instanceof spoon.reflect.reference.CtWildcardReference) {
            return adaptTypeParameterReferenceBoundingType(typeParamRef, typeParamRef.getBoundingType());
        }
        spoon.reflect.reference.CtTypeReference<?> typeRefAdapted = adaptTypeParameter(typeParamRef.getDeclaration());
        return typeRefAdapted;
    }

    private spoon.reflect.reference.CtTypeReference<?> adaptTypeParameterReferenceBoundingType(spoon.reflect.reference.CtTypeParameterReference typeParamRef, spoon.reflect.reference.CtTypeReference<?> boundingType) {
        spoon.reflect.reference.CtTypeParameterReference typeParamRefAdapted = typeParamRef.clone();
        typeParamRefAdapted.setParent(typeParamRef.getParent());
        typeParamRefAdapted.setBoundingType((boundingType.equals(boundingType.getFactory().Type().getDefaultBoundingType()) ? boundingType.getFactory().Type().getDefaultBoundingType() : adaptType(boundingType)));
        return typeParamRefAdapted;
    }

    protected abstract spoon.reflect.reference.CtTypeReference<?> adaptTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParam);
}


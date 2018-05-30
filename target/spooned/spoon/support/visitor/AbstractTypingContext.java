/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.visitor;


/**
 * Implements common adapting algorithm of {@link ClassTypingContext} and {@link MethodTypingContext}
 */
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
            // we have to adapt actual type arguments recursive too
            if (isCopy == false) {
                spoon.reflect.declaration.CtElement parent = result.getParent();
                result = result.clone();
                result.setParent(parent);
                java.util.List<spoon.reflect.reference.CtTypeReference<?>> actTypeArgs = new java.util.ArrayList<>(result.getActualTypeArguments());
                for (int i = 0; i < (actTypeArgs.size()); i++) {
                    spoon.reflect.reference.CtTypeReference adaptedTypeArgs = adaptType(actTypeArgs.get(i));
                    // for some type argument we might return null to avoid recursive calls
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

    /**
     * adapts `typeParam` to the {@link CtTypeReference}
     * of scope of this {@link GenericTypeAdapter}
     * In can be {@link CtTypeParameterReference} again - depending actual type arguments of this {@link GenericTypeAdapter}.
     *
     * @param typeParam
     * 		to be resolved {@link CtTypeParameter}
     * @return {@link CtTypeReference} or {@link CtTypeParameterReference} adapted to scope of this {@link GenericTypeAdapter}
     * or null if `typeParam` cannot be adapted to target `scope`
     */
    protected abstract spoon.reflect.reference.CtTypeReference<?> adaptTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParam);
}


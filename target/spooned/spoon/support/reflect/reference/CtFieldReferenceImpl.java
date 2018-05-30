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
package spoon.support.reflect.reference;


public class CtFieldReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtFieldReference<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    spoon.reflect.reference.CtTypeReference<?> declaringType;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_FINAL)
    boolean fina = false;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_STATIC)
    boolean stat = false;

    public CtFieldReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtFieldReference(this);
    }

    @java.lang.Override
    public java.lang.reflect.Member getActualField() {
        try {
            if (getDeclaringType().getActualClass().isAnnotation()) {
                return getDeclaringType().getActualClass().getDeclaredMethod(getSimpleName());
            }
            return getDeclaringType().getActualClass().getDeclaredField(getSimpleName());
        } catch (java.lang.Exception e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @java.lang.Override
    protected java.lang.reflect.AnnotatedElement getActualAnnotatedElement() {
        return ((java.lang.reflect.AnnotatedElement) (getActualField()));
    }

    // @Override
    // public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
    // A annotation = super.getAnnotation(annotationType);
    // if (annotation != null) {
    // return annotation;
    // }
    // // use reflection
    // Class<?> c = getDeclaringType().getActualClass();
    // if (c.isAnnotation()) {
    // for (Method m : RtHelper.getAllMethods(c)) {
    // if (!getSimpleName().equals(m.getName())) {
    // continue;
    // }
    // m.setAccessible(true);
    // return m.getAnnotation(annotationType);
    // }
    // } else {
    // for (Field f : RtHelper.getAllFields(c)) {
    // if (!getSimpleName().equals(f.getName())) {
    // continue;
    // }
    // f.setAccessible(true);
    // return f.getAnnotation(annotationType);
    // }
    // }
    // return null;
    // }
    // @Override
    // public Annotation[] getAnnotations() {
    // Annotation[] annotations = super.getAnnotations();
    // if (annotations != null) {
    // return annotations;
    // }
    // // use reflection
    // Class<?> c = getDeclaringType().getActualClass();
    // for (Field f : RtHelper.getAllFields(c)) {
    // if (!getSimpleName().equals(f.getName())) {
    // continue;
    // }
    // f.setAccessible(true);
    // return f.getAnnotations();
    // }
    // // If the fields belong to an annotation type, they are actually
    // // methods
    // for (Method m : RtHelper.getAllMethods(c)) {
    // if (!getSimpleName().equals(m.getName())) {
    // continue;
    // }
    // m.setAccessible(true);
    // return m.getAnnotations();
    // }
    // return null;
    // }
    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.declaration.CtField<T> getDeclaration() {
        return fromDeclaringType();
    }

    private spoon.reflect.declaration.CtField<T> fromDeclaringType() {
        if ((declaringType) == null) {
            return null;
        }
        spoon.reflect.declaration.CtType<?> type = declaringType.getDeclaration();
        if (type != null) {
            return ((spoon.reflect.declaration.CtField<T>) (type.getField(getSimpleName())));
        }
        return null;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtField<T> getFieldDeclaration() {
        if ((declaringType) == null) {
            return null;
        }
        spoon.reflect.declaration.CtType<?> type = declaringType.getTypeDeclaration();
        if (type != null) {
            final spoon.reflect.declaration.CtField<T> ctField = ((spoon.reflect.declaration.CtField<T>) (type.getField(getSimpleName())));
            if ((ctField == null) && (type instanceof spoon.reflect.declaration.CtEnum)) {
                return ((spoon.reflect.declaration.CtEnum) (type)).getEnumValue(getSimpleName());
            }
            return ctField;
        }
        return null;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getDeclaringType() {
        return declaringType;
    }

    @java.lang.Override
    public java.lang.String getQualifiedName() {
        return ((getDeclaringType().getQualifiedName()) + "#") + (getSimpleName());
    }

    @java.lang.Override
    public boolean isFinal() {
        return fina;
    }

    /**
     * Tells if the referenced field is static.
     */
    @java.lang.Override
    public boolean isStatic() {
        return stat;
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtFieldReference<T>> C setDeclaringType(spoon.reflect.reference.CtTypeReference<?> declaringType) {
        if (declaringType != null) {
            declaringType.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.DECLARING_TYPE, declaringType, this.declaringType);
        this.declaringType = declaringType;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtFieldReference<T>> C setFinal(boolean fina) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_FINAL, fina, this.fina);
        this.fina = fina;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtFieldReference<T>> C setStatic(boolean stat) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_STATIC, stat, this.stat);
        this.stat = stat;
        return ((C) (this));
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        spoon.reflect.declaration.CtVariable<?> v = getDeclaration();
        if (v != null) {
            return v.getModifiers();
        }
        java.lang.reflect.Member m = getActualField();
        if (m != null) {
            return spoon.support.util.RtHelper.getModifiers(m.getModifiers());
        }
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    public spoon.reflect.reference.CtFieldReference<T> clone() {
        return ((spoon.reflect.reference.CtFieldReference<T>) (super.clone()));
    }
}


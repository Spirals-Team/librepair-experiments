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
package spoon.support.reflect.declaration;


/**
 * The implementation for {@link spoon.reflect.declaration.CtClass}.
 *
 * @author Renaud Pawlak
 */
public class CtClassImpl<T extends java.lang.Object> extends spoon.support.reflect.declaration.CtTypeImpl<T> implements spoon.reflect.declaration.CtClass<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.SUPER_TYPE)
    spoon.reflect.reference.CtTypeReference<?> superClass;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor v) {
        v.visitCtClass(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtAnonymousExecutable> getAnonymousExecutables() {
        java.util.List<spoon.reflect.declaration.CtAnonymousExecutable> anonymousExecutables = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if (typeMember instanceof spoon.reflect.declaration.CtAnonymousExecutable) {
                anonymousExecutables.add(((spoon.reflect.declaration.CtAnonymousExecutable) (typeMember)));
            }
        }
        return java.util.Collections.unmodifiableList(anonymousExecutables);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtConstructor<T> getConstructor(spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        for (spoon.reflect.declaration.CtTypeMember typeMember : getTypeMembers()) {
            if (!(typeMember instanceof spoon.reflect.declaration.CtConstructor)) {
                continue;
            }
            spoon.reflect.declaration.CtConstructor<T> c = ((spoon.reflect.declaration.CtConstructor<T>) (typeMember));
            boolean cont = (c.getParameters().size()) == (parameterTypes.length);
            for (int i = 0; (cont && (i < (c.getParameters().size()))) && (i < (parameterTypes.length)); i++) {
                if (!(parameterTypes[i].getQualifiedName().equals(c.getParameters().get(i).getType().getQualifiedName()))) {
                    cont = false;
                }
            }
            if (cont) {
                return c;
            }
        }
        return null;
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtConstructor<T>> getConstructors() {
        java.util.Set<spoon.reflect.declaration.CtConstructor<T>> constructors = new spoon.support.util.SignatureBasedSortedSet<>();
        for (spoon.reflect.declaration.CtTypeMember typeMember : typeMembers) {
            if (typeMember instanceof spoon.reflect.declaration.CtConstructor) {
                constructors.add(((spoon.reflect.declaration.CtConstructor<T>) (typeMember)));
            }
        }
        return java.util.Collections.unmodifiableSet(constructors);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtClass<T>> C addAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable e) {
        if (e == null) {
            return ((C) (this));
        }
        e.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE, typeMembers, e);
        return addTypeMember(e);
    }

    @java.lang.Override
    public boolean removeAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable e) {
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE, typeMembers, typeMembers.indexOf(e), e);
        return removeTypeMember(e);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getSuperclass() {
        return superClass;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtClass<T>> C setAnonymousExecutables(java.util.List<spoon.reflect.declaration.CtAnonymousExecutable> anonymousExecutables) {
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE, typeMembers, new java.util.ArrayList<>(getAnonymousExecutables()));
        if ((anonymousExecutables == null) || (anonymousExecutables.isEmpty())) {
            this.typeMembers.removeAll(getAnonymousExecutables());
            return ((C) (this));
        }
        typeMembers.removeAll(getAnonymousExecutables());
        for (spoon.reflect.declaration.CtAnonymousExecutable exec : anonymousExecutables) {
            addAnonymousExecutable(exec);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtClass<T>> C setConstructors(java.util.Set<spoon.reflect.declaration.CtConstructor<T>> constructors) {
        java.util.Set<spoon.reflect.declaration.CtConstructor<T>> oldConstructor = getConstructors();
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.CONSTRUCTOR, typeMembers, oldConstructor);
        if ((constructors == null) || (constructors.isEmpty())) {
            this.typeMembers.removeAll(oldConstructor);
            return ((C) (this));
        }
        typeMembers.removeAll(oldConstructor);
        for (spoon.reflect.declaration.CtConstructor<T> constructor : constructors) {
            addConstructor(constructor);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtClass<T>> C addConstructor(spoon.reflect.declaration.CtConstructor<T> constructor) {
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.CONSTRUCTOR, typeMembers, constructor);
        return addTypeMember(constructor);
    }

    @java.lang.Override
    public void removeConstructor(spoon.reflect.declaration.CtConstructor<T> constructor) {
        removeTypeMember(constructor);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass) {
        if (superClass != null) {
            superClass.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.SUPER_TYPE, superClass, this.superClass);
        this.superClass = superClass;
        return ((C) (this));
    }

    @java.lang.Override
    public boolean isClass() {
        return true;
    }

    @java.lang.Override
    public boolean isAnonymous() {
        try {
            java.lang.Integer.parseInt(getSimpleName());
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
        return true;
    }

    @java.lang.Override
    public boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> type) {
        return getReference().isSubtypeOf(type);
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertAfter(spoon.reflect.code.CtStatement statement) {
        spoon.support.reflect.code.CtStatementImpl.insertAfter(this, statement);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertAfter(spoon.reflect.code.CtStatementList statements) {
        spoon.support.reflect.code.CtStatementImpl.insertAfter(this, statements);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertBefore(spoon.reflect.code.CtStatement statement) {
        spoon.support.reflect.code.CtStatementImpl.insertBefore(this, statement);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtStatement> C insertBefore(spoon.reflect.code.CtStatementList statements) {
        spoon.support.reflect.code.CtStatementImpl.insertBefore(this, statements);
        return ((C) (this));
    }

    @java.lang.Override
    public java.lang.String getLabel() {
        return null;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.code.CtStatement> C setLabel(java.lang.String label) {
        return ((C) (this));
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <R extends spoon.reflect.code.CtCodeElement> R partiallyEvaluate() {
        spoon.support.reflect.eval.VisitorPartialEvaluator eval = new spoon.support.reflect.eval.VisitorPartialEvaluator();
        return eval.evaluate(((R) (this)));
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getDeclaredExecutables() {
        java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> declaredExecutables = super.getDeclaredExecutables();
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> l = new java.util.ArrayList<>(((declaredExecutables.size()) + (getConstructors().size())));
        l.addAll(declaredExecutables);
        for (spoon.reflect.declaration.CtExecutable<?> c : getConstructors()) {
            l.add(c.getReference());
        }
        return java.util.Collections.unmodifiableList(l);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtClass<T> clone() {
        return ((spoon.reflect.declaration.CtClass<T>) (super.clone()));
    }

    @java.lang.Override
    public T newInstance() {
        try {
            spoon.support.compiler.jdt.JDTBasedSpoonCompiler spooner = new spoon.support.compiler.jdt.JDTBasedSpoonCompiler(getFactory());
            spooner.compile(spoon.SpoonModelBuilder.InputType.CTTYPES);// compiling the types of the factory

            java.lang.Class<?> klass = new NewInstanceClassloader(spooner.getBinaryOutputDirectory()).loadClass(getQualifiedName());
            return ((T) (klass.newInstance()));
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException(e);
        }
    }

    private class NewInstanceClassloader extends java.net.URLClassLoader {
        NewInstanceClassloader(java.io.File binaryOutputDirectory) throws java.net.MalformedURLException {
            super(new java.net.URL[]{ binaryOutputDirectory.toURI().toURL() });
        }

        @java.lang.Override
        public java.lang.Class<?> loadClass(java.lang.String s) throws java.lang.ClassNotFoundException {
            try {
                return findClass(s);
            } catch (java.lang.Exception e) {
                return super.loadClass(s);
            }
        }
    }

    /**
     * adding the constructors and static executables
     */
    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getAllExecutables() {
        java.util.Set<spoon.reflect.reference.CtExecutableReference<?>> l = ((java.util.Set<spoon.reflect.reference.CtExecutableReference<?>>) (super.getAllExecutables()));
        for (spoon.reflect.declaration.CtConstructor c : getConstructors()) {
            l.add(c.getReference());
        }
        for (spoon.reflect.declaration.CtExecutable<?> anon : getAnonymousExecutables()) {
            l.add(anon.getReference());
        }
        return l;
    }
}


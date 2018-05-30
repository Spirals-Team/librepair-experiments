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


public abstract class CtReferenceImpl extends spoon.support.reflect.declaration.CtElementImpl implements java.io.Serializable , spoon.reflect.reference.CtReference {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.NAME)
    protected java.lang.String simplename = "";

    public CtReferenceImpl() {
        super();
    }

    protected abstract java.lang.reflect.AnnotatedElement getActualAnnotatedElement();

    @java.lang.Override
    public java.lang.String getSimpleName() {
        return simplename;
    }

    @java.lang.Override
    public <T extends spoon.reflect.reference.CtReference> T setSimpleName(java.lang.String simplename) {
        spoon.reflect.factory.Factory factory = getFactory();
        if (factory == null) {
            this.simplename = simplename;
            return ((T) (this));
        }
        if (factory instanceof spoon.reflect.factory.FactoryImpl) {
            simplename = ((spoon.reflect.factory.FactoryImpl) (factory)).dedup(simplename);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.NAME, simplename, this.simplename);
        this.simplename = simplename;
        return ((T) (this));
    }

    @spoon.support.UnsettableProperty
    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement> E setComments(java.util.List<spoon.reflect.code.CtComment> comments) {
        return ((E) (this));
    }

    @java.lang.Override
    public java.lang.String toString() {
        spoon.reflect.visitor.DefaultJavaPrettyPrinter printer = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(getFactory().getEnvironment());
        printer.scan(this);
        return printer.toString();
    }

    @java.lang.Override
    public abstract void accept(spoon.reflect.visitor.CtVisitor visitor);

    @java.lang.Override
    public spoon.reflect.reference.CtReference clone() {
        return ((spoon.reflect.reference.CtReference) (super.clone()));
    }
}


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


public class CtImportImpl extends spoon.support.reflect.declaration.CtElementImpl implements spoon.reflect.declaration.CtImport {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IMPORT_REFERENCE)
    private spoon.reflect.reference.CtReference localReference;

    public CtImportImpl() {
        super();
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtImportKind getImportKind() {
        if ((this.localReference) == null) {
            return null;
        }
        if ((this.localReference) instanceof spoon.reflect.reference.CtFieldReference) {
            return spoon.reflect.declaration.CtImportKind.FIELD;
        }else
            if ((this.localReference) instanceof spoon.reflect.reference.CtExecutableReference) {
                return spoon.reflect.declaration.CtImportKind.METHOD;
            }else
                if ((this.localReference) instanceof spoon.reflect.reference.CtPackageReference) {
                    return spoon.reflect.declaration.CtImportKind.ALL_TYPES;
                }else
                    if ((this.localReference) instanceof spoon.support.reflect.reference.CtWildcardStaticTypeMemberReferenceImpl) {
                        return spoon.reflect.declaration.CtImportKind.ALL_STATIC_MEMBERS;
                    }else
                        if ((this.localReference) instanceof spoon.reflect.reference.CtTypeReference) {
                            return spoon.reflect.declaration.CtImportKind.TYPE;
                        }else {
                            throw new spoon.SpoonException("Only CtFieldReference, CtExecutableReference, CtPackageReference and CtTypeReference are accepted reference types.");
                        }




    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtImport> T setReference(spoon.reflect.reference.CtReference reference) {
        if (reference != null) {
            reference.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IMPORT_REFERENCE, reference, this.localReference);
        this.localReference = reference;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtReference getReference() {
        return this.localReference;
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtImport(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtImport clone() {
        return ((spoon.reflect.declaration.CtImport) (super.clone()));
    }
}


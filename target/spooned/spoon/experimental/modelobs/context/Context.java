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
package spoon.experimental.modelobs.context;


/**
 * defines the context of an action
 */
public abstract class Context {
    private spoon.reflect.declaration.CtElement elementWhereChangeHappens;

    private spoon.reflect.path.CtRole changedProperty;

    public Context(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole changedProperty) {
        this.elementWhereChangeHappens = element;
        this.changedProperty = changedProperty;
    }

    /**
     * the changed parent
     *
     * @return the changed parent
     */
    public spoon.reflect.declaration.CtElement getElementWhereChangeHappens() {
        return elementWhereChangeHappens;
    }

    /**
     * the role that has been modified
     *
     * @return the role that has been modified
     */
    public spoon.reflect.path.CtRole getChangedProperty() {
        return changedProperty;
    }
}


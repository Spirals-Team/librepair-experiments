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
package spoon.support.reflect.code;


public abstract class CtCodeElementImpl extends spoon.support.reflect.declaration.CtElementImpl implements spoon.reflect.code.CtCodeElement {
    private static final long serialVersionUID = 1L;

    public CtCodeElementImpl() {
        super();
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <R extends spoon.reflect.code.CtCodeElement> R partiallyEvaluate() {
        spoon.support.reflect.eval.VisitorPartialEvaluator eval = new spoon.support.reflect.eval.VisitorPartialEvaluator();
        return eval.evaluate(((R) (this)));
    }

    @java.lang.Override
    public spoon.reflect.code.CtCodeElement clone() {
        return ((spoon.reflect.code.CtCodeElement) (super.clone()));
    }
}


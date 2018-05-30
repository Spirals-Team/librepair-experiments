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
package spoon.support.reflect.eval;


/**
 * Simplifies an AST inline based on {@link VisitorPartialEvaluator} (wanring: the nodes are changed).
 */
public class InlinePartialEvaluator extends spoon.reflect.visitor.CtScanner {
    private final spoon.reflect.eval.PartialEvaluator eval;

    public InlinePartialEvaluator(spoon.reflect.eval.PartialEvaluator eval) {
        this.eval = eval;
    }

    @java.lang.Override
    protected void exit(spoon.reflect.declaration.CtElement e) {
        spoon.reflect.declaration.CtElement simplified = eval.evaluate(e);
        if (simplified != null) {
            e.replace(simplified);
        }
    }
}


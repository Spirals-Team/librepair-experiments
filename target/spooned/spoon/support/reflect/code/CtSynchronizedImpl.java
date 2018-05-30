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


public class CtSynchronizedImpl extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtSynchronized {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> block;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<?> expression;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtSynchronized(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtBlock<?> getBlock() {
        return block;
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<?> getExpression() {
        return expression;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtSynchronized> T setBlock(spoon.reflect.code.CtBlock<?> block) {
        if (block != null) {
            block.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.BODY, block, this.block);
        this.block = block;
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtSynchronized> T setExpression(spoon.reflect.code.CtExpression<?> expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.expression);
        this.expression = expression;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtSynchronized clone() {
        return ((spoon.reflect.code.CtSynchronized) (super.clone()));
    }
}


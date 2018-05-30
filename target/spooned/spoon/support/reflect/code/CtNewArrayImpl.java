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


public class CtNewArrayImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtNewArray<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.DIMENSION)
    java.util.List<spoon.reflect.code.CtExpression<java.lang.Integer>> dimensionExpressions = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    java.util.List<spoon.reflect.code.CtExpression<?>> expressions = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtNewArray(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtExpression<java.lang.Integer>> getDimensionExpressions() {
        return dimensionExpressions;
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtExpression<?>> getElements() {
        return expressions;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtNewArray<T>> C setDimensionExpressions(java.util.List<spoon.reflect.code.CtExpression<java.lang.Integer>> dimensionExpressions) {
        if ((dimensionExpressions == null) || (dimensionExpressions.isEmpty())) {
            this.dimensionExpressions = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.DIMENSION, this.dimensionExpressions, new java.util.ArrayList<>(this.dimensionExpressions));
        this.dimensionExpressions.clear();
        for (spoon.reflect.code.CtExpression<java.lang.Integer> expr : dimensionExpressions) {
            addDimensionExpression(expr);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtNewArray<T>> C addDimensionExpression(spoon.reflect.code.CtExpression<java.lang.Integer> dimension) {
        if (dimension == null) {
            return ((C) (this));
        }
        if ((dimensionExpressions) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtExpression<java.lang.Integer>>emptyList())) {
            dimensionExpressions = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.NEW_ARRAY_DEFAULT_EXPRESSIONS_CONTAINER_DEFAULT_CAPACITY);
        }
        dimension.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.DIMENSION, this.dimensionExpressions, dimension);
        dimensionExpressions.add(dimension);
        return ((C) (this));
    }

    @java.lang.Override
    public boolean removeDimensionExpression(spoon.reflect.code.CtExpression<java.lang.Integer> dimension) {
        if ((dimensionExpressions) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtExpression<java.lang.Integer>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.DIMENSION, dimensionExpressions, dimensionExpressions.indexOf(dimension), dimension);
        return dimensionExpressions.remove(dimension);
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtNewArray<T>> C setElements(java.util.List<spoon.reflect.code.CtExpression<?>> expressions) {
        if ((expressions == null) || (expressions.isEmpty())) {
            this.expressions = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.EXPRESSION, this.expressions, new java.util.ArrayList<>(this.expressions));
        this.expressions.clear();
        for (spoon.reflect.code.CtExpression<?> expr : expressions) {
            addElement(expr);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtNewArray<T>> C addElement(spoon.reflect.code.CtExpression<?> expression) {
        if (expression == null) {
            return ((C) (this));
        }
        if ((expressions) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtExpression<?>>emptyList())) {
            this.expressions = new java.util.ArrayList<>();
        }
        expression.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.EXPRESSION, this.expressions, expression);
        expressions.add(expression);
        return ((C) (this));
    }

    @java.lang.Override
    public boolean removeElement(spoon.reflect.code.CtExpression<?> expression) {
        if ((expressions) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtExpression<?>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.EXPRESSION, expressions, expressions.indexOf(expression), expression);
        return expressions.remove(expression);
    }

    @java.lang.Override
    public spoon.reflect.code.CtNewArray<T> clone() {
        return ((spoon.reflect.code.CtNewArray<T>) (super.clone()));
    }
}


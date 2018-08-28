/*
 * Copyright 2014 - 2018 Blazebit.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blazebit.persistence.parser.predicate;

import com.blazebit.persistence.parser.expression.Expression;

/**
 *
 * @author Christian Beikov
 * @author Moritz Becker
 * @since 1.0.0
 */
public class LePredicate extends QuantifiableBinaryExpressionPredicate {

    public LePredicate() {
    }

    public LePredicate(Expression left, Expression right) {
        this(left, right, false);
    }

    public LePredicate(Expression left, Expression right, boolean negated) {
        this(left, right, PredicateQuantifier.ONE, negated);
    }

    public LePredicate(Expression left, Expression right, PredicateQuantifier quantifier, boolean negated) {
        super(left, right, quantifier, negated);
    }

    @Override
    public LePredicate clone(boolean resolved) {
        return new LePredicate(left.clone(resolved), right.clone(resolved), quantifier, negated);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T accept(ResultVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

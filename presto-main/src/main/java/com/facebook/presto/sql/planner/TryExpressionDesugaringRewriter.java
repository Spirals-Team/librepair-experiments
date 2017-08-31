/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.presto.sql.planner;

import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.ExpressionRewriter;
import com.facebook.presto.sql.tree.ExpressionTreeRewriter;
import com.facebook.presto.sql.tree.FunctionCall;
import com.facebook.presto.sql.tree.LambdaExpression;
import com.facebook.presto.sql.tree.QualifiedName;
import com.facebook.presto.sql.tree.TryExpression;
import com.google.common.collect.ImmutableList;

import java.util.Map;

import static java.util.Objects.requireNonNull;

public class TryExpressionDesugaringRewriter
{
    private final Map<Symbol, Type> symbolTypes;
    private final SymbolAllocator symbolAllocator;

    public TryExpressionDesugaringRewriter(Map<Symbol, Type> symbolTypes, SymbolAllocator symbolAllocator)
    {
        this.symbolTypes = requireNonNull(symbolTypes, "symbolTypes is null");
        this.symbolAllocator = requireNonNull(symbolAllocator, "symbolAllocator is null");
    }

    public Expression rewrite(Expression expression)
    {
        return ExpressionTreeRewriter.rewriteWith(new Visitor(), expression);
    }

    public class Visitor
            extends ExpressionRewriter<Void>
    {
        @Override
        public Expression rewriteTryExpression(TryExpression node, Void context, ExpressionTreeRewriter<Void> treeRewriter)
        {
            Expression value = treeRewriter.rewrite(node.getInnerExpression(), context);

            return new FunctionCall(QualifiedName.of("$internal$try_function"), ImmutableList.of(
                    new LambdaExpression(ImmutableList.of(), value)));
        }
    }
}

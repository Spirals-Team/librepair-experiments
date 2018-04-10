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
package com.facebook.presto.sql.planner.iterative.rule;

import com.facebook.presto.sql.tree.CurrentUser;
import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.ExpressionRewriter;
import com.facebook.presto.sql.tree.ExpressionTreeRewriter;
import com.facebook.presto.sql.tree.FunctionCall;
import com.facebook.presto.sql.tree.QualifiedName;
import com.google.common.collect.ImmutableList;

public class DesugarCurrentUserRewriter
{
    private DesugarCurrentUserRewriter() {}

    public static Expression rewrite(Expression expression)
    {
        return ExpressionTreeRewriter.rewriteWith(new Visitor(), expression);
    }
    private static class Visitor
            extends ExpressionRewriter<Void>
    {
        @Override
        public Expression rewriteCurrentUser(CurrentUser node, Void context, ExpressionTreeRewriter<Void> treeRewriter)
        {
            return getCall(node);
        }
    }

    public static FunctionCall getCall(CurrentUser node)
    {
        return new FunctionCall(QualifiedName.of("current_user"), ImmutableList.of());
    }
}

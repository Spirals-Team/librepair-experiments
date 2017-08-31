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
import com.facebook.presto.sql.planner.iterative.rule.test.BaseRuleTest;
import com.facebook.presto.sql.tree.ArithmeticBinaryExpression;
import com.facebook.presto.sql.tree.DecimalLiteral;
import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.FunctionCall;
import com.facebook.presto.sql.tree.LambdaExpression;
import com.facebook.presto.sql.tree.QualifiedName;
import com.facebook.presto.sql.tree.TryExpression;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import java.util.Map;

import static com.facebook.presto.sql.tree.ArithmeticBinaryExpression.Type.ADD;
import static org.testng.Assert.assertEquals;

public class TestTryExpressionDesugaringRewriter
        extends BaseRuleTest
{
    @Test
    public void testTryExpressionDesugaringRewriter()
    {
        final Map<Symbol, Type> symbols = ImmutableMap.of();
        final SymbolAllocator allocator = new SymbolAllocator(symbols);
        TryExpressionDesugaringRewriter rewriter = new TryExpressionDesugaringRewriter(symbols, allocator);

        // 1 + try(2)
        Expression before = new ArithmeticBinaryExpression(
                ADD,
                new DecimalLiteral("1"),
                new TryExpression(new DecimalLiteral("2")));

        // 1 + try_function(() -> 2)
        Expression after = new ArithmeticBinaryExpression(
                ADD,
                new DecimalLiteral("1"),
                new FunctionCall(
                        QualifiedName.of("$internal$try_function"),
                        ImmutableList.of(new LambdaExpression(ImmutableList.of(), new DecimalLiteral("2")))));
        assertEquals(rewriter.rewrite(before), after);
    }
}

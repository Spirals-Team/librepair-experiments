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

import com.facebook.presto.matching.Captures;
import com.facebook.presto.matching.Pattern;
import com.facebook.presto.metadata.Metadata;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.parser.SqlParser;
import com.facebook.presto.sql.planner.Symbol;
import com.facebook.presto.sql.planner.iterative.Rule;
import com.facebook.presto.sql.planner.plan.Assignments;
import com.facebook.presto.sql.planner.plan.JoinNode;
import com.facebook.presto.sql.planner.plan.PlanNode;
import com.facebook.presto.sql.planner.plan.ProjectNode;
import com.facebook.presto.sql.tree.AstVisitor;
import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.FunctionCall;
import com.facebook.presto.sql.tree.Node;
import com.facebook.presto.sql.tree.NodeRef;
import com.facebook.presto.sql.tree.SymbolReference;
import com.google.common.base.Verify;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.facebook.presto.SystemSessionProperties.isDistributedJoinEnabled;
import static com.facebook.presto.sql.analyzer.ExpressionAnalyzer.getExpressionTypes;
import static com.facebook.presto.sql.planner.ExpressionNodeInliner.replaceExpression;
import static com.facebook.presto.sql.planner.plan.Patterns.join;
import static com.facebook.presto.util.SpatialJoinUtils.extractSupportedSpatialFunctions;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.Collections.emptyList;

/**
 * Applies to broadcast spatial joins using ST_Contains, ST_Intersects and ST_Distance functions.
 * <p>
 * Pushes non-trivial expressions of the spatial function arguments into projections on top of
 * join's child nodes.
 * <p>
 * For example, rewrites ST_Contains(ST_GeometryFromText(wkt), ST_Point(longitude, latitude)) join
 * as ST_Contains(st_geometryfromtext, st_point) with st_geometryfromtext -> 'ST_GeometryFromText(wkt)' and
 * st_point -> 'ST_Point(longitude, latitude)' projections on top of child nodes.
 */
public class PushDownSpatialProjections
        implements Rule<JoinNode>
{
    private static final Pattern<JoinNode> PATTERN = join().matching(node -> node.isCrossJoin() && node.getFilter().isPresent());

    private final Metadata metadata;
    private final SqlParser sqlParser;

    public PushDownSpatialProjections(Metadata metadata, SqlParser sqlParser)
    {
        this.metadata = metadata;
        this.sqlParser = sqlParser;
    }

    private static class SymbolCollector
            extends AstVisitor<Set<String>, Void>
    {
        @Override
        protected Set<String> visitNode(Node node, Void context)
        {
            Set<String> symbolNames = new HashSet<>();
            for (Node child : node.getChildren()) {
                symbolNames.addAll(process(child, context));
            }
            return symbolNames;
        }

        @Override
        protected Set<String> visitSymbolReference(SymbolReference symbolReference, Void context)
        {
            return Collections.singleton(symbolReference.getName());
        }
    }

    @Override
    public Pattern<JoinNode> getPattern()
    {
        return PATTERN;
    }

    @Override
    public Result apply(JoinNode node, Captures captures, Context context)
    {
        if (isDistributedJoinEnabled(context.getSession())) {
            return Result.empty();
        }

        Expression filter = node.getFilter().get();
        List<FunctionCall> spatialFunctions = extractSupportedSpatialFunctions(filter);
        for (FunctionCall spatialFunction : spatialFunctions) {
            List<Expression> arguments = spatialFunction.getArguments();
            Verify.verify(arguments.size() == 2);

            Expression firstArgument = arguments.get(0);
            Expression secondArgument = arguments.get(1);

            Set<String> firstSymbols = new SymbolCollector().process(firstArgument);
            Set<String> secondSymbols = new SymbolCollector().process(secondArgument);

            Optional<Symbol> newFirstSymbol = (firstArgument instanceof SymbolReference || firstSymbols.isEmpty()) ? Optional.empty() : Optional.of(newSymbol(context, firstArgument));
            Optional<Symbol> newSecondSymbol = (secondArgument instanceof SymbolReference || secondSymbols.isEmpty()) ? Optional.empty() : Optional.of(newSymbol(context, secondArgument));

            if (!newFirstSymbol.isPresent() && !newSecondSymbol.isPresent()) {
                return Result.empty();
            }

            Expression newFirstArgument = newFirstSymbol.map(Symbol::toSymbolReference).orElseGet(() -> (SymbolReference) firstArgument);
            Expression newSecondArgument = newSecondSymbol.map(Symbol::toSymbolReference).orElseGet(() -> (SymbolReference) secondArgument);

            Expression newSpatialFunction = new FunctionCall(spatialFunction.getName(), ImmutableList.of(newFirstArgument, newSecondArgument));
            Expression newFilter = replaceExpression(filter, ImmutableMap.of(spatialFunction, newSpatialFunction));

            Set<String> leftSymbols = getSymbolNames(node.getLeft().getOutputSymbols());
            Set<String> rightSymbols = getSymbolNames(node.getRight().getOutputSymbols());

            PlanNode leftNode = node.getLeft();
            PlanNode rightNode = node.getRight();

            PlanNode newLeftNode;
            PlanNode newRightNode;

            if (leftSymbols.containsAll(firstSymbols)
                    && containsNone(leftSymbols, secondSymbols)
                    && rightSymbols.containsAll(secondSymbols)
                    && containsNone(rightSymbols, firstSymbols)) {
                newLeftNode = newFirstSymbol.map(symbol -> addProjection(context, leftNode, symbol, firstArgument)).orElse(leftNode);
                newRightNode = newSecondSymbol.map(symbol -> addProjection(context, rightNode, symbol, secondArgument)).orElse(rightNode);
            }
            else if (leftSymbols.containsAll(secondSymbols)
                    && containsNone(leftSymbols, firstSymbols)
                    && rightSymbols.containsAll(firstSymbols)
                    && containsNone(rightSymbols, secondSymbols)) {
                newLeftNode = newSecondSymbol.map(symbol -> addProjection(context, leftNode, symbol, secondArgument)).orElse(leftNode);
                newRightNode = newFirstSymbol.map(symbol -> addProjection(context, rightNode, symbol, firstArgument)).orElse(rightNode);
            }
            else {
                continue;
            }

            return Result.ofPlanNode(new JoinNode(
                    node.getId(),
                    node.getType(),
                    newLeftNode,
                    newRightNode,
                    node.getCriteria(),
                    node.getOutputSymbols(),
                    Optional.of(newFilter),
                    node.getLeftHashSymbol(),
                    node.getRightHashSymbol(),
                    node.getDistributionType()));
        }

        return Result.empty();
    }

    private Set<String> getSymbolNames(Collection<Symbol> symbols)
    {
        return symbols.stream().map(Symbol::getName).collect(toImmutableSet());
    }

    private Symbol newSymbol(Context context, Expression expression)
    {
        Map<NodeRef<Expression>, Type> expressionTypes = getExpressionTypes(context.getSession(), metadata, sqlParser, context.getSymbolAllocator().getTypes(), expression, emptyList() /* parameters already replaced */);
        return context.getSymbolAllocator().newSymbol(expression, expressionTypes.get(NodeRef.of(expression)));
    }

    private PlanNode addProjection(Context context, PlanNode node, Symbol symbol, Expression expression)
    {
        Assignments.Builder projections = Assignments.builder();
        for (Symbol outputSymbol : node.getOutputSymbols()) {
            projections.putIdentity(outputSymbol);
        }

        projections.put(symbol, expression);
        return new ProjectNode(context.getIdAllocator().getNextId(), node, projections.build());
    }

    private boolean containsNone(Collection<String> values, Collection<String> testValues)
    {
        return values.stream().noneMatch(v -> testValues.contains(v));
    }
}

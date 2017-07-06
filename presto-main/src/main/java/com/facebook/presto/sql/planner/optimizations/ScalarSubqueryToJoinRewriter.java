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
package com.facebook.presto.sql.planner.optimizations;

import com.facebook.presto.metadata.FunctionRegistry;
import com.facebook.presto.spi.StandardErrorCode;
import com.facebook.presto.spi.type.StandardTypes;
import com.facebook.presto.sql.analyzer.TypeSignatureProvider;
import com.facebook.presto.sql.planner.PlanNodeIdAllocator;
import com.facebook.presto.sql.planner.Symbol;
import com.facebook.presto.sql.planner.SymbolAllocator;
import com.facebook.presto.sql.planner.iterative.Lookup;
import com.facebook.presto.sql.planner.optimizations.PlanNodeDecorrelator.DecorrelatedNode;
import com.facebook.presto.sql.planner.plan.AggregationNode;
import com.facebook.presto.sql.planner.plan.AggregationNode.Aggregation;
import com.facebook.presto.sql.planner.plan.AssignUniqueId;
import com.facebook.presto.sql.planner.plan.Assignments;
import com.facebook.presto.sql.planner.plan.EnforceSingleRowNode;
import com.facebook.presto.sql.planner.plan.FilterNode;
import com.facebook.presto.sql.planner.plan.JoinNode;
import com.facebook.presto.sql.planner.plan.LateralJoinNode;
import com.facebook.presto.sql.planner.plan.PlanNode;
import com.facebook.presto.sql.planner.plan.ProjectNode;
import com.facebook.presto.sql.tree.Cast;
import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.FunctionCall;
import com.facebook.presto.sql.tree.LongLiteral;
import com.facebook.presto.sql.tree.QualifiedName;
import com.facebook.presto.sql.tree.SimpleCaseExpression;
import com.facebook.presto.sql.tree.StringLiteral;
import com.facebook.presto.sql.tree.WhenClause;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.facebook.presto.spi.type.BigintType.BIGINT;
import static com.facebook.presto.spi.type.BooleanType.BOOLEAN;
import static com.facebook.presto.sql.analyzer.TypeSignatureProvider.fromTypeSignatures;
import static com.facebook.presto.sql.planner.optimizations.PlanNodeSearcher.searchFrom;
import static com.facebook.presto.sql.planner.optimizations.SymbolMapper.recurse;
import static com.facebook.presto.sql.tree.BooleanLiteral.TRUE_LITERAL;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;

// TODO: move this class to TransformCorrelatedScalarAggregationToJoin when old optimizer is gone
public class ScalarSubqueryToJoinRewriter
{
    private static final QualifiedName COUNT = QualifiedName.of("count");
    private static final QualifiedName ARBITRARY = QualifiedName.of("arbitrary");

    private final FunctionRegistry functionRegistry;
    private final SymbolAllocator symbolAllocator;
    private final PlanNodeIdAllocator idAllocator;
    private final Lookup lookup;
    private final PlanNodeDecorrelator planNodeDecorrelator;

    public ScalarSubqueryToJoinRewriter(FunctionRegistry functionRegistry, SymbolAllocator symbolAllocator, PlanNodeIdAllocator idAllocator, Lookup lookup)
    {
        this.functionRegistry = requireNonNull(functionRegistry, "metadata is null");
        this.symbolAllocator = requireNonNull(symbolAllocator, "symbolAllocator is null");
        this.idAllocator = requireNonNull(idAllocator, "idAllocator is null");
        this.lookup = requireNonNull(lookup, "lookup is null");
        this.planNodeDecorrelator = new PlanNodeDecorrelator(idAllocator, lookup);
    }

    public Optional<PlanNode> rewriteScalarFilterScan(LateralJoinNode lateralJoinNode, FilterNode filterNode)
    {
        List<Symbol> correlation = lateralJoinNode.getCorrelation();
        Optional<DecorrelatedNode> decorrelatedSubquerySource = planNodeDecorrelator.decorrelateFilters(filterNode, correlation);
        if (!decorrelatedSubquerySource.isPresent()) {
            return Optional.empty();
        }

        // we need to map output symbols to some different symbols, so output symbols could be used as output of aggregation
        PlanNode decorrelatedSubquerySourceNode = decorrelatedSubquerySource.get().getNode();
        Map<Symbol, Symbol> subqueryOutputsMapping = decorrelatedSubquerySourceNode.getOutputSymbols().stream()
                .collect(toImmutableMap(identity(), symbol -> symbolAllocator.newSymbol(symbol.getName(), symbolAllocator.getTypes().get(symbol))));
        SymbolMapper symbolMapper = new SymbolMapper(subqueryOutputsMapping);

        PlanNode mappedDecorelatedSubquerySourceNode = symbolMapper.map(decorrelatedSubquerySourceNode, idAllocator, recurse());
        SubqueryJoin subqueryJoin = createSubqueryJoin(
                lateralJoinNode,
                mappedDecorelatedSubquerySourceNode,
                decorrelatedSubquerySource.get().getCorrelatedPredicates().map(symbolMapper::map));

        Symbol count = symbolAllocator.newSymbol("count", BIGINT);
        AggregationNode aggregationNode = new AggregationNode(
                idAllocator.getNextId(),
                subqueryJoin.getLeftOuterJoin(),
                ImmutableMap.<Symbol, Aggregation>builder()
                        .put(count, createCountAggregation(subqueryJoin.getNonNull(), Optional.empty()))
                        .putAll(createArbitraries(subqueryOutputsMapping))
                        .build(),
                ImmutableList.of(subqueryJoin.getLeftOuterJoin().getLeft().getOutputSymbols()),
                AggregationNode.Step.SINGLE,
                Optional.empty(),
                Optional.empty());

        FilterNode filterNodeToMakeSureThatCheckCountSymbolIsNotPruned = new FilterNode(
                idAllocator.getNextId(),
                aggregationNode,
                new SimpleCaseExpression(
                        count.toSymbolReference(),
                        ImmutableList.of(
                                new WhenClause(new LongLiteral("0"), TRUE_LITERAL),
                                new WhenClause(new LongLiteral("1"), TRUE_LITERAL)),
                        Optional.of(new Cast(
                                new FunctionCall(
                                        QualifiedName.of("fail"),
                                        ImmutableList.of(
                                                new LongLiteral(Integer.toString(StandardErrorCode.SUBQUERY_MULTIPLE_ROWS.toErrorCode().getCode())),
                                                new StringLiteral("Scalar sub-query has returned multiple rows"))),
                                StandardTypes.BOOLEAN))));

        return projectToLateralOutputSymbols(lateralJoinNode, filterNodeToMakeSureThatCheckCountSymbolIsNotPruned);
    }

    public Optional<PlanNode> rewriteScalarAggregation(LateralJoinNode lateralJoinNode, AggregationNode aggregation)
    {
        List<Symbol> correlation = lateralJoinNode.getCorrelation();
        Optional<DecorrelatedNode> aggregationSource = planNodeDecorrelator.decorrelateFilters(lookup.resolve(aggregation.getSource()), correlation);
        if (!aggregationSource.isPresent()) {
            return Optional.empty();
        }

        return rewriteScalarAggregation(
                lateralJoinNode,
                aggregation,
                aggregationSource.get().getNode(),
                aggregationSource.get().getCorrelatedPredicates());
    }

    private Optional<PlanNode> rewriteScalarAggregation(
            LateralJoinNode lateralJoinNode,
            AggregationNode scalarAggregation,
            PlanNode scalarAggregationSource,
            Optional<Expression> joinExpression)
    {
        SubqueryJoin subqueryJoin = createSubqueryJoin(lateralJoinNode, scalarAggregationSource, joinExpression);

        Optional<AggregationNode> aggregationNode = createAggregationNode(
                scalarAggregation,
                subqueryJoin.getLeftOuterJoin(),
                subqueryJoin.getNonNull());

        if (!aggregationNode.isPresent()) {
            return Optional.empty();
        }

        return projectToLateralOutputSymbols(lateralJoinNode, aggregationNode.get());
    }

    private Optional<PlanNode> projectToLateralOutputSymbols(LateralJoinNode lateralJoinNode, PlanNode decorrelatedSubquery)
    {
        List<Symbol> outputSymbols = truncateToLateralSymbols(lateralJoinNode, decorrelatedSubquery);

        List<ProjectNode> subqueryProjections = searchFrom(lateralJoinNode.getSubquery(), lookup)
                .where(ProjectNode.class::isInstance)
                .skipOnlyWhen(EnforceSingleRowNode.class::isInstance)
                .findAll();

        if (subqueryProjections.size() > 1) {
            return Optional.empty();
        }

        Assignments.Builder assignments = Assignments.builder()
                .putIdentities(outputSymbols);

        if (subqueryProjections.size() == 1) {
            assignments.putAll(subqueryProjections.get(0).getAssignments());
        }

        return Optional.of(new ProjectNode(
                idAllocator.getNextId(),
                decorrelatedSubquery,
                assignments.build()));
    }

    private SubqueryJoin createSubqueryJoin(LateralJoinNode lateralJoinNode, PlanNode decorrelatedSubquery, Optional<Expression> joinExpression)
    {
        AssignUniqueId inputWithUniqueColumns = new AssignUniqueId(
                idAllocator.getNextId(),
                lateralJoinNode.getInput(),
                symbolAllocator.newSymbol("unique", BIGINT));

        Symbol nonNull = symbolAllocator.newSymbol("non_null", BOOLEAN);
        ProjectNode scalarAggregationSourceWithNonNullableSymbol = new ProjectNode(
                idAllocator.getNextId(),
                decorrelatedSubquery,
                Assignments.builder()
                        .putAll(Assignments.identity(decorrelatedSubquery.getOutputSymbols()))
                        .put(nonNull, TRUE_LITERAL)
                        .build());

        JoinNode leftOuterJoin = new JoinNode(
                idAllocator.getNextId(),
                JoinNode.Type.LEFT,
                inputWithUniqueColumns,
                scalarAggregationSourceWithNonNullableSymbol,
                ImmutableList.of(),
                ImmutableList.<Symbol>builder()
                        .addAll(inputWithUniqueColumns.getOutputSymbols())
                        .addAll(scalarAggregationSourceWithNonNullableSymbol.getOutputSymbols())
                        .build(),
                joinExpression,
                Optional.empty(),
                Optional.empty(),
                Optional.empty());

        return new SubqueryJoin(leftOuterJoin, nonNull);
    }

    private static List<Symbol> truncateToLateralSymbols(LateralJoinNode lateralJoinNode, PlanNode plan)
    {
        Set<Symbol> applySymbols = new HashSet<>(lateralJoinNode.getOutputSymbols());
        return plan.getOutputSymbols().stream()
                .filter(symbol -> applySymbols.contains(symbol))
                .collect(toImmutableList());
    }

    private Optional<AggregationNode> createAggregationNode(
            AggregationNode scalarAggregation,
            JoinNode leftOuterJoin,
            Symbol nonNullableAggregationSourceSymbol)
    {
        ImmutableMap.Builder<Symbol, Aggregation> aggregations = ImmutableMap.builder();
        for (Map.Entry<Symbol, Aggregation> entry : scalarAggregation.getAggregations().entrySet()) {
            FunctionCall call = entry.getValue().getCall();
            Symbol symbol = entry.getKey();
            if (call.getName().equals(COUNT)) {
                aggregations.put(symbol, createCountAggregation(nonNullableAggregationSourceSymbol, entry.getValue().getMask()));
            }
            else {
                aggregations.put(symbol, entry.getValue());
            }
        }

        List<Symbol> groupBySymbols = leftOuterJoin.getLeft().getOutputSymbols();
        return Optional.of(new AggregationNode(
                idAllocator.getNextId(),
                leftOuterJoin,
                aggregations.build(),
                ImmutableList.of(groupBySymbols),
                scalarAggregation.getStep(),
                scalarAggregation.getHashSymbol(),
                Optional.empty()));
    }

    private Map<Symbol, Aggregation> createArbitraries(Map<Symbol, Symbol> subqueryOutputsMapping)
    {
        return subqueryOutputsMapping.entrySet().stream()
                .collect(toImmutableMap(e -> e.getKey(), e -> createArbitrary(e.getValue())));
    }

    private Aggregation createArbitrary(Symbol symbol)
    {
        return new Aggregation(
                new FunctionCall(ARBITRARY, ImmutableList.of(symbol.toSymbolReference())),
                functionRegistry.resolveFunction(ARBITRARY, asTypeSignature(symbol)),
                Optional.empty());
    }

    private List<TypeSignatureProvider> asTypeSignature(Symbol symbol)
    {
        return fromTypeSignatures(ImmutableList.of(symbolAllocator.getTypes().get(symbol).getTypeSignature()));
    }

    private Aggregation createCountAggregation(Symbol nonNullableAggregationSourceSymbol, Optional<Symbol> mask)
    {
        return new Aggregation(
                new FunctionCall(
                        COUNT,
                        ImmutableList.of(nonNullableAggregationSourceSymbol.toSymbolReference())),
                functionRegistry.resolveFunction(
                        COUNT,
                        asTypeSignature(nonNullableAggregationSourceSymbol)),
                mask);
    }

    private static final class SubqueryJoin
    {
        private final JoinNode leftOuterJoin;
        private final Symbol nonNull;

        private SubqueryJoin(JoinNode leftOuterJoin, Symbol nonNull)
        {
            this.leftOuterJoin = requireNonNull(leftOuterJoin, "leftOuterJoin is null");
            this.nonNull = requireNonNull(nonNull, "nonNull is null");
        }

        private JoinNode getLeftOuterJoin()
        {
            return leftOuterJoin;
        }

        private Symbol getNonNull()
        {
            return nonNull;
        }
    }
}

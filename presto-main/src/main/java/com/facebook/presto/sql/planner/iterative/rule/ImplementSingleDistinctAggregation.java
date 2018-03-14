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
import com.facebook.presto.sql.planner.Symbol;
import com.facebook.presto.sql.planner.iterative.Rule;
import com.facebook.presto.sql.planner.plan.AggregationNode;
import com.facebook.presto.sql.planner.plan.AggregationNode.Aggregation;
import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.FunctionCall;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.facebook.presto.sql.planner.plan.AggregationNode.Step.SINGLE;
import static com.facebook.presto.sql.planner.plan.Patterns.aggregation;

/**
 * Implements distinct aggregations with similar inputs by transforming plans of the following shape:
 * <pre>
 * - Aggregation
 *        GROUP BY (k)
 *        F1(DISTINCT s0, s1, ...),
 *        F2(DISTINCT s0, s1, ...),
 *     - X
 * </pre>
 * into
 * <pre>
 * - Aggregation
 *          GROUP BY (k)
 *          F1(x)
 *          F2(x)
 *      - Aggregation
 *             GROUP BY (k, s0, s1, ...)
 *          - X
 * </pre>
 * <p>
 * Assumes s0, s1, ... are symbol references (i.e., complex expressions have been pre-projected)
 */
public class ImplementSingleDistinctAggregation
        implements Rule<AggregationNode>
{
    private static final Pattern<AggregationNode> PATTERN = aggregation()
            .matching(ImplementSingleDistinctAggregation::hasDistinct);

    private static boolean hasDistinct(AggregationNode aggregation)
    {
        return aggregation.getAggregations()
                .values().stream()
                .filter(e -> e.getCall().isDistinct())
                .map(e -> e.getCall().getArguments())
                .distinct()
                .count() == 1;
    }

    @Override
    public Pattern<AggregationNode> getPattern()
    {
        return PATTERN;
    }

    @Override
    public Result apply(AggregationNode aggregation, Captures captures, Context context)
    {
        Collection<Aggregation> aggregations = aggregation.getAggregations().values();

        List<Set<Expression>> argumentSets = aggregations.stream()
                .map(Aggregation::getCall)
                .filter(FunctionCall::isDistinct)
                .map(FunctionCall::getArguments)
                .map(HashSet::new)
                .distinct()
                .collect(Collectors.toList());

        Set<Set<Expression>> uniqueArgumentSet = ImmutableSet.copyOf(argumentSets);

        if (uniqueArgumentSet.size() != 1 || argumentSets.size() != aggregations.size()) {
            // bail out if different DISTINCT argument sets or not every aggregation has DISTINCT
            return Result.empty();
        }

        Set<Symbol> symbols = Iterables.getOnlyElement(uniqueArgumentSet).stream()
                .map(Symbol::from)
                .collect(Collectors.toSet());

        return Result.ofPlanNode(
                new AggregationNode(
                        aggregation.getId(),
                        new AggregationNode(
                                context.getIdAllocator().getNextId(),
                                aggregation.getSource(),
                                Collections.emptyMap(),
                                ImmutableList.of(ImmutableList.<Symbol>builder()
                                        .addAll(aggregation.getGroupingKeys())
                                        .addAll(symbols)
                                        .build()),
                                SINGLE,
                                Optional.empty(),
                                Optional.empty()),
                        // remove DISTINCT flag from function calls
                        aggregation.getAggregations()
                                .entrySet().stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        e -> removeDistinct(e.getValue()))),
                        aggregation.getGroupingSets(),
                        aggregation.getStep(),
                        aggregation.getHashSymbol(),
                        aggregation.getGroupIdSymbol()));
    }

    private static AggregationNode.Aggregation removeDistinct(AggregationNode.Aggregation aggregation)
    {
        FunctionCall call = aggregation.getCall();
        return new AggregationNode.Aggregation(
                new FunctionCall(
                        call.getName(),
                        call.getWindow(),
                        call.getFilter(),
                        call.getOrderBy(),
                        false,
                        call.getArguments()),
                aggregation.getSignature(),
                Optional.empty());
    }
}

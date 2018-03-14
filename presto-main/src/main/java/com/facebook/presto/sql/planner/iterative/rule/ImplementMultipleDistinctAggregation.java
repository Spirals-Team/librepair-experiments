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
import com.facebook.presto.sql.planner.plan.MarkDistinctNode;
import com.facebook.presto.sql.planner.plan.PlanNode;
import com.facebook.presto.sql.tree.FunctionCall;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.facebook.presto.spi.type.BooleanType.BOOLEAN;
import static com.facebook.presto.sql.planner.plan.Patterns.aggregation;

/**
 * Implements distinct aggregations with different inputs by transforming plans of the following shape:
 * <pre>
 * - Aggregation
 *        GROUP BY (k),
 *        F1(DISTINCT s0, s1, ...),
 *        F2(DISTINCT s2, s3, ...)
 *     - X
 * </pre>
 * into
 * <pre>
 * - Aggregation
 *        F1(...) mask ($0)
 *        F2(...) mask ($1)
 *     - MarkDistinct (k, s0, s1, ...) -> $0
 *          - MarkDistinct (k, s2, s3, ...) -> $1
 *              - X
 * </pre>
 */
public class ImplementMultipleDistinctAggregation
        implements Rule<AggregationNode>
{
    private static final Pattern<AggregationNode> PATTERN = aggregation()
            .matching(ImplementMultipleDistinctAggregation::hasDistinct);

    private static boolean hasDistinct(AggregationNode aggregation)
    {
        return aggregation.getAggregations()
                .values().stream()
                .anyMatch(e -> e.getCall().isDistinct() && !e.getMask().isPresent());  // MarkDistinct doesn't support a mask (yet), so skip if aggregation has mask set
    }

    @Override
    public Pattern<AggregationNode> getPattern()
    {
        return PATTERN;
    }

    @Override
    public Result apply(AggregationNode parent, Captures captures, Context context)
    {
        // the distinct marker for the given set of input columns
        Map<Set<Symbol>, Symbol> markers = new HashMap<>();

        Map<Symbol, Aggregation> aggregations = new HashMap<>();
        PlanNode subPlan = parent.getSource();

        for (Map.Entry<Symbol, Aggregation> entry : parent.getAggregations().entrySet()) {
            Aggregation aggregation = entry.getValue();
            FunctionCall call = aggregation.getCall();

            if (call.isDistinct()) {
                Set<Symbol> inputs = call.getArguments().stream()
                        .map(Symbol::from)
                        .collect(Collectors.toSet());

                Symbol marker = markers.get(inputs);
                if (marker == null) {
                    marker = context.getSymbolAllocator().newSymbol(inputs.iterator().next().getName(), BOOLEAN, "distinct");
                    markers.put(inputs, marker);
                }

                ImmutableSet.Builder<Symbol> distinctSymbols = ImmutableSet.<Symbol>builder()
                        .addAll(parent.getGroupingKeys())
                        .addAll(inputs);

                parent.getGroupIdSymbol().ifPresent(distinctSymbols::add);

                // remove the distinct flag and set the distinct marker
                aggregations.put(entry.getKey(),
                        new Aggregation(
                                new FunctionCall(
                                        call.getName(),
                                        call.getWindow(),
                                        call.getFilter(),
                                        call.getOrderBy(),
                                        false,
                                        call.getArguments()),
                                aggregation.getSignature(),
                                Optional.of(marker)));

                subPlan = new MarkDistinctNode(
                        context.getIdAllocator().getNextId(),
                        subPlan,
                        marker,
                        ImmutableList.copyOf(distinctSymbols.build()),
                        Optional.empty());
            }
            else {
                aggregations.put(entry.getKey(), aggregation);
            }
        }

        return Result.ofPlanNode(
                new AggregationNode(
                        parent.getId(),
                        subPlan,
                        aggregations,
                        parent.getGroupingSets(),
                        parent.getStep(),
                        parent.getHashSymbol(),
                        parent.getGroupIdSymbol()));
    }
}

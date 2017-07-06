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

import com.facebook.presto.Session;
import com.facebook.presto.metadata.FunctionRegistry;
import com.facebook.presto.sql.planner.PlanNodeIdAllocator;
import com.facebook.presto.sql.planner.SymbolAllocator;
import com.facebook.presto.sql.planner.iterative.Lookup;
import com.facebook.presto.sql.planner.iterative.Pattern;
import com.facebook.presto.sql.planner.iterative.Rule;
import com.facebook.presto.sql.planner.optimizations.ScalarSubqueryToJoinRewriter;
import com.facebook.presto.sql.planner.plan.EnforceSingleRowNode;
import com.facebook.presto.sql.planner.plan.FilterNode;
import com.facebook.presto.sql.planner.plan.LateralJoinNode;
import com.facebook.presto.sql.planner.plan.PlanNode;
import com.facebook.presto.sql.planner.plan.ProjectNode;
import com.facebook.presto.sql.planner.plan.TableScanNode;

import java.util.List;
import java.util.Optional;

import static com.facebook.presto.sql.planner.optimizations.PlanNodeSearcher.searchFrom;
import static com.facebook.presto.sql.planner.optimizations.QueryCardinalityUtil.isScalar;
import static com.facebook.presto.util.MorePredicates.isInstanceOfAny;
import static java.util.Objects.requireNonNull;

/**
 * Scalar filter scan query is something like:
 * <pre>
 *     SELECT a,b,c FROM rel WHERE a = correlated1 AND b = correlated2
 * </pre>
 * <p>
 * This optimizer can rewrite to aggregation over a left outer join:
 * <p>
 * From:
 * <pre>
 * - LateralJoin (with correlation list: [C])
 *   - (input) plan which produces symbols: [A, B, C]
 *   - (subquery) Project F
 *     - Filter(D = C AND E > 5)
 *       - plan which produces symbols: [D, E, F]
 * </pre>
 * to:
 * <pre>
 * - Filter(CASE CN WHEN 0 true WHEN 1 true ELSE fail('Scalar sub-query has returned multiple rows'))
 *   - Aggregation(GROUP BY A, B, C, U; functions: [count(non_null) AS CN, arbitrary(F1) AS F...]
 *     - Join(LEFT_OUTER, D = C)
 *       - AssignUniqueId(adds symbol U)
 *         - (input) plan which produces symbols: [A, B, C]
 *       - Filter(E > 5)
 *         - projection which adds no null symbol used for count() function
 *           - plan which produces symbols: [D, E, F1]
 * </pre>
 * <p>
 * Note only conjunction predicates in FilterNode are supported
 */
public class TransformCorrelatedScalarFilterScanToJoin
        implements Rule
{
    private static final Pattern PATTERN = Pattern.node(LateralJoinNode.class);
    private final FunctionRegistry functionRegistry;

    public TransformCorrelatedScalarFilterScanToJoin(FunctionRegistry functionRegistry)
    {
        this.functionRegistry = requireNonNull(functionRegistry, "functionRegistry is null");
    }

    @Override
    public Pattern getPattern()
    {
        return PATTERN;
    }

    @Override
    public Optional<PlanNode> apply(PlanNode node, Lookup lookup, PlanNodeIdAllocator idAllocator, SymbolAllocator symbolAllocator, Session session)
    {
        LateralJoinNode lateralJoinNode = (LateralJoinNode) node;
        PlanNode subquery = lookup.resolve(lateralJoinNode.getSubquery());

        if (lateralJoinNode.getCorrelation().isEmpty() || !isScalar(subquery, lookup)) {
            return Optional.empty();
        }

        Optional<FilterNode> filterScan = getFilterScan(subquery, lookup);
        if (!filterScan.isPresent()) {
            return Optional.empty();
        }

        ScalarSubqueryToJoinRewriter rewriter = new ScalarSubqueryToJoinRewriter(functionRegistry, symbolAllocator, idAllocator, lookup);
        return rewriter.rewriteScalarFilterScan(lateralJoinNode, filterScan.get());
    }

    private Optional<FilterNode> getFilterScan(PlanNode planNode, Lookup lookup)
    {
        if (!searchFrom(planNode, lookup)
                .where(TableScanNode.class::isInstance)
                .skipOnlyWhen(isInstanceOfAny(ProjectNode.class, FilterNode.class, EnforceSingleRowNode.class))
                .matches()) {
            return Optional.empty();
        }

        List<FilterNode> filterNodes = searchFrom(planNode, lookup)
                .where(FilterNode.class::isInstance)
                .skipOnlyWhen(isInstanceOfAny(ProjectNode.class, EnforceSingleRowNode.class))
                .findAll();

        if (filterNodes.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(filterNodes.get(0));
    }
}

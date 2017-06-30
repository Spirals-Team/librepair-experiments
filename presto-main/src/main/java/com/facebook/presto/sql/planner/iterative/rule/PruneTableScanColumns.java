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
import com.facebook.presto.sql.planner.PlanNodeIdAllocator;
import com.facebook.presto.sql.planner.Symbol;
import com.facebook.presto.sql.planner.SymbolAllocator;
import com.facebook.presto.sql.planner.iterative.Lookup;
import com.facebook.presto.sql.planner.iterative.Pattern;
import com.facebook.presto.sql.planner.iterative.Rule;
import com.facebook.presto.sql.planner.plan.PlanNode;
import com.facebook.presto.sql.planner.plan.ProjectNode;
import com.facebook.presto.sql.planner.plan.TableScanNode;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.facebook.presto.sql.planner.iterative.rule.Util.pushDownProjectOff;
import static com.google.common.collect.ImmutableList.toImmutableList;

public class PruneTableScanColumns
        implements Rule
{
    private static final Pattern PATTERN = Pattern.node(ProjectNode.class);

    @Override
    public Pattern getPattern()
    {
        return PATTERN;
    }

    @Override
    public Optional<PlanNode> apply(PlanNode node, Lookup lookup, PlanNodeIdAllocator idAllocator, SymbolAllocator symbolAllocator, Session session)
    {
        return pushDownProjectOff(
                lookup,
                TableScanNode.class,
                (ProjectNode) node,
                (tableScanNode, referencedOutputs) -> {
                    List<Symbol> newOutputs = tableScanNode.getOutputSymbols().stream()
                            .filter(referencedOutputs::contains)
                            .collect(toImmutableList());

                    return Optional.of(
                            new TableScanNode(
                                    tableScanNode.getId(),
                                    tableScanNode.getTable(),
                                    newOutputs,
                                    newOutputs.stream()
                                            .collect(Collectors.toMap(Function.identity(), e -> tableScanNode.getAssignments().get(e))),
                                    tableScanNode.getLayout(),
                                    tableScanNode.getCurrentConstraint(),
                                    tableScanNode.getOriginalConstraint()));
                });
    }
}

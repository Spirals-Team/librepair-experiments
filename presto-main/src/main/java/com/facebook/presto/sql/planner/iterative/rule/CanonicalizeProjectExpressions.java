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

import com.facebook.presto.matching.Pattern;
import com.facebook.presto.sql.planner.iterative.Rule;
import com.facebook.presto.sql.planner.optimizations.CanonicalizeExpressions;
import com.facebook.presto.sql.planner.plan.Assignments;
import com.facebook.presto.sql.planner.plan.PlanNode;
import com.facebook.presto.sql.planner.plan.ProjectNode;

import java.util.Optional;

public class CanonicalizeProjectExpressions
        implements Rule
{
    private static final Pattern PATTERN = Pattern.typeOf(ProjectNode.class);

    @Override
    public Pattern getPattern()
    {
        return PATTERN;
    }

    @Override
    public Optional<PlanNode> apply(PlanNode node, Context context)
    {
        ProjectNode projectNode = (ProjectNode) node;

        Assignments assignments = projectNode.getAssignments()
                .rewrite(CanonicalizeExpressions::canonicalizeExpression);

        if (assignments.equals(projectNode.getAssignments())) {
            return Optional.empty();
        }

        PlanNode replacement = new ProjectNode(node.getId(), projectNode.getSource(), assignments);

        return Optional.of(replacement);
    }
}

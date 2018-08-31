/*
 * Copyright (c) 2018-present PowerFlows.org - all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.powerflows.dmn.engine.evaluator.decision;


import org.powerflows.dmn.engine.evaluator.rule.RuleEvaluator;
import org.powerflows.dmn.engine.model.decision.Decision;
import org.powerflows.dmn.engine.model.decision.HitPolicy;
import org.powerflows.dmn.engine.model.decision.rule.Rule;
import org.powerflows.dmn.engine.model.evaluation.context.ContextVariables;
import org.powerflows.dmn.engine.model.evaluation.result.DecisionResult;
import org.powerflows.dmn.engine.model.evaluation.result.RuleResult;
import org.powerflows.dmn.engine.model.evaluation.result.exception.EvaluationResultException;

import java.util.ArrayList;
import java.util.List;

public class DecisionEvaluator {

    private final RuleEvaluator ruleEvaluator;

    public DecisionEvaluator(RuleEvaluator ruleEvaluator) {
        this.ruleEvaluator = ruleEvaluator;
    }

    public DecisionResult evaluate(final Decision decision, final ContextVariables contextVariables) {
        final List<RuleResult> ruleResults = new ArrayList<>();
        final boolean singleNonUniqueRuleResultExpected = isSingleNonUniqueRuleResultExpected(decision);

        for (Rule rule : decision.getRules()) {
            final RuleResult ruleResult = ruleEvaluator.evaluate(rule, decision, contextVariables);
            ruleResults.add(ruleResult);

            if (ruleResult != null) {
                ruleResults.add(ruleResult);

                if (singleNonUniqueRuleResultExpected) {
                    break;
                }
            }
        }

        if (isUniqueRuleResultExpected(decision) && isNonUniqueRuleResult(decision)) {
            throw new EvaluationResultException("Unique result is expected");
        }

        final DecisionResult decisionResult = DecisionResult.builder().ruleResults(ruleResults).build();

        //TODO here will be logger

        return decisionResult;
    }

    private boolean isSingleNonUniqueRuleResultExpected(final Decision decision) {
        return HitPolicy.FIRST.equals(decision.getHitPolicy()) || HitPolicy.ANY.equals(decision.getHitPolicy());
    }

    private boolean isUniqueRuleResultExpected(final Decision decision) {
        return HitPolicy.UNIQUE.equals(decision.getHitPolicy());
    }

    private boolean isCollectionRulesResultExpected(final Decision decision) {
        return HitPolicy.COLLECT.equals(decision.getHitPolicy());
    }

    private boolean isNonUniqueRuleResult(final Decision decision) {
        return decision.getRules().size() > 1;
    }


}

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

package org.powerflows.dmn.engine.evaluator.entry;


import org.powerflows.dmn.engine.evaluator.entry.expression.provider.EvaluationProviderFactory;
import org.powerflows.dmn.engine.evaluator.entry.expression.provider.ExpressionEvaluable;
import org.powerflows.dmn.engine.model.decision.expression.ExpressionType;
import org.powerflows.dmn.engine.model.decision.field.Output;
import org.powerflows.dmn.engine.model.decision.rule.entry.OutputEntry;
import org.powerflows.dmn.engine.model.evaluation.context.ContextVariables;
import org.powerflows.dmn.engine.model.evaluation.result.EntryResult;

class OutputEntryEvaluator {

    private final EvaluationProviderFactory evaluationProviderFactory;

    public OutputEntryEvaluator(EvaluationProviderFactory evaluationProviderFactory) {
        this.evaluationProviderFactory = evaluationProviderFactory;
    }

    EntryResult evaluate(final OutputEntry outputEntry, final Output output, final ExpressionType expressionType, final ContextVariables contextVariables) {
        final ExpressionEvaluable expressionEvaluator = evaluationProviderFactory.getInstance(expressionType);

        return expressionEvaluator.evaluateOutput(outputEntry, output, contextVariables);
    }

}

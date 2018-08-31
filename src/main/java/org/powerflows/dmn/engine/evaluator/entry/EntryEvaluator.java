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


import org.powerflows.dmn.engine.model.decision.Decision;
import org.powerflows.dmn.engine.model.decision.field.Input;
import org.powerflows.dmn.engine.model.decision.field.Output;
import org.powerflows.dmn.engine.model.decision.rule.entry.InputEntry;
import org.powerflows.dmn.engine.model.decision.rule.entry.OutputEntry;
import org.powerflows.dmn.engine.model.evaluation.context.ContextVariables;
import org.powerflows.dmn.engine.model.evaluation.result.EntryResult;
import org.powerflows.dmn.engine.model.evaluation.result.exception.EvaluationResultException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class EntryEvaluator {

    private final InputEntryEvaluator inputEntryEvaluator;
    private final OutputEntryEvaluator outputEntryEvaluator;

    public EntryEvaluator(InputEntryEvaluator inputEntryEvaluator,
                          OutputEntryEvaluator outputEntryEvaluator) {
        this.inputEntryEvaluator = inputEntryEvaluator;
        this.outputEntryEvaluator = outputEntryEvaluator;
    }

    public List<EntryResult> evaluate(final List<InputEntry> inputEntries, final List<OutputEntry> outputEntries, final Decision decision, final ContextVariables contextVariables) {
        final List<EntryResult> entryResults = new ArrayList<>();
        boolean positive = true;

        for (InputEntry inputEntry : inputEntries) {
            final Input input = decision
                    .getInputs()
                    .stream()
                    .filter(in -> in.getName().equals(inputEntry.getName()))
                    .findFirst()
                    .orElseThrow(() -> new EvaluationResultException("Missing input for input entry."));

            if (!inputEntryEvaluator.evaluate(inputEntry, input, decision.getExpressionType(), contextVariables)) {
                positive = false;
                break;
            }
        }

        if (positive) {
            for (OutputEntry outputEntry : outputEntries) {
                final Output output = decision
                        .getOutputs()
                        .stream()
                        .filter(in -> in.getName().equals(outputEntry.getName()))
                        .findFirst()
                        .orElseThrow(() -> new EvaluationResultException("Missing output for output entry."));

                final EntryResult entryResult = outputEntryEvaluator.evaluate(outputEntry, output, decision.getExpressionType(), contextVariables);
                entryResults.add(entryResult);
            }
        }

        return unmodifiableList(entryResults);
    }

}

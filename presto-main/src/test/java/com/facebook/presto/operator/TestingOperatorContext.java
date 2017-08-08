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
package com.facebook.presto.operator;

import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.facebook.presto.testing.TestingSession;
import com.facebook.presto.testing.TestingTaskContext;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executor;

public class TestingOperatorContext
{
    public static OperatorContext create()
    {
        Executor executor = MoreExecutors.directExecutor();

        TaskContext taskContext = TestingTaskContext.createTaskContext(
                executor,
                TestingSession.testSessionBuilder().build());

        PipelineContext pipelineContext = new PipelineContext(
                1,
                taskContext,
                executor,
                false,
                false);

        DriverContext driverContext = new DriverContext(
                pipelineContext,
                executor,
                false);

        OperatorContext operatorContext = driverContext.addOperatorContext(
                1,
                new PlanNodeId("test"),
                "operator type");

        return operatorContext;
    }

    private TestingOperatorContext() {}
}

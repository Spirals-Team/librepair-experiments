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
package com.facebook.presto.tests;

import com.facebook.presto.execution.QueryManager;
import com.facebook.presto.sql.parser.SqlParserOptions;
import com.facebook.presto.testing.MaterializedResult;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.facebook.presto.testing.TestingSession.testSessionBuilder;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class TestLegacyLogFunction
{
    private TestLegacyLogFunction() {};

    public abstract static class AbstractTest
    {
        private static final String QUERY = "select log(25, 5)";
        protected DistributedQueryRunner queryRunner;

        protected abstract boolean isLegacyLog();

        @BeforeMethod
        public void setup()
                throws Exception
        {
            queryRunner = createQueryRunner(isLegacyLog());
        }

        @AfterMethod(alwaysRun = true)
        public void tearDown()
        {
            QueryManager queryManager = queryRunner.getCoordinator().getQueryManager();
            queryManager.getAllQueryInfo().forEach(queryInfo -> queryManager.cancelQuery(queryInfo.getQueryId()));
            queryRunner.close();
        }

        protected MaterializedResult getResult()
        {
            return queryRunner.execute(QUERY);
        }
    }

    public static class Enabled
            extends AbstractTest
    {
        private DistributedQueryRunner queryRunner;

        @Override
        public boolean isLegacyLog()
        {
            return true;
        }

        @Test
        public void testLegacyLogFunctionEnabled()
                throws Exception
        {
            MaterializedResult result = getResult();
            assertEquals(result.getOnlyValue(), 2.0);
        }
    }

    public static class Disabled
            extends AbstractTest
    {
        private DistributedQueryRunner queryRunner;

        @Override
        public boolean isLegacyLog()
        {
            return false;
        }

        @Test
        public void testLegacyLogFunctionDisabled()
        {
            try {
                MaterializedResult result = getResult();
                fail("Legacy log function should be disabled");
            }
            catch (Exception e) {
                assertTrue(e.getMessage().contains("Function log not registered"));
            }
        }
    }

    public static DistributedQueryRunner createQueryRunner(boolean legacyLogFunction)
            throws Exception
    {
        return new DistributedQueryRunner(testSessionBuilder().build(),
                1,
                legacyLogFunction ? ImmutableMap.of("deprecated.legacy-log-function", "true") : ImmutableMap.of(),
                ImmutableMap.of(),
                new SqlParserOptions());
    }
}

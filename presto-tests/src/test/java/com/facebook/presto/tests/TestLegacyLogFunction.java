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

import com.facebook.presto.testing.MaterializedResult;
import com.facebook.presto.tests.tpch.TpchQueryRunner;
import com.google.common.collect.ImmutableMap;
import io.airlift.log.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestLegacyLogFunction
{
    private static final Logger log = Logger.get(TestLegacyLogFunction.class);

    @Test
    public void testLegacyLogFunctionEnabled()
            throws Exception
    {
        DistributedQueryRunner queryRunner = TpchQueryRunner.createQueryRunner();
        MaterializedResult result = queryRunner.execute("select log(25, 5)");
        assertEquals(result.getOnlyValue(), 2.0);
    }

    @Test
    public void testLegacyLogFunctionDisabled()
    {
        try {
            DistributedQueryRunner queryRunner = TpchQueryRunner.createQueryRunner(ImmutableMap.of("deprecated.legacy-log-function-enabled", "false"));
            MaterializedResult result = queryRunner.execute("select log(25, 5)");
        }
        catch (Exception e) {
            assertTrue(e.getMessage().contains("Function log not registered"));
        }
    }
}

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
package com.facebook.presto.kudu;

import com.facebook.presto.spi.type.VarcharType;
import com.facebook.presto.testing.MaterializedResult;
import com.facebook.presto.testing.MaterializedRow;
import com.facebook.presto.tests.AbstractTestIntegrationSmokeTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.facebook.presto.testing.assertions.Assert.assertEquals;
import static io.airlift.tpch.TpchTable.ORDERS;

public class TestIntegrationSmoke
        extends AbstractTestIntegrationSmokeTest
{
    private KuduQueryRunnerTpch kuduQueryRunner;

    public TestIntegrationSmoke()
    {
        super(() -> KuduQueryRunnerTpch.createKuduQueryRunner(ORDERS));
    }

    @BeforeClass
    public void setUp()
    {
        kuduQueryRunner = (KuduQueryRunnerTpch) getQueryRunner();
    }

    /**
     * Overrides original implementation because of usage of 'extra' column.
     */
    @Test
    @Override
    public void testDescribeTable()
    {
        MaterializedResult actualColumns = this.computeActual("DESC ORDERS").toTestTypes();
        MaterializedResult.Builder builder = MaterializedResult.resultBuilder(this.getQueryRunner().getDefaultSession(), VarcharType.VARCHAR, VarcharType.VARCHAR, VarcharType.VARCHAR, VarcharType.VARCHAR);
        for (MaterializedRow row : actualColumns.getMaterializedRows()) {
            builder.row(row.getField(0), row.getField(1), "", "");
        }
        MaterializedResult filteredActual = builder.build();
        builder = MaterializedResult.resultBuilder(this.getQueryRunner().getDefaultSession(), VarcharType.VARCHAR, VarcharType.VARCHAR, VarcharType.VARCHAR, VarcharType.VARCHAR);
        MaterializedResult expectedColumns = builder
                .row("orderkey", "bigint", "", "")
                .row("custkey", "bigint", "", "")
                .row("orderstatus", "varchar", "", "")
                .row("totalprice", "double", "", "")
                .row("orderdate", "varchar", "", "")
                .row("orderpriority", "varchar", "", "")
                .row("clerk", "varchar", "", "")
                .row("shippriority", "integer", "", "")
                .row("comment", "varchar", "", "").build();
        assertEquals(filteredActual, expectedColumns, String.format("%s != %s", filteredActual, expectedColumns));
    }

    @AfterClass(alwaysRun = true)
    public final void destroy()
    {
        kuduQueryRunner.shutdown();
        kuduQueryRunner = null;
    }
}

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

import com.facebook.presto.testing.LocalQueryRunner;
import com.facebook.presto.testing.MaterializedResult;
import com.facebook.presto.testing.QueryRunner;
import org.testng.annotations.Test;

import static com.facebook.presto.testing.TestingSession.testSessionBuilder;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestIsNotDistinctFrom
{
    @Test
    public void testNan()
    {
        try (QueryRunner queryRunner = createQueryRunner()) {
            MaterializedResult result = queryRunner.execute("select nan() is not distinct from nan()");
            assertTrue((Boolean) result.getOnlyValue());
        }
    }

    @Test
    public void testDistinctNan()
    {
        try (QueryRunner queryRunner = createQueryRunner()) {
            MaterializedResult result = queryRunner.execute("select distinct a/a FROM (VALUES (0.0), (0.0)) x (a)");
            assertTrue(Double.isNaN((Double) result.getOnlyValue()));
        }
    }

    @Test
    public void testGroupByNan()
    {
        try (QueryRunner queryRunner = createQueryRunner()) {
            MaterializedResult result = queryRunner.execute("select * from (values nan(), nan(), nan()) group by 1");
            assertTrue(Double.isNaN((Double) result.getOnlyValue()));
        }
    }

    @Test
    public void testRow()
    {
        try (QueryRunner queryRunner = createQueryRunner()) {
            // String query1 = "select a, sum(b) from (values (row(nan(), 2)), (row(nan(),4))) t(a,b) group by a";
            String query2 =
                    "SELECT a.col1[1].col0, SUM(a.col0), SUM(a.col1[1].col1), SUM(a.col1[2].col0), SUM(a.col2.col1) " +
                            "FROM (VALUES ROW(CAST(ROW(2.2, ARRAY[row(31, 4.2E0), row(22, 4.2E0)], row(5, 4.0E0)) " +
                            "AS ROW(col0 double, col1 array(row(col0 integer, col1 double)), col2 row(col0 integer, col1 double)))), " +
                            "ROW(CAST(ROW(1.0, ARRAY[row(31, 4.5E0), row(12, 4.2E0)], row(3, 4.1E0)) AS " +
                            "ROW(col0 double, col1 array(row(col0 integer, col1 double)), col2 row(col0 integer, col1 double)))), " +
                            "ROW(CAST(ROW(3.1, ARRAY[row(41, 3.1E0), row(32, 4.2E0)], row(6, 6.0E0)) AS " +
                            "ROW(col0 double, col1 array(row(col0 integer, col1 double)), col2 row(col0 integer, col1 double)))), " +
                            "ROW(CAST(ROW(3.3, ARRAY[row(41, 3.1E0), row(32, 4.2E0)], row(6, 6.0E0)) AS " +
                            "ROW(col0 double, col1 array(row(col0 integer, col1 double)), col2 row(col0 integer, col1 double)))) ) t(a) " +
                            "GROUP BY a.col1[1]";
            // String query3 = "select a from (values (array[nan(),2,3]), (array[nan(), 2, 3])) t(a) group by 1";
            // String query4 = "select cast(a as integer) from (values (1), (1), (1)) t(a) group by 1";
            MaterializedResult result = queryRunner.execute(query2);
            assertEquals(result.getRowCount(), 3);
        }
    }

    private static QueryRunner createQueryRunner()
    {
        return new LocalQueryRunner(testSessionBuilder().build());
    }
}

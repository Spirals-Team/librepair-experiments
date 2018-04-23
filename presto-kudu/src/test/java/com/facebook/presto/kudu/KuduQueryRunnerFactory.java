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

import com.facebook.presto.Session;
import com.facebook.presto.testing.QueryRunner;
import com.facebook.presto.tests.DistributedQueryRunner;
import com.facebook.presto.tpch.TpchPlugin;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.airlift.tpch.TpchTable;

import java.util.Map;

import static com.facebook.presto.spi.type.TimeZoneKey.UTC_KEY;
import static com.facebook.presto.testing.TestingSession.testSessionBuilder;
import static com.facebook.presto.tests.QueryAssertions.copyTpchTables;
import static com.facebook.presto.tpch.TpchMetadata.TINY_SCHEMA_NAME;
import static io.airlift.testing.Closeables.closeAllSuppress;
import static java.util.Locale.ENGLISH;

public class KuduQueryRunnerFactory
{
    private KuduQueryRunnerFactory() {}

    public static QueryRunner createKuduQueryRunner(String schema)
            throws Exception
    {
        QueryRunner runner = null;
        try {
            runner = DistributedQueryRunner.builder(createSession(schema)).setNodeCount(3).build();

            installKuduConnector(runner, schema);

            return runner;
        }
        catch (Throwable e) {
            closeAllSuppress(e, runner);
            throw e;
        }
    }

    public static QueryRunner createKuduQueryRunnerTpch(TpchTable<?>... tables)
            throws Exception
    {
        return createKuduQueryRunnerTpch(ImmutableList.copyOf(tables));
    }

    public static QueryRunner createKuduQueryRunnerTpch(Iterable<TpchTable<?>> tables)
            throws Exception
    {
        DistributedQueryRunner runner = null;
        try {
            runner = DistributedQueryRunner.builder(createSession("tpch")).setNodeCount(3).build();

            runner.installPlugin(new TpchPlugin());
            runner.createCatalog("tpch", "tpch");

            installKuduConnector(runner, "tpch");

            copyTpchTables(runner, "tpch", TINY_SCHEMA_NAME, createSession("tpch"), tables);

            return runner;
        }
        catch (Throwable e) {
            closeAllSuppress(e, runner);
            throw e;
        }
    }

    private static void installKuduConnector(QueryRunner runner, String schema)
    {
        String masterAddresses = System.getProperty("kudu.client.master-addresses", "localhost:7051");
        Map<String, String> properties = ImmutableMap.of(
                "kudu.client.master-addresses", masterAddresses);

        runner.installPlugin(new KuduPlugin());
        runner.createCatalog("kudu", "kudu", properties);

        runner.execute("DROP SCHEMA IF EXISTS " + schema);
        runner.execute("CREATE SCHEMA " + schema);
    }

    public static Session createSession(String schema)
    {
        return testSessionBuilder()
                .setCatalog("kudu")
                .setSchema(schema)
                .setTimeZoneKey(UTC_KEY)
                .setLocale(ENGLISH)
                .build();
    }
}

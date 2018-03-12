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

package com.facebook.presto.execution;

import com.facebook.presto.Session;
import com.facebook.presto.SystemSessionProperties;
import com.facebook.presto.execution.TestEventListener.EventsBuilder;
import com.facebook.presto.execution.TestEventListenerPlugin.TestingEventListenerPlugin;
import com.facebook.presto.spi.WarningCode;
import com.facebook.presto.testing.QueryRunner;
import com.facebook.presto.tests.DistributedQueryRunner;
import com.google.common.collect.ImmutableMap;
import org.intellij.lang.annotations.Language;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

import static com.facebook.presto.testing.TestingSession.testSessionBuilder;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static com.google.common.collect.Iterables.getOnlyElement;
import static org.testng.Assert.fail;

@Test(singleThreaded = true)
public class TestCompletionEventWarnings
{
    private QueryRunner queryRunner;
    private EventsBuilder generatedEvents;

    @BeforeClass
    public void setUp()
            throws Exception
    {
        Session.SessionBuilder sessionBuilder = testSessionBuilder();
        generatedEvents = new EventsBuilder();
        queryRunner = DistributedQueryRunner.builder(sessionBuilder.build()).setNodeCount(1).build();
        queryRunner.installPlugin(new TestingEventListenerPlugin(generatedEvents));
    }

    @AfterClass
    public void tearDown()
    {
        queryRunner.close();
    }

    @Test
    public void testLegacyOrderBy()
            throws Exception
    {
        assertWarnings("SELECT -a AS a FROM (VALUES -1, 0, 2) t(a) ORDER BY -a",
                ImmutableMap.of(SystemSessionProperties.LEGACY_ORDER_BY, "true"),
                3,
                WarningCode.LEGACY_ORDER_BY);
    }

    @Test
    public void testOrderBy()
            throws Exception
    {
        assertNoWarnings("SELECT -a AS a FROM (VALUES -1, 0, 2) t(a) ORDER BY -a",
                ImmutableMap.of(),
                3);
    }

    private void assertNoWarnings(@Language("SQL") String sql, Map<String, String> sessionProperties, int expectedEvents)
            throws Exception
    {
        assertWarnings(sql, sessionProperties, expectedEvents);
    }

    private void assertWarnings(@Language("SQL") String sql,
                                Map<String, String> sessionProperties,
                                int expectedEvents, WarningCode... expectedWarnings)
            throws Exception
    {
        Session.SessionBuilder sessionBuilder = testSessionBuilder()
                .setSystemProperty("task_concurrency", "1");
        sessionProperties.forEach(sessionBuilder::setSystemProperty);
        generatedEvents.initialize(expectedEvents);
        queryRunner.execute(sessionBuilder.build(), sql);
        generatedEvents.waitForEvents(10);
        Set<WarningCode> warnings = getOnlyElement(generatedEvents.getQueryCompletedEvents()).getQueryWarnings()
                .stream()
                .map(warning -> WarningCode.valueOf(warning.getCode()))
                .collect(toImmutableSet());
        for (WarningCode warningCode : expectedWarnings) {
            if (!warnings.contains(warningCode)) {
                fail("Expected warning");
            }
        }
    }
}

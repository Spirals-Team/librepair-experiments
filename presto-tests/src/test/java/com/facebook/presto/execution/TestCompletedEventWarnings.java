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
import com.facebook.presto.Session.SessionBuilder;
import com.facebook.presto.SystemSessionProperties;
import com.facebook.presto.execution.TestEventListener.EventsBuilder;
import com.facebook.presto.execution.TestEventListenerPlugin.TestingEventListenerPlugin;
import com.facebook.presto.spi.PrestoWarning;
import com.facebook.presto.spi.WarningCode;
import com.facebook.presto.testing.QueryRunner;
import com.facebook.presto.tests.DistributedQueryRunner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.intellij.lang.annotations.Language;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.facebook.presto.testing.TestingSession.testSessionBuilder;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static com.google.common.collect.Iterables.getOnlyElement;
import static org.testng.Assert.fail;

@Test(singleThreaded = true)
public class TestCompletedEventWarnings
{
    // QueryCreated, SplitCompleted, QueryCompleted
    private static final int EXPECTED_EVENTS = 3;
    private QueryRunner queryRunner;
    private EventsBuilder generatedEvents;

    @BeforeMethod
    public void setUp()
            throws Exception
    {
        SessionBuilder sessionBuilder = testSessionBuilder();
        generatedEvents = new EventsBuilder();
        queryRunner = DistributedQueryRunner.builder(sessionBuilder.build()).setNodeCount(1).build();
        queryRunner.installPlugin(new TestingEventListenerPlugin(generatedEvents));
        generatedEvents.initialize(EXPECTED_EVENTS);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        queryRunner.close();
        queryRunner = null;
        generatedEvents = null;
    }

    @Test
    public void testLegacyOrderBy()
            throws Exception
    {
        assertWarnings("SELECT -a AS a FROM (VALUES -1, 0, 2) t(a) ORDER BY -a",
                ImmutableMap.of(SystemSessionProperties.LEGACY_ORDER_BY, "true"),
                ImmutableList.of(WarningCode.LEGACY_ORDER_BY));
    }

    @Test
    public void testOrderBy()
            throws Exception
    {
        assertNoWarnings("SELECT -a AS a FROM (VALUES -1, 0, 2) t(a) ORDER BY -a", ImmutableMap.of(SystemSessionProperties.LEGACY_ORDER_BY, "false"));
    }

    private void assertNoWarnings(@Language("SQL") String sql, Map<String, String> sessionProperties)
            throws Exception
    {
        assertWarnings(sql, sessionProperties, ImmutableList.of());
    }

    private void assertWarnings(@Language("SQL") String sql,
                                Map<String, String> sessionProperties,
                                List<WarningCode> expectedWarnings)
            throws InterruptedException
    {
        // Task concurrency must be 1 otherwise these tests fail due to change in the number of EXPECTED_EVENTS
        Session.SessionBuilder sessionBuilder = testSessionBuilder()
                .setSystemProperty("task_concurrency", "1");
        sessionProperties.forEach(sessionBuilder::setSystemProperty);
        queryRunner.execute(sessionBuilder.build(), sql);
        generatedEvents.waitForEvents(10);

        Set<WarningCode> warnings = getOnlyElement(generatedEvents.getQueryCompletedEvents())
                .getWarnings()
                .stream()
                .map(PrestoWarning::getCode)
                .collect(toImmutableSet());
        for (WarningCode warningCode : expectedWarnings) {
            if (!warnings.contains(warningCode)) {
                fail("Expected warning: " + warningCode);
            }
        }
    }
}

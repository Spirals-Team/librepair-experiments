/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.core.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayDeque;
import java.util.Collection;

import net.atf4j.core.TestContext;
import net.atf4j.core.TestResult;
import net.atf4j.core.TestResultsReporting;
import net.atf4j.core.VerificationError;

/**
 * Abstract Test Base Class.
 */
public abstract class AbstractTestBase extends TestResultsReporting {

    protected TestIdentifier uniqueIdentifier = new TestIdentifier();
    protected TestResult testResult = TestResult.PENDING;
    protected TestReport testReport;

    private String tester; // test actor
    private String name; // short name of test
    private String taxonomy; // taxonomy of test
    private String description; // full description of test
    private String timestamp; // ISO date.
    private Collection<Condition> preConditions;
    private Collection<Condition> postConditions;

    /**
     * Instantiates a new test base.
     */
    public AbstractTestBase() {
        super();
        testResult = TestResult.initialise();
        uniqueIdentifier = new TestIdentifier();
    }

    /**
     * Instantiates a new test base.
     *
     * @param name the name
     */
    public AbstractTestBase(final String name) {
        super();
        this.name = name;
        testResult = TestResult.initialise();
        uniqueIdentifier = new TestIdentifier();
    }

    /**
     * Register logging.
     *
     * @param logging the logging
     * @return the test base
     * @throws VerificationError the atf4j exception
     */
    public AbstractTestBase registerLogging(final TestReport logging) throws VerificationError {
        setLogging(logging);
        return this;
    }

    /**
     * Execute.
     *
     * @param context the context
     * @return the test result
     * @throws VerificationError the atf4j exception
     */
    public AbstractTestBase execute(final TestContext context) throws VerificationError {
        throw new VerificationError("execute Must be overridden.");
    }

    /**
     * Adds the pre condition.
     *
     * @param newPreCondition as Condition
     * @return success as boolean.
     * @see java.util.Collection#add(java.lang.Object)
     */
    public AbstractTestBase addPreCondition(final Condition newPreCondition) {
        if (preConditions == null) {
            preConditions = new ArrayDeque<Condition>();
        }
        assumeTrue(preConditions.add(newPreCondition));
        return this;
    }

    /**
     * Assumed pre conditions.
     *
     * @return the abstract test base
     */
    public AbstractTestBase assumedPreConditions() {
        if (preConditions == null) {
            return this;
        } else {
            for (final Condition condition : postConditions) {
                assumeTrue(condition.isTrue());
            }
        }
        return this;
    }

    /**
     * Adds the post condition.
     *
     * @param newPostCondition as Condition.
     * @return success as boolean.
     * @see java.util.Collection#add(java.lang.Object)
     */
    public AbstractTestBase addPostCondition(final Condition newPostCondition) {
        if (postConditions == null) {
            postConditions = new ArrayDeque<Condition>();
        }
        assumeTrue(postConditions.add(newPostCondition));
        return this;
    }

    /**
     * Assert post conditions.
     *
     * @return the abstract test base
     */
    public AbstractTestBase assertPostConditions() {
        if (postConditions == null) {
            return this;
        } else {
            for (final Condition condition : postConditions) {
                assertTrue(condition.isTrue());
            }
        }
        return this;
    }

    /**
     * Gets the test status.
     *
     * @return the testStatus
     */
    protected TestResult getTestStatus() {
        return testResult;
    }

    /**
     * Sets the test status.
     *
     * @param testStatus the testStatus to set
     * @return the test base
     */
    protected AbstractTestBase setTestStatus(final TestResult testStatus) {
        testResult = testStatus;
        return this;
    }

    /**
     * Gets the unique identifier.
     *
     * @return the uniqueIdentifier
     */
    protected TestIdentifier getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    /**
     * Sets the unique identifier.
     *
     * @param uniqueIdentifier the uniqueIdentifier to set
     * @return the test base
     */
    protected AbstractTestBase setUniqueIdentifier(final TestIdentifier uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
        return this;
    }

    /**
     * Gets the tester.
     *
     * @return the tester
     */
    protected String getTester() {
        return tester;
    }

    /**
     * Sets the tester.
     *
     * @param tester the tester to set
     * @return the test base
     */
    protected AbstractTestBase setTester(final String tester) {
        this.tester = tester;
        return this;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    protected String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     * @return the test base
     */
    protected AbstractTestBase setName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the taxonomy.
     *
     * @return the taxonomy
     */
    protected String getTaxonomy() {
        return taxonomy;
    }

    /**
     * Sets the taxonomy.
     *
     * @param taxonomy the taxonomy to set
     * @return the test base
     */
    protected AbstractTestBase setTaxonomy(final String taxonomy) {
        this.taxonomy = taxonomy;
        return this;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    protected String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the description to set
     * @return the test base
     */
    protected AbstractTestBase setDescription(final String description) {
        this.description = description;
        return this;
    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    protected String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp the timestamp to set
     * @return the test base
     */
    protected AbstractTestBase setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Gets the pre conditions.
     *
     * @return the preConditions
     */
    protected Collection<Condition> getPreConditions() {
        return preConditions;
    }

    /**
     * Gets the post conditions.
     *
     * @return the postConditions
     */
    protected Collection<Condition> getPostConditions() {
        return postConditions;
    }

    /**
     * Gets the logging.
     *
     * @return the logging
     */
    public TestReport getLogging() {
        return testReport;
    }

    /**
     * Sets the logging.
     *
     * @param logging the new logging
     * @return the test base
     */
    public AbstractTestBase setLogging(final TestReport logging) {
        testReport = logging;
        return this;
    }

    /**
     * Result.
     *
     * @return the test result
     */
    public TestResult result() {
        return testResult;
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    public String debugString() {
        return String.format(
                "%s [testStatus=%s, uniqueIdentifier=%s, tester=%s, name=%s, taxonomy=%s, description=%s, timestamp=%s, preConditions=%s, postConditions=%s]",
                this.getClass().getSimpleName(),
                testResult,
                uniqueIdentifier,
                tester,
                name,
                taxonomy,
                description,
                timestamp,
                preConditions,
                postConditions);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return debugString();
    }

}

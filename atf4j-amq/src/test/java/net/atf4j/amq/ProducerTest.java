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

package net.atf4j.amq;

import static org.junit.Assume.assumeTrue;

import java.util.Arrays;

import javax.jms.JMSException;

import org.junit.Test;

import net.atf4j.core.TestContext;
import net.atf4j.core.TestResultsReporting;

/**
 * The ProducerTest Class.
 */
public class ProducerTest extends TestResultsReporting {

    /**
     * Unit test the message producer default constructor.
     *
     * @throws JMSException the JMS exception
     */
    @Test
    public void testProducer() throws JMSException {
        assumeTrue(TestContext.isActiveMQ());
        verifyNotNull(new Producer());
    }

    /**
     * Unit Test execute.
     *
     * @throws JMSException the JMS exception
     */
    @Test
    public void testExecute() throws JMSException {
        assumeTrue(TestContext.isActiveMQ());
        final Producer producer = new Producer();
        verifyNotNull(producer);

        final String[] messages = null;
        log.debug("{}", Arrays.toString(messages));
        verifyNotNull(producer.execute(messages));
    }
}

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

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import net.atf4j.core.TestResultsReporting;

/**
 * An abstract class to wrap ActiveMQ Connections.
 */
public abstract class AbstractConnectionWrapper
        extends TestResultsReporting
        implements ConnectionWrapperInterface {

    /** URL. */
    protected static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    /** connection factory. */
    protected ConnectionFactory connectionFactory;

    /** connection. */
    protected Connection connection;

    /** session. */
    protected Session session;

    /** topic name. */
    protected String topicName = "testQueue";

    /** topic. */
    protected Topic topic;

    /**
     * Default constructor instantiates a new connection wrapper.
     *
     * @throws JMSException the JMS exception exception.
     */
    protected AbstractConnectionWrapper() throws JMSException {
        super();
        connection = initialise();
        session = newSession(connection);
        topic = session.createTopic(topicName);
    }

    /**
     * Initialise the connection.
     *
     * @return the connection
     * @throws JMSException the JMS exception exception.
     */
    protected Connection initialise() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();
        return connection;
    }

    /**
     * New session on the connection.
     *
     * @param connection the connection
     * @return the session
     * @throws JMSException the JMS exception exception.
     */
    protected Session newSession(final Connection connection) throws JMSException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
}

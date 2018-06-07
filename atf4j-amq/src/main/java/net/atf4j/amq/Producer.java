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

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

/**
 * Message Producer Class.
 */
public final class Producer extends AbstractConnectionWrapper {

    /**
     * Instantiates a new producer.
     *
     * @throws JMSException the JMS exception
     */
    public Producer() throws JMSException {
        super();
    }

    /**
     * Execute, add the messages to the queue.
     *
     * @param messages the messages
     * @return
     * @throws JMSException the JMS exception
     */
    public Producer execute(final String[] messages) throws JMSException {

        final MessageProducer messageProducer = session.createProducer(topic);

        final TextMessage textMessage = session.createTextMessage();

        for (final String messageString : messages) {
            textMessage.setText(messageString);
            messageProducer.send(textMessage);
            log.info("Sent {}", messageString);
        }
        connection.close();
        return this;
    }
}

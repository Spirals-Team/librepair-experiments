/*
 * DomUI Java User Interface library
 * Copyright (c) 2010 by Frits Jalvingh, Itris B.V.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * See the "sponsors" file for a list of supporters.
 *
 * The latest version of DomUI and related code, support and documentation
 * can be found at http://www.domui.org/
 * The contact for the project is Frits Jalvingh <jal@etc.to>.
 */
package to.etc.webapp.ajax.eventmanager;

/**
 * Holds an event as generated by the code, and the data
 * needed to cleanup the event.
 *
 * @author <a href="mailto:jal@etc.to">Frits Jalvingh</a>
 * Created on Oct 25, 2006
 */
final public class QueuedEvent {
	/** The channel that this event fires on */
	private final String m_channel;

	private final Object m_data;

	/** The framework-assigned event ID */
	private final int m_eventID;

	/** The time the event was fired (for expiry pps) */
	private final long m_eventTS;

	public QueuedEvent(final int eventID, final long eventTS, final String channel, final Object data) {
		m_eventID = eventID;
		m_eventTS = eventTS;
		m_channel = channel;
		m_data = data;
	}

	QueuedEvent createCopy(final Object newdata) {
		return new QueuedEvent(m_eventID, m_eventTS, m_channel, newdata);
	}

	public String getChannel() {
		return m_channel;
	}

	public Object getData() {
		return m_data;
	}

	public int getEventID() {
		return m_eventID;
	}

	long getEventTS() {
		return m_eventTS;
	}
}

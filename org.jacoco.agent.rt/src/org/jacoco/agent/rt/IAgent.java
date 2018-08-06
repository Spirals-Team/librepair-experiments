/*******************************************************************************
 * Copyright (c) 2009, 2018 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Evgeny Mandrikov - initial API and implementation
 *
 *******************************************************************************/
package org.jacoco.agent.rt;

import java.io.IOException;

/**
 * Runtime API and MBean agent interface.
 */
public interface IAgent {

	/**
	 * Returns version of JaCoCo.
	 * 
	 * @return version of JaCoCo
	 */
	String getVersion();

	/**
	 * Returns current a session identifier.
	 * 
	 * @return current session identifier
	 */
	String getSessionId();

	/**
	 * Sets a session identifier.
	 * 
	 * @param id
	 *            new session identifier
	 */
	void setSessionId(String id);

	/**
	 * Resets all coverage information.
	 */
	void reset();

	/**
	 * Returns current execution data.
	 * 
	 * @param reset
	 *            if <code>true</code> the current execution data is cleared
	 *            afterwards
	 * @return dump of current execution data in JaCoCo binary format
	 */
	byte[] getExecutionData(boolean reset);

	/**
	 * Triggers a dump of the current execution data through the configured
	 * output.
	 * 
	 * @param reset
	 *            if <code>true</code> the current execution data is cleared
	 *            afterwards
	 * @throws IOException
	 *             if the output can't write execution data
	 */
	void dump(boolean reset) throws IOException;

}

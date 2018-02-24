/*  Copyright (C) 2016  Nicholas Wright
    
    This file is part of similarImage - A similar image finder using pHash
    
    similarImage is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.dozedoff.similarImage.messaging;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.apache.activemq.artemis.core.config.FileDeploymentManager;
import org.apache.activemq.artemis.core.config.impl.FileConfiguration;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Setup an embedded Artemis server.
 * 
 * @author Nicholas Wright
 *
 */

public class ArtemisEmbeddedServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtemisEmbeddedServer.class);
	private final EmbeddedActiveMQ server;

	/**
	 * Create a new embedded artemis server that listens to network and in VM connections. he data directory will be
	 * created in the current working directory.
	 * 
	 * @throws Exception
	 *             if the server setup failed
	 * @deprecated Use dagger 2 injection
	 */
	@Deprecated
	public ArtemisEmbeddedServer() throws Exception {
		this(Paths.get(""));
	}

	/**
	 * Create a new embedded artemis server that listens to network and in VM connections. The data directory will be
	 * created in the given path.
	 * 
	 * @param workingDirectory
	 *            base directory where the data directory will be created
	 * @throws Exception
	 *             if the server setup failed
	 * @deprecated Use dagger 2 injection
	 */
	@Deprecated
	public ArtemisEmbeddedServer(Path workingDirectory) throws Exception {

		FileConfiguration config = new FileConfiguration();
		FileDeploymentManager fdm = new FileDeploymentManager("broker.xml");
		fdm.addDeployable(config);
		fdm.readConfiguration();

		config.setBrokerInstance(workingDirectory.toFile());

		server = new EmbeddedActiveMQ();
		server.setConfiguration(config);
	}
	
	/**
	 * Create a new embedded artemis server based on the passed configuration.
	 * 
	 * @param fileConfig
	 *            the file based server configuration
	 */
	@Inject
	public ArtemisEmbeddedServer(FileConfiguration fileConfig) {
			server = new EmbeddedActiveMQ();
			server.setConfiguration(fileConfig);
	}

	/**
	 * Start the artemis server.
	 * 
	 * @throws Exception
	 *             if things go pear shaped
	 */
	public void start() throws Exception {
		server.start();
	}

	/**
	 * Stop the artemis server.
	 * 
	 * @throws Exception
	 *             if things go pear shaped
	 */
	public void stop() throws Exception {
		server.stop();
	}
}

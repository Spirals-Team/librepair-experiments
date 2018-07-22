/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.airavata.xbaya;

public class ThriftClientData {
	private String serverAddress;
	private int serverPort;
	private String gatewayId;
	private String username;
	private ThriftServiceType serviceType;
	
	public ThriftClientData(ThriftServiceType serviceName, String serverAddress,
			int serverPort, String gatewayId, String username) {
		this.serviceType = serviceName;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.gatewayId = gatewayId;
		this.username = username;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public String getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ThriftServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ThriftServiceType serviceName) {
		this.serviceType = serviceName;
	}
	
}

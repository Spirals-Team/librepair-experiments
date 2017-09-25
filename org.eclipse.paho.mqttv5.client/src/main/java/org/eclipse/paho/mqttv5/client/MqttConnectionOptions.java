/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *    Dave Locke - initial API and implementation and/or initial documentation
 *    Ian Craggs - MQTT 3.1.1 support
 *    James Sutton - Automatic Reconnect & Offline Buffering
 *    James Sutton - MQTT v5
 */
package org.eclipse.paho.mqttv5.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;

import org.eclipse.paho.mqttv5.client.util.Debug;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.UserProperty;

/**
 * Holds the set of options that control how the client connects to a server.
 */
public class MqttConnectionOptions {

	static enum UriType {
		TCP, SSL, WS, WSS
	}
	
	private static final String CLIENT_ID_PREFIX = "paho";

	// Connection Properties
	private String[] serverURIs = null; // List of Servers to connect to in order
	private boolean automaticReconnect = false; // Automatic Reconnect
	private int automaticReconnectMinDelay = 1; // Time to wait before first automatic reconnection attempt in seconds.
	private int automaticReconnectMaxDelay = 120; // Maximum time to wait for automatic reconnection attempts in
													// seconds.
	private int keepAliveInterval = 60; // Keep Alive Interval
	private int maxInflight = 10; // Max inflight messages
	private int connectionTimeout = 30; // Connection timeout in seconds

	// Connection packet properties
	private int MqttVersion = 5; // MQTT Version 5
	private boolean cleanSession = true; // Clean Session
	private String willDestination = null; // Will Topic
	private MqttMessage willMessage = null; // Will @link{MqttMessage}
	private String userName; // Username
	private byte[] password; // Password
	private Integer sessionExpiryInterval = null; // The Session expiry Interval in seconds, null is the default of
													// never.
	private Integer willDelayInterval = null; // The Will Delay Interval in seconds, null is the default of 0.
	private Integer receiveMaximum = null; // The Receive Maximum, null defaults to 65,535, cannot be 0.
	private Integer maximumPacketSize = null; // The Maximum packet size, null defaults to no limit.
	private Integer topicAliasMaximum = null; // The Topic Alias Maximum, null defaults to 0.
	private Boolean requestResponseInfo = null; // Request Response Information, null defaults to false.
	private Boolean requestProblemInfo = null; // Request Problem Information, null defaults to true.
	private ArrayList<UserProperty> userProperties = null; // User Defined Properties.
	private String authMethod = null; // Authentication Method, If null, Extended Authentication is not performed.
	private byte[] authData = null; // Authentication Data.

	// TLS Properties
	private SocketFactory socketFactory; // SocketFactory to be used to connect
	private Properties sslClientProps = null; // SSL Client Properties
	private HostnameVerifier sslHostnameVerifier = null; // SSL Hostname Verifier

	/**
	 * Constructs a new <code>linkMqttConnectionOptions</code> object using the
	 * default values.
	 *
	 * The defaults are:
	 * <ul>
	 * <li>The keep alive interval is 60 seconds</li>
	 * <li>Clean Session is true</li>
	 * <li>The message delivery retry interval is 15 seconds</li>
	 * <li>The connection timeout period is 30 seconds</li>
	 * <li>No Will message is set</li>
	 * <li>Automatic Reconnect is enabled, starting at 1 second and capped at two
	 * minutes</li>
	 * <li>A standard SocketFactory is used</li>
	 * </ul>
	 * More information about these values can be found in the setter methods.
	 */
	public MqttConnectionOptions() {
	}

	/**
	 * Returns the MQTT version.
	 * 
	 * @return the MQTT version.
	 */
	public int getMqttVersion() {
		return MqttVersion;
	}

	/**
	 * Returns the user name to use for the connection.
	 * 
	 * @return the user name to use for the connection.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name to use for the connection.
	 * 
	 * @param userName
	 *            The Username as a String
	 * @throws IllegalArgumentException
	 *             if the user name is blank or only contains whitespace characters.
	 */
	public void setUserName(String userName) {
		if ((userName != null) && (userName.trim().equals(""))) {
			throw new IllegalArgumentException();
		}
		this.userName = userName;
	}

	/**
	 * Returns the password to use for the connection.
	 * 
	 * @return the password to use for the connection.
	 */
	public byte[] getPassword() {
		return password;
	}

	/**
	 * Sets the password to use for the connection.
	 * 
	 * @param password
	 *            A Char Array of the password
	 */
	public void setPassword(byte[] password) {
		this.password = password;
	}

	/**
	 * Returns the topic to be used for last will and testament (LWT).
	 * 
	 * @return the MqttTopic to use, or <code>null</code> if LWT is not set.
	 * @see #setWill(String, MqttMessage)
	 */
	public String getWillDestination() {
		return willDestination;
	}

	/**
	 * Returns the message to be sent as last will and testament (LWT). The returned
	 * object is "read only". Calling any "setter" methods on the returned object
	 * will result in an <code>IllegalStateException</code> being thrown.
	 * 
	 * @return the message to use, or <code>null</code> if LWT is not set.
	 */
	public MqttMessage getWillMessage() {
		return willMessage;
	}

	/**
	 * Sets the "Last Will and Testament" (LWT) for the connection. In the event
	 * that this client unexpectedly looses it's connection to the server, the
	 * server will publish a message to itself using the supplied details.
	 * 
	 * @param topic
	 *            the topic to publish to.
	 * @param message
	 *            the {@link MqttMessage} to send
	 */
	public void setWill(String topic, MqttMessage message) {
		if (topic == null || message == null || message.getPayload() == null) {
			throw new IllegalArgumentException();
		}
		MqttTopic.validate(topic, false); // Wildcards are not allowed
		this.willDestination = topic;
		this.willMessage = message;
		// Prevent any more changes to the will message
		this.willMessage.setMutable(false);
	}

	/**
	 * Returns whether the client and server should remember state for the client
	 * across reconnects.
	 * 
	 * @return the clean session flag
	 */
	public boolean isCleanSession() {
		return this.cleanSession;
	}

	/**
	 * Sets whether the client and server should remember state across restarts and
	 * reconnects.
	 * <ul>
	 * <li>If set to false both the client and server will maintain state across
	 * restarts of the client, the server and the connection. As state is
	 * maintained:
	 * <ul>
	 * <li>Message delivery will be reliable meeting the specified QOS even if the
	 * client, server or connection are restarted.
	 * <li>The server will treat a subscription as durable.
	 * </ul>
	 * <li>If set to true the client and server will not maintain state across
	 * restarts of the client, the server or the connection. This means
	 * <ul>
	 * <li>Message delivery to the specified QOS cannot be maintained if the client,
	 * server or connection are restarted
	 * <li>The server will treat a subscription as non-durable
	 * </ul>
	 * </ul>
	 * 
	 * @param cleanSession
	 *            Set to True to enable cleanSession
	 */
	public void setCleanSession(boolean cleanSession) {
		this.cleanSession = cleanSession;
	}

	/**
	 * Returns the "keep alive" interval.
	 * 
	 * @see #setKeepAliveInterval(int)
	 * @return the keep alive interval.
	 */
	public int getKeepAliveInterval() {
		return keepAliveInterval;
	}

	/**
	 * Sets the "keep alive" interval. This value, measured in seconds, defines the
	 * maximum time interval between messages sent or received. It enables the
	 * client to detect if the server is no longer available, without having to wait
	 * for the TCP/IP timeout. The client will ensure that at least one message
	 * travels across the network within each keep alive period. In the absence of a
	 * data-related message during the time period, the client sends a very small
	 * "ping" message, which the server will acknowledge. A value of 0 disables
	 * keepalive processing in the client.
	 * <p>
	 * The default value is 60 seconds
	 * </p>
	 *
	 * @param keepAliveInterval
	 *            the interval, measured in seconds, must be &gt;= 0.
	 * @throws IllegalArgumentException
	 *             if the keepAliveInterval was invalid
	 */
	public void setKeepAliveInterval(int keepAliveInterval) throws IllegalArgumentException {
		if (keepAliveInterval < 0) {
			throw new IllegalArgumentException();
		}
		this.keepAliveInterval = keepAliveInterval;
	}

	/**
	 * Returns the "max inflight". The max inflight limits to how many messages we
	 * can send without receiving acknowledgments.
	 * 
	 * @see #setMaxInflight(int)
	 * @return the max inflight
	 */
	public int getMaxInflight() {
		return maxInflight;
	}

	/**
	 * Sets the "max inflight". please increase this value in a high traffic
	 * environment.
	 * <p>
	 * The default value is 10
	 * </p>
	 * 
	 * @param maxInflight
	 *            the number of maxInfligt messages
	 */
	public void setMaxInflight(int maxInflight) {
		if (maxInflight < 0) {
			throw new IllegalArgumentException();
		}
		this.maxInflight = maxInflight;
	}

	/**
	 * Returns the connection timeout value.
	 * 
	 * @see #setConnectionTimeout(int)
	 * @return the connection timeout value.
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * Sets the connection timeout value. This value, measured in seconds, defines
	 * the maximum time interval the client will wait for the network connection to
	 * the MQTT server to be established. The default timeout is 30 seconds. A value
	 * of 0 disables timeout processing meaning the client will wait until the
	 * network connection is made successfully or fails.
	 * 
	 * @param connectionTimeout
	 *            the timeout value, measured in seconds. It must be &gt;0;
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		if (connectionTimeout < 0) {
			throw new IllegalArgumentException();
		}
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * Return a list of serverURIs the client may connect to
	 * 
	 * @return the serverURIs or null if not set
	 */
	public String[] getServerURIs() {
		return serverURIs;
	}

	/**
	 * Set a list of one or more serverURIs the client may connect to.
	 * <p>
	 * Each <code>serverURI</code> specifies the address of a server that the client
	 * may connect to. Two types of connection are supported <code>tcp://</code> for
	 * a TCP connection and <code>ssl://</code> for a TCP connection secured by
	 * SSL/TLS. For example:
	 * <ul>
	 * <li><code>tcp://localhost:1883</code></li>
	 * <li><code>ssl://localhost:8883</code></li>
	 * </ul>
	 * If the port is not specified, it will default to 1883 for
	 * <code>tcp://</code>" URIs, and 8883 for <code>ssl://</code> URIs.
	 * <p>
	 * If serverURIs is set then it overrides the serverURI parameter passed in on
	 * the constructor of the MQTT client.
	 * <p>
	 * When an attempt to connect is initiated the client will start with the first
	 * serverURI in the list and work through the list until a connection is
	 * established with a server. If a connection cannot be made to any of the
	 * servers then the connect attempt fails.
	 * <p>
	 * Specifying a list of servers that a client may connect to has several uses:
	 * <ol>
	 * <li>High Availability and reliable message delivery
	 * <p>
	 * Some MQTT servers support a high availability feature where two or more
	 * "equal" MQTT servers share state. An MQTT client can connect to any of the
	 * "equal" servers and be assured that messages are reliably delivered and
	 * durable subscriptions are maintained no matter which server the client
	 * connects to.
	 * </p>
	 * <p>
	 * The cleansession flag must be set to false if durable subscriptions and/or
	 * reliable message delivery is required.
	 * </p>
	 * </li>
	 * <li>Hunt List
	 * <p>
	 * A set of servers may be specified that are not "equal" (as in the high
	 * availability option). As no state is shared across the servers reliable
	 * message delivery and durable subscriptions are not valid. The cleansession
	 * flag must be set to true if the hunt list mode is used
	 * </p>
	 * </li>
	 * </ol>
	 * 
	 * @param array
	 *            of serverURIs
	 */
	public void setServerURIs(String[] array) {
		for (int i = 0; i < array.length; i++) {
			validateURI(array[i]);
		}
		this.serverURIs = array;
	}

	/**
	 * Returns whether the client will automatically attempt to reconnect to the
	 * server if the connection is lost
	 * 
	 * @return the automatic reconnection flag.
	 */
	public boolean isAutomaticReconnect() {
		return automaticReconnect;
	}

	/**
	 * Sets whether the client will automatically attempt to reconnect to the server
	 * if the connection is lost.
	 * <ul>
	 * <li>If set to false, the client will not attempt to automatically reconnect
	 * to the server in the event that the connection is lost.</li>
	 * <li>If set to true, in the event that the connection is lost, the client will
	 * attempt to reconnect to the server. It will initially wait 1 second before it
	 * attempts to reconnect, for every failed reconnect attempt, the delay will
	 * double until it is at 2 minutes at which point the delay will stay at 2
	 * minutes.</li>
	 * </ul>
	 * 
	 * You can change the Minimum and Maximum delays by using
	 * {@link #setAutomaticReconnectDelay(int, int)}
	 * 
	 * This Defaults to true
	 * 
	 * @param automaticReconnect
	 *            If set to True, Automatic Reconnect will be enabled
	 */
	public void setAutomaticReconnect(boolean automaticReconnect) {
		this.automaticReconnect = automaticReconnect;
	}

	/**
	 * Sets the Minimum and Maximum delays used when attempting to automatically
	 * reconnect.
	 * 
	 * @param minDelay
	 *            the minimum delay to wait before attempting to reconnect in
	 *            seconds, defaults to 1 second.
	 * @param maxDelay
	 *            the maximum delay to wait before attempting to reconnect in
	 *            seconds, defaults to 120 seconds.
	 */
	public void setAutomaticReconnectDelay(int minDelay, int maxDelay) {
		this.automaticReconnectMinDelay = minDelay;
		this.automaticReconnectMaxDelay = maxDelay;
	}

	/**
	 * Returns the minimum number of seconds to wait before attempting to
	 * automatically reconnect.
	 * 
	 * @return the automatic reconnect minimum delay in seconds.
	 */
	public int getAutomaticReconnectMinDelay() {
		return automaticReconnectMinDelay;
	}

	/**
	 * Returns the maximum number of seconds to wait before attempting to
	 * automatically reconnect.
	 * 
	 * @return the automatic reconnect maximum delay in seconds.
	 */
	public int getAutomaticReconnectMaxDelay() {
		return automaticReconnectMaxDelay;
	}

	/**
	 * Returns the Session Expiry Interval. If <code>null</code>, this means the
	 * session will not expire.
	 * 
	 * @return the Session Expiry Interval in seconds.
	 */
	public Integer getSessionExpiryInterval() {
		return sessionExpiryInterval;
	}

	/**
	 * Sets the Session Expiry Interval. This value, measured in seconds, defines
	 * the maximum time that the broker will maintain the session for once the
	 * client disconnects. Clients should only connect with a long Session Expiry
	 * interval if they intend to connect to the server at some later point in time.
	 * 
	 * <ul>
	 * <li>By default this value is null and so will not be sent, in this case, the
	 * session will not expire.</li>
	 * <li>If a 0 is sent, the session will end immediately once the Network
	 * Connection is closed.</li>
	 * </ul>
	 * 
	 * When the client has determined that it has no longer any use for the session,
	 * it should disconnect with a Session Expiry Interval set to 0.
	 * 
	 * @param sessionExpiryInterval
	 *            The Session Expiry Interval in seconds.
	 */
	public void setSessionExpiryInterval(Integer sessionExpiryInterval) {
		this.sessionExpiryInterval = sessionExpiryInterval;
	}

	/**
	 * Returns the Will Delay Interval. If <code>null</code>, it will default to 0
	 * and there is no will delay before the will message is published.
	 * 
	 * @return The Will Delay Interval in seconds.
	 */
	public Integer getWillDelayInterval() {
		return willDelayInterval;
	}

	/**
	 * Sets the Will Delay Interval. This value, measured in seconds, defines the
	 * time that the server will wait to send the Client's will message after it has
	 * disconnected.
	 * <ul>
	 * <li>By default this value is null and so will not be sent, in this case, the
	 * default value is 0.</li>
	 * <li>If a 0 is sent, the will message will be sent immediately.</li>
	 * </ul>
	 * 
	 * The Server delays publishing the Client's Will Message until the Will Delay
	 * Interval has passed or the Session ends, whichever happens first.
	 * 
	 * @param willDelayInterval The will delay interval
	 */
	public void setWillDelayInterval(Integer willDelayInterval) {
		this.willDelayInterval = willDelayInterval;
	}

	/**
	 * Returns the Receive Maximum value. If <code>null</code>, it will default to
	 * 65,535.
	 * 
	 * @return the Receive Maxumum
	 */
	public Integer getReceiveMaximum() {
		return receiveMaximum;
	}

	/**
	 * Sets the Receive Maximum. This value represents the limit of QoS 1 and QoS 2
	 * publications that the client is willing to process concurrently. There is no
	 * mechanism to limit the number of QoS 0 publications that the Server might try
	 * to send.
	 * <ul>
	 * <li>If set to <code>null</code> then this value defaults to 65,535.</li>
	 * <li>If set, the minimum value for this property is 1.</li>
	 * <li>The maximum value for this property is 65,535.</li>
	 * </ul>
	 * 
	 * @param receiveMaximum
	 *            the Receive Maximum.
	 */
	public void setReceiveMaximum(Integer receiveMaximum) {
		if (receiveMaximum != null && (receiveMaximum == 0 || receiveMaximum > 65535)) {
			throw new IllegalArgumentException();
		}
		this.receiveMaximum = receiveMaximum;
	}

	/**
	 * Returns the Maximum Packet Size. If <code>null</code>, no limit is imposed.
	 * 
	 * @return the Maximum Packet Size in bytes.
	 */
	public Integer getMaximumPacketSize() {
		return maximumPacketSize;
	}

	/**
	 * Sets the Maximum Packet Size. This value represents the Maximum Packet Size
	 * the client is willing to accept.
	 * 
	 * <ul>
	 * <li>If set to <code>null</code> then no limit is imposed beyond the
	 * limitations of the protocol.</li>
	 * <li>If set, the minimum value for this property is 1.</li>
	 * <li>The maximum value for this property is 2,684,354,656.</li>
	 * </ul>
	 * 
	 * @param maximumPacketSize The Maximum packet size.
	 */
	public void setMaximumPacketSize(Integer maximumPacketSize) {
		this.maximumPacketSize = maximumPacketSize;
	}

	/**
	 * Returns the Topic Alias Maximum. If <code>null</code>, the default value is
	 * 0.
	 * 
	 * @return the Topic Alias Maximum.
	 */
	public Integer getTopicAliasMaximum() {
		return topicAliasMaximum;
	}

	/**
	 * Sets the Topic Alias Maximum. This value if present represents the highest
	 * value that the Client will accept as a Topic Alias sent by the Server.
	 * 
	 * <ul>
	 * <li>If set to <code>null</code>, then it will default to to 0.</li>
	 * <li>If set to 0, the Client will not accept any Topic Aliases</li>
	 * <li>The Maximum value for this property is 65535.</li>
	 * </ul>
	 * 
	 * @param topicAliasMaximum
	 *            the Topic Alias Maximum
	 */
	public void setTopicAliasMaximum(Integer topicAliasMaximum) {
		if (topicAliasMaximum != null && topicAliasMaximum > 65535) {
			throw new IllegalArgumentException();
		}
		this.topicAliasMaximum = topicAliasMaximum;
	}

	/**
	 * Returns the Request Response Info flag. If <code>null</code>, the default
	 * value is false.
	 * 
	 * @return The Request Response Info Flag.
	 */
	public Boolean getRequestResponseInfo() {
		return requestResponseInfo;
	}

	/**
	 * Sets the Request Response Info Flag.
	 * <ul>
	 * <li>If set to <code>null</code>, then it will default to false.</li>
	 * <li>If set to false, the server will not return any response information in
	 * the CONNACK.</li>
	 * <li>If set to true, the server MAY return response information in the
	 * CONNACK.</li>
	 * </ul>
	 * 
	 * @param requestResponseInfo
	 *            The Request Response Info Flag.
	 */
	public void setRequestResponseInfo(boolean requestResponseInfo) {
		this.requestResponseInfo = requestResponseInfo;
	}

	/**
	 * Returns the Request Problem Info flag. If <code>null</code>, the default
	 * value is true.
	 * 
	 * @return the Request Problem Info flag.
	 */
	public Boolean getRequestProblemInfo() {
		return requestProblemInfo;
	}

	/**
	 * Sets the Request Problem Info flag.
	 * <ul>
	 * <li>If set to <code>null</code>, then it will default to true.</li>
	 * <li>If set to false, the server MAY return a Reason String or User Properties
	 * on a CONNACK or DISCONNECT, but must not send a Reason String or User
	 * Properties on any packet other than PUBLISH, CONNACK or DISCONNECT.</li>
	 * <li>If set to true, the server MAY return a Reason String or User Properties
	 * on any packet where it is allowed.</li>
	 * </ul>
	 * 
	 * @param requestProblemInfo The Flag to request problem information.
	 */
	public void setRequestProblemInfo(boolean requestProblemInfo) {
		this.requestProblemInfo = requestProblemInfo;
	}

	/**
	 * Returns the User Properties.
	 * 
	 * @return the User Properties.
	 */
	public ArrayList<UserProperty> getUserProperties() {
		return userProperties;
	}

	/**
	 * Sets the User Properties. A User Property is a UTF-8 String Pair, the same
	 * name is allowed to appear more than once.
	 * 
	 * @param userProperties User Properties
	 */
	public void setUserProperties(ArrayList<UserProperty> userProperties) {
		this.userProperties = userProperties;
	}

	/**
	 * Returns the Authentication Method. If <code>null</code>, extended
	 * authentication is not performed.
	 * 
	 * @return the Authentication Method.
	 */
	public String getAuthMethod() {
		return authMethod;
	}

	/**
	 * Sets the Authentication Method. If set, this value contains the name of the
	 * authentication method to be used for extended authentication.
	 * 
	 * If <code>null</code>, extended authentication is not performed.
	 * 
	 * @param authMethod
	 *            The Authentication Method.
	 */
	public void setAuthMethod(String authMethod) {
		this.authMethod = authMethod;
	}

	/**
	 * Returns the Authentication Data.
	 * 
	 * @return the Authentication Data.
	 */
	public byte[] getAuthData() {
		return authData;
	}

	/**
	 * Sets the Authentication Data. If set, this byte array contains the extended
	 * authentication data, defined by the Authenticated Method. It is a protocol
	 * error to include Authentication Data if there is no Authentication Method.
	 * 
	 * @param authData
	 *            The Authentication Data
	 */
	public void setAuthData(byte[] authData) {
		this.authData = authData;
	}

	/**
	 * Validate a URI
	 * 
	 * @param srvURI
	 *            The Server URI
	 * @return the URI type
	 */
	public static UriType validateURI(String srvURI) {
		try {
			URI vURI = new URI(srvURI);
			if ("ws".equals(vURI.getScheme())) {
				return UriType.WS;
			} else if ("wss".equals(vURI.getScheme())) {
				return UriType.WSS;
			}

			if ((vURI.getPath() == null) || vURI.getPath().isEmpty()) {
				// No op path must be empty
			} else {
				throw new IllegalArgumentException(srvURI);
			}
			if ("tcp".equals(vURI.getScheme())) {
				return UriType.TCP;
			} else if ("ssl".equals(vURI.getScheme())) {
				return UriType.SSL;
			} else {
				throw new IllegalArgumentException(srvURI);
			}
		} catch (URISyntaxException ex) {
			throw new IllegalArgumentException(srvURI);
		}
	}

	/**
	 * Returns the socket factory that will be used when connecting, or
	 * <code>null</code> if one has not been set.
	 * 
	 * @return The Socket Factory
	 */
	public SocketFactory getSocketFactory() {
		return socketFactory;
	}

	/**
	 * Sets the <code>SocketFactory</code> to use. This allows an application to
	 * apply its own policies around the creation of network sockets. If using an
	 * SSL connection, an <code>SSLSocketFactory</code> can be used to supply
	 * application-specific security settings.
	 * 
	 * @param socketFactory
	 *            the factory to use.
	 */
	public void setSocketFactory(SocketFactory socketFactory) {
		this.socketFactory = socketFactory;
	}

	/**
	 * Returns the SSL properties for the connection.
	 * 
	 * @return the properties for the SSL connection
	 */
	public Properties getSSLProperties() {
		return sslClientProps;
	}

	/**
	 * Sets the SSL properties for the connection.
	 * <p>
	 * Note that these properties are only valid if an implementation of the Java
	 * Secure Socket Extensions (JSSE) is available. These properties are
	 * <em>not</em> used if a SocketFactory has been set using
	 * {@link #setSocketFactory(SocketFactory)}. The following properties can be
	 * used:
	 * </p>
	 * <dl>
	 * <dt>com.ibm.ssl.protocol</dt>
	 * <dd>One of: SSL, SSLv3, TLS, TLSv1, SSL_TLS.</dd>
	 * <dt>com.ibm.ssl.contextProvider
	 * <dd>Underlying JSSE provider. For example "IBMJSSE2" or "SunJSSE"</dd>
	 *
	 * <dt>com.ibm.ssl.keyStore</dt>
	 * <dd>The name of the file that contains the KeyStore object that you want the
	 * KeyManager to use. For example /mydir/etc/key.p12</dd>
	 *
	 * <dt>com.ibm.ssl.keyStorePassword</dt>
	 * <dd>The password for the KeyStore object that you want the KeyManager to use.
	 * The password can either be in plain-text, or may be obfuscated using the
	 * static method:
	 * <code>com.ibm.micro.security.Password.obfuscate(char[] password)</code>. This
	 * obfuscates the password using a simple and insecure XOR and Base64 encoding
	 * mechanism. Note that this is only a simple scrambler to obfuscate clear-text
	 * passwords.</dd>
	 *
	 * <dt>com.ibm.ssl.keyStoreType</dt>
	 * <dd>Type of key store, for example "PKCS12", "JKS", or "JCEKS".</dd>
	 *
	 * <dt>com.ibm.ssl.keyStoreProvider</dt>
	 * <dd>Key store provider, for example "IBMJCE" or "IBMJCEFIPS".</dd>
	 *
	 * <dt>com.ibm.ssl.trustStore</dt>
	 * <dd>The name of the file that contains the KeyStore object that you want the
	 * TrustManager to use.</dd>
	 *
	 * <dt>com.ibm.ssl.trustStorePassword</dt>
	 * <dd>The password for the TrustStore object that you want the TrustManager to
	 * use. The password can either be in plain-text, or may be obfuscated using the
	 * static method:
	 * <code>com.ibm.micro.security.Password.obfuscate(char[] password)</code>. This
	 * obfuscates the password using a simple and insecure XOR and Base64 encoding
	 * mechanism. Note that this is only a simple scrambler to obfuscate clear-text
	 * passwords.</dd>
	 *
	 * <dt>com.ibm.ssl.trustStoreType</dt>
	 * <dd>The type of KeyStore object that you want the default TrustManager to
	 * use. Same possible values as "keyStoreType".</dd>
	 *
	 * <dt>com.ibm.ssl.trustStoreProvider</dt>
	 * <dd>Trust store provider, for example "IBMJCE" or "IBMJCEFIPS".</dd>
	 *
	 * <dt>com.ibm.ssl.enabledCipherSuites</dt>
	 * <dd>A list of which ciphers are enabled. Values are dependent on the
	 * provider, for example:
	 * SSL_RSA_WITH_AES_128_CBC_SHA;SSL_RSA_WITH_3DES_EDE_CBC_SHA.</dd>
	 *
	 * <dt>com.ibm.ssl.keyManager</dt>
	 * <dd>Sets the algorithm that will be used to instantiate a KeyManagerFactory
	 * object instead of using the default algorithm available in the platform.
	 * Example values: "IbmX509" or "IBMJ9X509".</dd>
	 *
	 * <dt>com.ibm.ssl.trustManager</dt>
	 * <dd>Sets the algorithm that will be used to instantiate a TrustManagerFactory
	 * object instead of using the default algorithm available in the platform.
	 * Example values: "PKIX" or "IBMJ9X509".</dd>
	 * </dl>
	 * 
	 * @param props
	 *            The SSL {@link Properties}
	 */
	public void setSSLProperties(Properties props) {
		this.sslClientProps = props;
	}

	/**
	 * Returns the HostnameVerifier for the SSL connection.
	 * 
	 * @return the HostnameVerifier for the SSL connection
	 */
	public HostnameVerifier getSSLHostnameVerifier() {
		return sslHostnameVerifier;
	}

	/**
	 * Sets the HostnameVerifier for the SSL connection. Note that it will be used
	 * after handshake on a connection and you should do actions by yourserlf when
	 * hostname is verified error.
	 * <p>
	 * There is no default HostnameVerifier
	 * </p>
	 * 
	 * @param hostnameVerifier
	 *            the {@link HostnameVerifier}
	 */
	public void setSSLHostnameVerifier(HostnameVerifier hostnameVerifier) {
		this.sslHostnameVerifier = hostnameVerifier;
	}

	/**
	 * @return The Debug Properties
	 */
	public Properties getDebug() {
		final String strNull = "null";
		Properties p = new Properties();
		p.put("MqttVersion", new Integer(getMqttVersion()));
		p.put("CleanSession", Boolean.valueOf(isCleanSession()));
		p.put("ConTimeout", new Integer(getConnectionTimeout()));
		p.put("KeepAliveInterval", new Integer(getKeepAliveInterval()));
		p.put("UserName", (getUserName() == null) ? strNull : getUserName());
		p.put("WillDestination", (getWillDestination() == null) ? strNull : getWillDestination());
		if (getSocketFactory() == null) {
			p.put("SocketFactory", strNull);
		} else {
			p.put("SocketFactory", getSocketFactory());
		}
		if (getSSLProperties() == null) {
			p.put("SSLProperties", strNull);
		} else {
			p.put("SSLProperties", getSSLProperties());
		}
		return p;
	}

	public String toString() {
		return Debug.dumpProperties(getDebug(), "Connection options");
	}

	
	
	public static String generateClientId() {
		return CLIENT_ID_PREFIX + System.nanoTime();
	}
}

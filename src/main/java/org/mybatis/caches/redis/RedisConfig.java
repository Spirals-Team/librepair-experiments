/**
 *    Copyright 2015-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.caches.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisConfig extends JedisPoolConfig {

  private String host = Protocol.DEFAULT_HOST;
  private int port = Protocol.DEFAULT_PORT;

  private int connectionTimeout = Protocol.DEFAULT_TIMEOUT;
  private int soTimeout = Protocol.DEFAULT_TIMEOUT;

  private String password;

  private int database = Protocol.DEFAULT_DATABASE;
  private String clientName;

  private String serializer = "jdk";

  private boolean ssl;
  private String sslKeyStoreType;
  private String sslTrustStoreFile;
  private String sslProtocol;
  private String sslAlgorithm;

  public String getClientName() {
    return clientName;
  }

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public int getDatabase() {
    return database;
  }

  public String getHost() {
    return host;
  }

  public String getPassword() {
    return password;
  }

  public int getPort() {
    return port;
  }

  public String getSerializer() {
    return serializer;
  }

  public int getSoTimeout() {
    return soTimeout;
  }

  public String getSslAlgorithm() {
    return sslAlgorithm;
  }

  public String getSslKeyStoreType() {
    return sslKeyStoreType;
  }

  public String getSslProtocol() {
    return sslProtocol;
  }

  public String getSslTrustStoreFile() {
    return sslTrustStoreFile;
  }

  public boolean isSsl() {
    return ssl;
  }

  public void setClientName(String clientName) {
    if ("".equals(clientName)) {
      clientName = null;
    }
    this.clientName = clientName;
  }

  public void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public void setDatabase(int database) {
    this.database = database;
  }

  public void setHost(String host) {
    if (host == null || "".equals(host)) {
      host = Protocol.DEFAULT_HOST;
    }
    this.host = host;
  }

  public void setPassword(String password) {
    if ("".equals(password)) {
      password = null;
    }
    this.password = password;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setSerializer(String serializer) {
    this.serializer = serializer;
  }

  public void setSoTimeout(int soTimeout) {
    this.soTimeout = soTimeout;
  }

  public void setSsl(boolean ssl) {
    this.ssl = ssl;
  }

  public void setSslAlgorithm(String sslAlgorithm) {
    this.sslAlgorithm = sslAlgorithm;
  }

  public void setSslKeyStoreType(String sslKeyStoreType) {
    this.sslKeyStoreType = sslKeyStoreType;
  }

  public void setSslProtocol(String sslProtocol) {
    this.sslProtocol = sslProtocol;
  }

  public void setSslTrustStoreFile(String sslTrustStoreFile) {
    this.sslTrustStoreFile = sslTrustStoreFile;
  }

}

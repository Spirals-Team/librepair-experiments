/*
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

package io.druid.security.basic.authentication.db.cache;

import com.google.inject.Inject;
import io.druid.java.util.common.logger.Logger;
import io.druid.security.basic.authentication.db.updater.BasicAuthenticatorMetadataStorageUpdater;
import io.druid.security.basic.authentication.entity.BasicAuthenticatorUser;

import java.util.Map;

/**
 * Used on coordinator nodes, reading from a BasicAuthenticatorMetadataStorageUpdater that has direct access to the
 * metadata store.
 */
public class MetadataStoragePollingBasicAuthenticatorCacheManager implements BasicAuthenticatorCacheManager
{
  private static final Logger log = new Logger(MetadataStoragePollingBasicAuthenticatorCacheManager.class);

  private final BasicAuthenticatorMetadataStorageUpdater storageUpdater;

  @Inject
  public MetadataStoragePollingBasicAuthenticatorCacheManager(
      BasicAuthenticatorMetadataStorageUpdater storageUpdater
  )
  {
    this.storageUpdater = storageUpdater;

    log.info("Starting coordinator basic authenticator cache manager.");
  }

  @Override
  public void handleAuthenticatorUpdate(String authenticatorPrefix, byte[] serializedUserMap)
  {
  }

  @Override
  public Map<String, BasicAuthenticatorUser> getUserMap(String authenticatorPrefix)
  {
    return storageUpdater.getCachedUserMap(authenticatorPrefix);
  }
}

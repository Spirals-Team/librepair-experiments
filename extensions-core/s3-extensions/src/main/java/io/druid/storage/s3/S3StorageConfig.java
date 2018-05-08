/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.druid.storage.s3;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * General configurations for Amazon S3 storage.
 */
public class S3StorageConfig
{
  /**
   * Server-side encryption type. We use a short name to match the configuration prefix with {@link S3SSEKmsConfig} and
   * {@link S3SSECustomConfig}.
   *
   * @see S3StorageDruidModule#configure
   */
  @JsonProperty
  private String sse = NoopServerSideEncryption.NAME;

  public String getSse()
  {
    return sse;
  }
}

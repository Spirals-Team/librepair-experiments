/**
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
package com.datatorrent.stram.security;

/**
 * Authentication scheme
 *
 * BASIC: BASIC HTTP authentication - RFC 2617
 * SPNEGO: Kerberos based SPNEGO authentication - RFC 4559
 * KERBEROS: Kerberos authentication - RFC 4120
 *
 * @since 3.5.0
 */
public enum AuthScheme
{
  BASIC("basic"), DIGEST("digest"), SPNEGO("kerberos"), KERBEROS("kerberos-standard");

  String name;

  AuthScheme(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  @Override
  public String toString()
  {
    return name;
  }

}

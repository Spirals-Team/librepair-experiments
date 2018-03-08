/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.access;

import org.apache.calcite.sql.SqlAccessType;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory of principal based access
 */
public class PrincipalBasedGuardFactory implements AuthorisationGuardFactory {

  @Override public AuthorizationGuard create(Map<String, Object> operand) {
    Map<String, SqlAccessType> accessMap = convert(operand);
    return new PrincipalBasedGuard(accessMap, CalcitePrincipalFairy.INSTANCE);
  }

  private Map<String, SqlAccessType> convert(Map<String, Object> operand) {
    Map<String, SqlAccessType> result = new HashMap<>();
    for (Map.Entry<String, Object> entry : operand.entrySet()) {
      String userName = entry.getKey();
      SqlAccessType type = SqlAccessType.create(entry.getValue().toString());
      result.put(userName, type);
    }
    return result;
  }

}

// End PrincipalBasedGuardFactory.java

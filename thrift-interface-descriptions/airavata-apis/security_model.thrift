/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

namespace java org.apache.airavata.model.security
namespace php Airavata.Model.Security
namespace cpp apache.airavata.model.security
namespace py airavata.model.security

/*
 * This file describes the definitions of the security model which encapsulates the information that needs to be passed
  to the API methods in order to authenticate and authorize the users.
 *
*/

struct AuthzToken {
    1: required string accessToken,
    2: optional map<string, string> claimsMap
}
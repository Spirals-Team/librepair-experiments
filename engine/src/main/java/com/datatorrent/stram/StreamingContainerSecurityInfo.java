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
package com.datatorrent.stram;

import java.lang.annotation.Annotation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.KerberosInfo;
import org.apache.hadoop.security.SecurityInfo;
import org.apache.hadoop.security.token.TokenIdentifier;
import org.apache.hadoop.security.token.TokenInfo;
import org.apache.hadoop.security.token.TokenSelector;

import com.datatorrent.stram.api.StreamingContainerUmbilicalProtocol;
import com.datatorrent.stram.security.StramDelegationTokenSelector;


/**
 * <p>StreamingContainerSecurityInfo class.</p>
 *
 * @since 0.3.2
 */
public class StreamingContainerSecurityInfo extends SecurityInfo
{

  @Override
  public KerberosInfo getKerberosInfo(Class<?> type, Configuration c)
  {
    return null;
  }

  @Override
  public TokenInfo getTokenInfo(Class<?> type, Configuration c)
  {
    TokenInfo tokenInfo = null;
    if (type.equals(StreamingContainerUmbilicalProtocol.class)) {
      tokenInfo = new TokenInfo()
      {
        @Override
        public Class<? extends TokenSelector<? extends TokenIdentifier>> value()
        {
          return StramDelegationTokenSelector.class;
        }

        @Override
        public Class<? extends Annotation> annotationType()
        {
          return null;
        }
      };
    }
    return tokenInfo;
  }

}

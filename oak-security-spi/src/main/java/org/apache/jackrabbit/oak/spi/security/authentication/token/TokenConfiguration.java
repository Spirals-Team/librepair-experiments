/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.oak.spi.security.authentication.token;

import org.apache.jackrabbit.oak.api.Root;
import org.apache.jackrabbit.oak.spi.security.SecurityConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * Configuration for token management.
 */
public interface TokenConfiguration extends SecurityConfiguration {

    String NAME = "org.apache.jackrabbit.oak.authentication.token";

    /**
     * Returns a new instance of {@link org.apache.jackrabbit.oak.spi.security.authentication.token.TokenProvider}.
     *
     * @param root The target root.
     * @return A new instance of {@link org.apache.jackrabbit.oak.spi.security.authentication.token.TokenProvider}.
     */
    @NotNull
    TokenProvider getTokenProvider(Root root);
}

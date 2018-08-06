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
package org.apache.jackrabbit.oak.security.user.whiteboard;

import java.util.List;

import org.apache.jackrabbit.oak.spi.security.SecurityProvider;
import org.apache.jackrabbit.oak.spi.security.user.action.AuthorizableAction;
import org.apache.jackrabbit.oak.spi.security.user.action.AuthorizableActionProvider;
import org.apache.jackrabbit.oak.spi.security.user.action.CompositeActionProvider;
import org.apache.jackrabbit.oak.spi.whiteboard.AbstractServiceTracker;
import org.jetbrains.annotations.NotNull;

/**
 * Dynamic {@link AuthorizableActionProvider} based on the available
 * whiteboard services.
 */
public class WhiteboardAuthorizableActionProvider
        extends AbstractServiceTracker<AuthorizableActionProvider>
        implements AuthorizableActionProvider {

    public WhiteboardAuthorizableActionProvider() {
        super(AuthorizableActionProvider.class);
    }

    @NotNull
    @Override
    public List<? extends AuthorizableAction> getAuthorizableActions(@NotNull SecurityProvider securityProvider) {
        AuthorizableActionProvider actionProvider = new CompositeActionProvider(getServices());
        return actionProvider.getAuthorizableActions(securityProvider);
    }
}

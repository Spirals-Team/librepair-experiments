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
package org.apache.jackrabbit.oak.security.user;

import javax.annotation.Nonnull;

import org.apache.jackrabbit.oak.plugins.tree.RootFactory;
import org.apache.jackrabbit.oak.plugins.tree.TreeFactory;
import org.apache.jackrabbit.oak.spi.commit.CommitInfo;
import org.apache.jackrabbit.oak.spi.commit.Validator;
import org.apache.jackrabbit.oak.spi.commit.ValidatorProvider;
import org.apache.jackrabbit.oak.spi.security.ConfigurationParameters;
import org.apache.jackrabbit.oak.spi.state.NodeState;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides a validator for user and group management.
 */
class UserValidatorProvider extends ValidatorProvider {

    private final ConfigurationParameters config;

    private MembershipProvider membershipProvider;

    UserValidatorProvider(ConfigurationParameters config) {
        this.config = checkNotNull(config);
    }

    //--------------------------------------------------< ValidatorProvider >---

    @Override @Nonnull
    public Validator getRootValidator(
            NodeState before, NodeState after, CommitInfo info) {
        membershipProvider = new MembershipProvider(RootFactory.createReadOnlyRoot(after), config);
        return new UserValidator(TreeFactory.createReadOnlyTree(before), TreeFactory.createReadOnlyTree(after), this);
    }

    //-----------------------------------------------------------< internal >---
    @Nonnull
    ConfigurationParameters getConfig() {
        return config;
    }

    @Nonnull
    MembershipProvider getMembershipProvider() {
        return membershipProvider;
    }
}
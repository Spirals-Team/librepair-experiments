/**
 * Copyright © 2016-2018 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.extensions.api.plugins.handlers;

import org.thingsboard.server.extensions.api.plugins.PluginContext;
import org.thingsboard.server.extensions.api.plugins.rpc.RpcMsg;

/**
 * @author Andrew Shvayka
 */
public class DefaultRpcMsgHandler implements RpcMsgHandler {

    @Override
    public void process(PluginContext ctx, RpcMsg msg) {
        throw new RuntimeException("Not registered msg type: " + msg.getMsgClazz() + "!");
    }
}

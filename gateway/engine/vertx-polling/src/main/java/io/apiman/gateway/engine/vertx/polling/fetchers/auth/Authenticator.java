/*
 * Copyright 2017 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apiman.gateway.engine.vertx.polling.fetchers.auth;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;

import java.util.Map;

/**
 * Authenticator interface.
 *
 * @author Marc Savy {@literal <marc@rhymewithgravy.com>}
 */
public interface Authenticator {
    /**
     * Validate config.
     * @param config
     * @return fluent
     */
    Authenticator validateConfig(Map<String, String> config);
    /**
     * Authenticate the call.
     * @param vertx
     * @param config
     * @param headerMap header(s) may be set by the authenticator.
     * @param resultHandler invoked with result of the authentication.
     * @return fluent
     */
    Authenticator authenticate(Vertx vertx, Map<String, String> config, MultiMap headerMap, Handler<AsyncResult<Void>> resultHandler);
}
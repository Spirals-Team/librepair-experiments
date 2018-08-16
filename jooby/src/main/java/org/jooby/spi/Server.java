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
package org.jooby.spi;

import java.util.Optional;
import java.util.concurrent.Executor;

/**
 * A HTTP web server.
 *
 * @author edgar
 * @since 0.1.0
 */
public interface Server {

  /**
   * Start the web server.
   *
   * @throws Exception If server fail to start.
   */
  void start() throws Exception;

  /**
   * Stop the web server.
   *
   * @throws Exception If server fail to stop.
   */
  void stop() throws Exception;

  /**
   * Waits for this thread to die.
   *
   * @throws InterruptedException If wait didn't success.
   */
  void join() throws InterruptedException;

  /**
   * Obtain the executor for worker threads.
   *
   * @return The executor for worker threads.
   */
  Optional<Executor> executor();

}

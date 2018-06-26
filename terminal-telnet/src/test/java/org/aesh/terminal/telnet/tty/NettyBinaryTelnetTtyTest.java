/*
 * JBoss, Home of Professional Open Source
 * Copyright 2017 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.aesh.terminal.telnet.tty;

import org.aesh.terminal.telnet.TelnetHandler;
import org.aesh.terminal.telnet.TelnetServerRule;

import java.io.Closeable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class NettyBinaryTelnetTtyTest extends TelnetTtyTestBase {

  public NettyBinaryTelnetTtyTest() {
    binary = true;
  }

  @Override
  protected Function<Supplier<TelnetHandler>, Closeable> serverFactory() {
    return TelnetServerRule.NETTY_SERVER;
  }

  @Override
  protected void assertThreading(Thread connThread, Thread schedulerThread) throws Exception {
    assertTrue(connThread.getName().startsWith("nioEventLoopGroup"));
    assertTrue(schedulerThread.getName().startsWith("nioEventLoopGroup"));
  }
}

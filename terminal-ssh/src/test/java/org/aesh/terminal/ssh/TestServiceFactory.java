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
package org.aesh.terminal.ssh;

import org.aesh.terminal.ssh.netty.NettyIoAcceptor;
import org.aesh.terminal.ssh.netty.NettyIoServiceFactory;
import org.apache.sshd.common.FactoryManager;
import org.apache.sshd.common.future.CloseFuture;
import org.apache.sshd.common.io.IoAcceptor;
import org.apache.sshd.common.io.IoHandler;
import org.apache.sshd.common.io.nio2.Nio2ServiceFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TestServiceFactory extends Nio2ServiceFactory {

  private final NettyIoServiceFactory factory = new NettyIoServiceFactory();

  public TestServiceFactory(FactoryManager factoryManager, ExecutorService service, boolean shutdownOnExit) {
    super(factoryManager, service, shutdownOnExit);
  }

  @Override
  public IoAcceptor createAcceptor(IoHandler handler) {
    return new NettyIoAcceptor(factory, handler);
  }

  @Override
  public CloseFuture close(boolean immediately) {
    factory.close(immediately);
    return super.close(immediately);
  }
}
